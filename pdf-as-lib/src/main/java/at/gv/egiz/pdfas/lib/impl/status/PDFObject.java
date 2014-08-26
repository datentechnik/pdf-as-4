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
package at.gv.egiz.pdfas.lib.impl.status;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PDFObject {
	
	private OperationStatus status;
	
	private PDDocument doc;
	private byte[] originalDocument;
	private byte[] signedDocument;

	public PDFObject(OperationStatus operationStatus) {
		this.status = operationStatus;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if(doc != null) {
			doc.close();
		}
	}

	public void close() {
		if(doc != null) {
			try {
				doc.close();
				//System.gc();
			} catch(Throwable e) {
				// ignore!
			}
			doc = null;
		}
	}
	
	public byte[] getOriginalDocument() {
		return originalDocument;
	}

	public void setOriginalDocument(byte[] originalDocument) throws IOException {
		this.originalDocument = originalDocument;
		if(doc != null) {
			doc.close();
		}
		this.doc = PDDocument.load(new ByteArrayInputStream(this.originalDocument));
		if(this.doc != null) {
			this.doc.getDocument().setWarnMissingClose(false);
		}
	}

	public PDDocument getDocument() {
		return this.doc;
	}
	
	public byte[] getSignedDocument() {
		return signedDocument;
	}

	public void setSignedDocument(byte[] signedDocument) {
		this.signedDocument = signedDocument;
	}

	public OperationStatus getStatus() {
		return status;
	}

	public void setStatus(OperationStatus status) {
		this.status = status;
	}
}
