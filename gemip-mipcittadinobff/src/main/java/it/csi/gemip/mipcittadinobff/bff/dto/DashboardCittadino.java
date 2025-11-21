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
import it.csi.gemip.mipcittadinobff.bff.dto.Cittadino;
import jakarta.validation.constraints.*;

public class DashboardCittadino   {
  private Cittadino cittadino = null;
  private Long idCittadinoIncontroPreacc = null;
  private Long idAnagraficaCittadino = null;
  private Long idIdeaDiImpresa = null;
  private Long idTuttor = null;
  private Long idQuestionari = null;
  private Boolean partecipzioneIncontro = null;
  private Boolean assenzaIncontro = null;
  private Boolean registrazioneIncontro = null;
  private Boolean disdicimentoIncontro = null;

  /**
   **/
  
  @JsonProperty("cittadino")
  public Cittadino getCittadino() {
    return cittadino;
  }
  public void setCittadino(Cittadino cittadino) {
    this.cittadino = cittadino;
  }

  /**
   **/
  
  @JsonProperty("idCittadinoIncontroPreacc")
  public Long getIdCittadinoIncontroPreacc() {
    return idCittadinoIncontroPreacc;
  }
  public void setIdCittadinoIncontroPreacc(Long idCittadinoIncontroPreacc) {
    this.idCittadinoIncontroPreacc = idCittadinoIncontroPreacc;
  }

  /**
   **/
  
  @JsonProperty("idAnagraficaCittadino")
  public Long getIdAnagraficaCittadino() {
    return idAnagraficaCittadino;
  }
  public void setIdAnagraficaCittadino(Long idAnagraficaCittadino) {
    this.idAnagraficaCittadino = idAnagraficaCittadino;
  }

  /**
   **/
  
  @JsonProperty("idIdeaDiImpresa")
  public Long getIdIdeaDiImpresa() {
    return idIdeaDiImpresa;
  }
  public void setIdIdeaDiImpresa(Long idIdeaDiImpresa) {
    this.idIdeaDiImpresa = idIdeaDiImpresa;
  }

  /**
   **/
  
  @JsonProperty("idTuttor")
  public Long getIdTuttor() {
    return idTuttor;
  }
  public void setIdTuttor(Long idTuttor) {
    this.idTuttor = idTuttor;
  }

  /**
   **/
  
  @JsonProperty("idQuestionari")
  public Long getIdQuestionari() {
    return idQuestionari;
  }
  public void setIdQuestionari(Long idQuestionari) {
    this.idQuestionari = idQuestionari;
  }

  /**
   **/
  
  @JsonProperty("partecipzioneIncontro")
  public Boolean isPartecipzioneIncontro() {
    return partecipzioneIncontro;
  }
  public void setPartecipzioneIncontro(Boolean partecipzioneIncontro) {
    this.partecipzioneIncontro = partecipzioneIncontro;
  }

  /**
   **/
  
  @JsonProperty("assenzaIncontro")
  public Boolean isAssenzaIncontro() {
    return assenzaIncontro;
  }
  public void setAssenzaIncontro(Boolean assenzaIncontro) {
    this.assenzaIncontro = assenzaIncontro;
  }

  /**
   **/
  
  @JsonProperty("registrazioneIncontro")
  public Boolean isRegistrazioneIncontro() {
    return registrazioneIncontro;
  }
  public void setRegistrazioneIncontro(Boolean registrazioneIncontro) {
    this.registrazioneIncontro = registrazioneIncontro;
  }

  /**
   **/
  
  @JsonProperty("disdicimentoIncontro")
  public Boolean isDisdicimentoIncontro() {
    return disdicimentoIncontro;
  }
  public void setDisdicimentoIncontro(Boolean disdicimentoIncontro) {
    this.disdicimentoIncontro = disdicimentoIncontro;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DashboardCittadino dashboardCittadino = (DashboardCittadino) o;
    return Objects.equals(cittadino, dashboardCittadino.cittadino) &&
        Objects.equals(idCittadinoIncontroPreacc, dashboardCittadino.idCittadinoIncontroPreacc) &&
        Objects.equals(idAnagraficaCittadino, dashboardCittadino.idAnagraficaCittadino) &&
        Objects.equals(idIdeaDiImpresa, dashboardCittadino.idIdeaDiImpresa) &&
        Objects.equals(idTuttor, dashboardCittadino.idTuttor) &&
        Objects.equals(idQuestionari, dashboardCittadino.idQuestionari) &&
        Objects.equals(partecipzioneIncontro, dashboardCittadino.partecipzioneIncontro) &&
        Objects.equals(assenzaIncontro, dashboardCittadino.assenzaIncontro) &&
        Objects.equals(registrazioneIncontro, dashboardCittadino.registrazioneIncontro) &&
        Objects.equals(disdicimentoIncontro, dashboardCittadino.disdicimentoIncontro);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cittadino, idCittadinoIncontroPreacc, idAnagraficaCittadino, idIdeaDiImpresa, idTuttor, idQuestionari, partecipzioneIncontro, assenzaIncontro, registrazioneIncontro, disdicimentoIncontro);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DashboardCittadino {\n");
    
    sb.append("    cittadino: ").append(toIndentedString(cittadino)).append("\n");
    sb.append("    idCittadinoIncontroPreacc: ").append(toIndentedString(idCittadinoIncontroPreacc)).append("\n");
    sb.append("    idAnagraficaCittadino: ").append(toIndentedString(idAnagraficaCittadino)).append("\n");
    sb.append("    idIdeaDiImpresa: ").append(toIndentedString(idIdeaDiImpresa)).append("\n");
    sb.append("    idTuttor: ").append(toIndentedString(idTuttor)).append("\n");
    sb.append("    idQuestionari: ").append(toIndentedString(idQuestionari)).append("\n");
    sb.append("    partecipzioneIncontro: ").append(toIndentedString(partecipzioneIncontro)).append("\n");
    sb.append("    assenzaIncontro: ").append(toIndentedString(assenzaIncontro)).append("\n");
    sb.append("    registrazioneIncontro: ").append(toIndentedString(registrazioneIncontro)).append("\n");
    sb.append("    disdicimentoIncontro: ").append(toIndentedString(disdicimentoIncontro)).append("\n");
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
