package it.csi.gemip.mipcommonapi.api.impl;

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

import it.csi.gemip.mipcommonapi.api.TutorApi;
import it.csi.gemip.mipcommonapi.api.dto.Tutor;

import it.csi.gemip.mipcommonapi.integration.entities.MipDStatoIdeaDiImpresa;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCompilazioneQuestionario;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIdeaDiImpresa;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSoggettoAttuatoreRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCompilazioneQuestionarioFaseRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCompilazioneQuestionarioRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIdeaDiImpresaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIncontroPreaccoglienzaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTRispostaCompilazioneRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.LogAuditUtil;
import it.csi.gemip.utils.MailUtil;
import it.csi.gemip.utils.Constants.STATO_IDEA_IMPRESA;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.*;

import io.quarkus.panache.common.Parameters;

public class TutorApiImpl extends ParentApiImpl implements TutorApi {

    @Inject
    GenericMapper genericMapper;
    @Inject
    MipDSoggettoAttuatoreRepository tutorRepository;
    @Inject
    MipTCittadinoRepository cittadinoRepository;
    @Inject
    MipTIdeaDiImpresaRepository ideaDiImpresaRepository;
    @Inject
    MipTRispostaCompilazioneRepository mipTRispostaCompilazioneRepository;
    @Inject
    MipTIncontroPreaccoglienzaRepository mipTIncontroPreaccoglienzaRepository;
    @Inject
    MipTCompilazioneQuestionarioRepository compilazioneQuestionarioRepository;
    @Inject
    MipRCompilazioneQuestionarioFaseRepository compilazioneQuestionarioFaseRepository;
    @Inject
    LogAuditUtil logAuditUtil;
    @Inject
    MailUtil mailUtil;

    @Override
    public Response getTutorPerAreaTerritoriale(String codAreaTerritoriale, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<Tutor> tutorList = tutorRepository.getTutorPerAreaTerritoriale(codAreaTerritoriale)
                .stream().map(from -> genericMapper.tutorToVo(from))
                .toList();
        return Response.ok(tutorList).build();
    }

    @Override
    public Response getTutorAbilitatiPerAreaTerritoriale(String codAreaTerritoriale, SecurityContext securityContext,
                                                HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<Tutor> tutorList = tutorRepository.getTutorAbilitatiPerAreaTerritoriale(codAreaTerritoriale)
                .stream().map(from -> genericMapper.tutorToVo(from))
                .toList();
        return Response.ok(tutorList).build();
    }

    @Override
    public Response getTutorPerId(Long idTutor, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        Tutor ris = genericMapper.tutorToVo(tutorRepository.findById(idTutor));
        return Response.ok(ris).build();
    }

    @Transactional
    @Override
    public Response sceltaTutor(Long idTutor, Long idImpresa, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipTIdeaDiImpresa mipTIdeaDiImpresa = ideaDiImpresaRepository.findById(idImpresa);
        mipTIdeaDiImpresa.setIdTutor(idTutor);
        mipTIdeaDiImpresa.setDataSceltaTutor(adesso);
        MipDStatoIdeaDiImpresa statoIdeaDiImpresa = MipDStatoIdeaDiImpresa
                .findById(STATO_IDEA_IMPRESA.ASSOC_SOGG_ATTUATORE);
        mipTIdeaDiImpresa.setDataCambioStato(adesso);
        mipTIdeaDiImpresa.setDataAggiorn(adesso);
        if ("S".equals(mipTIdeaDiImpresa.getFlgSbloccoAreaTerritoriale())) { // reset idea d'impresa come in stato
                                                                             // associazione soggetto attuatore,
                                                                             // cancellando i questionari già compilati
            mipTIdeaDiImpresa.setDataValidBusinessPlan(null);
            mipTIdeaDiImpresa.setFlgErogazionePrimaOra(null);
            mipTIdeaDiImpresa.setDataFirmaPattoServizio(null);
            List<MipTCompilazioneQuestionario> compilazioneQuestionarioList = compilazioneQuestionarioRepository
                    .find(" cittadino.idCittadino = :idCittadino ",
                            Parameters.with("idCittadino", cittadinoRepository
                                    .getCittadinoPerIdeaImpresa(mipTIdeaDiImpresa.getId()).get(0).getIdCittadino()))
                    .list();
            compilazioneQuestionarioList.forEach(e -> {
                mipTRispostaCompilazioneRepository.delete(
                        " idCompilazioneQuestionario = :idCompilazioneQuestionario AND idDomanda.idFaseQuestionario.id > 1",
                        Parameters.with("idCompilazioneQuestionario", e));
                compilazioneQuestionarioFaseRepository.delete(
                        " idCompilazioneQuestionario = :idCompilazioneQuestionario AND id.idFaseQuestionario > 1",
                        Parameters.with("idCompilazioneQuestionario", e));
            });
        }
        mipTIdeaDiImpresa.setFlgSbloccoAreaTerritoriale(null);
        mipTIdeaDiImpresa.setCodUserAggiorn(
                cittadinoRepository.getCittadinoPerIdeaImpresa(mipTIdeaDiImpresa.getId()).get(0).getCodiceFiscale());
        mipTIdeaDiImpresa.setStatoIdeaDiImpresa(statoIdeaDiImpresa);

        ideaDiImpresaRepository.getEntityManager().merge(mipTIdeaDiImpresa);
        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "sceltaTutor",
                mipTIdeaDiImpresa,
                null,
                httpRequest);
        Map<String, String> info = new HashMap<>();
        MipTCittadino cittadino = cittadinoRepository.getCittadinoPrincipaleIdeaImpresa(idImpresa);

        mailUtil.sendEmailCittadino(MailUtil.COD_MAIL_50_SCELTA_TUTOR_CONFERMA_CITTADINO, cittadino.getIdCittadino(),
                null, httpHeaders);

        info.put("mailCittadino.progetto", mipTIdeaDiImpresa.getDescrizioneIdeaDiImpresa());
        mailUtil.sendEmailTutor(MailUtil.COD_MAIL_50_SCELTA_TUTOR_CONFERMA_TUTOR, cittadino.getIdCittadino(), idTutor,
                info);

        return Response.ok(genericMapper.ideaDiImpresaToVo(mipTIdeaDiImpresa)).build();
    }

    @Transactional
    @Override
    public Response resetTutor(Long idImpresa,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

        Date adesso = new Date();
        MipTIdeaDiImpresa mipTIdeaDiImpresa = ideaDiImpresaRepository.findById(idImpresa);
        Long idTutor = mipTIdeaDiImpresa.getIdTutor();
        if (idTutor == null)
            return Response.ok().build(); // nothing to do;

        if (mipTIdeaDiImpresa.getStatoIdeaDiImpresa() == null
                || !Arrays.asList(5L, 8L, 9L, 12L).contains(mipTIdeaDiImpresa.getStatoIdeaDiImpresa().getId())) {
            throw new WebApplicationException("Operazione possibile solo nello stato 12", 400);
        }
        // non deve cambiare nulla
        // mipTIdeaDiImpresa.setIdTutor(null);
        // mipTIdeaDiImpresa.setDataSceltaTutor(null);
        // MipDStatoIdeaDiImpresa statoIdeaDiImpresa =
        // MipDStatoIdeaDiImpresa.findById(STATO_IDEA_IMPRESA.POST_PREACC);
        // mipTIdeaDiImpresa.setStatoIdeaDiImpresa(statoIdeaDiImpresa);
        mipTIdeaDiImpresa.setFlgSbloccoAreaTerritoriale("S");

        ideaDiImpresaRepository.getEntityManager().merge(mipTIdeaDiImpresa);
        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "resetTutor",
                mipTIdeaDiImpresa,
                null,
                httpRequest);

        // invio mail al tutor
        Map<String, String> info = new HashMap<>();
        MipTCittadino cittadino = cittadinoRepository.getCittadinoPrincipaleIdeaImpresa(mipTIdeaDiImpresa.getId());
        info.put("cc.vecchioTutor", tutorRepository.findById(idTutor).getEmail());
        mailUtil.sendEmailCittadino(MailUtil.COD_MAIL_55_RESET_TUTOR, cittadino.getIdCittadino(), info, httpHeaders);

        return Response.ok().build();
    }

}
