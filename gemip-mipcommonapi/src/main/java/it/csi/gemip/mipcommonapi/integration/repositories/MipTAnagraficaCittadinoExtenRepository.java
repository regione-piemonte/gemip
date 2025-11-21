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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.csi.gemip.mipcommonapi.integration.entities.MipTAnagraficaCittadinoExten;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.NoResultException;

@RequestScoped
public class MipTAnagraficaCittadinoExtenRepository implements PanacheRepository<MipTAnagraficaCittadinoExten> {

    @ConfigProperty(name = "encryption.key")
    String secret;

    @Override
    public MipTAnagraficaCittadinoExten findById(Long id) {
        try {
            return getEntityManager()
                    .createNamedQuery("AnagraficaExten.findById", MipTAnagraficaCittadinoExten.class)
                    .setParameter("idCittadino", id)
                    .setParameter("secret", secret)
                    .getSingleResult();
        } catch (NoResultException exc) {
            return null;
        }
    }

    public int delete(Long id) {
        int i = 0;
        try {
            i = getEntityManager().createNamedQuery(
                    "AnagraficaExten.delete")
                    .setParameter("idCittadino", id)
                    .executeUpdate();
            return i;
        } catch (NoResultException exc) {
            return 0;
        }
    }

    @Override
    public void persist(MipTAnagraficaCittadinoExten entity) {

        // PRIMA PROVO L'UPDATE

        int rows = getEntityManager()
                .createNamedQuery("AnagraficaExten.update")
                .setParameter("idCittadino", entity.getId())
                .setParameter("condizioneOccupazionale", entity.getCondizioneOccupazionale())
                .setParameter("condizioneOccupazionaleAltro", entity.getCondizioneOccupazionaleAltro())
                .setParameter("condizioneFamiliare", entity.getCondizioneFamiliare())
                .setParameter("svantaggioAbitativo", entity.getSvantaggioAbitativo())
                .setParameter("codUser", entity.getCodUserAggiorn())
                .setParameter("secret", secret)
                .executeUpdate();
        if (rows == 0) {

            // E POI L'INSERT
            getEntityManager()
                    .createNamedQuery("AnagraficaExten.insert")
                    .setParameter("idCittadino", entity.getId())
                    .setParameter("condizioneOccupazionale", entity.getCondizioneOccupazionale())
                    .setParameter("condizioneOccupazionaleAltro", entity.getCondizioneOccupazionaleAltro())
                    .setParameter("condizioneFamiliare", entity.getCondizioneFamiliare())
                    .setParameter("svantaggioAbitativo", entity.getSvantaggioAbitativo())
                    .setParameter("codUser", entity.getCodUserInserim())
                    .setParameter("secret", secret)
                    .executeUpdate();
        }
    }
}
