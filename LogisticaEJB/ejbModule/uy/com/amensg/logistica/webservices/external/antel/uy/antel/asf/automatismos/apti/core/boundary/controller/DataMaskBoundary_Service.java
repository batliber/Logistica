package uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import uy.com.amensg.logistica.util.Configuration;

@WebServiceClient(
	name = "DataMaskBoundary", 
	targetNamespace = "http://controller.boundary.core.apti.automatismos.asf.antel.uy/", 
	wsdlLocation = "file:/home/amensg/ANTEL/antel_import.wsdl"
)
public class DataMaskBoundary_Service extends Service {
	
	private final static URL DATAMASKBOUNDARY_WSDL_LOCATION;
	private final static WebServiceException DATAMASKBOUNDARY_EXCEPTION;
	private final static QName DATAMASKBOUNDARY_QNAME = 
		new QName("http://controller.boundary.core.apti.automatismos.asf.antel.uy/", "DataMaskBoundary");

	static {
		URL url = null;
		WebServiceException e = null;
		try {
//			url = new URL("https://wsi-prep.in.iantel.com.uy/apti/servicios/DataMaskBoundary/DataMaskBoundary?wsdl");
//			url = new URL("https://wsi-prod.in.iantel.com.uy/apti/servicios/DataMaskBoundary/DataMaskBoundary?wsdl");
			url = 
				new URL(
					"file:" + Configuration.getInstance().getProperty("antel.DataMaskBoundary.wsdllocalfilepath")
				);
		} catch (MalformedURLException ex) {
			e = new WebServiceException(ex);
		}
		DATAMASKBOUNDARY_WSDL_LOCATION = url;
		DATAMASKBOUNDARY_EXCEPTION = e;
	}

	public DataMaskBoundary_Service() {
		super(__getWsdlLocation(), DATAMASKBOUNDARY_QNAME);
	}

	public DataMaskBoundary_Service(WebServiceFeature... features) {
		super(__getWsdlLocation(), DATAMASKBOUNDARY_QNAME, features);
	}

	public DataMaskBoundary_Service(URL wsdlLocation) {
		super(wsdlLocation, DATAMASKBOUNDARY_QNAME);
	}

	public DataMaskBoundary_Service(URL wsdlLocation, WebServiceFeature... features) {
		super(wsdlLocation, DATAMASKBOUNDARY_QNAME, features);
	}

	public DataMaskBoundary_Service(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public DataMaskBoundary_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
		super(wsdlLocation, serviceName, features);
	}

	@WebEndpoint(name = "DataMaskBoundaryPort")
	public DataMaskBoundary getDataMaskBoundaryPort() {
		return super.getPort(
			new QName("http://controller.boundary.core.apti.automatismos.asf.antel.uy/", "DataMaskBoundaryPort"), 
			DataMaskBoundary.class
		);
	}

	@WebEndpoint(name = "DataMaskBoundaryPort")
	public DataMaskBoundary getDataMaskBoundaryPort(WebServiceFeature... features) {
		return super.getPort(
			new QName("http://controller.boundary.core.apti.automatismos.asf.antel.uy/", "DataMaskBoundaryPort"), 
			DataMaskBoundary.class, features
		);
	}

	private static URL __getWsdlLocation() {
		if (DATAMASKBOUNDARY_EXCEPTION!= null) {
			throw DATAMASKBOUNDARY_EXCEPTION;
		}
		return DATAMASKBOUNDARY_WSDL_LOCATION;
	}
}