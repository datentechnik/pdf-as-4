//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.22 at 04:01:10 PM CEST 
//


package at.gv.egiz.sl.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for DecryptXMLResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DecryptXMLResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="CandidateDocument" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}XMLContentType"/>
 *         &lt;element name="DecryptedBinaryData" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
 *                 &lt;attribute name="EncrElemSelector" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="MimeType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="Encoding" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DecryptXMLResponseType", propOrder = {
    "candidateDocument",
    "decryptedBinaryData"
})
public class DecryptXMLResponseType {

    @XmlElement(name = "CandidateDocument")
    protected XMLContentType candidateDocument;
    @XmlElement(name = "DecryptedBinaryData")
    protected List<DecryptXMLResponseType.DecryptedBinaryData> decryptedBinaryData;

    /**
     * Gets the value of the candidateDocument property.
     * 
     * @return
     *     possible object is
     *     {@link XMLContentType }
     *     
     */
    public XMLContentType getCandidateDocument() {
        return candidateDocument;
    }

    /**
     * Sets the value of the candidateDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLContentType }
     *     
     */
    public void setCandidateDocument(XMLContentType value) {
        this.candidateDocument = value;
    }

    /**
     * Gets the value of the decryptedBinaryData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the decryptedBinaryData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDecryptedBinaryData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DecryptXMLResponseType.DecryptedBinaryData }
     * 
     * 
     */
    public List<DecryptXMLResponseType.DecryptedBinaryData> getDecryptedBinaryData() {
        if (decryptedBinaryData == null) {
            decryptedBinaryData = new ArrayList<DecryptXMLResponseType.DecryptedBinaryData>();
        }
        return this.decryptedBinaryData;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
     *       &lt;attribute name="EncrElemSelector" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="MimeType" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="Encoding" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class DecryptedBinaryData {

        @XmlValue
        protected byte[] value;
        @XmlAttribute(name = "EncrElemSelector", required = true)
        protected String encrElemSelector;
        @XmlAttribute(name = "MimeType")
        protected String mimeType;
        @XmlAttribute(name = "Encoding")
        @XmlSchemaType(name = "anyURI")
        protected String encoding;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     byte[]
         */
        public byte[] getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     byte[]
         */
        public void setValue(byte[] value) {
            this.value = value;
        }

        /**
         * Gets the value of the encrElemSelector property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEncrElemSelector() {
            return encrElemSelector;
        }

        /**
         * Sets the value of the encrElemSelector property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEncrElemSelector(String value) {
            this.encrElemSelector = value;
        }

        /**
         * Gets the value of the mimeType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMimeType() {
            return mimeType;
        }

        /**
         * Sets the value of the mimeType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMimeType(String value) {
            this.mimeType = value;
        }

        /**
         * Gets the value of the encoding property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEncoding() {
            return encoding;
        }

        /**
         * Sets the value of the encoding property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEncoding(String value) {
            this.encoding = value;
        }

    }

}
