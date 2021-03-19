package uy.com.amensg.logistica.webservices.external.bicsa;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "ConsultaSoap", targetNamespace = "http://tempuri.org/")
@XmlSeeAlso({ ObjectFactory.class })
public interface ConsultaSoap {

	@WebMethod(operationName = "ObtenerPersona", action = "http://tempuri.org/ObtenerPersona")
	@RequestWrapper(
		localName = "ObtenerPersona", 
		targetNamespace = "http://tempuri.org/", 
		className = "org.tempuri.ObtenerPersona")
	@ResponseWrapper(
		localName = "ObtenerPersonaResponse", 
		targetNamespace = "http://tempuri.org/", 
		className = "org.tempuri.ObtenerPersonaResponse")
	@WebResult(
		name = "ObtenerPersonaResult", 
		targetNamespace = "http://tempuri.org/"
	)
	public Persona obtenerPersona(
		@WebParam(name = "IdTipoDoc", targetNamespace = "http://tempuri.org/") int idTipoDoc,
		@WebParam(name = "IdPaisDoc", targetNamespace = "http://tempuri.org/") java.lang.String idPaisDoc,
		@WebParam(name = "NroDoc", targetNamespace = "http://tempuri.org/") java.lang.String nroDoc,
		@WebParam(name = "cod_institucion", targetNamespace = "http://tempuri.org/") java.lang.String codInstitucion
	);

	@WebMethod(operationName = "ListadoPaises", action = "http://tempuri.org/ListadoPaises")
	@RequestWrapper(
		localName = "ListadoPaises", 
		targetNamespace = "http://tempuri.org/", 
		className = "org.tempuri.ListadoPaises"
	)
	@ResponseWrapper(
		localName = "ListadoPaisesResponse", 
		targetNamespace = "http://tempuri.org/", 
		className = "org.tempuri.ListadoPaisesResponse"
	)
	@WebResult(name = "ListadoPaisesResult", targetNamespace = "http://tempuri.org/")
	public ArrayOfPais listadoPaises();

	@WebMethod(operationName = "ObtenerPersona_PDF", action = "http://tempuri.org/ObtenerPersona_PDF")
	@RequestWrapper(
		localName = "ObtenerPersona_PDF", 
		targetNamespace = "http://tempuri.org/", 
		className = "org.tempuri.ObtenerPersonaPDF"
	)
	@ResponseWrapper(
		localName = "ObtenerPersona_PDFResponse", 
		targetNamespace = "http://tempuri.org/", 
		className = "org.tempuri.ObtenerPersonaPDFResponse"
	)
	@WebResult(
		name = "ObtenerPersona_PDFResult", 
		targetNamespace = "http://tempuri.org/"
	)
	public Persona obtenerPersonaPDF(
		@WebParam(name = "IdTipoDoc", targetNamespace = "http://tempuri.org/") int idTipoDoc,
		@WebParam(name = "IdPaisDoc", targetNamespace = "http://tempuri.org/") java.lang.String idPaisDoc,
		@WebParam(name = "NroDoc", targetNamespace = "http://tempuri.org/") java.lang.String nroDoc,
		@WebParam(name = "cod_institucion", targetNamespace = "http://tempuri.org/") java.lang.String codInstitucion
	);

	@WebMethod(operationName = "ListadoTipoDocumentos", action = "http://tempuri.org/ListadoTipoDocumentos")
	@RequestWrapper(
		localName = "ListadoTipoDocumentos", 
		targetNamespace = "http://tempuri.org/", 
		className = "org.tempuri.ListadoTipoDocumentos"
	)
	@ResponseWrapper(
		localName = "ListadoTipoDocumentosResponse", 
		targetNamespace = "http://tempuri.org/", 
		className = "org.tempuri.ListadoTipoDocumentosResponse"
	)
	@WebResult(
		name = "ListadoTipoDocumentosResult",
		targetNamespace = "http://tempuri.org/"
	)
	public ArrayOfTipoDocumento listadoTipoDocumentos();
}