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
import jakarta.validation.constraints.*;

public class AreaTerritoriale   {
  private String codiceAreaTerritoriale = null;
  private String descrizioneAreaTerritoriale = null;
  private String descrizioneBreveAreaTerritoriale = null;

  /**
   **/
  
  @JsonProperty("codiceAreaTerritoriale")
  public String getCodiceAreaTerritoriale() {
    return codiceAreaTerritoriale;
  }
  public void setCodiceAreaTerritoriale(String codiceAreaTerritoriale) {
    this.codiceAreaTerritoriale = codiceAreaTerritoriale;
  }

  /**
   **/
  
  @JsonProperty("descrizioneAreaTerritoriale")
  public String getDescrizioneAreaTerritoriale() {
    return descrizioneAreaTerritoriale;
  }
  public void setDescrizioneAreaTerritoriale(String descrizioneAreaTerritoriale) {
    this.descrizioneAreaTerritoriale = descrizioneAreaTerritoriale;
  }

  /**
   **/
  
  @JsonProperty("descrizioneBreveAreaTerritoriale")
  public String getDescrizioneBreveAreaTerritoriale() {
    return descrizioneBreveAreaTerritoriale;
  }
  public void setDescrizioneBreveAreaTerritoriale(String descrizioneBreveAreaTerritoriale) {
    this.descrizioneBreveAreaTerritoriale = descrizioneBreveAreaTerritoriale;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AreaTerritoriale areaTerritoriale = (AreaTerritoriale) o;
    return Objects.equals(codiceAreaTerritoriale, areaTerritoriale.codiceAreaTerritoriale) &&
        Objects.equals(descrizioneAreaTerritoriale, areaTerritoriale.descrizioneAreaTerritoriale) &&
        Objects.equals(descrizioneBreveAreaTerritoriale, areaTerritoriale.descrizioneBreveAreaTerritoriale);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceAreaTerritoriale, descrizioneAreaTerritoriale, descrizioneBreveAreaTerritoriale);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AreaTerritoriale {\n");
    
    sb.append("    codiceAreaTerritoriale: ").append(toIndentedString(codiceAreaTerritoriale)).append("\n");
    sb.append("    descrizioneAreaTerritoriale: ").append(toIndentedString(descrizioneAreaTerritoriale)).append("\n");
    sb.append("    descrizioneBreveAreaTerritoriale: ").append(toIndentedString(descrizioneBreveAreaTerritoriale)).append("\n");
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
