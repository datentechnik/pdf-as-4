/*******************************************************************************
 * <copyright> Copyright 2017 by PrimeSign GmbH, Graz, Austria </copyright>
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
package at.gv.egiz.pdfas.lib.pki.impl;

/**
 * Exception indicating problems while performing query on OCSP responder.
 * 
 * @author Thomas Knall, PrimeSign GmbH
 *
 */
public class OCSPClientException extends Exception {

	private static final long serialVersionUID = 1L;

	public OCSPClientException() {
		super();
	}

	public OCSPClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public OCSPClientException(String message) {
		super(message);
	}

	public OCSPClientException(Throwable cause) {
		super(cause);
	}

}
