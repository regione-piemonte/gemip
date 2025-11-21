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

import static it.csi.gemip.mipbackofficebff.filter.IrideIdAdapterFilter.USERINFO_SESSIONATTR;

import it.csi.gemip.mipbackofficebff.bff.IncontriPreaccoglienzaApi;
import it.csi.gemip.mipbackofficebff.bff.dto.CittadinoIncontroPreaccoglienza;
import it.csi.gemip.mipbackofficebff.bff.dto.IncontroPreAccoglienzaInsert;
import it.csi.gemip.mipbackofficebff.bff.dto.IncontroPreaccoglienzaAreaTerritoriale;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class IncontriPreaccoglienzaApiImpl extends ParentApiImpl implements IncontriPreaccoglienzaApi {
    @Override
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL"})
    public Response deleteIncontroPreaccoglienzaById(Long idIncontroPreaccoglienza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateDelete(uriInfo,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response getCittadinoIncontroPreaccoglienza(Long id, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getIncontriPreaccoglienzaByAreaTerritoriale(String codAreaTerritoriale, Boolean completo, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getIncontriPreaccoglienzaByParameters(String codAreaTerritoriale, String dataIncontroDa,
                                                          String dataIncontroA, Long luogo, String idOperatore,
                                                          String idSoggettoAttuatore, Integer pageIndex,
                                                          Integer pageSize, String orderBy, String sortDirection,
                                                          SecurityContext securityContext, HttpHeaders httpHeaders,
                                                          HttpServletRequest httpRequest)
    {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getIncontroPreaccoglienza(Long id, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getIncontroPreaccoglienzaPerCittadino(Long idCittadino, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getIncontroPreaccoglienzaPerIdeaImpresa(Long idIdeaImpresa, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL"})
    public Response insertCittadinoIncontroPreaccoglienza(CittadinoIncontroPreaccoglienza body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePost(uriInfo,body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL"})
    public Response insertIncontroPreaccoglienza(IncontroPreAccoglienzaInsert body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePost(uriInfo, body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }


    @Override
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL"})
    public Response insertRelazioneAreaTerritorialeIncontro(IncontroPreaccoglienzaAreaTerritoriale body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePost(uriInfo,body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL", "GEMIP_ATTUATORE_BASE"})
    public Response updateCittadinoIncontroPreaccoglienza(CittadinoIncontroPreaccoglienza body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePut(uriInfo,body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL"})
    public Response updateIncontroPreaccoglienza(IncontroPreAccoglienzaInsert body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePut(uriInfo,body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response inviaComunicatoPartecipanti(Long idIncontroPreaccoglienza, String oggetto, String corpo,
                                                Long idCittadino, SecurityContext securityContext,
                                                HttpHeaders httpHeaders, HttpServletRequest httpRequest)
    {
        return restUtils.chiamatePost(uriInfo,null,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response getIncontriPreaccoglienzaForControlloEsistente(Long idLuogo, String dataIncontro,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
                return restUtils.chiamateGet(uriInfo);
    }

}
