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

import io.quarkus.runtime.configuration.ConfigUtils;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import it.csi.gemip.mipbackofficebff.exception.MalformedIdTokenException;
import it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor.Identita;
import it.csi.gemip.mipbackofficebff.exception.UtenteNonAbilitatoException;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

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

    @ConfigProperty(name="token.dev.mode")
    Instance<String> tokenDevMode;

    @Context
    HttpServletRequest hreq;
    private boolean mustCheckPage(String requestURI) {
        return !"dev".equals(ConfigUtils.getProfiles().get(0));
    }

    
    public String getToken(ContainerRequestContext httpreq) {
        String marker = httpreq.getHeaderString(AUTH_ID_MARKER);

        LOG.info("marker:" + marker);
        LOG.info("headers:" + httpreq.getHeaders());

        if (marker == null && "dev".equals(ConfigUtils.getProfiles().get(0))) {
            return getTokenDevMode();
        }

        // 2024-03-04 LV mock shibboleth integrazione 
        if (marker == null && "int".equals(ConfigUtils.getProfiles().get(0))) {
            return getTokenDevMode();
        }

        if (marker == null && !"dev".equals(ConfigUtils.getProfiles().get(0))) {
            throw new UtenteNonAbilitatoException();
        }
        return marker;
    }

    private String getTokenDevMode() {
        return tokenDevMode.get();
    }

    private Identita getFlaidoorIdentitaFromToken(String token) throws MalformedIdTokenException {
        Identita identita=new Identita();
        int slash1Index = token.indexOf('/');
        if (slash1Index == -1)
            throw new MalformedIdTokenException(token);
        identita.setCodFiscale(token.substring(0, slash1Index));
        int slash2Index = token.indexOf('/', slash1Index + 1);
        if (slash2Index == -1)
            throw new MalformedIdTokenException(token);
        identita.setNome(token.substring(slash1Index + 1, slash2Index));
        int slash3Index = token.indexOf('/', slash2Index + 1);
        if (slash3Index == -1)
            throw new MalformedIdTokenException(token);
        identita.setCognome(token.substring(slash2Index + 1, slash3Index));
        int slash4Index = token.indexOf('/', slash3Index + 1);
        if (slash4Index == -1)
            throw new MalformedIdTokenException(token);
        identita.setIdProvider (token.substring(slash3Index + 1, slash4Index));
        int slash5Index = token.indexOf('/', slash4Index + 1);
        if (slash5Index == -1)
            throw new MalformedIdTokenException(token);
        identita.setTimestamp (token.substring(slash4Index + 1, slash5Index));
        int slash6Index = token.indexOf('/', slash5Index + 1);
        if (slash6Index == -1)
            throw new MalformedIdTokenException(token);
        identita.setLivelloAutenticazione (Integer.parseInt(token.substring(slash5Index + 1, slash6Index)));
        identita.setMac (token.substring(slash6Index + 1));

        return identita;
    }

    private String normalizeToken(String token) {
        return token;
    }

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {

        if (hreq.getSession().getAttribute(IRIDE_ID_SESSIONATTR) == null) {
            String marker = getToken(requestContext);
			
            if ("dev".equals(ConfigUtils.getProfiles().get(0))
                // 2024-03-04 LV mock shibboleth integrazione 
                || "int".equals(ConfigUtils.getProfiles().get(0))
            ) {

                Identita identita = getFlaidoorIdentitaFromToken(
                        normalizeToken(getTokenDevMode()));
                requestContext.setProperty(IRIDE_ID_SESSIONATTR, identita);
                UserInfo userInfo = new UserInfo();
                userInfo.setNome(identita.getNome());
                userInfo.setCognome(identita.getCognome());
                userInfo.setEnte("--");
                userInfo.setRuolo("--");
                userInfo.setCodFisc(identita.getCodFiscale());
                hreq.setAttribute(USERINFO_SESSIONATTR, userInfo);
                hreq.setAttribute(IRIDE_ID_SESSIONATTR,identita);
            } else if (marker != null) {
                try {
                    Identita identita = getFlaidoorIdentitaFromToken(
                            normalizeToken(marker));
                    UserInfo userInfo = new UserInfo();
                    userInfo.setCodFisc(identita.getCodFiscale());
                    requestContext.setProperty(IRIDE_ID_SESSIONATTR, identita);
                    hreq.setAttribute(USERINFO_SESSIONATTR, userInfo);
                    hreq.setAttribute(IRIDE_ID_SESSIONATTR, identita);
                } catch (Exception e) {

                    throw new IOException("errore nella parsificazione dell'header di autenticazione", e);
                }
            } else   {
                // il marcatore deve sempre essere presente altrimenti e' una
                // condizione di errore (escluse le pagine home e di servizio)
                if (mustCheckPage(requestContext.getUriInfo().getPath())) {
                    throw new IOException(
                            "Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
                } else if ("dev".equals(ConfigUtils.getProfiles().get(0))) {
                    Identita identita = getFlaidoorIdentitaFromToken(
                            normalizeToken(getTokenDevMode()));
                    requestContext.setProperty(IRIDE_ID_SESSIONATTR, identita);
                    hreq.setAttribute(IRIDE_ID_SESSIONATTR,identita);
                }
            }
        }
    }
}