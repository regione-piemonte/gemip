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
import it.csi.gemip.mipbackofficebff.bff.dto.EventoCalendarioRicerca;
import java.io.File;
import it.csi.gemip.mipbackofficebff.bff.dto.FileIcs;
import it.csi.gemip.mipbackofficebff.bff.dto.StoricoCalendario;

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
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
@Path("/file-ics")


public interface FileIcsApi  {
   
    /**
     * summary = elenco eventi calendario filtrati per parametri
     * description = elenco eventi calendario filtrati per parametri
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco incontri preaccoglienza<br>
           schema implementation = { @see EventoCalendarioRicerca }</p>
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
    @Path("/eventi-calendario")
    
    @Produces({ "application/json" })
    Response getEventiCalendario( @QueryParam("dataDa") String dataDa, @QueryParam("dataA") String dataA, @QueryParam("idOperatore") Long idOperatore, @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore, @QueryParam("pageIndex") Integer pageIndex, @QueryParam("pageSize") Integer pageSize, @QueryParam("sortDirection") String sortDirection, @QueryParam("orderBy") String orderBy,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = scarica documento
     * description = restituisce il documento con id passato come parametro
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = ricevuto<br>
           schema implementation = { @see File }</p>
         </li>
         <li>    
           <p>responseCode = 400, description = inserimento non avvenuto con successoa<br>
           schema implementation = { @see Errore }</p>
         </li>
       </ul>
    */
    @GET
    @Path("/download")
    
    @Produces({ "application/octet-stream", "application/problem+json" })
    Response getFileIcsById( @NotNull @QueryParam("idFileIcs") Long idFileIcs,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = storico inserimento file ics
     * description = storico inserimento file ics
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco incontri preaccoglienza<br>
           schema implementation = { @see StoricoCalendario }</p>
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
    @Path("/storico")
    
    @Produces({ "application/json" })
    Response getStoricoCalendario( @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = file ics tramite id
     * description = restituisci il file con l'id indicato
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = file selezionato<br>
           schema implementation = { @see StoricoCalendario }</p>
         </li>
       </ul>
    */
    @GET
    
    
    @Produces({ "application/json" })
    Response getStoricoFileIcsById( @NotNull @QueryParam("idFileIcs") Long idFileIcs,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = carica un file ics inserisce i dati dentro evento calendario e file ics
     * description = carica un file inserisce i dati dentro evento calendario e file ics
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = inserimento avvenuto con successo<br>
         </li>
         <li>    
           <p>responseCode = 400, description = inserimento non avvenuto con successo<br>
           schema implementation = { @see Errore }</p>
         </li>
       </ul>
    */
    @POST
    
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/problem+json" })
    Response insertFileIcs(MultipartFormDataInput input,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = aggiornamento file calendario
     * description = aggiorna abilitazione e descrizione
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = file selezionato<br>
           schema implementation = { @see StoricoCalendario }</p>
         </li>
       </ul>
    */
    @PUT
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response updateFileIcs( FileIcs body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
