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
import it.csi.gemip.mipcommonapi.api.dto.InfoOperatore;
import java.util.List;
import jakarta.validation.constraints.*;


public class Ruolo   {
  private String codiceDominio = null;
  private String codiceRuolo = null;
  private String mnemonico = null;
  private String descrizione = null;
  private List<InfoOperatore> entiAssociati = new ArrayList<InfoOperatore>();

  /**
   * codiceDominio
   **/
  
  @JsonProperty("codiceDominio")
  @jakarta.validation.Valid
  public String getCodiceDominio() {
    return codiceDominio;
  }
  public void setCodiceDominio(String codiceDominio) {
    this.codiceDominio = codiceDominio;
  }

  /**
   * codiceRuolo
   **/
  
  @JsonProperty("codiceRuolo")
  @jakarta.validation.Valid
  public String getCodiceRuolo() {
    return codiceRuolo;
  }
  public void setCodiceRuolo(String codiceRuolo) {
    this.codiceRuolo = codiceRuolo;
  }

  /**
   * mnemonico
   **/
  
  @JsonProperty("mnemonico")
  @jakarta.validation.Valid
  public String getMnemonico() {
    return mnemonico;
  }
  public void setMnemonico(String mnemonico) {
    this.mnemonico = mnemonico;
  }

  /**
   * descrizione
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
  
  @JsonProperty("entiAssociati")
  @jakarta.validation.Valid
  public List<InfoOperatore> getEntiAssociati() {
    return entiAssociati;
  }
  public void setEntiAssociati(List<InfoOperatore> entiAssociati) {
    this.entiAssociati = entiAssociati;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ruolo ruolo = (Ruolo) o;
    return Objects.equals(codiceDominio, ruolo.codiceDominio) &&
        Objects.equals(codiceRuolo, ruolo.codiceRuolo) &&
        Objects.equals(mnemonico, ruolo.mnemonico) &&
        Objects.equals(descrizione, ruolo.descrizione) &&
        Objects.equals(entiAssociati, ruolo.entiAssociati);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceDominio, codiceRuolo, mnemonico, descrizione, entiAssociati);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ruolo {\n");
    
    sb.append("    codiceDominio: ").append(toIndentedString(codiceDominio)).append("\n");
    sb.append("    codiceRuolo: ").append(toIndentedString(codiceRuolo)).append("\n");
    sb.append("    mnemonico: ").append(toIndentedString(mnemonico)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    entiAssociati: ").append(toIndentedString(entiAssociati)).append("\n");
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
