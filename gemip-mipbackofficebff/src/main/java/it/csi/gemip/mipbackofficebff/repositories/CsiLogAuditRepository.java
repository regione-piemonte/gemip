package it.csi.gemip.mipbackofficebff.repositories;

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

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import it.csi.gemip.mipbackofficebff.entities.CsiLogAudit;
import it.csi.gemip.mipbackofficebff.entities.CsiLogAuditId;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@RequestScoped
public class CsiLogAuditRepository implements PanacheRepositoryBase<CsiLogAudit, CsiLogAuditId> {

    @Inject
    EntityManager entityManager;

    @ConfigProperty(name = "encryption.key")
    String secret;

    /**
     * Logica custom per il salvataggio, criptiamo i dati sensibili
     */
    @Override
    public void persist(CsiLogAudit logAudit) {

        // caso particolare per il frontoffice bff, non criptiamo i dati
        if ("GEMIP_rp01_PROD_mipCittadinobff".equals(logAudit.getId().getIdApp())) {
            PanacheRepositoryBase.super.persist(logAudit);
            return;
        }

        String sql = "INSERT INTO csi_log_audit\n" + //
                        "(data_ora, id_app, ip_address, utente, operazione, resource_oper, key_oper, ogg_oper)\n" + //
                        "VALUES(:dataOra, :idApp, :ip, cifra(:utente,:secret), :oper, :res, :key, :ogg)";
        
        CsiLogAuditId id = logAudit.getId();
        entityManager.createNativeQuery(sql)
            .setParameter("dataOra", id.getDataOra())
            .setParameter("idApp", id.getIdApp())
            .setParameter("utente", id.getUtente())
            .setParameter("oper", id.getOperazione())
            .setParameter("ip", logAudit.getIpAddress())
            .setParameter("res", logAudit.getResourceOper())
            .setParameter("key", logAudit.getKeyOper())
            .setParameter("ogg", logAudit.getOggOper())
            .setParameter("secret", this.secret)
            .executeUpdate();
    }

    @Override
    public CsiLogAudit findById(CsiLogAuditId id) {
        throw new UnsupportedOperationException("non bisognerebbe mai leggere i log audit, solo scriverli");
    }
}
