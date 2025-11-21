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
import it.csi.gemip.mipcommonapi.api.dto.Nazionalita;
import jakarta.validation.constraints.*;


public class Cittadinanza   {
  private String codiceCittadinanza = null;
  private String descrizione = null;
  private Nazionalita codiceNazionalita = null;
  private String codiceCittadinanzaOld = null;
  private String descrizionePrecedente = null;

  /**
   **/
  
  @JsonProperty("codiceCittadinanza")
  @jakarta.validation.Valid
  public String getCodiceCittadinanza() {
    return codiceCittadinanza;
  }
  public void setCodiceCittadinanza(String codiceCittadinanza) {
    this.codiceCittadinanza = codiceCittadinanza;
  }

  /**
   **/
  
  @JsonProperty("descrizione")
  @jakarta.validation.Valid
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   **/
  
  @JsonProperty("codiceNazionalita")
  @jakarta.validation.Valid
  public Nazionalita getCodiceNazionalita() {
    return codiceNazionalita;
  }
  public void setCodiceNazionalita(Nazionalita codiceNazionalita) {
    this.codiceNazionalita = codiceNazionalita;
  }

  /**
   **/
  
  @JsonProperty("codiceCittadinanzaOld")
  @jakarta.validation.Valid
  public String getCodiceCittadinanzaOld() {
    return codiceCittadinanzaOld;
  }
  public void setCodiceCittadinanzaOld(String codiceCittadinanzaOld) {
    this.codiceCittadinanzaOld = codiceCittadinanzaOld;
  }

  /**
   **/
  
  @JsonProperty("descrizionePrecedente")
  @jakarta.validation.Valid
  public String getDescrizionePrecedente() {
    return descrizionePrecedente;
  }
  public void setDescrizionePrecedente(String descrizionePrecedente) {
    this.descrizionePrecedente = descrizionePrecedente;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cittadinanza cittadinanza = (Cittadinanza) o;
    return Objects.equals(codiceCittadinanza, cittadinanza.codiceCittadinanza) &&
        Objects.equals(descrizione, cittadinanza.descrizione) &&
        Objects.equals(codiceNazionalita, cittadinanza.codiceNazionalita) &&
        Objects.equals(codiceCittadinanzaOld, cittadinanza.codiceCittadinanzaOld) &&
        Objects.equals(descrizionePrecedente, cittadinanza.descrizionePrecedente);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceCittadinanza, descrizione, codiceNazionalita, codiceCittadinanzaOld, descrizionePrecedente);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Cittadinanza {\n");
    
    sb.append("    codiceCittadinanza: ").append(toIndentedString(codiceCittadinanza)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    codiceNazionalita: ").append(toIndentedString(codiceNazionalita)).append("\n");
    sb.append("    codiceCittadinanzaOld: ").append(toIndentedString(codiceCittadinanzaOld)).append("\n");
    sb.append("    descrizionePrecedente: ").append(toIndentedString(descrizionePrecedente)).append("\n");
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
