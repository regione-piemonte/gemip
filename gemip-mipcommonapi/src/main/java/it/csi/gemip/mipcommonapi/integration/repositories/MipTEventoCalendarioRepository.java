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
import io.quarkus.panache.common.Sort;
import it.csi.gemip.mipcommonapi.integration.entities.MipTEventoCalendario;
import jakarta.enterprise.context.RequestScoped;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RequestScoped
public class MipTEventoCalendarioRepository implements PanacheRepository<MipTEventoCalendario> {

    private String filtroData(Map<String,Object> params){
        //per evitare sonar per la quantità d'if
        String filtro = "";

        if (params.containsKey("dataDa") && params.containsKey("dataA")) {
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime((Date) params.get("dataA"));   // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, 23);      // adds one hour
            params.replace("dataA", cal.getTime());
            return " and evca.dataOraInizio BETWEEN :dataDa AND :dataA";
        }

        if (params.containsKey("dataDa")) {
            return " and evca.dataOraInizio >= :dataDa";
        }

        if (params.containsKey("dataA")) {
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) params.get("dataA"));
            cal.add(Calendar.HOUR_OF_DAY, 23);
            params.replace("dataA",cal.getTime());

            return " and evca.dataOraInizio <= :dataA";
        }
        return filtro;
    }

    public PanacheQuery<MipTEventoCalendario> getEventiCalendarioPerParametri(Map<String, Object> params,
            String orderBy, String sortDirection) {
        StringBuilder query = new StringBuilder("SELECT evca FROM MipTEventoCalendario evca");
        query.append(" LEFT JOIN MipTFileIc ics ON ics.idFileIcs = evca.idFileIcs");
        query.append(" LEFT JOIN MipROperatoreSoggAttuatore opsog ON opsog.id = ics.operatoreSoggettoAttuatore.id ");
        query.append(" WHERE ics.flgAbilitato = 'S' ");

        query.append(filtroData(params));

        if (params.containsKey("idSoggettoAttuatore")) {
            query.append(" AND opsog.soggettoAttuatore.id = :idSoggettoAttuatore");
        }

        if (params.containsKey("idOperatore")) {
            query.append(" AND opsog.operatore.id = :idOperatore");
        }

        PanacheQuery<MipTEventoCalendario> panachequery;
        if (sortDirection.equals("desc")) {
            if ("operatoreSoggetto".equals(orderBy)) {
                panachequery = find(query.toString(), Sort.by("opsog.operatore.cognome", Sort.Direction.Descending)
                        .and("opsog.soggettoAttuatore.denominazione", Sort.Direction.Descending), params);
            } else {
                panachequery = find(query.toString(), Sort.by(getOrderBy(orderBy), Sort.Direction.Descending), params);
            }
        } else {
            if ("operatoreSoggetto".equals(orderBy)) {
                panachequery = find(query.toString(), Sort.by("opsog.operatore.cognome", Sort.Direction.Ascending)
                        .and("opsog.soggettoAttuatore.denominazione", Sort.Direction.Ascending), params);
            } else {
                panachequery = find(query.toString(), Sort.by(getOrderBy(orderBy), Sort.Direction.Ascending), params);
            }
        }

        return panachequery;
    }

    private String getOrderBy(String orderBy){
        switch(orderBy){
            case "data":
                orderBy = "evca.dataOraInizio";
                break;
            case "titolo":
                orderBy = "evca.titolo";
                break;
            case "operatoreSoggetto":
                orderBy = "opsog.operatore.cognome";
                break;
            case "luogo":
                orderBy = "evca.luogo";
                break;

            default:
                orderBy = "evca.data_ora_inizio";
                break;
        }

        return orderBy;
    }
}
