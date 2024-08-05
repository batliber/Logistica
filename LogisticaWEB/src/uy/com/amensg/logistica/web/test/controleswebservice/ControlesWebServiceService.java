package uy.com.amensg.logistica.web.test.controleswebservice;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceFeature;

@WebServiceClient(
	name = "ControlesWebServiceService", 
//	targetNamespace = "http://soap.webservices.logistica.amensg.com.uy/",
	targetNamespace = "http://webservices.logistica.amensg.com.uy/",
//	wsdlLocation = "https://www.amensg.com:8443/LogisticaWEB/ControlesWebService?wsdl"
	wsdlLocation = "https://www.uruentregas.com.uy:8444/LogisticaWEB/ControlesWebService?wsdl"
)
public class ControlesWebServiceService extends Service {

    private final static URL CONTROLESWEBSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException CONTROLESWEBSERVICESERVICE_EXCEPTION;
    private final static QName CONTROLESWEBSERVICESERVICE_QNAME = 
//    	new QName("http://soap.webservices.logistica.amensg.com.uy/", "ControlesWebServiceService");
    	new QName("http://webservices.logistica.amensg.com.uy/", "ControlesWebServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
//            url = new URL("https://www.amensg.com:8443/LogisticaWEB/ControlesWebService?wsdl");
        	url = new URL("https://www.uruentregas.com.uy:8444/LogisticaWEB/ControlesWebService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CONTROLESWEBSERVICESERVICE_WSDL_LOCATION = url;
        CONTROLESWEBSERVICESERVICE_EXCEPTION = e;
    }
    
    public ControlesWebServiceService() {
        super(__getWsdlLocation(), CONTROLESWEBSERVICESERVICE_QNAME);
    }
    
    public ControlesWebServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CONTROLESWEBSERVICESERVICE_QNAME, features);
    }
    
    public ControlesWebServiceService(URL wsdlLocation) {
        super(wsdlLocation, CONTROLESWEBSERVICESERVICE_QNAME);
    }
    
    public ControlesWebServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CONTROLESWEBSERVICESERVICE_QNAME, features);
    }
    
    public ControlesWebServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }
    
    public ControlesWebServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }
    
    public ControlesWebService getControlesWebServicePort() {
        return super.getPort(
        	new QName(
//        		"http://soap.webservices.logistica.amensg.com.uy/",
        		"http://webservices.logistica.amensg.com.uy/",
        		"ControlesWebServicePort"
        	), 
        	ControlesWebService.class
        );
    }

    public ControlesWebService getControlesWebServicePort(WebServiceFeature... features) {
        return super.getPort(
        	new QName(
//        		"http://soap.webservices.logistica.amensg.com.uy/", 
        		"http://webservices.logistica.amensg.com.uy/",
        		"ControlesWebServicePort"
        	), 
        	ControlesWebService.class, 
        	features
        );
    }

    private static URL __getWsdlLocation() {
        if (CONTROLESWEBSERVICESERVICE_EXCEPTION!= null) {
            throw CONTROLESWEBSERVICESERVICE_EXCEPTION;
        }
        return CONTROLESWEBSERVICESERVICE_WSDL_LOCATION;
    }
}