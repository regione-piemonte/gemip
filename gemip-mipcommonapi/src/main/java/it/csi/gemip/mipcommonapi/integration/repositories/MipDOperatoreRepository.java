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
import it.csi.gemip.mipcommonapi.integration.entities.MipDOperatore;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;
import java.util.Map;

@RequestScoped
public class MipDOperatoreRepository implements PanacheRepository<MipDOperatore> {


    public List<MipDOperatore> getOperatoriIncontroPreaccoglienza(Long idIncontroPreacc) {
        StringBuilder query = new StringBuilder("SELECT operatore FROM MipDOperatore operatore");
        query.append(" JOIN MipROperatoreIncontroPreaccoglienza opincpre ON opincpre.idOperatoreAffidatario.id = operatore.id");
        query.append(" WHERE opincpre.idIncontroPreaccoglienza.id = ?1");

        return find(query.toString(), idIncontroPreacc).list();
    }

    public List<MipDOperatore> getOperatoriSoggettiAffidatari(Integer idSoggettoAffidatario) {
        StringBuilder query = new StringBuilder("SELECT operatore FROM MipDOperatore operatore");
        query.append(" JOIN MipROperatoreSoggAffidatario opsoggaff ON opsoggaff.idOperatore.id = operatore.id");
        if(idSoggettoAffidatario != null){
            query.append(" WHERE opsoggaff.idSoggettoAffidatario.id = ?1 AND operatore.dataDisabilitazione IS NULL");
            return find(query.toString(),idSoggettoAffidatario).list();
        }
        query.append(" WHERE operatore.dataDisabilitazione IS NULL");
        return find(query.toString()).list();
    }

    public List<MipDOperatore> getOperatoriByIdSoggettoAttuatore(Long idSoggettoAttuatore) {
        StringBuilder query = new StringBuilder("SELECT operatore FROM MipDOperatore operatore");
        query.append(" JOIN MipROperatoreSoggAttuatore opsoggatt ON opsoggatt.operatore.id = operatore.id");
        query.append(" WHERE opsoggatt.soggettoAttuatore.id = ?1 ");
        return find(query.toString(), idSoggettoAttuatore.intValue()).list();
    }

    public List<MipDOperatore> getOperatoriByIdEnte(Long idEnte){
        StringBuilder query = new StringBuilder("SELECT operatore FROM MipDOperatore operatore");
        query.append(" JOIN MipROperatoreEnte opente ON opente.idOperatore.id = operatore.id");
        query.append(" WHERE opente.idEnte.id = ?1 ");
        return find(query.toString(), idEnte.intValue()).list();
    }

    private String getSoggettiFiltro(String soggetto, boolean filtroSoggettoAttuatore){
        if (soggetto == null && !filtroSoggettoAttuatore){
            return "";
        }
        if(filtroSoggettoAttuatore || soggetto.equals("attuatore")) {
            return " RIGHT JOIN MipROperatoreSoggAttuatore opsoggatt ON opsoggatt.operatore.id = operatore.id";
        }
        if(soggetto.equals("affidatario")) {
            return " RIGHT JOIN MipROperatoreSoggAffidatario opsoggaff ON opsoggaff.idOperatore.id = operatore.id";
        }
        if(soggetto.equals("apl") || soggetto.equals("regione")) {
            return " RIGHT JOIN MipROperatoreEnte opente ON opente.idOperatore.id = operatore.id";
        }
        return "";
    }

    public List<MipDOperatore> getOperatorePerParametri(Map<String, Object> params, String abilitato, String soggetto){
        StringBuilder query = new StringBuilder("SELECT operatore FROM MipDOperatore operatore");
        query.append(getSoggettiFiltro(soggetto, params.containsKey("idSoggettoAttuatore")));

        if (!params.isEmpty() || abilitato != null || soggetto != null) {
            query.append(" WHERE 0=0");
        }

        if (params.containsKey("nome")) {
            query.append(" AND LOWER(operatore.nome) LIKE :nome");
        }

        if (params.containsKey("cognome")) {
            query.append(" AND LOWER(operatore.cognome) LIKE :cognome");
        }

        if (params.containsKey("email")) {
            query.append(" AND LOWER(operatore.email) LIKE :email");
        }

        if (params.containsKey("codiceFiscale")) {
            query.append(" AND LOWER(operatore.codFiscaleUtente) LIKE :codiceFiscale");
        }

        if (params.containsKey("idOperatore")) {
            query.append(" AND operatore.id = :idOperatore");
        }

        if (params.containsKey("idSoggettoAttuatore")) {
            query.append(" AND opsoggatt.soggettoAttuatore.id = :idSoggettoAttuatore");
        }

        if (abilitato != null) {
            query.append(" AND operatore.dataDisabilitazione IS ");
            query.append(abilitato.equals("si") ? "" : "NOT ");
            query.append("NULL");
        }

        if (soggetto != null) {
            if (soggetto.equals("apl")) {
                query.append(" AND opente.idEnte.gruppoOperatore = 'A' AND opente.idEnte.codOperatore = 1535");
            }
            if (soggetto.equals("regione")) {
                query.append(" AND opente.idEnte.gruppoOperatore = 'A' AND opente.idEnte.codOperatore = 1");
            }
        }
        return find(query.toString(), params).list();
    }
}
