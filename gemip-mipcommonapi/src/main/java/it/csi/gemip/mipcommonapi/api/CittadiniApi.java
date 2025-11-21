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


import it.csi.gemip.mipcommonapi.api.dto.AnagraficaCittadinoRicerca;
import it.csi.gemip.mipcommonapi.api.dto.Cittadino;
import it.csi.gemip.mipcommonapi.api.dto.DashboardCittadino;
import it.csi.gemip.mipcommonapi.api.dto.Errore;

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
     * summary = elenco cittadini associati a una idea d'impresa
     * description = elenco svantaggi abitativo
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco cittadini associati a una idea d'impresa<br>
           schema implementation = { @see Cittadino }</p>
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
    @Path("/cittadino-idea-impresa")
    
    @Produces({ "application/json" })
    Response getCittadinoPerIdeaImpresa(@NotNull


@jakarta.validation.Valid  @QueryParam("idIdeaDiImpresa") Long idIdeaDiImpresa,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = ricerca cittadini per parametri
     * description = elenco di cittadini pasando diversi parametri di ricerca
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = l'elenco idea di impresa<br>
           schema implementation = { @see AnagraficaCittadinoRicerca }</p>
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
    Response getCittadinoPerParametri(


@jakarta.validation.Valid  @QueryParam("nome") String nome,


@jakarta.validation.Valid  @QueryParam("cognome") String cognome,


@jakarta.validation.Valid  @QueryParam("email") String email,


@jakarta.validation.Valid  @QueryParam("codiceFiscale") String codiceFiscale,


@jakarta.validation.Valid  @QueryParam("codAreaTerritoriale") String codAreaTerritoriale,


@jakarta.validation.Valid  @QueryParam("dataInseritoDa") String dataInseritoDa,


@jakarta.validation.Valid  @QueryParam("dataInseritoA") String dataInseritoA,


@jakarta.validation.Valid  @QueryParam("idOperatore") Long idOperatore,


@jakarta.validation.Valid  @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,


@jakarta.validation.Valid  @QueryParam("pageIndex") Integer pageIndex,


@jakarta.validation.Valid  @QueryParam("pageSize") Integer pageSize,


@jakarta.validation.Valid  @QueryParam("orderBy") String orderBy,


@jakarta.validation.Valid  @QueryParam("sortDirection") String sortDirection,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
    Response getDashboardCittadino(@jakarta.validation.Valid 
Cittadino
body
,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = cambia lo stato della idea d'impresa a concluso e invia la mail di compilazione questionario terza fase
     * description = 
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
    @Path("/cittadino/concludi-mip")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response updateIdeaImpresaConclusa(@jakarta.validation.Valid 
DashboardCittadino
body
,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
    Response updateIdeaImpresaInviata(@jakarta.validation.Valid 
DashboardCittadino
body
,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
