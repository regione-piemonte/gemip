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
import java.util.Date;
import jakarta.validation.constraints.*;


public class EventoCalendario   {
  private Long idEventoCalendario = null;
  private Long idFileIcs = null;
  private Date dataOraInizio = null;
  private Date dataOraFine = null;
  private String descrizioneEvento = null;
  private String luogo = null;
  private String titolo = null;
  private String datoUid = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;

  /**
   **/
  
  @JsonProperty("idEventoCalendario")
  @jakarta.validation.Valid
  public Long getIdEventoCalendario() {
    return idEventoCalendario;
  }
  public void setIdEventoCalendario(Long idEventoCalendario) {
    this.idEventoCalendario = idEventoCalendario;
  }

  /**
   **/
  
  @JsonProperty("idFileIcs")
  @jakarta.validation.Valid
  public Long getIdFileIcs() {
    return idFileIcs;
  }
  public void setIdFileIcs(Long idFileIcs) {
    this.idFileIcs = idFileIcs;
  }

  /**
   **/
  
  @JsonProperty("dataOraInizio")
  @jakarta.validation.Valid
  public Date getDataOraInizio() {
    return dataOraInizio;
  }
  public void setDataOraInizio(Date dataOraInizio) {
    this.dataOraInizio = dataOraInizio;
  }

  /**
   **/
  
  @JsonProperty("dataOraFine")
  @jakarta.validation.Valid
  public Date getDataOraFine() {
    return dataOraFine;
  }
  public void setDataOraFine(Date dataOraFine) {
    this.dataOraFine = dataOraFine;
  }

  /**
   **/
  
  @JsonProperty("descrizioneEvento")
  @jakarta.validation.Valid
  public String getDescrizioneEvento() {
    return descrizioneEvento;
  }
  public void setDescrizioneEvento(String descrizioneEvento) {
    this.descrizioneEvento = descrizioneEvento;
  }

  /**
   **/
  
  @JsonProperty("luogo")
  @jakarta.validation.Valid
  public String getLuogo() {
    return luogo;
  }
  public void setLuogo(String luogo) {
    this.luogo = luogo;
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
  
  @JsonProperty("datoUid")
  @jakarta.validation.Valid
  public String getDatoUid() {
    return datoUid;
  }
  public void setDatoUid(String datoUid) {
    this.datoUid = datoUid;
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventoCalendario eventoCalendario = (EventoCalendario) o;
    return Objects.equals(idEventoCalendario, eventoCalendario.idEventoCalendario) &&
        Objects.equals(idFileIcs, eventoCalendario.idFileIcs) &&
        Objects.equals(dataOraInizio, eventoCalendario.dataOraInizio) &&
        Objects.equals(dataOraFine, eventoCalendario.dataOraFine) &&
        Objects.equals(descrizioneEvento, eventoCalendario.descrizioneEvento) &&
        Objects.equals(luogo, eventoCalendario.luogo) &&
        Objects.equals(titolo, eventoCalendario.titolo) &&
        Objects.equals(datoUid, eventoCalendario.datoUid) &&
        Objects.equals(codUserInserim, eventoCalendario.codUserInserim) &&
        Objects.equals(dataInserim, eventoCalendario.dataInserim) &&
        Objects.equals(codUserAggiorn, eventoCalendario.codUserAggiorn) &&
        Objects.equals(dataAggiorn, eventoCalendario.dataAggiorn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idEventoCalendario, idFileIcs, dataOraInizio, dataOraFine, descrizioneEvento, luogo, titolo, datoUid, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventoCalendario {\n");
    
    sb.append("    idEventoCalendario: ").append(toIndentedString(idEventoCalendario)).append("\n");
    sb.append("    idFileIcs: ").append(toIndentedString(idFileIcs)).append("\n");
    sb.append("    dataOraInizio: ").append(toIndentedString(dataOraInizio)).append("\n");
    sb.append("    dataOraFine: ").append(toIndentedString(dataOraFine)).append("\n");
    sb.append("    descrizioneEvento: ").append(toIndentedString(descrizioneEvento)).append("\n");
    sb.append("    luogo: ").append(toIndentedString(luogo)).append("\n");
    sb.append("    titolo: ").append(toIndentedString(titolo)).append("\n");
    sb.append("    datoUid: ").append(toIndentedString(datoUid)).append("\n");
    sb.append("    codUserInserim: ").append(toIndentedString(codUserInserim)).append("\n");
    sb.append("    dataInserim: ").append(toIndentedString(dataInserim)).append("\n");
    sb.append("    codUserAggiorn: ").append(toIndentedString(codUserAggiorn)).append("\n");
    sb.append("    dataAggiorn: ").append(toIndentedString(dataAggiorn)).append("\n");
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
