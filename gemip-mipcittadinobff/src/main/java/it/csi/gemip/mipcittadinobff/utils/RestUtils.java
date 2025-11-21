package it.csi.gemip.mipcittadinobff.utils;

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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import it.csi.gemip.mipcittadinobff.bff.dto.UserInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@RequestScoped
public class RestUtils {

    @ConfigProperty(name = "endpoint", defaultValue = "prova")
    String endpoint;

    public static final String NOME_APPLICATIVO = "mipCittadinobff";

    public static final String[] HEADERS_DA_PROPAGARE = new String[]{
        // cfr. SpidService.java
        "email", "dateOfBirth", "placeOfBirth", "countyOfBirth", "digitalAddress",
        "idCard", "mobilePhone", "gender"
    };

    private Client client = ClientBuilder.newClient();

    public Response chiamateGet(UriInfo uriInfo) {
        WebTarget target = commonStuff(uriInfo);
        return target.request().get();
    }

    public Response chiamatePost(UriInfo uriInfo, Object body) {
        WebTarget target = commonStuff(uriInfo);
        return target.request().post(Entity.json(body));
    }

    public Response chiamateDelete(UriInfo uriInfo) {
        WebTarget target = commonStuff(uriInfo);
        return target.request().delete();
    }

    public Response chiamatePut(UriInfo uriInfo, Object body) {
        WebTarget target = commonStuff(uriInfo);
        return target.request().put(Entity.json(body));
    }

    public Response chiamatePut(UriInfo uriInfo) {
        WebTarget target = commonStuff(uriInfo);
        return target.request().put(null);
    }

    public Response chiamateGet(UriInfo uriInfo, UserInfo currentUser, String userIpAddress) {
        WebTarget target = commonStuff(uriInfo);

        Invocation.Builder response = target.request()
                .header("X_Current_User", currentUser != null? currentUser.getCodFisc() : "" )
                .header("X_Current_Applicativo", NOME_APPLICATIVO)
                .header("X_User_Ip_Address", userIpAddress);
        return response.get();
    }

    public Response chiamateGet(UriInfo uriInfo, HttpHeaders httpHeaders) {
        WebTarget target = commonStuff(uriInfo);
        Invocation.Builder response = target.request();
        response = propagateHeaders(httpHeaders, response);
        return response.get();
    }

    public Response chiamatePost(UriInfo uriInfo, Object body, UserInfo currentUser, String userIpAddress) {
        WebTarget target = commonStuff(uriInfo);
        Invocation.Builder response = target.request()
                .header("X_Current_User", currentUser != null? currentUser.getCodFisc() : "" )
                .header("X_Current_Applicativo", NOME_APPLICATIVO)
                .header("X_User_Ip_Address", userIpAddress);
        return response.post(Entity.json(body));
    }

    /**
     * Questa versione del metodo propaga anche alcune headers al backend, elencate in HEADERS_DA_PROPAGARE
     */
    public Response chiamatePost(UriInfo uriInfo, Object body, UserInfo currentUser, String userIpAddress, HttpHeaders httpHeaders) {
        WebTarget target = commonStuff(uriInfo);

        Invocation.Builder response = target.request()
                .header("X_Current_User", currentUser != null? currentUser.getCodFisc() : "" )
                .header("X_Current_Applicativo", NOME_APPLICATIVO)
                .header("X_User_Ip_Address", userIpAddress);
        
        response = propagateHeaders(httpHeaders, response);

        return response.post(Entity.json(body));
    }

    public Response chiamatePut(UriInfo uriInfo, Object body, UserInfo currentUser, String userIpAddress) {
        WebTarget target = commonStuff(uriInfo);

        Invocation.Builder response = target.request()
                .header("X_Current_User", currentUser != null? currentUser.getCodFisc() : "" )
                .header("X_Current_Applicativo", NOME_APPLICATIVO)
                .header("X_User_Ip_Address", userIpAddress);
        return response.put(Entity.json(body));
    }

    public Response chiamateDelete(UriInfo uriInfo, UserInfo currentUser, String userIpAddress) {
        WebTarget target = commonStuff(uriInfo);
        Invocation.Builder response = target.request()
                .header("X_Current_User", currentUser != null? currentUser.getCodFisc() : "" )
                .header("X_Current_Applicativo", NOME_APPLICATIVO)
                .header("X_User_Ip_Address", userIpAddress);

        return response.delete();
    }

    private WebTarget commonStuff(UriInfo uriInfo) {
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String url = uriInfo.getPath();
        WebTarget target = client.target(endpoint + url);

        if (queryParams != null && !queryParams.isEmpty()) {
            for (String key : queryParams.keySet()) {
                target = target.queryParam(key, queryParams.get(key).toArray()[0]);
            }
        }
        return target;
    }

    private Invocation.Builder propagateHeaders(HttpHeaders httpHeaders, Invocation.Builder response) {
        for (String headerKey: HEADERS_DA_PROPAGARE) {
            String headerValue = httpHeaders.getHeaderString(headerKey);
            if (headerValue != null && !headerValue.isEmpty()) {
                response = response.header(headerKey, headerValue);
            }
        }
        return response;
    }
}
