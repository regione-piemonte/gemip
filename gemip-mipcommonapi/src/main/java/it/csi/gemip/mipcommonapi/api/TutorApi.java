package it.csi.gemip.mipcommonapi.api;

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

import it.csi.gemip.mipcommonapi.api.dto.*;


import it.csi.gemip.mipcommonapi.api.dto.Errore;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresa;
import it.csi.gemip.mipcommonapi.api.dto.Tutor;

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
@Path("/tutor")


public interface TutorApi  {
   
    /**
     * summary = elenco tutor abilitati per mostrare al cittadino in base l'area territoriale
     * description = elenco tutor abilitati per mostrare al cittadino  in base l'area territoriale
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco incontri preaccoglienza<br>
           schema implementation = { @see Tutor }</p>
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
    @Path("/tutorAbilitati-area-territoriale")
    
    @Produces({ "application/json" })
    Response getTutorAbilitatiPerAreaTerritoriale(


@jakarta.validation.Valid  @QueryParam("codAreaTerritoriale") String codAreaTerritoriale,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco tutor per mostrare al cittadino in base l'area territoriale
     * description = elenco tutor per mostrare al cittadino  in base l'area territoriale
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco incontri preaccoglienza<br>
           schema implementation = { @see Tutor }</p>
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
    @Path("/tutor-area-territoriale")
    
    @Produces({ "application/json" })
    Response getTutorPerAreaTerritoriale(


@jakarta.validation.Valid  @QueryParam("codAreaTerritoriale") String codAreaTerritoriale,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = tutor per id
     * description = tutor per id tutor
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = tutor<br>
           schema implementation = { @see Tutor }</p>
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
    
    
    @Produces({ "application/json" })
    Response getTutorPerId(@NotNull


@jakarta.validation.Valid  @QueryParam("idTutor") Long idTutor,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = disassocia un tutor dall'idea di impresa
     * description = disassocia il tutor dall'idea di impresa ed invia mail al soggetto attuatore
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see IdeaDiImpresa }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @DELETE
    @Path("/reset-tutor")
    
    @Produces({ "application/json" })
    Response resetTutor(@NotNull


@jakarta.validation.Valid  @QueryParam("idIdeaImpresa") Long idIdeaImpresa,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = aggiunge il tutor all'idea di impresa
     * description = aggiunge il tutor all'idea di impresa ed invia mail al cittadino e soggetto attuatore
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see IdeaDiImpresa }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @PUT
    @Path("/scelta-tutor")
    
    @Produces({ "application/json" })
    Response sceltaTutor(@NotNull


@jakarta.validation.Valid  @QueryParam("idTutor") Long idTutor,@NotNull


@jakarta.validation.Valid  @QueryParam("idImpresa") Long idImpresa,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
