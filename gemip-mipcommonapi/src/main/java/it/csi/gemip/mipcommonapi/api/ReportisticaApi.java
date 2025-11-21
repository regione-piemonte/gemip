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
import it.csi.gemip.mipcommonapi.api.dto.Errore;
import java.io.File;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresaRicercaExtendedConTotale;
import it.csi.gemip.mipcommonapi.api.dto.ReportQuestionario;

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
@Path("/reportistica")


public interface ReportisticaApi  {
   
    /**
     * summary = esportazione del foglio excel
     * description = restituisce il file excel delle idee d'impresa
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = excel<br>
           schema implementation = { @see File }</p>
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
    @Path("/esporta-excel-idee-impresa")
    
    @Produces({ "application/octet-stream", "application/json" })
    Response exportExcelIdeeImpresa(


@jakarta.validation.Valid  @QueryParam("nomeOperatore") String nomeOperatore,


@jakarta.validation.Valid  @QueryParam("dataDa") Date dataDa,


@jakarta.validation.Valid  @QueryParam("dataA") Date dataA,


@jakarta.validation.Valid  @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,


@jakarta.validation.Valid  @QueryParam("idCodAreaTerritoriale") String idCodAreaTerritoriale,


@jakarta.validation.Valid  @QueryParam("idStatoIdea") Long idStatoIdea,


@jakarta.validation.Valid  @QueryParam("tipoReport") String tipoReport,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = esportazione del foglio excel
     * description = restituisce il file excel dei questionari
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = excel<br>
           schema implementation = { @see File }</p>
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
    @Path("/esporta-excel-questionari")
    
    @Produces({ "application/octet-stream", "application/json" })
    Response exportExcelQuestionari(


@jakarta.validation.Valid  @QueryParam("idFase") Long idFase,


@jakarta.validation.Valid  @QueryParam("dataDa") Date dataDa,


@jakarta.validation.Valid  @QueryParam("dataA") Date dataA,


@jakarta.validation.Valid  @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,


@jakarta.validation.Valid  @QueryParam("idCodAreaTerritoriale") String idCodAreaTerritoriale,


@jakarta.validation.Valid  @QueryParam("nomeOperatore") String nomeOperatore,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = restituzione domande e risposte questionario fase 1
     * description = restituisce domande e risposte questionario fase 1
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = domande e risposte<br>
           schema implementation = { @see ReportQuestionario }</p>
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
    @Path("/questionari")
    
    @Produces({ "application/octet-stream", "application/json" })
    Response getDomandeRisposte(


@jakarta.validation.Valid  @QueryParam("idFase") Long idFase,


@jakarta.validation.Valid  @QueryParam("dataDa") Date dataDa,


@jakarta.validation.Valid  @QueryParam("dataA") Date dataA,


@jakarta.validation.Valid  @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,


@jakarta.validation.Valid  @QueryParam("idCodAreaTerritoriale") String idCodAreaTerritoriale,


@jakarta.validation.Valid  @QueryParam("pageIndex") Integer pageIndex,


@jakarta.validation.Valid  @QueryParam("pageSize") Integer pageSize,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

    /**
     * summary = esportazione del foglio excel
     * description = restituisce  idee d'impresa
     * @return Response
     * responses: 
       <ul>
         <li>    
           <p>responseCode = 200, description = elnco idee d'impresa<br>
           schema implementation = { @see IdeaDiImpresaRicercaExtendedConTotale }</p>
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
    @Path("/idee-impresa")
    
    @Produces({ "application/octet-stream", "application/json" })
    Response getIdeeImpresa(


@jakarta.validation.Valid  @QueryParam("dataDa") Date dataDa,


@jakarta.validation.Valid  @QueryParam("dataA") Date dataA,


@jakarta.validation.Valid  @QueryParam("idSoggettoAttuatore") Long idSoggettoAttuatore,


@jakarta.validation.Valid  @QueryParam("idCodAreaTerritoriale") String idCodAreaTerritoriale,


@jakarta.validation.Valid  @QueryParam("idStatoIdea") Long idStatoIdea,


@jakarta.validation.Valid  @QueryParam("tipoReport") String tipoReport,


@jakarta.validation.Valid  @QueryParam("pageIndex") Integer pageIndex,


@jakarta.validation.Valid  @QueryParam("pageSize") Integer pageSize,@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders , @Context HttpServletRequest httpRequest );

}
