
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.bf_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.w3c.dom.Element;

import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.InvalidFilterFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.InvalidMessageContentExpressionFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.InvalidProducerPropertiesExpressionFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.InvalidTopicExpressionFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.MultipleTopicsSpecifiedFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.NoCurrentMessageOnTopicFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.NotifyMessageNotSupportedFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.PauseFailedFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.ResumeFailedFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.SubscribeCreationFailedFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.TopicExpressionDialectUnknownFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.TopicNotSupportedFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnableToCreatePullPointFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnableToDestroyPullPointFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnableToDestroySubscriptionFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnableToGetMessagesFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnacceptableInitialTerminationTimeFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnacceptableTerminationTimeFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnrecognizedPolicyRequestFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.UnsupportedPolicyRequestFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2.PublisherRegistrationFailedFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2.PublisherRegistrationRejectedFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2.ResourceNotDestroyedFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.r_2.ResourceUnavailableFaultType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsrf.r_2.ResourceUnknownFaultType;


/**
 * &lt;p&gt;Java class for BaseFaultType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="BaseFaultType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&amp;gt;
 *         &amp;lt;element name="Originator" type="{http://www.w3.org/2005/08/addressing}EndpointReferenceType" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="ErrorCode" minOccurs="0"&amp;gt;
 *           &amp;lt;complexType&amp;gt;
 *             &amp;lt;complexContent&amp;gt;
 *               &amp;lt;extension base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *                 &amp;lt;attribute name="dialect" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&amp;gt;
 *                 &amp;lt;anyAttribute processContents='skip'/&amp;gt;
 *               &amp;lt;/extension&amp;gt;
 *             &amp;lt;/complexContent&amp;gt;
 *           &amp;lt;/complexType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *         &amp;lt;element name="Description" maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *           &amp;lt;complexType&amp;gt;
 *             &amp;lt;simpleContent&amp;gt;
 *               &amp;lt;extension base="&amp;lt;http://www.w3.org/2001/XMLSchema&amp;gt;string"&amp;gt;
 *                 &amp;lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/&amp;gt;
 *               &amp;lt;/extension&amp;gt;
 *             &amp;lt;/simpleContent&amp;gt;
 *           &amp;lt;/complexType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *         &amp;lt;element name="FaultCause" minOccurs="0"&amp;gt;
 *           &amp;lt;complexType&amp;gt;
 *             &amp;lt;complexContent&amp;gt;
 *               &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *                 &amp;lt;sequence&amp;gt;
 *                   &amp;lt;any processContents='lax' namespace='##other'/&amp;gt;
 *                 &amp;lt;/sequence&amp;gt;
 *               &amp;lt;/restriction&amp;gt;
 *             &amp;lt;/complexContent&amp;gt;
 *           &amp;lt;/complexType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;anyAttribute processContents='lax' namespace='##other'/&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseFaultType", propOrder = {
    "any",
    "timestamp",
    "originator",
    "errorCode",
    "description",
    "faultCause"
})
@XmlSeeAlso({
    PublisherRegistrationRejectedFaultType.class,
    PublisherRegistrationFailedFaultType.class,
    ResourceNotDestroyedFaultType.class,
    ResourceUnknownFaultType.class,
    ResourceUnavailableFaultType.class,
    SubscribeCreationFailedFaultType.class,
    InvalidFilterFaultType.class,
    TopicExpressionDialectUnknownFaultType.class,
    InvalidTopicExpressionFaultType.class,
    TopicNotSupportedFaultType.class,
    MultipleTopicsSpecifiedFaultType.class,
    InvalidProducerPropertiesExpressionFaultType.class,
    InvalidMessageContentExpressionFaultType.class,
    UnrecognizedPolicyRequestFaultType.class,
    UnsupportedPolicyRequestFaultType.class,
    NotifyMessageNotSupportedFaultType.class,
    UnacceptableInitialTerminationTimeFaultType.class,
    NoCurrentMessageOnTopicFaultType.class,
    UnableToGetMessagesFaultType.class,
    UnableToDestroyPullPointFaultType.class,
    UnableToCreatePullPointFaultType.class,
    UnacceptableTerminationTimeFaultType.class,
    UnableToDestroySubscriptionFaultType.class,
    PauseFailedFaultType.class,
    ResumeFailedFaultType.class
})
public class BaseFaultType {

    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlElement(name = "Timestamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlElement(name = "Originator")
    protected W3CEndpointReference originator;
    @XmlElement(name = "ErrorCode")
    protected BaseFaultType.ErrorCode errorCode;
    @XmlElement(name = "Description")
    protected List<BaseFaultType.Description> description;
    @XmlElement(name = "FaultCause")
    protected BaseFaultType.FaultCause faultCause;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

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

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the originator property.
     * 
     * @return
     *     possible object is
     *     {@link W3CEndpointReference }
     *     
     */
    public W3CEndpointReference getOriginator() {
        return originator;
    }

    /**
     * Sets the value of the originator property.
     * 
     * @param value
     *     allowed object is
     *     {@link W3CEndpointReference }
     *     
     */
    public void setOriginator(W3CEndpointReference value) {
        this.originator = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link BaseFaultType.ErrorCode }
     *     
     */
    public BaseFaultType.ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseFaultType.ErrorCode }
     *     
     */
    public void setErrorCode(BaseFaultType.ErrorCode value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the description property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link BaseFaultType.Description }
     * 
     * 
     */
    public List<BaseFaultType.Description> getDescription() {
        if (description == null) {
            description = new ArrayList<BaseFaultType.Description>();
        }
        return this.description;
    }

    /**
     * Gets the value of the faultCause property.
     * 
     * @return
     *     possible object is
     *     {@link BaseFaultType.FaultCause }
     *     
     */
    public BaseFaultType.FaultCause getFaultCause() {
        return faultCause;
    }

    /**
     * Sets the value of the faultCause property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseFaultType.FaultCause }
     *     
     */
    public void setFaultCause(BaseFaultType.FaultCause value) {
        this.faultCause = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * &lt;p&gt;
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }


    /**
     * &lt;p&gt;Java class for anonymous complex type.
     * 
     * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
     * 
     * &lt;pre&gt;
     * &amp;lt;complexType&amp;gt;
     *   &amp;lt;simpleContent&amp;gt;
     *     &amp;lt;extension base="&amp;lt;http://www.w3.org/2001/XMLSchema&amp;gt;string"&amp;gt;
     *       &amp;lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang"/&amp;gt;
     *     &amp;lt;/extension&amp;gt;
     *   &amp;lt;/simpleContent&amp;gt;
     * &amp;lt;/complexType&amp;gt;
     * &lt;/pre&gt;
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Description {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
        protected String lang;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the lang property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLang() {
            return lang;
        }

        /**
         * Sets the value of the lang property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLang(String value) {
            this.lang = value;
        }

    }


    /**
     * &lt;p&gt;Java class for anonymous complex type.
     * 
     * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
     * 
     * &lt;pre&gt;
     * &amp;lt;complexType&amp;gt;
     *   &amp;lt;complexContent&amp;gt;
     *     &amp;lt;extension base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
     *       &amp;lt;attribute name="dialect" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&amp;gt;
     *       &amp;lt;anyAttribute processContents='skip'/&amp;gt;
     *     &amp;lt;/extension&amp;gt;
     *   &amp;lt;/complexContent&amp;gt;
     * &amp;lt;/complexType&amp;gt;
     * &lt;/pre&gt;
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "content"
    })
    public static class ErrorCode {

        @XmlMixed
        @XmlAnyElement
        protected List<Object> content;
        @XmlAttribute(name = "dialect", required = true)
        @XmlSchemaType(name = "anyURI")
        protected String dialect;
        @XmlAnyAttribute
        private Map<QName, String> otherAttributes = new HashMap<QName, String>();

        /**
         * Gets the value of the content property.
         * 
         * &lt;p&gt;
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the content property.
         * 
         * &lt;p&gt;
         * For example, to add a new item, do as follows:
         * &lt;pre&gt;
         *    getContent().add(newItem);
         * &lt;/pre&gt;
         * 
         * 
         * &lt;p&gt;
         * Objects of the following type(s) are allowed in the list
         * {@link Element }
         * {@link String }
         * 
         * 
         */
        public List<Object> getContent() {
            if (content == null) {
                content = new ArrayList<Object>();
            }
            return this.content;
        }

        /**
         * Gets the value of the dialect property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDialect() {
            return dialect;
        }

        /**
         * Sets the value of the dialect property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDialect(String value) {
            this.dialect = value;
        }

        /**
         * Gets a map that contains attributes that aren't bound to any typed property on this class.
         * 
         * &lt;p&gt;
         * the map is keyed by the name of the attribute and 
         * the value is the string value of the attribute.
         * 
         * the map returned by this method is live, and you can add new attribute
         * by updating the map directly. Because of this design, there's no setter.
         * 
         * 
         * @return
         *     always non-null
         */
        public Map<QName, String> getOtherAttributes() {
            return otherAttributes;
        }

    }


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
     *         &amp;lt;any processContents='lax' namespace='##other'/&amp;gt;
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
        "any"
    })
    public static class FaultCause {

        @XmlAnyElement(lax = true)
        protected Object any;

        /**
         * Gets the value of the any property.
         * 
         * @return
         *     possible object is
         *     {@link Element }
         *     {@link Object }
         *     
         */
        public Object getAny() {
            return any;
        }

        /**
         * Sets the value of the any property.
         * 
         * @param value
         *     allowed object is
         *     {@link Element }
         *     {@link Object }
         *     
         */
        public void setAny(Object value) {
            this.any = value;
        }

    }

}
