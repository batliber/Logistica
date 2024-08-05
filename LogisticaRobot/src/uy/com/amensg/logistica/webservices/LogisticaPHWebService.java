package uy.com.amensg.logistica.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(
	targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
	name = "LogisticaPHWebService"
)
@XmlSeeAlso({ObjectFactory.class})
public interface LogisticaPHWebService {

	@WebMethod
	@RequestWrapper(
		localName = "getSiguienteMidSinProcesar", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.GetSiguienteMidSinProcesar"
	)
	@ResponseWrapper(
		localName = "getSiguienteMidSinProcesarResponse", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.GetSiguienteMidSinProcesarResponse"
	)
	@WebResult(name = "return", targetNamespace = "")
	public String getSiguienteMidSinProcesar();
	
	@WebMethod
	@RequestWrapper(
		localName = "getSiguienteNumeroContratoSinProcesar", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.GetSiguienteNumeroContratoSinProcesar"
	)
	@ResponseWrapper(
		localName = "getSiguienteNumeroContratoSinProcesarResponse", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.GetSiguienteNumeroContratoSinProcesarResponse"
	)
	@WebResult(name = "return", targetNamespace = "")
	public String getSiguienteNumeroContratoSinProcesar();
	
	@WebMethod
	@RequestWrapper(
		localName = "actualizarDatosMidContrato", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidContrato"
	)
	@ResponseWrapper(
		localName = "actualizarDatosMidContratoResponse", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidContratoResponse"
	)
	public void actualizarDatosMidContrato(
		@WebParam(name = "arg0", targetNamespace = "")
		String arg0,
		@WebParam(name = "arg1", targetNamespace = "")
		String arg1,
		@WebParam(name = "arg2", targetNamespace = "")
		String arg2,
		@WebParam(name = "arg3", targetNamespace = "")
		String arg3,
		@WebParam(name = "arg4", targetNamespace = "")
		String arg4,
		@WebParam(name = "arg5", targetNamespace = "")
		String arg5,
		@WebParam(name = "arg6", targetNamespace = "")
		String arg6,
		@WebParam(name = "arg7", targetNamespace = "")
		String arg7,
		@WebParam(name = "arg8", targetNamespace = "")
		String arg8,
		@WebParam(name = "arg9", targetNamespace = "")
		String arg9,
		@WebParam(name = "arg10", targetNamespace = "")
		String arg10,
		@WebParam(name = "arg11", targetNamespace = "")
		String arg11,
		@WebParam(name = "arg12", targetNamespace = "")
		String arg12,
		@WebParam(name = "arg13", targetNamespace = "")
		String arg13,
		@WebParam(name = "arg14", targetNamespace = "")
		String arg14
	);
	
	@WebMethod
	@RequestWrapper(
		localName = "actualizarDatosMidPrepago", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidPrepago"
	)
	@ResponseWrapper(
		localName = "actualizarDatosMidPrepagoResponse", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidPrepagoResponse"
	)
	public void actualizarDatosMidPrepago(
		@WebParam(name = "arg0", targetNamespace = "")
		String arg0,
		@WebParam(name = "arg1", targetNamespace = "")
		String arg1,
		@WebParam(name = "arg2", targetNamespace = "")
		String arg2,
		@WebParam(name = "arg3", targetNamespace = "")
		String arg3,
		@WebParam(name = "arg4", targetNamespace = "")
		String arg4,
		@WebParam(name = "arg5", targetNamespace = "")
		String arg5,
		@WebParam(name = "arg6", targetNamespace = "")
		String arg6
	);

	@WebMethod
	@RequestWrapper(
		localName = "actualizarDatosMidListaVacia", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidListaVacia"
	)
	@ResponseWrapper(
		localName = "actualizarDatosMidListaVaciaResponse", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidListaVaciaResponse"
	)
	public void actualizarDatosMidListaVacia(
		@WebParam(name = "arg0", targetNamespace = "")
		String arg0
	);
	
	@WebMethod
	@RequestWrapper(
		localName = "actualizarDatosMidListaNegra", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidListaNegra"
	)
	@ResponseWrapper(
		localName = "actualizarDatosMidListaNegraResponse", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidListaNegraResponse"
	)
	public void actualizarDatosMidListaNegra(
		@WebParam(name = "arg0", targetNamespace = "")
		String arg0
	);
	
	@WebMethod
	@RequestWrapper(
		localName = "actualizarDatosMidNegociando", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidNegociando"
	)
	@ResponseWrapper(
		localName = "actualizarDatosMidNegociandoResponse", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidNegociandoResponse"
	)
	public void actualizarDatosMidNegociando(
		@WebParam(name = "arg0", targetNamespace = "")
		String arg0
	);
	
	@WebMethod
	@RequestWrapper(
		localName = "actualizarDatosMidNoLlamar", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidNoLlamar"
	)
	@ResponseWrapper(
		localName = "actualizarDatosMidNoLlamarResponse", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosMidNoLlamarResponse"
	)
	public void actualizarDatosMidNoLlamar(
		@WebParam(name = "arg0", targetNamespace = "")
		String arg0
	);
	
	@WebMethod
	@RequestWrapper(
		localName = "actualizarDatosNumeroContratoListaVacia", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosNumeroContratoListaVacia"
	)
	@ResponseWrapper(
		localName = "actualizarDatosNumeroContratoListaVaciaResponse", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosNumeroContratoListaVaciaResponse"
	)
	public void actualizarDatosNumeroContratoListaVacia(
		@WebParam(name = "arg0", targetNamespace = "")
		String arg0
	);

	@WebMethod
	@RequestWrapper(
		localName = "actualizarDatosPersona", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosPersona"
	)
	@ResponseWrapper(
		localName = "actualizarDatosPersonaResponse", 
		targetNamespace = "http://soap.webservices.web.logistica.amensg.com.uy/", 
		className = "uy.com.amensg.logistica.webservices.ActualizarDatosPersonaResponse"
	)
	public void actualizarDatosPersona(
		@WebParam(name = "arg0", targetNamespace = "")
		String arg0,
		@WebParam(name = "arg1", targetNamespace = "")
		String arg1,
		@WebParam(name = "arg2", targetNamespace = "")
		String arg2,
		@WebParam(name = "arg3", targetNamespace = "")
		String arg3,
		@WebParam(name = "arg4", targetNamespace = "")
		String arg4,
		@WebParam(name = "arg5", targetNamespace = "")
		String arg5,
		@WebParam(name = "arg6", targetNamespace = "")
		String arg6,
		@WebParam(name = "arg7", targetNamespace = "")
		String arg7,
		@WebParam(name = "arg8", targetNamespace = "")
		String arg8,
		@WebParam(name = "arg9", targetNamespace = "")
		String arg9,
		@WebParam(name = "arg10", targetNamespace = "")
		String arg10,
		@WebParam(name = "arg11", targetNamespace = "")
		String arg11,
		@WebParam(name = "arg12", targetNamespace = "")
		String arg12, 
		@WebParam(name = "arg13", targetNamespace = "")
		String arg13,
		@WebParam(name = "arg14", targetNamespace = "")
		String arg14,
		@WebParam(name = "arg15", targetNamespace = "")
		String arg15,
		@WebParam(name = "arg16", targetNamespace = "")
		String arg16,
		@WebParam(name = "arg17", targetNamespace = "")
		String arg17,
		@WebParam(name = "arg18", targetNamespace = "")
		String arg18,
		@WebParam(name = "arg19", targetNamespace = "")
		String arg19,
		@WebParam(name = "arg20", targetNamespace = "")
		String arg20,
		@WebParam(name = "arg21", targetNamespace = "")
		String arg21,
		@WebParam(name = "arg22", targetNamespace = "")
		String arg22,
		@WebParam(name = "arg23", targetNamespace = "")
		String arg23,
		@WebParam(name = "arg24", targetNamespace = "")
		String arg24,
		@WebParam(name = "arg25", targetNamespace = "")
		String arg25,
		@WebParam(name = "arg26", targetNamespace = "")
		String arg26,
		@WebParam(name = "arg27", targetNamespace = "")
		String arg27,
		@WebParam(name = "arg28", targetNamespace = "")
		String arg28
	);
}