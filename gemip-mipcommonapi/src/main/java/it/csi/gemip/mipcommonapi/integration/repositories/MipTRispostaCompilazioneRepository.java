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
import it.csi.gemip.mipcommonapi.integration.entities.MipTRispostaCompilazione;
import jakarta.enterprise.context.RequestScoped;

import java.util.HashMap;
import java.util.Map;

@RequestScoped
public class MipTRispostaCompilazioneRepository implements PanacheRepository<MipTRispostaCompilazione> {
    public PanacheQuery<MipTRispostaCompilazione> getDomandeERisposteByFase(Long idFase, Long idCompilazioneQuestionario){
        StringBuilder query = new StringBuilder("SELECT rispostaCompilazione FROM MipTRispostaCompilazione rispostaCompilazione");
        query.append(" JOIN MipDDomanda domanda ON rispostaCompilazione.idDomanda.id = domanda.id");
        query.append(" WHERE domanda.idFaseQuestionario.id = :idFase");
        query.append(" AND rispostaCompilazione.idCompilazioneQuestionario.id = :idCompilazioneQuestionario");
        query.append(" ORDER BY rispostaCompilazione.idDomanda.id");

        Map<String, Object> params = new HashMap<>();
        params.put("idFase", idFase);
        params.put("idCompilazioneQuestionario", idCompilazioneQuestionario);

        PanacheQuery<MipTRispostaCompilazione> panachequery;

        panachequery = find(query.toString(), params);
        return panachequery;
    }

    public PanacheQuery<MipTRispostaCompilazione> findByIdCittadino(Long idCittadino) {
        String query = "SELECT r FROM MipTRispostaCompilazione r WHERE r.idCompilazioneQuestionario.cittadino.idCittadino = :idCittadino";

        Map<String, Object> params = new HashMap<>();
        params.put("idCittadino", idCittadino);

        PanacheQuery<MipTRispostaCompilazione> panachequery;

        panachequery = find(query, params);
        return panachequery;
    }
}
