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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateHashResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateHashResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HashInfo" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}CreateHashInfoResponseType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateHashResponseType", propOrder = {
    "hashInfo"
})
public class CreateHashResponseType {

    @XmlElement(name = "HashInfo", required = true)
    protected List<CreateHashInfoResponseType> hashInfo;

    /**
     * Gets the value of the hashInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hashInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHashInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CreateHashInfoResponseType }
     * 
     * 
     */
    public List<CreateHashInfoResponseType> getHashInfo() {
        if (hashInfo == null) {
            hashInfo = new ArrayList<CreateHashInfoResponseType>();
        }
        return this.hashInfo;
    }

}
