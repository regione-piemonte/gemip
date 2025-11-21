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
import it.csi.gemip.mipbackofficebff.bff.dto.Operatore;
import it.csi.gemip.mipbackofficebff.bff.dto.SoggettoAttuatore;
import java.util.Date;
import jakarta.validation.constraints.*;

public class StoricoCalendario   {
  private Long numeroEventi = null;
  private SoggettoAttuatore soggettoAttuatore = null;
  private Operatore operatoreInserimento = null;
  private Long idDocumentoIcs = null;
  private Boolean flgAbilitato = null;
  private String nomeFile = null;
  private String descrizioneFile = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;

  /**
   **/
  
  @JsonProperty("numeroEventi")
  public Long getNumeroEventi() {
    return numeroEventi;
  }
  public void setNumeroEventi(Long numeroEventi) {
    this.numeroEventi = numeroEventi;
  }

  /**
   **/
  
  @JsonProperty("soggettoAttuatore")
  public SoggettoAttuatore getSoggettoAttuatore() {
    return soggettoAttuatore;
  }
  public void setSoggettoAttuatore(SoggettoAttuatore soggettoAttuatore) {
    this.soggettoAttuatore = soggettoAttuatore;
  }

  /**
   **/
  
  @JsonProperty("operatoreInserimento")
  public Operatore getOperatoreInserimento() {
    return operatoreInserimento;
  }
  public void setOperatoreInserimento(Operatore operatoreInserimento) {
    this.operatoreInserimento = operatoreInserimento;
  }

  /**
   **/
  
  @JsonProperty("idDocumentoIcs")
  public Long getIdDocumentoIcs() {
    return idDocumentoIcs;
  }
  public void setIdDocumentoIcs(Long idDocumentoIcs) {
    this.idDocumentoIcs = idDocumentoIcs;
  }

  /**
   **/
  
  @JsonProperty("flgAbilitato")
  public Boolean isFlgAbilitato() {
    return flgAbilitato;
  }
  public void setFlgAbilitato(Boolean flgAbilitato) {
    this.flgAbilitato = flgAbilitato;
  }

  /**
   **/
  
  @JsonProperty("nomeFile")
  public String getNomeFile() {
    return nomeFile;
  }
  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  /**
   **/
  
  @JsonProperty("descrizioneFile")
  public String getDescrizioneFile() {
    return descrizioneFile;
  }
  public void setDescrizioneFile(String descrizioneFile) {
    this.descrizioneFile = descrizioneFile;
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
    StoricoCalendario storicoCalendario = (StoricoCalendario) o;
    return Objects.equals(numeroEventi, storicoCalendario.numeroEventi) &&
        Objects.equals(soggettoAttuatore, storicoCalendario.soggettoAttuatore) &&
        Objects.equals(operatoreInserimento, storicoCalendario.operatoreInserimento) &&
        Objects.equals(idDocumentoIcs, storicoCalendario.idDocumentoIcs) &&
        Objects.equals(flgAbilitato, storicoCalendario.flgAbilitato) &&
        Objects.equals(nomeFile, storicoCalendario.nomeFile) &&
        Objects.equals(descrizioneFile, storicoCalendario.descrizioneFile) &&
        Objects.equals(codUserInserim, storicoCalendario.codUserInserim) &&
        Objects.equals(dataInserim, storicoCalendario.dataInserim) &&
        Objects.equals(codUserAggiorn, storicoCalendario.codUserAggiorn) &&
        Objects.equals(dataAggiorn, storicoCalendario.dataAggiorn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numeroEventi, soggettoAttuatore, operatoreInserimento, idDocumentoIcs, flgAbilitato, nomeFile, descrizioneFile, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StoricoCalendario {\n");
    
    sb.append("    numeroEventi: ").append(toIndentedString(numeroEventi)).append("\n");
    sb.append("    soggettoAttuatore: ").append(toIndentedString(soggettoAttuatore)).append("\n");
    sb.append("    operatoreInserimento: ").append(toIndentedString(operatoreInserimento)).append("\n");
    sb.append("    idDocumentoIcs: ").append(toIndentedString(idDocumentoIcs)).append("\n");
    sb.append("    flgAbilitato: ").append(toIndentedString(flgAbilitato)).append("\n");
    sb.append("    nomeFile: ").append(toIndentedString(nomeFile)).append("\n");
    sb.append("    descrizioneFile: ").append(toIndentedString(descrizioneFile)).append("\n");
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
