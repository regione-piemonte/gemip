
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

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per InvocationNode complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="InvocationNode"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="childs" type="{http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/}ArrayOf_tns1_InvocationNode"/&gt;
 *         &lt;element name="outcome" type="{http://api.coopdiag.csi.it}Outcome"/&gt;
 *         &lt;element name="resource" type="{http://api.coopdiag.csi.it}CalledResource"/&gt;
 *         &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="stopTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InvocationNode", namespace = "http://api.coopdiag.csi.it", propOrder = {
    "childs",
    "outcome",
    "resource",
    "startTime",
    "stopTime"
})
public class InvocationNode {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfTns1InvocationNode childs;
    @XmlElement(required = true, nillable = true)
    protected Outcome outcome;
    @XmlElement(required = true, nillable = true)
    protected CalledResource resource;
    protected long startTime;
    protected long stopTime;

    /**
     * Recupera il valore della proprietà childs.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfTns1InvocationNode }
     *     
     */
    public ArrayOfTns1InvocationNode getChilds() {
        return childs;
    }

    /**
     * Imposta il valore della proprietà childs.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfTns1InvocationNode }
     *     
     */
    public void setChilds(ArrayOfTns1InvocationNode value) {
        this.childs = value;
    }

    /**
     * Recupera il valore della proprietà outcome.
     * 
     * @return
     *     possible object is
     *     {@link Outcome }
     *     
     */
    public Outcome getOutcome() {
        return outcome;
    }

    /**
     * Imposta il valore della proprietà outcome.
     * 
     * @param value
     *     allowed object is
     *     {@link Outcome }
     *     
     */
    public void setOutcome(Outcome value) {
        this.outcome = value;
    }

    /**
     * Recupera il valore della proprietà resource.
     * 
     * @return
     *     possible object is
     *     {@link CalledResource }
     *     
     */
    public CalledResource getResource() {
        return resource;
    }

    /**
     * Imposta il valore della proprietà resource.
     * 
     * @param value
     *     allowed object is
     *     {@link CalledResource }
     *     
     */
    public void setResource(CalledResource value) {
        this.resource = value;
    }

    /**
     * Recupera il valore della proprietà startTime.
     * 
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Imposta il valore della proprietà startTime.
     * 
     */
    public void setStartTime(long value) {
        this.startTime = value;
    }

    /**
     * Recupera il valore della proprietà stopTime.
     * 
     */
    public long getStopTime() {
        return stopTime;
    }

    /**
     * Imposta il valore della proprietà stopTime.
     * 
     */
    public void setStopTime(long value) {
        this.stopTime = value;
    }

}
