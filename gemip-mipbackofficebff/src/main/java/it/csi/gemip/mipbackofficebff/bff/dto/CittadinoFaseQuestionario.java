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
import it.csi.gemip.mipbackofficebff.bff.dto.Cittadino;
import it.csi.gemip.mipbackofficebff.bff.dto.FaseQuestionario;
import jakarta.validation.constraints.*;

public class CittadinoFaseQuestionario   {
  private Cittadino idCittadino = null;
  private FaseQuestionario idFaseQuestionario = null;

  /**
   **/
  
  @JsonProperty("idCittadino")
  @NotNull
  public Cittadino getIdCittadino() {
    return idCittadino;
  }
  public void setIdCittadino(Cittadino idCittadino) {
    this.idCittadino = idCittadino;
  }

  /**
   **/
  
  @JsonProperty("idFaseQuestionario")
  @NotNull
  public FaseQuestionario getIdFaseQuestionario() {
    return idFaseQuestionario;
  }
  public void setIdFaseQuestionario(FaseQuestionario idFaseQuestionario) {
    this.idFaseQuestionario = idFaseQuestionario;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CittadinoFaseQuestionario cittadinoFaseQuestionario = (CittadinoFaseQuestionario) o;
    return Objects.equals(idCittadino, cittadinoFaseQuestionario.idCittadino) &&
        Objects.equals(idFaseQuestionario, cittadinoFaseQuestionario.idFaseQuestionario);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idCittadino, idFaseQuestionario);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CittadinoFaseQuestionario {\n");
    
    sb.append("    idCittadino: ").append(toIndentedString(idCittadino)).append("\n");
    sb.append("    idFaseQuestionario: ").append(toIndentedString(idFaseQuestionario)).append("\n");
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
