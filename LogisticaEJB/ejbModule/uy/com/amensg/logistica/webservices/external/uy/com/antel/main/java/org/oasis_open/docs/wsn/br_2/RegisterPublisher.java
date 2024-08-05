
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.w3c.dom.Element;

import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.TopicExpressionType;


/**
 * &lt;p&gt;Java class for anonymous complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="PublisherReference" type="{http://www.w3.org/2005/08/addressing}EndpointReferenceType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Topic" type="{http://docs.oasis-open.org/wsn/b-2}TopicExpressionType" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Demand" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="InitialTerminationTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "publisherReference",
    "topic",
    "demand",
    "initialTerminationTime",
    "any"
})
@XmlRootElement(name = "RegisterPublisher")
public class RegisterPublisher {

    @XmlElement(name = "PublisherReference")
    protected W3CEndpointReference publisherReference;
    @XmlElement(name = "Topic")
    protected List<TopicExpressionType> topic;
    @XmlElement(name = "Demand", defaultValue = "false")
    protected Boolean demand;
    @XmlElement(name = "InitialTerminationTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar initialTerminationTime;
    @XmlAnyElement(lax = true)
    protected List<Object> any;

    /**
     * Gets the value of the publisherReference property.
     * 
     * @return
     *     possible object is
     *     {@link W3CEndpointReference }
     *     
     */
    public W3CEndpointReference getPublisherReference() {
        return publisherReference;
    }

    /**
     * Sets the value of the publisherReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link W3CEndpointReference }
     *     
     */
    public void setPublisherReference(W3CEndpointReference value) {
        this.publisherReference = value;
    }

    /**
     * Gets the value of the topic property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the topic property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTopic().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TopicExpressionType }
     * 
     * 
     */
    public List<TopicExpressionType> getTopic() {
        if (topic == null) {
            topic = new ArrayList<TopicExpressionType>();
        }
        return this.topic;
    }

    /**
     * Gets the value of the demand property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDemand() {
        return demand;
    }

    /**
     * Sets the value of the demand property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDemand(Boolean value) {
        this.demand = value;
    }

    /**
     * Gets the value of the initialTerminationTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInitialTerminationTime() {
        return initialTerminationTime;
    }

    /**
     * Sets the value of the initialTerminationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInitialTerminationTime(XMLGregorianCalendar value) {
        this.initialTerminationTime = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the any property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAny().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
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
