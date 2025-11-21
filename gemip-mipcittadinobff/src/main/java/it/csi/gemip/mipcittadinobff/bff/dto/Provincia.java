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

public class Provincia   {
  private String codiceProvincia = null;
  private String codiceRegione = null;
  private String descrizioneProvincia = null;
  private String siglaProvincia = null;

  /**
   **/
  
  @JsonProperty("codiceProvincia")
  public String getCodiceProvincia() {
    return codiceProvincia;
  }
  public void setCodiceProvincia(String codiceProvincia) {
    this.codiceProvincia = codiceProvincia;
  }

  /**
   **/
  
  @JsonProperty("codiceRegione")
  public String getCodiceRegione() {
    return codiceRegione;
  }
  public void setCodiceRegione(String codiceRegione) {
    this.codiceRegione = codiceRegione;
  }

  /**
   **/
  
  @JsonProperty("descrizioneProvincia")
  public String getDescrizioneProvincia() {
    return descrizioneProvincia;
  }
  public void setDescrizioneProvincia(String descrizioneProvincia) {
    this.descrizioneProvincia = descrizioneProvincia;
  }

  /**
   **/
  
  @JsonProperty("siglaProvincia")
  public String getSiglaProvincia() {
    return siglaProvincia;
  }
  public void setSiglaProvincia(String siglaProvincia) {
    this.siglaProvincia = siglaProvincia;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Provincia provincia = (Provincia) o;
    return Objects.equals(codiceProvincia, provincia.codiceProvincia) &&
        Objects.equals(codiceRegione, provincia.codiceRegione) &&
        Objects.equals(descrizioneProvincia, provincia.descrizioneProvincia) &&
        Objects.equals(siglaProvincia, provincia.siglaProvincia);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceProvincia, codiceRegione, descrizioneProvincia, siglaProvincia);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Provincia {\n");
    
    sb.append("    codiceProvincia: ").append(toIndentedString(codiceProvincia)).append("\n");
    sb.append("    codiceRegione: ").append(toIndentedString(codiceRegione)).append("\n");
    sb.append("    descrizioneProvincia: ").append(toIndentedString(descrizioneProvincia)).append("\n");
    sb.append("    siglaProvincia: ").append(toIndentedString(siglaProvincia)).append("\n");
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
