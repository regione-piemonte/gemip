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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "mip_r_idea_di_impresa_cittadino")
public class MipRIdeaDiImpresaCittadino extends PanacheEntityBase {
    @EmbeddedId
    private MipRIdeaDiImpresaCittadinoId id;

    @MapsId("idIdeaDiImpresa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_idea_di_impresa", nullable = false)
    private MipTIdeaDiImpresa idIdeaDiImpresa;

    @MapsId("idCittadino")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cittadino", nullable = false)
    private MipTCittadino idCittadino;

    @NotNull
    @Column(name = "d_associazione", nullable = false)
    private Date dataAssociazione;

    @Size(max = 16)
    @NotNull
    @Column(name = "cod_user_inserim", nullable = false, length = 16)
    private String codUserInserim;

    @NotNull
    @Column(name = "d_inserim", nullable = false)
    private Date dataInserim;

    @Size(max = 16)
    @NotNull
    @Column(name = "cod_user_aggiorn", nullable = false, length = 16)
    private String codUserAggiorn;

    @NotNull
    @Column(name = "d_aggiorn", nullable = false)
    private Date dataAggiorn;

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

    public MipRIdeaDiImpresaCittadinoId getId() {
        return id;
    }

    public void setId(MipRIdeaDiImpresaCittadinoId id) {
        this.id = id;
    }

    public MipTIdeaDiImpresa getIdIdeaDiImpresa() {
        return idIdeaDiImpresa;
    }

    public void setIdIdeaDiImpresa(MipTIdeaDiImpresa idIdeaDiImpresa) {
        this.idIdeaDiImpresa = idIdeaDiImpresa;
    }

    public MipTCittadino getIdCittadino() {
        return idCittadino;
    }

    public void setIdCittadino(MipTCittadino idCittadino) {
        this.idCittadino = idCittadino;
    }

    public Date getDataAssociazione() {
        return dataAssociazione;
    }

    public void setDataAssociazione(Date dataAssociazione) {
        this.dataAssociazione = dataAssociazione;
    }

}