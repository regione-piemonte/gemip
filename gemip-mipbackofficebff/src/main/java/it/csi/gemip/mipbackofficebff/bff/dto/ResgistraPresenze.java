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
import jakarta.validation.constraints.*;

public class ResgistraPresenze   {
  private Long idCittadino = null;
  private Long idIncotroPreaccoglienza = null;
  private String stato = null;

  /**
   **/
  
  @JsonProperty("idCittadino")
  public Long getIdCittadino() {
    return idCittadino;
  }
  public void setIdCittadino(Long idCittadino) {
    this.idCittadino = idCittadino;
  }

  /**
   **/
  
  @JsonProperty("idIncotroPreaccoglienza")
  public Long getIdIncotroPreaccoglienza() {
    return idIncotroPreaccoglienza;
  }
  public void setIdIncotroPreaccoglienza(Long idIncotroPreaccoglienza) {
    this.idIncotroPreaccoglienza = idIncotroPreaccoglienza;
  }

  /**
   **/
  
  @JsonProperty("stato")
  public String getStato() {
    return stato;
  }
  public void setStato(String stato) {
    this.stato = stato;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResgistraPresenze resgistraPresenze = (ResgistraPresenze) o;
    return Objects.equals(idCittadino, resgistraPresenze.idCittadino) &&
        Objects.equals(idIncotroPreaccoglienza, resgistraPresenze.idIncotroPreaccoglienza) &&
        Objects.equals(stato, resgistraPresenze.stato);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idCittadino, idIncotroPreaccoglienza, stato);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ResgistraPresenze {\n");
    
    sb.append("    idCittadino: ").append(toIndentedString(idCittadino)).append("\n");
    sb.append("    idIncotroPreaccoglienza: ").append(toIndentedString(idIncotroPreaccoglienza)).append("\n");
    sb.append("    stato: ").append(toIndentedString(stato)).append("\n");
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
