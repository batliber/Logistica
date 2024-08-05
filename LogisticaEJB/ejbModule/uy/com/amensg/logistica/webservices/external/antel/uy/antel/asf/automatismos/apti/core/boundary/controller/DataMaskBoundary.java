package uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.controller;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.DataMask;
import uy.com.amensg.logistica.webservices.external.antel.uy.antel.asf.automatismos.apti.core.boundary.ObjectFactory;

@WebService(
	name = "DataMaskBoundary", 
	targetNamespace = "http://boundary.core.apti.automatismos.asf.antel.uy/"
)
@XmlSeeAlso({
	ObjectFactory.class
})
public interface DataMaskBoundary {

	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(
		localName = "getDataMask", 
		targetNamespace = "http://boundary.core.apti.automatismos.asf.antel.uy/", 
		className = "uy.antel.asf.automatismos.apti.core.boundary.GetDataMask"
	)
	@ResponseWrapper(
		localName = "getDataMaskResponse", 
		targetNamespace = "http://boundary.core.apti.automatismos.asf.antel.uy/", 
		className = "uy.antel.asf.automatismos.apti.core.boundary.GetDataMaskResponse"
	)
	public DataMask getDataMask(
		@WebParam(name = "token", targetNamespace = "")
		String token
	);

	public int addDataMask(
		@WebParam(name = "token", targetNamespace = "")
		String token,
		@WebParam(name = "serial", targetNamespace = "")
		String serial,
		@WebParam(name = "phoneNumber", targetNamespace = "")
		String phoneNumber,
		@WebParam(name = "idTypeData", targetNamespace = "")
		int idTypeData,
		@WebParam(name = "info", targetNamespace = "")
		String info
	);
}