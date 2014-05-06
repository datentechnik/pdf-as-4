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
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for GetPropertiesResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetPropertiesResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ViewerMediaType" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}MimeTypeType" maxOccurs="unbounded"/>
 *         &lt;element name="XMLSignatureTransform" type="{http://www.w3.org/2001/XMLSchema}anyURI" maxOccurs="unbounded"/>
 *         &lt;element name="KeyboxIdentifier" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}QualifiedBoxIdentifierType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Binding" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}BindingType" maxOccurs="unbounded"/>
 *         &lt;element name="ProtocolVersion" type="{http://www.w3.org/2001/XMLSchema}token" maxOccurs="unbounded"/>
 *         &lt;any namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPropertiesResponseType", propOrder = {
    "viewerMediaType",
    "xmlSignatureTransform",
    "keyboxIdentifier",
    "binding",
    "protocolVersion",
    "any"
})
public class GetPropertiesResponseType {

    @XmlElement(name = "ViewerMediaType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected List<String> viewerMediaType;
    @XmlElement(name = "XMLSignatureTransform", required = true)
    @XmlSchemaType(name = "anyURI")
    protected List<String> xmlSignatureTransform;
    @XmlElement(name = "KeyboxIdentifier")
    protected List<QualifiedBoxIdentifierType> keyboxIdentifier;
    @XmlElement(name = "Binding", required = true)
    protected List<BindingType> binding;
    @XmlElement(name = "ProtocolVersion", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected List<String> protocolVersion;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the viewerMediaType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the viewerMediaType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getViewerMediaType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getViewerMediaType() {
        if (viewerMediaType == null) {
            viewerMediaType = new ArrayList<String>();
        }
        return this.viewerMediaType;
    }

    /**
     * Gets the value of the xmlSignatureTransform property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the xmlSignatureTransform property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getXMLSignatureTransform().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getXMLSignatureTransform() {
        if (xmlSignatureTransform == null) {
            xmlSignatureTransform = new ArrayList<String>();
        }
        return this.xmlSignatureTransform;
    }

    /**
     * Gets the value of the keyboxIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyboxIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyboxIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualifiedBoxIdentifierType }
     * 
     * 
     */
    public List<QualifiedBoxIdentifierType> getKeyboxIdentifier() {
        if (keyboxIdentifier == null) {
            keyboxIdentifier = new ArrayList<QualifiedBoxIdentifierType>();
        }
        return this.keyboxIdentifier;
    }

    /**
     * Gets the value of the binding property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the binding property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBinding().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BindingType }
     * 
     * 
     */
    public List<BindingType> getBinding() {
        if (binding == null) {
            binding = new ArrayList<BindingType>();
        }
        return this.binding;
    }

    /**
     * Gets the value of the protocolVersion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the protocolVersion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtocolVersion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getProtocolVersion() {
        if (protocolVersion == null) {
            protocolVersion = new ArrayList<String>();
        }
        return this.protocolVersion;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

}
