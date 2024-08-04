package uy.com.amensg.logistica.test;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(
	name="TestService",
	targetNamespace="http://tosteaun.no-ip.biz",
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