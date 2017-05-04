package uy.com.amensg.riesgoCrediticio.robot.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private final static QName _ActualizarDatosRiesgoCrediticioACM_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosRiesgoCrediticioACM");
    private final static QName _ActualizarDatosRiesgoCrediticioACMResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosRiesgoCrediticioACMResponse");
    private final static QName _ActualizarDatosRiesgoCrediticioBCU_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosRiesgoCrediticioBCU");
    private final static QName _ActualizarDatosRiesgoCrediticioBCUResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosRiesgoCrediticioBCUResponse");
    private final static QName _ActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera");
    private final static QName _ActualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse");
    private final static QName _GetSiguienteDocumentoParaControlar_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "getSiguienteDocumentoParaControlar");
    private final static QName _GetSiguienteDocumentoParaControlarResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "getSiguienteDocumentoParaControlarResponse");

    public ObjectFactory() {
    	
    }

    public ActualizarDatosRiesgoCrediticioACM createActualizarDatosRiesgoCrediticioACM() {
        return new ActualizarDatosRiesgoCrediticioACM();
    }

    public ActualizarDatosRiesgoCrediticioACMResponse createActualizarDatosRiesgoCrediticioACMResponse() {
        return new ActualizarDatosRiesgoCrediticioACMResponse();
    }
    
    public ActualizarDatosRiesgoCrediticioBCU createActualizarDatosRiesgoCrediticioBCU() {
        return new ActualizarDatosRiesgoCrediticioBCU();
    }

    public ActualizarDatosRiesgoCrediticioBCUResponse createActualizarDatosRiesgoCrediticioBCUResponse() {
        return new ActualizarDatosRiesgoCrediticioBCUResponse();
    }
    
    public ActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera createActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera() {
        return new ActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera();
    }

    public ActualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse createActualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse() {
        return new ActualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse();
    }

    public GetSiguienteDocumentoParaControlar createGetSiguienteDocumentoParaControlar() {
        return new GetSiguienteDocumentoParaControlar();
    }

    public GetSiguienteDocumentoParaControlarResponse createGetSiguienteDocumentoParaControlarResponse() {
        return new GetSiguienteDocumentoParaControlarResponse();
    }

    @XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosRiesgoCrediticioACM")
    public JAXBElement<ActualizarDatosRiesgoCrediticioACM> createActualizarDatosRiesgoCrediticioACM(ActualizarDatosRiesgoCrediticioACM value) {
        return new JAXBElement<ActualizarDatosRiesgoCrediticioACM>(_ActualizarDatosRiesgoCrediticioACM_QNAME, ActualizarDatosRiesgoCrediticioACM.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosRiesgoCrediticioACMResponse")
    public JAXBElement<ActualizarDatosRiesgoCrediticioACMResponse> createActualizarDatosRiesgoCrediticioACMResponse(ActualizarDatosRiesgoCrediticioACMResponse value) {
        return new JAXBElement<ActualizarDatosRiesgoCrediticioACMResponse>(_ActualizarDatosRiesgoCrediticioACMResponse_QNAME, ActualizarDatosRiesgoCrediticioACMResponse.class, null, value);
    }
    
    @XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosRiesgoCrediticioBCU")
    public JAXBElement<ActualizarDatosRiesgoCrediticioBCU> createActualizarDatosRiesgoCrediticioBCU(ActualizarDatosRiesgoCrediticioBCU value) {
        return new JAXBElement<ActualizarDatosRiesgoCrediticioBCU>(_ActualizarDatosRiesgoCrediticioBCU_QNAME, ActualizarDatosRiesgoCrediticioBCU.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosRiesgoCrediticioBCUResponse")
    public JAXBElement<ActualizarDatosRiesgoCrediticioBCUResponse> createActualizarDatosRiesgoCrediticioBCUResponse(ActualizarDatosRiesgoCrediticioBCUResponse value) {
        return new JAXBElement<ActualizarDatosRiesgoCrediticioBCUResponse>(_ActualizarDatosRiesgoCrediticioBCUResponse_QNAME, ActualizarDatosRiesgoCrediticioBCUResponse.class, null, value);
    }
    
    @XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera")
    public JAXBElement<ActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera> createActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(ActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera value) {
        return new JAXBElement<ActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera>(_ActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera_QNAME, ActualizarDatosRiesgoCrediticioBCUInstitucionFinanciera.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse")
    public JAXBElement<ActualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse> createActualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse(ActualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse value) {
        return new JAXBElement<ActualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse>(_ActualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse_QNAME, ActualizarDatosRiesgoCrediticioBCUInstitucionFinancieraResponse.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "getSiguienteDocumentoParaControlar")
    public JAXBElement<GetSiguienteDocumentoParaControlar> createGetSiguienteDocumentoParaControlar(GetSiguienteDocumentoParaControlar value) {
        return new JAXBElement<GetSiguienteDocumentoParaControlar>(_GetSiguienteDocumentoParaControlar_QNAME, GetSiguienteDocumentoParaControlar.class, null, value);
    }

    @XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "getSiguienteDocumentoParaControlarResponse")
    public JAXBElement<GetSiguienteDocumentoParaControlarResponse> createGetSiguienteDocumentoParaControlarResponse(GetSiguienteDocumentoParaControlarResponse value) {
        return new JAXBElement<GetSiguienteDocumentoParaControlarResponse>(_GetSiguienteDocumentoParaControlarResponse_QNAME, GetSiguienteDocumentoParaControlarResponse.class, null, value);
    }
}