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

@Entity
@Table(name = "ext_tt_comune")
public class ExtTtComune extends PanacheEntityBase {
    @Id
    @Size(max = 6)
    @Column(name = "comune", nullable = false, length = 6)
    private String codiceIstatComune;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prov", nullable = false)
    private ExtTtProvincia provincia;

    @Size(max = 30)
    @NotNull
    @Column(name = "descom", nullable = false, length = 30)
    private String descrizioneComune;

    @Size(max = 5)
    @Column(name = "cap", length = 5)
    private String cap;

    @Size(max = 4)
    @Column(name = "codfisc", length = 4)
    private String codiceFiscaleComune;

    @Size(max = 2)
    @Column(name = "montana", length = 2)
    private String montana;

    @Size(max = 3)
    @Column(name = "ussl", length = 3)
    private String ussl;

    @Size(max = 1)
    @Column(name = "zonaalt", length = 1)
    private String zonaalt;

    @Size(max = 1)
    @Column(name = "zonaalts1", length = 1)
    private String zonaalts1;

    @Size(max = 1)
    @Column(name = "zonaalts2", length = 1)
    private String zonaalts2;

    @Size(max = 2)
    @Column(name = "regagri", length = 2)
    private String regagri;

    @Size(max = 3)
    @Column(name = "prov_old", length = 3)
    private String provOld;

    @Size(max = 6)
    @Column(name = "comune_old", length = 6)
    private String comuneOld;

    @Size(max = 3)
    @Column(name = "usl_old", length = 3)
    private String uslOld;

    @Size(max = 1)
    @Column(name = "prov_new", length = 1)
    private String provNew;

    @Column(name = "popolazione", precision = 10)
    private BigDecimal popolazione;

    @Size(max = 5)
    @Column(name = "prefisso", length = 5)
    private String prefisso;

    @Column(name = "cod_bacino", precision = 2)
    private BigDecimal codBacino;

    public String getCodiceIstatComune() {
        return codiceIstatComune;
    }

    public void setCodiceIstatComune(String codiceIstatComune) {
        this.codiceIstatComune = codiceIstatComune;
    }

    public ExtTtProvincia getProvincia() {
        return provincia;
    }

    public void setProvincia(ExtTtProvincia provincia) {
        this.provincia = provincia;
    }

    public String getDescrizioneComune() {
        return descrizioneComune;
    }

    public void setDescrizioneComune(String descrizioneComune) {
        this.descrizioneComune = descrizioneComune;
    }

    public String getCodiceFiscaleComune() {
        return codiceFiscaleComune;
    }

    public void setCodiceFiscaleComune(String codiceFiscaleComune) {
        this.codiceFiscaleComune = codiceFiscaleComune;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getMontana() {
        return montana;
    }

    public void setMontana(String montana) {
        this.montana = montana;
    }

    public String getUssl() {
        return ussl;
    }

    public void setUssl(String ussl) {
        this.ussl = ussl;
    }

    public String getZonaalt() {
        return zonaalt;
    }

    public void setZonaalt(String zonaalt) {
        this.zonaalt = zonaalt;
    }

    public String getZonaalts1() {
        return zonaalts1;
    }

    public void setZonaalts1(String zonaalts1) {
        this.zonaalts1 = zonaalts1;
    }

    public String getZonaalts2() {
        return zonaalts2;
    }

    public void setZonaalts2(String zonaalts2) {
        this.zonaalts2 = zonaalts2;
    }

    public String getRegagri() {
        return regagri;
    }

    public void setRegagri(String regagri) {
        this.regagri = regagri;
    }

    public String getProvOld() {
        return provOld;
    }

    public void setProvOld(String provOld) {
        this.provOld = provOld;
    }

    public String getComuneOld() {
        return comuneOld;
    }

    public void setComuneOld(String comuneOld) {
        this.comuneOld = comuneOld;
    }

    public String getUslOld() {
        return uslOld;
    }

    public void setUslOld(String uslOld) {
        this.uslOld = uslOld;
    }

    public String getProvNew() {
        return provNew;
    }

    public void setProvNew(String provNew) {
        this.provNew = provNew;
    }

    public BigDecimal getPopolazione() {
        return popolazione;
    }

    public void setPopolazione(BigDecimal popolazione) {
        this.popolazione = popolazione;
    }

    public String getPrefisso() {
        return prefisso;
    }

    public void setPrefisso(String prefisso) {
        this.prefisso = prefisso;
    }

    public BigDecimal getCodBacino() {
        return codBacino;
    }

    public void setCodBacino(BigDecimal codBacino) {
        this.codBacino = codBacino;
    }

}