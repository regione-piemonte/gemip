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

import java.util.Date;

@Entity
@Table(name = "mip_r_cittadino_incontro_preacc")
public class MipRCittadinoIncontroPreacc extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "cittadinoIncontroPreacc",
            sequenceName = "seq_mip_r_cittadino_incontro_preacc",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cittadinoIncontroPreacc")
    @Column(name = "id_cittadino_incontro_preacc", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cittadino", nullable = false)
    private MipTCittadino cittadino;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_incontro_preaccoglienza", nullable = false)
    private MipTIncontroPreaccoglienza incontroPreaccoglienza;

    @Size(max = 1)
    @Column(name = "flg_cittadino_presente", length = 1)
    private String flgCittadinoPresente;

    @Column(name = "d_annullamento")
    private Date dAnnullamento;

    @Size(max = 4000)
    @Column(name = "note", length = 4000)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cod_area_territoriale_selezionata", nullable = false)
    private ExtGmopDAreaTerritoriale codiceAreaTerritorialeSelezionata;

    @Column(name = "cod_user_inserim")
    private String codUserInserim = null;

    @Column(name = "d_inserim")
    private Date dataInserim = null;

    @Column(name = "cod_user_aggiorn")
    private String codUserAggiorn = null;

    @Column(name = "d_aggiorn")
    private Date dataAggiorn = null;


    public ExtGmopDAreaTerritoriale getCodiceAreaTerritorialeSelezionata() {
        return codiceAreaTerritorialeSelezionata;
    }

    public void setCodiceAreaTerritorialeSelezionata(ExtGmopDAreaTerritoriale codiceAreaTerritorialeSelezionata) {
        this.codiceAreaTerritorialeSelezionata = codiceAreaTerritorialeSelezionata;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MipTCittadino getCittadino() {
        return cittadino;
    }

    public void setCittadino(MipTCittadino cittadino) {
        this.cittadino = cittadino;
    }

    public MipTIncontroPreaccoglienza getIncontroPreaccoglienza() {
        return incontroPreaccoglienza;
    }

    public void setIncontroPreaccoglienza(MipTIncontroPreaccoglienza incontroPreaccoglienza) {
        this.incontroPreaccoglienza = incontroPreaccoglienza;
    }

    public String getFlgCittadinoPresente() {
        return flgCittadinoPresente;
    }

    public void setFlgCittadinoPresente(String flgCittadinoPresente) {
        this.flgCittadinoPresente = flgCittadinoPresente;
    }

    public Date getDAnnullamento() {
        return dAnnullamento;
    }

    public void setDAnnullamento(Date dAnnullamento) {
        this.dAnnullamento = dAnnullamento;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
}