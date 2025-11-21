
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
 * <p>Classe Java per MenuDiApplicativo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="MenuDiApplicativo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codApplicativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="elencoFunzionalita" type="{http://flaidoorsrv.interfacecsi.orchflai.flaidoor.csi.it/}ArrayOfFunzionalita"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="servizioEsterno" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MenuDiApplicativo", propOrder = {
    "codApplicativo",
    "descrizione",
    "elencoFunzionalita",
    "id",
    "link",
    "servizioEsterno"
})
public class MenuDiApplicativo {

    @XmlElement(required = true, nillable = true)
    protected String codApplicativo;
    @XmlElement(required = true, nillable = true)
    protected String descrizione;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfFunzionalita elencoFunzionalita;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer id;
    @XmlElement(required = true, nillable = true)
    protected String link;
    protected boolean servizioEsterno;

    /**
     * Recupera il valore della proprietà codApplicativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodApplicativo() {
        return codApplicativo;
    }

    /**
     * Imposta il valore della proprietà codApplicativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodApplicativo(String value) {
        this.codApplicativo = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà elencoFunzionalita.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFunzionalita }
     *     
     */
    public ArrayOfFunzionalita getElencoFunzionalita() {
        return elencoFunzionalita;
    }

    /**
     * Imposta il valore della proprietà elencoFunzionalita.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFunzionalita }
     *     
     */
    public void setElencoFunzionalita(ArrayOfFunzionalita value) {
        this.elencoFunzionalita = value;
    }

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà link.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLink() {
        return link;
    }

    /**
     * Imposta il valore della proprietà link.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLink(String value) {
        this.link = value;
    }

    /**
     * Recupera il valore della proprietà servizioEsterno.
     * 
     */
    public boolean isServizioEsterno() {
        return servizioEsterno;
    }

    /**
     * Imposta il valore della proprietà servizioEsterno.
     * 
     */
    public void setServizioEsterno(boolean value) {
        this.servizioEsterno = value;
    }

}
