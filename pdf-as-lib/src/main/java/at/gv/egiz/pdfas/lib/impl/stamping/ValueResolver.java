package at.gv.egiz.pdfas.lib.impl.stamping;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.gv.egiz.pdfas.common.settings.IProfileConstants;
import at.gv.egiz.pdfas.common.settings.SignatureProfileSettings;
import at.gv.egiz.pdfas.lib.impl.status.RequestedSignature;

/**
 * Created with IntelliJ IDEA. User: afitzek Date: 9/11/13 Time: 11:11 AM To
 * change this template use File | Settings | File Templates.
 */
public class ValueResolver implements IProfileConstants, IResolver {

	private static final Logger logger = LoggerFactory
			.getLogger(ValueResolver.class);

	public static final String PatternRegex = "\\$(\\{[^\\$]*\\})";

	public static final String defaultDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static final String EXP_START = "${";
	public static final String EXP_END = "}";

	public String resolve(String key, String value,
			SignatureProfileSettings settings, RequestedSignature signature) {

		logger.debug("Resolving value for key: " + key);
		logger.debug("Resolving value with value: " + value);

		if (key.equals(SIG_DATE)) {
			if (value == null) {
				value = defaultDateFormat;
			}
			// Value holds the date format!
			SimpleDateFormat formater = new SimpleDateFormat(value);
			Calendar cal = Calendar.getInstance();
			return formater.format(cal.getTime());
		}
		
		if (value != null) {

			Pattern pattern = Pattern.compile(PatternRegex);
			Matcher matcher = pattern.matcher(value);
			CertificateResolver certificateResolver = new CertificateResolver(
					signature.getCertificate());
			String result = "";
			int curidx = 0;
			if (matcher.find()) {
				do {
					int idx = matcher.start(0);
					int idxe = matcher.end(0);
					result += value.substring(curidx, idx);
					curidx = idxe;
					result += certificateResolver.resolve(key,
							matcher.group(1), settings, signature);
				} while (matcher.find());
			} else {
				result = value;
			}
			return result;
		}

		return value;
	}

}
