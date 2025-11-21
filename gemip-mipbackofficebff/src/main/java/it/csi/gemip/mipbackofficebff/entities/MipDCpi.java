package it.csi.gemip.mipbackofficebff.entities;

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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "mip_d_cpi")
public class MipDCpi {
    @Id
    @Column(name = "id_cpi", nullable = false, precision = 3)
    private BigDecimal id;

    @Size(max = 11)
    @NotNull
    @Column(name = "codice_cpi", nullable = false, length = 11)
    private String codiceCpi;

    @Size(max = 50)
    @NotNull
    @Column(name = "cpi", nullable = false, length = 50)
    private String cpi;

    @Size(max = 1)
    @NotNull
    @Column(name = "gruppo_operatore", nullable = false, length = 1)
    private String gruppoOperatore;

    @NotNull
    @Column(name = "cod_operatore", nullable = false, precision = 5)
    private BigDecimal codOperatore;

    @Size(max = 4)
    @NotNull
    @Column(name = "sede", nullable = false, length = 4)
    private String sede;

    @Size(max = 300)
    @NotNull
    @Column(name = "indirizzo", nullable = false, length = 300)
    private String indirizzo;

    @Size(max = 5)
    @NotNull
    @Column(name = "cap", nullable = false, length = 5)
    private String cap;

    @Size(max = 6)
    @NotNull
    @Column(name = "cod_istat_comune", nullable = false, length = 6)
    private String codIstatComune;

    @Size(max = 3)
    @NotNull
    @Column(name = "cod_provincia", nullable = false, length = 3)
    private String codProvincia;

    @Size(max = 20)
    @NotNull
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @Size(max = 20)
    @Column(name = "telefono_2", length = 20)
    private String telefono2;

    @Size(max = 20)
    @Column(name = "telefono_3", length = 20)
    private String telefono3;

    @Size(max = 20)
    @Column(name = "numero_verde", length = 20)
    private String numeroVerde;

    @Size(max = 60)
    @Column(name = "email", length = 60)
    private String email;

    @Size(max = 50)
    @Column(name = "indirizzo_web", length = 50)
    private String indirizzoWeb;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getCodiceCpi() {
        return codiceCpi;
    }

    public void setCodiceCpi(String codiceCpi) {
        this.codiceCpi = codiceCpi;
    }

    public String getCpi() {
        return cpi;
    }

    public void setCpi(String cpi) {
        this.cpi = cpi;
    }

    public String getGruppoOperatore() {
        return gruppoOperatore;
    }

    public void setGruppoOperatore(String gruppoOperatore) {
        this.gruppoOperatore = gruppoOperatore;
    }

    public BigDecimal getCodOperatore() {
        return codOperatore;
    }

    public void setCodOperatore(BigDecimal codOperatore) {
        this.codOperatore = codOperatore;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCodIstatComune() {
        return codIstatComune;
    }

    public void setCodIstatComune(String codIstatComune) {
        this.codIstatComune = codIstatComune;
    }

    public String getCodProvincia() {
        return codProvincia;
    }

    public void setCodProvincia(String codProvincia) {
        this.codProvincia = codProvincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getTelefono3() {
        return telefono3;
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }

    public String getNumeroVerde() {
        return numeroVerde;
    }

    public void setNumeroVerde(String numeroVerde) {
        this.numeroVerde = numeroVerde;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIndirizzoWeb() {
        return indirizzoWeb;
    }

    public void setIndirizzoWeb(String indirizzoWeb) {
        this.indirizzoWeb = indirizzoWeb;
    }

}