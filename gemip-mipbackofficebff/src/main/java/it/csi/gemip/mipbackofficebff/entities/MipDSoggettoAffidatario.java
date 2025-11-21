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

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "mip_d_soggetto_affidatario")
public class MipDSoggettoAffidatario extends PanacheEntityBase {
    @Id
    @Column(name = "id_soggetto_affidatario", nullable = false, precision = 10)
    private Integer id;

    @Size(max = 1)
    @NotNull
    @Column(name = "gruppo_operatore", nullable = false, length = 1)
    private String gruppoOperatore;

    @NotNull
    @Column(name = "cod_operatore", nullable = false, precision = 5)
    private BigDecimal codOperatore;

    @NotNull
    @Column(name = "denominazione", nullable = false)
    private String denominazione;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}