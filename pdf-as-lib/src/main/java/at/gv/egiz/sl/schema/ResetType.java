//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.22 at 04:01:10 PM CEST 
//


package at.gv.egiz.sl.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="cold" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}ResetColdType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResetType")
public class ResetType {

    @XmlAttribute(name = "cold")
    protected ResetColdType cold;

    /**
     * Gets the value of the cold property.
     * 
     * @return
     *     possible object is
     *     {@link ResetColdType }
     *     
     */
    public ResetColdType getCold() {
        return cold;
    }

    /**
     * Sets the value of the cold property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResetColdType }
     *     
     */
    public void setCold(ResetColdType value) {
        this.cold = value;
    }

}
