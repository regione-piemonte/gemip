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
public class MipRIdeaDiImpresaCittadinoId implements Serializable {
    private static final long serialVersionUID = 6211770003292361325L;
    @NotNull
    @Column(name = "id_idea_di_impresa", nullable = false, precision = 10)
    private Long idIdeaDiImpresa;

    @NotNull
    @Column(name = "id_cittadino", nullable = false, precision = 10)
    private Long idCittadino;

    public Long getIdIdeaDiImpresa() {
        return idIdeaDiImpresa;
    }

    public void setIdIdeaDiImpresa(Long idIdeaDiImpresa) {
        this.idIdeaDiImpresa = idIdeaDiImpresa;
    }

    public Long getIdCittadino() {
        return idCittadino;
    }

    public void setIdCittadino(Long idCittadino) {
        this.idCittadino = idCittadino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MipRIdeaDiImpresaCittadinoId entity = (MipRIdeaDiImpresaCittadinoId) o;
        return Objects.equals(this.idIdeaDiImpresa, entity.idIdeaDiImpresa) &&
                Objects.equals(this.idCittadino, entity.idCittadino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIdeaDiImpresa, idCittadino);
    }

}