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


public class FileIcs   {
  private Long idFileIcs = null;
  private String nomeFile = null;
  private String descrizioneFile = null;
  private byte[] fileIcs = null;
  private Long operatore = null;
  private Boolean flgAbilitato = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;

  /**
   **/
  
  @JsonProperty("idFileIcs")
  @jakarta.validation.Valid
  public Long getIdFileIcs() {
    return idFileIcs;
  }
  public void setIdFileIcs(Long idFileIcs) {
    this.idFileIcs = idFileIcs;
  }

  /**
   **/
  
  @JsonProperty("nomeFile")
  @jakarta.validation.Valid
  public String getNomeFile() {
    return nomeFile;
  }
  public void setNomeFile(String nomeFile) {
    this.nomeFile = nomeFile;
  }

  /**
   **/
  
  @JsonProperty("descrizioneFile")
  @jakarta.validation.Valid
  public String getDescrizioneFile() {
    return descrizioneFile;
  }
  public void setDescrizioneFile(String descrizioneFile) {
    this.descrizioneFile = descrizioneFile;
  }

  /**
   **/
  
  @JsonProperty("fileIcs")
  @jakarta.validation.Valid
  public byte[] getFileIcs() {
    return fileIcs;
  }
  public void setFileIcs(byte[] fileIcs) {
    this.fileIcs = fileIcs;
  }

  /**
   **/
  
  @JsonProperty("operatore")
  @jakarta.validation.Valid
  public Long getOperatore() {
    return operatore;
  }
  public void setOperatore(Long operatore) {
    this.operatore = operatore;
  }

  /**
   **/
  
  @JsonProperty("flgAbilitato")
  @jakarta.validation.Valid
  public Boolean isFlgAbilitato() {
    return flgAbilitato;
  }
  public void setFlgAbilitato(Boolean flgAbilitato) {
    this.flgAbilitato = flgAbilitato;
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
    FileIcs fileIcs = (FileIcs) o;
    return Objects.equals(idFileIcs, fileIcs.idFileIcs) &&
        Objects.equals(nomeFile, fileIcs.nomeFile) &&
        Objects.equals(descrizioneFile, fileIcs.descrizioneFile) &&
        Objects.equals(fileIcs, fileIcs.fileIcs) &&
        Objects.equals(operatore, fileIcs.operatore) &&
        Objects.equals(flgAbilitato, fileIcs.flgAbilitato) &&
        Objects.equals(codUserInserim, fileIcs.codUserInserim) &&
        Objects.equals(dataInserim, fileIcs.dataInserim) &&
        Objects.equals(codUserAggiorn, fileIcs.codUserAggiorn) &&
        Objects.equals(dataAggiorn, fileIcs.dataAggiorn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idFileIcs, nomeFile, descrizioneFile, fileIcs, operatore, flgAbilitato, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileIcs {\n");
    
    sb.append("    idFileIcs: ").append(toIndentedString(idFileIcs)).append("\n");
    sb.append("    nomeFile: ").append(toIndentedString(nomeFile)).append("\n");
    sb.append("    descrizioneFile: ").append(toIndentedString(descrizioneFile)).append("\n");
    sb.append("    fileIcs: ").append(toIndentedString(fileIcs)).append("\n");
    sb.append("    operatore: ").append(toIndentedString(operatore)).append("\n");
    sb.append("    flgAbilitato: ").append(toIndentedString(flgAbilitato)).append("\n");
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
