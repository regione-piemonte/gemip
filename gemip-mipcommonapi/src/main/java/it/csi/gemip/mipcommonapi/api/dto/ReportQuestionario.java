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
import it.csi.gemip.mipcommonapi.api.dto.ReportQuestionarioRigheReportistica;
import java.util.List;
import jakarta.validation.constraints.*;


public class ReportQuestionario   {
  private Integer totaleRisposte = null;
  private List<String> domande = new ArrayList<String>();
  private List<ReportQuestionarioRigheReportistica> righeReportistica = new ArrayList<ReportQuestionarioRigheReportistica>();

  /**
   **/
  
  @JsonProperty("totaleRisposte")
  @jakarta.validation.Valid
  public Integer getTotaleRisposte() {
    return totaleRisposte;
  }
  public void setTotaleRisposte(Integer totaleRisposte) {
    this.totaleRisposte = totaleRisposte;
  }

  /**
   **/
  
  @JsonProperty("domande")
  @jakarta.validation.Valid
  public List<String> getDomande() {
    return domande;
  }
  public void setDomande(List<String> domande) {
    this.domande = domande;
  }

  /**
   **/
  
  @JsonProperty("righeReportistica")
  @jakarta.validation.Valid
  public List<ReportQuestionarioRigheReportistica> getRigheReportistica() {
    return righeReportistica;
  }
  public void setRigheReportistica(List<ReportQuestionarioRigheReportistica> righeReportistica) {
    this.righeReportistica = righeReportistica;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ReportQuestionario reportQuestionario = (ReportQuestionario) o;
    return Objects.equals(totaleRisposte, reportQuestionario.totaleRisposte) &&
        Objects.equals(domande, reportQuestionario.domande) &&
        Objects.equals(righeReportistica, reportQuestionario.righeReportistica);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totaleRisposte, domande, righeReportistica);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReportQuestionario {\n");
    
    sb.append("    totaleRisposte: ").append(toIndentedString(totaleRisposte)).append("\n");
    sb.append("    domande: ").append(toIndentedString(domande)).append("\n");
    sb.append("    righeReportistica: ").append(toIndentedString(righeReportistica)).append("\n");
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
