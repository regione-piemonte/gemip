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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.mailer.Mailer;
import it.csi.gemip.mipcommonapi.api.CittadiniApi;
import it.csi.gemip.mipcommonapi.api.dto.AnagraficaCittadino;
import it.csi.gemip.mipcommonapi.api.dto.AnagraficaCittadinoRicerca;
import it.csi.gemip.mipcommonapi.api.dto.Cittadino;
import it.csi.gemip.mipcommonapi.api.dto.CondizioneFamiliare;
import it.csi.gemip.mipcommonapi.api.dto.CondizioneOccupazionale;
import it.csi.gemip.mipcommonapi.api.dto.DashboardCittadino;
import it.csi.gemip.mipcommonapi.api.dto.SvantaggioAbitativo;
import it.csi.gemip.mipcommonapi.integration.entities.MipDStatoIdeaDiImpresa;
import it.csi.gemip.mipcommonapi.integration.entities.MipRCittadinoIncontroPreacc;
import it.csi.gemip.mipcommonapi.integration.entities.MipRIdeaDiImpresaCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTAnagraficaCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTAnagraficaCittadinoExten;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIdeaDiImpresa;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDCondizioneFamiliareRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDCondizioneOccupazionaleRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSvantaggioAbitativoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCittadinoIncontroPreaccRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTAnagraficaCittadinoExtenRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTAnagraficaCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCompilazioneQuestionarioRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIdeaDiImpresaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIncontroPreaccoglienzaRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.Constants.STATO_IDEA_IMPRESA;
import it.csi.gemip.utils.LogAuditUtil;
import it.csi.gemip.utils.MailUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;

public class CittadiniApiImpl extends ParentApiImpl implements CittadiniApi {
    @Inject
    GenericMapper genericMapper;

    @Inject
    Mailer mailer;

    @Inject
    Logger log;

    @Inject
    MipTCittadinoRepository mipTCittadinoRepository;

    @Inject
    MipTAnagraficaCittadinoRepository mipTAnagraficaCittadinoRepository;

    @Inject
    MipRCittadinoIncontroPreaccRepository mipRCittadinoIncontroPreaccRepository;

    @Inject
    MipTCompilazioneQuestionarioRepository mipTCompilazioneQuestionarioRepository;

    @Inject
    MipTIdeaDiImpresaRepository mipTIdeaDiImpresaRepository;

    @Inject
    MipTAnagraficaCittadinoExtenRepository anagraficaCittadinoExtenRepository;

    @Inject
    MipDCondizioneOccupazionaleRepository condizioneOccupazionaleRepository;

    @Inject
    MipDSvantaggioAbitativoRepository svantaggioAbitativoRepository;
    @Inject
    MipDCondizioneFamiliareRepository condizioneFamiliareRepository;
    @Inject
    MipTIncontroPreaccoglienzaRepository mipTIncontroPreaccoglienzaRepository;

    @Context
    UriInfo uriInfo;
    @Inject
    LogAuditUtil logAuditUtil;
    @Inject
    MailUtil mailUtil;

    @Override
    public Response getCittadinoPerIdeaImpresa(Long idIdeaDiImpresa, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<Cittadino> cittadinoList = mipTCittadinoRepository.getCittadinoPerIdeaImpresa(idIdeaDiImpresa)
                .stream().map(from -> genericMapper.cittadinoEntyToVo(from))
                .toList();
        return Response.ok(cittadinoList).build();
    }

    @Transactional
    @Override
    public Response getCittadinoPerParametri(String nome, String cognome, String email, String codiceFiscale,
            String codAreaTerritoriale, String dataInseritoDa, String dataInseritoA, Long idOperatore,
            Long idSoggettoAttuatore, Integer pageIndex, Integer pageSize, String orderBy, String sortDirection,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        Map<String, Object> params = new HashMap<>();
        if (idOperatore != null) {
            params.put("idOperatore", idOperatore);
        }
        if (idSoggettoAttuatore != null) {
            params.put("idSoggettoAttuatore", idSoggettoAttuatore);
        }
        if (codAreaTerritoriale != null) {
            params.put("codAreaTerritoriale", codAreaTerritoriale);
        }
        if (dataInseritoDa != null) {
            params.put("dataInseritoDa", dataInseritoDa);
        }
        if (dataInseritoA != null) {
            params.put("dataInseritoA", dataInseritoA);
        }
        if (nome != null) {
            nome = nome.trim();
            params.put("nome", "%" + nome.toLowerCase() + "%");
        }
        if (cognome != null) {
            cognome = cognome.trim();
            params.put("cognome", "%" + cognome.toLowerCase() + "%");
        }
        if (email != null) {
            email = email.trim();
            params.put("email", "%" + email.toLowerCase() + "%");
        }
        if (codiceFiscale != null) {
            codiceFiscale = codiceFiscale.trim().replace(" ", "");
            params.put("codiceFiscale", "%" + codiceFiscale.toLowerCase() + "%");
        }

        orderBy = orderBy != null ? orderBy : "codiceFiscale";
        sortDirection = sortDirection != null ? sortDirection : "asc";

        boolean datisensibili = false;

        PanacheQuery<MipTAnagraficaCittadino> mipTAnagraficaCittadinoPanacheQuery = mipTAnagraficaCittadinoRepository
                .getCittadinoPerParametri(params, orderBy, sortDirection);

        List<MipTAnagraficaCittadino> mipTAnagraficaCittadinoList = mipTAnagraficaCittadinoPanacheQuery
                .page(pageIndex, pageSize).stream().toList();

        List<AnagraficaCittadino> cittadinoList = new ArrayList<>();

        for (MipTAnagraficaCittadino mipTAnagraficaCittadino : mipTAnagraficaCittadinoList) {
            AnagraficaCittadino anagraficaCittadino = genericMapper
                    .anagraficaCittadinoEntyToVo(mipTAnagraficaCittadino);
            MipTAnagraficaCittadinoExten anagraficaCittadinoExten = anagraficaCittadinoExtenRepository
                    .findById(mipTAnagraficaCittadino.getId());
            anagraficaCittadino.setFlgIdeaImpresa(
                    mipTIdeaDiImpresaRepository.getIdeaDiImpresaByIdCittadino(anagraficaCittadino.getId()) != null);
            if (anagraficaCittadinoExten != null) {

                if (anagraficaCittadinoExten.getCondizioneOccupazionale() != null) {
                    String codiceCondizioneOccupazionale = anagraficaCittadinoExten.getCondizioneOccupazionale();
                    CondizioneOccupazionale condizioneOccupazionale = genericMapper
                            .condizioneOccupazionaleEntyToVo(condizioneOccupazionaleRepository
                                    .find("codiceCondizioneOccupazionale", codiceCondizioneOccupazionale)
                                    .firstResult());
                    anagraficaCittadino.setCodiceCondizioneOccupazionale(condizioneOccupazionale);
                    datisensibili = true;
                }

                if (anagraficaCittadinoExten.getSvantaggioAbitativo() != null) {
                    Long idSvantaggioAbitativo = (new BigDecimal(anagraficaCittadinoExten.getSvantaggioAbitativo()))
                            .longValue();
                    SvantaggioAbitativo svantaggioAbitativo = genericMapper.svantaggioAbitativoEntyToVo(
                            svantaggioAbitativoRepository.find("id", idSvantaggioAbitativo).firstResult());
                    anagraficaCittadino.setSvantaggioAbitativo(svantaggioAbitativo);
                    datisensibili = true;
                }
                if (anagraficaCittadinoExten.getCondizioneFamiliare() != null
                        && !(anagraficaCittadinoExten.getCondizioneFamiliare().isEmpty())) {
                    Long idCondizioneFamiliare = (new BigDecimal(anagraficaCittadinoExten.getCondizioneFamiliare()))
                            .longValue();
                    CondizioneFamiliare condizioneFamiliare = genericMapper.condizioneFamiliareEntyToVo(
                            condizioneFamiliareRepository.find("id", idCondizioneFamiliare).firstResult());
                    anagraficaCittadino.setCondizioneFamiliare(condizioneFamiliare);
                    datisensibili = true;
                }
                if (anagraficaCittadinoExten.getCondizioneOccupazionaleAltro() != null) {
                    String condiString = anagraficaCittadinoExten.getCondizioneOccupazionaleAltro();
                    anagraficaCittadino.setCondizioneOccupazionaleAltro(condiString);
                    datisensibili = true;
                }

            }
            cittadinoList.add(anagraficaCittadino);
        }
        if (datisensibili) {
            logAuditUtil.insertLogAudit(adesso,
                    httpHeaders,
                    "getCittadinoPerParametri",
                    cittadinoList.stream().map(elem -> elem.getCittadino().getIdCittadino())
                            .collect(Collectors.toList()),
                    null,
                    httpRequest);
        }
        AnagraficaCittadinoRicerca response = new AnagraficaCittadinoRicerca();
        response.setAnagrafiche(cittadinoList);
        response.setTotRis(mipTAnagraficaCittadinoPanacheQuery.count());
        return Response.ok(response).build();
    }

    @Transactional
    @Override
    public Response getDashboardCittadino(Cittadino body, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        Date adesso = getNow();
        DashboardCittadino dashboardCittadino = new DashboardCittadino();

        MipTCittadino mipTCittadino = mipTCittadinoRepository.find("codiceFiscale", body.getCodiceFiscale())
                .firstResult();
        if (mipTCittadino == null) {
            mipTCittadino = genericMapper.cittadinoVoToEnty(body);
            mipTCittadino.setIdCittadino(null);
            mipTCittadino.setCodUserInserim(body.getCodiceFiscale());
            mipTCittadino.setCodUserAggiorn(body.getCodiceFiscale());
            mipTCittadino.setDataAggiorn(new Date());
            mipTCittadino.setDataInserim(new Date());
            mipTCittadino.persist();
            dashboardCittadino.setCittadino(genericMapper.cittadinoEntyToVo(mipTCittadino));
            logAuditUtil.insertLogAudit(adesso,
                    httpHeaders,
                    "getDashboardCittadino",
                    mipTCittadino,
                    body,
                    httpRequest);
            return Response.accepted(dashboardCittadino).build();
        }

        dashboardCittadino.setCittadino(genericMapper.cittadinoEntyToVo(mipTCittadino));
        if (mipTAnagraficaCittadinoRepository.findById(mipTCittadino.getIdCittadino()) != null) {
            dashboardCittadino.setIdAnagraficaCittadino(mipTCittadino.getIdCittadino());
        }

        MipRCittadinoIncontroPreacc mipRCittadinoIncontroPreacc = mipRCittadinoIncontroPreaccRepository
                .find("cittadino.idCittadino", mipTCittadino.getIdCittadino()).firstResult();
        if (mipRCittadinoIncontroPreacc != null) {
            dashboardCittadino.setIdCittadinoIncontroPreacc(mipRCittadinoIncontroPreacc.getId());
            dashboardCittadino.setDisdicimentoIncontro(mipRCittadinoIncontroPreacc.getDAnnullamento() != null);
            dashboardCittadino
                    .setPartecipzioneIncontro("S".equals(mipRCittadinoIncontroPreacc.getFlgCittadinoPresente()));
            dashboardCittadino.setAssenzaIncontro("N".equals(mipRCittadinoIncontroPreacc.getFlgCittadinoPresente()));
        }

        MipRIdeaDiImpresaCittadino mipRIdeaDiImpresaCittadino = mipRIdeaDiImpresaCittadinoRepository
                .find("id.idCittadino", mipTCittadino.getIdCittadino()).firstResult();
        if (mipRIdeaDiImpresaCittadino != null) {
            dashboardCittadino.setIdIdeaDiImpresa(mipRIdeaDiImpresaCittadino.getId().getIdIdeaDiImpresa());

            MipTIdeaDiImpresa mipTIdeaDiImpresa = mipTIdeaDiImpresaRepository
                    .findById(mipRIdeaDiImpresaCittadino.getId().getIdIdeaDiImpresa());
            dashboardCittadino.setIdTuttor(mipTIdeaDiImpresa.getIdTutor());
        }

        return Response.accepted(dashboardCittadino).build();

    }

    @Transactional
    @Override
    public Response updateIdeaImpresaInviata(DashboardCittadino body, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipTIdeaDiImpresa ideaDiImpresa = mipTIdeaDiImpresaRepository.findById(body.getIdIdeaDiImpresa());
        MipDStatoIdeaDiImpresa statoIdeaDiImpresa = new MipDStatoIdeaDiImpresa();
        statoIdeaDiImpresa.setId(6L);
        ideaDiImpresa.setDataAggiorn(adesso);
        ideaDiImpresa.setCodUserAggiorn(body.getCittadino().getCodiceFiscale());
        ideaDiImpresa.setStatoIdeaDiImpresa(statoIdeaDiImpresa);
        ideaDiImpresa.setDataCambioStato(getNow());

        mipTIdeaDiImpresaRepository.getEntityManager().merge(ideaDiImpresa);

        MipTIncontroPreaccoglienza incontro = mipTIncontroPreaccoglienzaRepository
                .findIncontroPreaccoglienzaCittadino(body.getCittadino().getIdCittadino());
        if ("S".equals(incontro.getFlgIncontroErogatoDaRemoto())) {
            mailUtil.sendEmailCittadino(MailUtil.COD_MAIL_10_POST_REGISTRAZIONE_ONLINE,
                    body.getCittadino().getIdCittadino(), null, httpHeaders);
        } else {
            mailUtil.sendEmailCittadino(MailUtil.COD_MAIL_10_POST_REGISTRAZIONE_PRESENZA,
                    body.getCittadino().getIdCittadino(), null, httpHeaders);
        }

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "updateIdeaImpresaInviata",
                ideaDiImpresa,
                body,
                httpRequest);

        return Response.ok(body).build();
    }

    @Transactional
    @Override
    public Response updateIdeaImpresaConclusa(DashboardCittadino body, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipTIdeaDiImpresa ideaDiImpresa = mipTIdeaDiImpresaRepository.findById(body.getIdIdeaDiImpresa());
        MipDStatoIdeaDiImpresa statoIdeaDiImpresa = new MipDStatoIdeaDiImpresa();
        statoIdeaDiImpresa.setId(STATO_IDEA_IMPRESA.CONCLUSA);
        ideaDiImpresa.setStatoIdeaDiImpresa(statoIdeaDiImpresa);
        ideaDiImpresa.setDataCambioStato(getNow());
        mipTIdeaDiImpresaRepository.getEntityManager().merge(ideaDiImpresa);

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "updateIdeaImpresaConclusa",
                ideaDiImpresa,
                body,
                httpRequest);

        return Response.ok(body).build();
    }
}
