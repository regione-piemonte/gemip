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
import it.csi.gemip.mipcittadinobff.bff.dto.Domanda;
import java.util.List;
import jakarta.validation.constraints.*;

public class Questionario   {
  private Long idQuestionario = null;
  private List<Domanda> domande = new ArrayList<Domanda>();

  /**
   **/
  
  @JsonProperty("idQuestionario")
  @NotNull
  public Long getIdQuestionario() {
    return idQuestionario;
  }
  public void setIdQuestionario(Long idQuestionario) {
    this.idQuestionario = idQuestionario;
  }

  /**
   **/
  
  @JsonProperty("domande")
  @NotNull
  public List<Domanda> getDomande() {
    return domande;
  }
  public void setDomande(List<Domanda> domande) {
    this.domande = domande;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Questionario questionario = (Questionario) o;
    return Objects.equals(idQuestionario, questionario.idQuestionario) &&
        Objects.equals(domande, questionario.domande);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idQuestionario, domande);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Questionario {\n");
    
    sb.append("    idQuestionario: ").append(toIndentedString(idQuestionario)).append("\n");
    sb.append("    domande: ").append(toIndentedString(domande)).append("\n");
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
