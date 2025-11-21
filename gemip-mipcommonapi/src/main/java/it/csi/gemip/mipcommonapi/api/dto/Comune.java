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
import it.csi.gemip.mipcommonapi.api.dto.Provincia;
import jakarta.validation.constraints.*;


public class Comune   {
  private String codiceIstatComune = null;
  private Provincia provincia = null;
  private String descrizioneComune = null;
  private String cap = null;
  private String codiceFiscaleComune = null;

  /**
   **/
  
  @JsonProperty("codiceIstatComune")
  @jakarta.validation.Valid
  public String getCodiceIstatComune() {
    return codiceIstatComune;
  }
  public void setCodiceIstatComune(String codiceIstatComune) {
    this.codiceIstatComune = codiceIstatComune;
  }

  /**
   **/
  
  @JsonProperty("provincia")
  @jakarta.validation.Valid
  public Provincia getProvincia() {
    return provincia;
  }
  public void setProvincia(Provincia provincia) {
    this.provincia = provincia;
  }

  /**
   **/
  
  @JsonProperty("descrizioneComune")
  @jakarta.validation.Valid
  public String getDescrizioneComune() {
    return descrizioneComune;
  }
  public void setDescrizioneComune(String descrizioneComune) {
    this.descrizioneComune = descrizioneComune;
  }

  /**
   **/
  
  @JsonProperty("cap")
  @jakarta.validation.Valid
  public String getCap() {
    return cap;
  }
  public void setCap(String cap) {
    this.cap = cap;
  }

  /**
   **/
  
  @JsonProperty("codiceFiscaleComune")
  @jakarta.validation.Valid
  public String getCodiceFiscaleComune() {
    return codiceFiscaleComune;
  }
  public void setCodiceFiscaleComune(String codiceFiscaleComune) {
    this.codiceFiscaleComune = codiceFiscaleComune;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comune comune = (Comune) o;
    return Objects.equals(codiceIstatComune, comune.codiceIstatComune) &&
        Objects.equals(provincia, comune.provincia) &&
        Objects.equals(descrizioneComune, comune.descrizioneComune) &&
        Objects.equals(cap, comune.cap) &&
        Objects.equals(codiceFiscaleComune, comune.codiceFiscaleComune);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceIstatComune, provincia, descrizioneComune, cap, codiceFiscaleComune);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Comune {\n");
    
    sb.append("    codiceIstatComune: ").append(toIndentedString(codiceIstatComune)).append("\n");
    sb.append("    provincia: ").append(toIndentedString(provincia)).append("\n");
    sb.append("    descrizioneComune: ").append(toIndentedString(descrizioneComune)).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    codiceFiscaleComune: ").append(toIndentedString(codiceFiscaleComune)).append("\n");
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
