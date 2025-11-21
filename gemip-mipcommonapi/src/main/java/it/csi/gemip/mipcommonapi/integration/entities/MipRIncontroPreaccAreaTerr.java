package it.csi.gemip.mipcommonapi.integration.entities;

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
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "mip_r_incontro_preacc_area_terr")
public class MipRIncontroPreaccAreaTerr extends PanacheEntityBase {
    @EmbeddedId
    private MipRIncontroPreaccAreaTerrId id;

    @MapsId("idIncontroPreaccoglienza")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_incontro_preaccoglienza", nullable = false)
    private MipTIncontroPreaccoglienza idIncontroPreaccoglienza;

    @MapsId("codAreaTerritoriale")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cod_area_territoriale", nullable = false)
    private ExtGmopDAreaTerritoriale codAreaTerritoriale;

    @Column(name = "cod_user_inserim")
    private String codUserInserim = null;

    @Column(name = "d_inserim")
    private Date dataInserim = null;

    @Column(name = "cod_user_aggiorn")
    private String codUserAggiorn = null;

    @Column(name = "d_aggiorn")
    private Date dataAggiorn = null;

    public MipRIncontroPreaccAreaTerrId getId() {
        return id;
    }

    public void setId(MipRIncontroPreaccAreaTerrId id) {
        this.id = id;
    }

    public MipTIncontroPreaccoglienza getIdIncontroPreaccoglienza() {
        return idIncontroPreaccoglienza;
    }

    public void setIdIncontroPreaccoglienza(MipTIncontroPreaccoglienza idIncontroPreaccoglienza) {
        this.idIncontroPreaccoglienza = idIncontroPreaccoglienza;
    }

    public ExtGmopDAreaTerritoriale getCodAreaTerritoriale() {
        return codAreaTerritoriale;
    }

    public void setCodAreaTerritoriale(ExtGmopDAreaTerritoriale codAreaTerritoriale) {
        this.codAreaTerritoriale = codAreaTerritoriale;
    }

    public String getCodUserInserim() {
        return codUserInserim;
    }

    public void setCodUserInserim(String codUserInserim) {
        this.codUserInserim = codUserInserim;
    }

    public Date getDataInserim() {
        return dataInserim;
    }

    public void setDataInserim(Date dataInserim) {
        this.dataInserim = dataInserim;
    }

    public String getCodUserAggiorn() {
        return codUserAggiorn;
    }

    public void setCodUserAggiorn(String codUserAggiorn) {
        this.codUserAggiorn = codUserAggiorn;
    }

    public Date getDataAggiorn() {
        return dataAggiorn;
    }

    public void setDataAggiorn(Date dataAggiorn) {
        this.dataAggiorn = dataAggiorn;
    }
}