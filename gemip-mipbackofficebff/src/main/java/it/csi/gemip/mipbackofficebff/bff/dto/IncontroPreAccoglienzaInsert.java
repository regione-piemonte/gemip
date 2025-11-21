package it.csi.gemip.mipbackofficebff.bff.dto;

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
import it.csi.gemip.mipbackofficebff.bff.dto.AreaTerritoriale;
import it.csi.gemip.mipbackofficebff.bff.dto.IncontroPreaccoglienza;
import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.*;

public class IncontroPreAccoglienzaInsert   {
  private IncontroPreaccoglienza incontroPreaccoglienza = null;
  private List<AreaTerritoriale> areaTerritoriale = new ArrayList<AreaTerritoriale>();
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;

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
  
  @JsonProperty("areaTerritoriale")
  public List<AreaTerritoriale> getAreaTerritoriale() {
    return areaTerritoriale;
  }
  public void setAreaTerritoriale(List<AreaTerritoriale> areaTerritoriale) {
    this.areaTerritoriale = areaTerritoriale;
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
    IncontroPreAccoglienzaInsert incontroPreAccoglienzaInsert = (IncontroPreAccoglienzaInsert) o;
    return Objects.equals(incontroPreaccoglienza, incontroPreAccoglienzaInsert.incontroPreaccoglienza) &&
        Objects.equals(areaTerritoriale, incontroPreAccoglienzaInsert.areaTerritoriale) &&
        Objects.equals(codUserInserim, incontroPreAccoglienzaInsert.codUserInserim) &&
        Objects.equals(dataInserim, incontroPreAccoglienzaInsert.dataInserim) &&
        Objects.equals(codUserAggiorn, incontroPreAccoglienzaInsert.codUserAggiorn) &&
        Objects.equals(dataAggiorn, incontroPreAccoglienzaInsert.dataAggiorn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(incontroPreaccoglienza, areaTerritoriale, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IncontroPreAccoglienzaInsert {\n");
    
    sb.append("    incontroPreaccoglienza: ").append(toIndentedString(incontroPreaccoglienza)).append("\n");
    sb.append("    areaTerritoriale: ").append(toIndentedString(areaTerritoriale)).append("\n");
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
