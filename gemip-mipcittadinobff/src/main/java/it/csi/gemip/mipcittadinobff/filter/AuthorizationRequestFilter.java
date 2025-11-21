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

import jakarta.enterprise.inject.Instance;
import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Base64;

@Provider
public class AuthorizationRequestFilter implements ClientRequestFilter {

    @ConfigProperty(name="mipcommonapi.user")
    Instance<String> user;

    @ConfigProperty(name = "mipcommonapi.password")
    Instance<String> passw;

    @Override
    public void filter(ClientRequestContext requestContext) {
        requestContext.getHeaders().add("Authorization", lookupAuth());
    }

    private String lookupAuth() {
        return "Basic " + Base64.getEncoder().encodeToString((user.get() + ":" + passw.get()).getBytes());
    }

}
