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
import it.csi.gemip.mipbackofficebff.bff.dto.Cittadino;
import it.csi.gemip.mipbackofficebff.bff.dto.IdeaDiImpresa;
import it.csi.gemip.mipbackofficebff.bff.dto.Operatore;
import it.csi.gemip.mipbackofficebff.bff.dto.Tutor;
import jakarta.validation.constraints.*;

public class IdeaDiImpresaRicerca   {
  private IdeaDiImpresa ideaDiImpresa = null;
  private Cittadino citadino = null;
  private AreaTerritoriale areaTerritoriale = null;
  private Tutor tutor = null;
  private Operatore operatore = null;

  /**
   **/
  
  @JsonProperty("ideaDiImpresa")
  public IdeaDiImpresa getIdeaDiImpresa() {
    return ideaDiImpresa;
  }
  public void setIdeaDiImpresa(IdeaDiImpresa ideaDiImpresa) {
    this.ideaDiImpresa = ideaDiImpresa;
  }

  /**
   **/
  
  @JsonProperty("citadino")
  public Cittadino getCitadino() {
    return citadino;
  }
  public void setCitadino(Cittadino citadino) {
    this.citadino = citadino;
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

  /**
   **/
  
  @JsonProperty("tutor")
  public Tutor getTutor() {
    return tutor;
  }
  public void setTutor(Tutor tutor) {
    this.tutor = tutor;
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
    IdeaDiImpresaRicerca ideaDiImpresaRicerca = (IdeaDiImpresaRicerca) o;
    return Objects.equals(ideaDiImpresa, ideaDiImpresaRicerca.ideaDiImpresa) &&
        Objects.equals(citadino, ideaDiImpresaRicerca.citadino) &&
        Objects.equals(areaTerritoriale, ideaDiImpresaRicerca.areaTerritoriale) &&
        Objects.equals(tutor, ideaDiImpresaRicerca.tutor) &&
        Objects.equals(operatore, ideaDiImpresaRicerca.operatore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ideaDiImpresa, citadino, areaTerritoriale, tutor, operatore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdeaDiImpresaRicerca {\n");
    
    sb.append("    ideaDiImpresa: ").append(toIndentedString(ideaDiImpresa)).append("\n");
    sb.append("    citadino: ").append(toIndentedString(citadino)).append("\n");
    sb.append("    areaTerritoriale: ").append(toIndentedString(areaTerritoriale)).append("\n");
    sb.append("    tutor: ").append(toIndentedString(tutor)).append("\n");
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
