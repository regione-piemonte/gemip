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


import java.util.HashMap;
import java.util.Map;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.csi.gemip.mipcommonapi.integration.entities.ExtGmopDAreaTerritoriale;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ExtGmopDAreaTerritorialeRepository implements PanacheRepository<ExtGmopDAreaTerritoriale> {

    public PanacheQuery<ExtGmopDAreaTerritoriale> getAreaTerritorialeByIdCittadino(Long idCittadino){
        StringBuilder query =new StringBuilder("SELECT areaTerritoriale from ExtGmopDAreaTerritoriale areaTerritoriale");
        query.append(" JOIN MipDSoggettoAttuatore soggettoAtt ON soggettoAtt.codiceAreaTerritoriale.codiceAreaTerritoriale = areaTerritoriale.codiceAreaTerritoriale");
        query.append(" JOIN MipTIdeaDiImpresa ideaImp ON soggettoAtt.id = ideaImp.idTutor");
        query.append(" JOIN MipRIdeaDiImpresaCittadino idea ON ideaImp.id = idea.idIdeaDiImpresa.id");
        query.append(" JOIN MipTCittadino cittadino ON idea.idCittadino.idCittadino = cittadino.idCittadino");
        query.append(" WHERE cittadino.idCittadino = :idCittadino");

        Map<String, Object> params = new HashMap<>();
        params.put("idCittadino", idCittadino);

        PanacheQuery<ExtGmopDAreaTerritoriale> panachequery;

        panachequery = find(query.toString(), params);
        return panachequery;
    }

    public PanacheQuery<ExtGmopDAreaTerritoriale> getAreaTerritorialeByIdCittadinoFase1(Long idCittadino){
        StringBuilder query =new StringBuilder("SELECT areaTerritoriale FROM ExtGmopDAreaTerritoriale areaTerritoriale");
        query.append(" JOIN MipRCittadinoIncontroPreacc incontroCittadino ON areaTerritoriale.codiceAreaTerritoriale = incontroCittadino.codiceAreaTerritorialeSelezionata.codiceAreaTerritoriale");
        query.append(" WHERE incontroCittadino.cittadino.idCittadino = :idCittadino");

        Map<String, Object> params = new HashMap<>();
        params.put("idCittadino", idCittadino);

        PanacheQuery<ExtGmopDAreaTerritoriale> panachequery;

        panachequery = find(query.toString(), params);
        return panachequery;
    }

}
