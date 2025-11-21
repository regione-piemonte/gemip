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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mip_d_risposta")
public class MipDRisposta {
    @Id
    @Column(name = "id_risposta", nullable = false, precision = 10)
    private BigDecimal id;

    @Column(name = "id_domanda", nullable = false, precision = 10)
    private BigDecimal idDomanda;

    @Size(max = 255)
    @Column(name = "testo_risposta")
    private String testoRisposta;

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
    @Size(max = 1)
    @Column(name = "flg_richiesto_dettaglio")
    private String flgRichiestoDettaglio;

    @Size(max = 100)
    @Column(name = "placeholder", length = 100)
    private String placeholder;


    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTestoRisposta() {
        return testoRisposta;
    }

    public void setTestoRisposta(String testoRisposta) {
        this.testoRisposta = testoRisposta;
    }

    public String getCodUserInserim() {
        return codUserInserim;
    }

    public void setCodUserInserim(String codUserInserim) {
        this.codUserInserim = codUserInserim;
    }

    public String getCodUserAggiorn() {
        return codUserAggiorn;
    }

    public void setCodUserAggiorn(String codUserAggiorn) {
        this.codUserAggiorn = codUserAggiorn;
    }

    public BigDecimal getIdDomanda() {
        return idDomanda;
    }

    public void setIdDomanda(BigDecimal idDomanda) {
        this.idDomanda = idDomanda;
    }

    public Date getDataInserim() {
        return dataInserim;
    }

    public void setDataInserim(Date dataInserim) {
        this.dataInserim = dataInserim;
    }

    public Date getDataAggiorn() {
        return dataAggiorn;
    }

    public void setDataAggiorn(Date dataAggiorn) {
        this.dataAggiorn = dataAggiorn;
    }

    public String getFlgRichiestoDettaglio() {
        return flgRichiestoDettaglio;
    }

    public void setFlgRichiestoDettaglio(String flgRichiestoDettaglio) {
        this.flgRichiestoDettaglio = flgRichiestoDettaglio;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }
}