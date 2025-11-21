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
import it.csi.gemip.mipbackofficebff.bff.dto.FonteConoscenzaMip;
import it.csi.gemip.mipbackofficebff.bff.dto.IdeaDiImpresa;
import it.csi.gemip.mipbackofficebff.bff.dto.IdeaDiImpresaIncontroPreaccoglienza;
import it.csi.gemip.mipbackofficebff.bff.dto.IdeaDiImpresaRicerca;
import it.csi.gemip.mipbackofficebff.bff.dto.IdeaDiImpresaRicercaConTotale;
import it.csi.gemip.mipbackofficebff.bff.dto.ResgistraPresenze;
import it.csi.gemip.mipbackofficebff.bff.dto.StatoIdeaDiImpresa;

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
@Path("/idee-di-impresa")


public interface IdeeDiImpresaApi  {
   
    /**
     * summary = elenco fonti conoscenza mip
     * description = elenco degli fonti conoscenza mip
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco degli stati idea di impresa<br>
           schema implementation = { @see FonteConoscenzaMip }</p>
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
    @Path("/fonti-conoscenza-mip")
    
    @Produces({ "application/json" })
    Response getFontiConoscenzaMip(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = torna l'idea di impresa pasando come parametro l'id
     * description = torna l'idea di impresa pasando come parametro l'id
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = l'idea di impresa dell'id pasato come parametro<br>
           schema implementation = { @see IdeaDiImpresa }</p>
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
    @Path("/idea-di-impresa")
    
    @Produces({ "application/json" })
    Response getIdeaDiImpresaById( @NotNull @QueryParam("id") Long id,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = torna l'elenco idea di impresa pasando come parametro l'id del cittadino
     * description = torna l'idea di impresa pasando come parametro l'id cittadino
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = idea di impresa<br>
           schema implementation = { @see IdeaDiImpresaRicerca }</p>
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
    @Path("/idea-di-impresa-cittadino")
    
    @Produces({ "application/json" })
    Response getIdeaDiImpresaByIdCittadino( @NotNull @QueryParam("idCittadino") Long idCittadino,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = torna l'elenco idea di impresa pasando come parametro l'id incontro preaccoglienza
     * description = torna l'idea di impresa pasando come parametro l'id
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = l'elenco idea di impresa<br>
           schema implementation = { @see IdeaDiImpresaIncontroPreaccoglienza }</p>
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
    @Path("/incontro-pre-accoglienza")
    
    @Produces({ "application/json" })
    Response getIdeaDiImpresaByIdIncontroPreacc( @NotNull @QueryParam("idIncontroPreaccoglienza") Long idIncontroPreaccoglienza,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = torna l'elenco idea di impresa pasando diversi parametro di ricerca preaccoglienza
     * description = torna l'idea di impresa pasando diversi parametri di ricerca
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = l'elenco idea di impresa<br>
           schema implementation = { @see IdeaDiImpresaRicercaConTotale }</p>
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
    @Path("/ricerca")
    
    @Produces({ "application/json" })
    Response getIdeaDiImpresaPerParametri( @NotNull @QueryParam("pageIndex") Integer pageIndex, @NotNull @QueryParam("pageSize") Integer pageSize, @QueryParam("codAreaTerritoriale") String codAreaTerritoriale, @QueryParam("dataInseritaDa") String dataInseritaDa, @QueryParam("dataInseritaA") String dataInseritaA, @QueryParam("ideaDiImpresa") String ideaDiImpresa, @QueryParam("cittadinoNome") String cittadinoNome, @QueryParam("cittadinoCognome") String cittadinoCognome, @QueryParam("idStatoIdea") Long idStatoIdea, @QueryParam("statoPresenze") String statoPresenze, @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore, @QueryParam("idOperatore") Long idOperatore, @QueryParam("orderBy") String orderBy, @QueryParam("sortDirection") String sortDirection,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = elenco stati idea di impresa
     * description = elenco degli stati idea di impresa
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elenco degli stati idea di impresa<br>
           schema implementation = { @see StatoIdeaDiImpresa }</p>
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
    @Path("/stati-idea-di-impresa")
    
    @Produces({ "application/json" })
    Response getStatiIdeaDiImpresa(@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = inserisce un'idea di impresa
     * description = inserisce un'idea di impresa
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
    @POST
    @Path("/idea-di-impresa")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response insertIdeaDiImpresa( @NotNull @QueryParam("idCittadino") Long idCittadino, IdeaDiImpresa body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = registra le presenze ad un incontro
     * description = 
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = successful operation<br>
           schema implementation = { @see ResgistraPresenze }</p>
         </li>
         <li>    
           <p>responseCode = 405, description = Invalid input<br>
         </li>
       </ul>
    */
    @PUT
    @Path("/registra-presenze")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response registraPresenzeIncontro( List<ResgistraPresenze> body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = modifica un'idea di impresa
     * description = modifica un'idea di impresa
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
    @Path("/idea-di-impresa")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    Response updateIdeaDiImpresa( IdeaDiImpresa body,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
