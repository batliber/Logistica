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
	name = "PullPointService",
	targetNamespace = "http://www.antel.com.uy/pi/notification/nbr-v1",
	wsdlLocation = "file:/home/amensg/ANTEL/notif/ServicioNotificaciones.wsdl"
)
public class PullPointService extends Service {

	public final static URL WSDL_LOCATION;

	public final static QName SERVICE = new QName("http://www.antel.com.uy/pi/notification/nbr-v1", "PullPointService");
	public final static QName PullPointPort = new QName("http://www.antel.com.uy/pi/notification/nbr-v1", "PullPointPort");
	
	static {
		URL url = null;
		try {
			url = 
				new URL(
					"file:" + Configuration.getInstance().getProperty("antel.NotificationBroker.wsdllocalfilepath")
				);
		} catch (MalformedURLException e) {
			java.util.logging.Logger.getLogger(PullPointService.class.getName())
				.log(java.util.logging.Level.INFO,
					"Can not initialize the default wsdl from {0}", 
					"file:" + Configuration.getInstance().getProperty("antel.NotificationBroker.wsdllocalfilepath")
				);
		}
		WSDL_LOCATION = url;
	}

	public PullPointService(URL wsdlLocation) {
		super(wsdlLocation, SERVICE);
	}

	public PullPointService(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public PullPointService() {
		super(WSDL_LOCATION, SERVICE);
	}

	public PullPointService(WebServiceFeature ... features) {
		super(WSDL_LOCATION, SERVICE, features);
	}

	public PullPointService(URL wsdlLocation, WebServiceFeature ... features) {
		super(wsdlLocation, SERVICE, features);
	}

	public PullPointService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
		super(wsdlLocation, serviceName, features);
	}

	@WebEndpoint(name = "PullPointPort")
	public PullPoint getPullPointPort() {
		return super.getPort(PullPointPort, PullPoint.class);
	}

	@WebEndpoint(name = "PullPointPort")
	public PullPoint getPullPointPort(WebServiceFeature... features) {
		return super.getPort(PullPointPort, PullPoint.class, features);
	}
}