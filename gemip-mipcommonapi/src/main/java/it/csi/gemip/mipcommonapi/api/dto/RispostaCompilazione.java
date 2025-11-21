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


public class RispostaCompilazione   {
  private Long idDomanda = null;
  private String rispostaLibera = null;
  private Long idRispostaScelta = null;

  /**
   **/
  
  @JsonProperty("idDomanda")
  @NotNull
  @jakarta.validation.Valid
  public Long getIdDomanda() {
    return idDomanda;
  }
  public void setIdDomanda(Long idDomanda) {
    this.idDomanda = idDomanda;
  }

  /**
   **/
  
  @JsonProperty("rispostaLibera")
  @jakarta.validation.Valid
  public String getRispostaLibera() {
    return rispostaLibera;
  }
  public void setRispostaLibera(String rispostaLibera) {
    this.rispostaLibera = rispostaLibera;
  }

  /**
   **/
  
  @JsonProperty("idRispostaScelta")
  @jakarta.validation.Valid
  public Long getIdRispostaScelta() {
    return idRispostaScelta;
  }
  public void setIdRispostaScelta(Long idRispostaScelta) {
    this.idRispostaScelta = idRispostaScelta;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RispostaCompilazione rispostaCompilazione = (RispostaCompilazione) o;
    return Objects.equals(idDomanda, rispostaCompilazione.idDomanda) &&
        Objects.equals(rispostaLibera, rispostaCompilazione.rispostaLibera) &&
        Objects.equals(idRispostaScelta, rispostaCompilazione.idRispostaScelta);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDomanda, rispostaLibera, idRispostaScelta);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RispostaCompilazione {\n");
    
    sb.append("    idDomanda: ").append(toIndentedString(idDomanda)).append("\n");
    sb.append("    rispostaLibera: ").append(toIndentedString(rispostaLibera)).append("\n");
    sb.append("    idRispostaScelta: ").append(toIndentedString(idRispostaScelta)).append("\n");
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
