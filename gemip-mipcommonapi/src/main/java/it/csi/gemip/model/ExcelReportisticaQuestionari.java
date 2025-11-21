package it.csi.gemip.model;

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

public class ExcelReportisticaQuestionari {

    private String idQuestionario;

    private Date dataInserim;

    private String areaTerritoriale;

    private String soggettoAttuatore;

    private List<String> risposte;

    public String getIdQuestionario() {
        return idQuestionario;
    }

    public void setIdQuestionario(String idQuestionario) {
        this.idQuestionario = idQuestionario;
    }

    public Date getDataInserim() {
        return dataInserim;
    }

    public void setDataInserim(Date dataInserim) {
        this.dataInserim = dataInserim;
    }

    public String getAreaTerritoriale() {
        return areaTerritoriale;
    }

    public void setAreaTerritoriale(String areaTerritoriale) {
        this.areaTerritoriale = areaTerritoriale;
    }

    public String getSoggettoAttuatore() {
        return soggettoAttuatore;
    }

    public void setSoggettoAttuatore(String soggettoAttuatore) {
        this.soggettoAttuatore = soggettoAttuatore;
    }

    public List<String> getRisposte() {
        return risposte;
    }

    public void setRisposte(List<String> domande) {
        this.risposte = domande;
    }
}
