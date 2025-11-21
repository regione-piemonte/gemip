package it.csi.gemip.mipcittadinobff.exception;

/*-
 * Copyright © 2025 Regione Piemonte
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://interoperable-europe.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 * or
 * https://opensource.org/license/EUPL-1.2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

public class ErroreGestitoException extends RuntimeException {

	private final String codice;

	public String getCodice() {
		return codice;
	}

	public ErroreGestitoException(String message) {
		super(message);
		codice = null;
	}

	public ErroreGestitoException(String message, String codice) {
		super(message);
		this.codice = codice;
	}

	public ErroreGestitoException(Throwable cause) {
		super(cause);
		codice = null;
	}

	public ErroreGestitoException(String message, Throwable cause) {
		super(message, cause);
		codice = null;
	}

	public ErroreGestitoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		codice = null;
	}

}

