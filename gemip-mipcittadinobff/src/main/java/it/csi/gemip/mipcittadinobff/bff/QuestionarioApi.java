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


import java.math.BigDecimal;
import it.csi.gemip.mipcittadinobff.bff.dto.Errore;
import it.csi.gemip.mipcittadinobff.bff.dto.Questionario;
import it.csi.gemip.mipcittadinobff.bff.dto.RispostaCompilazione;

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
@Path("/questionario")


public interface QuestionarioApi  {
   
    /**
     * summary = Ottiene il questionario corrente basato sulla fase in cui si trova il cittadino
     * description = 
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = Questionario della fase corrente con domande e risposte.<br>
           schema implementation = { @see Questionario }</p>
         </li>
         <li>    
           <p>responseCode = 400, description = usata quando i campi non sono validati correttamente<br>
           schema implementation = { @see Errore }</p>
         </li>
         <li>    
           <p>responseCode = 404, description = Cittadino non trovato o nessun questionario disponibile per la fase corrente<br>
         </li>
         <li>    
           <p>responseCode = 200, description = Errore generico<br>
         </li>
       </ul>
    */
    @GET
    @Path("/corrente")
    
    @Produces({ "application/json" })
    Response getQuestionarioCorrente( @NotNull @QueryParam("idCittadino") Long idCittadino,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = Ottiene l'ID fase corrente per il cittadino
     * description = 
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = ID della fase corrente per il cittadino dato.<br>
           schema implementation = { @see BigDecimal }</p>
         </li>
         <li>    
           <p>responseCode = 400, description = usata quando i campi non sono validati correttamente<br>
           schema implementation = { @see Errore }</p>
         </li>
         <li>    
           <p>responseCode = 404, description = Cittadino non trovato<br>
         </li>
         <li>    
           <p>responseCode = 200, description = Errore generico<br>
         </li>
       </ul>
    */
    @GET
    @Path("/fase-corrente")
    
    @Produces({ "application/json" })
    Response getQuestionarioFaseCorrente( @NotNull @QueryParam("idCittadino") Long idCittadino,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = Salva le risposte del cittadino al questionario
     * description = 
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 201, description = Risposte salvate con successo.<br>
         </li>
         <li>    
           <p>responseCode = 400, description = usata quando i campi non sono validati correttamente<br>
           schema implementation = { @see Errore }</p>
         </li>
         <li>    
           <p>responseCode = 404, description = Cittadino o questionario non trovato<br>
         </li>
         <li>    
           <p>responseCode = 200, description = Errore generico<br>
         </li>
       </ul>
    */
    @POST
    @Path("/{id_questionario}/risposte")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response salvaRisposteQuestionario( List<RispostaCompilazione> body, @NotNull @QueryParam("idCittadino") Long idCittadino, @PathParam("id_questionario") Long idQuestionario,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
