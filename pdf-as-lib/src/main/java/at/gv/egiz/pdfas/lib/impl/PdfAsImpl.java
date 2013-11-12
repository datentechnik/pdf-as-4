package at.gv.egiz.pdfas.lib.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.gv.egiz.pdfas.common.exceptions.PdfAsException;
import at.gv.egiz.pdfas.common.exceptions.PdfAsSettingsException;
import at.gv.egiz.pdfas.common.settings.ISettings;
import at.gv.egiz.pdfas.common.settings.Settings;
import at.gv.egiz.pdfas.common.settings.SignatureProfileSettings;
import at.gv.egiz.pdfas.common.utils.PDFUtils;
import at.gv.egiz.pdfas.lib.api.Configuration;
import at.gv.egiz.pdfas.lib.api.IConfigurationConstants;
import at.gv.egiz.pdfas.lib.api.PdfAs;
import at.gv.egiz.pdfas.lib.api.StatusRequest;
import at.gv.egiz.pdfas.lib.api.sign.SignParameter;
import at.gv.egiz.pdfas.lib.api.sign.SignResult;
import at.gv.egiz.pdfas.lib.api.verify.VerifyParameter;
import at.gv.egiz.pdfas.lib.api.verify.VerifyResult;
import at.gv.egiz.pdfas.lib.impl.configuration.ConfigurationImpl;
import at.gv.egiz.pdfas.lib.impl.configuration.PlaceholderConfiguration;
import at.gv.egiz.pdfas.lib.impl.configuration.SignatureProfileConfiguration;
import at.gv.egiz.pdfas.lib.impl.positioning.Positioning;
import at.gv.egiz.pdfas.lib.impl.signing.IPdfSigner;
import at.gv.egiz.pdfas.lib.impl.signing.PdfSignerFactory;
import at.gv.egiz.pdfas.lib.impl.signing.pdfbox.PdfboxSignerWrapper;
import at.gv.egiz.pdfas.lib.impl.signing.sig_interface.SignatureDataExtractor;
import at.gv.egiz.pdfas.lib.impl.stamping.IPDFStamper;
import at.gv.egiz.pdfas.lib.impl.stamping.IPDFVisualObject;
import at.gv.egiz.pdfas.lib.impl.stamping.StamperFactory;
import at.gv.egiz.pdfas.lib.impl.stamping.TableFactory;
import at.gv.egiz.pdfas.lib.impl.status.OperationStatus;
import at.gv.egiz.pdfas.lib.impl.status.RequestedSignature;
import at.gv.egiz.pdfas.lib.impl.verify.IVerifyFilter;
import at.gv.egiz.pdfas.lib.impl.verify.VerifierDispatcher;
import at.knowcenter.wag.egov.egiz.pdf.PDFUtilities;
import at.knowcenter.wag.egov.egiz.pdf.PositioningInstruction;
import at.knowcenter.wag.egov.egiz.pdf.TablePos;
import at.knowcenter.wag.egov.egiz.table.Table;

public class PdfAsImpl implements PdfAs, IConfigurationConstants {

	private static final Logger logger = LoggerFactory
			.getLogger(PdfAsImpl.class);

	private Settings settings;

	public PdfAsImpl(File cfgFile) {
		logger.info("Initializing PDF-AS with config: " + cfgFile.getPath());
		this.settings = new Settings(cfgFile);
	}

	public SignResult sign(SignParameter parameter) throws PdfAsException {

		logger.trace("sign started");

		// TODO: verify signParameter

		try {
			// Status initialization
			if (!(parameter.getConfiguration() instanceof ISettings)) {
				throw new PdfAsSettingsException("Invalid settings object!");
			}

			ISettings settings = (ISettings) parameter.getConfiguration();
			OperationStatus status = new OperationStatus(settings, parameter);
			PlaceholderConfiguration placeholderConfiguration = status
					.getPlaceholderConfiguration();

			RequestedSignature requestedSignature = new RequestedSignature(
					status);

			status.setRequestedSignature(requestedSignature);
			
			requestedSignature.setCertificate(status.getSignParamter()
					.getPlainSigner().getCertificate());

			// Only use this profileID because validation was done in
			// RequestedSignature
			String signatureProfileID = requestedSignature
					.getSignatureProfileID();

			logger.info("Selected signature Profile: " + signatureProfileID);

			// SignatureProfileConfiguration signatureProfileConfiguration =
			// status
			// .getSignatureProfileConfiguration(signatureProfileID);

			// set Original PDF Document Data
			status.getPdfObject().setOriginalDocument(
					parameter.getDataSource().getByteData());

			// Placeholder search?
			if (placeholderConfiguration.isGlobalPlaceholderEnabled()) {
				// TODO: Do placeholder search
			}

			this.stampPdf(status);

			/*
			 * if (requestedSignature.isVisual()) {
			 * logger.info("Creating visual siganture block"); //
			 * ================================================================
			 * // SignBlockCreationStage (visual) -> create visual signature //
			 * block (logicaly) SignatureProfileSettings
			 * signatureProfileSettings = TableFactory
			 * .createProfile(signatureProfileID, settings);
			 * 
			 * Table main = TableFactory.createSigTable(
			 * signatureProfileSettings, MAIN, settings, requestedSignature);
			 * 
			 * IPDFStamper stamper = StamperFactory
			 * .createDefaultStamper(settings); IPDFVisualObject visualObject =
			 * stamper.createVisualPDFObject( status.getPdfObject(), main);
			 * 
			 * //
			 * ================================================================
			 * // PositioningStage (visual) -> find position or use fixed //
			 * position
			 * 
			 * String posString = status.getSignParamter()
			 * .getSignaturePosition();
			 * 
			 * if (posString == null) { posString =
			 * signatureProfileConfiguration .getDefaultPositioning(); }
			 * 
			 * TablePos tablePos = null;
			 * 
			 * if (posString == null) { tablePos = new TablePos(); } else {
			 * tablePos = new TablePos(posString); }
			 * 
			 * PDDocument originalDocument = PDDocument .load(new
			 * ByteArrayInputStream(status.getPdfObject()
			 * .getOriginalDocument()));
			 * 
			 * PositioningInstruction positioningInstruction = Positioning
			 * .determineTablePositioning(tablePos, "", originalDocument,
			 * visualObject);
			 * 
			 * //
			 * ================================================================
			 * // StampingStage (visual) -> stamp logical signature block to //
			 * location (itext)
			 * 
			 * byte[] incrementalUpdate = stamper.writeVisualObject(
			 * visualObject, positioningInstruction, status
			 * .getPdfObject().getOriginalDocument());
			 * status.getPdfObject().setStampedDocument(incrementalUpdate); }
			 * else { logger.info("No visual siganture block"); // Stamped
			 * Object is equal to original
			 * status.getPdfObject().setStampedDocument(
			 * status.getPdfObject().getOriginalDocument()); }
			 */

			// TODO: Create signature
			IPdfSigner signer = PdfSignerFactory.createPdfSigner();
			signer.signPDF(status.getPdfObject(), requestedSignature,
					new PdfboxSignerWrapper(status.getSignParamter()
							.getPlainSigner()));

			// status.getPdfObject().setSignedDocument(status.getPdfObject().getStampedDocument());

			// ================================================================
			// Create SignResult
			SignResultImpl result = new SignResultImpl(status.getSignParamter()
					.getOutput());
			OutputStream outputStream = result.getOutputDocument()
					.createOutputStream();

			outputStream.write(status.getPdfObject().getSignedDocument());

			outputStream.close();

			return result;
		} catch (Throwable e) {
			logger.error("sign failed " + e.getMessage(), e);
			throw new PdfAsException("sign Failed", e);
		} finally {
			logger.trace("sign done");
		}
	}

	public List<VerifyResult> verify(VerifyParameter parameter)
			throws PdfAsException {
		try {
			List<VerifyResult> result = new ArrayList<VerifyResult>();
			ISettings settings = (ISettings) parameter.getConfiguration();
			VerifierDispatcher verifier = new VerifierDispatcher(settings);
			PDDocument doc = PDDocument.load(new ByteArrayInputStream(parameter
					.getDataSource().getByteData()));

			COSDictionary trailer = doc.getDocument().getTrailer();
			COSDictionary root = (COSDictionary) trailer
					.getDictionaryObject(COSName.ROOT);
			COSDictionary acroForm = (COSDictionary) root
					.getDictionaryObject(COSName.ACRO_FORM);
			COSArray fields = (COSArray) acroForm
					.getDictionaryObject(COSName.FIELDS);
			for (int i = 0; i < fields.size(); i++) {
				COSDictionary field = (COSDictionary) fields.getObject(i);
				String type = field.getNameAsString("FT");
				if ("Sig".equals(type)) {
					logger.trace("Found Signature: ");
					COSBase base = field.getDictionaryObject("V");
					COSDictionary dict = (COSDictionary) base;

					logger.debug("Signer: " + dict.getNameAsString("Name"));
					logger.debug("SubFilter: "
							+ dict.getNameAsString("SubFilter"));
					logger.debug("Filter: " + dict.getNameAsString("Filter"));
					logger.debug("Modified: " + dict.getNameAsString("M"));
					COSArray byteRange = (COSArray) dict
							.getDictionaryObject("ByteRange");

					StringBuilder sb = new StringBuilder();
					int[] bytes = new int[byteRange.size()];
					for (int j = 0; j < byteRange.size(); j++) {
						bytes[j] = byteRange.getInt(j);
						sb.append(" " + bytes[j]);
					}

					logger.debug("ByteRange" + sb.toString());

					COSString content = (COSString) dict
							.getDictionaryObject("Contents");
					/*
					 * logger.trace("Content: " +
					 * StringUtils.bytesToHexString(content.getBytes()));
					 */

					ByteArrayOutputStream contentData = new ByteArrayOutputStream();
					for (int j = 0; j < bytes.length; j = j + 2) {
						int offset = bytes[j];
						int length = bytes[j + 1];
						contentData.write(parameter.getDataSource()
								.getByteData(), offset, length);
					}
					contentData.close();

					IVerifyFilter verifyFilter = verifier.getVerifier(
							dict.getNameAsString("Filter"),
							dict.getNameAsString("SubFilter"));

					List<VerifyResult> results = verifyFilter.verify(
							contentData.toByteArray(), content.getBytes());

					result.addAll(results);
				}
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (PdfAsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new PdfAsException();
	}

	public Configuration getConfiguration() {
		return new ConfigurationImpl(this.settings);
	}

	public StatusRequest startSign(SignParameter parameter)
			throws PdfAsException {
		// TODO: VERIFY PARAMETERS
		StatusRequestImpl request = new StatusRequestImpl();

		try {
			// Status initialization
			if (!(parameter.getConfiguration() instanceof ISettings)) {
				throw new PdfAsSettingsException("Invalid settings object!");
			}

			ISettings settings = (ISettings) parameter.getConfiguration();
			OperationStatus status = new OperationStatus(settings, parameter);

			RequestedSignature requestedSignature = new RequestedSignature(
					status);

			status.setRequestedSignature(requestedSignature);

			request.setStatus(status);

			request.setNeedCertificate(true);

			return request;
		} catch (Throwable e) {
			logger.error("startSign", e);
			throw new PdfAsException("startSign", e);
		}
	}

	public StatusRequest process(StatusRequest statusRequest)
			throws PdfAsException {
		if (!(statusRequest instanceof StatusRequestImpl)) {
			throw new PdfAsException("Invalid Status");
		}

		StatusRequestImpl request = (StatusRequestImpl) statusRequest;
		OperationStatus status = request.getStatus();

		if (request.needCertificate()) {
			try {
				status.getRequestedSignature().setCertificate(
						request.getCertificate());

				// set Original PDF Document Data
				status.getPdfObject().setOriginalDocument(
						status.getSignParamter().getDataSource().getByteData());

				// STAMPER!
				stampPdf(status);
				request.setNeedCertificate(false);

				status.setSigningDate(Calendar.getInstance());

				// GET Signature DATA
				String pdfFilter = status.getSignParamter().getPlainSigner()
						.getPDFFilter();
				String pdfSubFilter = status.getSignParamter().getPlainSigner()
						.getPDFSubFilter();
				SignatureDataExtractor signatureDataExtractor = new SignatureDataExtractor(
						request.getCertificate(), pdfFilter, pdfSubFilter,
						status.getSigningDate());
				
				IPdfSigner signer = PdfSignerFactory.createPdfSigner();
				signer.signPDF(status.getPdfObject(),
						status.getRequestedSignature(), signatureDataExtractor);
				
				StringBuilder sb = new StringBuilder();
				
				int[] byteRange = PDFUtils.extractSignatureByteRange(signatureDataExtractor
						.getSignatureData());
				
				for(int i = 0; i < byteRange.length; i++) {
					sb.append(" " + byteRange[i]);
				}
				
				logger.info("ByteRange: " + sb.toString());
				
				request.setSignatureData(signatureDataExtractor
						.getSignatureData());
				request.setByteRange(byteRange);
				request.setNeedSignature(true);

			} catch (Throwable e) {
				logger.error("process", e);
				throw new PdfAsException("process", e);
			}
		} else if (request.needSignature()) {
			request.setNeedSignature(false);
			// Inject signature byte[] into signedDocument
			int offset = request.getSignatureDataByteRange()[1] + 1;

			String signature = new COSString(request.getSignature()).getHexString();
			byte[] pdfSignature = signature.getBytes();
			
			for (int i = 0; i < pdfSignature.length; i++) {
				status.getPdfObject().getSignedDocument()[offset + i] = pdfSignature[i];
			}
			/*
			 * 
			 * String pdfFilter = status.getSignParamter().getPlainSigner()
			 * .getPDFFilter(); String pdfSubFilter =
			 * status.getSignParamter().getPlainSigner() .getPDFSubFilter();
			 * SignatureDataInjector injector = new SignatureDataInjector(
			 * request.getCertificate(), pdfFilter, pdfSubFilter,
			 * status.getSigningDate(), request.getSignature(),
			 * request.getSignatureData());
			 * 
			 * IPdfSigner signer = PdfSignerFactory.createPdfSigner();
			 * signer.signPDF(status.getPdfObject(),
			 * status.getRequestedSignature(), injector);
			 */
			request.setIsReady(true);
		} else {
			throw new PdfAsException("Invalid Status");
		}

		return request;
	}

	public SignResult finishSign(StatusRequest statusRequest)
			throws PdfAsException {
		if (!(statusRequest instanceof StatusRequestImpl)) {
			throw new PdfAsException("Invalid Status");
		}

		StatusRequestImpl request = (StatusRequestImpl) statusRequest;
		OperationStatus status = request.getStatus();

		if (!request.isReady()) {
			throw new PdfAsException("Invalid Status");
		}

		try {
			return createSignResult(status);
		} catch (IOException e) {
			throw new PdfAsException("Invalid Status", e);
		}
	}

	private void stampPdf(OperationStatus status) throws PdfAsException,
			IOException {

		RequestedSignature requestedSignature = status.getRequestedSignature();
		String signatureProfileID = requestedSignature.getSignatureProfileID();
		SignatureProfileConfiguration signatureProfileConfiguration = status
				.getSignatureProfileConfiguration(signatureProfileID);

		if (requestedSignature.isVisual()) {
			logger.info("Creating visual siganture block");
			// ================================================================
			// SignBlockCreationStage (visual) -> create visual signature
			// block (logicaly)
			SignatureProfileSettings signatureProfileSettings = TableFactory
					.createProfile(signatureProfileID, settings);

			Table main = TableFactory.createSigTable(signatureProfileSettings,
					MAIN, settings, requestedSignature);

			IPDFStamper stamper = StamperFactory.createDefaultStamper(settings);
			IPDFVisualObject visualObject = stamper.createVisualPDFObject(
					status.getPdfObject(), main);

			// ================================================================
			// PositioningStage (visual) -> find position or use fixed
			// position

			String posString = status.getSignParamter().getSignaturePosition();

			if (posString == null) {
				posString = signatureProfileConfiguration
						.getDefaultPositioning();
			}

			TablePos tablePos = null;

			if (posString == null) {
				tablePos = new TablePos();
			} else {
				tablePos = new TablePos(posString);
			}

			PDDocument originalDocument = PDDocument
					.load(new ByteArrayInputStream(status.getPdfObject()
							.getOriginalDocument()));

			PositioningInstruction positioningInstruction = Positioning
					.determineTablePositioning(tablePos, "", originalDocument,
							visualObject);

			// ================================================================
			// StampingStage (visual) -> stamp logical signature block to
			// location (itext)

			byte[] incrementalUpdate = stamper.writeVisualObject(visualObject,
					positioningInstruction, status.getPdfObject()
							.getOriginalDocument());

			SignaturePositionImpl position = new SignaturePositionImpl();
			position.setX(positioningInstruction.getX());
			position.setY(positioningInstruction.getY());
			position.setPage(positioningInstruction.getPage());
			position.setHeight(visualObject.getHeight());
			position.setWidth(visualObject.getWidth());

			requestedSignature.setSignaturePosition(position);

			status.getPdfObject().setStampedDocument(incrementalUpdate);
		} else {
			logger.info("No visual siganture block");
			// Stamped Object is equal to original
			status.getPdfObject().setStampedDocument(
					status.getPdfObject().getOriginalDocument());
		}
	}

	private SignResult createSignResult(OperationStatus status)
			throws IOException {
		// ================================================================
		// Create SignResult
		SignResultImpl result = new SignResultImpl(status.getSignParamter()
				.getOutput());
		OutputStream outputStream = result.getOutputDocument()
				.createOutputStream();

		outputStream.write(status.getPdfObject().getSignedDocument());

		outputStream.close();

		// result.setSignaturePosition(status.getRequestedSignature().);

		result.setSignerCertificate(status.getRequestedSignature()
				.getCertificate());
		result.setSignaturePosition(status.getRequestedSignature()
				.getSignaturePosition());

		return result;
	}

}
