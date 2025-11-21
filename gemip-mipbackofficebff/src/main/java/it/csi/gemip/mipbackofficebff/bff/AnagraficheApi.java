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


import it.csi.gemip.mipbackofficebff.bff.dto.AnagraficaCittadino;
import it.csi.gemip.mipbackofficebff.bff.dto.CondizioneFamiliare;
import it.csi.gemip.mipbackofficebff.bff.dto.CondizioneOccupazionale;
import it.csi.gemip.mipbackofficebff.bff.dto.Errore;
import it.csi.gemip.mipbackofficebff.bff.dto.SvantaggioAbitativo;
import it.csi.gemip.mipbackofficebff.bff.dto.TitoloDiStudio;

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
@Path("/anagrafiche")


public interface AnagraficheApi  {
   
    /**
     * summary = torna l'anagrafica del cittadino passando come parametro l'id
     * description = torna l'anagrafica del cittadino passando come parametro l'id
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = Anagrafica del cittadino<br>
           schema implementation = { @see AnagraficaCittadino }</p>
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
         </li>
       </ul>
    */
    @GET
    @Path("/anagrafica")
    
    @Produces({ "application/json" })
    Response getAnafraficaCittadinoPerId( @NotNull @QueryParam("idAnagraficaCittadino") Long idAnagraficaCittadino,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco condizione-occupazionale
     * description = torna l'elenco delle condizione occupazionale
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco delle condizione occupazionale<br>
           schema implementation = { @see CondizioneOccupazionale }</p>
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
         </li>
       </ul>
    */
    @GET
    @Path("/condizione-occupazionale")
    
    @Produces({ "application/json" })
    Response getCondizioneOccupazionale(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco condizioni familiari
     * description = elenco condizoni familiari
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco delle condizioni familiari<br>
           schema implementation = { @see CondizioneFamiliare }</p>
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
    @Path("/condizione-familiare")
    
    @Produces({ "application/json" })
    Response getCondizioniFamiliare(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco svantaggi-abitativo
     * description = elenco svantaggi abitativo
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco degli svantaggi abitativo<br>
           schema implementation = { @see SvantaggioAbitativo }</p>
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
    @Path("/svantaggi-abitativo")
    
    @Produces({ "application/json" })
    Response getSvantaggiAbitativo(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco titolo-studio
     * description = torna l'elenco dei titolo di studio
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco dei titolo di studio<br>
           schema implementation = { @see TitoloDiStudio }</p>
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
         </li>
       </ul>
    */
    @GET
    @Path("/titolo-studio")
    
    @Produces({ "application/json" })
    Response getTitoliStudio(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = inserisce un'anagradica cittadino
     * description = inserisce un'anagradica cittadino
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see AnagraficaCittadino }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @POST
    @Path("/anagrafica")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response insertAnagraficaCittadino( AnagraficaCittadino body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = modifica un'anagradica cittadino
     * description = modifica un'anagradica cittadino
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see AnagraficaCittadino }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @PUT
    @Path("/anagrafica")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response updateAnagraficaCittadino( AnagraficaCittadino body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
