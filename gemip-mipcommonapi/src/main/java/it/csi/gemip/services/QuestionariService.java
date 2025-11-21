package it.csi.gemip.services;

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
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import it.csi.gemip.mipcommonapi.api.dto.Domanda;
import it.csi.gemip.mipcommonapi.api.dto.Questionario;
import it.csi.gemip.mipcommonapi.api.dto.RispostaCompilazione;
import it.csi.gemip.mipcommonapi.api.impl.ParentApiImpl;
import it.csi.gemip.mipcommonapi.integration.entities.MipDDomanda;
import it.csi.gemip.mipcommonapi.integration.entities.MipDRisposta;
import it.csi.gemip.mipcommonapi.integration.entities.MipRCompilazioneQuestionarioFase;
import it.csi.gemip.mipcommonapi.integration.entities.MipRCompilazioneQuestionarioFaseId;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCompilazioneQuestionario;
import it.csi.gemip.mipcommonapi.integration.entities.MipTRispostaCompilazione;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDDomandaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDQuestionarioRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDRispostaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCittadinoFaseQuestionarioRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCittadinoIncontroPreaccRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCompilazioneQuestionarioFaseRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCompilazioneQuestionarioRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTRispostaCompilazioneRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class QuestionariService extends ParentApiImpl {
    @Inject
    GenericMapper genericMapper;
    @Inject
    MipRCittadinoIncontroPreaccRepository mipRCittadinoIncontroPreaccRepository;
    @Inject
    MipRCittadinoFaseQuestionarioRepository mipRCittadinoFaseQuestionarioRepository;
    @Inject
    MipTCompilazioneQuestionarioRepository mipTCompilazioneQuestionarioRepository;
    @Inject
    MipDQuestionarioRepository mipDQuestionarioRepository;
    @Inject
    MipRCompilazioneQuestionarioFaseRepository mipRCompilazioneQuestionarioFaseRepository;
    @Inject
    MipDDomandaRepository mipDDomandaRepository;
    @Inject
    MipDRispostaRepository mipDRispostaRepository;
    @Inject
    MipTRispostaCompilazioneRepository mipTRispostaCompilazioneRepository;

    @ConfigProperty(name = "idQuestionario")
    Long idQuestionario;

    public Integer findFaseCorrente(MipTCittadino mipTCittadino){
        if(mipRIdeaDiImpresaCittadinoRepository.isFase3(mipTCittadino) > 0){
            return mipRCompilazioneQuestionarioFaseRepository.findQuestionarioByFaseAndCittadino(3,mipTCittadino).isPresent() ? null : 3  ;
        }
        if (mipRIdeaDiImpresaCittadinoRepository.isFase2(mipTCittadino) > 0) {
            return mipRCompilazioneQuestionarioFaseRepository.findQuestionarioByFaseAndCittadino(2,mipTCittadino).isPresent() ? null : 2  ;
        }
        if (mipRCittadinoIncontroPreaccRepository.getCountCittadinoFlgCittadinoPresente(mipTCittadino) > 0) {
            return mipRCompilazioneQuestionarioFaseRepository.findQuestionarioByFaseAndCittadino(1,mipTCittadino).isPresent() ? null : 1  ;
        }
        return null;

    }

    public Questionario getQuestionario(MipTCittadino mipTCittadino){
        return getQuestionario(mipTCittadino,findFaseCorrente(mipTCittadino));
    }

    public Questionario getQuestionario(MipTCittadino mipTCittadino,Integer idFase){

        List<MipDDomanda> domande = mipDDomandaRepository.findByFase(idFase);
        List<Domanda> domandeDTO = domande.stream().map(domanda -> {
            List<MipDRisposta> risposte = mipDRispostaRepository.findByDomanda(domanda.getId());
            return genericMapper.domandaEntityToDto(domanda,risposte);
        }).collect(Collectors.toList());

        Questionario questionarioDTO = new Questionario();
        questionarioDTO.setIdQuestionario(idQuestionario);
        questionarioDTO.setDomande(domandeDTO);

        return questionarioDTO;
    }

    @Transactional
    public void salvaRisposte(List<RispostaCompilazione> body,MipTCittadino mipTCittadino,long idQuestionario){
        Date adesso = getNow();
        Optional<MipTCompilazioneQuestionario> mipTCompilazioneQuestionarioOptional = mipTCompilazioneQuestionarioRepository.findByCittadino(mipTCittadino);
        MipTCompilazioneQuestionario mipTCompilazioneQuestionario = new MipTCompilazioneQuestionario();

        if (mipTCompilazioneQuestionarioOptional.isEmpty()) {
            mipTCompilazioneQuestionario.setCittadino(mipTCittadino);
            mipTCompilazioneQuestionario.setQuestionario(mipDQuestionarioRepository.findById(idQuestionario));
            mipTCompilazioneQuestionario.setCodUserInserim(mipTCittadino.getCodiceFiscale());
            mipTCompilazioneQuestionario.setDataInserim(adesso);
            mipTCompilazioneQuestionario.setCodUserAggiorn(mipTCittadino.getCodiceFiscale());
            mipTCompilazioneQuestionario.setDataAggiorn(adesso);
            mipTCompilazioneQuestionarioRepository.persist(mipTCompilazioneQuestionario);
        } else {
            mipTCompilazioneQuestionario = mipTCompilazioneQuestionarioOptional.get();
        }

        MipRCompilazioneQuestionarioFaseId mipRCompilazioneQuestionarioFaseId = new MipRCompilazioneQuestionarioFaseId();
        mipRCompilazioneQuestionarioFaseId.setIdFaseQuestionario(BigDecimal.valueOf(findFaseCorrente(mipTCittadino)));
        mipRCompilazioneQuestionarioFaseId.getIdCompilazioneQuestionario(mipTCompilazioneQuestionario.getId());

        MipRCompilazioneQuestionarioFase mipRCompilazioneQuestionarioFase = new MipRCompilazioneQuestionarioFase();
        mipRCompilazioneQuestionarioFase.setId(mipRCompilazioneQuestionarioFaseId);
        mipRCompilazioneQuestionarioFase.setIdCompilazioneQuestionario(mipTCompilazioneQuestionario);
        mipRCompilazioneQuestionarioFase.setDCompilazione(adesso);
        mipRCompilazioneQuestionarioFase.setCodUserInserim(mipTCittadino.getCodiceFiscale());
        mipRCompilazioneQuestionarioFase.setDataInserim(adesso);
        mipRCompilazioneQuestionarioFase.setCodUserAggiorn(mipTCittadino.getCodiceFiscale());
        mipRCompilazioneQuestionarioFase.setDataAggiorn(adesso);

        mipRCompilazioneQuestionarioFaseRepository.persist(mipRCompilazioneQuestionarioFase);

        MipTCompilazioneQuestionario finalMipTCompilazioneQuestionario = mipTCompilazioneQuestionario;


        mipTRispostaCompilazioneRepository.persist(body.stream().map(elem -> {
            MipTRispostaCompilazione mipTRispostaCompilazione = new MipTRispostaCompilazione();
            mipTRispostaCompilazione.setIdDomanda(mipDDomandaRepository.findById(elem.getIdDomanda()));
            if (elem.getIdRispostaScelta() != null)
                mipTRispostaCompilazione.setIdRisposta(mipDRispostaRepository.findById(elem.getIdRispostaScelta()));
            if (elem.getRispostaLibera() != null) mipTRispostaCompilazione.setRispostaLibera(elem.getRispostaLibera());
            mipTRispostaCompilazione.setDataInserim(adesso);
            mipTRispostaCompilazione.setCodUserInserim(mipTCittadino.getCodiceFiscale());
            mipTRispostaCompilazione.setDataAggiorn(adesso);
            mipTRispostaCompilazione.setCodUserAggiorn(mipTCittadino.getCodiceFiscale());
            mipTRispostaCompilazione.setIdCompilazioneQuestionario(finalMipTCompilazioneQuestionario);
            return mipTRispostaCompilazione;
        }));
    }
}
