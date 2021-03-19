package uy.com.amensg.logistica.webservices.external.tablero;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "RiesgoCreditoAmigoSoapPort", targetNamespace = "TABLERO")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ ObjectFactory.class })
public interface RiesgoCreditoAmigoSoapPort {

	@WebMethod(operationName = "RIESGOBCU", action = "TABLEROaction/ARIESGOCREDITOAMIGO.RIESGOBCU")
	@WebResult(
		name = "RiesgoCreditoAmigo.RIESGOBCUResponse", targetNamespace = "TABLERO", partName = "parameters"
	)
	public RiesgoCreditoAmigoRIESGOBCUResponse riesgobcu(
		@WebParam(
			name = "RiesgoCreditoAmigo.RIESGOBCU", targetNamespace = "TABLERO", partName = "parameters"
		) RiesgoCreditoAmigoRIESGOBCU parameters
	);

	@WebMethod(operationName = "STATUSBCU", action = "TABLEROaction/ARIESGOCREDITOAMIGO.STATUSBCU")
	@WebResult(
		name = "RiesgoCreditoAmigo.STATUSBCUResponse", targetNamespace = "TABLERO", partName = "parameters"
	)
	public RiesgoCreditoAmigoSTATUSBCUResponse statusbcu(
		@WebParam(
			name = "RiesgoCreditoAmigo.STATUSBCU", targetNamespace = "TABLERO", partName = "parameters"
		) RiesgoCreditoAmigoSTATUSBCU parameters
	);
}