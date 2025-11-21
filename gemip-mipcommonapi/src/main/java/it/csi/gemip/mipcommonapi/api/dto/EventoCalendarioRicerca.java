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
import it.csi.gemip.mipcommonapi.api.dto.EventoCalendarioWrapper;
import java.util.List;
import jakarta.validation.constraints.*;


public class EventoCalendarioRicerca   {
  private List<EventoCalendarioWrapper> eventiCalendario = new ArrayList<EventoCalendarioWrapper>();
  private Long totRis = null;

  /**
   **/
  
  @JsonProperty("eventiCalendario")
  @jakarta.validation.Valid
  public List<EventoCalendarioWrapper> getEventiCalendario() {
    return eventiCalendario;
  }
  public void setEventiCalendario(List<EventoCalendarioWrapper> eventiCalendario) {
    this.eventiCalendario = eventiCalendario;
  }

  /**
   **/
  
  @JsonProperty("totRis")
  @jakarta.validation.Valid
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
    EventoCalendarioRicerca eventoCalendarioRicerca = (EventoCalendarioRicerca) o;
    return Objects.equals(eventiCalendario, eventoCalendarioRicerca.eventiCalendario) &&
        Objects.equals(totRis, eventoCalendarioRicerca.totRis);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eventiCalendario, totRis);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventoCalendarioRicerca {\n");
    
    sb.append("    eventiCalendario: ").append(toIndentedString(eventiCalendario)).append("\n");
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
