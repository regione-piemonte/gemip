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

@Entity
@Table(name = "mip_r_operatore_idea_di_impresa")
public class MipROperatoreIdeaDiImpresa {
    @EmbeddedId
    private MipROperatoreIdeaDiImpresaId id;

    @MapsId("idOperatoreAttuatore")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_operatore_attuatore", nullable = false)
    private MipDOperatore idOperatoreAttuatore;

    @MapsId("idIdeaDiImpresa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_idea_di_impresa", nullable = false)
    private MipTIdeaDiImpresa idIdeaDiImpresa;

    public MipROperatoreIdeaDiImpresaId getId() {
        return id;
    }

    public void setId(MipROperatoreIdeaDiImpresaId id) {
        this.id = id;
    }

    public MipDOperatore getIdOperatoreAttuatore() {
        return idOperatoreAttuatore;
    }

    public void setIdOperatoreAttuatore(MipDOperatore idOperatoreAttuatore) {
        this.idOperatoreAttuatore = idOperatoreAttuatore;
    }

    public MipTIdeaDiImpresa getIdIdeaDiImpresa() {
        return idIdeaDiImpresa;
    }

    public void setIdIdeaDiImpresa(MipTIdeaDiImpresa idIdeaDiImpresa) {
        this.idIdeaDiImpresa = idIdeaDiImpresa;
    }

}