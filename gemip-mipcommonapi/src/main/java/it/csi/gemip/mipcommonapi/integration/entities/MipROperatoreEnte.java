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

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "mip_r_operatore_ente")
public class MipROperatoreEnte {
    @Id
    @Column(name = "id_operatore_ente", nullable = false, precision = 10)
    private BigDecimal id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_operatore", nullable = false)
    private MipDOperatore idOperatore;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ente", nullable = false)
    private MipDEnte idEnte;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public MipDOperatore getIdOperatore() {
        return idOperatore;
    }

    public void setIdOperatore(MipDOperatore idOperatore) {
        this.idOperatore = idOperatore;
    }

    public MipDEnte getIdEnte() {
        return idEnte;
    }

    public void setIdEnte(MipDEnte idEnte) {
        this.idEnte = idEnte;
    }

}