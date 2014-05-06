//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.22 at 04:01:10 PM CEST 
//


package at.gv.egiz.sl.schema;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultElement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResultElement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="CardAction" use="required" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}CardActionType" />
 *       &lt;attribute name="ApplicationIdentifier" use="required" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}ApplicationIdentifierType" />
 *       &lt;attribute name="Result" use="required" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}CardActionResponseType" />
 *       &lt;attribute name="RetryCount" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultElement")
public class ResultElement {

    @XmlAttribute(name = "CardAction", required = true)
    protected CardActionType cardAction;
    @XmlAttribute(name = "ApplicationIdentifier", required = true)
    protected ApplicationIdentifierType applicationIdentifier;
    @XmlAttribute(name = "Result", required = true)
    protected CardActionResponseType result;
    @XmlAttribute(name = "RetryCount")
    protected BigInteger retryCount;

    /**
     * Gets the value of the cardAction property.
     * 
     * @return
     *     possible object is
     *     {@link CardActionType }
     *     
     */
    public CardActionType getCardAction() {
        return cardAction;
    }

    /**
     * Sets the value of the cardAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardActionType }
     *     
     */
    public void setCardAction(CardActionType value) {
        this.cardAction = value;
    }

    /**
     * Gets the value of the applicationIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationIdentifierType }
     *     
     */
    public ApplicationIdentifierType getApplicationIdentifier() {
        return applicationIdentifier;
    }

    /**
     * Sets the value of the applicationIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationIdentifierType }
     *     
     */
    public void setApplicationIdentifier(ApplicationIdentifierType value) {
        this.applicationIdentifier = value;
    }

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link CardActionResponseType }
     *     
     */
    public CardActionResponseType getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardActionResponseType }
     *     
     */
    public void setResult(CardActionResponseType value) {
        this.result = value;
    }

    /**
     * Gets the value of the retryCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRetryCount() {
        return retryCount;
    }

    /**
     * Sets the value of the retryCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRetryCount(BigInteger value) {
        this.retryCount = value;
    }

}
