
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

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per MessaggioBacheca complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="MessaggioBacheca"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dataInserimento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="descrizioneAzione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizioneMessaggio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="letto" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="titoloMessaggio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="urgente" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MessaggioBacheca", propOrder = {
    "codice",
    "dataInserimento",
    "dataScadenza",
    "descrizioneAzione",
    "descrizioneMessaggio",
    "letto",
    "titoloMessaggio",
    "urgente"
})
public class MessaggioBacheca {

    @XmlElement(required = true, nillable = true)
    protected String codice;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInserimento;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadenza;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneAzione;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneMessaggio;
    protected boolean letto;
    @XmlElement(required = true, nillable = true)
    protected String titoloMessaggio;
    protected boolean urgente;

    /**
     * Recupera il valore della proprietà codice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietà codice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della proprietà dataInserimento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInserimento() {
        return dataInserimento;
    }

    /**
     * Imposta il valore della proprietà dataInserimento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInserimento(XMLGregorianCalendar value) {
        this.dataInserimento = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Imposta il valore della proprietà dataScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneAzione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneAzione() {
        return descrizioneAzione;
    }

    /**
     * Imposta il valore della proprietà descrizioneAzione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneAzione(String value) {
        this.descrizioneAzione = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneMessaggio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneMessaggio() {
        return descrizioneMessaggio;
    }

    /**
     * Imposta il valore della proprietà descrizioneMessaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneMessaggio(String value) {
        this.descrizioneMessaggio = value;
    }

    /**
     * Recupera il valore della proprietà letto.
     * 
     */
    public boolean isLetto() {
        return letto;
    }

    /**
     * Imposta il valore della proprietà letto.
     * 
     */
    public void setLetto(boolean value) {
        this.letto = value;
    }

    /**
     * Recupera il valore della proprietà titoloMessaggio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitoloMessaggio() {
        return titoloMessaggio;
    }

    /**
     * Imposta il valore della proprietà titoloMessaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitoloMessaggio(String value) {
        this.titoloMessaggio = value;
    }

    /**
     * Recupera il valore della proprietà urgente.
     * 
     */
    public boolean isUrgente() {
        return urgente;
    }

    /**
     * Imposta il valore della proprietà urgente.
     * 
     */
    public void setUrgente(boolean value) {
        this.urgente = value;
    }

}
