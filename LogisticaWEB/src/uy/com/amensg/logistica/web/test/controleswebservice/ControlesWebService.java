package uy.com.amensg.logistica.web.test.controleswebservice;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;

@WebService(
	name = "ControlesWebService", 
//	targetNamespace = "http://soap.webservices.logistica.amensg.com.uy/"
	targetNamespace = "http://webservices.logistica.amensg.com.uy/"
)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ControlesWebService {

    @WebMethod
    @RequestWrapper(
    	localName = "actualizarDatosControl", 
//    	targetNamespace = "http://soap.webservices.logistica.amensg.com.uy/", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/",
    	className = "uy.com.amensg.logistica.webservices.ActualizarDatosControl"
    )
    @ResponseWrapper(
    	localName = "actualizarDatosControlResponse", 
//    	targetNamespace = "http://soap.webservices.logistica.amensg.com.uy/", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/",
    	className = "uy.com.amensg.logistica.webservices.ActualizarDatosControlResponse"
    )
    public void actualizarDatosControl(
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
        String arg9
    );

    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(
    	localName = "getSiguienteMidParaControlar", 
//    	targetNamespace = "http://soap.webservices.logistica.amensg.com.uy/", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/",
    	className = "uy.com.amensg.logistica.webservices.GetSiguienteMidParaControlar"
    )
    @ResponseWrapper(
    	localName = "getSiguienteMidParaControlarResponse", 
//    	targetNamespace = "http://soap.webservices.logistica.amensg.com.uy/", 
    	targetNamespace = "http://webservices.logistica.amensg.com.uy/",
    	className = "uy.com.amensg.logistica.webservices.GetSiguienteMidParaControlarResponse"
    )
    public String getSiguienteMidParaControlar();
}