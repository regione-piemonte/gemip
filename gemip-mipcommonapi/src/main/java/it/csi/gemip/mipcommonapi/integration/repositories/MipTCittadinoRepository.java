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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MipTCittadinoRepository implements PanacheRepository<MipTCittadino> {

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

    public List<MipTCittadino> getCittadinoPerIdeaImpresa(Long idIdeaDiImpresa) {
        StringBuilder query = new StringBuilder("SELECT citta FROM  MipTCittadino citta");
        query.append(
                " JOIN MipRIdeaDiImpresaCittadino ideacitt ON ideacitt.idCittadino.idCittadino = citta.idCittadino");
        query.append(" WHERE ideacitt.idIdeaDiImpresa.id = ?1");

        return find(query.toString(), idIdeaDiImpresa).list();
    }

    public List<MipTCittadino> getCittadinoPerParametri(Map<String, Object> params) {
        StringBuilder query = new StringBuilder("SELECT citta FROM  MipTCittadino citta");
        query.append(" LEFT JOIN MipRCittadinoIncontroPreacc cittincprec ON cittincprec.cittadino.idCittadino = citta.idCittadino");
        query.append(" LEFT JOIN MipTAnagraficaCittadino anacitta ON anacitta.id = citta.idCittadino");

        if (!params.isEmpty()) {
            query.append(" WHERE 0=0");
        }

        if (params.containsKey("codAreaTerritoriale")) {
            query.append(" AND cittincprec.codiceAreaTerritorialeSelezionata.codiceAreaTerritoriale = :codAreaTerritoriale");
        }

        query.append(filtroData(params));

        if (params.containsKey("nome")) {
            query.append(" AND LOWER(citta.nome) LIKE :nome");
        }

        if (params.containsKey("cognome")) {
            query.append(" AND LOWER(citta.cognome) LIKE :cognome");
        }

        if (params.containsKey("email")) {
            query.append(" AND LOWER(anacitta.recapitoEmail) LIKE :email");
        }

        if (params.containsKey("codiceFiscale")) {
            query.append(" AND LOWER(citta.codiceFiscale) LIKE :codiceFiscale");
        }

        return find(query.toString(), params).list();

    }

    // Ne scelgo uno quasi a caso... il primo...
    public MipTCittadino getCittadinoPrincipaleIdeaImpresa(Long idIdeaDiImpresa) {
        StringBuilder query = new StringBuilder("SELECT c FROM  MipTCittadino c");
        query.append(" JOIN MipRIdeaDiImpresaCittadino r ON r.idCittadino.idCittadino = c.idCittadino");
        query.append(" WHERE r.idIdeaDiImpresa.id = ?1");
        query.append(" ORDER BY r.dataAssociazione ");

        List<MipTCittadino> l = find(query.toString(), idIdeaDiImpresa).list();
        return l.isEmpty() ? null : l.get(0);
    }

    public List<MipTCittadino> getCittadiniCleanup(Date adesso) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(adesso);
        calendar.add(Calendar.MONTH, -1);
        Date oneMonthAgo = calendar.getTime();
        return getEntityManager().createQuery(
                "SELECT cittadino " +
                        "FROM MipTCittadino cittadino " +
                        " LEFT JOIN MipRIdeaDiImpresaCittadino mridic ON cittadino.idCittadino = mridic.id.idCittadino "+
                        " LEFT JOIN MipTIdeaDiImpresa mtidi ON mridic.id.idIdeaDiImpresa = mtidi.id " +
                        "WHERE (mridic IS NULL OR  mtidi.statoIdeaDiImpresa.id = 1) " +
                        "AND cittadino.dataInserim < :oneMonthAgo",
                MipTCittadino.class)
                .setParameter("oneMonthAgo", oneMonthAgo)
                .getResultList();
    }
}
