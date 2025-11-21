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

import java.time.Instant;

@Entity
@Table(name = "ext_tab_cittadinanza")
public class ExtTabCittadinanza extends PanacheEntityBase {
    @Id
    @Size(max = 3)
    @Column(name = "cod_istat_cittadinanza", nullable = false, length = 3)
    private String codiceCittadinanza;

    @Size(max = 100)
    @NotNull
    @Column(name = "descr_cittadinanza", nullable = false, length = 100)
    private String descrizione;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_nazionalita")
    private ExtTabNazionalita codiceNazionalita;

    @Size(max = 1)
    @Column(name = "flg_sett_lav", length = 1)
    private String flgSettLav;

    @Size(max = 3)
    @Column(name = "codice", length = 3)
    private String codiceCittadinanzaOld;

    @Size(max = 100)
    @Column(name = "descrizione_precedente", length = 100)
    private String descrizionePrecedente;

    @NotNull
    @Column(name = "d_inizio", nullable = false)
    private Instant dataInizio;

    @Column(name = "d_fine")
    private Instant dataFine;

    public String getCodiceCittadinanza() {
        return codiceCittadinanza;
    }

    public void setCodiceCittadinanza(String codiceCittadinanza) {
        this.codiceCittadinanza = codiceCittadinanza;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public ExtTabNazionalita getCodiceNazionalita() {
        return codiceNazionalita;
    }

    public void setCodiceNazionalita(ExtTabNazionalita codiceNazionalita) {
        this.codiceNazionalita = codiceNazionalita;
    }

    public String getCodiceCittadinanzaOld() {
        return codiceCittadinanzaOld;
    }

    public void setCodiceCittadinanzaOld(String codiceCittadinanzaOld) {
        this.codiceCittadinanzaOld = codiceCittadinanzaOld;
    }

    public String getFlgSettLav() {
        return flgSettLav;
    }

    public void setFlgSettLav(String flgSettLav) {
        this.flgSettLav = flgSettLav;
    }


    public String getDescrizionePrecedente() {
        return descrizionePrecedente;
    }

    public void setDescrizionePrecedente(String descrizionePrecedente) {
        this.descrizionePrecedente = descrizionePrecedente;
    }

    public Instant getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Instant dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Instant getDataFine() {
        return dataFine;
    }

    public void setDataFine(Instant dataFine) {
        this.dataFine = dataFine;
    }

}