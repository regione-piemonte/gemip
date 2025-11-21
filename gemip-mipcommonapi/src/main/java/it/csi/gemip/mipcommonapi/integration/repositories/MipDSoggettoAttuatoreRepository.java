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
import it.csi.gemip.mipcommonapi.integration.entities.MipDSoggettoAttuatore;
import jakarta.enterprise.context.RequestScoped;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestScoped
public class MipDSoggettoAttuatoreRepository implements PanacheRepository<MipDSoggettoAttuatore> {

    public Date getNow() {
        Instant adesso = (Instant) getEntityManager().createQuery("select now()").getResultList().get(0);
        return Date.from(adesso);
    }

    public List<MipDSoggettoAttuatore> getSoggettoAttuatoreByIdOperatori(Long idOperatore){
        StringBuilder query = new StringBuilder("select sogg from MipDSoggettoAttuatore sogg");
        query.append(" JOIN MipROperatoreSoggAttuatore soggatt on sogg.id = soggatt.soggettoAttuatore.id");
        query.append(" where soggatt.operatore.id = ?1");

        return find(query.toString(), idOperatore).list();
    }


    public PanacheQuery<MipDSoggettoAttuatore> getTutorByIdCittadino(Long idCittadino){
        StringBuilder query =new StringBuilder("SELECT soggettoAtt from MipDSoggettoAttuatore soggettoAtt");
        query.append(" JOIN MipTIdeaDiImpresa ideaImp ON soggettoAtt.id = ideaImp.idTutor");
        query.append(" JOIN MipRIdeaDiImpresaCittadino idea ON ideaImp.id = idea.idIdeaDiImpresa.id");
        query.append(" JOIN MipTCittadino cittadino ON idea.idCittadino.idCittadino = cittadino.idCittadino");
        query.append(" WHERE cittadino.idCittadino = :idCittadino");

        Map<String, Object> params = new HashMap<>();
        params.put("idCittadino", idCittadino);

        PanacheQuery<MipDSoggettoAttuatore> panachequery;

        panachequery = find(query.toString(), params);
        return panachequery;
    }    

    public List<MipDSoggettoAttuatore> getTutorPerAreaTerritoriale(String codiceAreaTerritoriale){

        StringBuilder queryBulder = new StringBuilder("SELECT satt FROM MipDSoggettoAttuatore satt ");
        if (codiceAreaTerritoriale != null && !codiceAreaTerritoriale.isBlank()) {
            queryBulder.append(" WHERE satt.codiceAreaTerritoriale.codiceAreaTerritoriale = ?1");
            return find(queryBulder.toString(), codiceAreaTerritoriale).list();
        }

        return find(queryBulder.toString()).list();
    }

    public List<MipDSoggettoAttuatore> getTutorAbilitatiPerAreaTerritoriale(String codiceAreaTerritoriale){

        StringBuilder queryBulder = new StringBuilder("SELECT satt FROM MipDSoggettoAttuatore satt ");
        if (codiceAreaTerritoriale != null && !codiceAreaTerritoriale.isBlank()) {
            queryBulder.append(" WHERE satt.codiceAreaTerritoriale.codiceAreaTerritoriale = ?1" +
                    "AND (CURRENT_TIMESTAMP < satt.dataDisabilitazione OR satt.dataDisabilitazione IS NULL)");
            return find(queryBulder.toString(), codiceAreaTerritoriale).list();
        }

        return find(queryBulder.toString()).list();
    }
}
