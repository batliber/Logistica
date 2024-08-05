
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
 * &lt;p&gt;Java class for InvalidFilterFaultType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="InvalidFilterFaultType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://docs.oasis-open.org/wsrf/bf-2}BaseFaultType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="UnknownFilter" type="{http://www.w3.org/2001/XMLSchema}QName" maxOccurs="unbounded"/&amp;gt;
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
@XmlType(name = "InvalidFilterFaultType", propOrder = {
    "unknownFilter"
})
public class InvalidFilterFaultType
    extends BaseFaultType
{

    @XmlElement(name = "UnknownFilter", required = true)
    protected List<QName> unknownFilter;

    /**
     * Gets the value of the unknownFilter property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the unknownFilter property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getUnknownFilter().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link QName }
     * 
     * 
     */
    public List<QName> getUnknownFilter() {
        if (unknownFilter == null) {
            unknownFilter = new ArrayList<QName>();
        }
        return this.unknownFilter;
    }

}
