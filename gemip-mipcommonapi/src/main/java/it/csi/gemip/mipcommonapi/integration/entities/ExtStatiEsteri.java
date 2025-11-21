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

@Entity
@Table(name = "ext_stati_esteri")
public class ExtStatiEsteri extends PanacheEntityBase {
    @Id
    @Column(name = "cod_stato", nullable = false, precision = 3)
    private BigDecimal codStato;

    @Size(max = 30)
    @NotNull
    @Column(name = "descrizione_stato", nullable = false, length = 30)
    private String descrizioneStato;

    @Size(max = 4)
    @Column(name = "stato_cod_fiscale", length = 4)
    private String statoCodFiscale;

    @Size(max = 2)
    @Column(name = "sigla_nazione", length = 2)
    private String siglaNazione;

    public BigDecimal getCodStato() {
        return codStato;
    }

    public void setCodStato(BigDecimal codStato) {
        this.codStato = codStato;
    }

    public String getDescrizioneStato() {
        return descrizioneStato;
    }

    public void setDescrizioneStato(String descrizioneStato) {
        this.descrizioneStato = descrizioneStato;
    }

    public String getStatoCodFiscale() {
        return statoCodFiscale;
    }

    public void setStatoCodFiscale(String statoCodFiscale) {
        this.statoCodFiscale = statoCodFiscale;
    }

    public String getSiglaNazione() {
        return siglaNazione;
    }

    public void setSiglaNazione(String siglaNazione) {
        this.siglaNazione = siglaNazione;
    }

}