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

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mip_d_svantaggio_abitativo")
public class MipDSvantaggioAbitativo extends PanacheEntityBase {
    @Id
    @Column(name = "id_svantaggio_abitativo", nullable = false, precision=10, columnDefinition = "numeric")
    private Long id;

    @Size(max = 200)
    @NotNull
    @Column(name = "descr_svantaggio_abitativo", nullable = false, length = 200)
    private String descrizioneSvantaggioAbitativo;

    @NotNull
    @Column(name = "d_inizio", nullable = false)
    private Date dataInizio;

    @Column(name = "d_fine")
    private Date dataFine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizioneSvantaggioAbitativo() {
        return descrizioneSvantaggioAbitativo;
    }

    public void setDescrizioneSvantaggioAbitativo(String descrizioneSvantaggioAbitativo) {
        this.descrizioneSvantaggioAbitativo = descrizioneSvantaggioAbitativo;
    }

    public Date getDInizio() {
        return dataInizio;
    }

    public void setDInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDFine() {
        return dataFine;
    }

    public void setDFine(Date dataFine) {
        this.dataFine = dataFine;
    }

}