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


import org.apache.commons.lang3.StringUtils;

import it.csi.gemip.mipcittadinobff.utils.RestUtils;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ParentApiImpl {

    @Inject
    RestUtils restUtils;

    @Context
    UriInfo uriInfo;

    public String getIncomingIPAddress(HttpServletRequest req){

        StringBuilder sb = new StringBuilder("");
        if (req != null) {
            String xForwardedFor = req.getHeader("X-Forwarded-For");
            xForwardedFor = xForwardedFor != null && xForwardedFor.contains(",") ? xForwardedFor.split(",")[0] : xForwardedFor;
            String remoteHost = req.getRemoteHost();
            String remoteAddr = req.getRemoteAddr();
            int remotePort = req.getRemotePort();
            if (StringUtils.isNotBlank(remoteHost) && !remoteHost.equalsIgnoreCase(remoteAddr)) {
                sb.append(remoteHost).append(" ");
            }
            if (StringUtils.isNotBlank(xForwardedFor)) {
                sb.append(xForwardedFor).append("(fwd)=>");
            }
            if (remoteAddr != null) {
                sb.append(remoteAddr).append(":").append(remotePort);
            }
        } else {
            sb.append("null");
        }
        return sb.toString();
    }
}
