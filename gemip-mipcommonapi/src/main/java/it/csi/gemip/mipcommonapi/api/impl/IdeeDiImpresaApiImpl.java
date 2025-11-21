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

import static it.csi.gemip.utils.Constants.INPUT_MISSING.CITTADINO_ID_CITTADINO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import it.csi.gemip.mipcommonapi.api.IdeeDiImpresaApi;
import it.csi.gemip.mipcommonapi.api.dto.CittadinoIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.api.dto.FonteConoscenzaMip;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresa;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresaIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresaRicerca;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresaRicercaConTotale;
import it.csi.gemip.mipcommonapi.api.dto.ResgistraPresenze;
import it.csi.gemip.mipcommonapi.api.dto.StatoIdeaDiImpresa;
import it.csi.gemip.mipcommonapi.integration.entities.MipDFonteConoscenzaMip;
import it.csi.gemip.mipcommonapi.integration.entities.MipDStatoIdeaDiImpresa;
import it.csi.gemip.mipcommonapi.integration.entities.MipRCittadinoIncontroPreacc;
import it.csi.gemip.mipcommonapi.integration.entities.MipRIdeaDiImpresaCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipRIdeaDiImpresaCittadinoId;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIdeaDiImpresa;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDFonteConoscenzaMipRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSoggettoAttuatoreRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDStatoIdeaDiImpresaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCittadinoIncontroPreaccRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIdeaDiImpresaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIncontroPreaccoglienzaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTMailInviataRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.Constants.STATO_IDEA_IMPRESA;
import it.csi.gemip.utils.Convert;
import it.csi.gemip.utils.LogAuditUtil;
import it.csi.gemip.utils.MailUtil;
import it.csi.gemip.utils.cache.MethodNameKeyGenerator;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class IdeeDiImpresaApiImpl extends ParentApiImpl implements IdeeDiImpresaApi {
    @Inject
    GenericMapper genericMapper;

    @Inject
    MipDStatoIdeaDiImpresaRepository mipDStatoIdeaDiImpresaRepository;

    @Inject
    MipDFonteConoscenzaMipRepository mipDFonteConoscenzaMipRepository;

    @Inject
    MipTIdeaDiImpresaRepository mipTIdeaDiImpresaRepository;

    @Inject
    MipTCittadinoRepository mipTCittadinoRepository;

    @Inject
    MipRCittadinoIncontroPreaccRepository cittadinoIncontroPreaccRepository;

    @Inject
    MipTIncontroPreaccoglienzaRepository incontroPreaccoglienzaRepository;
    @Inject
    MipTMailInviataRepository mipTMailInviataRepository;

    @Inject
    MipDSoggettoAttuatoreRepository tutorRepository;

    @Inject
    LogAuditUtil logAuditUtil;

    @Inject
    MailUtil mailUtil;

    @Override
    @CacheResult(cacheName = "getFontiConoscenzaMip", keyGenerator = MethodNameKeyGenerator.class)
    public Response getFontiConoscenzaMip(SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        List<FonteConoscenzaMip> fonteConoscenzaMipList = mipDFonteConoscenzaMipRepository.findAll().list()
                .stream().map(from -> genericMapper.fonteConoscenzaMipToVo(from))
                .toList();
        return Response.ok(fonteConoscenzaMipList).build();
    }

    @Override
    public Response getIdeaDiImpresaById(Long id, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {

        return Response.ok(genericMapper.ideaDiImpresaToVo(mipTIdeaDiImpresaRepository.findById(id))).build();
    }

    @Override
    public Response getIdeaDiImpresaByIdCittadino(Long idCittadino, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        MipTIdeaDiImpresa ideaImpresa = mipTIdeaDiImpresaRepository.getIdeaDiImpresaByIdCittadino(idCittadino);
        IdeaDiImpresaRicerca ideaDiImpresaRicerca = new IdeaDiImpresaRicerca();
        ideaDiImpresaRicerca.setIdeaDiImpresa(genericMapper.ideaDiImpresaToVo(ideaImpresa));
        if (ideaImpresa != null) {
            ideaDiImpresaRicerca.setCitadino(genericMapper
                    .cittadinoEntyToVo(mipTCittadinoRepository.getCittadinoPerIdeaImpresa(ideaImpresa.getId()).get(0)));
            MipRCittadinoIncontroPreacc cittadinoIncontroPreacc = cittadinoIncontroPreaccRepository
                    .find(CITTADINO_ID_CITTADINO, ideaDiImpresaRicerca.getCitadino().getIdCittadino()).firstResult();
            ideaDiImpresaRicerca.setAreaTerritoriale(genericMapper
                    .areaTerritorialeEntyToVo(cittadinoIncontroPreacc.getCodiceAreaTerritorialeSelezionata()));
            if (ideaImpresa.getIdTutor() != null) {
                ideaDiImpresaRicerca
                        .setTutor(genericMapper.tutorToVo(tutorRepository.findById(ideaImpresa.getIdTutor())));
            }
        }
        return Response.ok(ideaDiImpresaRicerca).build();
    }

    @Override
    public Response getIdeaDiImpresaByIdIncontroPreacc(Long idIncontroPreaccoglienza, Integer pageIndex, Integer pageSize,  SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<IdeaDiImpresa> ideaDiImpresaList = mipTIdeaDiImpresaRepository
                .getIdeaDiImpresaByIdIncontroPreacc(idIncontroPreaccoglienza)
                .stream().map(from -> genericMapper.ideaDiImpresaToVo(from))
                .toList();
        IdeaDiImpresaIncontroPreaccoglienza response = new IdeaDiImpresaIncontroPreaccoglienza();
        List<IdeaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa> tmpRes = new ArrayList<>();
        int risultatiTot = 0;
        for (IdeaDiImpresa idea : ideaDiImpresaList) {
            MipRIdeaDiImpresaCittadino tmp = mipRIdeaDiImpresaCittadinoRepository
                    .find("idIdeaDiImpresa.id = ?1", idea.getId()).list().get(0);
            CittadinoIncontroPreaccoglienza tmpCitt = genericMapper
                    .cittadinoIncontroPreaccoglienzaToVo(cittadinoIncontroPreaccRepository
                            .find(CITTADINO_ID_CITTADINO, tmp.getIdCittadino().getIdCittadino()).list().get(0));
            IdeaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa tmpDato = new IdeaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa();
            tmpDato.setIdeaDiImpresa(idea);
            tmpDato.setCittadinoIncontro(tmpCitt);
            risultatiTot++;
            tmpRes.add(tmpDato);
        }
        response.setRisultatiTotali(risultatiTot);
        if(pageIndex != null && pageSize != null){
            int startIndex = pageIndex * pageSize;
            int endIndex = Math.min(startIndex + pageSize, tmpRes.size());
            tmpRes = tmpRes.subList(startIndex, endIndex);
        }
        response.setIdeaDiImpresa(tmpRes);
        return Response.ok(response).build();
    }

    @Override
    public Response getIdeaDiImpresaPerParametri(Integer pageIndex, Integer pageSize, String codAreaTerritoriale,
            String dataInseritaDa, String dataInseritaA, String ideaDiImpresa, String cittadinoNome,
            String cittadinoCognome, Long idStatoIdea, String statoPresenze, Long idSoggettoAttuatore, Long idOperatore,
            String orderBy, String sortDirection, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        Map<String, Object> params = new HashMap<>();

        if (codAreaTerritoriale != null) {
            params.put("codAreaTerritoriale", codAreaTerritoriale);
        }
        if (dataInseritaDa != null) {
            params.put("dataInseritaDa", Convert.convertStringToDate(dataInseritaDa));
        }
        if (dataInseritaA != null) {
            params.put("dataInseritaA", Convert.convertStringToDate(dataInseritaA));
        }
        if (ideaDiImpresa != null) {
            ideaDiImpresa = ideaDiImpresa.trim();
            params.put("ideaDiImpresa", "%" + ideaDiImpresa.toLowerCase() + "%");
        }
        if (cittadinoNome != null) {
            cittadinoNome = cittadinoNome.trim();
            params.put("cittadinoNome", "%" + cittadinoNome.toLowerCase() + "%");
        }
        if (cittadinoCognome != null) {
            cittadinoCognome = cittadinoCognome.trim();
            params.put("cittadinoCognome", "%" + cittadinoCognome.toLowerCase() + "%");
        }
        if (idStatoIdea != null) {
            params.put("idStatoIdea", idStatoIdea);
        }
        if (idSoggettoAttuatore != null) {
            params.put("idSoggettoAttuatore", idSoggettoAttuatore);
        }
        if (idOperatore != null) {
            params.put("idOperatore", idOperatore);
        }
        if (statoPresenze != null) {
            params.put("statoPresenze", statoPresenze);
        }

        orderBy = orderBy != null ? orderBy : "data";
        sortDirection = sortDirection != null ? sortDirection : "desc";
        PanacheQuery<MipTIdeaDiImpresa> mipTIdeaDiImpresaPanacheQuery = mipTIdeaDiImpresaRepository
                .getIdeaDiImpresaPerParametri(params, orderBy, sortDirection);

        List<MipTIdeaDiImpresa> ideaDiImpresaList = mipTIdeaDiImpresaPanacheQuery.page(pageIndex, pageSize).list();
        List<IdeaDiImpresaRicerca> ideaDiImpresaRicercaList = new ArrayList<>();

        for (MipTIdeaDiImpresa ideaImpresa : ideaDiImpresaList) {
            IdeaDiImpresaRicerca ideaDiImpresaRicerca = new IdeaDiImpresaRicerca();
            ideaDiImpresaRicerca.setIdeaDiImpresa(genericMapper.ideaDiImpresaToVo(ideaImpresa));
            ideaDiImpresaRicerca.setCitadino(genericMapper
                    .cittadinoEntyToVo(mipTCittadinoRepository.getCittadinoPerIdeaImpresa(ideaImpresa.getId()).get(0)));

            MipRCittadinoIncontroPreacc cittadinoIncontroPreacc = cittadinoIncontroPreaccRepository
                    .find("cittadino.idCittadino", ideaDiImpresaRicerca.getCitadino().getIdCittadino()).firstResult();
            if (cittadinoIncontroPreacc != null)
                ideaDiImpresaRicerca.setAreaTerritoriale(genericMapper
                        .areaTerritorialeEntyToVo(cittadinoIncontroPreacc.getCodiceAreaTerritorialeSelezionata()));

            if (ideaImpresa.getIdTutor() != null) {
                ideaDiImpresaRicerca
                        .setTutor(genericMapper.tutorToVo(tutorRepository.findById(ideaImpresa.getIdTutor())));
            }
            ideaDiImpresaRicercaList.add(ideaDiImpresaRicerca);
        }

        IdeaDiImpresaRicercaConTotale response = new IdeaDiImpresaRicercaConTotale();
        response.setIdeeDiImpresa(ideaDiImpresaRicercaList);
        response.setRisultatiTotali(mipTIdeaDiImpresaPanacheQuery.count());
        return Response.ok(response).build();
    }

    @Override
    @CacheResult(cacheName = "getStatiIdeaDiImpresa", keyGenerator = MethodNameKeyGenerator.class)
    public Response getStatiIdeaDiImpresa(SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        List<StatoIdeaDiImpresa> statoIdeaDiImpresaList = mipDStatoIdeaDiImpresaRepository.findAll().list()
                .stream().map(from -> genericMapper.statoIdeaDiImpresaToVo(from))
                .toList();
        List<StatoIdeaDiImpresa> statoIdeaDiImpresaRidottoList = new ArrayList<>();
        for (StatoIdeaDiImpresa stato : statoIdeaDiImpresaList) {
            switch (stato.getDescrizioneStatoIdeaDiImpresa()) {
                case "Annullata":
                    break;
                case "Modificata":
                    break;
                case "Accorpata":
                    break;
                case "Abbandonato":
                    break;
                case "Questionario compilato":
                    break;
                default:
                    statoIdeaDiImpresaRidottoList.add(stato);
                    break;
            }
        }
        return Response.ok(statoIdeaDiImpresaRidottoList).build();
    }

    @Transactional
    @Override
    public Response insertIdeaDiImpresa(Long idCittadino, IdeaDiImpresa body, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipTCittadino mipTCittadino = mipTCittadinoRepository.findById(idCittadino);
        MipTIdeaDiImpresa mipTIdeaDiImpresa = genericMapper.ideaDiImpresaToEnty(body);
        mipTIdeaDiImpresa.setCodUserInserim(mipTCittadino.getCodiceFiscale());
        mipTIdeaDiImpresa.setCodUserAggiorn(mipTCittadino.getCodiceFiscale());
        mipTIdeaDiImpresa.setDataInserim(adesso);
        mipTIdeaDiImpresa.setDataAggiorn(adesso);
        mipTIdeaDiImpresa.setDataCambioStato(adesso);

        mipTIdeaDiImpresa.persist();

        MipRIdeaDiImpresaCittadino mipRIdeaDiImpresaCittadino = new MipRIdeaDiImpresaCittadino();
        MipRIdeaDiImpresaCittadinoId mipRIdeaDiImpresaCittadinoId = new MipRIdeaDiImpresaCittadinoId();
        mipRIdeaDiImpresaCittadinoId.setIdIdeaDiImpresa(mipTIdeaDiImpresa.getId());
        mipRIdeaDiImpresaCittadinoId.setIdCittadino(mipTCittadino.getIdCittadino());

        mipRIdeaDiImpresaCittadino.setId(mipRIdeaDiImpresaCittadinoId);
        mipRIdeaDiImpresaCittadino.setIdIdeaDiImpresa(mipTIdeaDiImpresa);
        mipRIdeaDiImpresaCittadino.setIdCittadino(mipTCittadino);

        mipRIdeaDiImpresaCittadino.setDataAssociazione(adesso);
        mipRIdeaDiImpresaCittadino.setCodUserInserim(mipTCittadino.getCodiceFiscale());
        mipRIdeaDiImpresaCittadino.setCodUserAggiorn(mipTCittadino.getCodiceFiscale());
        mipRIdeaDiImpresaCittadino.setDataInserim(adesso);
        mipRIdeaDiImpresaCittadino.setDataAggiorn(adesso);

        mipRIdeaDiImpresaCittadino.setDataAssociazione(adesso);

        mipRIdeaDiImpresaCittadino.persist();

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "insertIdeaDiImpresa",
                mipRIdeaDiImpresaCittadino,
                body,
                httpRequest);

        body.setId(mipTIdeaDiImpresa.getId());
        MipDFonteConoscenzaMip tmp = MipDFonteConoscenzaMip
                .findById(body.getFonteConoscenzaMip().getCodiceFonteConoscenzaMip());
        body.setFonteConoscenzaMip(genericMapper.fonteConoscenzaMipToVo(tmp));
        return Response.ok(body).build();
    }

    @Override
    @Transactional
    public Response registraPresenzeIncontro(List<ResgistraPresenze> body, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        for (ResgistraPresenze registro : body) {
            Date adesso = getNow();
            MipRCittadinoIncontroPreacc updateObject = cittadinoIncontroPreaccRepository
                    .find("cittadino.idCittadino=?1", registro.getIdCittadino()).list().get(0);

            switch (registro.getStato()) {
                case "DISDETTO":
                    updateObject.setDAnnullamento(getNow());
                    updateObject.setFlgCittadinoPresente(null);
                    break;
                case "HA PARTECIPATO":
                    updateObject.setDAnnullamento(null);
                    updateObject.setFlgCittadinoPresente("S");
                    break;
                case "ISCRITTO":
                    updateObject.setDAnnullamento(null);
                    updateObject.setFlgCittadinoPresente(null);
                    break;
                case "NON PRESENTATO":
                    updateObject.setDAnnullamento(null);
                    updateObject.setFlgCittadinoPresente("N");
                    break;
            }

            cittadinoIncontroPreaccRepository.getEntityManager().merge(updateObject);
            if ("S".equals(updateObject.getFlgCittadinoPresente())) {
                if ((mipTMailInviataRepository.count("idCittadino = :idCittadino AND codTestoEmail = :codTestoEmail",
                        Parameters.with("idCittadino", updateObject.getCittadino().getIdCittadino()).and(
                                "codTestoEmail", MailUtil.COD_MAIL_30_QUESTIONARIO_1)) == 0)
                        &&
                        (mipTMailInviataRepository.count(
                                "idCittadino = :idCittadino AND codTestoEmail = :codTestoEmail",
                                Parameters.with("idCittadino", updateObject.getCittadino().getIdCittadino()).and(
                                        "codTestoEmail", MailUtil.COD_MAIL_40_SCELTA_TUTOR)) == 0)) {
                    mailUtil.sendEmailCittadino(MailUtil.COD_MAIL_30_QUESTIONARIO_1,
                            updateObject.getCittadino().getIdCittadino(), null, httpHeaders);
                    mailUtil.sendEmailCittadino(MailUtil.COD_MAIL_40_SCELTA_TUTOR,
                            updateObject.getCittadino().getIdCittadino(), null, httpHeaders);
                }
            }

            logAuditUtil.insertLogAudit(
                    adesso,
                    httpHeaders,
                    "registraPresenzeIncontro",
                    updateObject,
                    body,
                    httpRequest);
            MipTIdeaDiImpresa updateIdeaImpresa = mipTIdeaDiImpresaRepository
                    .getIdeaDiImpresaByIdCittadino(updateObject.getCittadino().getIdCittadino());
            MipDStatoIdeaDiImpresa statoIdeaDiImpresa;
            if ("S".equals(updateObject.getFlgCittadinoPresente())) {
                statoIdeaDiImpresa = mipDStatoIdeaDiImpresaRepository.findById(7L);
            } else {
                statoIdeaDiImpresa = mipDStatoIdeaDiImpresaRepository.findById(6L);
            }
            updateIdeaImpresa.setStatoIdeaDiImpresa(statoIdeaDiImpresa);

            mipTIdeaDiImpresaRepository.getEntityManager().merge(updateIdeaImpresa);
        }
        return Response.ok(body).build();
    }

    @Transactional
    @Override
    public Response updateIdeaDiImpresa(IdeaDiImpresa body, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipTIdeaDiImpresa mipTIdeaDiImpresa = genericMapper.ideaDiImpresaToEnty(body);

        mipTIdeaDiImpresa.setStatoIdeaDiImpresa(genericMapper.statoIdeaDiImpresaToEntity(body.getStatoIdeaDiImpresa()));
        mipTIdeaDiImpresa.setCodUserAggiorn(body.getCodUserAggiorn());

        MipTIdeaDiImpresa tmp = MipTIdeaDiImpresa.findById(body.getId());

        if (!body.getStatoIdeaDiImpresa().getId().equals(tmp.getStatoIdeaDiImpresa().getId())) {
            mipTIdeaDiImpresa.setDataCambioStato(getNow());
        } else {
            mipTIdeaDiImpresa.setDataCambioStato(tmp.getDataCambioStato());
        }
        Long stato = mipTIdeaDiImpresa.getStatoIdeaDiImpresa().getId();
        if ("Accoglienza individuale svolta (No firma PdS)"
                .equals(body.getStatoIdeaDiImpresa().getDescrizioneStatoIdeaDiImpresa())) {
            mipTIdeaDiImpresa.setFlgErogazionePrimaOra("S");
        } else if (stato == STATO_IDEA_IMPRESA.INCONTRO_PREACC || stato == STATO_IDEA_IMPRESA.POST_PREACC
                || stato == STATO_IDEA_IMPRESA.ASSOC_SOGG_ATTUATORE) {
            mipTIdeaDiImpresa.setFlgErogazionePrimaOra(null);
        }

        if ("Patto di servizio firmato".equals(body.getStatoIdeaDiImpresa().getDescrizioneStatoIdeaDiImpresa())
                && tmp.getDataFirmaPattoServizio() == null) {
            mipTIdeaDiImpresa.setDataFirmaPattoServizio(getNow());
            mipTIdeaDiImpresa.setFlgErogazionePrimaOra("S");
        } else if (stato == STATO_IDEA_IMPRESA.INCONTRO_PREACC || stato == STATO_IDEA_IMPRESA.POST_PREACC
                || stato == STATO_IDEA_IMPRESA.ASSOC_SOGG_ATTUATORE || stato == STATO_IDEA_IMPRESA.ACC_INDIVID_SVOLTA) {
            mipTIdeaDiImpresa.setDataFirmaPattoServizio(null);
        }
        if (body.isBusinessPlanValidato() != null && body.isBusinessPlanValidato()) {
            mipTIdeaDiImpresa.setDataValidBusinessPlan(body.getDataValidBusinessPlan());
            mipTIdeaDiImpresa.setDataFirmaPattoServizio(
                    tmp.getDataFirmaPattoServizio() != null ? tmp.getDataFirmaPattoServizio() : getNow());
            mipTIdeaDiImpresa.setFlgErogazionePrimaOra("S");
            MipTCittadino cittadino = mipTCittadinoRepository.getCittadinoPrincipaleIdeaImpresa(tmp.getId());
            if (mipTMailInviataRepository.count("idCittadino = :idCittadino AND codTestoEmail = :codTestoEmail",
                    Parameters.with("idCittadino", cittadino.getIdCittadino()).and(
                            "codTestoEmail", MailUtil.COD_MAIL_60_QUESTIONARIO_3)) == 0) {
                mailUtil.sendEmailCittadino(MailUtil.COD_MAIL_60_QUESTIONARIO_3, cittadino.getIdCittadino(), null,
                        httpHeaders);
            }
        } else if (stato == STATO_IDEA_IMPRESA.INCONTRO_PREACC || stato == STATO_IDEA_IMPRESA.POST_PREACC
                || stato == STATO_IDEA_IMPRESA.ASSOC_SOGG_ATTUATORE || stato == STATO_IDEA_IMPRESA.ACC_INDIVID_SVOLTA
                || stato == STATO_IDEA_IMPRESA.PATTO_SERVIZIO_FIRMATO) {
            mipTIdeaDiImpresa.setDataValidBusinessPlan(null);
        }
        mipTIdeaDiImpresa.setDataAggiorn(getNow());

        mipTIdeaDiImpresaRepository.getEntityManager().merge(mipTIdeaDiImpresa);

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "updateIdeaDiImpresa",
                mipTIdeaDiImpresa,
                body,
                httpRequest);
        IdeaDiImpresa res = genericMapper.ideaDiImpresaToVo(mipTIdeaDiImpresa);
        res.setBusinessPlanValidato(mipTIdeaDiImpresa.getDataValidBusinessPlan() != null);
        return Response.ok(genericMapper.ideaDiImpresaToVo(mipTIdeaDiImpresa)).build();
    }
}
