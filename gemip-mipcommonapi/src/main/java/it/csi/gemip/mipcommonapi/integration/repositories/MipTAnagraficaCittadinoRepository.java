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
import java.util.List;
import java.util.Map;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import it.csi.gemip.mipcommonapi.integration.entities.MipTAnagraficaCittadino;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MipTAnagraficaCittadinoRepository implements PanacheRepository<MipTAnagraficaCittadino> {
    private String filtroData(Map<String, Object> params) {
        // per evitare sonar per la quantità d'if
        String filtro = "";
        if (params.containsKey("dataInseritoDa") && params.containsKey("dataInseritoA")) {
            return " and citta.dataInserim BETWEEN :dataInseritoDa AND :dataInseritoA";
        }
        if (params.containsKey("dataInseritoDa")) {
            return " and citta.dataInserim >= :dataInseritoDa";
        }
        if (params.containsKey("dataInseritoA")) {
            return " and citta.dataInserim <= :dataInseritoA";
        }
        return filtro;
    }

    public PanacheQuery<MipTAnagraficaCittadino> getCittadinoPerParametri(Map<String, Object> params, String orderBy,
            String sortDirection) {
        StringBuilder query = new StringBuilder("SELECT anacitta FROM  MipTCittadino citta");
        query.append(
                " LEFT JOIN MipRIdeaDiImpresaCittadino ideaimpcitt ON ideaimpcitt.idCittadino.idCittadino = citta.idCittadino");
        query.append(" LEFT JOIN MipTIdeaDiImpresa ideaimp ON ideaimpcitt.idIdeaDiImpresa.id = ideaimp.id");
        query.append(" LEFT JOIN MipDSoggettoAttuatore soggatt ON soggatt.id = ideaimp.idTutor");
        query.append(
                " LEFT JOIN MipRCittadinoIncontroPreacc cittincprec ON cittincprec.cittadino.idCittadino = citta.idCittadino");
        query.append(" RIGHT JOIN MipTAnagraficaCittadino anacitta ON anacitta.id = citta.idCittadino");

        if (!params.isEmpty()) {
            query.append(" WHERE 0=0");
        }

        if (params.containsKey("codAreaTerritoriale")) {
            query.append(
                    " AND cittincprec.codiceAreaTerritorialeSelezionata.codiceAreaTerritoriale = :codAreaTerritoriale");
        }

        if (params.containsKey("idSoggettoAttuatore")) {
            query.append(" AND soggatt.id = :idSoggettoAttuatore");
        }

        query.append(filtroData(params));

        if (params.containsKey("nome")) {
            query.append(" AND LOWER(citta.nome) LIKE LOWER(:nome)");
        }

        if (params.containsKey("cognome")) {
            query.append(" AND LOWER(citta.cognome) LIKE LOWER(:cognome)");
        }

        if (params.containsKey("email")) {
            query.append(" AND LOWER(anacitta.recapitoEmail) LIKE LOWER(:email)");
        }

        if (params.containsKey("codiceFiscale")) {
            query.append(" AND LOWER(citta.codiceFiscale) LIKE LOWER(:codiceFiscale)");
        }

        PanacheQuery<MipTAnagraficaCittadino> panachequery;
        if (sortDirection.equals("desc")) {
            panachequery = find(query.toString(), Sort.by(getOrderBy(orderBy), Sort.Direction.Descending), params);
        } else {
            panachequery = find(query.toString(), Sort.by(getOrderBy(orderBy), Sort.Direction.Ascending), params);
        }
        return panachequery;

    }

    private String getOrderBy(String orderBy) {
        switch (orderBy) {
            case "codiceFiscale":
                orderBy = "citta.codiceFiscale";
                break;
            case "cognome":
                orderBy = "citta.cognome";
                break;
            case "nome":
                orderBy = "citta.nome";
                break;
            case "email":
                orderBy = "anacitta.recapitoEmail";
                break;
            case "telefono":
                orderBy = "anacitta.recapitoTelefono";
                break;
            default:
                orderBy = "citta.codiceFiscale";
                break;
        }

        return orderBy;
    }

    public List<MipTAnagraficaCittadino> getCittadinoPerIdIncontro(HashMap<String, Object> map) {
        StringBuilder query = new StringBuilder("SELECT anacitta FROM  MipTCittadino citta");
        query.append(" JOIN MipTAnagraficaCittadino anacitta ON anacitta.id = citta.idCittadino");
        query.append(
                " JOIN MipRCittadinoIncontroPreacc cittincprec ON cittincprec.cittadino.idCittadino = citta.idCittadino");
        query.append(
                " JOIN MipRIdeaDiImpresaCittadino ideacitta ON ideacitta.idCittadino.idCittadino = citta.idCittadino");
        query.append(" JOIN MipTIdeaDiImpresa idea ON ideacitta.idIdeaDiImpresa.id = idea.id");
        query.append(
                " WHERE cittincprec.incontroPreaccoglienza.id = :idIncontroPreaccoglienza AND idea.statoIdeaDiImpresa.id > 2");

        if (map.containsKey("idSoggettoAttuatore"))
            query.append(" AND idea.idTutor = :idSoggettoAttuatore");

        return find(query.toString(), map).list();
    }

}
