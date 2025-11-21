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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.csi.gemip.mipcommonapi.api.dto.DomandeRisposte;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresaRicercaExtended;
import it.csi.gemip.mipcommonapi.api.dto.ReportQuestionario;
import it.csi.gemip.mipcommonapi.api.dto.ReportQuestionarioRigheReportistica;
import it.csi.gemip.mipcommonapi.api.impl.ParentApiImpl;
import it.csi.gemip.mipcommonapi.integration.entities.ExtGmopDAreaTerritoriale;
import it.csi.gemip.mipcommonapi.integration.entities.MipDDomanda;
import it.csi.gemip.mipcommonapi.integration.entities.MipDSoggettoAttuatore;
import it.csi.gemip.mipcommonapi.integration.entities.MipDStatoIdeaDiImpresa;
import it.csi.gemip.mipcommonapi.integration.entities.MipRCompilazioneQuestionarioFase;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCompilazioneQuestionario;
import it.csi.gemip.mipcommonapi.integration.entities.MipTRispostaCompilazione;
import it.csi.gemip.mipcommonapi.integration.repositories.ExtGmopDAreaTerritorialeRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDCondizioneOccupazionaleRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDDomandaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSoggettoAttuatoreRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDStatoIdeaDiImpresaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSvantaggioAbitativoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCittadinoIncontroPreaccRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCompilazioneQuestionarioFaseRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTAnagraficaCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCompilazioneQuestionarioRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIdeaDiImpresaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIncontroPreaccoglienzaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTRispostaCompilazioneRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.ReportIdeeImpresaRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.model.ExcelReportisticaQuestionari;
import it.csi.gemip.model.ReportIdeaImpresaBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ReportisticaService extends ParentApiImpl {
    @Inject
    GenericMapper genericMapper;
    
    @Inject
    MipTIdeaDiImpresaRepository mipTIdeaDiImpresaRepository;
    @Inject
    MipTCittadinoRepository mipTCittadinoRepository;
    @Inject
    MipTAnagraficaCittadinoRepository mipTAnagraficaCittadinoRepository;
    @Inject
    MipDCondizioneOccupazionaleRepository condizioneOccupazionaleRepository;
    @Inject
    MipDSvantaggioAbitativoRepository svantaggioAbitativoRepository;
    @Inject
    MipRCittadinoIncontroPreaccRepository cittadinoIncontroPreaccRepository;

    @Inject
    MipDSoggettoAttuatoreRepository soggettoAttuatoreRepository;
    @Inject
    ExtGmopDAreaTerritorialeRepository areaTerritorialeRepository;

    @Inject
    ReportIdeeImpresaRepository reportRepository;

    @Inject
    MipTCompilazioneQuestionarioRepository compilazioneQuestionarioRepository;

    @Inject
    MipTRispostaCompilazioneRepository rispostaCompilazioneRepository;
    @Inject
    MipDDomandaRepository mipDDomandaRepository;

    @Inject
    MipRCompilazioneQuestionarioFaseRepository compilazioneQuestionarioFaseRepository;

    @Inject
    MipDStatoIdeaDiImpresaRepository statoIdeaDiImpresaRepository;
    @Inject
    MipTIncontroPreaccoglienzaRepository mipTIncontroPreaccoglienzaRepository;

    public List<IdeaDiImpresaRicercaExtended> getIdeaDiImpresaRicercaExtendeds(Date dataDa, Date dataA,
                                                                               Long idSoggettoAttuatore,
                                                                               String idCodAreaTerritoriale,
                                                                               Long idStatoIdea)
    {
        List<ReportIdeaImpresaBean> results = reportRepository.getIdeaDiImpresaRicercaExtendeds(dataDa, dataA, idSoggettoAttuatore,idCodAreaTerritoriale, idStatoIdea);
        List<IdeaDiImpresaRicercaExtended> mapped =  genericMapper.reportIdeaImpresaEntityToVo(results);
        return mapped;
    }

    public ReportQuestionario makeReportQuestionario(Long idFase, Date dataDa, Date dataA, Long idSoggettoAttuatore, String idCodAreaTerritoriale) {
        ReportQuestionario reportQuestionario = new ReportQuestionario();
        List<ReportQuestionarioRigheReportistica> righeReportistica = new ArrayList<>();

        Map <String,Object> QuestParams = new HashMap<>();

        if(idFase == null) throw new IllegalArgumentException("idFase required");

        QuestParams.put("idFase",idFase);
        if(dataDa != null) QuestParams.put("dataDa",dataDa);
        if(dataA != null) QuestParams.put("dataA",dataA);
        if(idSoggettoAttuatore != null) QuestParams.put("idSoggettoAttuatore",idSoggettoAttuatore);
        if(idCodAreaTerritoriale != null) QuestParams.put("idCodAreaTerritoriale",idCodAreaTerritoriale);


        List<MipTCompilazioneQuestionario> listaDomande = compilazioneQuestionarioRepository.getDomandeRisposte(QuestParams).list();

        //creo array di domande
        List<MipDDomanda> mipDDomandas = mipDDomandaRepository.findByFase(idFase.intValue());
        List<String> domande = new ArrayList<>();
        for(MipDDomanda d: mipDDomandas ){
            domande.add(d.getTestoDomanda());
            if(d.getTipoDomanda() != null){
                domande.add("Altro (Specificare)");
            }
        }
        reportQuestionario.setDomande(domande);

        for(int i = 0; i < listaDomande.size(); i++){
            ReportQuestionarioRigheReportistica rigaReportistica = createRigaReportistica(idFase, idSoggettoAttuatore,
                    idCodAreaTerritoriale, QuestParams, listaDomande, i);
            righeReportistica.add(rigaReportistica);
        }

        reportQuestionario.setRigheReportistica(righeReportistica);
        return reportQuestionario;
    }

    private ReportQuestionarioRigheReportistica createRigaReportistica(Long idFase, Long idSoggettoAttuatore,
            String idCodAreaTerritoriale, Map<String, Object> QuestParams,
            List<MipTCompilazioneQuestionario> listaDomande, int i) {
        ReportQuestionarioRigheReportistica rigaReportistica = new ReportQuestionarioRigheReportistica();
        // setto id questionario
        rigaReportistica.setId(listaDomande.get(i).getId());
        //recupero nome soggetto attuatore in base all'idSoggetto Attuatore se esistente nei filtri
        if(QuestParams.containsKey("idSoggettoAttuatore")){
            MipDSoggettoAttuatore soggettoAttuatore =  soggettoAttuatoreRepository.find("idSoggettoAttuatore",idSoggettoAttuatore).firstResult();
            //setto nome soggetto attuatore
            rigaReportistica.setSoggettoAttuatore(soggettoAttuatore.getDenominazione());
        }else{
            //recupero id soggetto attuatore associato
            if(idFase >= 2){
                List<MipDSoggettoAttuatore> soggettoAttuatore = soggettoAttuatoreRepository.getTutorByIdCittadino(listaDomande.get(i).getCittadino().getIdCittadino()).list();
                if(soggettoAttuatore.size() > 0 && soggettoAttuatore.get(0) != null){
                    rigaReportistica.setSoggettoAttuatore(soggettoAttuatore.get(0).getDenominazione());
                }
            }else{
                rigaReportistica.setSoggettoAttuatore(null);
            }
        }
        //recupero area territoriale in base a idAreaTerritoriale se esistente nei filtri
        if(QuestParams.containsKey("idCodAreaTerritoriale")){
            ExtGmopDAreaTerritoriale areaTerritoriale = areaTerritorialeRepository.find("codiceAreaTerritoriale",idCodAreaTerritoriale).firstResult();
            //setto nome area territoriale
            rigaReportistica.setAreaTerritoriale(areaTerritoriale.getDescrizioneAreaTerritoriale());
        }else{
            if(idFase >= 2){
                //recupero area territoriale associata
                List<ExtGmopDAreaTerritoriale> areaTerritoriale =  areaTerritorialeRepository.getAreaTerritorialeByIdCittadino(listaDomande.get(i).getCittadino().getIdCittadino()).list();
                //setto nome area territoriale
                if(!areaTerritoriale.isEmpty() && areaTerritoriale.get(0) != null){
                    rigaReportistica.setAreaTerritoriale(areaTerritoriale.get(0).getDescrizioneAreaTerritoriale());
                }
            }else{
                List<ExtGmopDAreaTerritoriale> areaTerritoriale =  areaTerritorialeRepository.getAreaTerritorialeByIdCittadinoFase1(listaDomande.get(i).getCittadino().getIdCittadino()).list();
                //setto nome area territoriale
                rigaReportistica.setAreaTerritoriale(areaTerritoriale.get(0).getDescrizioneAreaTerritoriale());
            }
        }
        //recupero data inserimento
        List<MipRCompilazioneQuestionarioFase> dataInserim = compilazioneQuestionarioFaseRepository.findDateByIdFaseEIdCompilazioneQuestionario(idFase,listaDomande.get(i).getId()).list();
        //setto data inserimento
        rigaReportistica.setDataInserim(dataInserim.get(0).getDataInserim());

        //faccio array di domande e risposte
        List<MipTRispostaCompilazione> compilazione = rispostaCompilazioneRepository.getDomandeERisposteByFase(idFase,listaDomande.get(i).getId()).list();
        List<DomandeRisposte> domandeRisposte = new ArrayList<>();

         for(int j = 0; j < compilazione.size(); j++){
            DomandeRisposte record = createRecord(compilazione, j);
            domandeRisposte.add(record);
        }

        List<DomandeRisposte> domandeRisposteReload = new ArrayList<>();
        DomandeRisposte newMultiRecord = new DomandeRisposte();
        for(int j = 0; j < domandeRisposte.size(); j++){
            String record = domandeRisposte.get(j).getTestoRisposta();
            String rispostaLibera = domandeRisposte.get(j).getRispostaLibera();
            char numDomanda = domandeRisposte.get(j).getTestoDomanda().charAt(0);
            boolean multi = false;
            for (int c = j + 1; c < domandeRisposte.size(); c++){
                if(numDomanda == domandeRisposte.get(c).getTestoDomanda().charAt(0)){
                    record = record + " - " + domandeRisposte.get(c).getTestoRisposta();
                    rispostaLibera = domandeRisposte.get(c).getRispostaLibera();
                }
                if(c == domandeRisposte.size()-1){
                    multi = true;
                }
            }
            if(multi){
                newMultiRecord.setTestoDomanda(domandeRisposte.get(j).getTestoDomanda());
                newMultiRecord.setTestoRisposta(record);
                newMultiRecord.setRispostaLibera(rispostaLibera);
                domandeRisposteReload.add(newMultiRecord);
                break;
            }
        }

        for(int j = 0; j < domandeRisposte.size(); j++){
            if(domandeRisposte.get(j).getTestoDomanda().charAt(0) != newMultiRecord.getTestoDomanda().charAt(0)) domandeRisposteReload.add(domandeRisposte.get(j));
        }
        domandeRisposte = domandeRisposteReload;
        rigaReportistica.setCompilazione(domandeRisposte);
        return rigaReportistica;
    }

    private DomandeRisposte createRecord(List<MipTRispostaCompilazione> compilazione, int j) {
        DomandeRisposte record = new DomandeRisposte();

        if(compilazione.get(j).getIdDomanda().getTipoDomanda() == null){
            //domanda aperta

            record.setTestoDomanda(compilazione.get(j).getIdDomanda().getTestoDomanda());
            record.setRispostaLibera(compilazione.get(j).getRispostaLibera());
        }else if(compilazione.get(j).getIdDomanda().getTipoDomanda().equals("M")){
            //domanda multipla
            record.setTestoDomanda(compilazione.get(j).getIdDomanda().getTestoDomanda());
            record.setTestoRisposta(compilazione.get(j).getIdRisposta().getTestoRisposta());
            record.setRispostaLibera(compilazione.get(j).getRispostaLibera());

        }else if(compilazione.get(j).getIdDomanda().getTipoDomanda().equals("S")){
            //domanda chiusa
            record.setTestoDomanda(compilazione.get(j).getIdDomanda().getTestoDomanda());
            record.setTestoRisposta(compilazione.get(j).getIdRisposta().getTestoRisposta());
            record.setRispostaLibera(compilazione.get(j).getRispostaLibera());
        }
        return record;
    }

    public final static List<String> HEADERS = Arrays.asList(
            "ID IDEA",
            "Titolo",
            "Tipo progetto",
            "Data di registrazione",
            "Area territoriale incontro pre-accoglienza",
            "Descrizione progetto",
            "Status",
            "Utente principale",
            "Data incontro pre-accoglienza",
            "Sede incontro di pre-accoglienza",
            "Incontro telefonico",
            "Soggetto attuatore",
            "Data associazione soggetto attuatore",
            "E’ stata erogata la 1° ora di consulenza individuale?",
            "Firma sul Patto di Servizio",
            "Data Firma Patto di Servizio",
            "Business plan validato",
            "Data Business plan validato",
            "Misura del Ricambio Generazionale",
            "ID UTENTE",
            "Cognome",
            "Nome",
            "Codice fiscale",
            "Sesso",
            "Cittadinanza",
            "Cittadinanza altro specificare",
            "Data di nascita",
            "Luogo di nascita",
            "Provincia di nascita",
            "Telefono fisso",
            "Cellulare",
            "Email",
            "Email alternativa",
            "Indirizzo",
            "Città",
            "Cap",
            "Provincia",
            "Indirizzo domicilio",
            "Cap domicilio",
            "Città domicilio",
            "Provincia domicilio",
            "Titolo di studio",
            "Titolo di studio altro specificare",
            "Situazione lavorativa",
            "Situazione lavorativa altro specificare",
            "Abitativo",
            "Condizione familiare",
            "Note / Commenti",
            "Creato il",
            "Inserito da",
            "Modificato il",
            "Modificato da",
            "Com’è venuto a conoscenza del Programma Mip",
            "Altro specificare",
            "Commenti (interni)",
            "Titolo di soggiorno",
            "Motivo titolo di soggiorno",
            "Carta di soggiorno",
            "Motivo carta di soggiorno");


    public String findSoggettoAttuatoreById(Long idSoggettoAttuatore) {
        MipDSoggettoAttuatore soggettoAttuatore =  soggettoAttuatoreRepository.find("id",idSoggettoAttuatore).firstResult();
        return soggettoAttuatore.getDenominazione();
    }

    public String findStatusById(Long idStatoIdea){
        MipDStatoIdeaDiImpresa status = statoIdeaDiImpresaRepository.findById(idStatoIdea);
        return status.getDescrizioneStatoIdeaDiImpresa();
    }

    public String findAreaTerritorialeById(String idCodAreaTerritoriale) {
        ExtGmopDAreaTerritoriale areaTerritoriale = areaTerritorialeRepository.find("codiceAreaTerritoriale",idCodAreaTerritoriale).firstResult();
        return areaTerritoriale.getDescrizioneAreaTerritoriale();
    }

    public final static List<String> getHeaders(List<String> domande){
        List<String> headers = new ArrayList<>();
        headers.add("id");
        headers.add("Data Completato");
        headers.add("Area Territoriale");
        headers.add("Soggetto Attuatore");
        for(String d : domande){
            headers.add(d);
        }
        return headers;
    }

    public List<ExcelReportisticaQuestionari> getExcelRisposte(Long idFase, Date dataDa, Date dataA, String idCodAreaTerritoriale, Long idSoggettoAttuatore){
        List<ExcelReportisticaQuestionari> excelList = new ArrayList<>();
        Map <String,Object> QuestParams = new HashMap<>();

        if(idFase != null) QuestParams.put("idFase",idFase);
        if(dataDa != null) QuestParams.put("dataDa",dataDa);
        if(dataA != null) QuestParams.put("dataA",dataA);
        if(idSoggettoAttuatore != null) QuestParams.put("idSoggettoAttuatore",idSoggettoAttuatore);
        if(idCodAreaTerritoriale != null) QuestParams.put("idCodAreaTerritoriale",idCodAreaTerritoriale);


        List<MipTCompilazioneQuestionario> listaDomande = compilazioneQuestionarioRepository.getDomandeRisposte(QuestParams).list();

        for(int i = 0; i < listaDomande.size(); i++){
            ExcelReportisticaQuestionari recordExcel = new ExcelReportisticaQuestionari();
            recordExcel.setIdQuestionario(String.valueOf(listaDomande.get(i).getId()));
            if(QuestParams.containsKey("idSoggettoAttuatore")){
                MipDSoggettoAttuatore soggettoAttuatore =  soggettoAttuatoreRepository.find("idSoggettoAttuatore",idSoggettoAttuatore).firstResult();
                recordExcel.setSoggettoAttuatore(soggettoAttuatore.getDenominazione());
            }else{
                if(idFase >= 2){
                    List<MipDSoggettoAttuatore> soggettoAttuatore = soggettoAttuatoreRepository.getTutorByIdCittadino(listaDomande.get(i).getCittadino().getIdCittadino()).list();
                    recordExcel.setSoggettoAttuatore(soggettoAttuatore.get(0).getDenominazione());
                }
            }
            if(QuestParams.containsKey("idCodAreaTerritoriale")){
                ExtGmopDAreaTerritoriale areaTerritoriale = areaTerritorialeRepository.find("codiceAreaTerritoriale",idCodAreaTerritoriale).firstResult();
                recordExcel.setAreaTerritoriale(areaTerritoriale.getDescrizioneAreaTerritoriale());
            }else{
                if(idFase >= 2){
                    List<ExtGmopDAreaTerritoriale> areaTerritoriale =  areaTerritorialeRepository.getAreaTerritorialeByIdCittadino(listaDomande.get(i).getCittadino().getIdCittadino()).list();
                    if(areaTerritoriale.size() > 0) recordExcel.setAreaTerritoriale(areaTerritoriale.get(0).getDescrizioneAreaTerritoriale());
                }else {
                    List<ExtGmopDAreaTerritoriale> areaTerritoriale =  areaTerritorialeRepository.getAreaTerritorialeByIdCittadinoFase1(listaDomande.get(i).getCittadino().getIdCittadino()).list();
                    recordExcel.setAreaTerritoriale(areaTerritoriale.get(0).getDescrizioneAreaTerritoriale());
                }
            }
            MipTCompilazioneQuestionario dataInserim = compilazioneQuestionarioRepository.find("dataInserim",listaDomande.get(i).getDataInserim()).firstResult();
            recordExcel.setDataInserim(dataInserim.getDataInserim());


            List<MipTRispostaCompilazione> compilazione = rispostaCompilazioneRepository.getDomandeERisposteByFase(idFase,listaDomande.get(i).getId()).list();

            List<DomandeRisposte> domandeRisposte = new ArrayList<>();

            for(int j = 0; j < compilazione.size(); j++){
                DomandeRisposte record = new DomandeRisposte();

                if(compilazione.get(j).getIdDomanda().getTipoDomanda() == null){
                    //domanda aperta
                    record.setTestoDomanda(compilazione.get(j).getIdDomanda().getTestoDomanda());
                    record.setRispostaLibera(compilazione.get(j).getRispostaLibera());
                }else if(compilazione.get(j).getIdDomanda().getTipoDomanda().equals("M")){
                    //domanda multipla
                    record.setTestoDomanda(compilazione.get(j).getIdDomanda().getTestoDomanda());
                    record.setTestoRisposta(compilazione.get(j).getIdRisposta().getTestoRisposta());
                    record.setRispostaLibera(compilazione.get(j).getRispostaLibera());

                }else if(compilazione.get(j).getIdDomanda().getTipoDomanda().equals("S")){
                    //domanda chiusa
                    record.setTestoDomanda(compilazione.get(j).getIdDomanda().getTestoDomanda());
                    record.setTestoRisposta(compilazione.get(j).getIdRisposta().getTestoRisposta());
                    record.setRispostaLibera(compilazione.get(j).getRispostaLibera());
                }
                domandeRisposte.add(record);
            }

            DomandeRisposte newMultiRecord = new DomandeRisposte();
            List<String> risposte = new ArrayList<>();
            for(int j = 0; j < domandeRisposte.size(); j++){
                String record = domandeRisposte.get(j).getTestoRisposta();
                String rispostaLibera = domandeRisposte.get(j).getRispostaLibera();
                char numDomanda = domandeRisposte.get(j).getTestoDomanda().charAt(0);
                boolean multi = false;
                for (int c = j + 1; c < domandeRisposte.size(); c++){
                    if(numDomanda == domandeRisposte.get(c).getTestoDomanda().charAt(0)){
                        record = record + " - " + domandeRisposte.get(c).getTestoRisposta();
                        rispostaLibera = domandeRisposte.get(c).getRispostaLibera();
                    }
                    if(c == domandeRisposte.size()-1){
                        multi = true;
                    }
                }
                if(multi){
                    newMultiRecord.setTestoDomanda(domandeRisposte.get(j).getTestoDomanda());
                    newMultiRecord.setTestoRisposta(record);
                    newMultiRecord.setRispostaLibera(rispostaLibera);
                    risposte.add(record);
                    risposte.add(rispostaLibera);
                    break;
                }
            }

            for(int j = 0; j < domandeRisposte.size(); j++){
                if(domandeRisposte.get(j).getTestoDomanda().charAt(0) != newMultiRecord.getTestoDomanda().charAt(0)){
                    if(compilazione.get(j).getIdDomanda().getTipoDomanda() == null){
                        risposte.add(domandeRisposte.get(j).getRispostaLibera());
                    }else{
                        risposte.add(domandeRisposte.get(j).getTestoRisposta());
                        risposte.add(domandeRisposte.get(j).getRispostaLibera());
                    }
                }
            }

            recordExcel.setRisposte(risposte);
            excelList.add(recordExcel);

        }
        return excelList;
    }


}
