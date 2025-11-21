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
import it.csi.gemip.mipcommonapi.integration.entities.MipRCittadinoIncontroPreacc;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIncontroPreaccoglienza;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class MipRCittadinoIncontroPreaccRepository implements PanacheRepository<MipRCittadinoIncontroPreacc> {

    public List<MipRCittadinoIncontroPreacc> getIncontroPreaccoglienzaPerIdeaImpresa(Long idIdeaImpresa) {
        StringBuilder query = new StringBuilder("SELECT cittaincpre FROM MipRCittadinoIncontroPreacc cittaincpre");
        query.append(" JOIN MipTCittadino citta ON citta.idCittadino = cittaincpre.cittadino.idCittadino");
        query.append(" JOIN MipRIdeaDiImpresaCittadino ideacitt ON ideacitt.idCittadino.idCittadino = citta.idCittadino");
        query.append(" WHERE ideacitt.idIdeaDiImpresa.id = ?1");
        return find(query.toString(), idIdeaImpresa).list();
    }

    public MipRCittadinoIncontroPreacc getUltimoIncontroPreaccoglienzaPerIdCittadino(Long idCittadino) {
        StringBuilder query = new StringBuilder("SELECT cittaincpre FROM MipRCittadinoIncontroPreacc cittaincpre");
        query.append(" WHERE cittaincpre.cittadino.idCittadino = ?1 ");
        query.append(" ORDER BY cittaincpre.cittadino.idCittadino desc");
        query.append(" LIMIT 1 ");
        return find(query.toString(), idCittadino).singleResult();
    }

    public int getCountCittadinoFlgCittadinoPresente(MipTCittadino mipTCittadino) {
        return (int) count("cittadino = ?1 AND flgCittadinoPresente = 'S' ", mipTCittadino);
    }

    public List<MipTCittadino> findCittadinoByIncontroPreaccoglienzaIscritto(
            MipTIncontroPreaccoglienza incontroDiPreaccoglienza) {
        StringBuilder query = new StringBuilder("SELECT DISTINCT cittincprec FROM  MipTCittadino citta");
        query.append(" JOIN MipRCittadinoIncontroPreacc cittincprec ON cittincprec.cittadino.idCittadino = citta.idCittadino");
        query.append(" JOIN MipRIdeaDiImpresaCittadino ideacitta ON ideacitta.idCittadino.idCittadino = citta.idCittadino");
        query.append(" JOIN MipTIdeaDiImpresa idea ON ideacitta.idIdeaDiImpresa.id = idea.id");
        query.append(" WHERE cittincprec.incontroPreaccoglienza.id = ?1 AND idea.statoIdeaDiImpresa.id > 2");
        return find(query.toString(), incontroDiPreaccoglienza.getId()).stream()
                .map(MipRCittadinoIncontroPreacc::getCittadino).toList();
    }
}
