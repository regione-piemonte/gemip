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
import it.csi.gemip.mipcommonapi.api.dto.Operatore;
import it.csi.gemip.mipcommonapi.api.dto.SoggettoAttuatore;
import it.csi.gemip.mipcommonapi.api.dto.SoggettoAttuatoreOperatore;

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
@Path("/soggetti-attuatore")


public interface SoggettiAttuatoreApi  {
   
    /**
     * summary = cambia stato di disabilitazione del soggetto attuatore operatore
     * description = cambia stato di disabilitazione del soggetto attuatore operatore
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco operatore<br>
           schema implementation = { @see SoggettoAttuatoreOperatore }</p>
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
    @PUT
    @Path("/operatore-soggetto-attuatore/cambio-stato-disabilitazione")
    
    @Produces({ "application/json" })
    Response cambiaStatoOperatoriSoggettoAttuatore(@NotNull


@jakarta.validation.Valid  @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,@NotNull


@jakarta.validation.Valid  @QueryParam("idOperatore") Long idOperatore,@NotNull


@jakarta.validation.Valid  @QueryParam("idOperatoreDisabilitazione") Long idOperatoreDisabilitazione,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco operatori associato a un soggetto attuatore pasando l'id come parametro
     * description = elenco soggetti attuatori associato a un soggetto attuatore pasando l'id come parametro
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco operatore<br>
           schema implementation = { @see Operatore }</p>
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
    @Path("/operatore-soggetto-attuatore")
    
    @Produces({ "application/json" })
    Response getOperatoriByIdSoggettoAttuatore(@NotNull


@jakarta.validation.Valid  @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco soggetti attuatori
     * description = elenco soggetti attuatori
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco incontri preaccoglienza<br>
           schema implementation = { @see SoggettoAttuatore }</p>
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
    Response getSoggettiAttuatori(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco soggetto attuatore associati a un operatori pasando l'id operatore come parametro
     * description = elenco soggetti attuatori associato a un soggetto attuatore pasando l'id come parametro
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco operatore<br>
           schema implementation = { @see SoggettoAttuatore }</p>
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
    @Path("/soggetto-attuatore-per-operatore")
    
    @Produces({ "application/json" })
    Response getSoggettoAttuatoreByIdOperatori(@NotNull


@jakarta.validation.Valid  @QueryParam("idOperatore") Long idOperatore,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = modifica dati aggiuntivi
     * description = aggiorna mail e telefono del soggetto
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = oggetto modificato<br>
           schema implementation = { @see SoggettoAttuatore }</p>
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
    @PUT
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response updateSoggettoAttuatore(@jakarta.validation.Valid 
SoggettoAttuatore
body
,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
