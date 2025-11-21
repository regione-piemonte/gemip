package it.csi.gemip.mipcommonapi.api.impl;

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


import javax.sql.DataSource;

import it.csi.gemip.mipcommonapi.api.StatusApi;
import it.csi.gemip.mipcommonapi.api.dto.Versione;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

@Provider
public class StatusApiServiceImpl implements StatusApi{

    @Inject
    DataSource dataSource;

    @Override
    public Response statusGet(String test, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        return Response.ok().build();
    }

    @Override
    public Response statusVersioneGet(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Versione versione = new Versione();
        versione.setVersioneDev("0.0.25");
        versione.setVersioneTest("0.0.12");
        versione.setVersioneProd("0.0.01");
        return Response.ok(versione).build();
    }


}