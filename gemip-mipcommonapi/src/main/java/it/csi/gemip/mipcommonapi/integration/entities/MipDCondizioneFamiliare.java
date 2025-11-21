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
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "mip_d_condizione_familiare")
public class MipDCondizioneFamiliare extends PanacheEntityBase {
    @Id
    @Column(name = "id_condizione_familiare",nullable = false ,length = 2)
    private Long id;
    @Column(name = "descr_condizione_familiare",nullable = false ,length = 200)
    private String descrCondizioneFamiliare;
    @Column(name = "d_inizio ",nullable = false)
    private Date dataInizio;
    @Column(name = "d_fine" ,length = 2)
    private Date dataFine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrCondizioneFamiliare() {
        return descrCondizioneFamiliare;
    }

    public void setDescrCondizioneFamiliare(String descrCondizioneFamiliare) {
        this.descrCondizioneFamiliare = descrCondizioneFamiliare;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }
}
