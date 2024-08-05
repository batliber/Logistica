package uy.com.amensg.logistica.web.webservices.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.web.test.controleswebservice.ControlesWebService;
import uy.com.amensg.logistica.web.test.controleswebservice.ControlesWebServiceService;

@Path("/TestREST")
public class TestREST {

	@GET
	@Path("/testHTTPSRESTService")
	@Produces({ MediaType.APPLICATION_JSON })
	public String testHTTPSRESTService(@Context HttpServletRequest request) {
		String result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ControlesWebServiceService service = new ControlesWebServiceService();
				ControlesWebService port = service.getControlesWebServicePort();
				
				result = port.getSiguienteMidParaControlar();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}