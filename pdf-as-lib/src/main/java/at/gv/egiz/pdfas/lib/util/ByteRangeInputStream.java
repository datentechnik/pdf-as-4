package at.gv.egiz.pdfas.lib.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.apache.commons.io.IOUtils;

/**
 * InputStream considering byte ranges as used for pdf signatures.
 * @author Thomas Knall, PrimeSign GmbH
 *
 */
public class ByteRangeInputStream extends FilterInputStream {

	/**
	 * Reflects a single byte range.
	 * 
	 * @author Thomas Knall, PrimeSign GmbH
	 *
	 */
	private static class ByteRange {

		private final int offset;
		private int bytesLeft;
		private final boolean gap;
		
		private Byte firstByte = (byte) '<';
		private Byte lastByte =  (byte) '>';

		/**
		 * Creates a single byte range.
		 * 
		 * @param offset The offset. (must be non-negative)
		 * @param length The length. (must be non-negative)
		 * @param gap    {@code true} in case the byte range represents a gap between two non-gap byte ranges, {@code false}
		 *               otherwise.
		 */
		public ByteRange(int offset, int length, boolean gap) {
			if (offset < 0 || length < 0) {
				throw new IllegalArgumentException("Negative offset or negative length are not supported.");
			}
			this.offset = offset;
			this.bytesLeft = length;
			this.gap = gap;
		}

		/**
		 * Returns the offset.
		 * @return The offset.
		 */
		public int getOffset() {
			return offset;
		}

		/**
		 * Returns the number of bytes not yet {@link #consume(int) consumed}.
		 * 
		 * @return The number of bytes left.
		 */
		public int bytesLeft() {
			return this.bytesLeft;
		}
		
		/**
		 * Consumes {@code bytes} bytes.
		 * 
		 * @param bytes The number of bytes to be consumed. (must be smaller or equal {@link #bytesLeft()}.
		 */
		public void consume(int bytes) {
			if (bytes > this.bytesLeft) {
				throw new IllegalArgumentException("Unable to consume more that bytesLeft() bytes.");
			}
			this.bytesLeft -= bytes;
		}

		public boolean isGap() {
			return gap;
		}

	}
	
	private Iterator<ByteRange> ranges;
	private ByteRange currentRange;
	private long currentPosition = 0;
	
	public ByteRangeInputStream(@Nonnull InputStream in, @Nonnull int[] byteRange) {
		this(in, byteRange, false);
	}

	public ByteRangeInputStream(@Nonnull InputStream in, @Nonnull int[] byteRange, boolean withGaps) {
		super(in);
		List<ByteRange> byteRanges = prepareByteRanges(Objects.requireNonNull(byteRange, "'byteRange' must not be null."));
		if (withGaps) {
			byteRanges = addGaps(byteRanges);
		}
		ranges = byteRanges.iterator();
	}
	
	@Nonnull
	private List<ByteRange> addGaps(@Nonnull List<ByteRange> byteRanges) {

		if (byteRanges.isEmpty()) {
			return Collections.emptyList();
		}
		
		List<ByteRange> result = new ArrayList<>();

		ByteRange lastByteRange;
		
		Iterator<ByteRange> it = byteRanges.iterator();
		
		// make sure not to start with gap
		result.add(lastByteRange = it.next());
		
		while (it.hasNext()) {
			ByteRange byteRange = it.next();
			// add gap byte range
			int offset = lastByteRange.getOffset() + lastByteRange.bytesLeft();
			int length = byteRange.getOffset() - offset;
			result.add(new ByteRange(offset, length, true));
			// add regular byte range
			result.add(byteRange);
			lastByteRange = byteRange;
		}
		
		return result;
		
	}

	/**
	 * Prepares and validates the provided byte ranges.
	 * 
	 * @param byteRange The byte ranges (offset/length tuples). (required; must not be {@code null})
	 * @return A list of byte ranges. (never {@code null} but may be empty)
	 */
	@Nonnull
	private List<ByteRange> prepareByteRanges(@Nonnull int[] byteRange) {
		if (byteRange.length % 2 != 0) {
			throw new IllegalArgumentException("'byteRange' must provide offset and length tuples.");
		}
		List<ByteRange> byteRanges = new ArrayList<>(byteRange.length / 2);
		long position = 0;
		for (int i = 0; i < byteRange.length / 2; i++) {
			int offset = byteRange[i * 2];
			int length = byteRange[i * 2 + 1];
			if (offset < position) {
				throw new IllegalArgumentException("Overlapping byteRanges are not supported: offset=" + offset + ", length=" + length);
			}
			byteRanges.add(new ByteRange(offset, length, false));
			position = offset + length;
		}
		return byteRanges;
	}

	/**
	 * Makes sure {@link #currentRange} reflects the byte range to read from. {@link #currentRange} is set {@code null} in
	 * case to further readable byte ranges are available.
	 * 
	 * @throws IOException Thrown in case of error reading from original stream.
	 */
	private void updateCurrentRange() throws IOException {
		
		if (currentRange != null && currentRange.bytesLeft() > 0) {
			// no need to update currentRange
			return;
		}
		
		// update currentRange
		
		while (ranges.hasNext() && (currentRange = ranges.next()).bytesLeft <= 0) {
			// skip empty ranges
		}

		// do we have readable byte ranges left ?
		if (currentRange != null && currentRange.bytesLeft() > 0) {
			
			while (currentPosition < currentRange.getOffset() ) {
				// skip bytes until reaching next byte range offset 
				long skipped = super.skip(currentRange.getOffset() - currentPosition);
				currentPosition += skipped;
			}
			
		} else {
			// no further byte ranges to read
			currentRange = null;
		}
		
	}
	
	@Override
	public int available() throws IOException {
		updateCurrentRange();
		if (currentRange == null) {
			return 0;
		}
		return Math.min(currentRange.bytesLeft(), super.available());
	}

	@Override
	public int read() throws IOException {
		updateCurrentRange();
		if (currentRange == null) {
			return -1;
		}
		int value = super.read();
		currentRange.consume(1);
		currentPosition++;
		return currentRange.isGap() ? gapValueRespectingDelimiter() : value;
	}
	
	private int gapValueRespectingDelimiter() {
		if (!currentRange.isGap()) {
			throw new IllegalStateException("Current range expected to reflect a gap.");
		}
		int value;
		if (currentRange.firstByte != null) {
			value = currentRange.firstByte;
			currentRange.firstByte = null;
		} else if (currentRange.bytesLeft() == 0) {
			if (currentRange.lastByte == null) {
				throw new IllegalStateException("Delimiter '>' already used for gap.");
			}
			value = currentRange.lastByte;
			currentRange.lastByte = null;
		} else {
			value = 0;
		}
		return value;
	}

	@Override
	public int read(byte[] b) throws IOException {
		return read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		updateCurrentRange();
		if (currentRange == null) {
			return -1;
		}
		int bytesRead = super.read(b, off, Math.min(currentRange.bytesLeft(), len));
		currentRange.consume(bytesRead);
		currentPosition += bytesRead;
		
		if (currentRange.isGap()) {
			for (int i = off; i < off + bytesRead; i++) {
				b[i] = 0;
			}
			adjustGapRespectingDelimiter(b, off, bytesRead);
		}
		
		return bytesRead;
	}
	
	private void adjustGapRespectingDelimiter(byte[] gap, int off, int len) {
		if (!currentRange.isGap()) {
			throw new IllegalStateException("Current range expected to reflect a gap.");
		}
		if (currentRange.firstByte != null) {
			gap[off] = currentRange.firstByte;
			currentRange.firstByte = null;
		}
		if (currentRange.lastByte != null && currentRange.bytesLeft() == 0) {
			gap[off + len - 1] = currentRange.lastByte;
			currentRange.lastByte = null;
		}
	}

	@Override
	public long skip(long n) throws IOException {
		return IOUtils.skip(this, n); // reads n bytes (this is essential for our code)
	}

	@Override
	public synchronized void mark(int readlimit) {
		throw new IllegalStateException(getClass().getSimpleName() + " does not support mark.");
	}

	@Override
	public synchronized void reset() throws IOException {
		throw new IOException("The stream has not been marked since " + getClass().getSimpleName() + " does not support mark.");
	}

	@Override
	public boolean markSupported() {
		return false;
	}

}
