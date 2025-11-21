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

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Embeddable
public class CsiLogAuditId implements Serializable {

    @NotNull
    @Column(name = "data_ora", nullable = false)
    private Date dataOra;

    @NotNull
    @Size(max = 100)
    @Column(name = "id_app", nullable = false, length = 100)
    private String idApp;

    @NotNull
    @Size(max = 100)
    @Column(name = "utente", nullable = false, length = 100)
    // NOT ENCRYPTED
    private String utente;

    @NotNull
    @Size(max = 50)
    @Column(name = "operazione", nullable = false, length = 50)
    private String operazione;

    public Date getDataOra() {
        return dataOra;
    }

    public void setDataOra(Date dataOra) {
        this.dataOra = dataOra;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }
    
    public String getOperazione() {
        return operazione;
    }

    public void setOperazione(String operazione) {
        this.operazione = operazione;
    }

    @Override
    public String toString() {
        return "CsiLogAuditId [dataOra=" + dataOra + ", idApp=" + idApp + ", utente=" + utente
                + ", operazione=" + operazione + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataOra == null) ? 0 : dataOra.hashCode());
        result = prime * result + ((idApp == null) ? 0 : idApp.hashCode());
        result = prime * result + ((utente == null) ? 0 : utente.hashCode());
        result = prime * result + ((operazione == null) ? 0 : operazione.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CsiLogAuditId other = (CsiLogAuditId) obj;
        if (dataOra == null) {
            if (other.dataOra != null)
                return false;
        } else if (!dataOra.equals(other.dataOra))
            return false;
        if (idApp == null) {
            if (other.idApp != null)
                return false;
        } else if (!idApp.equals(other.idApp))
            return false;
        if (utente == null) {
            if (other.utente != null)
                return false;
        } else if (!utente.equals(other.utente))
            return false;
        if (operazione == null) {
            if (other.operazione != null)
                return false;
        } else if (!operazione.equals(other.operazione))
            return false;
        return true;
    }

    

    

}