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
@Table(name = "mip_d_operatore")
public class MipDOperatore extends PanacheEntityBase {
    @Id
    @Column(name = "id_operatore", nullable = false, precision = 1)
    @SequenceGenerator(
            name = "operatoreSequence",
            sequenceName = "seq_mip_d_operatore",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operatoreSequence")
    private Long id;

    @Size(max = 16)
    @NotNull
    @Column(name = "cod_fiscale_utente", nullable = false, length = 16)
    private String codFiscaleUtente;

    @Size(max = 100)
    @NotNull
    @Column(name = "cognome", nullable = false, length = 100)
    private String cognome;

    @Size(max = 100)
    @NotNull
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Size(max = 100)
    @Column(name = "telefono", length = 100)
    private String telefono;

    @Size(max = 250)
    @Column(name = "email", length = 250)
    private String email;

    @NotNull
    @Column(name = "d_registrazione", nullable = false)
    private Date dataRegistrazione;

    @Column(name = "d_disabilitazione")
    private Date dataDisabilitazione;

    @Column(name = "id_operatore_disabilitazione", nullable = false, precision = 10)
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

    public String getCodFiscaleUtente() {
        return codFiscaleUtente;
    }

    public void setCodFiscaleUtente(String codFiscaleUtente) {
        this.codFiscaleUtente = codFiscaleUtente;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getIdOperatoreDisabilitazione() {
        return idOperatoreDisabilitazione;
    }

    public void setIdOperatoreDisabilitazione(Long idOperatoreDisabilitazione) {
        this.idOperatoreDisabilitazione = idOperatoreDisabilitazione;
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