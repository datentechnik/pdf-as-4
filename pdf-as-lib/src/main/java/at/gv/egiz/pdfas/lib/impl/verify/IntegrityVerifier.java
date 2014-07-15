package at.gv.egiz.pdfas.lib.impl.verify;

import iaik.asn1.ObjectID;
import iaik.asn1.structures.AlgorithmID;
import iaik.cms.ContentInfo;
import iaik.cms.SignedData;
import iaik.cms.SignerInfo;
import iaik.x509.X509Certificate;

import java.io.ByteArrayInputStream;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.gv.egiz.pdfas.common.exceptions.PdfAsException;
import at.gv.egiz.pdfas.common.exceptions.PdfAsSignatureException;
import at.gv.egiz.pdfas.lib.api.Configuration;
import at.gv.egiz.pdfas.lib.api.verify.VerifyResult;

public class IntegrityVerifier implements IVerifier {

	private static final Logger logger = LoggerFactory
			.getLogger(FullVerifier.class);
	
	public List<VerifyResult> verify(byte[] signature, byte[] signatureContent,
			Date verificationTime) throws PdfAsException {
		try {
			List<VerifyResult> result = new ArrayList<VerifyResult>();
			
			SignedData signedData = new SignedData(signatureContent, new AlgorithmID[] { 
					AlgorithmID.sha256, AlgorithmID.sha1, AlgorithmID.ripeMd160,  AlgorithmID.ripeMd160_ISO
			});			
			ContentInfo ci = new ContentInfo(new ByteArrayInputStream(signature
					));
			if (!ci.getContentType().equals(ObjectID.cms_signedData)) {
				throw new PdfAsException("error.pdf.verify.01");
			}
			//SignedData signedData = (SignedData)ci.getContent();
			//signedData.setContent(contentData);

			signedData.decode(ci.getContentInputStream());
			
			// get the signer infos
			SignerInfo[] signerInfos = signedData.getSignerInfos();
			// verify the signatures
			for (int i = 0; i < signerInfos.length; i++) {
				VerifyResultImpl verifyResult = new VerifyResultImpl();
				try {
					// verify the signature for SignerInfo at index i
					X509Certificate signer_cert = signedData.verify(i);
					logger.info("Signature Algo: {}, Digest {}",  
							signedData.getSignerInfos()[i].getSignatureAlgorithm(),
							signedData.getSignerInfos()[i].getDigestAlgorithm());
					// if the signature is OK the certificate of the
					// signer is returned
					logger.info("Signature OK from signer: "
							+ signer_cert.getSubjectDN());
					verifyResult.setSignerCertificate(signer_cert);
					verifyResult.setValueCheckCode(new SignatureCheckImpl(0, "OK"));
					verifyResult.setManifestCheckCode(new SignatureCheckImpl(99, "not checked"));
					verifyResult.setCertificateCheck(new SignatureCheckImpl(99, "not checked"));
					verifyResult.setVerificationDone(true);
				} catch (SignatureException ex) {
					// if the signature is not OK a SignatureException
					// is thrown
					logger.info("Signature ERROR from signer: "
							+ signedData.getCertificate(
									signerInfos[i].getSignerIdentifier())
									.getSubjectDN(), ex);
					
					verifyResult.setSignerCertificate(
							signedData.getCertificate(signerInfos[i].getSignerIdentifier()));
					verifyResult.setValueCheckCode(new SignatureCheckImpl(1, "failed to check signature"));
					verifyResult.setManifestCheckCode(new SignatureCheckImpl(99, "not checked"));
					verifyResult.setCertificateCheck(new SignatureCheckImpl(99, "not checked"));
					verifyResult.setVerificationDone(false);
					verifyResult.setVerificationException(new PdfAsSignatureException("failed to check signature", ex));
				}
				result.add(verifyResult);
			}

			return result;
		} catch (Throwable e) {
			throw new PdfAsException("error.pdf.verify.02", e);
		}
	}

	public void setConfiguration(Configuration config) {
		
	}
}