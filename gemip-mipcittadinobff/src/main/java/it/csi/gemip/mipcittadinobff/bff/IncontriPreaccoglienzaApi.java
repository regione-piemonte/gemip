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


import it.csi.gemip.mipcittadinobff.bff.dto.CittadinoIncontroPreaccoglienza;
import it.csi.gemip.mipcittadinobff.bff.dto.Errore;
import it.csi.gemip.mipcittadinobff.bff.dto.IncontroPreaccoglienza;

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
@Path("/incontri-preaccoglienza")


public interface IncontriPreaccoglienzaApi  {
   
    /**
     * summary = torna l'incontro di preaccoglienza relazionato al cittadino passando  l'id cittadino_incontro_preaccoglienza
     * description = torna l'incontro di preaccoglienza relazionato al cittadino passando  l'id cittadino_incontro_preaccoglienza
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = cittadino incontro preaccoglienza dell'id pasato come parametro<br>
           schema implementation = { @see CittadinoIncontroPreaccoglienza }</p>
         </li>
         <li>    
           <p>responseCode = 400, description = usata quando i campi non sono validati correttamente<br>
           schema implementation = { @see Errore }</p>
         </li>
         <li>    
           <p>responseCode = 403, description = utente non autorizzato al caso d'uso<br>
           schema implementation = { @see Errore }</p>
         </li>
         <li>    
           <p>responseCode = 200, description = errore generico<br>
           schema implementation = { @see Errore }</p>
         </li>
       </ul>
    */
    @GET
    @Path("/cittadino-incontro-preaccoglienza")
    
    @Produces({ "application/json" })
    Response getCittadinoIncontroPreaccoglienza( @NotNull @QueryParam("id") Long id,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco incontri preaccoglienza per mostrare al cittadino
     * description = elenco incontri preaccoglienza per mostrare al cittadino per scegliere l'incontro per asistere
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco incontri preaccoglienza<br>
           schema implementation = { @see IncontroPreaccoglienza }</p>
         </li>
         <li>    
           <p>responseCode = 400, description = usata quando i campi non sono validati correttamente<br>
           schema implementation = { @see Errore }</p>
         </li>
         <li>    
           <p>responseCode = 403, description = utente non autorizzato al caso d'uso<br>
           schema implementation = { @see Errore }</p>
         </li>
         <li>    
           <p>responseCode = 200, description = errore generico<br>
           schema implementation = { @see Errore }</p>
         </li>
       </ul>
    */
    @GET
    @Path("/incontri-preaccoglienza-disponibile")
    
    @Produces({ "application/json" })
    Response getIncontriPreaccoglienzaByAreaTerritoriale( @QueryParam("codAreaTerritoriale") String codAreaTerritoriale, @QueryParam("completo") Boolean completo,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = torna l'incontro di preaccoglienza relazionato al cittadino passando  l'id del cittadino come parametro
     * description = torna l'incontro di preaccoglienza relazionato al cittadino passando  l'id del cittadino come parametro
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = incontro preaccoglienza<br>
           schema implementation = { @see IncontroPreaccoglienza }</p>
         </li>
         <li>    
           <p>responseCode = 400, description = usata quando i campi non sono validati correttamente<br>
           schema implementation = { @see Errore }</p>
         </li>
         <li>    
           <p>responseCode = 403, description = utente non autorizzato al caso d'uso<br>
           schema implementation = { @see Errore }</p>
         </li>
         <li>    
           <p>responseCode = 200, description = errore generico<br>
           schema implementation = { @see Errore }</p>
         </li>
       </ul>
    */
    @GET
    @Path("/incontro-preaccoglienza-per-cittadino")
    
    @Produces({ "application/json" })
    Response getIncontroPreaccoglienzaPerCittadino( @NotNull @QueryParam("idCittadino") Long idCittadino,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = inserisce un'incontro di preaccoglienza relazionato al cittadino
     * description = inserisce un'incontro di preaccoglienza relazionato al cittadino
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see CittadinoIncontroPreaccoglienza }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @POST
    @Path("/cittadino-incontro-preaccoglienza")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response insertCittadinoIncontroPreaccoglienza( CittadinoIncontroPreaccoglienza body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = modifica un'incontro di preaccoglienza relazionato al cittadino
     * description = modifica un'incontro di preaccoglienza relazionato al cittadino
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see CittadinoIncontroPreaccoglienza }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @PUT
    @Path("/cittadino-incontro-preaccoglienza")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response updateCittadinoIncontroPreaccoglienza( CittadinoIncontroPreaccoglienza body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
