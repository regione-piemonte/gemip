
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
 * <p>Classe Java per Ruolo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Ruolo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceDominio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codiceRuolo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="mnemonico" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ruolo", propOrder = {
    "codiceDominio",
    "codiceRuolo",
    "mnemonico"
})
public class Ruolo {

    @XmlElement(required = true, nillable = true)
    protected String codiceDominio;
    @XmlElement(required = true, nillable = true)
    protected String codiceRuolo;
    @XmlElement(required = true, nillable = true)
    protected String mnemonico;

    /**
     * Recupera il valore della proprietà codiceDominio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceDominio() {
        return codiceDominio;
    }

    /**
     * Imposta il valore della proprietà codiceDominio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceDominio(String value) {
        this.codiceDominio = value;
    }

    /**
     * Recupera il valore della proprietà codiceRuolo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRuolo() {
        return codiceRuolo;
    }

    /**
     * Imposta il valore della proprietà codiceRuolo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRuolo(String value) {
        this.codiceRuolo = value;
    }

    /**
     * Recupera il valore della proprietà mnemonico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMnemonico() {
        return mnemonico;
    }

    /**
     * Imposta il valore della proprietà mnemonico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMnemonico(String value) {
        this.mnemonico = value;
    }

}
