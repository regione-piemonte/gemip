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

public class UserInfo   {
  private String nome = null;
  private String cognome = null;
  private String codFisc = null;
  private String ente = null;
  private String ruolo = null;
  private String codRuolo = null;
  private Long idOperatore = null;

  /**
   * nome dell&#x27;utente
   **/
  
  @JsonProperty("nome")
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * cognome dell&#x27;utente
   **/
  
  @JsonProperty("cognome")
  public String getCognome() {
    return cognome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   * codice fiscale dell&#x27;utente
   **/
  
  @JsonProperty("codFisc")
  public String getCodFisc() {
    return codFisc;
  }
  public void setCodFisc(String codFisc) {
    this.codFisc = codFisc;
  }

  /**
   * nome dell&#x27;ente
   **/
  
  @JsonProperty("ente")
  public String getEnte() {
    return ente;
  }
  public void setEnte(String ente) {
    this.ente = ente;
  }

  /**
   * nome del ruolo dell&#x27;utente
   **/
  
  @JsonProperty("ruolo")
  public String getRuolo() {
    return ruolo;
  }
  public void setRuolo(String ruolo) {
    this.ruolo = ruolo;
  }

  /**
   * codice del ruolo dell&#x27;utente
   **/
  
  @JsonProperty("cod_ruolo")
  public String getCodRuolo() {
    return codRuolo;
  }
  public void setCodRuolo(String codRuolo) {
    this.codRuolo = codRuolo;
  }

  /**
   * livello di autenticazione utilizzato per accedere
   **/
  
  @JsonProperty("idOperatore")
  public Long getIdOperatore() {
    return idOperatore;
  }
  public void setIdOperatore(Long idOperatore) {
    this.idOperatore = idOperatore;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfo userInfo = (UserInfo) o;
    return Objects.equals(nome, userInfo.nome) &&
        Objects.equals(cognome, userInfo.cognome) &&
        Objects.equals(codFisc, userInfo.codFisc) &&
        Objects.equals(ente, userInfo.ente) &&
        Objects.equals(ruolo, userInfo.ruolo) &&
        Objects.equals(codRuolo, userInfo.codRuolo) &&
        Objects.equals(idOperatore, userInfo.idOperatore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, cognome, codFisc, ente, ruolo, codRuolo, idOperatore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfo {\n");
    
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    codFisc: ").append(toIndentedString(codFisc)).append("\n");
    sb.append("    ente: ").append(toIndentedString(ente)).append("\n");
    sb.append("    ruolo: ").append(toIndentedString(ruolo)).append("\n");
    sb.append("    codRuolo: ").append(toIndentedString(codRuolo)).append("\n");
    sb.append("    idOperatore: ").append(toIndentedString(idOperatore)).append("\n");
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
