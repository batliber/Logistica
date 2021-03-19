package uy.com.amensg.logistica.webservices.external.creditoamigo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(
	targetNamespace = "http://tempuri.org/", 
	name = "wsBICSASoap"
)
@XmlSeeAlso(
	{ObjectFactory.class}
)
public interface WsBICSASoap {

	@WebMethod(operationName = "BicsaAdd", action = "http://tempuri.org/BicsaAdd")
	@RequestWrapper(
		localName = "BicsaAdd", 
		targetNamespace = "http://tempuri.org/", 
		className = "uy.com.amensg.logistica.webservices.external.creditoamigo.BicsaAdd"
	)
	@ResponseWrapper(
		localName = "BicsaAddResponse", 
		targetNamespace = "http://tempuri.org/", 
		className = "uy.com.amensg.logistica.webservices.external.creditoamigo.BicsaAddResponse"
	)
	@WebResult(name = "BicsaAddResult", targetNamespace = "http://tempuri.org/")
	public java.lang.String bicsaAdd(
		@WebParam(name = "DocumentoNro", targetNamespace = "http://tempuri.org/")
		int documentoNro,
		@WebParam(name = "JSon", targetNamespace = "http://tempuri.org/")
		java.lang.String jSon
	);

	@WebMethod(operationName = "HelloWorld", action = "http://tempuri.org/HelloWorld")
	@RequestWrapper(
		localName = "HelloWorld", 
		targetNamespace = "http://tempuri.org/", 
		className = "uy.com.amensg.logistica.webservices.external.creditoamigo.HelloWorld"
	)
	@ResponseWrapper(
		localName = "HelloWorldResponse", 
		targetNamespace = "http://tempuri.org/", 
		className = "uy.com.amensg.logistica.webservices.external.creditoamigo.HelloWorldResponse"
	)
	@WebResult(name = "HelloWorldResult", targetNamespace = "http://tempuri.org/")
	public java.lang.String helloWorld();
}
