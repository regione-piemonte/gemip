package it.csi.gemip.mipbackofficebff.exception;

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

public class ErroreConnessioneServizioException extends RuntimeException{
    private int status;

    public ErroreConnessioneServizioException(int status) {
        this.status = status;
    }

    public ErroreConnessioneServizioException( int status,String message) {
        super(message);
        this.status = status;
    }

    public ErroreConnessioneServizioException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public ErroreConnessioneServizioException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }


    public int getStatus(){return this.status;}
}
