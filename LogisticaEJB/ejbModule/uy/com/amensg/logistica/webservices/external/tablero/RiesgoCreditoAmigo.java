package uy.com.amensg.logistica.webservices.external.tablero;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(
	name = "RiesgoCreditoAmigo",
	targetNamespace = "TABLERO",
	wsdlLocation = "http://www.gisa.com.py:1010/creditoamigo/ariesgocreditoamigo.aspx?wsdl"
)
public class RiesgoCreditoAmigo extends Service {

	private final static URL RIESGOCREDITOAMIGO_WSDL_LOCATION;
	private final static WebServiceException RIESGOCREDITOAMIGO_EXCEPTION;
	private final static QName RIESGOCREDITOAMIGO_QNAME = new QName("TABLERO", "RiesgoCreditoAmigo");

	static {
		URL url = null;
		WebServiceException e = null;
		try {
			url = new URL("http://www.gisa.com.py:1010/creditoamigo/ariesgocreditoamigo.aspx?wsdl");
		} catch (MalformedURLException ex) {
			e = new WebServiceException(ex);
		}
		RIESGOCREDITOAMIGO_WSDL_LOCATION = url;
		RIESGOCREDITOAMIGO_EXCEPTION = e;
	}

	public RiesgoCreditoAmigo() {
		super(__getWsdlLocation(), RIESGOCREDITOAMIGO_QNAME);
	}

	public RiesgoCreditoAmigo(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public RiesgoCreditoAmigoSoapPort getRiesgoCreditoAmigoSoapPort() {
		return super.getPort(
			new QName("TABLERO", "RiesgoCreditoAmigoSoapPort"), RiesgoCreditoAmigoSoapPort.class
		);
	}

	public RiesgoCreditoAmigoSoapPort getRiesgoCreditoAmigoSoapPort(WebServiceFeature... features) {
		return super.getPort(
			new QName("TABLERO", "RiesgoCreditoAmigoSoapPort"), RiesgoCreditoAmigoSoapPort.class, features
		);
	}

	private static URL __getWsdlLocation() {
		if (RIESGOCREDITOAMIGO_EXCEPTION != null) {
			throw RIESGOCREDITOAMIGO_EXCEPTION;
		}
		return RIESGOCREDITOAMIGO_WSDL_LOCATION;
	}
}