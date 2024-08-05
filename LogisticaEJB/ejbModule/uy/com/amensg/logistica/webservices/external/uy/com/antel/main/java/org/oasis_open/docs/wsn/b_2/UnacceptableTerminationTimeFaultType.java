
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.bf_2.BaseFaultType;


/**
 * &lt;p&gt;Java class for UnacceptableTerminationTimeFaultType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="UnacceptableTerminationTimeFaultType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://docs.oasis-open.org/wsrf/bf-2}BaseFaultType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="MinimumTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="MaximumTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
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
@XmlType(name = "UnacceptableTerminationTimeFaultType", propOrder = {
    "minimumTime",
    "maximumTime"
})
public class UnacceptableTerminationTimeFaultType
    extends BaseFaultType
{

    @XmlElement(name = "MinimumTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar minimumTime;
    @XmlElement(name = "MaximumTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar maximumTime;

    /**
     * Gets the value of the minimumTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMinimumTime() {
        return minimumTime;
    }

    /**
     * Sets the value of the minimumTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMinimumTime(XMLGregorianCalendar value) {
        this.minimumTime = value;
    }

    /**
     * Gets the value of the maximumTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMaximumTime() {
        return maximumTime;
    }

    /**
     * Sets the value of the maximumTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMaximumTime(XMLGregorianCalendar value) {
        this.maximumTime = value;
    }

}
