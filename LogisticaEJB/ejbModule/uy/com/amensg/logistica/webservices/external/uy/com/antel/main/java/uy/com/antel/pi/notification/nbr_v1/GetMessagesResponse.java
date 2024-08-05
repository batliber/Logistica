package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1;

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

import uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.org.oasis_open.docs.wsn.b_2.TopicExpressionType;

@XmlAccessorType(
	XmlAccessType.FIELD
)
@XmlType(
	name = "", 
	propOrder = {
		"notificationMessage"
	}
)
@XmlRootElement(
	name = "GetMessagesResponse"
)
public class GetMessagesResponse {

	@XmlElement(name = "NotificationMessage")
	protected List<GetMessagesResponse.NotificationMessage> notificationMessage;

	public List<GetMessagesResponse.NotificationMessage> getNotificationMessage() {
		if (notificationMessage == null) {
			notificationMessage = new ArrayList<GetMessagesResponse.NotificationMessage>();
		}
		return this.notificationMessage;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
		"creationTime",
		"uuid",
		"publisher",
		"topic",
		"message"
	})
	public static class NotificationMessage {

		@XmlElement(name = "CreationTime", required = true)
		@XmlSchemaType(name = "dateTime")
		protected XMLGregorianCalendar creationTime;
		@XmlElement(required = true)
		protected String uuid;
		@XmlElement(name = "Publisher", required = true)
		protected String publisher;
		@XmlElement(name = "Topic", namespace = "http://docs.oasis-open.org/wsn/b-2")
		protected TopicExpressionType topic;
		@XmlElement(name = "Message", required = true)
		protected GetMessagesResponse.NotificationMessage.Message message;

		public XMLGregorianCalendar getCreationTime() {
			return creationTime;
		}

		public void setCreationTime(XMLGregorianCalendar value) {
			this.creationTime = value;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String value) {
			this.uuid = value;
		}

		public String getPublisher() {
			return publisher;
		}

		public void setPublisher(String value) {
			this.publisher = value;
		}

		public TopicExpressionType getTopic() {
			return topic;
		}

		public void setTopic(TopicExpressionType value) {
			this.topic = value;
		}

		public GetMessagesResponse.NotificationMessage.Message getMessage() {
			return message;
		}

		public void setMessage(GetMessagesResponse.NotificationMessage.Message value) {
			this.message = value;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		@XmlType(
			name = "", 
			propOrder = {
				"any"
			}
		)
		public static class Message {

			@XmlAnyElement(lax = true)
			protected Object any;

			public Object getAny() {
				return any;
			}

			public void setAny(Object value) {
				this.any = value;
			}
		}
	}
}