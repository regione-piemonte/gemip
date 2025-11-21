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
import java.util.Map;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCompilazioneQuestionario;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MipTCompilazioneQuestionarioRepository implements PanacheRepository<MipTCompilazioneQuestionario> {

    public Optional<MipTCompilazioneQuestionario> findByCittadino(MipTCittadino cittadino) {
        return find("cittadino", cittadino).singleResultOptional();
    }

    public PanacheQuery<MipTCompilazioneQuestionario> getDomandeRisposte(Map<String, Object> params){
        StringBuilder query = new StringBuilder("SELECT compilazione FROM MipTRispostaCompilazione rispostaCompilazione");
        query.append(" LEFT JOIN MipTCompilazioneQuestionario compilazione ON compilazione.id = rispostaCompilazione.idCompilazioneQuestionario.id");
        query.append(" LEFT JOIN MipRCompilazioneQuestionarioFase compilazioneFase ON compilazione.id = compilazioneFase.id.idCompilazioneQuestionario");
        if (((Long) params.get("idFase")) >= 2) {
            query.append(" LEFT JOIN MipRIdeaDiImpresaCittadino ideaCittadino ON ideaCittadino.idCittadino = compilazione.cittadino");
            query.append(" LEFT JOIN MipTIdeaDiImpresa ideaImp ON ideaImp.id = ideaCittadino.id.idIdeaDiImpresa");
            query.append(" LEFT JOIN MipDSoggettoAttuatore soggettoAttuatore ON soggettoAttuatore.id = ideaImp.idTutor");
            query.append(" LEFT JOIN ExtGmopDAreaTerritoriale area ON soggettoAttuatore.codiceAreaTerritoriale.codiceAreaTerritoriale = area.codiceAreaTerritoriale");
        }

        if (params.get("dataA") != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime((Date) params.get("dataA"));
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            Date dataFine = cal.getTime();
            params.put("dataA", dataFine);
        }

        if (!params.isEmpty()) {
            query.append(" WHERE 0=0");
        }
        if (params.containsKey("idFase")) {
            query.append(" AND compilazioneFase.id.idFaseQuestionario = :idFase");
        }
        if (params.containsKey("dataDa") && !params.containsKey("dataA")) {
            query.append(" AND compilazione.dataInserim >= :dataDa");
        }
        if (params.containsKey("dataA") && !params.containsKey("dataDa")) {
            query.append(" AND compilazione.dataInserim <= :dataA");
        }
        if (params.containsKey("dataA") && params.containsKey("dataDa")) {
            query.append(" AND compilazione.dataInserim >= :dataDa AND compilazione.dataInserim <= :dataA");
        }

        if (params.containsKey("idSoggettoAttuatore") && ((Long) params.get("idFase")) >= 2) {
            query.append(" AND soggettoAttuatore.id = " + params.get("idSoggettoAttuatore"));
            params.remove("idSoggettoAttuatore");
        } else {
            params.remove("idSoggettoAttuatore");
        }

        if (params.containsKey("idCodAreaTerritoriale") && ((Long) params.get("idFase")) >= 2) {
            query.append(" AND soggettoAttuatore.codiceAreaTerritoriale.codiceAreaTerritoriale = :idCodAreaTerritoriale");
        } else {
            params.remove("idCodAreaTerritoriale");
        }

        PanacheQuery<MipTCompilazioneQuestionario> panachequery;

        panachequery = find(query.toString(), params);
        return panachequery;
    }

}
