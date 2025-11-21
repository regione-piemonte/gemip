package it.csi.gemip.mipbackofficebff.utils;

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


import java.util.Date;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.gemip.mipbackofficebff.entities.CsiLogAudit;
import it.csi.gemip.mipbackofficebff.entities.CsiLogAuditId;
import it.csi.gemip.mipbackofficebff.repositories.CsiLogAuditRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;


@ApplicationScoped
public class LogAuditUtil {
    @Inject
    CsiLogAuditRepository csiLogAuditRepository;
    @Inject
    Logger logger;

    // QUESTO FILE E' UNA SEMPLIFICAZIONE DI QUELLO DEL mipcommonapi

    public  void insertLogAudit(Date date, String user, String idAdr, String operazione, Object object, HttpServletRequest httpRequest){
        final int limit = 4000;

        String X_Current_Applicativo = "mipBackofficebff";
        String X_Current_User = user;
        String X_User_Ip_Address = idAdr;

        CsiLogAuditId csiLogAuditId = new CsiLogAuditId();
        csiLogAuditId.setDataOra(date);
        csiLogAuditId.setIdApp("GEMIP_rp01_PROD_" + X_Current_Applicativo);
        csiLogAuditId.setUtente(X_Current_User);
        csiLogAuditId.setOperazione(operazione);

        CsiLogAudit csiLogAudit = new CsiLogAudit();
        csiLogAudit.setId(csiLogAuditId);
        csiLogAudit.setIpAddress(X_User_Ip_Address);
        csiLogAudit.setOggOper(generateCleanJson(object, limit));
        csiLogAudit.setKeyOper(httpRequest.getMethod() + " " + httpRequest.getRequestURI());

        csiLogAuditRepository.persist(csiLogAudit);
    }

    private String generateCleanJson(Object object , int limit) {
        String oggOper;
        try {
            if (object instanceof String) {
                oggOper = (String) object; 
            } else {
                oggOper = new ObjectMapper().writeValueAsString(object);
            }
            oggOper = oggOper.replaceAll(",\"codiceCondizioneOccupazionale\":(?:\\{[^{}]*\\}|null)", "");
            oggOper = oggOper.replaceAll(",\"svantaggioAbitativo\":(?:\\{[^{}]*\\}|null)", "");
            oggOper = oggOper.replaceAll(",\"condizioneOccupazionaleAltro\":(?:\"[^\"]*\"|null)", "");
            oggOper = oggOper.replaceAll(",\"condizioneFamiliare\":(?:\\{[^{}]*\\}|null)", "");
            oggOper = oggOper.replaceAll(",\"codUserAggiorn\":\"[^\"]*\"", "");
            oggOper = oggOper.replaceAll(",\"codUserInserim\":\"[^\"]*\"", "");
            oggOper = oggOper.replaceAll(",\"codFiscaleUtente\":\"[^\"]*\"", "");
            if (oggOper.length() > limit) {
                oggOper = oggOper.substring(0, limit);
            }
        } catch (Exception e) {
            oggOper = "Errore generazione del oggOper";
        }
        return oggOper;
    }
}
