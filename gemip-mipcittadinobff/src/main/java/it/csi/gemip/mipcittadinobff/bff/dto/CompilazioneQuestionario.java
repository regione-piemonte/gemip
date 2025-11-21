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
import it.csi.gemip.mipcittadinobff.bff.dto.Cittadino;
import it.csi.gemip.mipcittadinobff.bff.dto.Questionario;
import jakarta.validation.constraints.*;

public class CompilazioneQuestionario   {
  private Long idCompilazioneQuestionario = null;
  private Cittadino cittadino = null;
  private Questionario questionario = null;

  /**
   **/
  
  @JsonProperty("idCompilazioneQuestionario")
  @NotNull
  public Long getIdCompilazioneQuestionario() {
    return idCompilazioneQuestionario;
  }
  public void setIdCompilazioneQuestionario(Long idCompilazioneQuestionario) {
    this.idCompilazioneQuestionario = idCompilazioneQuestionario;
  }

  /**
   **/
  
  @JsonProperty("cittadino")
  @NotNull
  public Cittadino getCittadino() {
    return cittadino;
  }
  public void setCittadino(Cittadino cittadino) {
    this.cittadino = cittadino;
  }

  /**
   **/
  
  @JsonProperty("questionario")
  @NotNull
  public Questionario getQuestionario() {
    return questionario;
  }
  public void setQuestionario(Questionario questionario) {
    this.questionario = questionario;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompilazioneQuestionario compilazioneQuestionario = (CompilazioneQuestionario) o;
    return Objects.equals(idCompilazioneQuestionario, compilazioneQuestionario.idCompilazioneQuestionario) &&
        Objects.equals(cittadino, compilazioneQuestionario.cittadino) &&
        Objects.equals(questionario, compilazioneQuestionario.questionario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idCompilazioneQuestionario, cittadino, questionario);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompilazioneQuestionario {\n");
    
    sb.append("    idCompilazioneQuestionario: ").append(toIndentedString(idCompilazioneQuestionario)).append("\n");
    sb.append("    cittadino: ").append(toIndentedString(cittadino)).append("\n");
    sb.append("    questionario: ").append(toIndentedString(questionario)).append("\n");
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
