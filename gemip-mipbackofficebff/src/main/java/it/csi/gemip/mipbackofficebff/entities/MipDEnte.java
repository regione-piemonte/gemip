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
@Table(name = "mip_d_ente")
public class MipDEnte  extends PanacheEntityBase {
    @Id
    @Column(name = "id_ente", nullable = false, precision = 2)
    private BigDecimal id;

    @Size(max = 50)
    @NotNull
    @Column(name = "descrizione_ente", nullable = false, length = 50)
    private String descrizioneEnte;

    @Size(max = 1)
    @NotNull
    @Column(name = "gruppo_operatore", nullable = false, length = 1)
    private String gruppoOperatore;

    @NotNull
    @Column(name = "cod_operatore", nullable = false, precision = 5)
    private BigDecimal codOperatore;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getDescrizioneEnte() {
        return descrizioneEnte;
    }

    public void setDescrizioneEnte(String descrizioneEnte) {
        this.descrizioneEnte = descrizioneEnte;
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

}