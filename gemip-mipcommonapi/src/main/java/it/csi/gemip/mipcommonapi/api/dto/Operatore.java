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
import java.util.Date;
import jakarta.validation.constraints.*;


public class Operatore   {
  private Long id = null;
  private String codFiscaleUtente = null;
  private String cognome = null;
  private String nome = null;
  private String telefono = null;
  private String email = null;
  private Date dataRegistrazione = null;
  private Date dataDisabilitazione = null;
  private Long idOperatoreDisabilitazione = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;

  /**
   **/
  
  @JsonProperty("id")
  @jakarta.validation.Valid
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }

  /**
   **/
  
  @JsonProperty("codFiscaleUtente")
  @jakarta.validation.Valid
  public String getCodFiscaleUtente() {
    return codFiscaleUtente;
  }
  public void setCodFiscaleUtente(String codFiscaleUtente) {
    this.codFiscaleUtente = codFiscaleUtente;
  }

  /**
   **/
  
  @JsonProperty("cognome")
  @jakarta.validation.Valid
  public String getCognome() {
    return cognome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   **/
  
  @JsonProperty("nome")
  @jakarta.validation.Valid
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   **/
  
  @JsonProperty("telefono")
  @jakarta.validation.Valid
  public String getTelefono() {
    return telefono;
  }
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  /**
   **/
  
  @JsonProperty("email")
  @jakarta.validation.Valid
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   **/
  
  @JsonProperty("dataRegistrazione")
  @jakarta.validation.Valid
  public Date getDataRegistrazione() {
    return dataRegistrazione;
  }
  public void setDataRegistrazione(Date dataRegistrazione) {
    this.dataRegistrazione = dataRegistrazione;
  }

  /**
   **/
  
  @JsonProperty("dataDisabilitazione")
  @jakarta.validation.Valid
  public Date getDataDisabilitazione() {
    return dataDisabilitazione;
  }
  public void setDataDisabilitazione(Date dataDisabilitazione) {
    this.dataDisabilitazione = dataDisabilitazione;
  }

  /**
   **/
  
  @JsonProperty("idOperatoreDisabilitazione")
  @jakarta.validation.Valid
  public Long getIdOperatoreDisabilitazione() {
    return idOperatoreDisabilitazione;
  }
  public void setIdOperatoreDisabilitazione(Long idOperatoreDisabilitazione) {
    this.idOperatoreDisabilitazione = idOperatoreDisabilitazione;
  }

  /**
   **/
  
  @JsonProperty("codUserInserim")
  @jakarta.validation.Valid
  public String getCodUserInserim() {
    return codUserInserim;
  }
  public void setCodUserInserim(String codUserInserim) {
    this.codUserInserim = codUserInserim;
  }

  /**
   **/
  
  @JsonProperty("dataInserim")
  @jakarta.validation.Valid
  public Date getDataInserim() {
    return dataInserim;
  }
  public void setDataInserim(Date dataInserim) {
    this.dataInserim = dataInserim;
  }

  /**
   **/
  
  @JsonProperty("codUserAggiorn")
  @jakarta.validation.Valid
  public String getCodUserAggiorn() {
    return codUserAggiorn;
  }
  public void setCodUserAggiorn(String codUserAggiorn) {
    this.codUserAggiorn = codUserAggiorn;
  }

  /**
   **/
  
  @JsonProperty("dataAggiorn")
  @jakarta.validation.Valid
  public Date getDataAggiorn() {
    return dataAggiorn;
  }
  public void setDataAggiorn(Date dataAggiorn) {
    this.dataAggiorn = dataAggiorn;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Operatore operatore = (Operatore) o;
    return Objects.equals(id, operatore.id) &&
        Objects.equals(codFiscaleUtente, operatore.codFiscaleUtente) &&
        Objects.equals(cognome, operatore.cognome) &&
        Objects.equals(nome, operatore.nome) &&
        Objects.equals(telefono, operatore.telefono) &&
        Objects.equals(email, operatore.email) &&
        Objects.equals(dataRegistrazione, operatore.dataRegistrazione) &&
        Objects.equals(dataDisabilitazione, operatore.dataDisabilitazione) &&
        Objects.equals(idOperatoreDisabilitazione, operatore.idOperatoreDisabilitazione) &&
        Objects.equals(codUserInserim, operatore.codUserInserim) &&
        Objects.equals(dataInserim, operatore.dataInserim) &&
        Objects.equals(codUserAggiorn, operatore.codUserAggiorn) &&
        Objects.equals(dataAggiorn, operatore.dataAggiorn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, codFiscaleUtente, cognome, nome, telefono, email, dataRegistrazione, dataDisabilitazione, idOperatoreDisabilitazione, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Operatore {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    codFiscaleUtente: ").append(toIndentedString(codFiscaleUtente)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    dataRegistrazione: ").append(toIndentedString(dataRegistrazione)).append("\n");
    sb.append("    dataDisabilitazione: ").append(toIndentedString(dataDisabilitazione)).append("\n");
    sb.append("    idOperatoreDisabilitazione: ").append(toIndentedString(idOperatoreDisabilitazione)).append("\n");
    sb.append("    codUserInserim: ").append(toIndentedString(codUserInserim)).append("\n");
    sb.append("    dataInserim: ").append(toIndentedString(dataInserim)).append("\n");
    sb.append("    codUserAggiorn: ").append(toIndentedString(codUserAggiorn)).append("\n");
    sb.append("    dataAggiorn: ").append(toIndentedString(dataAggiorn)).append("\n");
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
