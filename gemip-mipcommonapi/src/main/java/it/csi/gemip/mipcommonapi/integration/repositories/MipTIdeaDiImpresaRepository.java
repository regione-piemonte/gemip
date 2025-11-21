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

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIdeaDiImpresa;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MipTIdeaDiImpresaRepository implements PanacheRepository<MipTIdeaDiImpresa> {

    public static final String DATA_INCONTRO_DA = "dataInseritaDa";
    public static final String DATA_INCONTRO_A = "dataInseritaA";

    public List<MipTIdeaDiImpresa> getIdeaDiImpresaByIdIncontroPreacc(Long idIncontroPreaccoglienza) {
        StringBuilder query = new StringBuilder("SELECT ideaimp FROM MipTIdeaDiImpresa ideaimp ");
        query.append(" JOIN MipRIdeaDiImpresaCittadino ideaimpcitt ON ideaimpcitt.idIdeaDiImpresa.id = ideaimp.id");
        query.append(" JOIN MipTCittadino citt ON citt.idCittadino = ideaimpcitt.idCittadino.idCittadino");
        query.append(" JOIN MipRCittadinoIncontroPreacc cittincprec ON cittincprec.cittadino.idCittadino = citt.idCittadino");
        query.append(" WHERE cittincprec.incontroPreaccoglienza.id = ?1");

        return find(query.toString(), idIncontroPreaccoglienza).list();
    }

    public MipTIdeaDiImpresa getIdeaDiImpresaByIdCittadino(Long idCittadino) {
        StringBuilder query = new StringBuilder("SELECT ideaimp FROM MipTIdeaDiImpresa ideaimp");
        query.append(" JOIN MipRIdeaDiImpresaCittadino ideaimpcitt ON ideaimpcitt.idIdeaDiImpresa.id = ideaimp.id");
        query.append(" WHERE ideaimpcitt.idCittadino.idCittadino = ?1");

        return find(query.toString(), idCittadino).firstResult();

    }

    private String filtroData(Map<String, Object> params) {
        // per evitare sonar per la quantità d'if
        String filtro = "";

        if (params.containsKey("dataInseritaDa") && params.containsKey("dataInseritaA")) {
            return " and ideaimp.dataInserim BETWEEN :dataInseritaDa AND :dataInseritaA";
        }

        if (params.containsKey("dataInseritaDa")) {
            return " and ideaimp.dataInserim >= :dataInseritaDa";
        }

        if (params.containsKey("dataInseritaA")) {
            return " and ideaimp.dataInserim <= :dataInseritaA";
        }

        return filtro;
    }

    public PanacheQuery<MipTIdeaDiImpresa> getIdeaDiImpresaPerParametri(Map<String, Object> params, String orderBy,
            String sortDirection) {
        StringBuilder query = new StringBuilder("SELECT DISTINCT ideaimp FROM MipTIdeaDiImpresa ideaimp");
        query.append(" LEFT JOIN MipDSoggettoAttuatore soggatt ON soggatt.id = ideaimp.idTutor");
        query.append(" LEFT JOIN MipRIdeaDiImpresaCittadino ideaimpcitt ON ideaimpcitt.idIdeaDiImpresa.id = ideaimp.id");
        query.append(" LEFT JOIN MipTCittadino citt ON citt.idCittadino = ideaimpcitt.idCittadino.idCittadino");
        query.append(" LEFT JOIN MipRCittadinoIncontroPreacc cittincprec ON cittincprec.cittadino.idCittadino = citt.idCittadino");
        query.append(" LEFT JOIN MipDStatoIdeaDiImpresa sidi ON sidi.id = ideaimp.statoIdeaDiImpresa.id ");
        if (!params.isEmpty()) {
            query.append(" WHERE 0=0");
        }
        if (params.containsKey("codAreaTerritoriale")) {
            query.append(" AND soggatt.codiceAreaTerritoriale.codiceAreaTerritoriale = :codAreaTerritoriale");
        }

        query.append(filtroData(params));

        if (params.containsKey("ideaDiImpresa")) {
            query.append(" AND CONCAT(LOWER(ideaimp.titolo), LOWER(ideaimp.descrizioneIdeaDiImpresa)) LIKE :ideaDiImpresa ");
        }
        if (params.containsKey("cittadinoNome")) {
            query.append(" AND LOWER(citt.nome) LIKE :cittadinoNome ");
        }
        if (params.containsKey("cittadinoCognome")) {
            query.append(" AND LOWER(citt.cognome) LIKE :cittadinoCognome ");
        }
        if (params.containsKey("idStatoIdea")) {
            query.append(" AND ideaimp.statoIdeaDiImpresa.id = :idStatoIdea");
        }
        if (params.containsKey("idSoggettoAttuatore")) {
            query.append(" AND soggatt.id = :idSoggettoAttuatore");
        }

        if (params.containsKey("statoPresenze")) {
            String[] stati = params.get("statoPresenze").toString().split(",");
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(" AND ( ");
            for (int i = 0; i < stati.length; i++) {
                switch (stati[i]) {
                    case "DISDETTO":
                        queryBuilder.append(
                                "(cittincprec.dAnnullamento IS NOT NULL AND cittincprec.flgCittadinoPresente IS NULL)");
                        break;
                    case "HA PARTECIPATO":
                        queryBuilder.append(
                                "(cittincprec.dAnnullamento IS NULL AND cittincprec.flgCittadinoPresente LIKE 'S')");
                        break;
                    case "ISCRITTO":
                        queryBuilder.append(
                                "(cittincprec.dAnnullamento IS NULL AND cittincprec.flgCittadinoPresente IS NULL)");
                        break;
                    case "NON PRESENTATO":
                        queryBuilder.append(
                                "(cittincprec.dAnnullamento IS NULL AND cittincprec.flgCittadinoPresente LIKE 'N')");
                        break;
                }
                if (i < stati.length - 1) {
                    queryBuilder.append(" OR ");
                }
            }
            queryBuilder.append(" ) ");
            query.append(queryBuilder.toString());
            params.remove("statoPresenze");
        }

        if (params.containsKey(DATA_INCONTRO_A)) {
            Date tmp = (Date) params.get(DATA_INCONTRO_A);
            tmp.setHours(23);
            params.replace(DATA_INCONTRO_A, tmp);
        }
        PanacheQuery<MipTIdeaDiImpresa> panachequery;
        if (sortDirection.equals("desc")) {
            panachequery = find(query.toString(),
                    Sort.by(getOrderBy(orderBy), Sort.Direction.Descending, Sort.NullPrecedence.NULLS_LAST), params);
        } else {
            panachequery = find(query.toString(),
                    Sort.by(getOrderBy(orderBy), Sort.Direction.Ascending, Sort.NullPrecedence.NULLS_LAST), params);
        }

        return panachequery;
    }

    private String getOrderBy(String orderBy) {

        switch (orderBy) {
            case "id":
                orderBy = "ideaimp.id";
                break;
            case "titolo":
                orderBy = "ideaimp.titolo";
                break;
            case "cittadino":
                orderBy = "LOWER(citt.cognome)";
                break;
            case "stato":
                orderBy = "ideaimp.statoIdeaDiImpresa.id";
                break;
            case "associazione":
                orderBy = "ideaimp.dataSceltaTutor ";
                break;
            case "soggetto":
                orderBy = "soggatt.denominazione";
                break;
            case "area":
                orderBy = "cittincprec.codiceAreaTerritorialeSelezionata.codiceAreaTerritoriale";
                break;
            case "data":
                orderBy = "ideaimp.dataInserim";
                break;
            default:
                orderBy = "ideaimp.id";
                break;
        }
        return orderBy;
    }

    public PanacheQuery<MipTIdeaDiImpresa> getReportisticaIdeaDiImpresaPerParametri(Map<String, Object> params) {
        StringBuilder query = new StringBuilder("SELECT ideaimp FROM MipTIdeaDiImpresa ideaimp");
        query.append(" LEFT JOIN MipDSoggettoAttuatore soggatt ON soggatt.id = ideaimp.idTutor");
        query.append(" LEFT JOIN MipRIdeaDiImpresaCittadino ideaimpcitt ON ideaimpcitt.idIdeaDiImpresa.id = ideaimp.id");
        query.append(" LEFT JOIN MipTCittadino citt ON citt.idCittadino = ideaimpcitt.idCittadino.idCittadino");
        query.append(" LEFT JOIN MipRCittadinoIncontroPreacc cittincprec ON cittincprec.cittadino.idCittadino = citt.idCittadino");
        if (!params.isEmpty()) {
            query.append(" WHERE 0=0");
        }
        query.append(filtroData(params));

        if (params.containsKey("idStatoIdea")) {
            query.append(" AND ideaimp.statoIdeaDiImpresa.id = :idStatoIdea");
        }

        if (params.containsKey("idSoggettoAttuatore")) {
            query.append(" AND soggatt.id = :idSoggettoAttuatore");
        }

        PanacheQuery<MipTIdeaDiImpresa> panachequery;
        panachequery = find(query.toString(), params);

        return panachequery;
    }
}
