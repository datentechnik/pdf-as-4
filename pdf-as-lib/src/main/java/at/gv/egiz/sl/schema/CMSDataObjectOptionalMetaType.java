/*******************************************************************************
 * <copyright> Copyright 2014 by E-Government Innovation Center EGIZ, Graz, Austria </copyright>
 * PDF-AS has been contracted by the E-Government Innovation Center EGIZ, a
 * joint initiative of the Federal Chancellery Austria and Graz University of
 * Technology.
 * 
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * http://www.osor.eu/eupl/
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * 
 * This product combines work with different licenses. See the "NOTICE" text
 * file for details on the various modules and licenses.
 * The "NOTICE" text file is part of the distribution. Any derivative works
 * that you distribute must include a readable copy of the "NOTICE" text file.
 ******************************************************************************/
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.22 at 04:01:10 PM CEST 
//


package at.gv.egiz.sl.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CMSDataObjectOptionalMetaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CMSDataObjectOptionalMetaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MetaInfo" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}MetaInfoType" minOccurs="0"/>
 *         &lt;element name="Content" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}Base64OptRefContentType"/>
 *         &lt;element name="ExcludedByteRange" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}ExcludedByteRangeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CMSDataObjectOptionalMetaType", propOrder = {
    "metaInfo",
    "content",
    "excludedByteRange"
})
@XmlSeeAlso({
    CMSDataObjectRequiredMetaType.class
})
public class CMSDataObjectOptionalMetaType {

    @XmlElement(name = "MetaInfo")
    protected MetaInfoType metaInfo;
    @XmlElement(name = "Content", required = true)
    protected Base64OptRefContentType content;
    @XmlElement(name = "ExcludedByteRange")
    protected ExcludedByteRangeType excludedByteRange;

    /**
     * Gets the value of the metaInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MetaInfoType }
     *     
     */
    public MetaInfoType getMetaInfo() {
        return metaInfo;
    }

    /**
     * Sets the value of the metaInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MetaInfoType }
     *     
     */
    public void setMetaInfo(MetaInfoType value) {
        this.metaInfo = value;
    }

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link Base64OptRefContentType }
     *     
     */
    public Base64OptRefContentType getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link Base64OptRefContentType }
     *     
     */
    public void setContent(Base64OptRefContentType value) {
        this.content = value;
    }

    /**
     * Gets the value of the excludedByteRange property.
     * 
     * @return
     *     possible object is
     *     {@link ExcludedByteRangeType }
     *     
     */
    public ExcludedByteRangeType getExcludedByteRange() {
        return excludedByteRange;
    }

    /**
     * Sets the value of the excludedByteRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExcludedByteRangeType }
     *     
     */
    public void setExcludedByteRange(ExcludedByteRangeType value) {
        this.excludedByteRange = value;
    }

}
