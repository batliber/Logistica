
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.bf_2.BaseFaultType;


/**
 * &lt;p&gt;Java class for UnrecognizedPolicyRequestFaultType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="UnrecognizedPolicyRequestFaultType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://docs.oasis-open.org/wsrf/bf-2}BaseFaultType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="UnrecognizedPolicy" type="{http://www.w3.org/2001/XMLSchema}QName" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;anyAttribute processContents='lax' namespace='##other'/&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UnrecognizedPolicyRequestFaultType", propOrder = {
    "unrecognizedPolicy"
})
public class UnrecognizedPolicyRequestFaultType
    extends BaseFaultType
{

    @XmlElement(name = "UnrecognizedPolicy")
    protected List<QName> unrecognizedPolicy;

    /**
     * Gets the value of the unrecognizedPolicy property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the unrecognizedPolicy property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getUnrecognizedPolicy().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link QName }
     * 
     * 
     */
    public List<QName> getUnrecognizedPolicy() {
        if (unrecognizedPolicy == null) {
            unrecognizedPolicy = new ArrayList<QName>();
        }
        return this.unrecognizedPolicy;
    }

}
