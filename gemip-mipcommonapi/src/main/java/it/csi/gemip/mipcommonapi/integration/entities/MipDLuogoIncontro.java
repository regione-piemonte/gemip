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
@Table(name = "mip_d_luogo_incontro")
public class MipDLuogoIncontro extends PanacheEntityBase {
    @Id
    @Column(name = "id_luogo_incontro", nullable = false, precision = 10, columnDefinition = "numeric" )
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cod_area_territoriale", nullable = false)
    private ExtGmopDAreaTerritoriale areaTerritoriale;

    @Size(max = 30)
    @NotNull
    @Column(name = "denominazione", nullable = false, length = 30)
    private String denominazione;

    @Size(max = 255)
    @NotNull
    @Column(name = "indirizzo", nullable = false)
    private String indirizzo;

    @Size(max = 40)
    @NotNull
    @Column(name = "cap", nullable = false, length = 40, columnDefinition = "bpchar" )
    private String cap;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cod_istat_comune", nullable = false)
    private ExtTtComune comune;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExtGmopDAreaTerritoriale getCodAreaTerritoriale() {
        return areaTerritoriale;
    }

    public void setCodAreaTerritoriale(ExtGmopDAreaTerritoriale codAreaTerritoriale) {
        this.areaTerritoriale = codAreaTerritoriale;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
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

    public ExtTtComune getCodIstatComune() {
        return comune;
    }

    public void setCodIstatComune(ExtTtComune codIstatComune) {
        this.comune = codIstatComune;
    }

}