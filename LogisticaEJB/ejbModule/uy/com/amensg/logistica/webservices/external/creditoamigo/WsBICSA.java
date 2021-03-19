package uy.com.amensg.logistica.webservices.external.creditoamigo;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

@WebServiceClient(
	name = "wsBICSA",
	wsdlLocation = "http://creditoamigo.systemmaster.com.ar/wsBICSA.asmx?wsdl",
	targetNamespace = "http://tempuri.org/"
)
public class WsBICSA extends Service {

	public final static URL WSDL_LOCATION;

	public final static QName SERVICE = new QName("http://tempuri.org/", "wsBICSA");
	public final static QName WsBICSASoap12 = new QName("http://tempuri.org/", "wsBICSASoap12");
	public final static QName WsBICSASoap = new QName("http://tempuri.org/", "wsBICSASoap");
	static {
		URL url = null;
		try {
			url = new URL("http://creditoamigo.systemmaster.com.ar/wsBICSA.asmx?wsdl");
		} catch (MalformedURLException e) {
			java.util.logging.Logger.getLogger(WsBICSA.class.getName())
				.log(java.util.logging.Level.INFO,
					"Can not initialize the default wsdl from {0}", "http://creditoamigo.systemmaster.com.ar/wsBICSA.asmx?wsdl"
				);
		}
		WSDL_LOCATION = url;
	}

	public WsBICSA(URL wsdlLocation) {
		super(wsdlLocation, SERVICE);
	}

	public WsBICSA(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public WsBICSA() {
		super(WSDL_LOCATION, SERVICE);
	}

	public WsBICSA(WebServiceFeature ... features) {
		super(WSDL_LOCATION, SERVICE, features);
	}

	public WsBICSA(URL wsdlLocation, WebServiceFeature ... features) {
		super(wsdlLocation, SERVICE, features);
	}

	public WsBICSA(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
		super(wsdlLocation, serviceName, features);
	}

	@WebEndpoint(name = "wsBICSASoap12")
	public WsBICSASoap getWsBICSASoap12() {
		return super.getPort(WsBICSASoap12, WsBICSASoap.class);
	}

	@WebEndpoint(name = "wsBICSASoap12")
	public WsBICSASoap getWsBICSASoap12(WebServiceFeature... features) {
		return super.getPort(WsBICSASoap12, WsBICSASoap.class, features);
	}

	@WebEndpoint(name = "wsBICSASoap")
	public WsBICSASoap getWsBICSASoap() {
		return super.getPort(WsBICSASoap, WsBICSASoap.class);
	}

	@WebEndpoint(name = "wsBICSASoap")
	public WsBICSASoap getWsBICSASoap(WebServiceFeature... features) {
		return super.getPort(WsBICSASoap, WsBICSASoap.class, features);
	}
}