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
import it.csi.gemip.mipcittadinobff.bff.dto.EventoCalendario;
import it.csi.gemip.mipcittadinobff.bff.dto.Operatore;
import it.csi.gemip.mipcittadinobff.bff.dto.SoggettoAttuatore;
import jakarta.validation.constraints.*;

public class EventoCalendarioWrapper   {
  private EventoCalendario evento = null;
  private SoggettoAttuatore soggettoAttuatore = null;
  private Operatore operatore = null;

  /**
   **/
  
  @JsonProperty("evento")
  public EventoCalendario getEvento() {
    return evento;
  }
  public void setEvento(EventoCalendario evento) {
    this.evento = evento;
  }

  /**
   **/
  
  @JsonProperty("soggettoAttuatore")
  public SoggettoAttuatore getSoggettoAttuatore() {
    return soggettoAttuatore;
  }
  public void setSoggettoAttuatore(SoggettoAttuatore soggettoAttuatore) {
    this.soggettoAttuatore = soggettoAttuatore;
  }

  /**
   **/
  
  @JsonProperty("operatore")
  public Operatore getOperatore() {
    return operatore;
  }
  public void setOperatore(Operatore operatore) {
    this.operatore = operatore;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventoCalendarioWrapper eventoCalendarioWrapper = (EventoCalendarioWrapper) o;
    return Objects.equals(evento, eventoCalendarioWrapper.evento) &&
        Objects.equals(soggettoAttuatore, eventoCalendarioWrapper.soggettoAttuatore) &&
        Objects.equals(operatore, eventoCalendarioWrapper.operatore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(evento, soggettoAttuatore, operatore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class EventoCalendarioWrapper {\n");
    
    sb.append("    evento: ").append(toIndentedString(evento)).append("\n");
    sb.append("    soggettoAttuatore: ").append(toIndentedString(soggettoAttuatore)).append("\n");
    sb.append("    operatore: ").append(toIndentedString(operatore)).append("\n");
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
