package it.csi.gemip.mipbackofficebff.integration;

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

import static it.csi.gemip.mipbackofficebff.filter.IrideIdAdapterFilter.IRIDE_ID_SESSIONATTR;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor.Application;
import it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor.Identita;
import it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor.OrchflaiFlaidoorsrv;
import it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor.Ruolo;
import it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor.UseCase;
import it.csi.gemip.mipbackofficebff.bff.dto.InfoOperatore;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import it.csi.gemip.mipbackofficebff.exception.ErroreConnessioneServizioException;
import it.csi.gemip.mipbackofficebff.utils.Convert;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public class SecurityHelper {

    @ConfigProperty(name = "securityHelper.url")
    private Instance<String> securityHelperUrl;

    private OrchflaiFlaidoorsrv orcflai;
    public static final Application GEMIP_BACKOFFICE_APPLICATION = new Application();
    
    static {
    	GEMIP_BACKOFFICE_APPLICATION.setId("MIPBACKOFFICE");
    }

    /**
     * @return the orcflai
     */
    protected OrchflaiFlaidoorsrv getOrcflai() {
        /*
         * Dato che la creazione dell'oggetto per accedere ai servizi è onerosa in
         * termini di tempo, lo creo solo la prima volta
         */
        if (orcflai != null) {
            return orcflai;
        }
        try {
        	
        	JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	    	factory.setAddress(securityHelperUrl.get());
	    	factory.setServiceClass(OrchflaiFlaidoorsrv.class);
            orcflai = (OrchflaiFlaidoorsrv) factory.create();
        } catch (Exception e) {

            throw new ErroreConnessioneServizioException(501, e.getMessage());

        }

        return orcflai;
    }

    public String getInfoPersonaInUseCase(HttpServletRequest hreq, UseCase usecase) throws Exception {

        Identita identita = (Identita) hreq.getAttribute(IRIDE_ID_SESSIONATTR);

        return getOrcflai().getInfoPersonaInUseCase(identita, usecase);
    }

    public List<InfoOperatore> getInfoOperatoreForRuolo(SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<Ruolo> ruoli = findRuoliForPersonaInApplication(httpRequest);

        Ruolo selected = ruoli != null && !ruoli.isEmpty() ? ruoli.get(0) : null ;
        String infoAggiuntiveXml = "";

        if (selected != null) {
            try {

            	UseCase useCase=new UseCase();
				useCase.setId(selected.getMnemonico());
                infoAggiuntiveXml = getInfoPersonaInUseCase(httpRequest,useCase);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        List<InfoOperatore> infoOperatoreList = Convert.convertFromXml(infoAggiuntiveXml);

        return infoOperatoreList;
    }

    public boolean verifyCurrentUserForRole(HttpServletRequest hreq, String roleCode, String domainCode)
            throws Exception {
        Ruolo rol = new Ruolo();

        rol.setCodiceRuolo(roleCode);
        rol.setCodiceDominio(domainCode);

        return getOrcflai().isPersonaInRuolo(getCurrentUser(hreq), rol);
    }

    public Identita getCurrentUser(HttpServletRequest hreq) throws IllegalStateException {
        Identita id = (Identita) hreq.getAttribute(IRIDE_ID_SESSIONATTR);
        if (id == null) {
            throw new IllegalStateException(
                    "Errore nel reperimento del current user dalla sessione: attributo non trovato");
        } else {
            return id;
        }
    }

    public UserInfo getCurrentUserInfo(HttpServletRequest hreq) throws IllegalStateException {

        Identita identita = (Identita) hreq.getAttribute(IRIDE_ID_SESSIONATTR);

        UserInfo userInfo = new UserInfo();
        userInfo.setCodFisc(identita.getCodFiscale());
        userInfo.setNome(identita.getNome());
        userInfo.setCognome(identita.getCognome());

        if (userInfo.getCodFisc() == null) {
            throw new IllegalStateException(
                    "Errore nel reperimento del current user dalla sessione: attributo non trovato");
        } else {
            return userInfo;
        }
    }

    public List<Ruolo> findRuoliForPersonaInApplication(HttpServletRequest hreq) {

        try {
            Identita identita = (Identita) hreq.getAttribute(IRIDE_ID_SESSIONATTR);

            List<Ruolo> ruoliFromOrchflai = getOrcflai().findRuoliForPersonaInApplication(identita,
                    GEMIP_BACKOFFICE_APPLICATION);
            List<Ruolo> ruoloReturnList = new ArrayList<>();
            if (ruoliFromOrchflai != null && ruoliFromOrchflai.size() > 0) {
            	for (Ruolo ruoloFromOrchflai : ruoliFromOrchflai){

                    Ruolo ruolo = new Ruolo();

                    ruolo.setCodiceRuolo(ruoloFromOrchflai.getCodiceRuolo());
                    ruolo.setCodiceDominio(ruoloFromOrchflai.getCodiceDominio());
                    ruolo.setMnemonico(ruoloFromOrchflai.getMnemonico());

                    ruoloReturnList.add(ruolo);

                }
                return ruoloReturnList;
            } else
                return ruoloReturnList;
        } catch (Exception e) {
            throw new ErroreConnessioneServizioException(501, e.getMessage());
            // throw new InternalErrorException(e) ;
        }
    }
}
