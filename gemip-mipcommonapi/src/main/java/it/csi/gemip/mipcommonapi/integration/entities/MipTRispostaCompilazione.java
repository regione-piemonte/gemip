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

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "mip_t_risposta_compilazione")
public class MipTRispostaCompilazione {
    @Id
    @SequenceGenerator(
            name = "rispostaCompilazioneSequence",
            sequenceName = "gemip.seq_mip_t_risposta_compilazione",
            allocationSize = 1
            )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rispostaCompilazioneSequence")
    @Column(name = "id_risposta_compilazione", nullable = false, precision = 10)
    private BigDecimal id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_compilazione_questionario", nullable = false)
    private MipTCompilazioneQuestionario idCompilazioneQuestionario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_domanda", nullable = false)
    private MipDDomanda idDomanda;


    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_risposta", nullable = false)
    private MipDRisposta idRisposta;
    @Column(name = "risposta_libera", nullable = false, length = 2000 )
    private String rispostaLibera;
    @NotNull
    @Size(max = 16)
    @Column(name = "cod_user_inserim", nullable = false, length = 16)
    private String codUserInserim;

    @NotNull
    @Column(name = "d_inserim", nullable = false)
    private Date dataInserim;
    @NotNull
    @Size(max = 16)
    @Column(name = "cod_user_aggiorn", nullable = false, length = 16)
    private String codUserAggiorn;
    @NotNull
    @Column(name = "d_aggiorn", nullable = false)
    private Date dataAggiorn;



    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public MipTCompilazioneQuestionario getIdCompilazioneQuestionario() {
        return idCompilazioneQuestionario;
    }

    public void setIdCompilazioneQuestionario(MipTCompilazioneQuestionario idCompilazioneQuestionario) {
        this.idCompilazioneQuestionario = idCompilazioneQuestionario;
    }

    public MipDDomanda getIdDomanda() {
        return idDomanda;
    }

    public void setIdDomanda(MipDDomanda idDomanda) {
        this.idDomanda = idDomanda;
    }

    public MipDRisposta getIdRisposta() {
        return idRisposta;
    }

    public void setIdRisposta(MipDRisposta idRisposta) {
        this.idRisposta = idRisposta;
    }

    public String getRispostaLibera() {
        return rispostaLibera;
    }

    public void setRispostaLibera(String rispostaLibera) {
        this.rispostaLibera = rispostaLibera;
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