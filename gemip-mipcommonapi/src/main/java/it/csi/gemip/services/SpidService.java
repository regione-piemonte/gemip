package it.csi.gemip.services;

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

import org.jboss.logging.Logger;

import it.csi.gemip.mipcommonapi.integration.entities.MipTAnagraficaCittadino;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;

@ApplicationScoped
public class SpidService {

    public static final String HEADER_EMAIL = "email";
    public static final String HEADER_DATE_BIRTH = "dateOfBirth";
    public static final String HEADER_PLACE_BIRTH = "placeOfBirth";
    public static final String HEADER_COUNTY_BIRTH = "countyOfBirth"; // provincia di nascita
    public static final String HEADER_DIGITAL_ADDR = "digitalAddress";
    public static final String HEADER_ID_CARD = "idCard";
    public static final String HEADER_MOBILE_PHONE = "mobilePhone";
    public static final String GENDER = "gender";

    @Inject
    Logger logger;

    /**
     * Aggiunge le informazioni che (dovebbero) arrivare dallo SPID
     * 
     * @param mipTCittadino
     */
    public boolean addInfoSpid(MipTAnagraficaCittadino anagrafica, HttpHeaders httpHeaders) {
        boolean ciSonoDatiDaSpid = false;
        String email = httpHeaders.getHeaderString(HEADER_EMAIL);
        if (email != null && ! email.isEmpty()) {
            anagrafica.setRecapitoEmail(email);
            ciSonoDatiDaSpid = true;
            logger.info("Ho trovato email da SPID");
        }
        // TODO settare eventuali altri campi
        return ciSonoDatiDaSpid;
    }

    public MipTAnagraficaCittadino creaAnagraficaDaSpid(HttpHeaders httpHeaders) {
        MipTAnagraficaCittadino anagrafica = new MipTAnagraficaCittadino();
        boolean ciSonoDatiDaSpid = addInfoSpid(anagrafica, httpHeaders);
        if (!ciSonoDatiDaSpid) return null;
        return anagrafica;
    }
}
