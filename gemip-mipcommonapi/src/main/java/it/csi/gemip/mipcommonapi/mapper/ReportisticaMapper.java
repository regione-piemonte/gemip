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

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import it.csi.gemip.mipcommonapi.api.dto.AnagraficaCittadino;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresaRicercaExtended;
import it.csi.gemip.model.ExcelReportisticaIdeaDiImpresa;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Mapper(componentModel = "cdi")
public interface ReportisticaMapper {

    @Mapping(target="idIdea", source="ideaDiImpresa.id")
    @Mapping(target="titolo", source="ideaDiImpresa.titolo")
    @Mapping(target="tipoProj", constant = "")
    @Mapping(target="dataInserim", source="ideaDiImpresa.dataInserim")
    @Mapping(target="descrizioneAreaTerritoriale", source="areaTerritoriale.descrizioneAreaTerritoriale")
    @Mapping(target="descrizioneIdeaDiImpresa", source="ideaDiImpresa.descrizioneIdeaDiImpresa")
    @Mapping(target="statoIdeaDiImpresa", source="ideaDiImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa")
    @Mapping(target="soggettoAttuatoreDenominazione", source="tutor.soggettoAttuatore.denominazione")
    @Mapping(target="dataSoggettoAttuatoreAssociazione", source="ideaDiImpresa.dataSceltaTutor")
    @Mapping(target="erogazionePrimaOra", source="ideaDiImpresa.flgErogazionePrimaOra")
    @Mapping(target="firmaPattoServizio", source="ideaDiImpresa.dataFirmaPattoServizio", qualifiedByName = "seValorizzato")
    @Mapping(target="dataFirmaPattoServizio", source="ideaDiImpresa.dataFirmaPattoServizio")
    @Mapping(target="BPValidato", source="ideaDiImpresa.dataValidBusinessPlan", qualifiedByName = "seValorizzato")
    @Mapping(target="dataBPValidato", source="ideaDiImpresa.dataValidBusinessPlan")
    @Mapping(target="misuraRicambio", source="ideaDiImpresa.flgRicambioGenerazionale")
    @Mapping(target="idUtente", source="citadino.idCittadino")
    @Mapping(target="nome", source="citadino.nome")
    @Mapping(target="cognome", source="citadino.cognome")
    @Mapping(target="codiceFiscale", source="citadino.codiceFiscale")
    @Mapping(target="sesso", source="cittadinoAnagrafica.sesso")
    @Mapping(target="cittadinanza", source="cittadinoAnagrafica.codiceCittadinanza.descrizione")
    @Mapping(target="cittadinanzaAltro", source="cittadinoAnagrafica.descCittadinanzaAltro")
    @Mapping(target="dataNascita", source="cittadinoAnagrafica.dataNascita")
    @Mapping(target="luogoNascita", source = "cittadinoAnagrafica.comuneNascita.descrizioneComune") // cfr. AfterMapping
    @Mapping(target="provinciaNascita", source = "cittadinoAnagrafica.comuneNascita.provincia.descrizioneProvincia") // cfr. AfterMapping
    @Mapping(target="recapitoTelefono", source="cittadinoAnagrafica.recapitoTelefono")
    @Mapping(target="recapitoTelefono2", source="cittadinoAnagrafica.recapitoTelefono2")
    @Mapping(target="email", source="cittadinoAnagrafica.recapitoEmail")
    @Mapping(target="email2", source="cittadinoAnagrafica.recapitoEmail2")
    @Mapping(target="indirizzoResidenza", source="cittadinoAnagrafica.indirizzoResidenza")
    @Mapping(target="cittaResidenza", source = "cittadinoAnagrafica.comuneResidenza.descrizioneComune") // cfr. AfterMapping
    @Mapping(target="provinciaResidenza", source = "cittadinoAnagrafica.comuneResidenza.provincia.descrizioneProvincia") // cfr. AfterMapping
    @Mapping(target="capResidenza", source = "cittadinoAnagrafica.capResidenza") // cfr. AfterMapping
    @Mapping(target="indirizzoDomicilio", source="cittadinoAnagrafica.indirizzoDomicilio") // cfr. AfterMapping
    @Mapping(target="cittaDomicilio", source="cittadinoAnagrafica.comuneDomicilio.descrizioneComune") // cfr. AfterMapping
    @Mapping(target="provinciaDomicilio", source="cittadinoAnagrafica.comuneDomicilio.provincia.descrizioneProvincia") // cfr. AfterMapping
    @Mapping(target="capDomicilio", source="cittadinoAnagrafica.capDomicilio") // cfr. AfterMapping
    @Mapping(target="titoloStudio", source="cittadinoAnagrafica.codiceTitoloDiStudio.descrizioneTitoloDiStudio")
    @Mapping(target="titoloStudioAltro", source="cittadinoAnagrafica.titoloDiStudioAltro")
    @Mapping(target="situazioneLavorativa", source="cittadinoAnagrafica.codiceCondizioneOccupazionale.descrizioneCondizioneOccupazionale")
    @Mapping(target="situazioneLavorativaAltro", source="cittadinoAnagrafica.condizioneOccupazionaleAltro")
    @Mapping(target="svantaggioAbitativo", source="cittadinoAnagrafica.svantaggioAbitativo.descrizioneSvantaggioAbitativo")
    @Mapping(target="condizioneFamiliare", source="cittadinoAnagrafica.condizioneFamiliare.descrCondizioneFamiliare")
    @Mapping(target="titoloSoggiorno", source="cittadinoAnagrafica.numeroPermessoDiSoggiorno")
    @Mapping(target="motivoTitoloSoggiorno", source="cittadinoAnagrafica.descrMotivoPermessoDiSoggiorno")
    @Mapping(target="creato", source="ideaDiImpresa.dataInserim")
    @Mapping(target="inseritoDa", source="ideaDiImpresa.codUserInserim")
    @Mapping(target="modificato", source="ideaDiImpresa.dataAggiorn")
    @Mapping(target="modificatoDa", source="ideaDiImpresa.codUserAggiorn")
    @Mapping(target="noteCommenti", source="ideaDiImpresa.noteCommenti")
    @Mapping(target="conoscenzaMip", source="ideaDiImpresa.fonteConoscenzaMip.descrizioneFonteConoscenzaMip")
    @Mapping(target="commentiInterni", source="ideaDiImpresa.commentiInterni")
    ExcelReportisticaIdeaDiImpresa entity2report(IdeaDiImpresaRicercaExtended source);

    List<ExcelReportisticaIdeaDiImpresa> entity2report(List<IdeaDiImpresaRicercaExtended> source);

    @Named("seValorizzato")
    default public String seValorizzato(Object x) {
        return x != null ? "S" : "N";
    }

    @AfterMapping
    default public void afterMapping(@MappingTarget ExcelReportisticaIdeaDiImpresa target, IdeaDiImpresaRicercaExtended source) {
        final String NON_APPLICABILE = "[Non applicabile]";
        AnagraficaCittadino anagrafica = source.getCittadinoAnagrafica();
        if (anagrafica != null) {
            if (anagrafica.getComuneNascita() == null) {
                target.setLuogoNascita(anagrafica.getDescrizioneCittaEsteraNascita());
                target.setProvinciaNascita(NON_APPLICABILE);
            }

            if(anagrafica.getCodiceStatoEsteroResidenza() != null) {
                target.setCittaResidenza(anagrafica.getDescrizioneCittaEsteraResidenza());
                target.setProvinciaResidenza(NON_APPLICABILE);
                target.setCapResidenza(NON_APPLICABILE);
            }

            if (anagrafica.getComuneDomicilio() == null) {
                target.setIndirizzoDomicilio(target.getIndirizzoResidenza());
                target.setCittaDomicilio(target.getCittaResidenza());
                target.setProvinciaDomicilio(target.getProvinciaResidenza());
                target.setCapDomicilio(target.getCapResidenza());
            }
        }
    }
}
