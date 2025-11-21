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
import java.util.Date;

@Entity
@Table(name = "mip_t_incontro_preaccoglienza")
public class MipTIncontroPreaccoglienza extends PanacheEntityBase {
    @Id
    @Column(name = "id_incontro_preaccoglienza", nullable = false, precision = 10)
    @SequenceGenerator(
            name = "incontroPreaccSequence",
            sequenceName = "seq_mip_t_incontro_preaccoglienza",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incontroPreaccSequence")
    private Long id;

    @Size(max = 500)
    @NotNull
    @Column(name = "denominazione", nullable = false, length = 500)
    private String denominazione;

    @Size(max = 1)
    @Column(name = "flg_incontro_erogato_da_remoto", length = 1)
    private String flgIncontroErogatoDaRemoto;

    @Size(max = 1)
    @Column(name = "flg_incontro_telefonico", length = 1)
    private String flgIncontroTelefonico;

    @Column(name = "link_incontro_remoto", length = 1)
    private String linkIncontroRemoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_luogo_incontro")
    private MipDLuogoIncontro luogoIncontro;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_operatore_creazione", nullable = false)
    private MipDOperatore operatoreCreazione;

    @NotNull
    @Column(name = "d_incontro", nullable = false)
    private Date dataIncontro;

    @NotNull
    @Column(name = "num_max_partecipanti", nullable = false, precision = 3)
    private Integer numMaxPartecipanti;

    @Size(max = 4000)
    @Column(name = "note", length = 4000)
    private String note;

    @Column(name = "cod_user_inserim")
    private String codUserInserim = null;

    @Column(name = "d_inserim")
    private Date dataInserim = null;

    @Column(name = "cod_user_aggiorn")
    private String codUserAggiorn = null;

    @Column(name = "d_aggiorn")
    private Date dataAggiorn = null;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominazione() {
        return denominazione;
    }

    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getFlgIncontroErogatoDaRemoto() {
        return flgIncontroErogatoDaRemoto;
    }

    public void setFlgIncontroErogatoDaRemoto(String flgIncontroErogatoDaRemoto) {
        this.flgIncontroErogatoDaRemoto = flgIncontroErogatoDaRemoto;
    }

    public String getFlgIncontroTelefonico(){ return this.flgIncontroTelefonico; }

    public void setFlgIncontroTelefonico(String flgIncontroTelefonico){
        this.flgIncontroTelefonico = flgIncontroTelefonico;
    }

    public MipDLuogoIncontro getLuogoIncontro() {
        return luogoIncontro;
    }

    public void setLuogoIncontro(MipDLuogoIncontro luogoIncontro) {
        this.luogoIncontro = luogoIncontro;
    }

    public MipDOperatore getOperatoreCreazione() {
        return operatoreCreazione;
    }

    public void setOperatoreCreazione(MipDOperatore operatoreCreazione) {
        this.operatoreCreazione = operatoreCreazione;
    }

    public Date getDataIncontro() {
        return dataIncontro;
    }

    public void setDataIncontro(Date dataIncontro) {
        this.dataIncontro = dataIncontro;
    }

    public Integer getNumMaxPartecipanti() {
        return numMaxPartecipanti;
    }

    public void setNumMaxPartecipanti(Integer numMaxPartecipanti) {
        this.numMaxPartecipanti = numMaxPartecipanti;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLinkIncontroRemoto() {
        return linkIncontroRemoto;
    }

    public void setLinkIncontroRemoto(String linkIncontroRemoto) {
        this.linkIncontroRemoto = linkIncontroRemoto;
    }
}