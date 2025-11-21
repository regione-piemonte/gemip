package it.csi.gemip.mipbackofficebff.bff.impl;

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

import it.csi.gemip.mipbackofficebff.bff.OperatoriApi;
import it.csi.gemip.mipbackofficebff.bff.dto.Operatore;
import it.csi.gemip.mipbackofficebff.bff.dto.OperatoreIncontroPreaccoglienza;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import static it.csi.gemip.mipbackofficebff.filter.IrideIdAdapterFilter.USERINFO_SESSIONATTR;

public class OperatoriApiImpl extends  ParentApiImpl implements OperatoriApi {
    @Override
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL"})
    public Response deleteOperatoreIncontroPreaccoglienza(Long idOperatore, Long idIncontroPreaccoglienza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateDelete(uriInfo,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response getOperatoreById(Long idOperatore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getOperatori(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getOperatoriIncontroPreaccoglienza(Long idIncontroPreacc, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getOperatoriRicerca(String nome, String cognome, String email, String codiceFiscale,
                                        Long idOperatore, String soggetto, String abilitato, Long idSoggettoAttuatore,
                                        SecurityContext securityContext, HttpHeaders httpHeaders,
                                        HttpServletRequest httpRequest)
    {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getSoggettiAttuatoriForOperatoreById(Long idOperatore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL"})
    public Response insertOperatore(Operatore body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePost(uriInfo, body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL"})
    public Response insertOperatoreIncontroPreaccoglienza(OperatoreIncontroPreaccoglienza body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePost(uriInfo, body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL"})
    public Response updateOperaotre(Operatore body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePut(uriInfo, body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response getOperatoriSoggAffidatarioById(Long idOperatore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest){
        return restUtils.chiamateGet(uriInfo);
    }
}
