package it.csi.gemip.mipcommonapi.mapper;

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
import it.csi.gemip.mipcommonapi.api.dto.Domanda.TipoDomandaEnum;
import it.csi.gemip.mipcommonapi.integration.entities.*;
import it.csi.gemip.model.Utente;
import it.csi.gemip.model.ReportIdeaImpresaBean;
import jakarta.enterprise.context.ApplicationScoped;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@ApplicationScoped
@Mapper(componentModel = "cdi")
public interface GenericMapper {
    public UserInfo userInfoToUtente(Utente utente);

    public Utente utenteToUserInfo(UserInfo userinfo);

    public Cittadinanza cittadinanzaEntyToVo(ExtTabCittadinanza cittadinanza);

    public Comune comuneEntyToVo(ExtTtComune comune);

    public Provincia provinciaEntyToVo(ExtTtProvincia provincia);

    public StatoEstero statoEsteroEntyToVo(ExtStatiEsteri statiEsteri);

    public CondizioneOccupazionale condizioneOccupazionaleEntyToVo(MipDCondizioneOccupazionale condizioneOccupazionale);

    public TitoloDiStudio titoloStudioEntyToVo(MipDTitoloDiStudio titoloDiStudio);

    public SvantaggioAbitativo svantaggioAbitativoEntyToVo(MipDSvantaggioAbitativo svantaggioAbitativo);

    public CondizioneFamiliare condizioneFamiliareEntyToVo(MipDCondizioneFamiliare condizioneFamiliare);

    public Cittadino cittadinoEntyToVo(MipTCittadino cittadino);

    public MipTCittadino cittadinoVoToEnty(Cittadino cittadino);

    public AreaTerritoriale areaTerritorialeEntyToVo(ExtGmopDAreaTerritoriale areaTerritoriale);

    public ExtGmopDAreaTerritoriale areaTerritorialeVoToEntity(AreaTerritoriale areaTerritoriale);

    @Mapping(target = "cittadino", source = "mipTCittadino")
    @Mapping(target = "numeroDocumentoIdentita",source = "numDocumIdentita")
    @Mapping(target = "documentoRilasciatoDa", source = "enteRilascioDocumIdentita")
    @Mapping(target = "dataScadenzaDocumento", source = "dataScadDocumIdentita")
    @Mapping(target = "permessoDiSoggiornoRilasciatoDa", source = "enteRilascioPermessoDiSoggiorno")
    @Mapping(target = "dataScadPermessoSoggiorno",source = "dataScadPermessoSoggiorno")
    public AnagraficaCittadino anagraficaCittadinoEntyToVo(MipTAnagraficaCittadino anagraficaCittadino);

    @Mapping(target = "mipTCittadino", source = "cittadino")
    @Mapping(target = "numDocumIdentita",source = "numeroDocumentoIdentita")
    @Mapping(target = "enteRilascioDocumIdentita", source = "documentoRilasciatoDa")
    @Mapping(target = "dataScadDocumIdentita", source = "dataScadenzaDocumento")
    @Mapping(target = "enteRilascioPermessoDiSoggiorno", source = "permessoDiSoggiornoRilasciatoDa")
    @Mapping(target = "dataScadPermessoSoggiorno",source = "dataScadPermessoSoggiorno")
    @Mapping(target = "tipoPermessoDiSoggiorno",source = "tipoPermessoDiSoggiorno")
    public MipTAnagraficaCittadino anaGraficaCittadinoVoToEnty(AnagraficaCittadino anagraficaCittadino);

    public IncontroPreaccoglienza incontroPreaccoglienzaToVo(MipTIncontroPreaccoglienza incontroPreaccoglienza);

    public MipTIncontroPreaccoglienza incontroPreaccoglienzaToEntity(IncontroPreaccoglienza incontroPreaccoglienza);

    public FonteConoscenzaMip fonteConoscenzaMipToVo(MipDFonteConoscenzaMip mipDFonteConoscenzaMip);

    public StatoIdeaDiImpresa statoIdeaDiImpresaToVo(MipDStatoIdeaDiImpresa mipDStatoIdeaDiImpresa);

    @Mapping(target="businessPlanValidato", expression = "java(ideaDiImpresa.getDataValidBusinessPlan() != null)")
    @Mapping(target="sbloccoAreaTerritoriale", source = "flgSbloccoAreaTerritoriale", qualifiedByName="flag2bool")
    public IdeaDiImpresa ideaDiImpresaToVo(MipTIdeaDiImpresa ideaDiImpresa);

    @Mapping(target="flgSbloccoAreaTerritoriale", source = "sbloccoAreaTerritoriale", qualifiedByName="bool2flag")
    public MipTIdeaDiImpresa ideaDiImpresaToEnty(IdeaDiImpresa ideaDiImpresa);

    @Mapping(target = "cittadino", source = "cittadino")
    public CittadinoIncontroPreaccoglienza cittadinoIncontroPreaccoglienzaToVo(MipRCittadinoIncontroPreacc cittadinoIncontroPreacc);

    @Mapping(target = "cittadino", source = "cittadino")
    public MipRCittadinoIncontroPreacc cittadinoIncontroPreaccToEnty(CittadinoIncontroPreaccoglienza cittadinoIncontroPreaccoglienza);

    @Mapping(target = "soggettoAttuatore", source = "soggettoAttuatore")
    public Tutor tutorToVo(MipDSoggettoAttuatore soggettoAttuatore);

    public LuogoIncontro luogoIncontroToVO(MipDLuogoIncontro luogoIncontro);

    public MipDLuogoIncontro luogoIncontroToEntity(LuogoIncontro luogoIncontro);

    @Mapping(target = "incontroPreaccoglienza", source = "idIncontroPreaccoglienza")
    @Mapping(target = "areaTerritoriale", source = "codAreaTerritoriale")
    public IncontroPreaccoglienzaAreaTerritoriale incontroPreaccAreaTerrToVo(MipRIncontroPreaccAreaTerr incontroPreaccAreaTerr);


    @Mapping(target = "idIncontroPreaccoglienza", source = "incontroPreaccoglienza")
    @Mapping(target = "codAreaTerritoriale", source = "areaTerritoriale")
    public MipRIncontroPreaccAreaTerr incontroPreaccAreaTerrToEnty(IncontroPreaccoglienzaAreaTerritoriale incontroPreaccAreaTerr);

    public Operatore operatoreToVo(MipDOperatore operatore);

    public MipDOperatore operatoreToEnty(Operatore operatore);

    public SoggettoAffidatarioOperatore soggettoAffOperatoreToVo(MipROperatoreSoggAttuatore operatoreSoggAttuatore);

    public MipROperatoreSoggAttuatore soggettoAffOperatoreToEnty(SoggettoAffidatarioOperatore soggettoAffidatarioOperatore);

    public SoggettoAttuatore soggettoAttuatoreToVo(MipDSoggettoAttuatore soggettoAttuatore);

    public SoggettoAffidatario soggettoAffidatarioToVo(MipDSoggettoAffidatario soggettoAffidatario);

    public SoggettoAttuatoreOperatore soggettoAttuatoreOperatoreToVo(MipROperatoreSoggAttuatore operatoreSoggAttuatore);

    public MipROperatoreSoggAttuatore soggettoAttuatoreToEnty(SoggettoAttuatoreOperatore soggettoAttuatoreOperatore);

    @Mapping(target ="idDocumento",source = "id")
    public Documento documentoToVo(MipTDocumento documento);

    @Mapping(target = "id", source = "idDocumento")
    public MipTDocumento documentoToEnty(Documento documento);

    @Mapping(target = "codiceTipoDocumento", source = "codTipoDocumento")
    @Mapping(target = "descrizioneTipoDocumento", source = "descrTipoDocumento")
    @Mapping(target = "dataInizio", source = "dataInizio")
    @Mapping(target = "dataFine", source = "dataFine")
    public TipoDocumento tipoDocumentoToVo(MipDTipoDocumento tipoDocumento);

    public EventoCalendario eventoCalendarioToVo(MipTEventoCalendario eventoCalendario);

    @Mapping(target = "idDomanda", source = "mipDDomanda.id")
    @Mapping(target = "risposte", source = "mipDRisposte")
    @Mapping(target = "tipoDomanda", expression = "java(calcolaTipoDomanda(mipDRisposte,mipDDomanda))")
    @Mapping(target = "idRispostaCondizionale", source = "mipDDomanda.rispostaCondizionale.id")
    @Mapping(target = "idDomandaCondizionale", source = "mipDDomanda.rispostaCondizionale.idDomanda")
    Domanda domandaEntityToDto(MipDDomanda mipDDomanda, List<MipDRisposta> mipDRisposte);

    @Mapping(target = "idRisposta", source = "id")
    @Mapping(target = "flgRichiestoDettaglio", expression = "java(calcolaFlgRichiestoDettaglio(mipDRisposta.getFlgRichiestoDettaglio()))")
    Risposta rispostaEntityToDto(MipDRisposta mipDRisposta);

    // Per gestire le liste di entità

    List<Risposta> risposteEntityListToDtoList(List<MipDRisposta> mipDRisposte);

    default TipoDomandaEnum calcolaTipoDomanda(List<MipDRisposta> mipDRisposte, MipDDomanda mipDDomanda) {
        if (mipDRisposte == null || mipDRisposte.isEmpty()) {
            //non ci sono risposte
            return TipoDomandaEnum.APERTA;
        } else {
            if ("S".equals(mipDDomanda.getTipoDomanda()))
                return TipoDomandaEnum.CHIUSA;
            else
                return TipoDomandaEnum.CHIUSA_SCELTA_MULTIPLA;
        }
    }

    default boolean calcolaFlgRichiestoDettaglio(String string ) {
        return "S".equals(string);
    }



    public StoricoCalendario fileIcsToVo(MipTFileIc file);

    public MipTFileIc fileIcsToEntity(FileIcs fileIcs);
    public Ente enteToVo(MipDEnte ente);

    public MipDEnte enteToEnty(Ente ente);

    MipDStatoIdeaDiImpresa statoIdeaDiImpresaToEntity(StatoIdeaDiImpresa statoIdeaDiImpresa);

    
    @Mapping(target="ideaDiImpresa", source = "entity")
    @Mapping(target="citadino", source = "entity")
    @Mapping(target="cittadinoAnagrafica", source = "entity")
    @Mapping(target="areaTerritoriale", source = "entity")
    @Mapping(target="tutor", source = "entity")
    @Mapping(target="operatore", source = "entity")
    @Mapping(target="dataPreaccoglienza", source = "dataIncontroPreaccoglienza")
    @Mapping(target="sedePreaccoglienza", source = "sedeIncontroPreaccoglienza")
    public IdeaDiImpresaRicercaExtended reportIdeaImpresaEntityToVo(ReportIdeaImpresaBean entity);
    
    @Mapping(target="statoIdeaDiImpresa.id", source = "idStatoIdeaImpresa")
    @Mapping(target="statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa", source = "descrizioneStatoIdeaDiImpresa")
    @Mapping(target="fonteConoscenzaMip.descrizioneFonteConoscenzaMip", source = "descrizioneFonteConoscenzaMip")
    @Mapping(target="dataSceltaTutor", source = "dataAssociazioneSoggettoAttuatore")
    @Mapping(target="dataFirmaPattoServizio", source = "dataFirmaPattoServizio")
    @Mapping(target="dataAggiorn", source = "dataAggiorn")
    public IdeaDiImpresa reportIdeaImpresaEntityToVo_idea(ReportIdeaImpresaBean entity);

    @Mapping(target="comuneDomicilio.descrizioneComune", source = "desComuneDomicilio")
    @Mapping(target="comuneDomicilio.provincia.codiceProvincia", source = "provinciaDomicilio")
    @Mapping(target="comuneDomicilio.provincia.descrizioneProvincia", source = "desProvinciaDomicilio")
    @Mapping(target="comuneResidenza.descrizioneComune", source = "desComuneResidenza")
    @Mapping(target="comuneResidenza.provincia.codiceProvincia", source = "provinciaResidenza")
    @Mapping(target="comuneResidenza.provincia.descrizioneProvincia", source = "desProvinciaResidenza")
    @Mapping(target="comuneNascita.descrizioneComune", source = "desComuneNascita")
    @Mapping(target="comuneNascita.provincia.codiceProvincia", source = "provinciaNascita")
    @Mapping(target="comuneNascita.provincia.descrizioneProvincia", source = "desProvinciaNascita")
    //@Mapping(target="svantaggioAbitativo.id", source = "svantaggioAbitativo")
    @Mapping(target="svantaggioAbitativo.descrizioneSvantaggioAbitativo", source = "descrSvantaggioAbitativo")
    @Mapping(target="codiceCondizioneOccupazionale.codiceCondizioneOccupazionale", source = "codiceCondizioneOccupazionale")
    @Mapping(target="codiceCondizioneOccupazionale.descrizioneCondizioneOccupazionale", source = "descrizioneCondizioneOccupazionale")
    //@Mapping(target="condizioneOccupazionaleAltro", source = "condizioneOccupazionaleAltro")
    @Mapping(target="condizioneFamiliare.descrCondizioneFamiliare", source = "descrCondizioneFamiliare")
    @Mapping(target="codiceCittadinanza.descrizione", source = "descrCittadinanza")
    @Mapping(target="codiceTitoloDiStudio.codiceTitoloDiStudio", source = "codiceTitoloDiStudio")
    @Mapping(target="codiceTitoloDiStudio.descrizioneTitoloDiStudio", source = "descrizioneTitoloDiStudio")
    public AnagraficaCittadino reportIdeaImpresaEntityToVo_anagrafica(ReportIdeaImpresaBean entity);

    public Cittadino reportIdeaImpresaEntityToVo_cittadino(ReportIdeaImpresaBean entity);

    public Operatore reportIdeaImpresaEntityToVo_operatore(ReportIdeaImpresaBean entity);
    @Mapping(target = "dataInserim", source = "dataAssociazioneSoggettoAttuatore")
    @Mapping(target = "soggettoAttuatore", source = "entity")
    public Tutor reportIdeaImpresaEntityToVo_tutor(ReportIdeaImpresaBean entity);
    @Mapping(target="codiceAreaTerritoriale.codiceAreaTerritoriale", source = "codiceAreaTerritoriale")
    @Mapping(target="codiceAreaTerritoriale.descrizioneAreaTerritoriale", source = "descrizioneAreaTerritoriale")
    public SoggettoAttuatore reportIdeaImpresaEntityToVo_soggettoAttuatore(ReportIdeaImpresaBean entity);

    @Mapping(target = "cittadinoAnagrafica.tipoPermessoDiSoggiorno",source = "tipoPermessoDiSoggiorno")
    public List<IdeaDiImpresaRicercaExtended> reportIdeaImpresaEntityToVo(List<ReportIdeaImpresaBean> entities);

    @Named("bool2flag")
    default String bool2Flag(boolean x) {
      return x ? "S" : null;
    }

    @Named("flag2bool")
    default boolean flag2bool(String x) {
      return "S".equals(x);
    }
}
