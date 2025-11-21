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

import io.quarkus.runtime.configuration.ConfigUtils;
import it.csi.gemip.mipcittadinobff.bff.dto.UserInfo;
import it.csi.gemip.mipcittadinobff.integration.iride.Identita;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.io.IOException;


/**
 * Inserisce in sessione:
 * <ul>
 *  <li>l'identit&agrave; digitale relativa all'utente autenticato.
 *  <li>l'oggetto <code>currentUser</code>
 * </ul>
 * Funge da adapter tra il filter del metodo di autenticaizone previsto e la
 * logica applicativa.
 *
 * @author CSIPiemonte
 */
@Provider
public class IrideIdAdapterFilter implements ContainerRequestFilter {

    public static final String COMPONENT_NAME = "mipcittadinobff";

    public static final String IRIDE_ID_SESSIONATTR = "iride2_id";

    public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";

    public static final String USERINFO_SESSIONATTR = "currentUser";

    @Inject
    Logger LOG;

    @Context
    private UriInfo uriInfo;

    @Context
    HttpServletRequest hreq;

    @ConfigProperty(name="shibboletDisable",defaultValue = "true")
    Instance<Boolean> shibboletDisable;

    private boolean mustCheckPage(String requestURI) {
        return !shibboletDisable.get();
    }

    
    public String getToken(ContainerRequestContext httpreq) {
        String marker = httpreq.getHeaderString(AUTH_ID_MARKER);
        LOG.debug("header Shib-Iride-IdentitaDigitale"+marker);
        LOG.debug("headers presenti" + httpreq.getHeaders());
        try {
            // gestione dell'encoding
            String decodedMarker = new String(marker.getBytes("ISO-8859-1"), "UTF-8");
            return decodedMarker;
        } 
        catch (java.io.UnsupportedEncodingException e) {
            // se la decodifica non funziona comunque sempre meglio restituire
            // il marker originale non decodificato
            return marker;
        }
        catch (NullPointerException npe){
            return marker;
        } 
    }


    private String normalizeToken(String token) {
        return token;
    }

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {
        LOG.debug("[IrideIdAdapterFilterDaSerse::filter] START");
            String marker = getToken(requestContext);
            if (marker != null) {
                try {
                    Identita identita = new Identita(
                            normalizeToken(marker));
                    LOG.debug("[IrideIdAdapterFilterDaSerse::doFilter] Inserito in sessione marcatore IRIDE:" + identita);
                    requestContext.setProperty(IRIDE_ID_SESSIONATTR, identita);
                    UserInfo userInfo = new UserInfo();
                    userInfo.setNome(identita.getNome());
                    userInfo.setCognome(identita.getCognome());
                    userInfo.setEnte("--");
                    userInfo.setRuolo("--");
                    userInfo.setCodFisc(identita.getCodFiscale());
                    requestContext.setProperty(USERINFO_SESSIONATTR, userInfo);
                    hreq.setAttribute(USERINFO_SESSIONATTR, userInfo);
                    LOG.debug("[IrideIdAdapterFilterDaSerse::filter] END");
                } catch (Exception e) {
                    LOG.error("[IrideIdAdapterFilterDaSerse::doFilter] " + e.toString(), e);
                    throw new IOException("errore nella parsificazione dell'header di autenticazione", e);
                }
            } else if ("dev".equals(ConfigUtils.getProfiles().get(0))
                    || "int".equals(ConfigUtils.getProfiles().get(0))
            ) {
                // mock data
                LOG.debug("[IrideIdAdapterFilterDaSerse::doFilter] Inserito in sessione marcatore IRIDE:" );
                UserInfo userInfo = new UserInfo();
                userInfo.setNome("Paperino");
                userInfo.setCognome("Paolino");
                userInfo.setEnte("--");
                userInfo.setRuolo("--");
                userInfo.setCodFisc("PLNPRN00D01L219M");
                requestContext.setProperty(USERINFO_SESSIONATTR, userInfo);
                hreq.setAttribute(USERINFO_SESSIONATTR, userInfo);
                LOG.debug("[IrideIdAdapterFilterDaSerse::filter] END");
            } else {
                // il marcatore deve sempre essere presente altrimenti e' una
                // condizione di errore (escluse le pagine home e di servizio)
                if (mustCheckPage(requestContext.getUriInfo().getPath())) {
                    LOG.error(
                            "[IrideIdAdapterFilterDaSerse::doFilter] Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
                    throw new IOException(
                            "Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
                }
            }
    }
}