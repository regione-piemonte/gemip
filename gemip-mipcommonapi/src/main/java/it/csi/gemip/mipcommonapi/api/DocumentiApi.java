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


import java.util.Date;
import it.csi.gemip.mipcommonapi.api.dto.Documento;
import it.csi.gemip.mipcommonapi.api.dto.Errore;
import java.io.File;
import it.csi.gemip.mipcommonapi.api.dto.TipoDocumento;

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
@Path("/documenti")


public interface DocumentiApi  {
   
    /**
     * summary = carica un documento
     * description = carica un documento
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = inserimento avvenuto con successo<br>
         </li>
         <li>    
           <p>responseCode = 400, description = inserimento non avvenuto con successoa<br>
           schema implementation = { @see Errore }</p>
         </li>
       </ul>
    */
    @POST
    @Path("/carica-documento")
    @Consumes({ "multipart/form-data" })
    @Produces({ "application/problem+json" })
    Response caricaDocumento(MultipartFormDataInput input,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elimina un documento caricato
     * description = 
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @DELETE
    @Path("/documento")
    
    
    Response deleteDocumento(@NotNull


@jakarta.validation.Valid  @QueryParam("idDocumento") Long idDocumento,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = ricerca documenti
     * description = ricerca di documenti tramite parametri
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco dei comuni associate ad una provincia<br>
           schema implementation = { @see Documento }</p>
         </li>
         <li>    
           <p>responseCode = 400, description = usata quando i campi non sono validati correttamente<br>
           schema implementation = { @see Errore }</p>
         </li>
       </ul>
    */
    @GET
    
    
    @Produces({ "application/json" })
    Response getDocumentiByParameters(


@jakarta.validation.Valid  @QueryParam("tipoDocumento") String tipoDocumento,


@jakarta.validation.Valid  @QueryParam("titolo") String titolo,


@jakarta.validation.Valid  @QueryParam("idIdeaDiImpresa") Long idIdeaDiImpresa,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
    @Path("/documento")
    
    @Produces({ "application/octet-stream", "application/problem+json" })
    Response getDocumentoById(@NotNull


@jakarta.validation.Valid  @QueryParam("idDocumento") Long idDocumento,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco tipi di documento
     * description = elenco tipi di documento
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco tipi di documento<br>
           schema implementation = { @see TipoDocumento }</p>
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
    @Path("/tipi-documento")
    
    @Produces({ "application/json" })
    Response getTipiDocumento(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
