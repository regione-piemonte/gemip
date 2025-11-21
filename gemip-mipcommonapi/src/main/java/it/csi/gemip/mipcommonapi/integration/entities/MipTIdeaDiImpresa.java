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
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "mip_t_idea_di_impresa")
public class MipTIdeaDiImpresa extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "ideaImpresaSequence",
            sequenceName = "seq_mip_t_idea_di_impresa",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ideaImpresaSequence")
    @Column(name = "id_idea_di_impresa", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "titolo", nullable = false)
    private String titolo;

    @NotNull
    @Column(name = "descrizione_idea_di_impresa", nullable = false)
    private String descrizioneIdeaDiImpresa;

    @Size(max = 1)
    @Column(name = "flg_ricambio_generazionale", length = 1)
    private String flgRicambioGenerazionale;

    @Size(max = 1)
    @Column(name = "flg_erogazione_prima_ora ", length = 1)
    private String flgErogazionePrimaOra;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cod_fonte_conoscenza_mip", nullable = false)
    private MipDFonteConoscenzaMip fonteConoscenzaMip;

    @Size(max = 250)
    @Column(name = "descr_altra_fonte_conoscenza_mip", length = 250)
    private String descrizioneAltraFonteConoscenzaMip;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_stato_idea_di_impresa", nullable = false)
    private MipDStatoIdeaDiImpresa statoIdeaDiImpresa;

    @Column(name = "d_cambio_stato")
    private Date dataCambioStato;

    @Column(name = "d_validazione_business_plan")
    private Date dataValidBusinessPlan;

    @Column(name = "d_firma_patto_di_servizio")
    private Date dataFirmaPattoServizio;

    @Column(name = "id_tutor")
    private Long idTutor;

    @Column(name = "d_scelta_tutor")
    private Date dataSceltaTutor;

    @Column(name = "id_idea_di_impresa_sostituente")
    private Long idIdeaDiImpresaSostituente;

    @Column(name = "note_commenti ")
    private String noteCommenti;

    @Column(name = "commenti_interni")
    private String commentiInterni;

    @Column(name = "flg_sblocco_area_territoriale")
    private String flgSbloccoAreaTerritoriale; // S/null

    @Size(max = 16)
    @NotNull
    @Column(name = "cod_user_inserim", nullable = false, length = 16)
    private String codUserInserim;

    @NotNull
    @Column(name = "d_inserim", nullable = false)
    private Date dataInserim;

    @Size(max = 16)
    @NotNull
    @Column(name = "cod_user_aggiorn", nullable = false, length = 16)
    private String codUserAggiorn;

    @NotNull
    @Column(name = "d_aggiorn", nullable = false)
    private Date dataAggiorn;

    public String getFlgErogazionePrimaOra() {
        return flgErogazionePrimaOra;
    }

    public void setFlgErogazionePrimaOra(String flgErogazionePrimaOra) {
        this.flgErogazionePrimaOra = flgErogazionePrimaOra;
    }

    public Date getDataFirmaPattoServizio() {
        return dataFirmaPattoServizio;
    }

    public void setDataFirmaPattoServizio(Date dataFirmaPattoServizio) {
        this.dataFirmaPattoServizio = dataFirmaPattoServizio;
    }

    public String getNoteCommenti() {
        return noteCommenti;
    }

    public void setNoteCommenti(String noteCommenti) {
        this.noteCommenti = noteCommenti;
    }

    public String getCommentiInterni() {
        return commentiInterni;
    }

    public void setCommentiInterni(String commentiInterni) {
        this.commentiInterni = commentiInterni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizioneIdeaDiImpresa() {
        return descrizioneIdeaDiImpresa;
    }

    public void setDescrizioneIdeaDiImpresa(String descrizioneIdeaDiImpresa) {
        this.descrizioneIdeaDiImpresa = descrizioneIdeaDiImpresa;
    }

    public String getFlgRicambioGenerazionale() {
        return flgRicambioGenerazionale;
    }

    public void setFlgRicambioGenerazionale(String flgRicambioGenerazionale) {
        this.flgRicambioGenerazionale = flgRicambioGenerazionale;
    }

    public MipDFonteConoscenzaMip getFonteConoscenzaMip() {
        return fonteConoscenzaMip;
    }

    public void setFonteConoscenzaMip(MipDFonteConoscenzaMip fonteConoscenzaMip) {
        this.fonteConoscenzaMip = fonteConoscenzaMip;
    }

    public String getDescrizioneAltraFonteConoscenzaMip() {
        return descrizioneAltraFonteConoscenzaMip;
    }

    public void setDescrizioneAltraFonteConoscenzaMip(String descrizioneAltraFonteConoscenzaMip) {
        this.descrizioneAltraFonteConoscenzaMip = descrizioneAltraFonteConoscenzaMip;
    }

    public MipDStatoIdeaDiImpresa getStatoIdeaDiImpresa() {
        return statoIdeaDiImpresa;
    }

    public void setStatoIdeaDiImpresa(MipDStatoIdeaDiImpresa statoIdeaDiImpresa) {
        this.statoIdeaDiImpresa = statoIdeaDiImpresa;
    }

    public Long getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(Long idTutor) {
        this.idTutor = idTutor;
    }

    public Date getDataSceltaTutor() {
        return dataSceltaTutor;
    }

    public void setDataSceltaTutor(Date dataSceltaTutor) {
        this.dataSceltaTutor = dataSceltaTutor;
    }

    public Long getIdIdeaDiImpresaSostituente() {
        return idIdeaDiImpresaSostituente;
    }

    public void setIdIdeaDiImpresaSostituente(Long idIdeaDiImpresaSostituente) {
        this.idIdeaDiImpresaSostituente = idIdeaDiImpresaSostituente;
    }

    public String getCodUserInserim() {
        return codUserInserim;
    }

    public void setCodUserInserim(String codUserInserim) {
        this.codUserInserim = codUserInserim;
    }

    public Date getDataInserim() {
        return dataInserim;
    }

    public void setDataInserim(Date dataInserim) {
        this.dataInserim = dataInserim;
    }

    public String getCodUserAggiorn() {
        return codUserAggiorn;
    }

    public void setCodUserAggiorn(String codUserAggiorn) {
        this.codUserAggiorn = codUserAggiorn;
    }

    public Date getDataAggiorn() {
        return dataAggiorn;
    }

    public void setDataAggiorn(Date dataAggiorn) {
        this.dataAggiorn = dataAggiorn;
    }

    public String getFlgSbloccoAreaTerritoriale() {
        return flgSbloccoAreaTerritoriale;
    }

    public void setFlgSbloccoAreaTerritoriale(String flgSbloccoAreaTerritoriale) {
        this.flgSbloccoAreaTerritoriale = flgSbloccoAreaTerritoriale;
    }

    public Date getDataCambioStato() {
        return dataCambioStato;
    }

    public void setDataCambioStato(Date dataCambioStato) {
        this.dataCambioStato = dataCambioStato;
    }

    public Date getDataValidBusinessPlan() {
        return dataValidBusinessPlan;
    }

    public void setDataValidBusinessPlan(Date dataValidBusinessPlan) {
        this.dataValidBusinessPlan = dataValidBusinessPlan;
    }
    
}