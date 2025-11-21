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

import it.csi.gemip.mipbackofficebff.bff.ReportisticaApi;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.Date;

import static it.csi.gemip.mipbackofficebff.filter.IrideIdAdapterFilter.USERINFO_SESSIONATTR;

public class ReportisticaApiImpl extends ParentApiImpl implements ReportisticaApi {

    @Override
    public Response exportExcelIdeeImpresa(String codOperatore, Date dataDa, Date dataA, Long idSoggettoAttuatore,
                                           String idCodAreaTerritoriale, Long idStatoIdea, String tipoReport,
                                           SecurityContext securityContext, HttpHeaders httpHeaders,
                                           HttpServletRequest httpRequest)
    {
        return restUtils.chiamateGet(uriInfo, (UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response exportExcelQuestionari(Long idFase, Date dataDa, Date dataA, Long idSoggettoAttuatore, String idCodAreaTerritoriale, String idOperatore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        return restUtils.chiamateGet(uriInfo, (UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response getDomandeRisposte(Long idFase, Date dataDa, Date dataA, Long idSoggettoAttuatore, String idCodAreaTerritoriale, Integer pageIndex, Integer pageSize, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
       return restUtils.chiamateGet(uriInfo, (UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response getIdeeImpresa(Date dataDa, Date dataA, Long idSoggettoAttuatore, String idCodAreaTerritoriale,
                                   Long idStatoIdea, String tipoReport,Integer pageIndex,Integer pageSize, SecurityContext securityContext,
                                   HttpHeaders httpHeaders, HttpServletRequest httpRequest)
    {
        return restUtils.chiamateGet(uriInfo, (UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }

}
