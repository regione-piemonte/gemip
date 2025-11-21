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
import java.io.File;
import java.util.Date;
import jakarta.validation.constraints.*;

public class Documento   {
  private Long idDocumento = null;
  private String nomeDocumento = null;
  private String descrizioneDocumento = null;
  private String codeTipoDocumento = null;
  private File documento = null;
  private Long idOperatoreInserimento = null;
  private Long idIdeaDiImpresa = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;

  /**
   **/
  
  @JsonProperty("idDocumento")
  public Long getIdDocumento() {
    return idDocumento;
  }
  public void setIdDocumento(Long idDocumento) {
    this.idDocumento = idDocumento;
  }

  /**
   **/
  
  @JsonProperty("nomeDocumento")
  public String getNomeDocumento() {
    return nomeDocumento;
  }
  public void setNomeDocumento(String nomeDocumento) {
    this.nomeDocumento = nomeDocumento;
  }

  /**
   **/
  
  @JsonProperty("descrizioneDocumento")
  public String getDescrizioneDocumento() {
    return descrizioneDocumento;
  }
  public void setDescrizioneDocumento(String descrizioneDocumento) {
    this.descrizioneDocumento = descrizioneDocumento;
  }

  /**
   **/
  
  @JsonProperty("codeTipoDocumento")
  public String getCodeTipoDocumento() {
    return codeTipoDocumento;
  }
  public void setCodeTipoDocumento(String codeTipoDocumento) {
    this.codeTipoDocumento = codeTipoDocumento;
  }

  /**
   **/
  
  @JsonProperty("documento")
  public File getDocumento() {
    return documento;
  }
  public void setDocumento(File documento) {
    this.documento = documento;
  }

  /**
   **/
  
  @JsonProperty("idOperatoreInserimento")
  public Long getIdOperatoreInserimento() {
    return idOperatoreInserimento;
  }
  public void setIdOperatoreInserimento(Long idOperatoreInserimento) {
    this.idOperatoreInserimento = idOperatoreInserimento;
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
    Documento documento = (Documento) o;
    return Objects.equals(idDocumento, documento.idDocumento) &&
        Objects.equals(nomeDocumento, documento.nomeDocumento) &&
        Objects.equals(descrizioneDocumento, documento.descrizioneDocumento) &&
        Objects.equals(codeTipoDocumento, documento.codeTipoDocumento) &&
        Objects.equals(documento, documento.documento) &&
        Objects.equals(idOperatoreInserimento, documento.idOperatoreInserimento) &&
        Objects.equals(idIdeaDiImpresa, documento.idIdeaDiImpresa) &&
        Objects.equals(codUserInserim, documento.codUserInserim) &&
        Objects.equals(dataInserim, documento.dataInserim) &&
        Objects.equals(codUserAggiorn, documento.codUserAggiorn) &&
        Objects.equals(dataAggiorn, documento.dataAggiorn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idDocumento, nomeDocumento, descrizioneDocumento, codeTipoDocumento, documento, idOperatoreInserimento, idIdeaDiImpresa, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Documento {\n");
    
    sb.append("    idDocumento: ").append(toIndentedString(idDocumento)).append("\n");
    sb.append("    nomeDocumento: ").append(toIndentedString(nomeDocumento)).append("\n");
    sb.append("    descrizioneDocumento: ").append(toIndentedString(descrizioneDocumento)).append("\n");
    sb.append("    codeTipoDocumento: ").append(toIndentedString(codeTipoDocumento)).append("\n");
    sb.append("    documento: ").append(toIndentedString(documento)).append("\n");
    sb.append("    idOperatoreInserimento: ").append(toIndentedString(idOperatoreInserimento)).append("\n");
    sb.append("    idIdeaDiImpresa: ").append(toIndentedString(idIdeaDiImpresa)).append("\n");
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
