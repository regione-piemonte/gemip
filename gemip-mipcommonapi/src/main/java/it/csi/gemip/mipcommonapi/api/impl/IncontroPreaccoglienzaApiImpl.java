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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import it.csi.gemip.mipcommonapi.api.IncontriPreaccoglienzaApi;
import it.csi.gemip.mipcommonapi.api.dto.AreaTerritoriale;
import it.csi.gemip.mipcommonapi.api.dto.CittadinoIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.api.dto.IncontroPreAccoglienzaInsert;
import it.csi.gemip.mipcommonapi.api.dto.IncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.api.dto.IncontroPreaccoglienzaAreaTerritoriale;
import it.csi.gemip.mipcommonapi.api.dto.IncontroPreaccoglienzaRicerca;
import it.csi.gemip.mipcommonapi.api.dto.LuogoIncontro;
import it.csi.gemip.mipcommonapi.integration.entities.ExtGmopDAreaTerritoriale;
import it.csi.gemip.mipcommonapi.integration.entities.ExtTtComune;
import it.csi.gemip.mipcommonapi.integration.entities.MipDLuogoIncontro;
import it.csi.gemip.mipcommonapi.integration.entities.MipRCittadinoIncontroPreacc;
import it.csi.gemip.mipcommonapi.integration.entities.MipRIncontroPreaccAreaTerr;
import it.csi.gemip.mipcommonapi.integration.entities.MipRIncontroPreaccAreaTerrId;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCittadinoIncontroPreaccRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRIncontroPreaccAreaTerrRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIncontroPreaccoglienzaRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.Convert;
import it.csi.gemip.utils.LogAuditUtil;
import it.csi.gemip.utils.MailUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

public class IncontroPreaccoglienzaApiImpl extends ParentApiImpl implements IncontriPreaccoglienzaApi {
    @Inject
    private MipTIncontroPreaccoglienzaRepository incontroPreaccoglienzaRepository;

    @Inject
    private GenericMapper genericMapper;

    @Inject
    private MipRCittadinoIncontroPreaccRepository cittadinoIncontroPreaccRepository;

    @Inject
    private MipRIncontroPreaccAreaTerrRepository mipRIncontroPreaccAreaTerrRepository;
    @Inject
    private LogAuditUtil logAuditUtil;
    @Inject
    private MailUtil mailUtil;
    @Inject
    private Logger logger;

    @Inject
    private MipTIncontroPreaccoglienzaRepository mipTIncontroPreaccoglienzaRepository;
    @Inject
    private MipRIncontroPreaccAreaTerrRepository areaIncontroRepository;

    @Override
    @Transactional
    public Response deleteIncontroPreaccoglienzaById(Long idIncontroPreaccoglienza, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        IncontroPreaccoglienza incontroPreaccoglienza = genericMapper
                .incontroPreaccoglienzaToVo(mipTIncontroPreaccoglienzaRepository.getIncontroPreaccoglienzaById(idIncontroPreaccoglienza));
        mipRIncontroPreaccAreaTerrRepository.deleteForIdIncontro(idIncontroPreaccoglienza);
        mipTIncontroPreaccoglienzaRepository.deleteById(idIncontroPreaccoglienza);
        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "deleteIncontroPreaccoglienzaById",
                incontroPreaccoglienza,
                null,
                httpRequest);
        return Response.ok().build();
    }

    @Override
    public Response getCittadinoIncontroPreaccoglienza(Long id, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        /**
         * Torna l'incontro di preaccoglienza relazionato al cittadino passando l'id
         * come parametro
         */
        CittadinoIncontroPreaccoglienza cittadinoIncontroPreaccoglienza = genericMapper
                .cittadinoIncontroPreaccoglienzaToVo(cittadinoIncontroPreaccRepository.findById(id));

        if (cittadinoIncontroPreaccoglienza != null)
            cittadinoIncontroPreaccoglienza.getIncontroPreaccoglienza()
                    .setNumPartecipantiIscritti(incontroPreaccoglienzaRepository.getNumPartecipantiIscritti(
                            cittadinoIncontroPreaccoglienza.getIncontroPreaccoglienza().getId()));
        return Response.ok(cittadinoIncontroPreaccoglienza).build();
    }

    @Override
    public Response getIncontriPreaccoglienzaByAreaTerritoriale(String codAreaTerritoriale, Boolean completo,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Map<String, Object> params = new HashMap<>();

        if (codAreaTerritoriale != null) {
            params.put("codAreaTerritoriale", codAreaTerritoriale);
        }

        Date dal;
        Date al;

        if (completo == null || Boolean.FALSE.equals(completo)) {
            dal = getNow();
        } else { // ricerca incontri cambio incontro BO
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -2); // Sottrae 2 mesi alla data attuale
            dal = cal.getTime();
            al = getNow();
            params.put("al", al);
        }

        params.put("dal", dal);

        List<MipTIncontroPreaccoglienza> incontriPreaccoglienzaList = incontroPreaccoglienzaRepository
                .getIncontriPreaccoglienzaByAreaTerritoriale((HashMap) params);
        List<IncontroPreaccoglienza> incontriPreaccoglienzaVoList = new ArrayList<>();

        for (MipTIncontroPreaccoglienza mipTIncontroPreaccoglienza : incontriPreaccoglienzaList) {
            IncontroPreaccoglienza incontroPreaccoglienza = genericMapper
                    .incontroPreaccoglienzaToVo(mipTIncontroPreaccoglienza);
            if (mipTIncontroPreaccoglienza.getLuogoIncontro() != null) {
                MipDLuogoIncontro tmp = MipDLuogoIncontro
                        .findById(mipTIncontroPreaccoglienza.getLuogoIncontro().getId());
                incontroPreaccoglienza.setLuogoIncontro(genericMapper.luogoIncontroToVO(tmp));

                incontroPreaccoglienza.getLuogoIncontro().setComune(genericMapper
                        .comuneEntyToVo(ExtTtComune.findById(tmp.getCodIstatComune().getCodiceIstatComune())));
            }
            incontroPreaccoglienza.setNumPartecipantiIscritti(
                    incontroPreaccoglienzaRepository.getNumPartecipantiIscritti(mipTIncontroPreaccoglienza.getId()));
            incontriPreaccoglienzaVoList.add(incontroPreaccoglienza);
        }

        incontriPreaccoglienzaVoList = incontriPreaccoglienzaVoList.stream()
                .filter(e -> (e.getNumMaxPartecipanti() - e.getNumPartecipantiIscritti() > 0))
                .collect(Collectors.toList());

        return Response.ok(incontriPreaccoglienzaVoList).build();
    }

    @Override
    public Response getIncontriPreaccoglienzaByParameters(String codAreaTerritoriale, String dataIncontroDa,
            String dataIncontroA, Long luogo, String idOperatore, String idSoggettoAttuatore, Integer pageIndex,
            Integer pageSize, String orderBy, String sortDirection, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Map<String, Object> params = new HashMap<>();

        if (codAreaTerritoriale != null) {
            params.put("codAreaTerritoriale", codAreaTerritoriale);
        }
        if (dataIncontroDa != null) {
            params.put("dataIncontroDa", dataIncontroDa);
        }
        if (dataIncontroA != null) {
            params.put("dataIncontroA", dataIncontroA);
        }
        if (luogo != null) {
            params.put("luogo", luogo);
        }
        if (idOperatore != null) {
            params.put("idOperatore", idOperatore);
        }
        if (idSoggettoAttuatore != null) {
            params.put("idSoggettoAttuatore", idSoggettoAttuatore);
        }

        IncontroPreaccoglienzaRicerca response = new IncontroPreaccoglienzaRicerca();
        PanacheQuery<MipTIncontroPreaccoglienza> incontroPreaccoglienzaPanacheQuery = null;

        orderBy = orderBy != null ? orderBy : "data";
        sortDirection = sortDirection != null ? sortDirection : "asc";

        incontroPreaccoglienzaPanacheQuery = mipTIncontroPreaccoglienzaRepository
                .getIncontriByParameters((HashMap) params, orderBy, sortDirection);

        List<MipTIncontroPreaccoglienza> incontriPreaccoglienzarList;

        if (pageIndex != null && pageSize != null) {
            incontriPreaccoglienzarList = incontroPreaccoglienzaPanacheQuery.page(pageIndex, pageSize).list();
        } else {
            incontriPreaccoglienzarList = incontroPreaccoglienzaPanacheQuery.list();
        }

        response.setTotaleIncontri(incontroPreaccoglienzaPanacheQuery.count());
        List<IncontroPreaccoglienza> incontriPreaccoglienzaVoList = new ArrayList<>();
        for (MipTIncontroPreaccoglienza mipTIncontroPreaccoglienza : incontriPreaccoglienzarList) {
            IncontroPreaccoglienza incontroPreaccoglienza = genericMapper
                    .incontroPreaccoglienzaToVo(mipTIncontroPreaccoglienza);
            incontroPreaccoglienza.setNumPartecipantiIscritti(
                    incontroPreaccoglienzaRepository.getNumPartecipantiIscritti(mipTIncontroPreaccoglienza.getId()));
            if (incontroPreaccoglienza.getLuogoIncontro() != null) {
                List<MipRIncontroPreaccAreaTerr> areaTerr = areaIncontroRepository
                        .findByIdIncontro(incontroPreaccoglienza.getId());
                AreaTerritoriale areaTerritoriale = genericMapper
                        .areaTerritorialeEntyToVo(areaTerr.get(0).getCodAreaTerritoriale());
                incontroPreaccoglienza.getLuogoIncontro().setAreaTerritoriale(areaTerritoriale);
                incontroPreaccoglienza.getLuogoIncontro().getAreaTerritoriale()
                        .setDescrizioneAreaTerritoriale(areaTerritoriale.getDescrizioneAreaTerritoriale());
            } else {
                List<MipRIncontroPreaccAreaTerr> areaTerr = areaIncontroRepository
                        .findByIdIncontro(incontroPreaccoglienza.getId());
                AreaTerritoriale areaTerritoriale = genericMapper
                        .areaTerritorialeEntyToVo(areaTerr.get(0).getCodAreaTerritoriale());
                String areeTerritoriali = "";
                if (areaTerr.size() == 1) {
                    for (MipRIncontroPreaccAreaTerr aree : areaTerr) {
                        areeTerritoriali = areeTerritoriali
                                + aree.getCodAreaTerritoriale().getDescrizioneAreaTerritoriale();
                    }
                } else {
                    int i = 0;
                    for (MipRIncontroPreaccAreaTerr aree : areaTerr) {
                        if (i == areaTerr.size() - 1) {
                            areeTerritoriali = areeTerritoriali
                                    + aree.getCodAreaTerritoriale().getDescrizioneAreaTerritoriale();
                        } else {
                            areeTerritoriali = areeTerritoriali
                                    + aree.getCodAreaTerritoriale().getDescrizioneAreaTerritoriale() + ",\n";
                        }
                        i++;
                    }
                }
                LuogoIncontro tmp = new LuogoIncontro();
                tmp.setAreaTerritoriale(areaTerritoriale);
                incontroPreaccoglienza.setLuogoIncontro(tmp);
                incontroPreaccoglienza.getLuogoIncontro().getAreaTerritoriale()
                        .setDescrizioneAreaTerritoriale(areeTerritoriali);
            }

            incontriPreaccoglienzaVoList.add(incontroPreaccoglienza);
        }
        response.setIncontriPreaccoglienza(incontriPreaccoglienzaVoList);

        return Response.ok(response).build();
    }

    @Override
    public Response getIncontroPreaccoglienza(Long id, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        IncontroPreAccoglienzaInsert response = new IncontroPreAccoglienzaInsert();
        List<MipRIncontroPreaccAreaTerr> incontroPreaccAreaTerrList = mipRIncontroPreaccAreaTerrRepository
                .findByIdIncontro(id);
        List<AreaTerritoriale> areaTerritorialeList = incontroPreaccAreaTerrList.stream()
                .map(incontro -> genericMapper.areaTerritorialeEntyToVo(incontro.getCodAreaTerritoriale()))
                .collect(Collectors.toList());
        response.setIncontroPreaccoglienza(genericMapper
                .incontroPreaccoglienzaToVo(incontroPreaccAreaTerrList.get(0).getIdIncontroPreaccoglienza()));

        response.setAreaTerritoriale(areaTerritorialeList);
        if (incontroPreaccAreaTerrList.get(0).getIdIncontroPreaccoglienza().getLuogoIncontro() != null) {
            MipDLuogoIncontro mipDLuogoIncontro = MipDLuogoIncontro.findById(
                    incontroPreaccAreaTerrList.get(0).getIdIncontroPreaccoglienza().getLuogoIncontro().getId());
            response.getIncontroPreaccoglienza().getLuogoIncontro().setAreaTerritoriale(
                    genericMapper.areaTerritorialeEntyToVo(mipDLuogoIncontro.getCodAreaTerritoriale()));
        }
        return Response.ok(response).build();
    }

    @Override
    public Response getIncontroPreaccoglienzaPerCittadino(Long idCittadino, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        if (cittadinoIncontroPreaccRepository.find("cittadino.idCittadino", idCittadino).firstResult() != null) {
            return Response
                    .ok(genericMapper.incontroPreaccoglienzaToVo(cittadinoIncontroPreaccRepository
                            .find("cittadino.idCittadino", idCittadino).firstResult().getIncontroPreaccoglienza()))
                    .build();
        } else {
            return Response.ok(new IncontroPreaccoglienza()).build();
        }
    }

    @Override
    public Response getIncontroPreaccoglienzaPerIdeaImpresa(Long idIdeaImpresa, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return Response
                .ok(genericMapper.incontroPreaccoglienzaToVo(cittadinoIncontroPreaccRepository
                        .getIncontroPreaccoglienzaPerIdeaImpresa(idIdeaImpresa).get(0).getIncontroPreaccoglienza()))
                .build();
    }

    @Transactional
    @Override
    public Response insertCittadinoIncontroPreaccoglienza(CittadinoIncontroPreaccoglienza body,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        /**
         * inserisce un'incontro di preaccoglienza relazionato al cittadino
         */
        Date adesso = getNow();
        MipRCittadinoIncontroPreacc mipRCittadinoIncontroPreacc = genericMapper.cittadinoIncontroPreaccToEnty(body);
        mipRCittadinoIncontroPreacc.setDataInserim(adesso);
        mipRCittadinoIncontroPreacc.setDataAggiorn(adesso);
        mipRCittadinoIncontroPreacc.persist();

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "insertCittadinoIncontroPreaccoglienza",
                mipRCittadinoIncontroPreacc,
                body,
                httpRequest);

        return Response.ok(genericMapper.cittadinoIncontroPreaccoglienzaToVo(mipRCittadinoIncontroPreacc)).build();
    }

    @Transactional
    @Override
    public Response insertIncontroPreaccoglienza(IncontroPreAccoglienzaInsert body, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipTIncontroPreaccoglienza incontroPreaccoglienza = genericMapper
                .incontroPreaccoglienzaToEntity(body.getIncontroPreaccoglienza());

        incontroPreaccoglienza.setDataInserim(adesso);
        incontroPreaccoglienza.setDataAggiorn(adesso);

        incontroPreaccoglienza.persist();
        for (AreaTerritoriale area : body.getAreaTerritoriale()) {
            ExtGmopDAreaTerritoriale areaEntity = ExtGmopDAreaTerritoriale.findById(area.getCodiceAreaTerritoriale());
            MipRIncontroPreaccAreaTerr tmp = new MipRIncontroPreaccAreaTerr();
            MipRIncontroPreaccAreaTerrId tmpId = new MipRIncontroPreaccAreaTerrId();
            tmpId.setIdIncontroPreaccoglienza(incontroPreaccoglienza.getId());
            tmpId.setCodAreaTerritoriale(areaEntity.getCodiceAreaTerritoriale());
            tmp.setId(tmpId);
            tmp.setIdIncontroPreaccoglienza(incontroPreaccoglienza);
            tmp.setCodAreaTerritoriale(areaEntity);
            tmp.setDataInserim(getNow());
            tmp.setDataAggiorn(getNow());
            tmp.setCodUserAggiorn(body.getCodUserAggiorn());
            tmp.setCodUserInserim(body.getCodUserInserim());
            tmp.persist();
        }
        body.getIncontroPreaccoglienza().setId(incontroPreaccoglienza.getId());

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "insertIncontroPreaccoglienza",
                incontroPreaccoglienza,
                body,
                httpRequest);
        body.setIncontroPreaccoglienza(genericMapper.incontroPreaccoglienzaToVo(incontroPreaccoglienza));

        return Response.ok(body).build();
    }

    @Override
    @Transactional
    public Response insertRelazioneAreaTerritorialeIncontro(IncontroPreaccoglienzaAreaTerritoriale body,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipRIncontroPreaccAreaTerr incontroPreaccAreaTerr = genericMapper.incontroPreaccAreaTerrToEnty(body);

        incontroPreaccAreaTerr.setDataAggiorn(getNow());
        incontroPreaccAreaTerr.setDataInserim(getNow());
        incontroPreaccAreaTerr.persist();

        logAuditUtil.insertLogAudit(adesso,
                httpHeaders,
                "getCittadinoPerParametri",
                incontroPreaccAreaTerr,
                body,
                httpRequest);

        return Response.ok(genericMapper.incontroPreaccAreaTerrToVo(incontroPreaccAreaTerr)).build();
    }

    @Transactional
    @Override
    public Response updateCittadinoIncontroPreaccoglienza(String statoIdeaImpresa, CittadinoIncontroPreaccoglienza body,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        /**
         * Modifica un incontro di preaccoglienza relazionato al cittadino
         */
        Date adesso = getNow();
        // Mappa l'oggetto VO all'entità
        MipRCittadinoIncontroPreacc mipRCittadinoIncontroPreacc = genericMapper.cittadinoIncontroPreaccToEnty(body);
        MipRCittadinoIncontroPreacc mipRCittadinoIncontroPreaccOLD = null;

        // Se l'ID è nullo, cerca l'incontro di preaccoglienza esistente per il
        // cittadino
        if (body.getId() == null) {
            mipRCittadinoIncontroPreaccOLD = cittadinoIncontroPreaccRepository.find(
                    "cittadino.idCittadino = :idCittadino",
                    Parameters.with("idCittadino", body.getCittadino().getIdCittadino())).firstResult();
            mipRCittadinoIncontroPreacc.setId(mipRCittadinoIncontroPreaccOLD.getId());
            mipRCittadinoIncontroPreacc.setDataInserim(mipRCittadinoIncontroPreaccOLD.getDataInserim());
            mipRCittadinoIncontroPreacc.setCodUserInserim(mipRCittadinoIncontroPreaccOLD.getCodUserInserim());
        }

        // Imposta i campi appropriati
        if (body.getIncontroPreaccoglienza() == null) {
            mipRCittadinoIncontroPreacc.setIncontroPreaccoglienza(null);
        }
        mipRCittadinoIncontroPreacc.setDataAggiorn(adesso);
        mipRCittadinoIncontroPreacc.setCodUserAggiorn(body.getCodUserAggiorn());

        // Esegue il merge dell'entità aggiornata
        cittadinoIncontroPreaccRepository.getEntityManager().merge(mipRCittadinoIncontroPreacc);

        //invia mail al cittadino con nuovo incontro
        if(statoIdeaImpresa.equals("Incontro pre-accoglienza")){
            MipTIncontroPreaccoglienza incontro = mipTIncontroPreaccoglienzaRepository
                    .findIncontroPreaccoglienzaCittadino(body.getCittadino().getIdCittadino());
            if ("S".equals(incontro.getFlgIncontroErogatoDaRemoto())) {
                mailUtil.sendEmailCittadino(MailUtil.COD_MAIL_10_POST_REGISTRAZIONE_ONLINE,
                        body.getCittadino().getIdCittadino(), null, httpHeaders);
            } else {
                mailUtil.sendEmailCittadino(MailUtil.COD_MAIL_10_POST_REGISTRAZIONE_PRESENZA,
                        body.getCittadino().getIdCittadino(), null, httpHeaders);
            }
        }


        // Inserisce un log di audit per l'azione di aggiornamento
        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "updateCittadinoIncontroPreaccoglienza",
                mipRCittadinoIncontroPreacc,
                body,
                httpRequest);

        // Restituisce la risposta con l'entità aggiornata
        return Response.ok(genericMapper.cittadinoIncontroPreaccoglienzaToVo(mipRCittadinoIncontroPreacc)).build();
    }

    @Transactional
    @Override
    public Response updateIncontroPreaccoglienza(IncontroPreAccoglienzaInsert body, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipTIncontroPreaccoglienza incontroPreaccoglienza = genericMapper
                .incontroPreaccoglienzaToEntity(body.getIncontroPreaccoglienza());
        incontroPreaccoglienzaRepository.getEntityManager().merge(incontroPreaccoglienza);
        incontroPreaccoglienza = incontroPreaccoglienzaRepository.findById(incontroPreaccoglienza.getId());
        mipRIncontroPreaccAreaTerrRepository.deleteForIdIncontro(incontroPreaccoglienza.getId());
        for (AreaTerritoriale area : body.getAreaTerritoriale()) {
            ExtGmopDAreaTerritoriale areaEntity = ExtGmopDAreaTerritoriale.findById(area.getCodiceAreaTerritoriale());
            MipRIncontroPreaccAreaTerr tmp = new MipRIncontroPreaccAreaTerr();
            MipRIncontroPreaccAreaTerrId tmpId = new MipRIncontroPreaccAreaTerrId();
            tmpId.setIdIncontroPreaccoglienza(incontroPreaccoglienza.getId());
            tmpId.setCodAreaTerritoriale(areaEntity.getCodiceAreaTerritoriale());
            tmp.setId(tmpId);
            tmp.setIdIncontroPreaccoglienza(incontroPreaccoglienza);
            tmp.setCodAreaTerritoriale(areaEntity);
            tmp.setDataInserim(getNow());
            tmp.setDataAggiorn(getNow());
            tmp.setCodUserAggiorn(body.getCodUserAggiorn());
            tmp.setCodUserInserim(body.getCodUserInserim());
            tmp.persist();
        }
        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "updateIncontroPreaccoglienza",
                body,
                body,
                httpRequest);

        return Response.ok(body).build();
    }

    @Transactional
    @Override
    public Response inviaComunicatoPartecipanti(String oggetto, String corpo, Long idCittadino,
            Long idIncontroPreaccoglienza, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        logger.info("inviaComunicatoPartecipanti(oggetto : " + oggetto + ", corpo : " + corpo + ", idCittadino : "
                + idCittadino + ", idIncontroPreaccoglienza : " + idIncontroPreaccoglienza + ")");
        Map<String, String> map = new HashMap<>();
        if (idCittadino != null) {
            map.put("oggetto", oggetto);
            map.put("corpo", corpo);
            mailUtil.sendEmailCittadino(null, idCittadino, map, httpHeaders);
            return Response.ok().build();
        }
        MipTIncontroPreaccoglienza mipTIncontroPreaccoglienza = mipTIncontroPreaccoglienzaRepository.findById(
                idIncontroPreaccoglienza);
        List<MipTCittadino> mipTCittadinoList = cittadinoIncontroPreaccRepository
                .findCittadinoByIncontroPreaccoglienzaIscritto(mipTIncontroPreaccoglienza);
        logger.info("Invio email a " + mipTCittadinoList.size() + " partecipanti...");

        if (mipTCittadinoList.isEmpty()) {
            return Response.status(Status.NOT_ACCEPTABLE).entity("Nessun partecipante trovato").build();
        }
        mipTCittadinoList.forEach(elem -> {
            map.put("oggetto", oggetto);
            map.put("corpo", corpo);
            mailUtil.sendEmailCittadino(null, elem.getIdCittadino(), map, httpHeaders);
            map.clear();
        });

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "inviaComunicatoPartecipanti",
                mipTIncontroPreaccoglienza,
                null,
                httpRequest);

        return Response.ok().build();
    }

    @Override
    public Response getIncontriPreaccoglienzaForControlloEsistente(Long idLuogo, String dataIncontro,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Optional<MipTIncontroPreaccoglienza> incontroOptional = incontroPreaccoglienzaRepository.find(
                "luogoIncontro.id = :idLuogo AND dataIncontro = :dataIncontro",
                Parameters.with("idLuogo", idLuogo)
                        .and("dataIncontro", Convert.convertStringToDateTime(dataIncontro)))
                .firstResultOptional();
        return incontroOptional.isPresent()
                ? Response.ok(genericMapper.incontroPreaccoglienzaToVo(incontroOptional.get())).build()
                : Response.ok(null).build();
    }

}
