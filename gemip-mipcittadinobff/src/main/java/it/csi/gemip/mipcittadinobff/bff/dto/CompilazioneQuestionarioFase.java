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
import it.csi.gemip.mipcittadinobff.bff.dto.CompilazioneQuestionario;
import it.csi.gemip.mipcittadinobff.bff.dto.FaseQuestionario;
import jakarta.validation.constraints.*;

public class CompilazioneQuestionarioFase   {
  private CompilazioneQuestionario compilazioneQuestionario = null;
  private FaseQuestionario faseQuestionario = null;

  /**
   **/
  
  @JsonProperty("compilazioneQuestionario")
  @NotNull
  public CompilazioneQuestionario getCompilazioneQuestionario() {
    return compilazioneQuestionario;
  }
  public void setCompilazioneQuestionario(CompilazioneQuestionario compilazioneQuestionario) {
    this.compilazioneQuestionario = compilazioneQuestionario;
  }

  /**
   **/
  
  @JsonProperty("faseQuestionario")
  @NotNull
  public FaseQuestionario getFaseQuestionario() {
    return faseQuestionario;
  }
  public void setFaseQuestionario(FaseQuestionario faseQuestionario) {
    this.faseQuestionario = faseQuestionario;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompilazioneQuestionarioFase compilazioneQuestionarioFase = (CompilazioneQuestionarioFase) o;
    return Objects.equals(compilazioneQuestionario, compilazioneQuestionarioFase.compilazioneQuestionario) &&
        Objects.equals(faseQuestionario, compilazioneQuestionarioFase.faseQuestionario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(compilazioneQuestionario, faseQuestionario);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompilazioneQuestionarioFase {\n");
    
    sb.append("    compilazioneQuestionario: ").append(toIndentedString(compilazioneQuestionario)).append("\n");
    sb.append("    faseQuestionario: ").append(toIndentedString(faseQuestionario)).append("\n");
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
