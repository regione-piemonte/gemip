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
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "mip_t_file_ics")
public class MipTFileIc extends PanacheEntityBase {
    @Id
    @SequenceGenerator(
            name = "fileIcsSequence",
            sequenceName = "seq_mip_t_file_ics",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fileIcsSequence")
    @Column(name = "id_file_ics", nullable = false, precision = 10)
    private Long idFileIcs;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_operatore_sogg_attuatore", nullable = false)
    private MipROperatoreSoggAttuatore operatoreSoggettoAttuatore;

    @Size(max = 250)
    @NotNull
    @Column(name = "nome_file", nullable = false, length = 250)
    private String nomeFile;

    @NotNull
    @Column(name = "file_ics", nullable = false, length = Integer.MAX_VALUE)
    private byte[] fileIcsByte;

    @Size(max = 250)
    @Column(name = "descr_file", length = 250)
    private String descrizioneFile;

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

    public Long getIdFileIcs() {
        return idFileIcs;
    }

    public void setIdFileIcs(Long id) {
        this.idFileIcs = id;
    }

    public MipROperatoreSoggAttuatore getOperatoreSoggettoAttuatore() {
        return operatoreSoggettoAttuatore;
    }

    public void setOperatoreSoggettoAttuatore(MipROperatoreSoggAttuatore operatoreSoggettoAttuatore) {
        this.operatoreSoggettoAttuatore = operatoreSoggettoAttuatore;
    }

    public String getNomeFile() {
        return nomeFile;
    }

    public void setNomeFile(String nomeFile) {
        this.nomeFile = nomeFile;
    }

    public byte[]  getFileIcsByte() {
        return fileIcsByte;
    }

    public void setFileIcsByte(byte[]  fileIcs) {
        this.fileIcsByte = fileIcs;
    }

    public String getDescrizioneFile() {
        return descrizioneFile;
    }

    public void setDescrizioneFile(String descrFile) {
        this.descrizioneFile = descrFile;
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