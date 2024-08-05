
package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.br_2;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.TopicExpressionType;
import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.t_1.TopicSetType;


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
 *         &amp;lt;element ref="{http://docs.oasis-open.org/wsn/b-2}TopicExpression" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://docs.oasis-open.org/wsn/b-2}FixedTopicSet" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://docs.oasis-open.org/wsn/b-2}TopicExpressionDialect" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://docs.oasis-open.org/wsn/t-1}TopicSet" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://docs.oasis-open.org/wsn/br-2}RequiresRegistration"/&amp;gt;
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
    "topicExpression",
    "fixedTopicSet",
    "topicExpressionDialect",
    "topicSet",
    "requiresRegistration"
})
@XmlRootElement(name = "NotificationBrokerRP")
public class NotificationBrokerRP {

    @XmlElement(name = "TopicExpression", namespace = "http://docs.oasis-open.org/wsn/b-2")
    protected List<TopicExpressionType> topicExpression;
    @XmlElement(name = "FixedTopicSet", namespace = "http://docs.oasis-open.org/wsn/b-2", defaultValue = "true")
    protected Boolean fixedTopicSet;
    @XmlElement(name = "TopicExpressionDialect", namespace = "http://docs.oasis-open.org/wsn/b-2")
    @XmlSchemaType(name = "anyURI")
    protected List<String> topicExpressionDialect;
    @XmlElement(name = "TopicSet", namespace = "http://docs.oasis-open.org/wsn/t-1")
    protected TopicSetType topicSet;
    @XmlElement(name = "RequiresRegistration")
    protected boolean requiresRegistration;

    /**
     * Gets the value of the topicExpression property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the topicExpression property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTopicExpression().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TopicExpressionType }
     * 
     * 
     */
    public List<TopicExpressionType> getTopicExpression() {
        if (topicExpression == null) {
            topicExpression = new ArrayList<TopicExpressionType>();
        }
        return this.topicExpression;
    }

    /**
     * Gets the value of the fixedTopicSet property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFixedTopicSet() {
        return fixedTopicSet;
    }

    /**
     * Sets the value of the fixedTopicSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFixedTopicSet(Boolean value) {
        this.fixedTopicSet = value;
    }

    /**
     * Gets the value of the topicExpressionDialect property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the topicExpressionDialect property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTopicExpressionDialect().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTopicExpressionDialect() {
        if (topicExpressionDialect == null) {
            topicExpressionDialect = new ArrayList<String>();
        }
        return this.topicExpressionDialect;
    }

    /**
     * Gets the value of the topicSet property.
     * 
     * @return
     *     possible object is
     *     {@link TopicSetType }
     *     
     */
    public TopicSetType getTopicSet() {
        return topicSet;
    }

    /**
     * Sets the value of the topicSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link TopicSetType }
     *     
     */
    public void setTopicSet(TopicSetType value) {
        this.topicSet = value;
    }

    /**
     * Gets the value of the requiresRegistration property.
     * 
     */
    public boolean isRequiresRegistration() {
        return requiresRegistration;
    }

    /**
     * Sets the value of the requiresRegistration property.
     * 
     */
    public void setRequiresRegistration(boolean value) {
        this.requiresRegistration = value;
    }

}
