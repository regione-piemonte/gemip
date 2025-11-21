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
import com.fasterxml.jackson.annotation.JsonValue;
import it.csi.gemip.mipcittadinobff.bff.dto.Risposta;
import java.util.List;
import jakarta.validation.constraints.*;

public class Domanda   {
  private Long idDomanda = null;
  private String testoDomanda = null;
  private String placeholder = null;
  /**
   * Gets or Sets tipoDomanda
   */
  public enum TipoDomandaEnum {
    APERTA("aperta"),
    CHIUSA("chiusa"),
    CHIUSA_SCELTA_MULTIPLA("chiusa_scelta_multipla");
    private String value;

    TipoDomandaEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }
  }
  private TipoDomandaEnum tipoDomanda = null;
  private List<Risposta> risposte = new ArrayList<Risposta>();
  private Long idDomandaCondizionale = null;
  private Long idRispostaCondizionale = null;

  /**
   **/
  
  @JsonProperty("idDomanda")
  @NotNull
  public Long getIdDomanda() {
    return idDomanda;
  }
  public void setIdDomanda(Long idDomanda) {
    this.idDomanda = idDomanda;
  }

  /**
   **/
  
  @JsonProperty("testoDomanda")
  @NotNull
  public String getTestoDomanda() {
    return testoDomanda;
  }
  public void setTestoDomanda(String testoDomanda) {
    this.testoDomanda = testoDomanda;
  }

  /**
   **/
  
  @JsonProperty("placeholder")
  public String getPlaceholder() {
    return placeholder;
  }
  public void setPlaceholder(String placeholder) {
    this.placeholder = placeholder;
  }

  /**
   **/
  
  @JsonProperty("tipoDomanda")
  @NotNull
  public TipoDomandaEnum getTipoDomanda() {
    return tipoDomanda;
  }
  public void setTipoDomanda(TipoDomandaEnum tipoDomanda) {
    this.tipoDomanda = tipoDomanda;
  }

  /**
   **/
  
  @JsonProperty("risposte")
  @NotNull
  public List<Risposta> getRisposte() {
    return risposte;
  }
  public void setRisposte(List<Risposta> risposte) {
    this.risposte = risposte;
  }

  /**
   **/
  
  @JsonProperty("idDomandaCondizionale")
  public Long getIdDomandaCondizionale() {
    return idDomandaCondizionale;
  }
  public void setIdDomandaCondizionale(Long idDomandaCondizionale) {
    this.idDomandaCondizionale = idDomandaCondizionale;
  }

  /**
   **/
  
  @JsonProperty("idRispostaCondizionale")
  public Long getIdRispostaCondizionale() {
    return idRispostaCondizionale;
  }
  public void setIdRispostaCondizionale(Long idRispostaCondizionale) {
    this.idRispostaCondizionale = idRispostaCondizionale;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Domanda domanda = (Domanda) o;
    return Objects.equals(idDomanda, domanda.idDomanda) &&
        Objects.equals(testoDomanda, domanda.testoDomanda) &&
        Objects.equals(placeholder, domanda.placeholder) &&
        Objects.equals(tipoDomanda, domanda.tipoDomanda) &&
        Objects.equals(risposte, domanda.risposte) &&
        Objects.equals(idDomandaCondizionale, domanda.idDomandaCondizionale) &&
        Objects.equals(idRispostaCondizionale, domanda.idRispostaCondizionale);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDomanda, testoDomanda, placeholder, tipoDomanda, risposte, idDomandaCondizionale, idRispostaCondizionale);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Domanda {\n");
    
    sb.append("    idDomanda: ").append(toIndentedString(idDomanda)).append("\n");
    sb.append("    testoDomanda: ").append(toIndentedString(testoDomanda)).append("\n");
    sb.append("    placeholder: ").append(toIndentedString(placeholder)).append("\n");
    sb.append("    tipoDomanda: ").append(toIndentedString(tipoDomanda)).append("\n");
    sb.append("    risposte: ").append(toIndentedString(risposte)).append("\n");
    sb.append("    idDomandaCondizionale: ").append(toIndentedString(idDomandaCondizionale)).append("\n");
    sb.append("    idRispostaCondizionale: ").append(toIndentedString(idRispostaCondizionale)).append("\n");
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
