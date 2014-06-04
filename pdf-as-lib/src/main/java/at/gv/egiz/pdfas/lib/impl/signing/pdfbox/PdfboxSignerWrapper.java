/*******************************************************************************
 * <copyright> Copyright 2014 by E-Government Innovation Center EGIZ, Graz, Austria </copyright>
 * PDF-AS has been contracted by the E-Government Innovation Center EGIZ, a
 * joint initiative of the Federal Chancellery Austria and Graz University of
 * Technology.
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * http://www.osor.eu/eupl/
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * 
 * This product combines work with different licenses. See the "NOTICE" text
 * file for details on the various modules and licenses.
 * The "NOTICE" text file is part of the distribution. Any derivative works
 * that you distribute must include a readable copy of the "NOTICE" text file.
 ******************************************************************************/
package at.gv.egiz.pdfas.lib.impl.signing.pdfbox;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.pdfbox.exceptions.SignatureException;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.gv.egiz.pdfas.common.exceptions.PdfAsException;
import at.gv.egiz.pdfas.common.exceptions.PdfAsWrappedIOException;
import at.gv.egiz.pdfas.common.utils.PDFUtils;
import at.gv.egiz.pdfas.common.utils.StreamUtils;
import at.gv.egiz.pdfas.lib.api.sign.IPlainSigner;
import at.gv.egiz.pdfas.lib.api.sign.SignParameter;
import at.gv.egiz.pdfas.lib.impl.signing.sig_interface.PDFASSignatureInterface;

public class PdfboxSignerWrapper implements PDFASSignatureInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(PdfboxSignerWrapper.class);

	private IPlainSigner signer;
	@SuppressWarnings("unused")
  private PDSignature signature;
	private int[] byteRange;
	private Calendar date;
	private SignParameter parameters;

	public PdfboxSignerWrapper(IPlainSigner signer, SignParameter parameters) {
		this.signer = signer;
		this.date = Calendar.getInstance();
		this.parameters = parameters;
	}

	public byte[] sign(InputStream inputStream) throws SignatureException,
			IOException {
		byte[] data = StreamUtils.inputStreamToByteArray(inputStream);
		byteRange = PDFUtils.extractSignatureByteRange(data);
		int[] byteRange2 = signature.getByteRange();
		logger.info("Byte Range 2: " + byteRange2);
		try {
			logger.info("Signing with Pdfbox Wrapper");
			byte[] signature = signer.sign(data, byteRange, this.parameters);
			return signature;
		} catch (PdfAsException e) {
			throw new PdfAsWrappedIOException(e);
		}
	}

	public int[] getByteRange() {
		return byteRange;
	}

	public String getPDFSubFilter() {
		return this.signer.getPDFSubFilter();
	}

	public String getPDFFilter() {
		return this.signer.getPDFFilter();
	}

	public void setPDSignature(PDSignature signature) {
		this.signature = signature;
	}

	public Calendar getSigningDate() {
		return this.date;
	}
}
