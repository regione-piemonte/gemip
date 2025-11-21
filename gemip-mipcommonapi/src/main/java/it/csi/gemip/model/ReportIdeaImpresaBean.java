package it.csi.gemip.model;

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

import java.util.Date;

import it.csi.gemip.mipcommonapi.integration.entities.ExtTtComune;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@NamedNativeQuery(name = "ReportIdeaImpresaBean.getAll", query = "SELECT\n" +
        "i.id_idea_di_impresa, i.titolo, i.descrizione_idea_di_impresa, i.flg_ricambio_generazionale, i.cod_fonte_conoscenza_mip, i.descr_altra_fonte_conoscenza_mip, i.id_stato_idea_di_impresa, i.d_cambio_stato, i.id_tutor,i.flg_erogazione_prima_ora, i.d_firma_patto_di_servizio , i.d_validazione_business_plan, i.id_idea_di_impresa_sostituente, i.cod_user_inserim, i.d_inserim, i.cod_user_aggiorn, i.d_aggiorn,i.commenti_interni ,i.note_commenti ,\n" +
        "c.id_cittadino, c.codice_fiscale, c.cognome, c.nome, \n" +
        "    (CASE \n" +
        "        WHEN i.id_idea_di_impresa_sostituente IS NULL THEN CONCAT(c.cognome, ' ', c.nome)  \n" +
        "        ELSE (\n" +
        "            SELECT CONCAT(mtc.cognome, ' ', mtc.nome) \n" +
        "            FROM mip_r_idea_di_impresa_cittadino mridic \n" +
        "            JOIN mip_t_cittadino mtc ON mtc.id_cittadino = mridic.id_cittadino\n" +
        "            WHERE mridic.id_idea_di_impresa = i.id_idea_di_impresa_sostituente\n" +
        "        )\n" +
        "    END) AS utente_principale,\n" +
        "mtip.d_incontro data_incontro_preaccoglienza, mtip.flg_incontro_telefonico flg_incontro_telefonico,mdli.denominazione sede_incontro_preaccoglienza,\n" +
        "a.d_nascita, a.sesso , a.cod_istat_comune_nascita, a.cod_stato_estero_nascita, a.descr_citta_estera_nascita, a.cod_istat_cittadinanza, a.descr_cittadinanza_altro , a.cod_istat_comune_residenza, a.cod_stato_estero_residenza, a.descr_citta_estera_residenza, a.indirizzo_residenza, a.cap_residenza, a.cod_istat_comune_domicilio, a.indirizzo_domicilio, a.cap_domicilio, a.recapito_email, a.recapito_email_2 , a.recapito_telefono, a.recapito_telefono_2 , a.num_permesso_di_soggiorno,a.descr_motivo_permesso_di_soggiorno  , a.tipo_permesso_di_soggiorno,a.cod_titolo_di_studio, a.titolo_di_studio_altro ,\n" +
        "comune_nascita.descom as des_comune_nascita, comune_nascita.prov as prov_nascita, prov_nascita.desprov as des_prov_nascita,\n" +
        "comune_residenza.descom as des_comune_residenza, comune_residenza.prov as prov_residenza, prov_residenza.desprov as des_prov_residenza,\n" +
        "comune_domicilio.descom as des_comune_domicilio, comune_domicilio.prov as prov_domicilio, prov_domicilio.desprov as des_prov_domicilio,\n" +
        "decifra(ae.condizione_occupazionale, :encriptionKey)as cod_condizione_occupazionale,\n" +
        "decifra(ae.condizione_occupazionale_altro , :encriptionKey)as cod_condizione_occupazionale_altro,\n" +
        "decifra(ae.svantaggio_abitativo, :encriptionKey)as svantaggio_abitativo,\n" +
        "decifra(ae.condizione_familiare , :encriptionKey)as condizione_familiare,\n" +
        "occ.descr_condizione_occupazionale,\n" +
        "dsa.descr_svantaggio_abitativo,\n" +
        "dcf.descr_condizione_familiare,\n" +
        "s.descr_stato_idea_di_impresa,\n" +
        "cittadinanza.descr_cittadinanza,\n" +
        "stud.descr_titolo_di_studio,\n" +
        "fonte.descr_fonte_conoscenza_mip,\n" +
        "area.cod_area_territoriale, area.descr_area_territoriale,\n" +
        "sa.id_soggetto_attuatore, sa.gruppo_operatore, sa.cod_operatore, sa.denominazione denominazione_soggetto_attu, sa.telefono, sa.email, i.d_scelta_tutor data_associazione_soggetto_attuatore\n" +
        "FROM gemip.mip_t_idea_di_impresa i\n" +
        "join mip_r_idea_di_impresa_cittadino ic on i.id_idea_di_impresa = ic.id_idea_di_impresa \n" +
        "join mip_t_cittadino c on c.id_cittadino = ic.id_cittadino\n" +
        "left join mip_d_stato_idea_di_impresa s on i.id_stato_idea_di_impresa = s.id_stato_idea_di_impresa\n" +
        "left join mip_t_anagrafica_cittadino a on a.id_cittadino =c.id_cittadino \n" +
        "left join mip_t_anagrafica_cittadino_exten ae on ae.id_cittadino = c.id_cittadino \n" +
        "left join ext_tt_comune comune_nascita on comune_nascita.comune = a.cod_istat_comune_nascita \n" +
        "left join ext_tt_comune comune_residenza on comune_residenza.comune = a.cod_istat_comune_residenza \n" +
        "left join ext_tt_comune comune_domicilio on comune_domicilio.comune = a.cod_istat_comune_domicilio \n" +
        "left join ext_tt_provincia prov_nascita on prov_nascita.prov = comune_nascita.prov \n" +
        "left join ext_tt_provincia prov_residenza on prov_residenza.prov = comune_residenza.prov \n" +
        "left join ext_tt_provincia prov_domicilio on prov_domicilio.prov = comune_domicilio.prov \n" +
        "left join ext_tab_cittadinanza cittadinanza on cittadinanza.cod_istat_cittadinanza = a.cod_istat_cittadinanza \n" +
        "left join mip_d_condizione_occupazionale occ on occ.cod_condizione_occupazionale = decifra(ae.condizione_occupazionale, :encriptionKey )\n" +
        "left join mip_d_svantaggio_abitativo dsa on dsa.id_svantaggio_abitativo  = CAST(decifra(ae.svantaggio_abitativo, :encriptionKey) AS numeric) \n" +
        "left join mip_d_condizione_familiare dcf on dcf.id_condizione_familiare  = CAST(decifra(ae.condizione_familiare , :encriptionKey) AS numeric) \n" +
        "left join mip_d_soggetto_attuatore sa on sa.id_soggetto_attuatore = i.id_tutor\n" +
        "left join mip_r_cittadino_incontro_preacc rpreacc on rpreacc.id_cittadino = c.id_cittadino\n" +
        "left join mip_t_incontro_preaccoglienza mtip  on rpreacc.id_incontro_preaccoglienza  = mtip.id_incontro_preaccoglienza \n" +
        "left join mip_d_luogo_incontro mdli on mdli.id_luogo_incontro = mtip.id_luogo_incontro \n" +
        "left join ext_gmop_d_area_territoriale area on area.cod_area_territoriale = rpreacc.cod_area_territoriale_selezionata\n" +
        "left join mip_d_titolo_di_studio stud on stud.cod_titolo_di_studio = a.cod_titolo_di_studio \n" +
        "left join mip_d_fonte_conoscenza_mip fonte on fonte.cod_fonte_conoscenza_mip = i.cod_fonte_conoscenza_mip\n" +
        "WHERE (cast(:dataInseritaDa AS DATE) IS NULL OR i.d_inserim >= :dataInseritaDa)\n" +
        "AND (cast(:dataInseritaA AS DATE) IS NULL OR i.d_inserim <= :dataInseritaA)\n" +
        "AND (cast(:idSoggettoAttuatore AS INT) IS NULL OR sa.id_soggetto_attuatore = :idSoggettoAttuatore)\n" +
        "AND (cast(:idCodAreaTerritoriale AS VARCHAR) IS NULL OR sa.cod_area_territoriale = :idCodAreaTerritoriale)\n" +
        "AND (cast(:idStatoIdea AS INT) IS NULL OR i.id_stato_idea_di_impresa = :idStatoIdea)", resultClass = ReportIdeaImpresaBean.class)
public class ReportIdeaImpresaBean {

    // ==== CAMPI IDEA DI IMPRESA
    @Id
    @Column(name = "id_idea_di_impresa")
    private Long id = null;
    private String titolo = null;
    @Column(name = "descrizione_idea_di_impresa")
    private String descrizioneIdeaDiImpresa = null;
    @Column(name = "flg_ricambio_generazionale")
    private String flgRicambioGenerazionale = null;
    @Column(name = "flg_erogazione_prima_ora")
    private String flgErogazionePrimaOra = null;
    @Column(name = "id_tutor")
    private Long idTutor = null;
    @Column(name = "d_validazione_business_plan")
    private Date dataValidBusinessPlan = null;
    @Column(name = "d_firma_patto_di_servizio")
    private Date dataFirmaPattoServizio = null;
    @Column(name = "cod_user_inserim")
    private String codUserInserim = null;
    @Column(name = "d_inserim")
    private Date dataInserim = null;
    @Column(name = "cod_user_aggiorn")
    private String codUserAggiorn = null;
    @Column(name = "d_aggiorn")
    private Date dataAggiorn = null;

    @Column(name = "note_commenti")
    private String noteCommenti;
    @Column(name = "commenti_interni")
    private String commentiInterni;
    // ==== CAMPI CITTADINO===============

    @Column(name = "id_cittadino")
    private Long idCittadino;
    @Column(name = "codice_fiscale")
    private String codiceFiscale;
    @Column(name = "cognome")
    private String cognome;
    @Column(name = "nome")
    private String nome;

    // ==== UTENTE PRINCIPALE ==============
    @Column(name = "utente_principale")
    private String utentePrincipale;

    // ==== CAMPI ANAGRAFICA ===============

    @Column(name = "data_incontro_preaccoglienza")
    private Date dataIncontroPreaccoglienza;

    @Column(name = "flg_incontro_telefonico")
    private String flgIncontroTelefonico;

    @Column(name = "sede_incontro_preaccoglienza")
    private String sedeIncontroPreaccoglienza;

    @Column(name = "sesso")
    private String sesso;


    // ==== SOGGETTO ATTUATORE =========
    @Column(name = "data_associazione_soggetto_attuatore")
    private Date dataAssociazioneSoggettoAttuatore;
    // ==== CAMPI ANAGRAFICA ===============

    @NotNull
    @Column(name = "d_nascita", nullable = false)
    private Date dataNascita;

    @Size(max = 100)
    @Column(name = "descr_citta_estera_nascita", length = 100)
    private String descrizioneCittaEsteraNascita;

    @Column(name = "cod_stato_estero_residenza", precision = 3)
    private Long codiceStatoEsteroResidenza;

    @Size(max = 100)
    @Column(name = "descr_citta_estera_residenza", length = 100)
    private String descrizioneCittaEsteraResidenza;

    @Size(max = 100)
    @Column(name = "indirizzo_residenza", length = 100)
    private String indirizzoResidenza;

    @Column(name = "cap_residenza", precision = 5)
    private String capResidenza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_istat_comune_domicilio")
    private ExtTtComune comuneDomicilio;

    @Size(max = 100)
    @Column(name = "indirizzo_domicilio", length = 100)
    private String indirizzoDomicilio;

    @Column(name = "cap_domicilio", precision = 5)
    private String capDomicilio;

    @Size(max = 100)
    @Column(name = "recapito_email", length = 100)
    private String recapitoEmail;
    @Size(max = 100)
    @Column(name = "recapito_email_2", length = 100)
    private String recapitoEmail2;

    @Size(max = 50)
    @Column(name = "recapito_telefono", length = 50)
    private String recapitoTelefono;
    @Size(max = 50)
    @Column(name = "recapito_telefono_2", length = 50)
    private String recapitoTelefono2;

    @Size(max = 50)
    @Column(name = "num_permesso_di_soggiorno", length = 50)
    private String numeroPermessoDiSoggiorno;

    @Size(max = 50)
    @Column(name = "descr_motivo_permesso_di_soggiorno", length = 50)
    private String descrMotivoPermessoDiSoggiorno;

    @Size(max = 50)
    @Column(name = "tipo_permesso_di_soggiorno", length = 50)
    private String tipoPermessoDiSoggiorno;

    // Questi campi nell'anagrafe sono ManyToOne:
    @Column(name = "des_comune_nascita")
    private String desComuneNascita;

    @Column(name = "des_comune_residenza")
    private String desComuneResidenza;

    @Column(name = "des_comune_domicilio")
    private String desComuneDomicilio;

    @Column(name = "prov_nascita")
    private String provinciaNascita;

    @Column(name = "prov_residenza")
    private String provinciaResidenza;

    @Column(name = "prov_domicilio")
    private String provinciaDomicilio;

    @Column(name = "des_prov_nascita")
    private String desProvinciaNascita;

    @Column(name = "des_prov_residenza")
    private String desProvinciaResidenza;

    @Column(name = "des_prov_domicilio")
    private String desProvinciaDomicilio;

    // ==== AREA TERRITORIALE ===============

    @Column(name = "cod_area_territoriale", nullable = false, length = 2)
    private String codiceAreaTerritoriale;

    @Column(name = "descr_area_territoriale", nullable = false, length = 100)
    private String descrizioneAreaTerritoriale;

    // ===== STATO =========================

    @Column(name = "id_stato_idea_di_impresa", nullable = false)
    private Long idStatoIdeaImpresa;
    @Column(name = "descr_stato_idea_di_impresa", nullable = false, length = 100)
    private String descrizioneStatoIdeaDiImpresa;

    // ===== SOGGETTO ATTUATORE =========================

    @Column(name = "id_soggetto_attuatore", nullable = false)
    private Long idSoggettoAttuatore;

    @NotNull
    @Column(name = "gruppo_operatore", nullable = false, length = 1)
    private String gruppoOperatore;

    @NotNull
    @Column(name = "cod_operatore", nullable = false, precision = 5)
    private Long codOperatore;

    @Column(name = "denominazione_soggetto_attu", nullable = false, precision = 5)
    private String denominazione;

    @Column(name = "email", nullable = false, precision = 5)
    private String email;


    // == TITOLO DI STUDIO ===========================================

    @Column(name = "cod_titolo_di_studio", nullable = false, length = 2)
    private String codiceTitoloDiStudio;

    @Column(name = "descr_titolo_di_studio", nullable = false, length = 200)
    private String descrizioneTitoloDiStudio;
    @Column(name = "titolo_di_studio_altro", nullable = false, length = 2)
    private String titoloDiStudioAltro;

    // ===== ANAGRAFICA ESTESA & CONDIZIONE OCCUPAZIONALE & CONDIZIONE FAMILIARE  =========================


    @Column(name = "svantaggio_abitativo")
    private String svantaggioAbitativo;
    @Column(name = "descr_svantaggio_abitativo")
    private String descrSvantaggioAbitativo;
    @Column(name = "cod_condizione_occupazionale", nullable = false, length = 2)
    private String codiceCondizioneOccupazionale;
    @Column(name = "cod_condizione_occupazionale_altro", nullable = false, length = 2)
    private String condizioneOccupazionaleAltro;
    @Column(name = "descr_condizione_occupazionale", nullable = false, length = 100)
    private String descrizioneCondizioneOccupazionale;
    @Column(name = "condizione_familiare", nullable = false)
    private String condizioneFamiliare;
    @Column(name = "descr_condizione_familiare", nullable = false)
    private String descrCondizioneFamiliare;

    // === CITTADINANZA ===========================

    @Column(name = "descr_cittadinanza", nullable = false, length = 100)
    private String descrCittadinanza;
    @Column(name = "descr_cittadinanza_altro", nullable = false, length = 100)
    private String descCittadinanzaAltro;

    // === FONTE CONOSCENZA  ===========================

    @Column(name = "cod_fonte_conoscenza_mip", nullable = false, length = 2)
    private String codiceFonteConoscenzaMip;

    @Column(name = "descr_fonte_conoscenza_mip", nullable = false, length = 100)
    private String descrizioneFonteConoscenzaMip;

    // =============================================

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

    public Long getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(Long idTutor) {
        this.idTutor = idTutor;
    }

    public Date getDataValidBusinessPlan() {
        return dataValidBusinessPlan;
    }

    public void setDataValidBusinessPlan(Date dataValidBusinessPlan) {
        this.dataValidBusinessPlan = dataValidBusinessPlan;
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

    public String getDescrMotivoPermessoDiSoggiorno() {
        return descrMotivoPermessoDiSoggiorno;
    }

    public void setDescrMotivoPermessoDiSoggiorno(String descrMotivoPermessoDiSoggiorno) {
        this.descrMotivoPermessoDiSoggiorno = descrMotivoPermessoDiSoggiorno;
    }

    public String getTipoPermessoDiSoggiorno() {
        return tipoPermessoDiSoggiorno;
    }

    public void setTipoPermessoDiSoggiorno(String tipoPermessoDiSoggiorno) {
        this.tipoPermessoDiSoggiorno = tipoPermessoDiSoggiorno;
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

    public Long getIdCittadino() {
        return idCittadino;
    }

    public void setIdCittadino(Long idCittadino) {
        this.idCittadino = idCittadino;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getDescrizioneCittaEsteraNascita() {
        return descrizioneCittaEsteraNascita;
    }

    public void setDescrizioneCittaEsteraNascita(String descrizioneCittaEsteraNascita) {
        this.descrizioneCittaEsteraNascita = descrizioneCittaEsteraNascita;
    }

    public Long getCodiceStatoEsteroResidenza() {
        return codiceStatoEsteroResidenza;
    }

    public void setCodiceStatoEsteroResidenza(Long codiceStatoEsteroResidenza) {
        this.codiceStatoEsteroResidenza = codiceStatoEsteroResidenza;
    }

    public String getDescrizioneCittaEsteraResidenza() {
        return descrizioneCittaEsteraResidenza;
    }

    public void setDescrizioneCittaEsteraResidenza(String descrizioneCittaEsteraResidenza) {
        this.descrizioneCittaEsteraResidenza = descrizioneCittaEsteraResidenza;
    }

    public String getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(String indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public String getCapResidenza() {
        return capResidenza;
    }

    public void setCapResidenza(String capResidenza) {
        this.capResidenza = capResidenza;
    }

    public ExtTtComune getComuneDomicilio() {
        return comuneDomicilio;
    }

    public void setComuneDomicilio(ExtTtComune comuneDomicilio) {
        this.comuneDomicilio = comuneDomicilio;
    }

    public String getIndirizzoDomicilio() {
        return indirizzoDomicilio;
    }

    public void setIndirizzoDomicilio(String indirizzoDomicilio) {
        this.indirizzoDomicilio = indirizzoDomicilio;
    }

    public String getCapDomicilio() {
        return capDomicilio;
    }

    public void setCapDomicilio(String capDomicilio) {
        this.capDomicilio = capDomicilio;
    }

    public String getRecapitoEmail() {
        return recapitoEmail;
    }

    public void setRecapitoEmail(String recapitoEmail) {
        this.recapitoEmail = recapitoEmail;
    }

    public String getRecapitoTelefono() {
        return recapitoTelefono;
    }

    public void setRecapitoTelefono(String recapitoTelefono) {
        this.recapitoTelefono = recapitoTelefono;
    }

    public String getNumeroPermessoDiSoggiorno() {
        return numeroPermessoDiSoggiorno;
    }

    public void setNumeroPermessoDiSoggiorno(String numeroPermessoDiSoggiorno) {
        this.numeroPermessoDiSoggiorno = numeroPermessoDiSoggiorno;
    }

    public String getCodiceAreaTerritoriale() {
        return codiceAreaTerritoriale;
    }

    public void setCodiceAreaTerritoriale(String codiceAreaTerritoriale) {
        this.codiceAreaTerritoriale = codiceAreaTerritoriale;
    }

    public String getDescrizioneAreaTerritoriale() {
        return descrizioneAreaTerritoriale;
    }

    public void setDescrizioneAreaTerritoriale(String descrizioneAreaTerritoriale) {
        this.descrizioneAreaTerritoriale = descrizioneAreaTerritoriale;
    }

    public Long getIdSoggettoAttuatore() {
        return idSoggettoAttuatore;
    }

    public void setIdSoggettoAttuatore(Long idSoggettoAttuatore) {
        this.idSoggettoAttuatore = idSoggettoAttuatore;
    }

    public String getGruppoOperatore() {
        return gruppoOperatore;
    }

    public void setGruppoOperatore(String gruppoOperatore) {
        this.gruppoOperatore = gruppoOperatore;
    }

    public Long getCodOperatore() {
        return codOperatore;
    }

    public void setCodOperatore(Long codOperatore) {
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

    public Long getIdStatoIdeaImpresa() {
        return idStatoIdeaImpresa;
    }

    public void setIdStatoIdeaImpresa(Long idStatoIdeaImpresa) {
        this.idStatoIdeaImpresa = idStatoIdeaImpresa;
    }

    public String getDescrizioneStatoIdeaDiImpresa() {
        return descrizioneStatoIdeaDiImpresa;
    }

    public void setDescrizioneStatoIdeaDiImpresa(String descrizioneStatoIdeaDiImpresa) {
        this.descrizioneStatoIdeaDiImpresa = descrizioneStatoIdeaDiImpresa;
    }

    public String getDesComuneNascita() {
        return desComuneNascita;
    }

    public void setDesComuneNascita(String desComuneNascita) {
        this.desComuneNascita = desComuneNascita;
    }

    public String getDesComuneResidenza() {
        return desComuneResidenza;
    }

    public void setDesComuneResidenza(String desComuneResidenza) {
        this.desComuneResidenza = desComuneResidenza;
    }

    public String getDesComuneDomicilio() {
        return desComuneDomicilio;
    }

    public void setDesComuneDomicilio(String desComuneDomicilio) {
        this.desComuneDomicilio = desComuneDomicilio;
    }

    public String getProvinciaNascita() {
        return provinciaNascita;
    }

    public void setProvinciaNascita(String provinciaNascita) {
        this.provinciaNascita = provinciaNascita;
    }

    public String getProvinciaResidenza() {
        return provinciaResidenza;
    }

    public void setProvinciaResidenza(String provinciaResidenza) {
        this.provinciaResidenza = provinciaResidenza;
    }

    public String getProvinciaDomicilio() {
        return provinciaDomicilio;
    }

    public void setProvinciaDomicilio(String provinciaDomicilio) {
        this.provinciaDomicilio = provinciaDomicilio;
    }

    public String getDesProvinciaNascita() {
        return desProvinciaNascita;
    }

    public void setDesProvinciaNascita(String desProvinciaNascita) {
        this.desProvinciaNascita = desProvinciaNascita;
    }

    public String getDesProvinciaResidenza() {
        return desProvinciaResidenza;
    }

    public void setDesProvinciaResidenza(String desProvinciaResidenza) {
        this.desProvinciaResidenza = desProvinciaResidenza;
    }

    public String getDesProvinciaDomicilio() {
        return desProvinciaDomicilio;
    }

    public void setDesProvinciaDomicilio(String desProvinciaDomicilio) {
        this.desProvinciaDomicilio = desProvinciaDomicilio;
    }

    public String getSvantaggioAbitativo() {
        return svantaggioAbitativo;
    }

    public void setSvantaggioAbitativo(String svantaggioAbitativo) {
        this.svantaggioAbitativo = svantaggioAbitativo;
    }

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

    public String getDescrCittadinanza() {
        return descrCittadinanza;
    }

    public void setDescrCittadinanza(String descrCittadinanza) {
        this.descrCittadinanza = descrCittadinanza;
    }

    public String getCodiceTitoloDiStudio() {
        return codiceTitoloDiStudio;
    }

    public void setCodiceTitoloDiStudio(String codiceTitoloDiStudio) {
        this.codiceTitoloDiStudio = codiceTitoloDiStudio;
    }

    public String getDescrizioneTitoloDiStudio() {
        return descrizioneTitoloDiStudio;
    }

    public void setDescrizioneTitoloDiStudio(String descrizioneTitoloDiStudio) {
        this.descrizioneTitoloDiStudio = descrizioneTitoloDiStudio;
    }

    public String getCodiceFonteConoscenzaMip() {
        return codiceFonteConoscenzaMip;
    }

    public void setCodiceFonteConoscenzaMip(String codiceFonteConoscenzaMip) {
        this.codiceFonteConoscenzaMip = codiceFonteConoscenzaMip;
    }

    public String getDescrizioneFonteConoscenzaMip() {
        return descrizioneFonteConoscenzaMip;
    }

    public void setDescrizioneFonteConoscenzaMip(String descrizioneFonteConoscenzaMip) {
        this.descrizioneFonteConoscenzaMip = descrizioneFonteConoscenzaMip;
    }

    public String getFlgErogazionePrimaOra() {
        return flgErogazionePrimaOra;
    }

    public void setFlgErogazionePrimaOra(String flgErogazionePrimaOra) {
        this.flgErogazionePrimaOra = flgErogazionePrimaOra;
    }

    public String getUtentePrincipale() {
        return utentePrincipale;
    }

    public void setUtentePrincipale(String utentePrincipale) {
        this.utentePrincipale = utentePrincipale;
    }

    public Date getDataIncontroPreaccoglienza() {
        return dataIncontroPreaccoglienza;
    }

    public void setDataIncontroPreaccoglienza(Date dataIncontroPreaccoglienza) {
        this.dataIncontroPreaccoglienza = dataIncontroPreaccoglienza;
    }

    public String getSedeIncontroPreaccoglienza() {
        return sedeIncontroPreaccoglienza;
    }

    public void setSedeIncontroPreaccoglienza(String sedeIncontroPreaccoglienza) {
        this.sedeIncontroPreaccoglienza = sedeIncontroPreaccoglienza;
    }

    public Date getDataAssociazioneSoggettoAttuatore() {
        return dataAssociazioneSoggettoAttuatore;
    }

    public void setDataAssociazioneSoggettoAttuatore(Date dataAssociazioneSoggettoAttuatore) {
        this.dataAssociazioneSoggettoAttuatore = dataAssociazioneSoggettoAttuatore;
    }

    public String getRecapitoTelefono2() {
        return recapitoTelefono2;
    }

    public void setRecapitoTelefono2(String recapitoTelefono2) {
        this.recapitoTelefono2 = recapitoTelefono2;
    }


    public String getCondizioneFamiliare() {
        return condizioneFamiliare;
    }

    public void setCondizioneFamiliare(String condizioneFamiliare) {
        this.condizioneFamiliare = condizioneFamiliare;
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

    public String getDescCittadinanzaAltro() {
        return descCittadinanzaAltro;
    }

    public void setDescCittadinanzaAltro(String descCittadinanzaAltro) {
        this.descCittadinanzaAltro = descCittadinanzaAltro;
    }

    public String getRecapitoEmail2() {
        return recapitoEmail2;
    }

    public void setRecapitoEmail2(String recapitoEmail2) {
        this.recapitoEmail2 = recapitoEmail2;
    }

    public String getDescrSvantaggioAbitativo() {
        return descrSvantaggioAbitativo;
    }

    public void setDescrSvantaggioAbitativo(String descrSvantaggioAbitativo) {
        this.descrSvantaggioAbitativo = descrSvantaggioAbitativo;
    }

    public String getDescrCondizioneFamiliare() {
        return descrCondizioneFamiliare;
    }

    public void setDescrCondizioneFamiliare(String descrCondizioneFamiliare) {
        this.descrCondizioneFamiliare = descrCondizioneFamiliare;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getTitoloDiStudioAltro() {
        return titoloDiStudioAltro;
    }

    public void setTitoloDiStudioAltro(String titoloDiStudioAltro) {
        this.titoloDiStudioAltro = titoloDiStudioAltro;
    }

    public String getCondizioneOccupazionaleAltro() {
        return condizioneOccupazionaleAltro;
    }

    public void setCondizioneOccupazionaleAltro(String condizioneOccupazionaleAltro) {
        this.condizioneOccupazionaleAltro = condizioneOccupazionaleAltro;
    }

    public void setFlgIncontroTelefonico(String flgIncontroTelefonico) {
        this.flgIncontroTelefonico = flgIncontroTelefonico;
    }

    public String getFlgIncontroTelefonico() {
        return flgIncontroTelefonico;
    }
    
    public Date getDataFirmaPattoServizio() {
        return dataFirmaPattoServizio;
    }

    public void setDataFirmaPattoServizio(Date dataFirmaPattoServizio) {
        this.dataFirmaPattoServizio = dataFirmaPattoServizio;
    }

}
