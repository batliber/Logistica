package uy.com.amensg.logistica.webservices.external.agtracmg.aws.ancel.com;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(
	targetNamespace = "com.ancel.aws.agtracmg",
	name = "GTRACMGSoapPort"
)
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(
	parameterStyle = SOAPBinding.ParameterStyle.BARE
)
public interface GTRACMGSoapPort {

	@WebMethod(operationName = "Execute", action = "com.ancel.aws.agtracmgaction/AGTRACMG.Execute")
	@WebResult(name = "GTRACMG.ExecuteResponse", targetNamespace = "com.ancel.aws.agtracmg", partName = "parameters")
	public GTRACMGExecuteResponse execute(
		@WebParam(
			partName = "parameters", 
			name = "GTRACMG.Execute", 
			targetNamespace = "com.ancel.aws.agtracmg"
		)
		GTRACMGExecute parameters
	);
}