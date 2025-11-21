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
import it.csi.gemip.mipcommonapi.api.dto.AreaTerritoriale;
import java.util.Date;
import jakarta.validation.constraints.*;


public class SoggettoAttuatore   {
  private Long id = null;
  private String gruppoOperatore = null;
  private Long codOperatore = null;
  private String denominazione = null;
  private String email = null;
  private String telefono = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;
  private Date dataDisabilitazione = null;
  private AreaTerritoriale codiceAreaTerritoriale = null;

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
  public Long getCodOperatore() {
    return codOperatore;
  }
  public void setCodOperatore(Long codOperatore) {
    this.codOperatore = codOperatore;
  }

  /**
   **/
  
  @JsonProperty("denominazione")
  @jakarta.validation.Valid
  public String getDenominazione() {
    return denominazione;
  }
  public void setDenominazione(String denominazione) {
    this.denominazione = denominazione;
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
  
  @JsonProperty("codiceAreaTerritoriale")
  @jakarta.validation.Valid
  public AreaTerritoriale getCodiceAreaTerritoriale() {
    return codiceAreaTerritoriale;
  }
  public void setCodiceAreaTerritoriale(AreaTerritoriale codiceAreaTerritoriale) {
    this.codiceAreaTerritoriale = codiceAreaTerritoriale;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SoggettoAttuatore soggettoAttuatore = (SoggettoAttuatore) o;
    return Objects.equals(id, soggettoAttuatore.id) &&
        Objects.equals(gruppoOperatore, soggettoAttuatore.gruppoOperatore) &&
        Objects.equals(codOperatore, soggettoAttuatore.codOperatore) &&
        Objects.equals(denominazione, soggettoAttuatore.denominazione) &&
        Objects.equals(email, soggettoAttuatore.email) &&
        Objects.equals(telefono, soggettoAttuatore.telefono) &&
        Objects.equals(codUserInserim, soggettoAttuatore.codUserInserim) &&
        Objects.equals(dataInserim, soggettoAttuatore.dataInserim) &&
        Objects.equals(codUserAggiorn, soggettoAttuatore.codUserAggiorn) &&
        Objects.equals(dataAggiorn, soggettoAttuatore.dataAggiorn) &&
        Objects.equals(dataDisabilitazione, soggettoAttuatore.dataDisabilitazione) &&
        Objects.equals(codiceAreaTerritoriale, soggettoAttuatore.codiceAreaTerritoriale);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, gruppoOperatore, codOperatore, denominazione, email, telefono, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn, dataDisabilitazione, codiceAreaTerritoriale);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SoggettoAttuatore {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    gruppoOperatore: ").append(toIndentedString(gruppoOperatore)).append("\n");
    sb.append("    codOperatore: ").append(toIndentedString(codOperatore)).append("\n");
    sb.append("    denominazione: ").append(toIndentedString(denominazione)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    telefono: ").append(toIndentedString(telefono)).append("\n");
    sb.append("    codUserInserim: ").append(toIndentedString(codUserInserim)).append("\n");
    sb.append("    dataInserim: ").append(toIndentedString(dataInserim)).append("\n");
    sb.append("    codUserAggiorn: ").append(toIndentedString(codUserAggiorn)).append("\n");
    sb.append("    dataAggiorn: ").append(toIndentedString(dataAggiorn)).append("\n");
    sb.append("    dataDisabilitazione: ").append(toIndentedString(dataDisabilitazione)).append("\n");
    sb.append("    codiceAreaTerritoriale: ").append(toIndentedString(codiceAreaTerritoriale)).append("\n");
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
