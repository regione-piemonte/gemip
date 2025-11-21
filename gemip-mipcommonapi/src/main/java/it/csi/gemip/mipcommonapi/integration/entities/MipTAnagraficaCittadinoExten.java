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
@NamedNativeQuery(name = "AnagraficaExten.findById", query = "SELECT \r\n" +
        "id_cittadino, \r\n" + //
        "cod_user_aggiorn, \r\n" + //
        "d_aggiorn, \r\n" + //
        "cod_user_inserim, \r\n" + //
        "d_inserim, \r\n" + //
        "decifra(condizione_occupazionale,:secret) as condizione_occupazionale, \r\n" + //
        "decifra(condizione_occupazionale_altro,:secret) as condizione_occupazionale_altro,\r\n" + //
        "decifra(svantaggio_abitativo,:secret) as svantaggio_abitativo,\r\n" + //
        "decifra(condizione_familiare,:secret) as condizione_familiare\r\n" + //
        "from mip_t_anagrafica_cittadino_exten where id_cittadino  = :idCittadino", resultClass = MipTAnagraficaCittadinoExten.class)
@NamedNativeQuery(name = "AnagraficaExten.insert", query = "INSERT into mip_t_anagrafica_cittadino_exten \r\n" +
        "(id_cittadino,condizione_occupazionale,condizione_occupazionale_altro,svantaggio_abitativo,condizione_familiare,cod_user_inserim,d_inserim,cod_user_aggiorn,d_aggiorn) VALUES( \r\n"
        + //
        ":idCittadino, \r\n" + //
        "cifra(:condizioneOccupazionale,:secret), \r\n" + //
        "cifra(:condizioneOccupazionaleAltro,:secret),\r\n" + //
        "cifra(:svantaggioAbitativo,:secret),\r\n" + //
        "cifra(:condizioneFamiliare,:secret),\r\n" + //
        ":codUser,\r\n" + //
        "current_timestamp," + //
        ":codUser,\r\n" + //
        "current_timestamp)")
@NamedNativeQuery(name = "AnagraficaExten.update", query = "UPDATE mip_t_anagrafica_cittadino_exten SET \r\n" +
        "condizione_occupazionale = cifra(:condizioneOccupazionale,:secret), \r\n" + //
        "condizione_occupazionale_altro = cifra(:condizioneOccupazionaleAltro,:secret),\r\n" + //
        "svantaggio_abitativo = cifra(:svantaggioAbitativo,:secret),\r\n" + //
        "condizione_familiare = cifra(:condizioneFamiliare,:secret),\r\n" + //
        "cod_user_aggiorn = :codUser,\r\n" + //
        "d_aggiorn = current_timestamp \r\n " + //
        "where id_cittadino  = :idCittadino")
@NamedNativeQuery(name = "AnagraficaExten.delete", query = "DELETE FROM mip_t_anagrafica_cittadino_exten ci WHERE ci.id_cittadino = :idCittadino ")
public class MipTAnagraficaCittadinoExten extends PanacheEntityBase {
    @Id
    @Column(name = "id_cittadino", nullable = false, precision = 10)
    private Long id;

    @Column(name = "condizione_occupazionale")
    private String condizioneOccupazionale;

    @Column(name = "condizione_occupazionale_altro")
    private String condizioneOccupazionaleAltro;

    @Column(name = "svantaggio_abitativo")
    private String svantaggioAbitativo;

    @Column(name = "condizione_familiare")
    private String condizioneFamiliare;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCondizioneOccupazionale() {
        return condizioneOccupazionale;
    }

    public void setCondizioneOccupazionale(String condizioneOccupazionale) {
        this.condizioneOccupazionale = condizioneOccupazionale;
    }

    public String getCondizioneOccupazionaleAltro() {
        return condizioneOccupazionaleAltro;
    }

    public void setCondizioneOccupazionaleAltro(String condizioneOccupazionaleAltro) {
        this.condizioneOccupazionaleAltro = condizioneOccupazionaleAltro;
    }

    public String getSvantaggioAbitativo() {
        return svantaggioAbitativo;
    }

    public void setSvantaggioAbitativo(String svantaggioAbitativo) {
        this.svantaggioAbitativo = svantaggioAbitativo;
    }

    public String getCondizioneFamiliare() {
        return condizioneFamiliare;
    }

    public void setCondizioneFamiliare(String condizioneFamiliare) {
        this.condizioneFamiliare = condizioneFamiliare;
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

    @Override
    public String toString() {
        return "MipTAnagraficaCittadinoExten [id=" + id + ", condizioneOccupazionale=" + condizioneOccupazionale
                + ", condizioneOccupazionaleAltro=" + condizioneOccupazionaleAltro + ", svantaggioAbitativo="
                + svantaggioAbitativo + ", condizioneFamiliare=" + condizioneFamiliare + ", codUserInserim="
                + codUserInserim + ", dataInserim=" + dataInserim + ", codUserAggiorn=" + codUserAggiorn
                + ", dataAggiorn=" + dataAggiorn + "]";
    }

}