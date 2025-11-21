package it.csi.gemip.mipbackofficebff.filter;

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

import java.util.UUID;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.ext.Provider;

import org.apache.log4j.Logger;


@Provider
public class XSRFTokenIssuerFilter implements ContainerResponseFilter {

  public static final String COMPONENT_NAME = "mipbackofficebff";

  static final Logger LOG = Logger.getLogger(COMPONENT_NAME + ".security");

  /*
   * nome del cookie XSRF
   */
  private static final String XSRF_COOKIE_NAME = "XSRF-TOKEN";

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
    LOG.debug("[XSRFTokenIssuerFilter::filter] START");

    // Check if cookie already exists
    if (requestContext.getCookies().containsKey(XSRF_COOKIE_NAME)) {
      LOG.debug("[XSRFTokenIssuerFilter::filter] no need to create a new token");
      LOG.debug("[XSRFTokenIssuerFilter::filter] END");
      return;
    }

    // Issue a new token
    LOG.debug("[XSRFTokenIssuerFilter::filter] creating a new random token");
    String randomToken = UUID.randomUUID().toString();
    var tokenCookie = new NewCookie(XSRF_COOKIE_NAME, randomToken, "/", null, null, -1, true, false);
    responseContext.getHeaders().add("Set-Cookie", tokenCookie);
    LOG.debug("[XSRFTokenIssuerFilter::filter] END");
  }
}