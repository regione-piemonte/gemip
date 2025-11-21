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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import it.csi.gemip.model.ReportIdeaImpresaBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@RequestScoped
public class ReportIdeeImpresaRepository implements PanacheRepository<ReportIdeaImpresaBean> {

    @Inject
    EntityManager em;

    @ConfigProperty(name = "encryption.key")
    String encriptionKey;

    public List<ReportIdeaImpresaBean> getIdeaDiImpresaRicercaExtendeds(Date dataDa, Date dataA,
            Long idSoggettoAttuatore, String idCodAreaTerritoriale, Long idStatoIdea) {
        if (dataA != null) {
            dataA.setHours(23);
        }
        return em.createNamedQuery("ReportIdeaImpresaBean.getAll", ReportIdeaImpresaBean.class)
                .setParameter("dataInseritaDa", dataDa)
                .setParameter("dataInseritaA", dataA)
                .setParameter("idSoggettoAttuatore", idSoggettoAttuatore)
                .setParameter("idCodAreaTerritoriale", idCodAreaTerritoriale)
                .setParameter("idStatoIdea", idStatoIdea)
                .setParameter("encriptionKey", encriptionKey)
                .getResultList();

    }
}
