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
import it.csi.gemip.mipcittadinobff.bff.dto.DomandeRisposte;
import java.util.Date;
import java.util.List;
import jakarta.validation.constraints.*;

public class ReportQuestionarioRigheReportistica   {
  private Long id = null;
  private Date dataInserim = null;
  private String areaTerritoriale = null;
  private String soggettoAttuatore = null;
  private List<DomandeRisposte> compilazione = new ArrayList<DomandeRisposte>();

  /**
   **/
  
  @JsonProperty("id")
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
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
  
  @JsonProperty("areaTerritoriale")
  public String getAreaTerritoriale() {
    return areaTerritoriale;
  }
  public void setAreaTerritoriale(String areaTerritoriale) {
    this.areaTerritoriale = areaTerritoriale;
  }

  /**
   **/
  
  @JsonProperty("soggettoAttuatore")
  public String getSoggettoAttuatore() {
    return soggettoAttuatore;
  }
  public void setSoggettoAttuatore(String soggettoAttuatore) {
    this.soggettoAttuatore = soggettoAttuatore;
  }

  /**
   **/
  
  @JsonProperty("compilazione")
  public List<DomandeRisposte> getCompilazione() {
    return compilazione;
  }
  public void setCompilazione(List<DomandeRisposte> compilazione) {
    this.compilazione = compilazione;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReportQuestionarioRigheReportistica reportQuestionarioRigheReportistica = (ReportQuestionarioRigheReportistica) o;
    return Objects.equals(id, reportQuestionarioRigheReportistica.id) &&
        Objects.equals(dataInserim, reportQuestionarioRigheReportistica.dataInserim) &&
        Objects.equals(areaTerritoriale, reportQuestionarioRigheReportistica.areaTerritoriale) &&
        Objects.equals(soggettoAttuatore, reportQuestionarioRigheReportistica.soggettoAttuatore) &&
        Objects.equals(compilazione, reportQuestionarioRigheReportistica.compilazione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, dataInserim, areaTerritoriale, soggettoAttuatore, compilazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportQuestionarioRigheReportistica {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    dataInserim: ").append(toIndentedString(dataInserim)).append("\n");
    sb.append("    areaTerritoriale: ").append(toIndentedString(areaTerritoriale)).append("\n");
    sb.append("    soggettoAttuatore: ").append(toIndentedString(soggettoAttuatore)).append("\n");
    sb.append("    compilazione: ").append(toIndentedString(compilazione)).append("\n");
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
