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
@Table(name = "mip_r_operatore_sogg_attuatore")
public class MipROperatoreSoggAttuatore extends PanacheEntityBase {
    @Id
    @Column(name = "id_operatore_sogg_attuatore", nullable = false, precision = 10)
    //seq_mip_r_operatore_sogg_affidatario
    @SequenceGenerator(
            name = "operatoreSoggSequence",
            sequenceName = "seq_mip_r_operatore_sogg_attuatore",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operatoreSoggSequence")
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_operatore", nullable = false)
    private MipDOperatore operatore;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_soggetto_attuatore", nullable = false)
    private MipDSoggettoAttuatore soggettoAttuatore;
    @NotNull
    @Column(name = "d_registrazione", nullable = false)
    private Date dataRegistrazione;

    @Column(name = "d_disabilitazione")
    private Date dataDisabilitazione;

    @Column(name = "id_operatore_disabilitazione")
    private Long idOperatoreDisabilitazione;
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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MipDOperatore getOperatore() {
        return operatore;
    }

    public void setOperatore(MipDOperatore operatore) {
        this.operatore = operatore;
    }

    public MipDSoggettoAttuatore getSoggettoAttuatore() {
        return soggettoAttuatore;
    }

    public void setSoggettoAttuatore(MipDSoggettoAttuatore soggettoAttuatore) {
        this.soggettoAttuatore = soggettoAttuatore;
    }

    public Date getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    public Date getDataDisabilitazione() {
        return dataDisabilitazione;
    }

    public void setDataDisabilitazione(Date dataDisabilitazione) {
        this.dataDisabilitazione = dataDisabilitazione;
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

    public Long getIdOperatoreDisabilitazione() {
        return idOperatoreDisabilitazione;
    }

    public void setIdOperatoreDisabilitazione(Long idOperatoreDisabilitazione) {
        this.idOperatoreDisabilitazione = idOperatoreDisabilitazione;
    }
}