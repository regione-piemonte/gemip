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
import java.util.Date;
import jakarta.validation.constraints.*;

public class OperatoreIncontroPreaccoglienza   {
  private Long idIncontroPreaccoglienza = null;
  private Long idOperatore = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;

  /**
   **/
  
  @JsonProperty("idIncontroPreaccoglienza")
  public Long getIdIncontroPreaccoglienza() {
    return idIncontroPreaccoglienza;
  }
  public void setIdIncontroPreaccoglienza(Long idIncontroPreaccoglienza) {
    this.idIncontroPreaccoglienza = idIncontroPreaccoglienza;
  }

  /**
   **/
  
  @JsonProperty("idOperatore")
  public Long getIdOperatore() {
    return idOperatore;
  }
  public void setIdOperatore(Long idOperatore) {
    this.idOperatore = idOperatore;
  }

  /**
   **/
  
  @JsonProperty("codUserInserim")
  public String getCodUserInserim() {
    return codUserInserim;
  }
  public void setCodUserInserim(String codUserInserim) {
    this.codUserInserim = codUserInserim;
  }

  /**
   **/
  
  @JsonProperty("dataInserim")
  public Date getDataInserim() {
    return dataInserim;
  }
  public void setDataInserim(Date dataInserim) {
    this.dataInserim = dataInserim;
  }

  /**
   **/
  
  @JsonProperty("codUserAggiorn")
  public String getCodUserAggiorn() {
    return codUserAggiorn;
  }
  public void setCodUserAggiorn(String codUserAggiorn) {
    this.codUserAggiorn = codUserAggiorn;
  }

  /**
   **/
  
  @JsonProperty("dataAggiorn")
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
    OperatoreIncontroPreaccoglienza operatoreIncontroPreaccoglienza = (OperatoreIncontroPreaccoglienza) o;
    return Objects.equals(idIncontroPreaccoglienza, operatoreIncontroPreaccoglienza.idIncontroPreaccoglienza) &&
        Objects.equals(idOperatore, operatoreIncontroPreaccoglienza.idOperatore) &&
        Objects.equals(codUserInserim, operatoreIncontroPreaccoglienza.codUserInserim) &&
        Objects.equals(dataInserim, operatoreIncontroPreaccoglienza.dataInserim) &&
        Objects.equals(codUserAggiorn, operatoreIncontroPreaccoglienza.codUserAggiorn) &&
        Objects.equals(dataAggiorn, operatoreIncontroPreaccoglienza.dataAggiorn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idIncontroPreaccoglienza, idOperatore, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OperatoreIncontroPreaccoglienza {\n");
    
    sb.append("    idIncontroPreaccoglienza: ").append(toIndentedString(idIncontroPreaccoglienza)).append("\n");
    sb.append("    idOperatore: ").append(toIndentedString(idOperatore)).append("\n");
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
