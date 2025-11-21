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
@Table(name = "mip_d_domanda")
public class MipDDomanda {
    @Id
    @Column(name = "id_domanda", nullable = false, precision = 10)
    private BigDecimal id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_questionario", nullable = false)
    private MipDQuestionario questionario;

    @Size(max = 255)
    @NotNull
    @Column(name = "testo_domanda", nullable = false)
    private String testoDomanda;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_fase_questionario", nullable = false)
    private MipDFaseQuestionario idFaseQuestionario;

    @Size(max = 1)
    @NotNull
    @Column(name = "tipo_domanda", nullable = true, length = 1)
    private String tipoDomanda;

    @Size(max = 100)
    @NotNull
    @Column(name = "placeholder", nullable = true, length = 100)
    private String placeholder;

    @ManyToOne()
    @JoinColumn(name = "id_risposta_condizionale", nullable = true)
    private MipDRisposta rispostaCondizionale;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public MipDQuestionario getQuestionario() {
        return questionario;
    }

    public void setQuestionario(MipDQuestionario idQuestionario) {
        this.questionario = idQuestionario;
    }

    public String getTestoDomanda() {
        return testoDomanda;
    }

    public void setTestoDomanda(String testoDomanda) {
        this.testoDomanda = testoDomanda;
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

    public MipDFaseQuestionario getIdFaseQuestionario() {
        return idFaseQuestionario;
    }

    public void setIdFaseQuestionario(MipDFaseQuestionario idFaseQuestionario) {
        this.idFaseQuestionario = idFaseQuestionario;
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

    public String getTipoDomanda() {
        return tipoDomanda;
    }

    public void setTipoDomanda(String tipoDomanda) {
        this.tipoDomanda = tipoDomanda;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public MipDRisposta getRispostaCondizionale() {
        return rispostaCondizionale;
    }

    public void setRispostaCondizionale(MipDRisposta rispostaCondizionale) {
        this.rispostaCondizionale = rispostaCondizionale;
    }
}