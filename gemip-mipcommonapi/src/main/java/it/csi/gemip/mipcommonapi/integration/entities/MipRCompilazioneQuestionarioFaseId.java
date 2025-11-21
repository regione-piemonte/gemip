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
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MipRCompilazioneQuestionarioFaseId implements Serializable {
    private static final long serialVersionUID = -8159162785692873255L;
    @NotNull
    @Column(name = "id_compilazione_questionario", nullable = false, precision = 10)
    private long idCompilazioneQuestionario;

    @NotNull
    @Column(name = "id_fase_questionario", nullable = false, precision = 2)
    private BigDecimal idFaseQuestionario;

    public long getIdCompilazioneQuestionario(long id) {
        return idCompilazioneQuestionario;
    }

    public void setIdCompilazioneQuestionario(long idCompilazioneQuestionario) {
        this.idCompilazioneQuestionario = idCompilazioneQuestionario;
    }

    public BigDecimal getIdFaseQuestionario() {
        return idFaseQuestionario;
    }

    public void setIdFaseQuestionario(BigDecimal idFaseQuestionario) {
        this.idFaseQuestionario = idFaseQuestionario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MipRCompilazioneQuestionarioFaseId entity = (MipRCompilazioneQuestionarioFaseId) o;
        return Objects.equals(this.idCompilazioneQuestionario, entity.idCompilazioneQuestionario) &&
                Objects.equals(this.idFaseQuestionario, entity.idFaseQuestionario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompilazioneQuestionario, idFaseQuestionario);
    }

}