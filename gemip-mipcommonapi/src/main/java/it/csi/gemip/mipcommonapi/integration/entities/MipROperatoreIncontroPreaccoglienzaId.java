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
import java.util.Objects;

@Embeddable
public class MipROperatoreIncontroPreaccoglienzaId implements Serializable {
    private static final long serialVersionUID = -3409020835819434004L;
    @NotNull
    @Column(name = "id_incontro_preaccoglienza", nullable = false, precision = 10)
    private Long idIncontroPreaccoglienza;

    @NotNull
    @Column(name = "id_operatore_affidatario", nullable = false, precision = 10)
    private Long idOperatoreAffidatario;

    public Long getIdIncontroPreaccoglienza() {
        return idIncontroPreaccoglienza;
    }

    public void setIdIncontroPreaccoglienza(Long idIncontroPreaccoglienza) {
        this.idIncontroPreaccoglienza = idIncontroPreaccoglienza;
    }

    public Long getIdOperatoreAffidatario() {
        return idOperatoreAffidatario;
    }

    public void setIdOperatoreAffidatario(Long idOperatoreAffidatario) {
        this.idOperatoreAffidatario = idOperatoreAffidatario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MipROperatoreIncontroPreaccoglienzaId entity = (MipROperatoreIncontroPreaccoglienzaId) o;
        return Objects.equals(this.idOperatoreAffidatario, entity.idOperatoreAffidatario) &&
                Objects.equals(this.idIncontroPreaccoglienza, entity.idIncontroPreaccoglienza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOperatoreAffidatario, idIncontroPreaccoglienza);
    }

}