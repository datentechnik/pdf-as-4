//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.22 at 04:01:10 PM CEST 
//


package at.gv.egiz.sl.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CardActionResponseType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CardActionResponseType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OK"/>
 *     &lt;enumeration value="Error"/>
 *     &lt;enumeration value="Blocked"/>
 *     &lt;enumeration value="Activ"/>
 *     &lt;enumeration value="Inactive"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CardActionResponseType")
@XmlEnum
public enum CardActionResponseType {

    OK("OK"),
    @XmlEnumValue("Error")
    ERROR("Error"),
    @XmlEnumValue("Blocked")
    BLOCKED("Blocked"),
    @XmlEnumValue("Activ")
    ACTIV("Activ"),
    @XmlEnumValue("Inactive")
    INACTIVE("Inactive");
    private final String value;

    CardActionResponseType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CardActionResponseType fromValue(String v) {
        for (CardActionResponseType c: CardActionResponseType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
