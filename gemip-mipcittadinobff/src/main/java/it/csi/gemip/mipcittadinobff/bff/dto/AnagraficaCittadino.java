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
import it.csi.gemip.mipcittadinobff.bff.dto.Cittadinanza;
import it.csi.gemip.mipcittadinobff.bff.dto.Cittadino;
import it.csi.gemip.mipcittadinobff.bff.dto.Comune;
import it.csi.gemip.mipcittadinobff.bff.dto.CondizioneFamiliare;
import it.csi.gemip.mipcittadinobff.bff.dto.CondizioneOccupazionale;
import it.csi.gemip.mipcittadinobff.bff.dto.StatoEstero;
import it.csi.gemip.mipcittadinobff.bff.dto.SvantaggioAbitativo;
import it.csi.gemip.mipcittadinobff.bff.dto.TitoloDiStudio;
import java.util.Date;
import jakarta.validation.constraints.*;

public class AnagraficaCittadino   {
  private Long id = null;
  private Cittadino cittadino = null;
  private Date dataNascita = null;
  private Comune comuneNascita = null;
  private String numeroDocumentoIdentita = null;
  private String documentoRilasciatoDa = null;
  private Date dataScadenzaDocumento = null;
  private StatoEstero statoEsteroNascita = null;
  private String descrizioneCittaEsteraNascita = null;
  private Cittadinanza codiceCittadinanza = null;
  private String descCittadinanzaAltro = null;
  private Comune comuneResidenza = null;
  private Long codiceStatoEsteroResidenza = null;
  private String descrizioneCittaEsteraResidenza = null;
  private String indirizzoResidenza = null;
  private String capResidenza = null;
  private Comune comuneDomicilio = null;
  private String indirizzoDomicilio = null;
  private String capDomicilio = null;
  private String recapitoEmail = null;
  private String recapitoEmail2 = null;
  private String recapitoTelefono = null;
  private String recapitoTelefono2 = null;
  private String numeroPermessoDiSoggiorno = null;
  private String descrMotivoPermessoDiSoggiorno = null;
  private String permessoDiSoggiornoRilasciatoDa = null;
  private Date dataScadPermessoSoggiorno = null;
  private String tipoPermessoDiSoggiorno = null;
  private String condizioneOccupazionaleAltro = null;
  private TitoloDiStudio codiceTitoloDiStudio = null;
  private String titoloDiStudioAltro = null;
  private CondizioneOccupazionale codiceCondizioneOccupazionale = null;
  private SvantaggioAbitativo svantaggioAbitativo = null;
  private CondizioneFamiliare condizioneFamiliare = null;
  private String sesso = null;
  private Boolean flgIdeaImpresa = null;
  private String codUserInserim = null;
  private Date dataInserim = null;
  private String codUserAggiorn = null;
  private Date dataAggiorn = null;

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
  
  @JsonProperty("cittadino")
  public Cittadino getCittadino() {
    return cittadino;
  }
  public void setCittadino(Cittadino cittadino) {
    this.cittadino = cittadino;
  }

  /**
   **/
  
  @JsonProperty("dataNascita")
  public Date getDataNascita() {
    return dataNascita;
  }
  public void setDataNascita(Date dataNascita) {
    this.dataNascita = dataNascita;
  }

  /**
   **/
  
  @JsonProperty("comuneNascita")
  public Comune getComuneNascita() {
    return comuneNascita;
  }
  public void setComuneNascita(Comune comuneNascita) {
    this.comuneNascita = comuneNascita;
  }

  /**
   **/
  
  @JsonProperty("numeroDocumentoIdentita")
  public String getNumeroDocumentoIdentita() {
    return numeroDocumentoIdentita;
  }
  public void setNumeroDocumentoIdentita(String numeroDocumentoIdentita) {
    this.numeroDocumentoIdentita = numeroDocumentoIdentita;
  }

  /**
   **/
  
  @JsonProperty("documentoRilasciatoDa")
  public String getDocumentoRilasciatoDa() {
    return documentoRilasciatoDa;
  }
  public void setDocumentoRilasciatoDa(String documentoRilasciatoDa) {
    this.documentoRilasciatoDa = documentoRilasciatoDa;
  }

  /**
   **/
  
  @JsonProperty("dataScadenzaDocumento")
  public Date getDataScadenzaDocumento() {
    return dataScadenzaDocumento;
  }
  public void setDataScadenzaDocumento(Date dataScadenzaDocumento) {
    this.dataScadenzaDocumento = dataScadenzaDocumento;
  }

  /**
   **/
  
  @JsonProperty("statoEsteroNascita")
  public StatoEstero getStatoEsteroNascita() {
    return statoEsteroNascita;
  }
  public void setStatoEsteroNascita(StatoEstero statoEsteroNascita) {
    this.statoEsteroNascita = statoEsteroNascita;
  }

  /**
   **/
  
  @JsonProperty("descrizioneCittaEsteraNascita")
  public String getDescrizioneCittaEsteraNascita() {
    return descrizioneCittaEsteraNascita;
  }
  public void setDescrizioneCittaEsteraNascita(String descrizioneCittaEsteraNascita) {
    this.descrizioneCittaEsteraNascita = descrizioneCittaEsteraNascita;
  }

  /**
   **/
  
  @JsonProperty("codiceCittadinanza")
  public Cittadinanza getCodiceCittadinanza() {
    return codiceCittadinanza;
  }
  public void setCodiceCittadinanza(Cittadinanza codiceCittadinanza) {
    this.codiceCittadinanza = codiceCittadinanza;
  }

  /**
   **/
  
  @JsonProperty("descCittadinanzaAltro")
  public String getDescCittadinanzaAltro() {
    return descCittadinanzaAltro;
  }
  public void setDescCittadinanzaAltro(String descCittadinanzaAltro) {
    this.descCittadinanzaAltro = descCittadinanzaAltro;
  }

  /**
   **/
  
  @JsonProperty("comuneResidenza")
  public Comune getComuneResidenza() {
    return comuneResidenza;
  }
  public void setComuneResidenza(Comune comuneResidenza) {
    this.comuneResidenza = comuneResidenza;
  }

  /**
   **/
  
  @JsonProperty("codiceStatoEsteroResidenza")
  public Long getCodiceStatoEsteroResidenza() {
    return codiceStatoEsteroResidenza;
  }
  public void setCodiceStatoEsteroResidenza(Long codiceStatoEsteroResidenza) {
    this.codiceStatoEsteroResidenza = codiceStatoEsteroResidenza;
  }

  /**
   **/
  
  @JsonProperty("descrizioneCittaEsteraResidenza")
  public String getDescrizioneCittaEsteraResidenza() {
    return descrizioneCittaEsteraResidenza;
  }
  public void setDescrizioneCittaEsteraResidenza(String descrizioneCittaEsteraResidenza) {
    this.descrizioneCittaEsteraResidenza = descrizioneCittaEsteraResidenza;
  }

  /**
   **/
  
  @JsonProperty("indirizzoResidenza")
  public String getIndirizzoResidenza() {
    return indirizzoResidenza;
  }
  public void setIndirizzoResidenza(String indirizzoResidenza) {
    this.indirizzoResidenza = indirizzoResidenza;
  }

  /**
   **/
  
  @JsonProperty("capResidenza")
  public String getCapResidenza() {
    return capResidenza;
  }
  public void setCapResidenza(String capResidenza) {
    this.capResidenza = capResidenza;
  }

  /**
   **/
  
  @JsonProperty("comuneDomicilio")
  public Comune getComuneDomicilio() {
    return comuneDomicilio;
  }
  public void setComuneDomicilio(Comune comuneDomicilio) {
    this.comuneDomicilio = comuneDomicilio;
  }

  /**
   **/
  
  @JsonProperty("indirizzoDomicilio")
  public String getIndirizzoDomicilio() {
    return indirizzoDomicilio;
  }
  public void setIndirizzoDomicilio(String indirizzoDomicilio) {
    this.indirizzoDomicilio = indirizzoDomicilio;
  }

  /**
   **/
  
  @JsonProperty("capDomicilio")
  public String getCapDomicilio() {
    return capDomicilio;
  }
  public void setCapDomicilio(String capDomicilio) {
    this.capDomicilio = capDomicilio;
  }

  /**
   **/
  
  @JsonProperty("recapitoEmail")
  public String getRecapitoEmail() {
    return recapitoEmail;
  }
  public void setRecapitoEmail(String recapitoEmail) {
    this.recapitoEmail = recapitoEmail;
  }

  /**
   **/
  
  @JsonProperty("recapitoEmail2")
  public String getRecapitoEmail2() {
    return recapitoEmail2;
  }
  public void setRecapitoEmail2(String recapitoEmail2) {
    this.recapitoEmail2 = recapitoEmail2;
  }

  /**
   **/
  
  @JsonProperty("recapitoTelefono")
  public String getRecapitoTelefono() {
    return recapitoTelefono;
  }
  public void setRecapitoTelefono(String recapitoTelefono) {
    this.recapitoTelefono = recapitoTelefono;
  }

  /**
   **/
  
  @JsonProperty("recapitoTelefono2")
  public String getRecapitoTelefono2() {
    return recapitoTelefono2;
  }
  public void setRecapitoTelefono2(String recapitoTelefono2) {
    this.recapitoTelefono2 = recapitoTelefono2;
  }

  /**
   **/
  
  @JsonProperty("numeroPermessoDiSoggiorno")
  public String getNumeroPermessoDiSoggiorno() {
    return numeroPermessoDiSoggiorno;
  }
  public void setNumeroPermessoDiSoggiorno(String numeroPermessoDiSoggiorno) {
    this.numeroPermessoDiSoggiorno = numeroPermessoDiSoggiorno;
  }

  /**
   **/
  
  @JsonProperty("descrMotivoPermessoDiSoggiorno")
  public String getDescrMotivoPermessoDiSoggiorno() {
    return descrMotivoPermessoDiSoggiorno;
  }
  public void setDescrMotivoPermessoDiSoggiorno(String descrMotivoPermessoDiSoggiorno) {
    this.descrMotivoPermessoDiSoggiorno = descrMotivoPermessoDiSoggiorno;
  }

  /**
   **/
  
  @JsonProperty("permessoDiSoggiornoRilasciatoDa")
  public String getPermessoDiSoggiornoRilasciatoDa() {
    return permessoDiSoggiornoRilasciatoDa;
  }
  public void setPermessoDiSoggiornoRilasciatoDa(String permessoDiSoggiornoRilasciatoDa) {
    this.permessoDiSoggiornoRilasciatoDa = permessoDiSoggiornoRilasciatoDa;
  }

  /**
   **/
  
  @JsonProperty("dataScadPermessoSoggiorno")
  public Date getDataScadPermessoSoggiorno() {
    return dataScadPermessoSoggiorno;
  }
  public void setDataScadPermessoSoggiorno(Date dataScadPermessoSoggiorno) {
    this.dataScadPermessoSoggiorno = dataScadPermessoSoggiorno;
  }

  /**
   **/
  
  @JsonProperty("tipoPermessoDiSoggiorno")
  public String getTipoPermessoDiSoggiorno() {
    return tipoPermessoDiSoggiorno;
  }
  public void setTipoPermessoDiSoggiorno(String tipoPermessoDiSoggiorno) {
    this.tipoPermessoDiSoggiorno = tipoPermessoDiSoggiorno;
  }

  /**
   **/
  
  @JsonProperty("condizioneOccupazionaleAltro")
  public String getCondizioneOccupazionaleAltro() {
    return condizioneOccupazionaleAltro;
  }
  public void setCondizioneOccupazionaleAltro(String condizioneOccupazionaleAltro) {
    this.condizioneOccupazionaleAltro = condizioneOccupazionaleAltro;
  }

  /**
   **/
  
  @JsonProperty("codiceTitoloDiStudio")
  public TitoloDiStudio getCodiceTitoloDiStudio() {
    return codiceTitoloDiStudio;
  }
  public void setCodiceTitoloDiStudio(TitoloDiStudio codiceTitoloDiStudio) {
    this.codiceTitoloDiStudio = codiceTitoloDiStudio;
  }

  /**
   **/
  
  @JsonProperty("titoloDiStudioAltro")
  public String getTitoloDiStudioAltro() {
    return titoloDiStudioAltro;
  }
  public void setTitoloDiStudioAltro(String titoloDiStudioAltro) {
    this.titoloDiStudioAltro = titoloDiStudioAltro;
  }

  /**
   **/
  
  @JsonProperty("codiceCondizioneOccupazionale")
  public CondizioneOccupazionale getCodiceCondizioneOccupazionale() {
    return codiceCondizioneOccupazionale;
  }
  public void setCodiceCondizioneOccupazionale(CondizioneOccupazionale codiceCondizioneOccupazionale) {
    this.codiceCondizioneOccupazionale = codiceCondizioneOccupazionale;
  }

  /**
   **/
  
  @JsonProperty("svantaggioAbitativo")
  public SvantaggioAbitativo getSvantaggioAbitativo() {
    return svantaggioAbitativo;
  }
  public void setSvantaggioAbitativo(SvantaggioAbitativo svantaggioAbitativo) {
    this.svantaggioAbitativo = svantaggioAbitativo;
  }

  /**
   **/
  
  @JsonProperty("condizioneFamiliare")
  public CondizioneFamiliare getCondizioneFamiliare() {
    return condizioneFamiliare;
  }
  public void setCondizioneFamiliare(CondizioneFamiliare condizioneFamiliare) {
    this.condizioneFamiliare = condizioneFamiliare;
  }

  /**
   **/
  
  @JsonProperty("sesso")
  public String getSesso() {
    return sesso;
  }
  public void setSesso(String sesso) {
    this.sesso = sesso;
  }

  /**
   **/
  
  @JsonProperty("flgIdeaImpresa")
  public Boolean isFlgIdeaImpresa() {
    return flgIdeaImpresa;
  }
  public void setFlgIdeaImpresa(Boolean flgIdeaImpresa) {
    this.flgIdeaImpresa = flgIdeaImpresa;
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
    AnagraficaCittadino anagraficaCittadino = (AnagraficaCittadino) o;
    return Objects.equals(id, anagraficaCittadino.id) &&
        Objects.equals(cittadino, anagraficaCittadino.cittadino) &&
        Objects.equals(dataNascita, anagraficaCittadino.dataNascita) &&
        Objects.equals(comuneNascita, anagraficaCittadino.comuneNascita) &&
        Objects.equals(numeroDocumentoIdentita, anagraficaCittadino.numeroDocumentoIdentita) &&
        Objects.equals(documentoRilasciatoDa, anagraficaCittadino.documentoRilasciatoDa) &&
        Objects.equals(dataScadenzaDocumento, anagraficaCittadino.dataScadenzaDocumento) &&
        Objects.equals(statoEsteroNascita, anagraficaCittadino.statoEsteroNascita) &&
        Objects.equals(descrizioneCittaEsteraNascita, anagraficaCittadino.descrizioneCittaEsteraNascita) &&
        Objects.equals(codiceCittadinanza, anagraficaCittadino.codiceCittadinanza) &&
        Objects.equals(descCittadinanzaAltro, anagraficaCittadino.descCittadinanzaAltro) &&
        Objects.equals(comuneResidenza, anagraficaCittadino.comuneResidenza) &&
        Objects.equals(codiceStatoEsteroResidenza, anagraficaCittadino.codiceStatoEsteroResidenza) &&
        Objects.equals(descrizioneCittaEsteraResidenza, anagraficaCittadino.descrizioneCittaEsteraResidenza) &&
        Objects.equals(indirizzoResidenza, anagraficaCittadino.indirizzoResidenza) &&
        Objects.equals(capResidenza, anagraficaCittadino.capResidenza) &&
        Objects.equals(comuneDomicilio, anagraficaCittadino.comuneDomicilio) &&
        Objects.equals(indirizzoDomicilio, anagraficaCittadino.indirizzoDomicilio) &&
        Objects.equals(capDomicilio, anagraficaCittadino.capDomicilio) &&
        Objects.equals(recapitoEmail, anagraficaCittadino.recapitoEmail) &&
        Objects.equals(recapitoEmail2, anagraficaCittadino.recapitoEmail2) &&
        Objects.equals(recapitoTelefono, anagraficaCittadino.recapitoTelefono) &&
        Objects.equals(recapitoTelefono2, anagraficaCittadino.recapitoTelefono2) &&
        Objects.equals(numeroPermessoDiSoggiorno, anagraficaCittadino.numeroPermessoDiSoggiorno) &&
        Objects.equals(descrMotivoPermessoDiSoggiorno, anagraficaCittadino.descrMotivoPermessoDiSoggiorno) &&
        Objects.equals(permessoDiSoggiornoRilasciatoDa, anagraficaCittadino.permessoDiSoggiornoRilasciatoDa) &&
        Objects.equals(dataScadPermessoSoggiorno, anagraficaCittadino.dataScadPermessoSoggiorno) &&
        Objects.equals(tipoPermessoDiSoggiorno, anagraficaCittadino.tipoPermessoDiSoggiorno) &&
        Objects.equals(condizioneOccupazionaleAltro, anagraficaCittadino.condizioneOccupazionaleAltro) &&
        Objects.equals(codiceTitoloDiStudio, anagraficaCittadino.codiceTitoloDiStudio) &&
        Objects.equals(titoloDiStudioAltro, anagraficaCittadino.titoloDiStudioAltro) &&
        Objects.equals(codiceCondizioneOccupazionale, anagraficaCittadino.codiceCondizioneOccupazionale) &&
        Objects.equals(svantaggioAbitativo, anagraficaCittadino.svantaggioAbitativo) &&
        Objects.equals(condizioneFamiliare, anagraficaCittadino.condizioneFamiliare) &&
        Objects.equals(sesso, anagraficaCittadino.sesso) &&
        Objects.equals(flgIdeaImpresa, anagraficaCittadino.flgIdeaImpresa) &&
        Objects.equals(codUserInserim, anagraficaCittadino.codUserInserim) &&
        Objects.equals(dataInserim, anagraficaCittadino.dataInserim) &&
        Objects.equals(codUserAggiorn, anagraficaCittadino.codUserAggiorn) &&
        Objects.equals(dataAggiorn, anagraficaCittadino.dataAggiorn);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cittadino, dataNascita, comuneNascita, numeroDocumentoIdentita, documentoRilasciatoDa, dataScadenzaDocumento, statoEsteroNascita, descrizioneCittaEsteraNascita, codiceCittadinanza, descCittadinanzaAltro, comuneResidenza, codiceStatoEsteroResidenza, descrizioneCittaEsteraResidenza, indirizzoResidenza, capResidenza, comuneDomicilio, indirizzoDomicilio, capDomicilio, recapitoEmail, recapitoEmail2, recapitoTelefono, recapitoTelefono2, numeroPermessoDiSoggiorno, descrMotivoPermessoDiSoggiorno, permessoDiSoggiornoRilasciatoDa, dataScadPermessoSoggiorno, tipoPermessoDiSoggiorno, condizioneOccupazionaleAltro, codiceTitoloDiStudio, titoloDiStudioAltro, codiceCondizioneOccupazionale, svantaggioAbitativo, condizioneFamiliare, sesso, flgIdeaImpresa, codUserInserim, dataInserim, codUserAggiorn, dataAggiorn);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AnagraficaCittadino {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    cittadino: ").append(toIndentedString(cittadino)).append("\n");
    sb.append("    dataNascita: ").append(toIndentedString(dataNascita)).append("\n");
    sb.append("    comuneNascita: ").append(toIndentedString(comuneNascita)).append("\n");
    sb.append("    numeroDocumentoIdentita: ").append(toIndentedString(numeroDocumentoIdentita)).append("\n");
    sb.append("    documentoRilasciatoDa: ").append(toIndentedString(documentoRilasciatoDa)).append("\n");
    sb.append("    dataScadenzaDocumento: ").append(toIndentedString(dataScadenzaDocumento)).append("\n");
    sb.append("    statoEsteroNascita: ").append(toIndentedString(statoEsteroNascita)).append("\n");
    sb.append("    descrizioneCittaEsteraNascita: ").append(toIndentedString(descrizioneCittaEsteraNascita)).append("\n");
    sb.append("    codiceCittadinanza: ").append(toIndentedString(codiceCittadinanza)).append("\n");
    sb.append("    descCittadinanzaAltro: ").append(toIndentedString(descCittadinanzaAltro)).append("\n");
    sb.append("    comuneResidenza: ").append(toIndentedString(comuneResidenza)).append("\n");
    sb.append("    codiceStatoEsteroResidenza: ").append(toIndentedString(codiceStatoEsteroResidenza)).append("\n");
    sb.append("    descrizioneCittaEsteraResidenza: ").append(toIndentedString(descrizioneCittaEsteraResidenza)).append("\n");
    sb.append("    indirizzoResidenza: ").append(toIndentedString(indirizzoResidenza)).append("\n");
    sb.append("    capResidenza: ").append(toIndentedString(capResidenza)).append("\n");
    sb.append("    comuneDomicilio: ").append(toIndentedString(comuneDomicilio)).append("\n");
    sb.append("    indirizzoDomicilio: ").append(toIndentedString(indirizzoDomicilio)).append("\n");
    sb.append("    capDomicilio: ").append(toIndentedString(capDomicilio)).append("\n");
    sb.append("    recapitoEmail: ").append(toIndentedString(recapitoEmail)).append("\n");
    sb.append("    recapitoEmail2: ").append(toIndentedString(recapitoEmail2)).append("\n");
    sb.append("    recapitoTelefono: ").append(toIndentedString(recapitoTelefono)).append("\n");
    sb.append("    recapitoTelefono2: ").append(toIndentedString(recapitoTelefono2)).append("\n");
    sb.append("    numeroPermessoDiSoggiorno: ").append(toIndentedString(numeroPermessoDiSoggiorno)).append("\n");
    sb.append("    descrMotivoPermessoDiSoggiorno: ").append(toIndentedString(descrMotivoPermessoDiSoggiorno)).append("\n");
    sb.append("    permessoDiSoggiornoRilasciatoDa: ").append(toIndentedString(permessoDiSoggiornoRilasciatoDa)).append("\n");
    sb.append("    dataScadPermessoSoggiorno: ").append(toIndentedString(dataScadPermessoSoggiorno)).append("\n");
    sb.append("    tipoPermessoDiSoggiorno: ").append(toIndentedString(tipoPermessoDiSoggiorno)).append("\n");
    sb.append("    condizioneOccupazionaleAltro: ").append(toIndentedString(condizioneOccupazionaleAltro)).append("\n");
    sb.append("    codiceTitoloDiStudio: ").append(toIndentedString(codiceTitoloDiStudio)).append("\n");
    sb.append("    titoloDiStudioAltro: ").append(toIndentedString(titoloDiStudioAltro)).append("\n");
    sb.append("    codiceCondizioneOccupazionale: ").append(toIndentedString(codiceCondizioneOccupazionale)).append("\n");
    sb.append("    svantaggioAbitativo: ").append(toIndentedString(svantaggioAbitativo)).append("\n");
    sb.append("    condizioneFamiliare: ").append(toIndentedString(condizioneFamiliare)).append("\n");
    sb.append("    sesso: ").append(toIndentedString(sesso)).append("\n");
    sb.append("    flgIdeaImpresa: ").append(toIndentedString(flgIdeaImpresa)).append("\n");
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
