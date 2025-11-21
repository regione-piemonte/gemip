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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIncontroPreaccoglienza;
import it.csi.gemip.utils.Convert;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MipTIncontroPreaccoglienzaRepository implements PanacheRepository<MipTIncontroPreaccoglienza> {
    private static final String DATA_INCONTRO_DA = "dataIncontroDa";
    private static final String DATA_INCONTRO_A = "dataIncontroA";

    private String filtroData(Map<String, Object> params){
        //per evitare sonar per la quantità d'if
        String filtro = "";

        if(params.containsKey(DATA_INCONTRO_DA) && params.containsKey(DATA_INCONTRO_A)){
            return " and incontro.dataIncontro BETWEEN :dataIncontroDa AND :dataIncontroA";
        }

        if(params.containsKey(DATA_INCONTRO_DA)){
            return " and incontro.dataIncontro >= :dataIncontroDa";
        }

        if(params.containsKey(DATA_INCONTRO_A)){
            return " and incontro.dataIncontro <= :dataIncontroA";
        }



        return filtro;
    }

    public List<MipTIncontroPreaccoglienza> getIncontriPreaccoglienzaByAreaTerritoriale(Map<String, Object> params){
        StringBuilder queryBulder=new StringBuilder("select incontro from MipRIncontroPreaccAreaTerr incontroArea");
        queryBulder.append(" JOIN MipTIncontroPreaccoglienza incontro ON incontro.id = incontroArea.idIncontroPreaccoglienza.id");

        if(!params.isEmpty()){
            queryBulder.append(" where 0=0");
        }
        if(params.containsKey("codAreaTerritoriale")){
            queryBulder.append(" and incontroArea.codAreaTerritoriale.codiceAreaTerritoriale = :codAreaTerritoriale");
        }

        if(params.containsKey("dal")){
            params.replace("dal", DateUtils.addDays((Date) params.get("dal"), 2));
            queryBulder.append(" AND incontro.dataIncontro > :dal");
        }
        if(params.containsKey("al")){
            params.replace("al", DateUtils.addDays((Date) params.get("al"), 1));
            queryBulder.append(" AND incontro.dataIncontro <= :al");
        }

        queryBulder.append(" order by incontro.dataIncontro desc");

        return list(queryBulder.toString(),params);
    }

    public MipTIncontroPreaccoglienza getIncontroPreaccoglienzaById(Long idIncontroPreaccoglienza){
        String query = "SELECT mtip FROM MipTIncontroPreaccoglienza mtip " +
                "WHERE mtip.id = :id";
        Parameters params = Parameters.with("id", idIncontroPreaccoglienza);
        return find(query, params).firstResult();
    }

    public Integer getNumPartecipantiIscritti(Long idIncontroPreacc){
        StringBuilder queryBulder = new StringBuilder(" FROM MipTIncontroPreaccoglienza mtip");
        queryBulder.append(" JOIN MipRCittadinoIncontroPreacc mrcip ON mrcip.incontroPreaccoglienza.id = mtip.id");
        queryBulder.append(" JOIN MipTCittadino mtc ON mrcip.cittadino.idCittadino = mtc.idCittadino");
        queryBulder.append(" JOIN MipRIdeaDiImpresaCittadino mridc ON mtc.idCittadino = mridc.idCittadino.idCittadino");
        queryBulder.append(" JOIN MipTIdeaDiImpresa mtidi ON mridc.idIdeaDiImpresa.id = mtidi.id");
        queryBulder.append(" WHERE mtidi.statoIdeaDiImpresa.id >= 6 AND mtip.id = ?1");
        Long result = find(queryBulder.toString(), idIncontroPreacc).count();
        return result.intValue();
    }

    public List<MipTIncontroPreaccoglienza> findByIdCittadino(Long idCittadino, boolean onlyS) {
        String query = "SELECT mtip FROM MipTIncontroPreaccoglienza mtip " +
            " JOIN MipRCittadinoIncontroPreacc mrcip ON mrcip.incontroPreaccoglienza.id = mtip.id " +
            " WHERE mrcip.cittadino.idCittadino = :idCittadino ";
        if (onlyS) {
            query += " AND mrcip.flgCittadinoPresente = 'S' ";
        }

        Parameters params = Parameters.with("idCittadino", idCittadino);

        return find(query, params).list();
    }

    public PanacheQuery<MipTIncontroPreaccoglienza> getIncontriByParameters(Map<String, Object> params, String orderBy,
            String sortDirection) {
        StringBuilder queryBulder = new StringBuilder("select distinct incontro from MipTIncontroPreaccoglienza incontro");

        queryBulder.append(" JOIN  MipRIncontroPreaccAreaTerr incarter ON incontro.id = incarter.idIncontroPreaccoglienza.id");
        if (params.containsKey("idOperatore")) {
            queryBulder.append(" JOIN MipROperatoreIncontroPreaccoglienza incontrooperat ON incontro.id = incontrooperat.idIncontroPreaccoglienza.id");
        }
        if (params.containsKey("idSoggettoAttuatore")) {
            // TODO mettere il controllo soggetto attuatore trova le idee d'impresa che
            // hanno come tutor il soggetto scelto
            queryBulder.append(" JOIN MipRCittadinoIncontroPreacc mrcip ON mrcip.incontroPreaccoglienza = incontro ");
            queryBulder.append(" JOIN MipRIdeaDiImpresaCittadino mrdic ON mrdic.idCittadino = mrcip.cittadino");
        }
        if (!params.isEmpty()) {
            queryBulder.append(" where 0=0");
        }
        if (params.containsKey("idSoggettoAttuatore")) {
            queryBulder.append(" and mrdic.idIdeaDiImpresa.idTutor = :idSoggettoAttuatore");
        }
        if (params.containsKey("codAreaTerritoriale")) {
            queryBulder.append(" and incarter.codAreaTerritoriale.codiceAreaTerritoriale = :codAreaTerritoriale");
        }

        if (params.containsKey("idOperatore")) {
            queryBulder.append(" and incontrooperat.idOperatoreAffidatario.id = :idOperatore");
        }
        if (params.containsKey("luogo")) {
            queryBulder.append(" and incontro.luogoIncontro.id = :luogo");
        }

        if (params.containsKey(DATA_INCONTRO_DA)) {
            Date tmp = Convert.convertStringToDate(params.get(DATA_INCONTRO_DA).toString());

            params.replace(DATA_INCONTRO_DA, tmp);
        }
        if (params.containsKey(DATA_INCONTRO_A)) {
            Date tmp = Convert.convertStringToDate(params.get(DATA_INCONTRO_A).toString());
            tmp.setHours(23);
            params.replace(DATA_INCONTRO_A, tmp);
        }
        PanacheQuery<MipTIncontroPreaccoglienza> query;
        queryBulder.append(filtroData(params));
        if (sortDirection.equals("desc")) {
            query = find(queryBulder.toString(), Sort.by(getOrderBy(orderBy), Sort.Direction.Descending), params);
        } else {
            query = find(queryBulder.toString(), Sort.by(getOrderBy(orderBy), Sort.Direction.Ascending), params);
        }
        return query;
    }

    private String getOrderBy(String orderBy) {

        switch(orderBy){
            case "id":
                orderBy = "incontro.id";
                break;
            case "sede":
                orderBy = "incontro.luogoIncontro.id";
                break;
            case "data":
                orderBy = "incontro.dataIncontro";
                break;
            default:
                orderBy = "incontro.dataIncontro";
                break;
        }
        return orderBy;
    }

    public List<MipTIncontroPreaccoglienza> findIncontriPreaccoglienzaDiDomani() {
        // Calcola le date di "domani" e "dopodomani" utilizzando Java
        LocalDate domani = LocalDate.now().plusDays(1);
        LocalDate dopodomani = domani.plusDays(1);

        // Converti LocalDate in Date
        Date dataDomani = Date.from(domani.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataDopodomani = Date.from(dopodomani.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Utilizza le date calcolate come parametri per la query
        return list("dataIncontro >= ?1 and dataIncontro < ?2", dataDomani, dataDopodomani);
    }

    public MipTIncontroPreaccoglienza findIncontroPreaccoglienzaCittadino(Long idCittadino) {
        StringBuilder queryBulder = new StringBuilder(" FROM MipTIncontroPreaccoglienza mtip");
        queryBulder.append(" JOIN MipRCittadinoIncontroPreacc mrcip ON mrcip.incontroPreaccoglienza.id = mtip.id");
        queryBulder.append(" WHERE mrcip.dAnnullamento is null AND mrcip.cittadino.id = ?1");
        queryBulder.append(" ORDER BY mrcip.dataInserim DESC");

        List<MipTIncontroPreaccoglienza> list = find(queryBulder.toString(), idCittadino).list();
        return list.isEmpty() ? null : list.get(0);
    }
}
