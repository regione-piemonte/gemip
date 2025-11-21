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
import java.math.BigDecimal;
import jakarta.validation.constraints.*;

public class InfoOperatore   {
  private BigDecimal codDbOperatore = null;
  private Integer codOperatore = null;
  private String descrOperatore = null;
  private String gruppoOperatore = null;
  private String areaTerritoriale = null;

  /**
   **/
  
  @JsonProperty("codDbOperatore")
  public BigDecimal getCodDbOperatore() {
    return codDbOperatore;
  }
  public void setCodDbOperatore(BigDecimal codDbOperatore) {
    this.codDbOperatore = codDbOperatore;
  }

  /**
   **/
  
  @JsonProperty("codOperatore")
  public Integer getCodOperatore() {
    return codOperatore;
  }
  public void setCodOperatore(Integer codOperatore) {
    this.codOperatore = codOperatore;
  }

  /**
   **/
  
  @JsonProperty("descrOperatore")
  public String getDescrOperatore() {
    return descrOperatore;
  }
  public void setDescrOperatore(String descrOperatore) {
    this.descrOperatore = descrOperatore;
  }

  /**
   **/
  
  @JsonProperty("gruppoOperatore")
  public String getGruppoOperatore() {
    return gruppoOperatore;
  }
  public void setGruppoOperatore(String gruppoOperatore) {
    this.gruppoOperatore = gruppoOperatore;
  }

  /**
   **/
  
  @JsonProperty("areaTerritoriale")
  public String getAreaTerritoriale() {
    return areaTerritoriale;
  }
  public void setAreaTerritoriale(String areaTerritoriale) {
    this.areaTerritoriale = areaTerritoriale;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoOperatore infoOperatore = (InfoOperatore) o;
    return Objects.equals(codDbOperatore, infoOperatore.codDbOperatore) &&
        Objects.equals(codOperatore, infoOperatore.codOperatore) &&
        Objects.equals(descrOperatore, infoOperatore.descrOperatore) &&
        Objects.equals(gruppoOperatore, infoOperatore.gruppoOperatore) &&
        Objects.equals(areaTerritoriale, infoOperatore.areaTerritoriale);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codDbOperatore, codOperatore, descrOperatore, gruppoOperatore, areaTerritoriale);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoOperatore {\n");
    
    sb.append("    codDbOperatore: ").append(toIndentedString(codDbOperatore)).append("\n");
    sb.append("    codOperatore: ").append(toIndentedString(codOperatore)).append("\n");
    sb.append("    descrOperatore: ").append(toIndentedString(descrOperatore)).append("\n");
    sb.append("    gruppoOperatore: ").append(toIndentedString(gruppoOperatore)).append("\n");
    sb.append("    areaTerritoriale: ").append(toIndentedString(areaTerritoriale)).append("\n");
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
