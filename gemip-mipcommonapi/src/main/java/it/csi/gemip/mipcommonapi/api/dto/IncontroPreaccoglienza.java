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
import it.csi.gemip.mipcommonapi.api.dto.LuogoIncontro;
import it.csi.gemip.mipcommonapi.api.dto.Operatore;
import java.util.Date;
import jakarta.validation.constraints.*;


public class IncontroPreaccoglienza   {
  private Long id = null;
  private String denominazione = null;
  private String flgIncontroErogatoDaRemoto = null;
  private String flgIncontroTelefonico = null;
  private String linkIncontroRemoto = null;
  private LuogoIncontro luogoIncontro = null;
  private Operatore operatoreCreazione = null;
  private Integer numMaxPartecipanti = null;
  private Integer numPartecipantiIscritti = null;
  private Date dataIncontro = null;
  private String note = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;

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
  
  @JsonProperty("denominazione")
  @jakarta.validation.Valid
  public String getDenominazione() {
    return denominazione;
  }
  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }

  /**
   **/
  
  @JsonProperty("flgIncontroErogatoDaRemoto")
  @jakarta.validation.Valid
  public String getFlgIncontroErogatoDaRemoto() {
    return flgIncontroErogatoDaRemoto;
  }
  public void setFlgIncontroErogatoDaRemoto(String flgIncontroErogatoDaRemoto) {
    this.flgIncontroErogatoDaRemoto = flgIncontroErogatoDaRemoto;
  }

  /**
   **/
  
  @JsonProperty("flgIncontroTelefonico")
  @jakarta.validation.Valid
  public String getFlgIncontroTelefonico() {
    return flgIncontroTelefonico;
  }
  public void setFlgIncontroTelefonico(String flgIncontroTelefonico) {
    this.flgIncontroTelefonico = flgIncontroTelefonico;
  }

  /**
   **/
  
  @JsonProperty("linkIncontroRemoto")
  @jakarta.validation.Valid
  public String getLinkIncontroRemoto() {
    return linkIncontroRemoto;
  }
  public void setLinkIncontroRemoto(String linkIncontroRemoto) {
    this.linkIncontroRemoto = linkIncontroRemoto;
  }

  /**
   **/
  
  @JsonProperty("luogoIncontro")
  @jakarta.validation.Valid
  public LuogoIncontro getLuogoIncontro() {
    return luogoIncontro;
  }
  public void setLuogoIncontro(LuogoIncontro luogoIncontro) {
    this.luogoIncontro = luogoIncontro;
  }

  /**
   **/
  
  @JsonProperty("operatoreCreazione")
  @jakarta.validation.Valid
  public Operatore getOperatoreCreazione() {
    return operatoreCreazione;
  }
  public void setOperatoreCreazione(Operatore operatoreCreazione) {
    this.operatoreCreazione = operatoreCreazione;
  }

  /**
   **/
  
  @JsonProperty("numMaxPartecipanti")
  @jakarta.validation.Valid
  public Integer getNumMaxPartecipanti() {
    return numMaxPartecipanti;
  }
  public void setNumMaxPartecipanti(Integer numMaxPartecipanti) {
    this.numMaxPartecipanti = numMaxPartecipanti;
  }

  /**
   **/
  
  @JsonProperty("numPartecipantiIscritti")
  @jakarta.validation.Valid
  public Integer getNumPartecipantiIscritti() {
    return numPartecipantiIscritti;
  }
  public void setNumPartecipantiIscritti(Integer numPartecipantiIscritti) {
    this.numPartecipantiIscritti = numPartecipantiIscritti;
  }

  /**
   **/
  
  @JsonProperty("dataIncontro")
  @jakarta.validation.Valid
  public Date getDataIncontro() {
    return dataIncontro;
  }
  public void setDataIncontro(Date dataIncontro) {
    this.dataIncontro = dataIncontro;
  }

  /**
   **/
  
  @JsonProperty("note")
  @jakarta.validation.Valid
  public String getNote() {
    return note;
  }
  public void setNote(String note) {
    this.note = note;
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
    IncontroPreaccoglienza incontroPreaccoglienza = (IncontroPreaccoglienza) o;
    return Objects.equals(id, incontroPreaccoglienza.id) &&
        Objects.equals(denominazione, incontroPreaccoglienza.denominazione) &&
        Objects.equals(flgIncontroErogatoDaRemoto, incontroPreaccoglienza.flgIncontroErogatoDaRemoto) &&
        Objects.equals(flgIncontroTelefonico, incontroPreaccoglienza.flgIncontroTelefonico) &&
        Objects.equals(linkIncontroRemoto, incontroPreaccoglienza.linkIncontroRemoto) &&
        Objects.equals(luogoIncontro, incontroPreaccoglienza.luogoIncontro) &&
        Objects.equals(operatoreCreazione, incontroPreaccoglienza.operatoreCreazione) &&
        Objects.equals(numMaxPartecipanti, incontroPreaccoglienza.numMaxPartecipanti) &&
        Objects.equals(numPartecipantiIscritti, incontroPreaccoglienza.numPartecipantiIscritti) &&
        Objects.equals(dataIncontro, incontroPreaccoglienza.dataIncontro) &&
        Objects.equals(note, incontroPreaccoglienza.note) &&
        Objects.equals(codUserInserim, incontroPreaccoglienza.codUserInserim) &&
        Objects.equals(dataInserim, incontroPreaccoglienza.dataInserim) &&
        Objects.equals(codUserAggiorn, incontroPreaccoglienza.codUserAggiorn) &&
        Objects.equals(dataAggiorn, incontroPreaccoglienza.dataAggiorn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, denominazione, flgIncontroErogatoDaRemoto, flgIncontroTelefonico, linkIncontroRemoto, luogoIncontro, operatoreCreazione, numMaxPartecipanti, numPartecipantiIscritti, dataIncontro, note, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IncontroPreaccoglienza {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    flgIncontroErogatoDaRemoto: ").append(toIndentedString(flgIncontroErogatoDaRemoto)).append("\n");
    sb.append("    flgIncontroTelefonico: ").append(toIndentedString(flgIncontroTelefonico)).append("\n");
    sb.append("    linkIncontroRemoto: ").append(toIndentedString(linkIncontroRemoto)).append("\n");
    sb.append("    luogoIncontro: ").append(toIndentedString(luogoIncontro)).append("\n");
    sb.append("    operatoreCreazione: ").append(toIndentedString(operatoreCreazione)).append("\n");
    sb.append("    numMaxPartecipanti: ").append(toIndentedString(numMaxPartecipanti)).append("\n");
    sb.append("    numPartecipantiIscritti: ").append(toIndentedString(numPartecipantiIscritti)).append("\n");
    sb.append("    dataIncontro: ").append(toIndentedString(dataIncontro)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
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
