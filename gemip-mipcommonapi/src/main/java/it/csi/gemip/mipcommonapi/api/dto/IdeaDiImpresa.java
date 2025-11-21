package it.csi.gemip.mipcommonapi.api.dto;

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

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.gemip.mipcommonapi.api.dto.FonteConoscenzaMip;
import it.csi.gemip.mipcommonapi.api.dto.StatoIdeaDiImpresa;
import java.util.Date;
import jakarta.validation.constraints.*;


public class IdeaDiImpresa   {
  private Long id = null;
  private String titolo = null;
  private String descrizioneIdeaDiImpresa = null;
  private String flgRicambioGenerazionale = null;
  private String flgErogazionePrimaOra = null;
  private FonteConoscenzaMip fonteConoscenzaMip = null;
  private String descrizioneAltraFonteConoscenzaMip = null;
  private StatoIdeaDiImpresa statoIdeaDiImpresa = null;
  private Date dataCambioStato = null;
  private Long idTutor = null;
  private Date dataSceltaTutor = null;
  private Long idIdeaDiImpresaSostituente = null;
  private Boolean businessPlanValidato = null;
  private String commentiInterni = null;
  private String noteCommenti = null;
  private Date dataValidBusinessPlan = null;
  private Date dataFirmaPattoServizio = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;
  private Boolean sbloccoAreaTerritoriale = null;

  /**
   **/
  
  @JsonProperty("id")
  @jakarta.validation.Valid
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   **/
  
  @JsonProperty("titolo")
  @jakarta.validation.Valid
  public String getTitolo() {
    return titolo;
  }
  public void setTitolo(String titolo) {
    this.titolo = titolo;
  }

  /**
   **/
  
  @JsonProperty("descrizioneIdeaDiImpresa")
  @jakarta.validation.Valid
  public String getDescrizioneIdeaDiImpresa() {
    return descrizioneIdeaDiImpresa;
  }
  public void setDescrizioneIdeaDiImpresa(String descrizioneIdeaDiImpresa) {
    this.descrizioneIdeaDiImpresa = descrizioneIdeaDiImpresa;
  }

  /**
   **/
  
  @JsonProperty("flgRicambioGenerazionale")
  @jakarta.validation.Valid
  public String getFlgRicambioGenerazionale() {
    return flgRicambioGenerazionale;
  }
  public void setFlgRicambioGenerazionale(String flgRicambioGenerazionale) {
    this.flgRicambioGenerazionale = flgRicambioGenerazionale;
  }

  /**
   **/
  
  @JsonProperty("flgErogazionePrimaOra")
  @jakarta.validation.Valid
  public String getFlgErogazionePrimaOra() {
    return flgErogazionePrimaOra;
  }
  public void setFlgErogazionePrimaOra(String flgErogazionePrimaOra) {
    this.flgErogazionePrimaOra = flgErogazionePrimaOra;
  }

  /**
   **/
  
  @JsonProperty("fonteConoscenzaMip")
  @jakarta.validation.Valid
  public FonteConoscenzaMip getFonteConoscenzaMip() {
    return fonteConoscenzaMip;
  }
  public void setFonteConoscenzaMip(FonteConoscenzaMip fonteConoscenzaMip) {
    this.fonteConoscenzaMip = fonteConoscenzaMip;
  }

  /**
   **/
  
  @JsonProperty("descrizioneAltraFonteConoscenzaMip")
  @jakarta.validation.Valid
  public String getDescrizioneAltraFonteConoscenzaMip() {
    return descrizioneAltraFonteConoscenzaMip;
  }
  public void setDescrizioneAltraFonteConoscenzaMip(String descrizioneAltraFonteConoscenzaMip) {
    this.descrizioneAltraFonteConoscenzaMip = descrizioneAltraFonteConoscenzaMip;
  }

  /**
   **/
  
  @JsonProperty("statoIdeaDiImpresa")
  @jakarta.validation.Valid
  public StatoIdeaDiImpresa getStatoIdeaDiImpresa() {
    return statoIdeaDiImpresa;
  }
  public void setStatoIdeaDiImpresa(StatoIdeaDiImpresa statoIdeaDiImpresa) {
    this.statoIdeaDiImpresa = statoIdeaDiImpresa;
  }

  /**
   **/
  
  @JsonProperty("dataCambioStato")
  @jakarta.validation.Valid
  public Date getDataCambioStato() {
    return dataCambioStato;
  }
  public void setDataCambioStato(Date dataCambioStato) {
    this.dataCambioStato = dataCambioStato;
  }

  /**
   **/
  
  @JsonProperty("idTutor")
  @jakarta.validation.Valid
  public Long getIdTutor() {
    return idTutor;
  }
  public void setIdTutor(Long idTutor) {
    this.idTutor = idTutor;
  }

  /**
   **/
  
  @JsonProperty("dataSceltaTutor")
  @jakarta.validation.Valid
  public Date getDataSceltaTutor() {
    return dataSceltaTutor;
  }
  public void setDataSceltaTutor(Date dataSceltaTutor) {
    this.dataSceltaTutor = dataSceltaTutor;
  }

  /**
   **/
  
  @JsonProperty("idIdeaDiImpresaSostituente")
  @jakarta.validation.Valid
  public Long getIdIdeaDiImpresaSostituente() {
    return idIdeaDiImpresaSostituente;
  }
  public void setIdIdeaDiImpresaSostituente(Long idIdeaDiImpresaSostituente) {
    this.idIdeaDiImpresaSostituente = idIdeaDiImpresaSostituente;
  }

  /**
   **/
  
  @JsonProperty("businessPlanValidato")
  @jakarta.validation.Valid
  public Boolean isBusinessPlanValidato() {
    return businessPlanValidato;
  }
  public void setBusinessPlanValidato(Boolean businessPlanValidato) {
    this.businessPlanValidato = businessPlanValidato;
  }

  /**
   **/
  
  @JsonProperty("commentiInterni")
  @jakarta.validation.Valid
  public String getCommentiInterni() {
    return commentiInterni;
  }
  public void setCommentiInterni(String commentiInterni) {
    this.commentiInterni = commentiInterni;
  }

  /**
   **/
  
  @JsonProperty("noteCommenti")
  @jakarta.validation.Valid
  public String getNoteCommenti() {
    return noteCommenti;
  }
  public void setNoteCommenti(String noteCommenti) {
    this.noteCommenti = noteCommenti;
  }

  /**
   **/
  
  @JsonProperty("dataValidBusinessPlan")
  @jakarta.validation.Valid
  public Date getDataValidBusinessPlan() {
    return dataValidBusinessPlan;
  }
  public void setDataValidBusinessPlan(Date dataValidBusinessPlan) {
    this.dataValidBusinessPlan = dataValidBusinessPlan;
  }

  /**
   **/
  
  @JsonProperty("dataFirmaPattoServizio")
  @jakarta.validation.Valid
  public Date getDataFirmaPattoServizio() {
    return dataFirmaPattoServizio;
  }
  public void setDataFirmaPattoServizio(Date dataFirmaPattoServizio) {
    this.dataFirmaPattoServizio = dataFirmaPattoServizio;
  }

  /**
   **/
  
  @JsonProperty("codUserInserim")
  @jakarta.validation.Valid
  public String getCodUserInserim() {
    return codUserInserim;
  }
  public void setCodUserInserim(String codUserInserim) {
    this.codUserInserim = codUserInserim;
  }

  /**
   **/
  
  @JsonProperty("dataInserim")
  @jakarta.validation.Valid
  public Date getDataInserim() {
    return dataInserim;
  }
  public void setDataInserim(Date dataInserim) {
    this.dataInserim = dataInserim;
  }

  /**
   **/
  
  @JsonProperty("codUserAggiorn")
  @jakarta.validation.Valid
  public String getCodUserAggiorn() {
    return codUserAggiorn;
  }
  public void setCodUserAggiorn(String codUserAggiorn) {
    this.codUserAggiorn = codUserAggiorn;
  }

  /**
   **/
  
  @JsonProperty("dataAggiorn")
  @jakarta.validation.Valid
  public Date getDataAggiorn() {
    return dataAggiorn;
  }
  public void setDataAggiorn(Date dataAggiorn) {
    this.dataAggiorn = dataAggiorn;
  }

  /**
   **/
  
  @JsonProperty("sbloccoAreaTerritoriale")
  @jakarta.validation.Valid
  public Boolean isSbloccoAreaTerritoriale() {
    return sbloccoAreaTerritoriale;
  }
  public void setSbloccoAreaTerritoriale(Boolean sbloccoAreaTerritoriale) {
    this.sbloccoAreaTerritoriale = sbloccoAreaTerritoriale;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IdeaDiImpresa ideaDiImpresa = (IdeaDiImpresa) o;
    return Objects.equals(id, ideaDiImpresa.id) &&
        Objects.equals(titolo, ideaDiImpresa.titolo) &&
        Objects.equals(descrizioneIdeaDiImpresa, ideaDiImpresa.descrizioneIdeaDiImpresa) &&
        Objects.equals(flgRicambioGenerazionale, ideaDiImpresa.flgRicambioGenerazionale) &&
        Objects.equals(flgErogazionePrimaOra, ideaDiImpresa.flgErogazionePrimaOra) &&
        Objects.equals(fonteConoscenzaMip, ideaDiImpresa.fonteConoscenzaMip) &&
        Objects.equals(descrizioneAltraFonteConoscenzaMip, ideaDiImpresa.descrizioneAltraFonteConoscenzaMip) &&
        Objects.equals(statoIdeaDiImpresa, ideaDiImpresa.statoIdeaDiImpresa) &&
        Objects.equals(dataCambioStato, ideaDiImpresa.dataCambioStato) &&
        Objects.equals(idTutor, ideaDiImpresa.idTutor) &&
        Objects.equals(dataSceltaTutor, ideaDiImpresa.dataSceltaTutor) &&
        Objects.equals(idIdeaDiImpresaSostituente, ideaDiImpresa.idIdeaDiImpresaSostituente) &&
        Objects.equals(businessPlanValidato, ideaDiImpresa.businessPlanValidato) &&
        Objects.equals(commentiInterni, ideaDiImpresa.commentiInterni) &&
        Objects.equals(noteCommenti, ideaDiImpresa.noteCommenti) &&
        Objects.equals(dataValidBusinessPlan, ideaDiImpresa.dataValidBusinessPlan) &&
        Objects.equals(dataFirmaPattoServizio, ideaDiImpresa.dataFirmaPattoServizio) &&
        Objects.equals(codUserInserim, ideaDiImpresa.codUserInserim) &&
        Objects.equals(dataInserim, ideaDiImpresa.dataInserim) &&
        Objects.equals(codUserAggiorn, ideaDiImpresa.codUserAggiorn) &&
        Objects.equals(dataAggiorn, ideaDiImpresa.dataAggiorn) &&
        Objects.equals(sbloccoAreaTerritoriale, ideaDiImpresa.sbloccoAreaTerritoriale);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, titolo, descrizioneIdeaDiImpresa, flgRicambioGenerazionale, flgErogazionePrimaOra, fonteConoscenzaMip, descrizioneAltraFonteConoscenzaMip, statoIdeaDiImpresa, dataCambioStato, idTutor, dataSceltaTutor, idIdeaDiImpresaSostituente, businessPlanValidato, commentiInterni, noteCommenti, dataValidBusinessPlan, dataFirmaPattoServizio, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn, sbloccoAreaTerritoriale);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdeaDiImpresa {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    titolo: ").append(toIndentedString(titolo)).append("\n");
    sb.append("    descrizioneIdeaDiImpresa: ").append(toIndentedString(descrizioneIdeaDiImpresa)).append("\n");
    sb.append("    flgRicambioGenerazionale: ").append(toIndentedString(flgRicambioGenerazionale)).append("\n");
    sb.append("    flgErogazionePrimaOra: ").append(toIndentedString(flgErogazionePrimaOra)).append("\n");
    sb.append("    fonteConoscenzaMip: ").append(toIndentedString(fonteConoscenzaMip)).append("\n");
    sb.append("    descrizioneAltraFonteConoscenzaMip: ").append(toIndentedString(descrizioneAltraFonteConoscenzaMip)).append("\n");
    sb.append("    statoIdeaDiImpresa: ").append(toIndentedString(statoIdeaDiImpresa)).append("\n");
    sb.append("    dataCambioStato: ").append(toIndentedString(dataCambioStato)).append("\n");
    sb.append("    idTutor: ").append(toIndentedString(idTutor)).append("\n");
    sb.append("    dataSceltaTutor: ").append(toIndentedString(dataSceltaTutor)).append("\n");
    sb.append("    idIdeaDiImpresaSostituente: ").append(toIndentedString(idIdeaDiImpresaSostituente)).append("\n");
    sb.append("    businessPlanValidato: ").append(toIndentedString(businessPlanValidato)).append("\n");
    sb.append("    commentiInterni: ").append(toIndentedString(commentiInterni)).append("\n");
    sb.append("    noteCommenti: ").append(toIndentedString(noteCommenti)).append("\n");
    sb.append("    dataValidBusinessPlan: ").append(toIndentedString(dataValidBusinessPlan)).append("\n");
    sb.append("    dataFirmaPattoServizio: ").append(toIndentedString(dataFirmaPattoServizio)).append("\n");
    sb.append("    codUserInserim: ").append(toIndentedString(codUserInserim)).append("\n");
    sb.append("    dataInserim: ").append(toIndentedString(dataInserim)).append("\n");
    sb.append("    codUserAggiorn: ").append(toIndentedString(codUserAggiorn)).append("\n");
    sb.append("    dataAggiorn: ").append(toIndentedString(dataAggiorn)).append("\n");
    sb.append("    sbloccoAreaTerritoriale: ").append(toIndentedString(sbloccoAreaTerritoriale)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
