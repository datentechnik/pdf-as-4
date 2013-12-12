package at.gv.egiz.pdfas.wrapper;

import java.security.cert.X509Certificate;
import java.util.List;

import at.gv.egiz.pdfas.api.io.DataSink;
import at.gv.egiz.pdfas.api.sign.SignResult;
import at.gv.egiz.pdfas.api.sign.pos.SignaturePosition;

public class SignResultImpl implements SignResult {

	private DataSink sink;
	private X509Certificate certificate;
	
	public SignResultImpl(DataSink data, X509Certificate cert) {
		this.certificate = cert;
		this.sink = data; 
	}
	
	public DataSink getOutputDocument() {
		return this.sink;
	}

	public X509Certificate getSignerCertificate() {
		return certificate;
	}

	public SignaturePosition getSignaturePosition() {
		return null;
	}

	public List getNonTextualObjects() {
		return null;
	}

	public boolean hasNonTextualObjects() {
		return false;
	}

}
