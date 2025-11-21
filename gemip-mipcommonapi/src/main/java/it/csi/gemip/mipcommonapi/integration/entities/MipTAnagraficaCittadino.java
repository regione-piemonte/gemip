package it.csi.gemip.mipcommonapi.integration.entities;

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

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mip_t_anagrafica_cittadino")
public class MipTAnagraficaCittadino extends PanacheEntityBase {
    @Id
    @Column(name = "id_cittadino", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cittadino", nullable = false)
    private MipTCittadino mipTCittadino;

    @NotNull
    @Column(name = "d_nascita", nullable = false)
    private Date dataNascita;

    @NotNull
    @Column(name = "sesso", nullable = false)
    private String sesso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_istat_comune_nascita")
    private ExtTtComune comuneNascita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_stato_estero_nascita")
    private ExtStatiEsteri statoEsteroNascita;

    @Size(max = 100)
    @Column(name = "descr_citta_estera_nascita", length = 100)
    private String descrizioneCittaEsteraNascita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_istat_cittadinanza")
    private ExtTabCittadinanza codiceCittadinanza;

    @Size(max = 1000)
    @Column(name = "descr_cittadinanza_altro ")
    private String descCittadinanzaAltro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_istat_comune_residenza")
    private ExtTtComune comuneResidenza;

    @Column(name = "cod_stato_estero_residenza", precision = 3)
    private Long codiceStatoEsteroResidenza;

    @Size(max = 100)
    @Column(name = "descr_citta_estera_residenza", length = 100)
    private String descrizioneCittaEsteraResidenza;

    @Size(max = 100)
    @Column(name = "indirizzo_residenza", length = 100)
    private String indirizzoResidenza;

    @Column(name = "cap_residenza", precision = 5)
    private String capResidenza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_istat_comune_domicilio")
    private ExtTtComune comuneDomicilio;

    @Size(max = 100)
    @Column(name = "indirizzo_domicilio", length = 100)
    private String indirizzoDomicilio;

    @Column(name = "cap_domicilio", precision = 5)
    private String capDomicilio;

    @Size(max = 100)
    @Column(name = "recapito_email", length = 100)
    private String recapitoEmail;

    @Size(max = 100)
    @Column(name = "recapito_email_2 ", length = 100)
    private String recapitoEmail2;

    @Size(max = 50)
    @Column(name = "recapito_telefono", length = 50)
    private String recapitoTelefono;

    @Size(max = 50)
    @Column(name = "recapito_telefono_2", length = 50)
    private String recapitoTelefono2;

    @Size(max = 50)
    @Column(name = "num_docum_identita", length = 50)
    private String numDocumIdentita;

    @Size(max = 80)
    @Column(name = "ente_rilascio_docum_identita", length = 80)
    private String enteRilascioDocumIdentita;

    @Column(name = "d_scadenza_docum_identita")
    private Date dataScadDocumIdentita;

    @Size(max = 50)
    @Column(name = "num_permesso_di_soggiorno", length = 50)
    private String numeroPermessoDiSoggiorno;

    @Size(max = 80)
    @Column(name = "ente_rilascio_permesso_di_soggiorno", length = 80)
    private String enteRilascioPermessoDiSoggiorno;

    @Column(name = "d_scadenza_permesso_di_soggiorno")
    private Date dataScadPermessoSoggiorno;

    @Column(name = "tipo_permesso_di_soggiorno")
    private String tipoPermessoDiSoggiorno;

    @Size(max = 1000)
    @Column(name = "descr_motivo_permesso_di_soggiorno", length = 1000)
    private String descrMotivoPermessoDiSoggiorno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_titolo_di_studio")
    private MipDTitoloDiStudio codiceTitoloDiStudio;

    @Size(max = 1000)
    @Column(name = "titolo_di_studio_altro",length = 1000)
    private String titoloDiStudioAltro;

    @Size(max = 16)
    @NotNull
    @Column(name = "cod_user_inserim", nullable = false, length = 16)
    private String codUserInserim;

    @NotNull
    @Column(name = "d_inserim", nullable = false)
    private Date dataInserim;

    @Size(max = 16)
    @NotNull
    @Column(name = "cod_user_aggiorn", nullable = false, length = 16)
    private String codUserAggiorn;

    @NotNull
    @Column(name = "d_aggiorn", nullable = false)
    private Date dataAggiorn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MipTCittadino getMipTCittadino() {
        return mipTCittadino;
    }

    public void setMipTCittadino(MipTCittadino mipTCittadino) {
        this.mipTCittadino = mipTCittadino;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public ExtTtComune getComuneNascita() {
        return comuneNascita;
    }

    public void setComuneNascita(ExtTtComune comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    public ExtStatiEsteri getStatoEsteroNascita() {
        return statoEsteroNascita;
    }

    public void setStatoEsteroNascita(ExtStatiEsteri statoEsteroNascita) {
        this.statoEsteroNascita = statoEsteroNascita;
    }

    public String getDescrizioneCittaEsteraNascita() {
        return descrizioneCittaEsteraNascita;
    }

    public void setDescrizioneCittaEsteraNascita(String descrizioneCittaEsteraNascita) {
        this.descrizioneCittaEsteraNascita = descrizioneCittaEsteraNascita;
    }

    public ExtTabCittadinanza getCodiceCittadinanza() {
        return codiceCittadinanza;
    }

    public void setCodiceCittadinanza(ExtTabCittadinanza codiceCittadinanza) {
        this.codiceCittadinanza = codiceCittadinanza;
    }

    public ExtTtComune getComuneResidenza() {
        return comuneResidenza;
    }

    public void setComuneResidenza(ExtTtComune comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    public Long getCodiceStatoEsteroResidenza() {
        return codiceStatoEsteroResidenza;
    }

    public void setCodiceStatoEsteroResidenza(Long codiceStatoEsteroResidenza) {
        this.codiceStatoEsteroResidenza = codiceStatoEsteroResidenza;
    }

    public String getDescrizioneCittaEsteraResidenza() {
        return descrizioneCittaEsteraResidenza;
    }

    public void setDescrizioneCittaEsteraResidenza(String descrizioneCittaEsteraResidenza) {
        this.descrizioneCittaEsteraResidenza = descrizioneCittaEsteraResidenza;
    }

    public String getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public String getCapResidenza() {
        return capResidenza;
    }

    public void setCapResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
    }

    public ExtTtComune getComuneDomicilio() {
        return comuneDomicilio;
    }

    public void setComuneDomicilio(ExtTtComune comuneDomicilio) {
        this.comuneDomicilio = comuneDomicilio;
    }

    public String getIndirizzoDomicilio() {
        return indirizzoDomicilio;
    }

    public void setIndirizzoDomicilio(String indirizzoDomicilio) {
        this.indirizzoDomicilio = indirizzoDomicilio;
    }

    public String getCapDomicilio() {
        return capDomicilio;
    }

    public void setCapDomicilio(String capDomicilio) {
        this.capDomicilio = capDomicilio;
    }

    public String getRecapitoEmail() {
        return recapitoEmail;
    }

    public void setRecapitoEmail(String recapitoEmail) {
        this.recapitoEmail = recapitoEmail;
    }

    public String getRecapitoTelefono() {
        return recapitoTelefono;
    }

    public void setRecapitoTelefono(String recapitoTelefono) {
        this.recapitoTelefono = recapitoTelefono;
    }

    public String getNumeroPermessoDiSoggiorno() {
        return numeroPermessoDiSoggiorno;
    }

    public void setNumeroPermessoDiSoggiorno(String numeroPermessoDiSoggiorno) {
        this.numeroPermessoDiSoggiorno = numeroPermessoDiSoggiorno;
    }

    public MipDTitoloDiStudio getCodiceTitoloDiStudio() {
        return codiceTitoloDiStudio;
    }

    public void setCodiceTitoloDiStudio(MipDTitoloDiStudio codiceTitoloDiStudio) {
        this.codiceTitoloDiStudio = codiceTitoloDiStudio;
    }

    public String getCodUserInserim() {
        return codUserInserim;
    }

    public void setCodUserInserim(String codUserInserim) {
        this.codUserInserim = codUserInserim;
    }

    public Date getDataInserim() {
        return dataInserim;
    }

    public void setDataInserim(Date dataInserim) {
        this.dataInserim = dataInserim;
    }

    public String getCodUserAggiorn() {
        return codUserAggiorn;
    }

    public void setCodUserAggiorn(String codUserAggiorn) {
        this.codUserAggiorn = codUserAggiorn;
    }

    public Date getDataAggiorn() {
        return dataAggiorn;
    }

    public void setDataAggiorn(Date dataAggiorn) {
        this.dataAggiorn = dataAggiorn;
    }

    public String getDescCittadinanzaAltro() {
        return descCittadinanzaAltro;
    }

    public void setDescCittadinanzaAltro(String descCittadinanzaAltro) {
        this.descCittadinanzaAltro = descCittadinanzaAltro;
    }

    public String getRecapitoEmail2() {
        return recapitoEmail2;
    }

    public void setRecapitoEmail2(String recapitoEmail2) {
        this.recapitoEmail2 = recapitoEmail2;
    }

    public String getRecapitoTelefono2() {
        return recapitoTelefono2;
    }

    public void setRecapitoTelefono2(String recapitoTelefono2) {
        this.recapitoTelefono2 = recapitoTelefono2;
    }

    public String getDescrMotivoPermessoDiSoggiorno() {
        return descrMotivoPermessoDiSoggiorno;
    }

    public void setDescrMotivoPermessoDiSoggiorno(String descrMotivoPermessoDiSoggiorno) {
        this.descrMotivoPermessoDiSoggiorno = descrMotivoPermessoDiSoggiorno;
    }

    public String getTitoloDiStudioAltro() {
        return titoloDiStudioAltro;
    }

    public void setTitoloDiStudioAltro(String titoloDiStudioAltro) {
        this.titoloDiStudioAltro = titoloDiStudioAltro;
    }

    public String getNumDocumIdentita() {
        return numDocumIdentita;
    }

    public void setNumDocumIdentita(String numDocumIdentita) {
        this.numDocumIdentita = numDocumIdentita;
    }

    public String getEnteRilascioDocumIdentita() {
        return enteRilascioDocumIdentita;
    }

    public void setEnteRilascioDocumIdentita(String enteRilascioDocumIdentita) {
        this.enteRilascioDocumIdentita = enteRilascioDocumIdentita;
    }

    public String getTipoPermessoDiSoggiorno() {
        return tipoPermessoDiSoggiorno;
    }

    public void setTipoPermessoDiSoggiorno(String tipoPermessoDiSoggiorno) {
        this.tipoPermessoDiSoggiorno = tipoPermessoDiSoggiorno;
    }

    public Date getDataScadDocumIdentita() {
        return dataScadDocumIdentita;
    }

    public void setDataScadDocumIdentita(Date dataScadDocumIdentita) {
        this.dataScadDocumIdentita = dataScadDocumIdentita;
    }

    public String getEnteRilascioPermessoDiSoggiorno() {
        return enteRilascioPermessoDiSoggiorno;
    }

    public void setEnteRilascioPermessoDiSoggiorno(String enteRilascioPermessoDiSoggiorno) {
        this.enteRilascioPermessoDiSoggiorno = enteRilascioPermessoDiSoggiorno;
    }

    public Date getDataScadPermessoSoggiorno() {
        return dataScadPermessoSoggiorno;
    }

    public void setDataScadPermessoSoggiorno(Date dataScadPermessoSoggiorno) {
        this.dataScadPermessoSoggiorno = dataScadPermessoSoggiorno;
    }

    @Override
    public String toString() {
        return "MipTAnagraficaCittadino{" +
                "id=" + id +
                ", mipTCittadino=" + mipTCittadino +
                ", dataNascita=" + dataNascita +
                ", sesso='" + sesso + '\'' +
                ", comuneNascita=" + comuneNascita +
                ", statoEsteroNascita=" + statoEsteroNascita +
                ", descrizioneCittaEsteraNascita='" + descrizioneCittaEsteraNascita + '\'' +
                ", codiceCittadinanza=" + codiceCittadinanza +
                ", descCittadinanzaAltro='" + descCittadinanzaAltro + '\'' +
                ", comuneResidenza=" + comuneResidenza +
                ", codiceStatoEsteroResidenza=" + codiceStatoEsteroResidenza +
                ", descrizioneCittaEsteraResidenza='" + descrizioneCittaEsteraResidenza + '\'' +
                ", indirizzoResidenza='" + indirizzoResidenza + '\'' +
                ", capResidenza='" + capResidenza + '\'' +
                ", comuneDomicilio=" + comuneDomicilio +
                ", indirizzoDomicilio='" + indirizzoDomicilio + '\'' +
                ", capDomicilio='" + capDomicilio + '\'' +
                ", recapitoEmail='" + recapitoEmail + '\'' +
                ", recapitoEmail2='" + recapitoEmail2 + '\'' +
                ", recapitoTelefono='" + recapitoTelefono + '\'' +
                ", recapitoTelefono2='" + recapitoTelefono2 + '\'' +
                ", numDocumIdentita='" + numDocumIdentita + '\'' +
                ", enteRilascioDocumIdentita='" + enteRilascioDocumIdentita + '\'' +
                ", dataScadDocumIdentita=" + dataScadDocumIdentita +
                ", numeroPermessoDiSoggiorno='" + numeroPermessoDiSoggiorno + '\'' +
                ", enteRilascioPermessoDiSoggiorno='" + enteRilascioPermessoDiSoggiorno + '\'' +
                ", dataScadPermessoSoggiorno=" + dataScadPermessoSoggiorno +
                ", tipoPermessoDiSoggiorno='" + tipoPermessoDiSoggiorno + '\'' +
                ", descrMotivoPermessoDiSoggiorno='" + descrMotivoPermessoDiSoggiorno + '\'' +
                ", codiceTitoloDiStudio=" + codiceTitoloDiStudio +
                ", titoloDiStudioAltro='" + titoloDiStudioAltro + '\'' +
                ", codUserInserim='" + codUserInserim + '\'' +
                ", dataInserim=" + dataInserim +
                ", codUserAggiorn='" + codUserAggiorn + '\'' +
                ", dataAggiorn=" + dataAggiorn +
                '}';
    }
}