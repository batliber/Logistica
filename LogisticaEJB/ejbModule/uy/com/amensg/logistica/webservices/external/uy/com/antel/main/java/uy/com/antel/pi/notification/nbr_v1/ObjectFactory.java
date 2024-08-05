package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
		
	}

	public GetMessagesResponse createGetMessagesResponse() {
		return new GetMessagesResponse();
	}

	public GetMessagesResponse.NotificationMessage createGetMessagesResponseNotificationMessage() {
		return new GetMessagesResponse.NotificationMessage();
	}

	public NotifyResponse createNotifyResponse() {
		return new NotifyResponse();
	}

	public GetMessagesResponse.NotificationMessage.Message createGetMessagesResponseNotificationMessageMessage() {
		return new GetMessagesResponse.NotificationMessage.Message();
	}
}