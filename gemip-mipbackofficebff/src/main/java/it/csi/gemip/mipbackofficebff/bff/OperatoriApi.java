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
import it.csi.gemip.mipbackofficebff.bff.dto.Operatore;
import it.csi.gemip.mipbackofficebff.bff.dto.OperatoreIncontroPreaccoglienza;
import it.csi.gemip.mipbackofficebff.bff.dto.SoggettoAffidatario;
import it.csi.gemip.mipbackofficebff.bff.dto.SoggettoAttuatore;

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
@Path("/operatori")


public interface OperatoriApi  {
   
    /**
     * summary = cancella / disassocia un operatore a un incontro preaccoglienza
     * description = delete a pet
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = se un payload è ritornato<br>
         </li>
         <li>    
           <p>responseCode = 202, description = se nessun payload è ritornato<br>
         </li>
       </ul>
    */
    @DELETE
    @Path("/operatore-incontro-preaccoglienza")
    
    
    Response deleteOperatoreIncontroPreaccoglienza( @NotNull @QueryParam("idOperatore") Long idOperatore, @NotNull @QueryParam("idIncontroPreaccoglienza") Long idIncontroPreaccoglienza,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = torna l'operatore pasando come parametro l'id
     * description = torna l'operaore pasando come parametro l'id
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = l'idea di impresa dell'id pasato come parametro<br>
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
    @Path("/operatore")
    
    @Produces({ "application/json" })
    Response getOperatoreById( @NotNull @QueryParam("idOperatore") Long idOperatore,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco tutti operatori
     * description = elenco dei operatori
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco dei operatorei<br>
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
    
    
    @Produces({ "application/json" })
    Response getOperatori(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco operatori relazionato a un incontro preaccoglienza passando l'id del incontro come parametro
     * description = torna l'elenco operatori relazionato a un incontro preaccoglienza passando l'id del incontro come parametro
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco delle province<br>
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
    @Path("/operatore-incontro-preaccoglienza")
    
    @Produces({ "application/json" })
    Response getOperatoriIncontroPreaccoglienza( @NotNull @QueryParam("idIncontroPreacc") Long idIncontroPreacc,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco tutti operatori passando diversi paramatri per la ricerca
     * description = elenco dei operatori passando diversi paramatri per la ricerca
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco dei operatori<br>
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
    @Path("/ricerca-operatori")
    
    @Produces({ "application/json" })
    Response getOperatoriRicerca( @QueryParam("nome") String nome, @QueryParam("cognome") String cognome, @QueryParam("email") String email, @QueryParam("codiceFiscale") String codiceFiscale, @QueryParam("idOperatore") Long idOperatore, @QueryParam("soggetto") String soggetto, @QueryParam("abilitato") String abilitato, @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco operatori associati al soggetto affidatario tramite id dell'utente loggato
     * description = elenco operatori associati al soggetto affidatario tramite id dell'utente loggato
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco dei operatori<br>
           schema implementation = { @see SoggettoAffidatario }</p>
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
    @Path("/operatori-sogg-affidatario")
    
    @Produces({ "application/json" })
    Response getOperatoriSoggAffidatarioById( @NotNull @QueryParam("idOperatore") Long idOperatore,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = torna l'operatore pasando come parametro l'id
     * description = torna l'elenco soggetti attuatori  come parametro l'id
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = l'elenco dei soggetti attuatori<br>
           schema implementation = array of { @see SoggettoAttuatore }</p>
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
    @Path("/operatore/soggetti-attutatori")
    
    @Produces({ "application/json" })
    Response getSoggettiAttuatoriForOperatoreById( @NotNull @QueryParam("idOperatore") Long idOperatore,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = inserisce un'operatore
     * description = inserisce un'operatore
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see Operatore }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @POST
    @Path("/operatore")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response insertOperatore( Operatore body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = inserisce/associa un'operatore ad un incontro preaccoglienza
     * description = inserisce/associa un'operatore ad un incontro preaccoglienza
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see OperatoreIncontroPreaccoglienza }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @POST
    @Path("/operatore-incontro-preaccoglienza")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response insertOperatoreIncontroPreaccoglienza( OperatoreIncontroPreaccoglienza body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = modifica un'operatore
     * description = modifica un'operatore
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see Operatore }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @PUT
    @Path("/operatore")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response updateOperaotre( Operatore body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
