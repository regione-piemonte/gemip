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

public class TipoDocumento   {
  private String codiceTipoDocumento = null;
  private String descrizioneTipoDocumento = null;
  private Date dataInizio = null;
  private Date dataFine = null;

  /**
   **/
  
  @JsonProperty("codiceTipoDocumento")
  public String getCodiceTipoDocumento() {
    return codiceTipoDocumento;
  }
  public void setCodiceTipoDocumento(String codiceTipoDocumento) {
    this.codiceTipoDocumento = codiceTipoDocumento;
  }

  /**
   **/
  
  @JsonProperty("descrizioneTipoDocumento")
  public String getDescrizioneTipoDocumento() {
    return descrizioneTipoDocumento;
  }
  public void setDescrizioneTipoDocumento(String descrizioneTipoDocumento) {
    this.descrizioneTipoDocumento = descrizioneTipoDocumento;
  }

  /**
   **/
  
  @JsonProperty("dataInizio")
  public Date getDataInizio() {
    return dataInizio;
  }
  public void setDataInizio(Date dataInizio) {
    this.dataInizio = dataInizio;
  }

  /**
   **/
  
  @JsonProperty("dataFine")
  public Date getDataFine() {
    return dataFine;
  }
  public void setDataFine(Date dataFine) {
    this.dataFine = dataFine;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TipoDocumento tipoDocumento = (TipoDocumento) o;
    return Objects.equals(codiceTipoDocumento, tipoDocumento.codiceTipoDocumento) &&
        Objects.equals(descrizioneTipoDocumento, tipoDocumento.descrizioneTipoDocumento) &&
        Objects.equals(dataInizio, tipoDocumento.dataInizio) &&
        Objects.equals(dataFine, tipoDocumento.dataFine);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codiceTipoDocumento, descrizioneTipoDocumento, dataInizio, dataFine);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TipoDocumento {\n");
    
    sb.append("    codiceTipoDocumento: ").append(toIndentedString(codiceTipoDocumento)).append("\n");
    sb.append("    descrizioneTipoDocumento: ").append(toIndentedString(descrizioneTipoDocumento)).append("\n");
    sb.append("    dataInizio: ").append(toIndentedString(dataInizio)).append("\n");
    sb.append("    dataFine: ").append(toIndentedString(dataFine)).append("\n");
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
