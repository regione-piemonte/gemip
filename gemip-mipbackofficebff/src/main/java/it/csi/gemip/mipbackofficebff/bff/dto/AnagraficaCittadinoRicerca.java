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
import it.csi.gemip.mipbackofficebff.bff.dto.AnagraficaCittadino;
import java.util.List;
import jakarta.validation.constraints.*;

public class AnagraficaCittadinoRicerca   {
  private List<AnagraficaCittadino> anagrafiche = new ArrayList<AnagraficaCittadino>();
  private Long totRis = null;

  /**
   **/
  
  @JsonProperty("anagrafiche")
  public List<AnagraficaCittadino> getAnagrafiche() {
    return anagrafiche;
  }
  public void setAnagrafiche(List<AnagraficaCittadino> anagrafiche) {
    this.anagrafiche = anagrafiche;
  }

  /**
   **/
  
  @JsonProperty("totRis")
  public Long getTotRis() {
    return totRis;
  }
  public void setTotRis(Long totRis) {
    this.totRis = totRis;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AnagraficaCittadinoRicerca anagraficaCittadinoRicerca = (AnagraficaCittadinoRicerca) o;
    return Objects.equals(anagrafiche, anagraficaCittadinoRicerca.anagrafiche) &&
        Objects.equals(totRis, anagraficaCittadinoRicerca.totRis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(anagrafiche, totRis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnagraficaCittadinoRicerca {\n");
    
    sb.append("    anagrafiche: ").append(toIndentedString(anagrafiche)).append("\n");
    sb.append("    totRis: ").append(toIndentedString(totRis)).append("\n");
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
