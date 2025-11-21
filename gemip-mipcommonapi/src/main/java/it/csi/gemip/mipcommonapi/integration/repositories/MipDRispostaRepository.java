package it.csi.gemip.mipcommonapi.integration.repositories;

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

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.csi.gemip.mipcommonapi.integration.entities.MipDRisposta;
import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.util.List;

@RequestScoped
public class MipDRispostaRepository implements PanacheRepository<MipDRisposta> {
    public List<MipDRisposta> findByDomanda(BigDecimal id){
        return find("FROM MipDRisposta d WHERE d.idDomanda = ?1 ORDER BY d.id", id).stream().toList();
    }
}
