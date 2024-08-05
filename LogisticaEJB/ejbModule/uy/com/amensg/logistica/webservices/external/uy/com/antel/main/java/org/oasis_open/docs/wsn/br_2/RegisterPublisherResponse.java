
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.ws.wsaddressing.W3CEndpointReference;


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
 *         &amp;lt;element name="PublisherRegistrationReference" type="{http://www.w3.org/2005/08/addressing}EndpointReferenceType"/&amp;gt;
 *         &amp;lt;element name="ConsumerReference" type="{http://www.w3.org/2005/08/addressing}EndpointReferenceType" minOccurs="0"/&amp;gt;
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
    "publisherRegistrationReference",
    "consumerReference"
})
@XmlRootElement(name = "RegisterPublisherResponse")
public class RegisterPublisherResponse {

    @XmlElement(name = "PublisherRegistrationReference", required = true)
    protected W3CEndpointReference publisherRegistrationReference;
    @XmlElement(name = "ConsumerReference")
    protected W3CEndpointReference consumerReference;

    /**
     * Gets the value of the publisherRegistrationReference property.
     * 
     * @return
     *     possible object is
     *     {@link W3CEndpointReference }
     *     
     */
    public W3CEndpointReference getPublisherRegistrationReference() {
        return publisherRegistrationReference;
    }

    /**
     * Sets the value of the publisherRegistrationReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link W3CEndpointReference }
     *     
     */
    public void setPublisherRegistrationReference(W3CEndpointReference value) {
        this.publisherRegistrationReference = value;
    }

    /**
     * Gets the value of the consumerReference property.
     * 
     * @return
     *     possible object is
     *     {@link W3CEndpointReference }
     *     
     */
    public W3CEndpointReference getConsumerReference() {
        return consumerReference;
    }

    /**
     * Sets the value of the consumerReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link W3CEndpointReference }
     *     
     */
    public void setConsumerReference(W3CEndpointReference value) {
        this.consumerReference = value;
    }

}
