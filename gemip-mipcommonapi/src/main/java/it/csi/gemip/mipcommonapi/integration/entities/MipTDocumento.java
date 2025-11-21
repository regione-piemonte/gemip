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
@Table(name = "mip_t_documento")
public class MipTDocumento extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "documentoSequence",
            sequenceName = "seq_mip_t_documento",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentoSequence")
    @Column(name = "id_documento", nullable = false, precision = 10)
    private Long id;

    @Size(max = 250)
    @NotNull
    @Column(name = "nome_documento", nullable = false, length = 250)
    private String nomeDocumento;

    @Size(max = 500)
    @NotNull
    @Column(name = "descrizione_documento", nullable = false, length = 500)
    private String descrizioneDocumento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cod_tipo_documento", nullable = false)
    private MipDTipoDocumento codTipoDocumento;

    @NotNull
    @Column(name = "documento", nullable = false)
    private byte[] documentoByte;

    @NotNull
    @Column(name = "id_operatore_inserimento", nullable = false)
    private Long idOperatoreInserimento;

    @Column(name = "id_idea_di_impresa")
    private Long idIdeaDiImpresa;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    public String getDescrizioneDocumento() {
        return descrizioneDocumento;
    }

    public void setDescrizioneDocumento(String descrizioneDocumento) {
        this.descrizioneDocumento = descrizioneDocumento;
    }

    public MipDTipoDocumento getCodTipoDocumento() {
        return codTipoDocumento;
    }

    public void setCodTipoDocumento(MipDTipoDocumento codTipoDocumento) {
        this.codTipoDocumento = codTipoDocumento;
    }

    public byte[] getDocumentoByte() {
        return documentoByte;
    }

    public void setDocumentoByte(byte[] documento) {
        this.documentoByte = documento;
    }

    public Long getIdOperatoreInserimento() {
        return idOperatoreInserimento;
    }

    public void setIdOperatoreInserimento(Long idOperatoreInserimento) {
        this.idOperatoreInserimento = idOperatoreInserimento;
    }

    public Long getIdIdeaDiImpresa() {
        return idIdeaDiImpresa;
    }

    public void setIdIdeaDiImpresa(Long idIdeaDiImpresa) {
        this.idIdeaDiImpresa = idIdeaDiImpresa;
    }

}