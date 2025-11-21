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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "mip_d_condizione_occupazionale")
public class MipDCondizioneOccupazionale extends PanacheEntityBase {
    @Id
    @Size(max = 2)
    @Column(name = "cod_condizione_occupazionale", nullable = false, length = 2)
    private String codiceCondizioneOccupazionale;

    @Size(max = 100)
    @NotNull
    @Column(name = "descr_condizione_occupazionale", nullable = false, length = 100)
    private String descrizioneCondizioneOccupazionale;

    @NotNull
    @Column(name = "d_inizio", nullable = false)
    private Date dataInizio;

    @Column(name = "d_fine")
    private Date dataFine;

    public String getCodiceCondizioneOccupazionale() {
        return codiceCondizioneOccupazionale;
    }

    public void setCodiceCondizioneOccupazionale(String codiceCondizioneOccupazionale) {
        this.codiceCondizioneOccupazionale = codiceCondizioneOccupazionale;
    }

    public String getDescrizioneCondizioneOccupazionale() {
        return descrizioneCondizioneOccupazionale;
    }

    public void setDescrizioneCondizioneOccupazionale(String descrizioneCondizioneOccupazionale) {
        this.descrizioneCondizioneOccupazionale = descrizioneCondizioneOccupazionale;
    }

    public Date getdInizio() {
        return dataInizio;
    }

    public void setdInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getdFine() {
        return dataFine;
    }

    public void setdFine(Date dataFine) {
        this.dataFine = dataFine;
    }
}