package it.csi.gemip.mipcittadinobff.bff;

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

import it.csi.gemip.mipcittadinobff.bff.dto.*;


import it.csi.gemip.mipcittadinobff.bff.dto.Cittadino;
import it.csi.gemip.mipcittadinobff.bff.dto.DashboardCittadino;

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
@Path("/cittadini")


public interface CittadiniApi  {
   
    /**
     * summary = stato compilazione del cittadino
     * description = stato compilazione del cittadino
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see DashboardCittadino }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @POST
    @Path("/cittadino/dashboard")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response getDashboardCittadino( Cittadino body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = cambia lo stato della idea d'impresa a inviato e invia la mail di conferma di partecipazione al'incontro
     * description = cambia lo stato della idea d'impresa a inviato e invia la mail di conferma di partecipazione al'incontro
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see DashboardCittadino }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @PUT
    @Path("/cittadino/invia-dati")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response updateIdeaImpresaInviata( DashboardCittadino body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
