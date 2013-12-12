package at.gv.egiz.pdfas.wrapper;

import iaik.x509.X509Certificate;

import java.io.File;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.gv.egiz.pdfas.api.PdfAs;
import at.gv.egiz.pdfas.api.analyze.AnalyzeParameters;
import at.gv.egiz.pdfas.api.analyze.AnalyzeResult;
import at.gv.egiz.pdfas.api.commons.DynamicSignatureLifetimeEnum;
import at.gv.egiz.pdfas.api.commons.DynamicSignatureProfile;
import at.gv.egiz.pdfas.api.commons.DynamicSignatureProfileImpl;
import at.gv.egiz.pdfas.api.exceptions.ErrorCode;
import at.gv.egiz.pdfas.api.exceptions.PdfAsException;
import at.gv.egiz.pdfas.api.sign.SignParameters;
import at.gv.egiz.pdfas.api.sign.SignResult;
import at.gv.egiz.pdfas.api.sign.SignatureDetailInformation;
import at.gv.egiz.pdfas.api.verify.VerifyAfterAnalysisParameters;
import at.gv.egiz.pdfas.api.verify.VerifyAfterReconstructXMLDsigParameters;
import at.gv.egiz.pdfas.api.verify.VerifyParameters;
import at.gv.egiz.pdfas.api.verify.VerifyResults;
import at.gv.egiz.pdfas.api.xmldsig.ReconstructXMLDsigAfterAnalysisParameters;
import at.gv.egiz.pdfas.api.xmldsig.ReconstructXMLDsigParameters;
import at.gv.egiz.pdfas.api.xmldsig.ReconstructXMLDsigResult;
import at.gv.egiz.pdfas.lib.api.ByteArrayDataSource;
import at.gv.egiz.pdfas.lib.api.Configuration;
import at.gv.egiz.pdfas.lib.api.PdfAsFactory;
import at.gv.egiz.pdfas.lib.api.StatusRequest;
import at.gv.egiz.pdfas.lib.api.sign.SignParameter;
import at.gv.egiz.pdfas.lib.api.verify.VerifyParameter;
import at.gv.egiz.pdfas.lib.api.verify.VerifyResult;

public class PdfAsObject implements PdfAs {

	private at.gv.egiz.pdfas.lib.api.PdfAs pdfas4;
	private Configuration configuration;
	private File workdir;

	public SignResult sign(SignParameters signParameters) throws PdfAsException {
		SignatureDetailInformation signatureDetailInformation = this
				.prepareSign(signParameters);
		return this.sign(signParameters, signatureDetailInformation);
	}

	public SignResult sign(SignParameters signParameters,
			SignatureDetailInformation signatureDetailInformation)
			throws PdfAsException {

		if (!(signatureDetailInformation instanceof SignatureDetailInformationWrapper)) {
			throw new PdfAsException(ErrorCode.SIGNATURE_COULDNT_BE_CREATED,
					"Invalid state");
		}

		SignatureDetailInformationWrapper sdi = (SignatureDetailInformationWrapper) signatureDetailInformation;
		StatusRequest request = sdi.getStatus();

		if (request.needSignature()) {
			try {
				byte[] signature = sdi.wrapper.getSignParameter4().getPlainSigner().sign(
						request.getSignatureData(), request.getSignatureDataByteRange());
				request.setSigature(signature);
				request = this.pdfas4.process(request);
				if(request.isReady()) {
					at.gv.egiz.pdfas.lib.api.sign.SignResult result = this.pdfas4.finishSign(request);
					sdi.wrapper.syncNewToOld();
					SignResultImpl oldresult = new SignResultImpl(sdi.wrapper.getSignParameters().getOutput(), 
							sdi.getX509Certificate());
					return oldresult;
				} else {
					throw new PdfAsException(ErrorCode.SIGNATURE_COULDNT_BE_CREATED,
							"Invalid state");
				}
			} catch (at.gv.egiz.pdfas.common.exceptions.PdfAsException e) {
				throw new PdfAsException(
						ErrorCode.SIGNATURE_COULDNT_BE_CREATED, e.getMessage());
			}
		} else {
			throw new PdfAsException(ErrorCode.SIGNATURE_COULDNT_BE_CREATED,
					"Invalid state");
		}
	}

	public VerifyResults verify(VerifyParameters verifyParameters)
			throws PdfAsException {
		try {
			VerifyParameter newParameter = VerifyParameterWrapper
					.toNewParameters(verifyParameters, this.pdfas4.getConfiguration());

			List<VerifyResult> results = this.pdfas4.verify(newParameter);

			Iterator<VerifyResult> it = results.iterator();

			List<at.gv.egiz.pdfas.api.verify.VerifyResult> resultList = new ArrayList<at.gv.egiz.pdfas.api.verify.VerifyResult>();

			while (it.hasNext()) {
				VerifyResult newResult = it.next();
				at.gv.egiz.pdfas.api.verify.VerifyResult oldResult = new VerifyResultWrapper(
						newResult);
				resultList.add(oldResult);
			}

			return new VerifyResultsImpl(resultList);
		} catch (at.gv.egiz.pdfas.common.exceptions.PdfAsException e) {
			throw new PdfAsException(0, e.getMessage());
		}
	}

	public AnalyzeResult analyze(AnalyzeParameters analyzeParameters)
			throws PdfAsException {
		throw new PdfAsException(ErrorCode.FUNCTION_NOT_AVAILABLE,
				new RuntimeException());
	}

	public ReconstructXMLDsigResult reconstructXMLDSIG(
			ReconstructXMLDsigParameters reconstructXMLDsigParameters)
			throws PdfAsException {
		throw new PdfAsException(ErrorCode.FUNCTION_NOT_AVAILABLE,
				new RuntimeException());
	}

	public ReconstructXMLDsigResult reconstructXMLDSIG(
			ReconstructXMLDsigAfterAnalysisParameters reconstructXMLDsigParameters)
			throws PdfAsException {
		throw new PdfAsException(ErrorCode.FUNCTION_NOT_AVAILABLE,
				new RuntimeException());
	}

	public VerifyResults verify(
			VerifyAfterAnalysisParameters verifyAfterAnalysisParameters)
			throws PdfAsException {
		throw new PdfAsException(ErrorCode.FUNCTION_NOT_AVAILABLE,
				new RuntimeException());
	}

	public VerifyResults verify(
			VerifyAfterReconstructXMLDsigParameters verifyAfterReconstructXMLDsigParameters)
			throws PdfAsException {
		throw new PdfAsException(ErrorCode.FUNCTION_NOT_AVAILABLE,
				new RuntimeException());
	}

	public void reloadConfig() throws PdfAsException {
		this.pdfas4 = at.gv.egiz.pdfas.lib.api.PdfAsFactory
				.createPdfAs(this.workdir);
		this.configuration = this.pdfas4.getConfiguration();
	}

	public List getProfileInformation() throws PdfAsException {
		throw new PdfAsException(ErrorCode.FUNCTION_NOT_AVAILABLE,
				new RuntimeException());
	}

	public DynamicSignatureProfile createDynamicSignatureProfile(
			String parentProfile, DynamicSignatureLifetimeEnum mode) {
		return DynamicSignatureProfileImpl.createFromParent(null,
				parentProfile, mode, configuration);
	}

	public DynamicSignatureProfile createDynamicSignatureProfile(
			String myUniqueName, String parentProfile,
			DynamicSignatureLifetimeEnum mode) {
		return DynamicSignatureProfileImpl.createFromParent(myUniqueName,
				parentProfile, mode, configuration);
	}

	public DynamicSignatureProfile createEmptyDynamicSignatureProfile(
			DynamicSignatureLifetimeEnum mode) {
		return DynamicSignatureProfileImpl.createEmptyProfile(null, mode,
				configuration);
	}

	public DynamicSignatureProfile createEmptyDynamicSignatureProfile(
			String myUniqueName, DynamicSignatureLifetimeEnum mode) {
		return DynamicSignatureProfileImpl.createEmptyProfile(myUniqueName,
				mode, configuration);
	}

	public DynamicSignatureProfile loadDynamicSignatureProfile(
			String profileName) {
		return DynamicSignatureProfileImpl.loadProfile(profileName);
	}

	public SignatureDetailInformation prepareSign(SignParameters signParameters)
			throws PdfAsException {
		try {
			// Prepare Signature

			SignParameter signParameter4 = PdfAsFactory.createSignParameter(
					this.configuration, new ByteArrayDataSource(signParameters
							.getDocument().getAsByteArray()));

			SignParameterWrapper wrapper = new SignParameterWrapper(
					signParameters, signParameter4);
			SignatureDetailInformationWrapper sdi = null;

			wrapper.syncOldToNew();

			StatusRequest request = this.pdfas4.startSign(wrapper
					.getSignParameter4());

			if (request.needCertificate()) {
				X509Certificate certificate = signParameter4.getPlainSigner()
						.getCertificate();
				sdi = new SignatureDetailInformationWrapper(certificate);
				request.setCertificate(certificate.getEncoded());
				request = this.pdfas4.process(request);
				if (request.needSignature()) {
					sdi.setDataSource(new ByteArrayDataSource_OLD(request
							.getSignatureData()));
				}
				sdi.wrapper = wrapper;
				sdi.setStatus(request);
			}

			return sdi;
		} catch (at.gv.egiz.pdfas.common.exceptions.PdfAsException e) {
			throw new PdfAsException(ErrorCode.SIGNATURE_COULDNT_BE_CREATED,
					e.getMessage());
		} catch (CertificateEncodingException e) {
			throw new PdfAsException(ErrorCode.SIGNATURE_COULDNT_BE_CREATED,
					e.getMessage());
		} catch (CertificateException e) {
			throw new PdfAsException(ErrorCode.SIGNATURE_COULDNT_BE_CREATED,
					e.getMessage());
		}
	}

	public SignResult finishSign(SignParameters signParameters,
			SignatureDetailInformation signatureDetailInformation)
			throws PdfAsException {
		return sign(signParameters, signatureDetailInformation);
	}

	public PdfAsObject(File workdirectory) {
		this.workdir = workdirectory;
		this.pdfas4 = at.gv.egiz.pdfas.lib.api.PdfAsFactory
				.createPdfAs(workdirectory);
		this.configuration = this.pdfas4.getConfiguration();
	}

}
