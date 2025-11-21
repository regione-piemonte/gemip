package it.csi.gemip.mipbackofficebff.bff;

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

import it.csi.gemip.mipbackofficebff.bff.dto.*;


import it.csi.gemip.mipbackofficebff.bff.dto.Errore;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;

import java.util.List;
import java.util.Map;

import java.io.InputStream;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.validation.constraints.*;
@Path("/users")


public interface UsersApi  {
   
    /**
     * summary = get current user
     * description = restituisce le informazioni relative all'utente corrente (me)
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = l'utente corrente (se l'applicazione è securizzata e se è stata effettuata l'autenticazione).<br>
           schema implementation = { @see UserInfo }</p>
         </li>
         <li>    
           <p>responseCode = 404, description = se l'applicazione non è securizzata o se non è stata effettuata l'autenticazione<br>
           schema implementation = { @see Errore }</p>
         </li>
         <li>    
           <p>responseCode = 200, description = Unexpected error<br>
           schema implementation = { @see Errore }</p>
         </li>
       </ul>
    */
    @GET
    @Path("/me")
    
    @Produces({ "application/json", "application/problem+json" })
    Response getCurrentUser(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
