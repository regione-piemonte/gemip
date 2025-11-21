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

import it.csi.gemip.mipbackofficebff.bff.ExcelApi;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class ExcelApiImpl extends ParentApiImpl implements ExcelApi {
    @Override
    public Response exportExcelAnagraficaCittadino(Long idIncontroPreaccoglienza, String codOperatore,String idSoggettoAttuatore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }
    @Override
    public Response getExportIncontriPreaccoglienzaAnagrafica(String idIncontriPreaccoglienza,
                                                              String codOperatore, String idSoggettoAttuatore,
                                                              SecurityContext securityContext, HttpHeaders httpHeaders,
                                                              HttpServletRequest httpRequest)
    {
        return restUtils.chiamateGet(uriInfo, (UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR),getIncomingIPAddress(httpRequest));
    }
}
