package it.csi.gemip.utils;

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
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.gemip.mipcommonapi.integration.entities.CsiLogAudit;
import it.csi.gemip.mipcommonapi.integration.entities.CsiLogAuditId;
import it.csi.gemip.mipcommonapi.integration.repositories.CsiLogAuditRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;

@ApplicationScoped
public class LogAuditUtil {
    @Inject
    CsiLogAuditRepository csiLogAuditRepository;

    public void insertLogAudit(Date date, HttpHeaders httpHeaders, String operazione, Object object, Object body,
            HttpServletRequest httpRequest) {
        final int limit = 4000;

        String X_Current_Applicativo = httpHeaders != null
                ? getHeaderValue(httpHeaders, "X_Current_Applicativo", "app è nullo")
                : "mipCommonApi";
        String X_Current_User = httpHeaders != null ? getHeaderValue(httpHeaders, "X_Current_User", "utente nullo")
                : "Scheduler";
        String X_User_Ip_Address = httpHeaders != null ? httpHeaders.getHeaderString("X_User_Ip_Address") : "Scheduler";

        CsiLogAuditId csiLogAuditId = new CsiLogAuditId();
        csiLogAuditId.setDataOra(date);
        csiLogAuditId.setIdApp("GEMIP_rp01_PROD_" + X_Current_Applicativo);
        csiLogAuditId.setUtente(X_Current_User);
        csiLogAuditId.setOperazione(operazione);

        CsiLogAudit csiLogAudit = new CsiLogAudit();
        String params = extractParams(body, httpRequest, limit);

        csiLogAudit.setResourceOper(generateCleanJson(params, limit).replace("\\", ""));
        csiLogAudit.setId(csiLogAuditId);
        csiLogAudit.setIpAddress(X_User_Ip_Address);
        csiLogAudit.setOggOper(generateCleanJson(object, limit));
        if (httpRequest != null)
            csiLogAudit.setKeyOper(httpRequest.getMethod() + " " + httpRequest.getRequestURI());
        else
            csiLogAudit.setKeyOper("Scheduler");
        csiLogAuditRepository.persist(csiLogAudit);
    }

    private String extractParams(Object body, HttpServletRequest httpRequest, int limit) {
        StringBuilder params = new StringBuilder();
        if (body != null) {
            params.append(generateCleanJson(body, limit));
        }
        Map<String, String[]> parameterMap = null;
        if (httpRequest != null)
            parameterMap = httpRequest.getParameterMap();
        if (httpRequest != null && parameterMap != null && !parameterMap.isEmpty()) {
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                String paramName = entry.getKey();
                String[] paramValues = entry.getValue();
                if (paramValues != null) {
                    for (String paramValue : paramValues) {
                        params.append(" " + paramName + " : " + generateCleanJson(paramValue, limit) + " ,");
                    }
                }
            }
        }
        if (body == null && httpRequest != null && parameterMap == null)
            params.append("Non sono presenti parametri");
        return params.toString();
    }

    private String generateCleanJson(Object object, int limit) {
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

    private String getHeaderValue(HttpHeaders headers, String headerName, String defaultValue) {
        String headerValue = headers.getHeaderString(headerName);
        return (headerValue == null) ? defaultValue : headerValue;
    }

}
