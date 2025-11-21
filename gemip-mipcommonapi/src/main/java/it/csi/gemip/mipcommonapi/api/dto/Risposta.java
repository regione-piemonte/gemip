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
import jakarta.validation.constraints.*;


public class Risposta   {
  private Long idRisposta = null;
  private String testoRisposta = null;
  private Boolean flgRichiestoDettaglio = null;
  private String placeholder = null;

  /**
   **/
  
  @JsonProperty("idRisposta")
  @NotNull
  @jakarta.validation.Valid
  public Long getIdRisposta() {
    return idRisposta;
  }
  public void setIdRisposta(Long idRisposta) {
    this.idRisposta = idRisposta;
  }

  /**
   **/
  
  @JsonProperty("testoRisposta")
  @NotNull
  @jakarta.validation.Valid
  public String getTestoRisposta() {
    return testoRisposta;
  }
  public void setTestoRisposta(String testoRisposta) {
    this.testoRisposta = testoRisposta;
  }

  /**
   **/
  
  @JsonProperty("flgRichiestoDettaglio")
  @NotNull
  @jakarta.validation.Valid
  public Boolean isFlgRichiestoDettaglio() {
    return flgRichiestoDettaglio;
  }
  public void setFlgRichiestoDettaglio(Boolean flgRichiestoDettaglio) {
    this.flgRichiestoDettaglio = flgRichiestoDettaglio;
  }

  /**
   **/
  
  @JsonProperty("placeholder")
  @jakarta.validation.Valid
  public String getPlaceholder() {
    return placeholder;
  }
  public void setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Risposta risposta = (Risposta) o;
    return Objects.equals(idRisposta, risposta.idRisposta) &&
        Objects.equals(testoRisposta, risposta.testoRisposta) &&
        Objects.equals(flgRichiestoDettaglio, risposta.flgRichiestoDettaglio) &&
        Objects.equals(placeholder, risposta.placeholder);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idRisposta, testoRisposta, flgRichiestoDettaglio, placeholder);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Risposta {\n");
    
    sb.append("    idRisposta: ").append(toIndentedString(idRisposta)).append("\n");
    sb.append("    testoRisposta: ").append(toIndentedString(testoRisposta)).append("\n");
    sb.append("    flgRichiestoDettaglio: ").append(toIndentedString(flgRichiestoDettaglio)).append("\n");
    sb.append("    placeholder: ").append(toIndentedString(placeholder)).append("\n");
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
