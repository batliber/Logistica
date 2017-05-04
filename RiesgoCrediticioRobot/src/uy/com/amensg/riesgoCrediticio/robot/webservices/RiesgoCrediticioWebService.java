package uy.com.amensg.riesgoCrediticio.robot.webservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(targetNamespace = "http://webservices.logistica.amensg.com.uy/", name = "RiesgoCrediticioWebService")
@XmlSeeAlso({ObjectFactory.class})
public interface RiesgoCrediticioWebService {

    @WebMethod
    @RequestWrapper(
    	localName = "actualizarDatosRiesgoCrediticioACM", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/", 
    	className = "uy.com.amensg.logistica.webservices.actualizarDatosRiesgoCrediticioACM"
    )
    @ResponseWrapper(
    	localName = "actualizarDatosRiesgoCrediticioACMResponse", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/", 
    	className = "uy.com.amensg.logistica.webservices.actualizarDatosRiesgoCrediticioACMResponse"
    )
    public void actualizarDatosRiesgoCrediticioACM(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        java.lang.String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        java.lang.String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        java.lang.String arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        java.lang.String arg7,
        @WebParam(name = "arg8", targetNamespace = "")
        java.lang.String arg8,
        @WebParam(name = "arg9", targetNamespace = "")
        java.lang.String arg9,
        @WebParam(name = "arg10", targetNamespace = "")
        java.lang.String arg10,
        @WebParam(name = "arg11", targetNamespace = "")
        java.lang.String arg11
    );
    
    @WebMethod
    @RequestWrapper(
    	localName = "actualizarDatosRiesgoCrediticioBCU", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/", 
    	className = "uy.com.amensg.logistica.webservices.actualizarDatosRiesgoCrediticioBCU"
    )
    @ResponseWrapper(
    	localName = "actualizarDatosRiesgoCrediticioBCUResponse", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/", 
    	className = "uy.com.amensg.logistica.webservices.actualizarDatosRiesgoCrediticioBCUResponse"
    )
    public void actualizarDatosRiesgoCrediticioBCU(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        java.lang.String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        java.lang.String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        java.lang.String arg6,
        @WebParam(name = "arg7", targetNamespace = "")
        java.lang.String arg7,
        @WebParam(name = "arg8", targetNamespace = "")
        java.lang.String arg8,
        @WebParam(name = "arg9", targetNamespace = "")
        java.lang.String arg9,
        @WebParam(name = "arg10", targetNamespace = "")
        java.lang.String arg10,
        @WebParam(name = "arg11", targetNamespace = "")
        java.lang.String arg11,
        @WebParam(name = "arg12", targetNamespace = "")
        java.lang.String arg12
    );
    
    @WebMethod
    @RequestWrapper(
    	localName = "actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/", 
    	className = "uy.com.amensg.logistica.webservices.actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera"
    )
    @ResponseWrapper(
    	localName = "actualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/", 
    	className = "uy.com.amensg.logistica.webservices.actualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse"
    )
    public void actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1,
        @WebParam(name = "arg2", targetNamespace = "")
        java.lang.String arg2,
        @WebParam(name = "arg3", targetNamespace = "")
        java.lang.String arg3,
        @WebParam(name = "arg4", targetNamespace = "")
        java.lang.String arg4,
        @WebParam(name = "arg5", targetNamespace = "")
        java.lang.String arg5,
        @WebParam(name = "arg6", targetNamespace = "")
        java.lang.String arg6
    );

    @WebMethod
    @RequestWrapper(
    	localName = "getSiguienteDocumentoParaControlar", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/", 
    	className = "uy.com.amensg.logistica.webservices.GetSiguienteDocumentoParaControlar"
    )
    @ResponseWrapper(
    	localName = "getSiguienteDocumentoParaControlarResponse", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/", 
    	className = "uy.com.amensg.logistica.webservices.GetSiguienteDocumentoParaControlarrResponse"
    )
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String getSiguienteDocumentoParaControlar();
}