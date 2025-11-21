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
import org.hibernate.annotations.ColumnTransformer;


import java.util.Date;

@Entity
@Table(name = "mip_t_cittadino")
public class MipTCittadino extends PanacheEntityBase {
    @Id
    @Column(name = "id_cittadino", nullable = false)
    @SequenceGenerator(
            name = "cittadinoSequence",
            sequenceName = "seq_mip_t_cittadino",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cittadinoSequence")
    private Long idCittadino;

    @Size(max = 16)
    @NotNull
    @Column(name = "codice_fiscale", nullable = false, length = 16)

    private String codiceFiscale;

    @Size(max = 100)
    @NotNull
    @Column(name = "cognome", nullable = false, length = 100)
    private String cognome;

    @Size(max = 100)
    @NotNull
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

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

    @Override
    public String toString(){
        return "MipTCittadino{" +
                "idCittadino=" + idCittadino +
                ", codiceFiscale='" + codiceFiscale + '\'' +
                ", cognome='" + cognome + '\'' +
                ", nome='" + nome + '\'' +
                ", codUserInserim='" + codUserInserim + '\'' +
                ", dataInserim=" + dataInserim +
                ", codUserAggiorn='" + codUserAggiorn + '\'' +
                ", dataAggiorn=" + dataAggiorn +
                '}';
    }
}