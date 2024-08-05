package uy.com.amensg.logistica.webservices.external.agtracmg.aws.ancel.com;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(
	name = "GTRACMG",
	targetNamespace = "com.ancel.aws.agtracmg",
	wsdlLocation = "file:/home/amensg/ANTEL/recargas/com.ancel.aws.agtracmg.wsdl"
)
public class GTRACMG extends Service {

	public final static URL WSDL_LOCATION;

	public final static QName SERVICE = new QName("com.ancel.aws.agtracmg", "GTRACMG");
	public final static QName GTRACMGSoapPort = new QName("com.ancel.aws.agtracmg", "GTRACMGSoapPort");
	static {
		URL url = null;
		
		try {
			url = new URL("file:/home/amensg/ANTEL/recargas/com.ancel.aws.agtracmg.wsdl");
//			url = 
//				new URL(
//					"file:" + Configuration.getInstance().getProperty("antel.GTRACMG.wsdllocalfilepath")
//				);
		} catch (MalformedURLException e) {
			java.util.logging.Logger.getLogger(GTRACMG.class.getName())
				.log(java.util.logging.Level.INFO,
					"Can not initialize the default wsdl from {0}", "file:/home/amensg/ANTEL/recargas/com.ancel.aws.agtracmg.wsdl"
				);
		}
		
		WSDL_LOCATION = url;
	}

	public GTRACMG(URL wsdlLocation) {
		super(wsdlLocation, SERVICE);
	}

	public GTRACMG(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public GTRACMG() {
		super(WSDL_LOCATION, SERVICE);
	}

	public GTRACMG(WebServiceFeature ... features) {
		super(WSDL_LOCATION, SERVICE, features);
	}

	public GTRACMG(URL wsdlLocation, WebServiceFeature ... features) {
		super(wsdlLocation, SERVICE, features);
	}

	public GTRACMG(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
		super(wsdlLocation, serviceName, features);
	}

	@WebEndpoint(name = "GTRACMGSoapPort")
	public GTRACMGSoapPort getGTRACMGSoapPort() {
		return super.getPort(GTRACMGSoapPort, GTRACMGSoapPort.class);
	}

	@WebEndpoint(name = "GTRACMGSoapPort")
	public GTRACMGSoapPort getGTRACMGSoapPort(WebServiceFeature... features) {
		return super.getPort(GTRACMGSoapPort, GTRACMGSoapPort.class, features);
	}
}