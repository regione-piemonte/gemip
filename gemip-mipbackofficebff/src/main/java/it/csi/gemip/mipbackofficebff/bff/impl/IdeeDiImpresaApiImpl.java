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


import it.csi.gemip.mipbackofficebff.bff.IdeeDiImpresaApi;
import it.csi.gemip.mipbackofficebff.bff.dto.IdeaDiImpresa;
import it.csi.gemip.mipbackofficebff.bff.dto.ResgistraPresenze;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

import static it.csi.gemip.mipbackofficebff.filter.IrideIdAdapterFilter.USERINFO_SESSIONATTR;

public class IdeeDiImpresaApiImpl extends ParentApiImpl implements IdeeDiImpresaApi {
    @Override
    public Response getFontiConoscenzaMip(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getIdeaDiImpresaById(Long id, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getIdeaDiImpresaByIdCittadino(Long idCittadino, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getIdeaDiImpresaByIdIncontroPreacc(Long idIncontroPreaccoglienza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getIdeaDiImpresaPerParametri(Integer pageIndex, Integer pageSize, String codAreaTerritoriale, String dataInseritaDa, String dataInseritaA, String ideaDiImpresa, String cittadinoNome, String cittadinoCognome, Long idStatoIdea, String statoPresenze, Long idSoggettoAttuatore, Long idOperatore, String orderBy, String sortDirection, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }



    @Override
    public Response getStatiIdeaDiImpresa(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response insertIdeaDiImpresa(Long idCittadino, IdeaDiImpresa body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePost(uriInfo,body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }


    @Override
    public Response registraPresenzeIncontro(List<ResgistraPresenze> body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePut(uriInfo,body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response updateIdeaDiImpresa(IdeaDiImpresa body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePut(uriInfo,body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }
}
