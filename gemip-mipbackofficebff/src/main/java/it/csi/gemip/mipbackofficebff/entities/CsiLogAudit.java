package it.csi.gemip.mipbackofficebff.entities;

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

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "csi_log_audit")
public class CsiLogAudit extends PanacheEntityBase {

    @EmbeddedId
    private CsiLogAuditId id;
    
    @Size(max = 40)
    @Column(name = "ip_address", length = 40)
    private String ipAddress;

    @Size(max = 500)
    @Column(name = "ogg_oper", length = 500)
    private String oggOper;

    @Size(max = 200)
    @Column(name = "resource_oper", length = 200)
    private String resourceOper;

    @Size(max = 500)
    @Column(name = "key_oper", length = 500)
    private String keyOper;

    public CsiLogAuditId getId() {
        return id;
    }

    public void setId(CsiLogAuditId id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOggOper() {
        return oggOper;
    }

    public void setOggOper(String oggOper) {
        this.oggOper = oggOper;
    }

    public String getResourceOper() {
        return resourceOper;
    }

    public void setResourceOper(String resourceOper) {
        this.resourceOper = resourceOper;
    }

    public String getKeyOper() {
        return keyOper;
    }

    public void setKeyOper(String keyOper) {
        this.keyOper = keyOper;
    }

    @Override
    public String toString() {
        return "CsiLogAudit{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", oggOper='" + oggOper + '\'' +
                ", keyOper='" + keyOper + '\'' +
                '}';
    }


}