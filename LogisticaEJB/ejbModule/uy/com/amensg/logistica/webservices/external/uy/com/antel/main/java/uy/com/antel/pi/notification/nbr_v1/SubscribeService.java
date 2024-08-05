package uy.com.amensg.logistica.webservices.external.uy.com.antel.main.java.uy.com.antel.pi.notification.nbr_v1;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import uy.com.amensg.logistica.util.Configuration;

import javax.xml.ws.Service;

@WebServiceClient(
	name = "SubscribeService",
	targetNamespace = "http://www.antel.com.uy/pi/notification/nbr-v1",
	wsdlLocation = "file:/home/amensg/ANTEL/notif/ServicioNotificaciones.wsdl"
)
public class SubscribeService extends Service {

	public final static URL WSDL_LOCATION;

	public final static QName SERVICE = new QName("http://www.antel.com.uy/pi/notification/nbr-v1", "SubscribeService");
	public final static QName SubscribePort = new QName("http://www.antel.com.uy/pi/notification/nbr-v1", "SubscribePort");
	
	static {
		URL url = null;
		try {
			url = 
				new URL(
					"file:" + Configuration.getInstance().getProperty("antel.NotificationBroker.wsdllocalfilepath")
				);
		} catch (MalformedURLException e) {
			java.util.logging.Logger.getLogger(SubscribeService.class.getName())
				.log(java.util.logging.Level.INFO,
					"Can not initialize the default wsdl from {0}", 
					"file:" + Configuration.getInstance().getProperty("antel.NotificationBroker.wsdllocalfilepath")
				);
		}
		WSDL_LOCATION = url;
	}

	public SubscribeService(URL wsdlLocation) {
		super(wsdlLocation, SERVICE);
	}

	public SubscribeService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public SubscribeService() {
		super(WSDL_LOCATION, SERVICE);
	}

	public SubscribeService(WebServiceFeature ... features) {
		super(WSDL_LOCATION, SERVICE, features);
	}

	public SubscribeService(URL wsdlLocation, WebServiceFeature ... features) {
		super(wsdlLocation, SERVICE, features);
	}

	public SubscribeService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
		super(wsdlLocation, serviceName, features);
	}

	@WebEndpoint(name = "SubscribePort")
	public NotificationBrokerSubscribe getSubscribePort() {
		return super.getPort(SubscribePort, NotificationBrokerSubscribe.class);
	}

	@WebEndpoint(name = "SubscribePort")
	public NotificationBrokerSubscribe getSubscribePort(WebServiceFeature... features) {
		return super.getPort(SubscribePort, NotificationBrokerSubscribe.class, features);
	}
}