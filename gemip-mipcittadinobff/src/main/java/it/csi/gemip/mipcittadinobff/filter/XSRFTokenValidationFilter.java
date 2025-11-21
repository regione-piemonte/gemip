package it.csi.gemip.mipcittadinobff.filter;

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

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.inject.Instance;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import org.eclipse.microprofile.config.inject.ConfigProperty;


@Provider
public class XSRFTokenValidationFilter implements ContainerRequestFilter {

    public static final String COMPONENT_NAME = "mipcittadinobff";

    static final List<String> SECURE_HTTP_METHODS = new ArrayList<>();

    /** disable validation (for use in development mode) */
    @ConfigProperty(name = "xsrf.validation.disabled", defaultValue = "true")
    Instance<Boolean> disabled;

    /**
     * nome dell'header XSRF che la componente client deve inserire ad ogni
     * richiesta rest
     */
    private static final String XSRF_HEADER_NAME = "X-XSRF-TOKEN";

    /*
     * nome del cookie XSRF
     */
    private static final String XSRF_COOKIE_NAME = "XSRF-TOKEN";

    private static final Response INVALID_CSRF_TOKEN_RESPONSE = Response.status(Response.Status.BAD_REQUEST)
            .entity("A valid CSRF token must be provided via the unambiguous header field: " + XSRF_HEADER_NAME
                    + " and cookie: " + XSRF_COOKIE_NAME)
            .build();

    static {
        SECURE_HTTP_METHODS.add("GET");
    }


    @Override
    public void filter(ContainerRequestContext requestContext) {

        if (!disabled.get()){
            // No check for "secure" HTTP methods
            if (SECURE_HTTP_METHODS.contains(requestContext.getMethod())) {
                return;
            }

            Cookie csrfTokenCookie = requestContext.getCookies().get(XSRF_COOKIE_NAME);
            List<String> csrfTokenHeader = requestContext.getHeaders().get(XSRF_HEADER_NAME);

            // Check if the CSRF token header and cookie is present,
            // the header has an unambiguous value and both values
            // must match.
            if (csrfTokenCookie == null || csrfTokenHeader == null
                    || csrfTokenHeader.size() != 1
                    || !csrfTokenHeader.get(0).equals(csrfTokenCookie.getValue())) {
                requestContext.abortWith(INVALID_CSRF_TOKEN_RESPONSE);
            }
        }
    }
}