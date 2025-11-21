package it.csi.gemip.mipbackofficebff.entities;

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
@Table(name = "mip_t_evento_calendario")
public class MipTEventoCalendario extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "eventoCalendarioSequence",
            sequenceName = "seq_mip_t_evento_calendario",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventoCalendarioSequence")
    @Column(name = "id_evento_calendario", nullable = false, precision = 10)
    private Long idEventoCalendario;

    @NotNull
    @Column(name = "id_file_ics", nullable = false, precision = 10)
    private Long idFileIcs;

    @NotNull
    @Column(name = "data_ora_inizio", nullable = false)
    private Date dataOraInizio;

    @NotNull
    @Column(name = "data_ora_fine", nullable = false)
    private Date dataOraFine;

    @Size(max = 500)
    @Column(name = "descr_evento", length = 500)
    private String descrizioneEvento;

    @Size(max = 100)
    @NotNull
    @Column(name = "dato_uid", length = 200)
    private String datoUid;
    @Size(max = 250)
    @Column(name = "luogo", nullable = false, length = 250)
    private String luogo;

    @Size(max = 250)
    @Column(name = "titolo", length = 250)
    private String titolo;
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

    public Long getIdEventoCalendario() {
        return idEventoCalendario;
    }

    public void setIdEventoCalendario(Long idEventoCalendario) {
        this.idEventoCalendario = idEventoCalendario;
    }

    public Long getIdFileIcs() {
        return idFileIcs;
    }

    public void setIdFileIcs(Long idFileIcs) {
        this.idFileIcs = idFileIcs;
    }

    public Date getDataOraInizio() {
        return dataOraInizio;
    }

    public void setDataOraInizio(Date dataOraInizio) {
        this.dataOraInizio = dataOraInizio;
    }

    public Date getDataOraFine() {
        return dataOraFine;
    }

    public void setDataOraFine(Date dataOraFine) {
        this.dataOraFine = dataOraFine;
    }

    public String getDescrizioneEvento() {
        return descrizioneEvento;
    }

    public void setDescrizioneEvento(String descrEvento) {
        this.descrizioneEvento = descrEvento;
    }

    public String getDatoUid() {
        return datoUid;
    }

    public void setDatoUid(String datoUid) {
        this.datoUid = datoUid;
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

    public String getLuogo() {
        return luogo;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}