
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.t_1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.w3c.dom.Element;


/**
 * &lt;p&gt;Java class for TopicNamespaceType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TopicNamespaceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;extension base="{http://docs.oasis-open.org/wsn/t-1}ExtensibleDocumented"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="Topic" maxOccurs="unbounded" minOccurs="0"&amp;gt;
 *           &amp;lt;complexType&amp;gt;
 *             &amp;lt;complexContent&amp;gt;
 *               &amp;lt;extension base="{http://docs.oasis-open.org/wsn/t-1}TopicType"&amp;gt;
 *                 &amp;lt;attribute name="parent" type="{http://docs.oasis-open.org/wsn/t-1}ConcreteTopicExpression" /&amp;gt;
 *                 &amp;lt;anyAttribute processContents='lax' namespace='##other'/&amp;gt;
 *               &amp;lt;/extension&amp;gt;
 *             &amp;lt;/complexContent&amp;gt;
 *           &amp;lt;/complexType&amp;gt;
 *         &amp;lt;/element&amp;gt;
 *         &amp;lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *       &amp;lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}NCName" /&amp;gt;
 *       &amp;lt;attribute name="targetNamespace" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&amp;gt;
 *       &amp;lt;attribute name="final" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&amp;gt;
 *       &amp;lt;anyAttribute processContents='lax' namespace='##other'/&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TopicNamespaceType", propOrder = {
    "topic",
    "any"
})
public class TopicNamespaceType
    extends ExtensibleDocumented
{

    @XmlElement(name = "Topic")
    protected List<TopicNamespaceType.Topic> topic;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "name")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String name;
    @XmlAttribute(name = "targetNamespace", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String targetNamespace;
    @XmlAttribute(name = "final")
    protected Boolean _final;

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
     * {@link TopicNamespaceType.Topic }
     * 
     * 
     */
    public List<TopicNamespaceType.Topic> getTopic() {
        if (topic == null) {
            topic = new ArrayList<TopicNamespaceType.Topic>();
        }
        return this.topic;
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

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the targetNamespace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetNamespace() {
        return targetNamespace;
    }

    /**
     * Sets the value of the targetNamespace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetNamespace(String value) {
        this.targetNamespace = value;
    }

    /**
     * Gets the value of the final property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isFinal() {
        if (_final == null) {
            return false;
        } else {
            return _final;
        }
    }

    /**
     * Sets the value of the final property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFinal(Boolean value) {
        this._final = value;
    }


    /**
     * &lt;p&gt;Java class for anonymous complex type.
     * 
     * &lt;p&gt;The following schema fragment specifies the expected content contained within this class.
     * 
     * &lt;pre&gt;
     * &amp;lt;complexType&amp;gt;
     *   &amp;lt;complexContent&amp;gt;
     *     &amp;lt;extension base="{http://docs.oasis-open.org/wsn/t-1}TopicType"&amp;gt;
     *       &amp;lt;attribute name="parent" type="{http://docs.oasis-open.org/wsn/t-1}ConcreteTopicExpression" /&amp;gt;
     *       &amp;lt;anyAttribute processContents='lax' namespace='##other'/&amp;gt;
     *     &amp;lt;/extension&amp;gt;
     *   &amp;lt;/complexContent&amp;gt;
     * &amp;lt;/complexType&amp;gt;
     * &lt;/pre&gt;
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Topic
        extends TopicType
    {

        @XmlAttribute(name = "parent")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String parent;

        /**
         * Gets the value of the parent property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getParent() {
            return parent;
        }

        /**
         * Sets the value of the parent property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setParent(String value) {
            this.parent = value;
        }

    }

}
