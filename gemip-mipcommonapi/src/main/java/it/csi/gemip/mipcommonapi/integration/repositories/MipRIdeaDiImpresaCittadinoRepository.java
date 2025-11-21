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

import java.time.Instant;
import java.util.Date;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import it.csi.gemip.mipcommonapi.integration.entities.MipRIdeaDiImpresaCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class MipRIdeaDiImpresaCittadinoRepository implements PanacheRepository<MipRIdeaDiImpresaCittadino> {
    public Date getNow() {
        Instant adesso = (Instant) getEntityManager().createQuery("select now()").getResultList().get(0);
        return Date.from(adesso);
    }

    public int isFase2(MipTCittadino mipTCittadino) {
        return (int) count("id.idCittadino = :idCittadino and idIdeaDiImpresa.dataFirmaPattoServizio is not null",
                Parameters.with("idCittadino", mipTCittadino.getIdCittadino()));
    }

    public int isFase3(MipTCittadino mipTCittadino) {
        return (int) count("id.idCittadino = :idCittadino and idIdeaDiImpresa.dataValidBusinessPlan is not null ",
                Parameters.with("idCittadino", mipTCittadino.getIdCittadino()));
    }

    public List<MipRIdeaDiImpresaCittadino> getIdeeDiImpresaCittadiniByIdIncontroPreacc(Long idIncontroPreacc) {
        StringBuilder query = new StringBuilder("SELECT ideacitt FROM  MipRIdeaDiImpresaCittadino ideacitt");
        query.append(" LEFT JOIN MipTCittadino citta ON citta.idCittadino = ideacitt.idCittadino.idCittadino");
        query.append( " LEFT JOIN MipRCittadinoIncontroPreacc cittaincpre ON cittaincpre.cittadino.idCittadino = citta.idCittadino");
        query.append(" WHERE cittaincpre.incontroPreaccoglienza.id = ?1 AND ideacitt.idIdeaDiImpresa.statoIdeaDiImpresa.id > 2");

        return find(query.toString(), idIncontroPreacc).list();
    }

    public List<MipRIdeaDiImpresaCittadino> findByIdImpresa(Long idIdeaImpresa) {
        String query = "SELECT ideacitt FROM  MipRIdeaDiImpresaCittadino ideacitt WHERE idIdeaDiImpresa.id = :idIdeaImpresa";
        Parameters parameters = Parameters.with("idIdeaImpresa", idIdeaImpresa);
        return find(query, parameters).list();
    }

}
