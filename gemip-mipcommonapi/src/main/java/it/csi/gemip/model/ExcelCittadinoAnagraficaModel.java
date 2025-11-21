package it.csi.gemip.model;

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


import java.util.Date;

public class ExcelCittadinoAnagraficaModel {
    private String cognome;
    private String nome;
    private Date dataDiNascita;
    private String luogoDiNascita;
    private String provinciaDiNascita;
    private String nazioneDiNascita;
    private String codiceFiscale;
    private String recapitoTelefono;
    private String recapitoEmail;
    private String cittadinanza;
    private String numPermessoDiSoggiorno;
    private String indirizzoResidenza;
    private String cittaResidenza;
    private String provinciaDiResidenza;
    private String nazioneDiResidenza;
    private String capResidenza;
    private String indirizzoDomicilio;
    private String cittaDomicilio;
    private String provinciaDiDomicilio;
    private String nazioneDiDomicilio;
    private String capDomicilio;
    private String titoloDiStudio;
    private String svantaggioAbitativo;
    private String condizioneOccupazionale;
    private String sesso;
    private String cellulare;
    private String emailAlternativa;
    private String citadinanzaAltroSpecificare;
    private String condizioneFamiliare;
    private String situazioneLavorativaAltroSpecificare;
    private String documentoIdentitaNumero;
    private String documentoIdentitaRilasciatoDa;
    private String documentoIdentitaScadenza;
    private String tipoPermessoDiSoggiorno;
    private String permessoDiSoggiornoRilasciatoDa;
    private String scadenzaTitoloDiSoggiorno;
    private String titoloDiStudioAltroSpecificare;

    @Override
    public String toString() {
        return "ExcelCittadinoAnagraficaModel{" +
                "cognome='" + cognome + '\'' +
                ", nome='" + nome + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", luogoDiNascita='" + luogoDiNascita + '\'' +
                ", provinciaDiNascita='" + provinciaDiNascita + '\'' +
                ", nazioneDiNascita='" + nazioneDiNascita + '\'' +
                ", codiceFiscale='" + codiceFiscale + '\'' +
                ", recapitoTelefono='" + recapitoTelefono + '\'' +
                ", recapitoEmail='" + recapitoEmail + '\'' +
                ", cittadinanza='" + cittadinanza + '\'' +
                ", numPermessoDiSoggiorno='" + numPermessoDiSoggiorno + '\'' +
                ", indirizzoResidenza='" + indirizzoResidenza + '\'' +
                ", cittaResidenza='" + cittaResidenza + '\'' +
                ", provinciaDiResidenza='" + provinciaDiResidenza + '\'' +
                ", nazioneDiResidenza='" + nazioneDiResidenza + '\'' +
                ", capResidenza='" + capResidenza + '\'' +
                ", indirizzoDomicilio='" + indirizzoDomicilio + '\'' +
                ", cittaDomicilio='" + cittaDomicilio + '\'' +
                ", provinciaDiDomicilio='" + provinciaDiDomicilio + '\'' +
                ", nazioneDiDomicilio='" + nazioneDiDomicilio + '\'' +
                ", capDomicilio='" + capDomicilio + '\'' +
                ", titoloDiStudio='" + titoloDiStudio + '\'' +
                ", svantaggioAbitativo='" + svantaggioAbitativo + '\'' +
                ", condizioneOccupazionale='" + condizioneOccupazionale + '\'' +
                ", sesso='" + sesso + '\'' +
                ", cellulare='" + cellulare + '\'' +
                ", emailAlternativa='" + emailAlternativa + '\'' +
                ", citadinanzaAltroSpecificare='" + citadinanzaAltroSpecificare + '\'' +
                ", condizioneFamiliare='" + condizioneFamiliare + '\'' +
                ", situazioneLavorativaAltroSpecificare='" + situazioneLavorativaAltroSpecificare + '\'' +
                ", documentoIdentitaNumero='" + documentoIdentitaNumero + '\'' +
                ", documentoIdentitaRilasciatoDa='" + documentoIdentitaRilasciatoDa + '\'' +
                ", documentoIdentitaScadenza='" + documentoIdentitaScadenza + '\'' +
                ", tipoPermessoDiSoggiorno='" + tipoPermessoDiSoggiorno + '\'' +
                ", permessoDiSoggiornoRilasciatoDa='" + permessoDiSoggiornoRilasciatoDa + '\'' +
                ", scadenzaTitoloDiSoggiorno='" + scadenzaTitoloDiSoggiorno + '\'' +
                ", titoloDiStudioAltroSpecificare='" + titoloDiStudioAltroSpecificare + '\'' +
                '}';
    }

    public String getTipoPermessoDiSoggiorno() {
        return tipoPermessoDiSoggiorno;
    }

    public void setTipoPermessoDiSoggiorno(String tipoPermessoDiSoggiorno) {
        this.tipoPermessoDiSoggiorno = tipoPermessoDiSoggiorno;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getLuogoDiNascita() {
        return luogoDiNascita;
    }

    public void setLuogoDiNascita(String luogoDiNascita) {
        this.luogoDiNascita = luogoDiNascita;
    }

    public String getProvinciaDiNascita() {
        return provinciaDiNascita;
    }

    public void setProvinciaDiNascita(String provinciaDiNascita) {
        this.provinciaDiNascita = provinciaDiNascita;
    }

    public String getNazioneDiNascita() {
        return nazioneDiNascita;
    }

    public void setNazioneDiNascita(String nazioneDiNascita) {
        this.nazioneDiNascita = nazioneDiNascita;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getRecapitoTelefono() {
        return recapitoTelefono;
    }

    public void setRecapitoTelefono(String recapitoTelefono) {
        this.recapitoTelefono = recapitoTelefono;
    }

    public String getRecapitoEmail() {
        return recapitoEmail;
    }

    public void setRecapitoEmail(String recapitoEmail) {
        this.recapitoEmail = recapitoEmail;
    }

    public String getCittadinanza() {
        return cittadinanza;
    }

    public void setCittadinanza(String cittadinanza) {
        this.cittadinanza = cittadinanza;
    }

    public String getNumPermessoDiSoggiorno() {
        return numPermessoDiSoggiorno;
    }

    public void setNumPermessoDiSoggiorno(String numPermessoDiSoggiorno) {
        this.numPermessoDiSoggiorno = numPermessoDiSoggiorno;
    }

    public String getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public String getCittaResidenza() {
        return cittaResidenza;
    }

    public void setCittaResidenza(String cittaResidenza) {
        this.cittaResidenza = cittaResidenza;
    }

    public String getProvinciaDiResidenza() {
        return provinciaDiResidenza;
    }

    public void setProvinciaDiResidenza(String provinciaDiResidenza) {
        this.provinciaDiResidenza = provinciaDiResidenza;
    }

    public String getNazioneDiResidenza() {
        return nazioneDiResidenza;
    }

    public void setNazioneDiResidenza(String nazioneDiResidenza) {
        this.nazioneDiResidenza = nazioneDiResidenza;
    }

    public String getCapResidenza() {
        return capResidenza;
    }

    public void setCapResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
    }

    public String getIndirizzoDomicilio() {
        return indirizzoDomicilio;
    }

    public void setIndirizzoDomicilio(String indirizzoDomicilio) {
        this.indirizzoDomicilio = indirizzoDomicilio;
    }

    public String getCittaDomicilio() {
        return cittaDomicilio;
    }

    public void setCittaDomicilio(String cittaDomicilio) {
        this.cittaDomicilio = cittaDomicilio;
    }

    public String getProvinciaDiDomicilio() {
        return provinciaDiDomicilio;
    }

    public void setProvinciaDiDomicilio(String provinciaDiDomicilio) {
        this.provinciaDiDomicilio = provinciaDiDomicilio;
    }

    public String getNazioneDiDomicilio() {
        return nazioneDiDomicilio;
    }

    public void setNazioneDiDomicilio(String nazioneDiDomicilio) {
        this.nazioneDiDomicilio = nazioneDiDomicilio;
    }

    public String getCapDomicilio() {
        return capDomicilio;
    }

    public void setCapDomicilio(String capDomicilio) {
        this.capDomicilio = capDomicilio;
    }

    public String getTitoloDiStudio() {
        return titoloDiStudio;
    }

    public void setTitoloDiStudio(String titoloDiStudio) {
        this.titoloDiStudio = titoloDiStudio;
    }

    public String getSvantaggioAbitativo() {
        return svantaggioAbitativo;
    }

    public void setSvantaggioAbitativo(String svantaggioAbitativo) {
        this.svantaggioAbitativo = svantaggioAbitativo;
    }

    public String getCondizioneOccupazionale() {
        return condizioneOccupazionale;
    }

    public void setCondizioneOccupazionale(String condizioneOccupazionale) {
        this.condizioneOccupazionale = condizioneOccupazionale;
    }

    public String getCellulare() {
        return cellulare;
    }

    public void setCellulare(String cellulare) {
        this.cellulare = cellulare;
    }

    public String getEmailAlternativa() {
        return emailAlternativa;
    }

    public void setEmailAlternativa(String emailAlternativa) {
        this.emailAlternativa = emailAlternativa;
    }

    public String getCitadinanzaAltroSpecificare() {
        return citadinanzaAltroSpecificare;
    }

    public void setCitadinanzaAltroSpecificare(String citadinanzaAltroSpecificare) {
        this.citadinanzaAltroSpecificare = citadinanzaAltroSpecificare;
    }

    public String getCondizioneFamiliare() {
        return condizioneFamiliare;
    }

    public void setCondizioneFamiliare(String condizioneFamiliare) {
        this.condizioneFamiliare = condizioneFamiliare;
    }

    public String getSituazioneLavorativaAltroSpecificare() {
        return situazioneLavorativaAltroSpecificare;
    }

    public void setSituazioneLavorativaAltroSpecificare(String situazioneLavorativaAltroSpecificare) {
        this.situazioneLavorativaAltroSpecificare = situazioneLavorativaAltroSpecificare;
    }

    public String getDocumentoIdentitaNumero() {
        return documentoIdentitaNumero;
    }

    public void setDocumentoIdentitaNumero(String documentoIdentitaNumero) {
        this.documentoIdentitaNumero = documentoIdentitaNumero;
    }

    public String getDocumentoIdentitaRilasciatoDa() {
        return documentoIdentitaRilasciatoDa;
    }

    public void setDocumentoIdentitaRilasciatoDa(String documentoIdentitaRilasciatoDa) {
        this.documentoIdentitaRilasciatoDa = documentoIdentitaRilasciatoDa;
    }

    public String getDocumentoIdentitaScadenza() {
        return documentoIdentitaScadenza;
    }

    public void setDocumentoIdentitaScadenza(String documentoIdentitaScadenza) {
        this.documentoIdentitaScadenza = documentoIdentitaScadenza;
    }

    public String getPermessoDiSoggiornoRilasciatoDa() {
        return permessoDiSoggiornoRilasciatoDa;
    }

    public void setPermessoDiSoggiornoRilasciatoDa(String permessoDiSoggiornoRilasciatoDa) {
        this.permessoDiSoggiornoRilasciatoDa = permessoDiSoggiornoRilasciatoDa;
    }

    public String getScadenzaTitoloDiSoggiorno() {
        return scadenzaTitoloDiSoggiorno;
    }

    public void setScadenzaTitoloDiSoggiorno(String scadenzaTitoloDiSoggiorno) {
        this.scadenzaTitoloDiSoggiorno = scadenzaTitoloDiSoggiorno;
    }

    public String getTitoloDiStudioAltroSpecificare() {
        return titoloDiStudioAltroSpecificare;
    }

    public void setTitoloDiStudioAltroSpecificare(String titoloDiStudioAltroSpecificare) {
        this.titoloDiStudioAltroSpecificare = titoloDiStudioAltroSpecificare;
    }
}
