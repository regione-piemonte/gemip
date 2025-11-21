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
import it.csi.gemip.mipbackofficebff.bff.dto.AreaTerritoriale;
import it.csi.gemip.mipbackofficebff.bff.dto.Comune;
import jakarta.validation.constraints.*;

public class LuogoIncontro   {
  private Long id = null;
  private String denominazione = null;
  private String indirizzo = null;
  private String cap = null;
  private Comune comune = null;
  private AreaTerritoriale areaTerritoriale = null;

  /**
   **/
  
  @JsonProperty("id")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   **/
  
  @JsonProperty("denominazione")
  public String getDenominazione() {
    return denominazione;
  }
  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
  }

  /**
   **/
  
  @JsonProperty("indirizzo")
  public String getIndirizzo() {
    return indirizzo;
  }
  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  /**
   **/
  
  @JsonProperty("cap")
  public String getCap() {
    return cap;
  }
  public void setCap(String cap) {
    this.cap = cap;
  }

  /**
   **/
  
  @JsonProperty("comune")
  public Comune getComune() {
    return comune;
  }
  public void setComune(Comune comune) {
    this.comune = comune;
  }

  /**
   **/
  
  @JsonProperty("areaTerritoriale")
  public AreaTerritoriale getAreaTerritoriale() {
    return areaTerritoriale;
  }
  public void setAreaTerritoriale(AreaTerritoriale areaTerritoriale) {
    this.areaTerritoriale = areaTerritoriale;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LuogoIncontro luogoIncontro = (LuogoIncontro) o;
    return Objects.equals(id, luogoIncontro.id) &&
        Objects.equals(denominazione, luogoIncontro.denominazione) &&
        Objects.equals(indirizzo, luogoIncontro.indirizzo) &&
        Objects.equals(cap, luogoIncontro.cap) &&
        Objects.equals(comune, luogoIncontro.comune) &&
        Objects.equals(areaTerritoriale, luogoIncontro.areaTerritoriale);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, denominazione, indirizzo, cap, comune, areaTerritoriale);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LuogoIncontro {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    comune: ").append(toIndentedString(comune)).append("\n");
    sb.append("    areaTerritoriale: ").append(toIndentedString(areaTerritoriale)).append("\n");
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
