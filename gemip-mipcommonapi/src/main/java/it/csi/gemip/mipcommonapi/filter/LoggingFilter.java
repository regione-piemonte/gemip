package it.csi.gemip.mipcommonapi.filter;

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

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Inject
    Logger logger;

    long start; // this object is request-scoped, so we can store attributes

    @ConfigProperty(name = "log.headers", defaultValue = "false")
    Instance<Boolean> logHeaders;
 
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final String method = requestContext.getMethod();
        final String uri = requestContext.getUriInfo().getRequestUri().toString(); // include parametri query

        start = System.currentTimeMillis();

        String message = "BEGIN Request " + method + " " + uri;
        if (logHeaders.get()) {
            message += printHeaders(requestContext.getHeaders());
        }
        logger.info(message);
    }

    private String printHeaders(MultivaluedMap<String, String> headers) {
        StringBuffer sb = new StringBuffer(" HEADERS:\r\n");
        for (Entry<String, List<String>> entry: headers.entrySet()) {
            sb.append("\t").append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
        }
        return sb.toString();
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        final String method = requestContext.getMethod();
        final String uri = requestContext.getUriInfo().getRequestUri().toString(); // include parametri query
        final int status = responseContext.getStatus();
        final long elapsed = System.currentTimeMillis() - start;

        logger.info("END Request " + method + " " + uri + " HTTP status code " + status +  " time elapsed: " + elapsed + "ms");
    }
}