package uy.com.amensg.logistica.web.test.controleswebservice;

import javax.xml.namespace.QName;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    private final static QName _ActualizarDatosControlResponse_QNAME = 
    	new QName("http://soap.webservices.logistica.amensg.com.uy/", "actualizarDatosControlResponse");
//    	new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosControlResponse");
    private final static QName _GetSiguienteMidParaControlar_QNAME = 
    	new QName("http://soap.webservices.logistica.amensg.com.uy/", "getSiguienteMidParaControlar");
//    	new QName("http://webservices.logistica.amensg.com.uy/", "getSiguienteMidParaControlar");
    private final static QName _ActualizarDatosControl_QNAME = 
    	new QName("http://soap.webservices.logistica.amensg.com.uy/", "actualizarDatosControl");
//    	new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosControl");
    private final static QName _GetSiguienteMidParaControlarResponse_QNAME = 
    	new QName("http://soap.webservices.logistica.amensg.com.uy/", "getSiguienteMidParaControlarResponse");
//    	new QName("http://webservices.logistica.amensg.com.uy/", "getSiguienteMidParaControlarResponse");

    public ObjectFactory() {
    
    }

    public GetSiguienteMidParaControlar createGetSiguienteMidParaControlar() {
        return new GetSiguienteMidParaControlar();
    }

    public ActualizarDatosControlResponse createActualizarDatosControlResponse() {
        return new ActualizarDatosControlResponse();
    }

    public GetSiguienteMidParaControlarResponse createGetSiguienteMidParaControlarResponse() {
        return new GetSiguienteMidParaControlarResponse();
    }

    public ActualizarDatosControl createActualizarDatosControl() {
        return new ActualizarDatosControl();
    }

    public JAXBElement<ActualizarDatosControlResponse> createActualizarDatosControlResponse(ActualizarDatosControlResponse value) {
        return new JAXBElement<ActualizarDatosControlResponse>(_ActualizarDatosControlResponse_QNAME, ActualizarDatosControlResponse.class, null, value);
    }

    public JAXBElement<GetSiguienteMidParaControlar> createGetSiguienteMidParaControlar(GetSiguienteMidParaControlar value) {
        return new JAXBElement<GetSiguienteMidParaControlar>(_GetSiguienteMidParaControlar_QNAME, GetSiguienteMidParaControlar.class, null, value);
    }

    public JAXBElement<ActualizarDatosControl> createActualizarDatosControl(ActualizarDatosControl value) {
        return new JAXBElement<ActualizarDatosControl>(_ActualizarDatosControl_QNAME, ActualizarDatosControl.class, null, value);
    }

    public JAXBElement<GetSiguienteMidParaControlarResponse> createGetSiguienteMidParaControlarResponse(GetSiguienteMidParaControlarResponse value) {
        return new JAXBElement<GetSiguienteMidParaControlarResponse>(_GetSiguienteMidParaControlarResponse_QNAME, GetSiguienteMidParaControlarResponse.class, null, value);
    }
}