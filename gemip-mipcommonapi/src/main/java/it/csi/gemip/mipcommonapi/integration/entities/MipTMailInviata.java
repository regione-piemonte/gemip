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

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mip_t_mail_inviata")
public class MipTMailInviata extends PanacheEntityBase {

    @Id
    @Column(name = "id_mail_inviata", nullable = false)
    @SequenceGenerator(
            name = "mailInviataSequence",
            sequenceName = "seq_mip_t_mail_inviata",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mailInviataSequence")
    private Long id;

    @NotNull
    @Column(name = "d_invio", nullable = false)
    private Date dInvio;

    @NotNull
    @Column(name = "id_cittadino", nullable = false)
    private Long idCittadino;


    @Column(name = "cod_testo_email", nullable = false)
    private String codTestoEmail;

    @NotNull
    @Column(name = "cod_user_inserim", nullable = false)
    private String codUserInserim;

    @NotNull
    @Column(name = "d_inserim", nullable = false)
    private Date dataInserim;
    @NotNull
    @Column(name = "cod_user_aggiorn", nullable = false)
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

    public Date getdInvio() {
        return dInvio;
    }

    public void setdInvio(Date dInvio) {
        this.dInvio = dInvio;
    }

    public Long getIdCittadino() {
        return idCittadino;
    }

    public void setIdCittadino(Long idCittadino) {
        this.idCittadino = idCittadino;
    }

    public String getCodTestoEmail() {
        return codTestoEmail;
    }

    public void setCodTestoEmail(String codTestoEmail) {
        this.codTestoEmail = codTestoEmail;
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