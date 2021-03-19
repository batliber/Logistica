package uy.com.amensg.logistica.webservices.external.bicsa;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(
	name = "Consulta", 
	targetNamespace = "http://tempuri.org/",
//	wsdlLocation = "file:/home/amensg/git/Logistica/LogisticaEJB/ejbModule/uy/com/amensg/logistica/webservices/external/bicsa/WS-Consulta-Test.WSDL"
//	wsdlLocation = "http://wscbc.bicsa.com.py/WS_V2_1/Consulta.asmx?wsdl"
//	wsdlLocation = "http://carga.bicsa.com.py/WS_Consulta_V2_1/Consulta.asmx?wsdl"
	wsdlLocation = "http://carga.bicsa.com.py/WS_Consulta_V2_2/Consulta.asmx?wsdl"
)
public class Consulta extends Service {

	public final static URL WSDL_LOCATION;
	private final static WebServiceException CONSULTA_EXCEPTION;
	public final static QName SERVICE = new QName("http://tempuri.org/", "Consulta");
	public final static QName ConsultaSoap12 = new QName("http://tempuri.org/", "ConsultaSoap12");
	public final static QName ConsultaSoap = new QName("http://tempuri.org/", "ConsultaSoap");
	
	static {
		URL url = null;
		WebServiceException e = null;
		try {
//			url = new URL(
//				"file:/home/amensg/git/Logistica/LogisticaEJB/ejbModule/uy/com/amensg/logistica/webservices/external/bicsa/WS-Consulta-Test.WSDL"
//			);
//			url = new URL(
//				"http://wscbc.bicsa.com.py/WS_V2_1/Consulta.asmx?wsdl"
//			);
			url = new URL(
				"http://carga.bicsa.com.py/WS_Consulta_V2_2/Consulta.asmx?wsdl"
			);
		} catch (MalformedURLException ex) {
			e = new WebServiceException(ex);
		}
		WSDL_LOCATION = url;
		CONSULTA_EXCEPTION = e;
	}

	public Consulta(URL wsdlLocation) {
		super(__getWsdlLocation(), SERVICE);
	}

	public Consulta(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public Consulta() {
		super(WSDL_LOCATION, SERVICE);
	}

	public Consulta(WebServiceFeature... features) {
		super(WSDL_LOCATION, SERVICE, features);
	}

	public Consulta(URL wsdlLocation, WebServiceFeature... features) {
		super(wsdlLocation, SERVICE, features);
	}

	public Consulta(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
		super(wsdlLocation, serviceName, features);
	}

	@WebEndpoint(name = "ConsultaSoap12")
	public ConsultaSoap getConsultaSoap12() {
		return super.getPort(ConsultaSoap12, ConsultaSoap.class);
	}

	@WebEndpoint(name = "ConsultaSoap12")
	public ConsultaSoap getConsultaSoap12(WebServiceFeature... features) {
		return super.getPort(ConsultaSoap12, ConsultaSoap.class, features);
	}

	@WebEndpoint(name = "ConsultaSoap")
	public ConsultaSoap getConsultaSoap() {
		return super.getPort(ConsultaSoap, ConsultaSoap.class);
	}

	@WebEndpoint(name = "ConsultaSoap")
	public ConsultaSoap getConsultaSoap(WebServiceFeature... features) {
		return super.getPort(ConsultaSoap, ConsultaSoap.class, features);
	}

	private static URL __getWsdlLocation() {
		if (CONSULTA_EXCEPTION != null) {
			throw CONSULTA_EXCEPTION;
		}
		return WSDL_LOCATION;
	}
}