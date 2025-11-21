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

import it.csi.gemip.mipbackofficebff.bff.CittadiniApi;
import it.csi.gemip.mipbackofficebff.bff.dto.Cittadino;
import it.csi.gemip.mipbackofficebff.bff.dto.DashboardCittadino;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import static it.csi.gemip.mipbackofficebff.filter.IrideIdAdapterFilter.USERINFO_SESSIONATTR;


@Provider
public class CittadiniApiImpl extends ParentApiImpl implements CittadiniApi {


    @Override
    public Response getCittadinoPerIdeaImpresa(Long idIdeaDiImpresa, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getCittadinoPerParametri(String nome, String cognome, String email, String codiceFiscale, String codAreaTerritoriale, String dataInseritoDa, String dataInseritoA, Long idOperatore, Long idSoggettoAttuatore, Integer pageIndex, Integer pageSize, String orderBy, String sortDirection, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }



    @Override
    public Response getDashboardCittadino(Cittadino body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        return restUtils.chiamatePost(uriInfo,body);
    }

    @Override
    public Response updateIdeaImpresaInviata(DashboardCittadino body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePut(uriInfo,body,(UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

}
