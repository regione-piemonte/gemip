
package it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor;

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

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="findUseCasesForPersonaRoleInApplicationReturn" type="{http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/}UseCase" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "findUseCasesForPersonaRoleInApplicationReturn"
})
@XmlRootElement(name = "findUseCasesForPersonaRoleInApplicationResponse")
public class FindUseCasesForPersonaRoleInApplicationResponse {

    protected List<UseCase> findUseCasesForPersonaRoleInApplicationReturn;

    /**
     * Gets the value of the findUseCasesForPersonaRoleInApplicationReturn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the Jakarta XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the findUseCasesForPersonaRoleInApplicationReturn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFindUseCasesForPersonaRoleInApplicationReturn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UseCase }
     * 
     * 
     */
    public List<UseCase> getFindUseCasesForPersonaRoleInApplicationReturn() {
        if (findUseCasesForPersonaRoleInApplicationReturn == null) {
            findUseCasesForPersonaRoleInApplicationReturn = new ArrayList<UseCase>();
        }
        return this.findUseCasesForPersonaRoleInApplicationReturn;
    }

}
