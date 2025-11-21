package it.csi.gemip.mipcittadinobff.bff.impl;

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

import it.csi.gemip.mipcittadinobff.bff.AnagraficheApi;
import it.csi.gemip.mipcittadinobff.bff.dto.AnagraficaCittadino;
import it.csi.gemip.mipcittadinobff.bff.dto.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import static it.csi.gemip.mipcittadinobff.filter.IrideIdAdapterFilter.USERINFO_SESSIONATTR;


@Provider
public class AnagraficheApiImpl extends  ParentApiImpl implements AnagraficheApi {
    @Override
    public Response getAnafraficaCittadinoPerId(Long idAnagraficaCittadino, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo, (UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR), getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response getCondizioneOccupazionale(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getCondizioniFamiliare(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getSvantaggiAbitativo(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getTitoliStudio(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response insertAnagraficaCittadino(AnagraficaCittadino body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePost(uriInfo, body, (UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR), getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response updateAnagraficaCittadino(AnagraficaCittadino body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePut(uriInfo,body, (UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR), getIncomingIPAddress(httpRequest));
    }

    @Override
    public Response getAnagraficaCittadinoSpid(SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo, httpHeaders);
    }
}
