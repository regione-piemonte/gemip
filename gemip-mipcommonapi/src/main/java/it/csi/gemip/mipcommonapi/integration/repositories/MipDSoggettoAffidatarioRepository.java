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

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.csi.gemip.mipcommonapi.integration.entities.MipDSoggettoAffidatario;
import jakarta.enterprise.context.RequestScoped;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScoped
public class MipDSoggettoAffidatarioRepository implements PanacheRepository<MipDSoggettoAffidatario> {
    public Date getNow(){
        Instant adesso = (Instant) getEntityManager().createQuery("select now()").getResultList().get(0);
        return Date.from(adesso);
    }

    public List<MipDSoggettoAffidatario> findSoggettiByIdOperatore(Long idOperatore){
        StringBuilder query = new StringBuilder("SELECT soggettoAffidatario FROM MipDOperatore operatore");
        query.append(" LEFT JOIN MipROperatoreSoggAffidatario operatoreAffidatario ON operatore.id = operatoreAffidatario.idOperatore.id");
        query.append(" LEFT JOIN MipDSoggettoAffidatario soggettoAffidatario ON operatoreAffidatario.idSoggettoAffidatario.id = soggettoAffidatario.id");
        query.append(" WHERE operatore.id = :idOperatore");
        Map<String, Object> params = new HashMap<>();
        params.put("idOperatore", idOperatore);
        PanacheQuery<MipDSoggettoAffidatario> panachequery;
        panachequery = find(query.toString(), params);
        return panachequery.list();
    }
}
