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
import it.csi.gemip.mipbackofficebff.bff.dto.IncontroPreaccoglienza;
import java.util.List;
import jakarta.validation.constraints.*;

public class IncontroPreaccoglienzaRicerca   {
  private List<IncontroPreaccoglienza> incontriPreaccoglienza = new ArrayList<IncontroPreaccoglienza>();
  private Long totaleIncontri = null;

  /**
   **/
  
  @JsonProperty("incontriPreaccoglienza")
  public List<IncontroPreaccoglienza> getIncontriPreaccoglienza() {
    return incontriPreaccoglienza;
  }
  public void setIncontriPreaccoglienza(List<IncontroPreaccoglienza> incontriPreaccoglienza) {
    this.incontriPreaccoglienza = incontriPreaccoglienza;
  }

  /**
   **/
  
  @JsonProperty("totaleIncontri")
  public Long getTotaleIncontri() {
    return totaleIncontri;
  }
  public void setTotaleIncontri(Long totaleIncontri) {
    this.totaleIncontri = totaleIncontri;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IncontroPreaccoglienzaRicerca incontroPreaccoglienzaRicerca = (IncontroPreaccoglienzaRicerca) o;
    return Objects.equals(incontriPreaccoglienza, incontroPreaccoglienzaRicerca.incontriPreaccoglienza) &&
        Objects.equals(totaleIncontri, incontroPreaccoglienzaRicerca.totaleIncontri);
  }

  @Override
  public int hashCode() {
    return Objects.hash(incontriPreaccoglienza, totaleIncontri);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IncontroPreaccoglienzaRicerca {\n");
    
    sb.append("    incontriPreaccoglienza: ").append(toIndentedString(incontriPreaccoglienza)).append("\n");
    sb.append("    totaleIncontri: ").append(toIndentedString(totaleIncontri)).append("\n");
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
