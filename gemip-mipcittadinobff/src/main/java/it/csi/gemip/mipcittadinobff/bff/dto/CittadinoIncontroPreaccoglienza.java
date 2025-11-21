package it.csi.gemip.mipcittadinobff.bff.dto;

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
import it.csi.gemip.mipcittadinobff.bff.dto.AreaTerritoriale;
import it.csi.gemip.mipcittadinobff.bff.dto.Cittadino;
import it.csi.gemip.mipcittadinobff.bff.dto.IncontroPreaccoglienza;
import java.util.Date;
import jakarta.validation.constraints.*;

public class CittadinoIncontroPreaccoglienza   {
  private Long id = null;
  private Cittadino cittadino = null;
  private IncontroPreaccoglienza incontroPreaccoglienza = null;
  private String flgCittadinoPresente = null;
  private Date dAnnullamento = null;
  private String note = null;
  private AreaTerritoriale codiceAreaTerritorialeSelezionata = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;

  /**
   **/
  
  @JsonProperty("id")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   **/
  
  @JsonProperty("cittadino")
  public Cittadino getCittadino() {
    return cittadino;
  }
  public void setCittadino(Cittadino cittadino) {
    this.cittadino = cittadino;
  }

  /**
   **/
  
  @JsonProperty("incontroPreaccoglienza")
  public IncontroPreaccoglienza getIncontroPreaccoglienza() {
    return incontroPreaccoglienza;
  }
  public void setIncontroPreaccoglienza(IncontroPreaccoglienza incontroPreaccoglienza) {
    this.incontroPreaccoglienza = incontroPreaccoglienza;
  }

  /**
   **/
  
  @JsonProperty("flgCittadinoPresente")
  public String getFlgCittadinoPresente() {
    return flgCittadinoPresente;
  }
  public void setFlgCittadinoPresente(String flgCittadinoPresente) {
    this.flgCittadinoPresente = flgCittadinoPresente;
  }

  /**
   **/
  
  @JsonProperty("dAnnullamento")
  public Date getDAnnullamento() {
    return dAnnullamento;
  }
  public void setDAnnullamento(Date dAnnullamento) {
    this.dAnnullamento = dAnnullamento;
  }

  /**
   **/
  
  @JsonProperty("note")
  public String getNote() {
    return note;
  }
  public void setNote(String note) {
    this.note = note;
  }

  /**
   **/
  
  @JsonProperty("codiceAreaTerritorialeSelezionata")
  public AreaTerritoriale getCodiceAreaTerritorialeSelezionata() {
    return codiceAreaTerritorialeSelezionata;
  }
  public void setCodiceAreaTerritorialeSelezionata(AreaTerritoriale codiceAreaTerritorialeSelezionata) {
    this.codiceAreaTerritorialeSelezionata = codiceAreaTerritorialeSelezionata;
  }

  /**
   **/
  
  @JsonProperty("codUserInserim")
  public String getCodUserInserim() {
    return codUserInserim;
  }
  public void setCodUserInserim(String codUserInserim) {
    this.codUserInserim = codUserInserim;
  }

  /**
   **/
  
  @JsonProperty("dataInserim")
  public Date getDataInserim() {
    return dataInserim;
  }
  public void setDataInserim(Date dataInserim) {
    this.dataInserim = dataInserim;
  }

  /**
   **/
  
  @JsonProperty("codUserAggiorn")
  public String getCodUserAggiorn() {
    return codUserAggiorn;
  }
  public void setCodUserAggiorn(String codUserAggiorn) {
    this.codUserAggiorn = codUserAggiorn;
  }

  /**
   **/
  
  @JsonProperty("dataAggiorn")
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
    CittadinoIncontroPreaccoglienza cittadinoIncontroPreaccoglienza = (CittadinoIncontroPreaccoglienza) o;
    return Objects.equals(id, cittadinoIncontroPreaccoglienza.id) &&
        Objects.equals(cittadino, cittadinoIncontroPreaccoglienza.cittadino) &&
        Objects.equals(incontroPreaccoglienza, cittadinoIncontroPreaccoglienza.incontroPreaccoglienza) &&
        Objects.equals(flgCittadinoPresente, cittadinoIncontroPreaccoglienza.flgCittadinoPresente) &&
        Objects.equals(dAnnullamento, cittadinoIncontroPreaccoglienza.dAnnullamento) &&
        Objects.equals(note, cittadinoIncontroPreaccoglienza.note) &&
        Objects.equals(codiceAreaTerritorialeSelezionata, cittadinoIncontroPreaccoglienza.codiceAreaTerritorialeSelezionata) &&
        Objects.equals(codUserInserim, cittadinoIncontroPreaccoglienza.codUserInserim) &&
        Objects.equals(dataInserim, cittadinoIncontroPreaccoglienza.dataInserim) &&
        Objects.equals(codUserAggiorn, cittadinoIncontroPreaccoglienza.codUserAggiorn) &&
        Objects.equals(dataAggiorn, cittadinoIncontroPreaccoglienza.dataAggiorn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cittadino, incontroPreaccoglienza, flgCittadinoPresente, dAnnullamento, note, codiceAreaTerritorialeSelezionata, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CittadinoIncontroPreaccoglienza {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    cittadino: ").append(toIndentedString(cittadino)).append("\n");
    sb.append("    incontroPreaccoglienza: ").append(toIndentedString(incontroPreaccoglienza)).append("\n");
    sb.append("    flgCittadinoPresente: ").append(toIndentedString(flgCittadinoPresente)).append("\n");
    sb.append("    dAnnullamento: ").append(toIndentedString(dAnnullamento)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    codiceAreaTerritorialeSelezionata: ").append(toIndentedString(codiceAreaTerritorialeSelezionata)).append("\n");
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
