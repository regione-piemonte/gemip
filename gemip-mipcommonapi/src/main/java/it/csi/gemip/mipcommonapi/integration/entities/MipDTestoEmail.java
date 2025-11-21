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

@Entity
@Table(name = "mip_d_testo_email")
public class MipDTestoEmail{
    @Id
    @Column(name = "cod_testo_email", length = 20)
    private String codTestoEmail;
    @NotNull
    @Column(name = "mittente", length = 100)
    private String mittente;
    @NotNull
    @Column(name = "oggetto", length = 100)
    private String oggetto;
    @NotNull
    @Column(name = "corpo", length = 4000)
    private String corpo;
    @NotNull
    @Column(name = "flg_html", length = 1)
    private String flgHtml;


    public String getCodTestoEmail() {
        return codTestoEmail;
    }

    public void setCodTestoEmail(String codTestoEmail) {
        this.codTestoEmail = codTestoEmail;
    }

    public String getMittente() {
        return mittente;
    }

    public void setMittente(String mittente) {
        this.mittente = mittente;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getFlgHtml() {
        return flgHtml;
    }

    public void setFlgHtml(String flgHtml) {
        this.flgHtml = flgHtml;
    }
}
