package uy.com.amensg.logistica.web.test;

import uy.com.amensg.logistica.web.test.controleswebservice.ControlesWebService;
import uy.com.amensg.logistica.web.test.controleswebservice.ControlesWebServiceService;

public class TestHTTPSService {

	public TestHTTPSService() {
		ControlesWebServiceService service = new ControlesWebServiceService();
		ControlesWebService port = service.getControlesWebServicePort();
		
		port.getSiguienteMidParaControlar();
	}
	
	public static void main(String[] args) {
		new TestHTTPSService();
	}
}