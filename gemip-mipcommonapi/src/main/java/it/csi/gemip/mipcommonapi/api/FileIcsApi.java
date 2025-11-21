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
import it.csi.gemip.mipcommonapi.api.dto.EventoCalendarioRicerca;
import java.io.File;
import it.csi.gemip.mipcommonapi.api.dto.FileIcs;
import it.csi.gemip.mipcommonapi.api.dto.StoricoCalendario;

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
    Response getEventiCalendario(


@jakarta.validation.Valid  @QueryParam("dataDa") String dataDa,


@jakarta.validation.Valid  @QueryParam("dataA") String dataA,


@jakarta.validation.Valid  @QueryParam("idOperatore") Long idOperatore,


@jakarta.validation.Valid  @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,


@jakarta.validation.Valid  @QueryParam("pageIndex") Integer pageIndex,


@jakarta.validation.Valid  @QueryParam("pageSize") Integer pageSize,


@jakarta.validation.Valid  @QueryParam("sortDirection") String sortDirection,


@jakarta.validation.Valid  @QueryParam("orderBy") String orderBy,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
    Response getFileIcsById(@NotNull


@jakarta.validation.Valid  @QueryParam("idFileIcs") Long idFileIcs,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
    Response getStoricoCalendario(


@jakarta.validation.Valid  @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
    Response getStoricoFileIcsById(@NotNull


@jakarta.validation.Valid  @QueryParam("idFileIcs") Long idFileIcs,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = aggiornamento file calendario
     * description = aggiorna abilitazione e descrizione
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = file selezionato<br>
           schema implementation = { @see FileIcs }</p>
         </li>
       </ul>
    */
    @PUT
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response updateFileIcs(@jakarta.validation.Valid 
FileIcs
body
,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
