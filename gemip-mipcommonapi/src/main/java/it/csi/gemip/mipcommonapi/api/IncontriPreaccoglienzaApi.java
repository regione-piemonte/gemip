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


import it.csi.gemip.mipcommonapi.api.dto.CittadinoIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.api.dto.Errore;
import it.csi.gemip.mipcommonapi.api.dto.IncontroPreAccoglienzaInsert;
import it.csi.gemip.mipcommonapi.api.dto.IncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.api.dto.IncontroPreaccoglienzaAreaTerritoriale;
import it.csi.gemip.mipcommonapi.api.dto.IncontroPreaccoglienzaRicerca;

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
     * summary = elimina un inconro di preaccoglienza
     * description = 
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see IncontroPreAccoglienzaInsert }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @DELETE
    @Path("/incontro-preaccoglienza")
    
    @Produces({ "application/json" })
    Response deleteIncontroPreaccoglienzaById(@NotNull


@jakarta.validation.Valid  @QueryParam("idIncontroPreaccoglienza") Long idIncontroPreaccoglienza,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
    Response getCittadinoIncontroPreaccoglienza(@NotNull


@jakarta.validation.Valid  @QueryParam("id") Long id,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
    Response getIncontriPreaccoglienzaByAreaTerritoriale(


@jakarta.validation.Valid  @QueryParam("codAreaTerritoriale") String codAreaTerritoriale,


@jakarta.validation.Valid  @QueryParam("completo") Boolean completo,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco incontri preaccoglienza con parametri
     * description = elenco degli incontri di preaccoglienza filtrati per parametri
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco incontri preaccoglienza<br>
           schema implementation = { @see IncontroPreaccoglienzaRicerca }</p>
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
    Response getIncontriPreaccoglienzaByParameters(


@jakarta.validation.Valid  @QueryParam("codAreaTerritoriale") String codAreaTerritoriale,


@jakarta.validation.Valid  @QueryParam("dataIncontroDa") String dataIncontroDa,


@jakarta.validation.Valid  @QueryParam("dataIncontroA") String dataIncontroA,


@jakarta.validation.Valid  @QueryParam("luogo") Long luogo,


@jakarta.validation.Valid  @QueryParam("idOperatore") String idOperatore,


@jakarta.validation.Valid  @QueryParam("idSoggettoAttuatore") String idSoggettoAttuatore,


@jakarta.validation.Valid  @QueryParam("pageIndex") Integer pageIndex,


@jakarta.validation.Valid  @QueryParam("pageSize") Integer pageSize,


@jakarta.validation.Valid  @QueryParam("orderBy") String orderBy,


@jakarta.validation.Valid  @QueryParam("sortDirection") String sortDirection,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = controllo incontro già esistente
     * description = controllo incontro già esistente
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
    @Path("/controllo-incontro-esistente")
    
    @Produces({ "application/json" })
    Response getIncontriPreaccoglienzaForControlloEsistente(


@jakarta.validation.Valid  @QueryParam("idLuogo") Long idLuogo,


@jakarta.validation.Valid  @QueryParam("dataIncontro") String dataIncontro,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = torna l'incontro di preaccoglienza  passando  l'id come parametro
     * description = torna l'incontro di preaccoglienza passando  l'id come parametro
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = l'incontro preaccoglienza dell'id pasato come parametro<br>
           schema implementation = { @see IncontroPreAccoglienzaInsert }</p>
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
    @Path("/incontro-preaccoglienza")
    
    @Produces({ "application/json" })
    Response getIncontroPreaccoglienza(@NotNull


@jakarta.validation.Valid  @QueryParam("id") Long id,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
    Response getIncontroPreaccoglienzaPerCittadino(@NotNull


@jakarta.validation.Valid  @QueryParam("idCittadino") Long idCittadino,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = torna l'incontro di preaccoglienza relazionato al cittadino passando  l'id ide d'impresa come parametro
     * description = torna l'incontro di preaccoglienza relazionato al cittadino passando  l'id della idea di impresa
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
    @Path("/incontro-preaccoglienza-per-idea-impresa")
    
    @Produces({ "application/json" })
    Response getIncontroPreaccoglienzaPerIdeaImpresa(@NotNull


@jakarta.validation.Valid  @QueryParam("idIdeaImpresa") Long idIdeaImpresa,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
       </ul>
    */
    @POST
    @Path("/cittadino-incontro-preaccoglienza")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response insertCittadinoIncontroPreaccoglienza(@jakarta.validation.Valid 
CittadinoIncontroPreaccoglienza
body
,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = inserisce un'incontro di preaccoglienza
     * description = inserisce un'incontro di preaccoglienza
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see IncontroPreAccoglienzaInsert }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @POST
    @Path("/incontro-preaccoglienza")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response insertIncontroPreaccoglienza(@jakarta.validation.Valid 
IncontroPreAccoglienzaInsert
body
,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = inserimento della relazione tra incontro preaccoglienza e area territoriale
     * description = inserisce un nuovo record nella tabella mip_r_incontro_preacc_area_terr
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see IncontroPreaccoglienzaAreaTerritoriale }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @POST
    @Path("/associazione-area-territoriale")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response insertRelazioneAreaTerritorialeIncontro(@jakarta.validation.Valid 
IncontroPreaccoglienzaAreaTerritoriale
body
,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = manda un comunicato a tutti i partecipanti all'incontro di preaccoglienza
     * description = torna l'incontro di preaccoglienza relazionato al cittadino passando  l'id del cittadino come parametro
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = comunicazione inviata con successo<br>
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
    @POST
    @Path("/incontro-preaccoglienza-invia-comunicato")
    
    @Produces({ "application/json" })
    Response inviaComunicatoPartecipanti(@NotNull


@jakarta.validation.Valid  @QueryParam("oggetto") String oggetto,@NotNull


@jakarta.validation.Valid  @QueryParam("corpo") String corpo,


@jakarta.validation.Valid  @QueryParam("idCittadino") Long idCittadino,


@jakarta.validation.Valid  @QueryParam("idIncontroPreaccoglienza") Long idIncontroPreaccoglienza,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

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
    Response updateCittadinoIncontroPreaccoglienza(@NotNull


@jakarta.validation.Valid  @QueryParam("statoIdeaImpresa") String statoIdeaImpresa,@jakarta.validation.Valid 
CittadinoIncontroPreaccoglienza
body
,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = modifica un'incontro di preaccoglienza
     * description = modifica un'incontro di preaccoglienza
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see IncontroPreAccoglienzaInsert }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @PUT
    @Path("/incontro-preaccoglienza")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response updateIncontroPreaccoglienza(@jakarta.validation.Valid 
IncontroPreAccoglienzaInsert
body
,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
