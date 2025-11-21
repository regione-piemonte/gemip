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
import java.util.Date;
import java.util.List;

import io.quarkus.cache.CacheResult;
import io.quarkus.panache.common.Sort;
import it.csi.gemip.mipcommonapi.api.dto.AnagraficaCittadino;
import it.csi.gemip.mipcommonapi.api.dto.CondizioneFamiliare;
import it.csi.gemip.mipcommonapi.api.dto.CondizioneOccupazionale;
import it.csi.gemip.mipcommonapi.api.dto.SvantaggioAbitativo;
import it.csi.gemip.mipcommonapi.api.dto.TitoloDiStudio;
import it.csi.gemip.mipcommonapi.integration.entities.MipDCondizioneOccupazionale;
import it.csi.gemip.mipcommonapi.integration.entities.MipDSvantaggioAbitativo;
import it.csi.gemip.mipcommonapi.integration.entities.MipTAnagraficaCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTAnagraficaCittadinoExten;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDCondizioneFamiliareRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDCondizioneOccupazionaleRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSvantaggioAbitativoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDTitoloDiStudioRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTAnagraficaCittadinoExtenRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTAnagraficaCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCittadinoRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.services.SpidService;

import it.csi.gemip.utils.LogAuditUtil;
import it.csi.gemip.utils.cache.MethodNameKeyGenerator;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class AnagraficheApiImpl extends ParentApiImpl implements it.csi.gemip.mipcommonapi.api.AnagraficheApi {
        @Inject
        GenericMapper genericMapper;

        @Inject
        MipDCondizioneOccupazionaleRepository condizioneOccupazionaleRepository;

        @Inject
        MipDTitoloDiStudioRepository titoloDiStudioRepository;

        @Inject
        MipDSvantaggioAbitativoRepository svantaggioAbitativoRepository;

        @Inject
        MipTAnagraficaCittadinoRepository mipTAnagraficaCittadinoRepository;

        @Inject
        MipTCittadinoRepository mipTCittadinoRepository;

        @Inject
        MipTAnagraficaCittadinoExtenRepository anagraficaCittadinoExtenRepository;

        @Inject
        MipDCondizioneFamiliareRepository condizioneFamiliareRepository;

        @Inject
        SpidService spidService;
        @Inject
        LogAuditUtil logAuditUtil;

        @Override
        @Transactional
        public Response getAnafraficaCittadinoPerId(Long id, SecurityContext securityContext, HttpHeaders httpHeaders,
                        HttpServletRequest httpRequest) {
                Date adesso = getNow();
                AnagraficaCittadino anagraficaCittadino = genericMapper
                                .anagraficaCittadinoEntyToVo(mipTAnagraficaCittadinoRepository.findById(id));
                MipTAnagraficaCittadinoExten anagraficaCittadinoExten = anagraficaCittadinoExtenRepository.findById(id);
                if (anagraficaCittadinoExten != null) {
                        if (anagraficaCittadinoExten.getCondizioneOccupazionale() != null) {
                                String codiceCondizioneOccupazionale = anagraficaCittadinoExten
                                                .getCondizioneOccupazionale();
                                CondizioneOccupazionale condizioneOccupazionale = genericMapper
                                                .condizioneOccupazionaleEntyToVo(condizioneOccupazionaleRepository
                                                                .find("codiceCondizioneOccupazionale",
                                                                                codiceCondizioneOccupazionale)
                                                                .firstResult());
                                anagraficaCittadino.setCodiceCondizioneOccupazionale(condizioneOccupazionale);
                        }
                        if (anagraficaCittadinoExten.getSvantaggioAbitativo() != null) {
                                Long idSvantaggioAbitativo = (new BigDecimal(
                                                anagraficaCittadinoExten.getSvantaggioAbitativo()))
                                                .longValue();
                                SvantaggioAbitativo svantaggioAbitativo = genericMapper.svantaggioAbitativoEntyToVo(
                                                svantaggioAbitativoRepository.find("id", idSvantaggioAbitativo)
                                                                .firstResult());
                                anagraficaCittadino.setSvantaggioAbitativo(svantaggioAbitativo);
                        }
                        if (anagraficaCittadinoExten.getCondizioneFamiliare() != null
                                        && !anagraficaCittadinoExten.getCondizioneFamiliare().isEmpty()) {
                                Long idCondizioneFamiliare = (new BigDecimal(
                                                anagraficaCittadinoExten.getCondizioneFamiliare()))
                                                .longValue();
                                CondizioneFamiliare condizioneFamiliare = genericMapper.condizioneFamiliareEntyToVo(
                                                condizioneFamiliareRepository.find("id", idCondizioneFamiliare)
                                                                .firstResult());
                                anagraficaCittadino.setCondizioneFamiliare(condizioneFamiliare);
                        }
                        if (anagraficaCittadinoExten.getCondizioneOccupazionaleAltro() != null) {
                                String condiString = anagraficaCittadinoExten.getCondizioneOccupazionaleAltro();
                                anagraficaCittadino.setCondizioneOccupazionaleAltro(condiString);
                        }
                        logAuditUtil.insertLogAudit(
                                        adesso,
                                        httpHeaders,
                                        "getAnafraficaCittadinoPerId",
                                        anagraficaCittadino,
                                        null,
                                        httpRequest);
                }

                return Response.ok(anagraficaCittadino).build();
        }

        @Override
        @CacheResult(cacheName = "getCondizioneOccupazionale", keyGenerator = MethodNameKeyGenerator.class)
        public Response getCondizioneOccupazionale(SecurityContext securityContext, HttpHeaders httpHeaders,
                        HttpServletRequest httpRequest) {
                List<CondizioneOccupazionale> condizioneOccupazionaleList = condizioneOccupazionaleRepository
                                .findAll(Sort.by("descrizioneCondizioneOccupazionale")).list()
                                .stream().map(from -> genericMapper.condizioneOccupazionaleEntyToVo(from))
                                .toList();
                return Response.ok(condizioneOccupazionaleList).build();
        }

        @Override
        @CacheResult(cacheName = "getSvantaggiAbitativo", keyGenerator = MethodNameKeyGenerator.class)
        public Response getSvantaggiAbitativo(SecurityContext securityContext, HttpHeaders httpHeaders,
                        HttpServletRequest httpRequest) {
                List<SvantaggioAbitativo> svantaggioAbitativoList = svantaggioAbitativoRepository.findAll().list()
                                .stream().map(from -> genericMapper.svantaggioAbitativoEntyToVo(from))
                                .toList();
                return Response.ok(svantaggioAbitativoList).build();
        }

        @Override
        @CacheResult(cacheName = "getCondizioniFamiliare", keyGenerator = MethodNameKeyGenerator.class)
        public Response getCondizioniFamiliare(SecurityContext securityContext, HttpHeaders httpHeaders,
                        HttpServletRequest httpRequest) {
                List<CondizioneFamiliare> condizioneFamiliareList = condizioneFamiliareRepository.findAll().list()
                                .stream().map(from -> genericMapper.condizioneFamiliareEntyToVo(from))
                                .toList();
                return Response.ok(condizioneFamiliareList).build();
        }

        @Override
        @CacheResult(cacheName = "getTitoliStudio", keyGenerator = MethodNameKeyGenerator.class)
        public Response getTitoliStudio(SecurityContext securityContext, HttpHeaders httpHeaders,
                        HttpServletRequest httpRequest) {
                List<TitoloDiStudio> titoloDiStudioList = titoloDiStudioRepository.findAll().list()
                                .stream().map(from -> genericMapper.titoloStudioEntyToVo(from))
                                .toList();
                return Response.ok(titoloDiStudioList).build();
        }

        @Transactional
        @Override
        public Response insertAnagraficaCittadino(AnagraficaCittadino body, SecurityContext securityContext,
                        HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
                Date adesso = getNow();
                MipTAnagraficaCittadino mipTAnagraficaCittadino = genericMapper.anaGraficaCittadinoVoToEnty(body);
                MipTCittadino mipTCittadino = mipTCittadinoRepository.findById(body.getCittadino().getIdCittadino());
                mipTAnagraficaCittadino.setMipTCittadino(mipTCittadino);
                mipTAnagraficaCittadino.setCodUserInserim(body.getCodUserInserim());
                mipTAnagraficaCittadino.setCodUserAggiorn(body.getCodUserInserim());
                mipTAnagraficaCittadino.setDataInserim(adesso);
                mipTAnagraficaCittadino.setDataAggiorn(adesso);
                mipTAnagraficaCittadino.setNumDocumIdentita(body.getNumeroDocumentoIdentita());
                mipTAnagraficaCittadino.setEnteRilascioDocumIdentita(body.getDocumentoRilasciatoDa());
                mipTAnagraficaCittadino.setDataScadDocumIdentita(body.getDataScadenzaDocumento());
                mipTAnagraficaCittadino.setDescCittadinanzaAltro(body.getDescCittadinanzaAltro());
                mipTAnagraficaCittadino.setSesso(body.getSesso());
                mipTAnagraficaCittadino.setEnteRilascioPermessoDiSoggiorno(body.getPermessoDiSoggiornoRilasciatoDa());
                mipTAnagraficaCittadino.setDescrMotivoPermessoDiSoggiorno(body.getDescrMotivoPermessoDiSoggiorno());
                mipTAnagraficaCittadino.setDataScadPermessoSoggiorno(body.getDataScadPermessoSoggiorno());
                mipTAnagraficaCittadino.setTipoPermessoDiSoggiorno(body.getTipoPermessoDiSoggiorno());

                MipTAnagraficaCittadino.persist(mipTAnagraficaCittadino);

                String codiceCondizioneOccupazionale = body.getCodiceCondizioneOccupazionale()
                                .getCodiceCondizioneOccupazionale();
                Long idSvantaggioAbitativo = body.getSvantaggioAbitativo().getId().longValue();
                MipDCondizioneOccupazionale condizioneOccupazionale = condizioneOccupazionaleRepository
                                .find("codiceCondizioneOccupazionale", codiceCondizioneOccupazionale).firstResult();
                MipDSvantaggioAbitativo svantaggioAbitativo = svantaggioAbitativoRepository
                                .findById(idSvantaggioAbitativo);

                MipTAnagraficaCittadinoExten mipTAnagraficaCittadinoExtenInser = new MipTAnagraficaCittadinoExten();
                mipTAnagraficaCittadinoExtenInser.setId(body.getCittadino().getIdCittadino());
                mipTAnagraficaCittadinoExtenInser.setDataInserim(adesso);
                mipTAnagraficaCittadinoExtenInser
                                .setCondizioneOccupazionale(condizioneOccupazionale.getCodiceCondizioneOccupazionale());
                mipTAnagraficaCittadinoExtenInser.setSvantaggioAbitativo(svantaggioAbitativo.getId().toString());
                mipTAnagraficaCittadinoExtenInser
                                .setCondizioneOccupazionaleAltro(body.getCondizioneOccupazionaleAltro());
                mipTAnagraficaCittadinoExtenInser.setDataAggiorn(adesso);
                mipTAnagraficaCittadinoExtenInser.setCodUserAggiorn(body.getCodUserAggiorn());
                mipTAnagraficaCittadinoExtenInser.setCodUserInserim(body.getCodUserInserim());

                anagraficaCittadinoExtenRepository.persist(mipTAnagraficaCittadinoExtenInser);

                logAuditUtil.insertLogAudit(
                                adesso,
                                httpHeaders,
                                "insertAnagraficaCittadino",
                                mipTAnagraficaCittadino,
                                body,
                                httpRequest);

                AnagraficaCittadino anagraficaCittadinoVo = genericMapper
                                .anagraficaCittadinoEntyToVo(mipTAnagraficaCittadino);
                anagraficaCittadinoVo.setCodiceCondizioneOccupazionale(body.getCodiceCondizioneOccupazionale());
                anagraficaCittadinoVo.setSvantaggioAbitativo(body.getSvantaggioAbitativo());
                return Response.ok(anagraficaCittadinoVo).build();
        }

        @Transactional
        @Override
        public Response updateAnagraficaCittadino(AnagraficaCittadino body, SecurityContext securityContext,
                        HttpHeaders httpHeaders, HttpServletRequest httpRequest) {

                Date adesso = getNow();
                MipTAnagraficaCittadino mipTAnagraficaCittadino = genericMapper.anaGraficaCittadinoVoToEnty(body);

                mipTAnagraficaCittadino.setCodUserAggiorn(body.getCodUserInserim());
                mipTAnagraficaCittadino.setDataInserim(body.getDataInserim());
                mipTAnagraficaCittadino.setDataAggiorn(adesso);
                mipTAnagraficaCittadinoRepository.getEntityManager().merge(mipTAnagraficaCittadino).persistAndFlush();

                MipTAnagraficaCittadinoExten mipTAnagraficaCittadinoExten = anagraficaCittadinoExtenRepository
                                .findById(body.getCittadino().getIdCittadino());
                anagraficaCittadinoExtenRepository.getEntityManager().clear();
                String codiceCondizioneOccupazionale = body.getCodiceCondizioneOccupazionale()
                                .getCodiceCondizioneOccupazionale();
                Long idSvantaggioAbitativo = body.getSvantaggioAbitativo().getId().longValue();
                MipDCondizioneOccupazionale condizioneOccupazionale = condizioneOccupazionaleRepository
                                .find("codiceCondizioneOccupazionale", codiceCondizioneOccupazionale).firstResult();
                MipDSvantaggioAbitativo svantaggioAbitativo = svantaggioAbitativoRepository
                                .findById(idSvantaggioAbitativo);

                if (mipTAnagraficaCittadinoExten == null) {
                        MipTAnagraficaCittadinoExten mipTAnagraficaCittadinoExtenInser = new MipTAnagraficaCittadinoExten();
                        mipTAnagraficaCittadinoExtenInser.setId(body.getCittadino().getIdCittadino());
                        mipTAnagraficaCittadinoExtenInser.setDataInserim(adesso);
                        mipTAnagraficaCittadinoExtenInser
                                        .setCondizioneOccupazionale(
                                                        condizioneOccupazionale.getCodiceCondizioneOccupazionale());
                        mipTAnagraficaCittadinoExtenInser
                                        .setSvantaggioAbitativo(svantaggioAbitativo.getId().toString());
                        mipTAnagraficaCittadinoExtenInser.setDataAggiorn(adesso);
                        mipTAnagraficaCittadinoExtenInser.setCodUserAggiorn(body.getCodUserAggiorn());
                        mipTAnagraficaCittadinoExtenInser.setCodUserInserim(body.getCodUserInserim());
                        mipTAnagraficaCittadinoExtenInser
                                        .setCondizioneOccupazionaleAltro(body.getCondizioneOccupazionaleAltro());
                        anagraficaCittadinoExtenRepository.persist(mipTAnagraficaCittadinoExtenInser);
                } else {
                        mipTAnagraficaCittadinoExten
                                        .setCondizioneOccupazionale(
                                                        condizioneOccupazionale.getCodiceCondizioneOccupazionale());
                        mipTAnagraficaCittadinoExten.setSvantaggioAbitativo(svantaggioAbitativo.getId().toString());
                        mipTAnagraficaCittadinoExten
                                        .setCondizioneOccupazionaleAltro(body.getCondizioneOccupazionaleAltro());
                        mipTAnagraficaCittadinoExten.setDataAggiorn(adesso);
                        mipTAnagraficaCittadinoExten.setCodUserAggiorn(body.getCodUserAggiorn());
                        anagraficaCittadinoExtenRepository.persist(mipTAnagraficaCittadinoExten);
                }

                AnagraficaCittadino anagraficaCittadinoVo = genericMapper
                                .anagraficaCittadinoEntyToVo(mipTAnagraficaCittadino);

                logAuditUtil.insertLogAudit(
                                adesso,
                                httpHeaders,
                                "updateAnagraficaCittadino",
                                mipTAnagraficaCittadino,
                                body,
                                httpRequest);

                anagraficaCittadinoVo.setCodiceCondizioneOccupazionale(body.getCodiceCondizioneOccupazionale());
                anagraficaCittadinoVo.setSvantaggioAbitativo(body.getSvantaggioAbitativo());
                return Response.ok(anagraficaCittadinoVo).build();
        }

        @Override
        @Transactional
        public Response getAnagraficaCittadinoSpid(SecurityContext securityContext, HttpHeaders httpHeaders,
                        HttpServletRequest httpRequest) {
                Date adesso = getNow();
                MipTAnagraficaCittadino anagrafica = spidService.creaAnagraficaDaSpid(httpHeaders);
                AnagraficaCittadino ana = genericMapper.anagraficaCittadinoEntyToVo(anagrafica);

                logAuditUtil.insertLogAudit(
                                adesso,
                                httpHeaders,
                                "getAnagraficaCittadinoSpid",
                                ana,
                                null,
                                httpRequest);

                return Response.ok(ana).build();
        }
}
