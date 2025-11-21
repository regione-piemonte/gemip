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
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.csi.gemip.mipcommonapi.integration.entities.MipRCompilazioneQuestionarioFase;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MipRCompilazioneQuestionarioFaseRepository implements PanacheRepository<MipRCompilazioneQuestionarioFase> {

    public Optional<MipRCompilazioneQuestionarioFase> findQuestionarioByFaseAndCittadino(Integer idFase, MipTCittadino mipTCittadino) {
        return find("idCompilazioneQuestionario.cittadino = ?1 AND id.idFaseQuestionario = ?2",
                mipTCittadino, idFase).singleResultOptional();
    }

    public PanacheQuery<MipRCompilazioneQuestionarioFase> findDateByIdFaseEIdCompilazioneQuestionario(Long idFase, Long idQuestionario){

        Map<String,Object> params = new HashMap<>();

        params.put("idFase",idFase);
        params.put("idQuestionario",idQuestionario);

        StringBuilder query = new StringBuilder("SELECT compilazioneFase FROM MipRCompilazioneQuestionarioFase compilazioneFase");
        query.append(" WHERE compilazioneFase.id.idCompilazioneQuestionario = :idQuestionario");
        query.append(" AND compilazioneFase.id.idFaseQuestionario = :idFase");

        PanacheQuery<MipRCompilazioneQuestionarioFase> panachequery;

        panachequery = find(query.toString(),params);
        return panachequery;
    }

}
