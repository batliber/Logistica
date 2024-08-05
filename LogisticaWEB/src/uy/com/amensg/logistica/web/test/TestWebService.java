package uy.com.amensg.logistica.web.test;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService(
	name="TestService",
	serviceName = "TestService"
)
@SOAPBinding(
	parameterStyle = SOAPBinding.ParameterStyle.BARE
)
public class TestWebService {

	@WebMethod
	public String test() {
		return "Hello world";
	}
}