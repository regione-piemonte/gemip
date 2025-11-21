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
import java.math.BigDecimal;
import jakarta.validation.constraints.*;


public class Cpi   {
  private Long id = null;
  private String codiceCpi = null;
  private String cpi = null;
  private String gruppoOperatore = null;
  private BigDecimal codOperatore = null;
  private String sede = null;
  private String indirizzo = null;
  private String cap = null;
  private String codiceComune = null;
  private String codiceProvincia = null;
  private String telefono = null;
  private String telefono2 = null;
  private String telefono3 = null;
  private String numeroVerde = null;
  private String email = null;
  private String indirizoWeb = null;

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
  
  @JsonProperty("codiceCpi")
  @jakarta.validation.Valid
  public String getCodiceCpi() {
    return codiceCpi;
  }
  public void setCodiceCpi(String codiceCpi) {
    this.codiceCpi = codiceCpi;
  }

  /**
   **/
  
  @JsonProperty("cpi")
  @jakarta.validation.Valid
  public String getCpi() {
    return cpi;
  }
  public void setCpi(String cpi) {
    this.cpi = cpi;
  }

  /**
   **/
  
  @JsonProperty("gruppoOperatore")
  @jakarta.validation.Valid
  public String getGruppoOperatore() {
    return gruppoOperatore;
  }
  public void setGruppoOperatore(String gruppoOperatore) {
    this.gruppoOperatore = gruppoOperatore;
  }

  /**
   **/
  
  @JsonProperty("codOperatore")
  @jakarta.validation.Valid
  public BigDecimal getCodOperatore() {
    return codOperatore;
  }
  public void setCodOperatore(BigDecimal codOperatore) {
    this.codOperatore = codOperatore;
  }

  /**
   **/
  
  @JsonProperty("sede")
  @jakarta.validation.Valid
  public String getSede() {
    return sede;
  }
  public void setSede(String sede) {
    this.sede = sede;
  }

  /**
   **/
  
  @JsonProperty("indirizzo")
  @jakarta.validation.Valid
  public String getIndirizzo() {
    return indirizzo;
  }
  public void setIndirizzo(String indirizzo) {
    this.indirizzo = indirizzo;
  }

  /**
   **/
  
  @JsonProperty("cap")
  @jakarta.validation.Valid
  public String getCap() {
    return cap;
  }
  public void setCap(String cap) {
    this.cap = cap;
  }

  /**
   **/
  
  @JsonProperty("codiceComune")
  @jakarta.validation.Valid
  public String getCodiceComune() {
    return codiceComune;
  }
  public void setCodiceComune(String codiceComune) {
    this.codiceComune = codiceComune;
  }

  /**
   **/
  
  @JsonProperty("codiceProvincia")
  @jakarta.validation.Valid
  public String getCodiceProvincia() {
    return codiceProvincia;
  }
  public void setCodiceProvincia(String codiceProvincia) {
    this.codiceProvincia = codiceProvincia;
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
  
  @JsonProperty("telefono2")
  @jakarta.validation.Valid
  public String getTelefono2() {
    return telefono2;
  }
  public void setTelefono2(String telefono2) {
    this.telefono2 = telefono2;
  }

  /**
   **/
  
  @JsonProperty("telefono3")
  @jakarta.validation.Valid
  public String getTelefono3() {
    return telefono3;
  }
  public void setTelefono3(String telefono3) {
    this.telefono3 = telefono3;
  }

  /**
   **/
  
  @JsonProperty("numeroVerde")
  @jakarta.validation.Valid
  public String getNumeroVerde() {
    return numeroVerde;
  }
  public void setNumeroVerde(String numeroVerde) {
    this.numeroVerde = numeroVerde;
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
  
  @JsonProperty("indirizoWeb")
  @jakarta.validation.Valid
  public String getIndirizoWeb() {
    return indirizoWeb;
  }
  public void setIndirizoWeb(String indirizoWeb) {
    this.indirizoWeb = indirizoWeb;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cpi cpi = (Cpi) o;
    return Objects.equals(id, cpi.id) &&
        Objects.equals(codiceCpi, cpi.codiceCpi) &&
        Objects.equals(cpi, cpi.cpi) &&
        Objects.equals(gruppoOperatore, cpi.gruppoOperatore) &&
        Objects.equals(codOperatore, cpi.codOperatore) &&
        Objects.equals(sede, cpi.sede) &&
        Objects.equals(indirizzo, cpi.indirizzo) &&
        Objects.equals(cap, cpi.cap) &&
        Objects.equals(codiceComune, cpi.codiceComune) &&
        Objects.equals(codiceProvincia, cpi.codiceProvincia) &&
        Objects.equals(telefono, cpi.telefono) &&
        Objects.equals(telefono2, cpi.telefono2) &&
        Objects.equals(telefono3, cpi.telefono3) &&
        Objects.equals(numeroVerde, cpi.numeroVerde) &&
        Objects.equals(email, cpi.email) &&
        Objects.equals(indirizoWeb, cpi.indirizoWeb);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, codiceCpi, cpi, gruppoOperatore, codOperatore, sede, indirizzo, cap, codiceComune, codiceProvincia, telefono, telefono2, telefono3, numeroVerde, email, indirizoWeb);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Cpi {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    codiceCpi: ").append(toIndentedString(codiceCpi)).append("\n");
    sb.append("    cpi: ").append(toIndentedString(cpi)).append("\n");
    sb.append("    gruppoOperatore: ").append(toIndentedString(gruppoOperatore)).append("\n");
    sb.append("    codOperatore: ").append(toIndentedString(codOperatore)).append("\n");
    sb.append("    sede: ").append(toIndentedString(sede)).append("\n");
    sb.append("    indirizzo: ").append(toIndentedString(indirizzo)).append("\n");
    sb.append("    cap: ").append(toIndentedString(cap)).append("\n");
    sb.append("    codiceComune: ").append(toIndentedString(codiceComune)).append("\n");
    sb.append("    codiceProvincia: ").append(toIndentedString(codiceProvincia)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
    sb.append("    telefono2: ").append(toIndentedString(telefono2)).append("\n");
    sb.append("    telefono3: ").append(toIndentedString(telefono3)).append("\n");
    sb.append("    numeroVerde: ").append(toIndentedString(numeroVerde)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    indirizoWeb: ").append(toIndentedString(indirizoWeb)).append("\n");
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
