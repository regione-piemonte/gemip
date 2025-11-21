package it.csi.gemip.mipbackofficebff.bff.impl;

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


import it.csi.gemip.mipbackofficebff.bff.UsersApi;
import it.csi.gemip.mipbackofficebff.bff.dto.Errore;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import it.csi.gemip.mipbackofficebff.filter.IrideIdAdapterFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UsersApiServiceImpl implements UsersApi{


    @Override
    public Response getCurrentUser(SecurityContext securityContext, HttpHeaders httpHeaders , HttpServletRequest httpRequest ){
        UserInfo currentUser = (UserInfo)httpRequest.getAttribute(IrideIdAdapterFilter.USERINFO_SESSIONATTR);
        if (currentUser != null){
            return Response.ok(currentUser).build();
        }
        else{
            Errore error = new Errore();
            error.setErrorMessage("utente non disponibile");
            error.setCode(404);
            return Response.status(404).entity(error).build();
        }
    }
    
}