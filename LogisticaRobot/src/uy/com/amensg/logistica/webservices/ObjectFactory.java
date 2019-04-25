package uy.com.amensg.logistica.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

	private final static QName _ActualizarDatosMidContrato_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosMidContrato");
	private final static QName _ActualizarDatosMidContratoResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosMidContratoResponse");
	private final static QName _ActualizarDatosMidListaVacia_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosMidListaVacia");
	private final static QName _ActualizarDatosMidListaVaciaResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosMidListaVaciaResponse");
	private final static QName _ActualizarDatosMidPrepago_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosMidPrepago");
	private final static QName _ActualizarDatosMidPrepagoResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosMidPrepagoResponse");
	private final static QName _ActualizarDatosNumeroContratoListaVacia_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosNumeroContratoListaVacia");
	private final static QName _ActualizarDatosNumeroContratoListaVaciaResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosNumeroContratoListaVaciaResponse");
	private final static QName _ActualizarDatosPersona_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosPersona");
	private final static QName _ActualizarDatosPersonaResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "actualizarDatosPersonaResponse");
	private final static QName _GetSiguienteMidSinProcesar_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "getSiguienteMidSinProcesar");
	private final static QName _GetSiguienteMidSinProcesarResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "getSiguienteMidSinProcesarResponse");
	private final static QName _GetSiguienteNumeroContratoSinProcesar_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "getSiguienteNumeroContratoSinProcesar");
	private final static QName _GetSiguienteNumeroContratoSinProcesarResponse_QNAME = new QName("http://webservices.logistica.amensg.com.uy/", "getSiguienteNumeroContratoSinProcesarResponse");
	
	public ObjectFactory() {
		
	}
	
	public ActualizarDatosMidContrato createActualizarDatosMidContrato() {
        return new ActualizarDatosMidContrato();
    }
	
	public ActualizarDatosMidContratoResponse createActualizarDatosMidContratoResponse() {
        return new ActualizarDatosMidContratoResponse();
    }
	
	public ActualizarDatosMidListaVacia createActualizarDatosMidListaVacia() {
        return new ActualizarDatosMidListaVacia();
    }
	
	public ActualizarDatosMidListaVaciaResponse createActualizarDatosMidListaVaciaResponse() {
        return new ActualizarDatosMidListaVaciaResponse();
    }
	
	public ActualizarDatosMidPrepago createActualizarDatosMidPrepago() {
        return new ActualizarDatosMidPrepago();
    }
	
	public ActualizarDatosMidPrepagoResponse createActualizarDatosMidPrepagoResponse() {
        return new ActualizarDatosMidPrepagoResponse();
    }
	
	public ActualizarDatosNumeroContratoListaVacia createActualizarDatosNumeroContratoListaVacia() {
        return new ActualizarDatosNumeroContratoListaVacia();
    }
	
	public ActualizarDatosNumeroContratoListaVaciaResponse createActualizarDatosNumeroContratoListaVaciaResponse() {
        return new ActualizarDatosNumeroContratoListaVaciaResponse();
    }
	
	public ActualizarDatosPersona createActualizarDatosPersona() {
        return new ActualizarDatosPersona();
    }
	
	public ActualizarDatosPersonaResponse createActualizarDatosPersonaResponse() {
        return new ActualizarDatosPersonaResponse();
    }
	
	public GetSiguienteMidSinProcesar createGetSiguienteMidSinProcesar() {
        return new GetSiguienteMidSinProcesar();
    }
	
	public GetSiguienteMidSinProcesarResponse createGetSiguienteMidSinProcesarResponse() {
        return new GetSiguienteMidSinProcesarResponse();
    }
	
	public GetSiguienteNumeroContratoSinProcesar createGetSiguienteNumeroContratoSinProcesar() {
        return new GetSiguienteNumeroContratoSinProcesar();
    }
	
	public GetSiguienteNumeroContratoSinProcesarResponse createGetSiguienteNumeroContratoSinProcesarResponse() {
        return new GetSiguienteNumeroContratoSinProcesarResponse();
    }

	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosMidContrato")
    public JAXBElement<ActualizarDatosMidContrato> createActualizarDatosMidContrato(ActualizarDatosMidContrato value) {
        return new JAXBElement<ActualizarDatosMidContrato>(_ActualizarDatosMidContrato_QNAME, ActualizarDatosMidContrato.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosMidContratoResponse")
    public JAXBElement<ActualizarDatosMidContratoResponse> createActualizarDatosMidContratoResponse(ActualizarDatosMidContratoResponse value) {
        return new JAXBElement<ActualizarDatosMidContratoResponse>(_ActualizarDatosMidContratoResponse_QNAME, ActualizarDatosMidContratoResponse.class, null, value);
    }

	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosMidListaVacia")
    public JAXBElement<ActualizarDatosMidListaVacia> createActualizarDatosMidListaVacia(ActualizarDatosMidListaVacia value) {
        return new JAXBElement<ActualizarDatosMidListaVacia>(_ActualizarDatosMidListaVacia_QNAME, ActualizarDatosMidListaVacia.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosMidListaVaciaResponse")
    public JAXBElement<ActualizarDatosMidListaVaciaResponse> createActualizarDatosMidListaVaciaResponse(ActualizarDatosMidListaVaciaResponse value) {
        return new JAXBElement<ActualizarDatosMidListaVaciaResponse>(_ActualizarDatosMidListaVaciaResponse_QNAME, ActualizarDatosMidListaVaciaResponse.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosMidPrepago")
    public JAXBElement<ActualizarDatosMidPrepago> createActualizarDatosMidPrepago(ActualizarDatosMidPrepago value) {
        return new JAXBElement<ActualizarDatosMidPrepago>(_ActualizarDatosMidPrepago_QNAME, ActualizarDatosMidPrepago.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosMidPrepagoResponse")
    public JAXBElement<ActualizarDatosMidPrepagoResponse> createActualizarDatosMidPrepagoResponse(ActualizarDatosMidPrepagoResponse value) {
        return new JAXBElement<ActualizarDatosMidPrepagoResponse>(_ActualizarDatosMidPrepagoResponse_QNAME, ActualizarDatosMidPrepagoResponse.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosNumeroContratoListaVacia")
    public JAXBElement<ActualizarDatosNumeroContratoListaVacia> createActualizarDatosNumeroContratoListaVacia(ActualizarDatosNumeroContratoListaVacia value) {
        return new JAXBElement<ActualizarDatosNumeroContratoListaVacia>(_ActualizarDatosNumeroContratoListaVacia_QNAME, ActualizarDatosNumeroContratoListaVacia.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosNumeroContratoListaVaciaResponse")
    public JAXBElement<ActualizarDatosNumeroContratoListaVaciaResponse> createActualizarDatosNumeroContratoListaVaciaResponse(ActualizarDatosNumeroContratoListaVaciaResponse value) {
        return new JAXBElement<ActualizarDatosNumeroContratoListaVaciaResponse>(_ActualizarDatosNumeroContratoListaVaciaResponse_QNAME, ActualizarDatosNumeroContratoListaVaciaResponse.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosPersona")
    public JAXBElement<ActualizarDatosPersona> createActualizarDatosPersona(ActualizarDatosPersona value) {
        return new JAXBElement<ActualizarDatosPersona>(_ActualizarDatosPersona_QNAME, ActualizarDatosPersona.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "actualizarDatosPersonaResponse")
    public JAXBElement<ActualizarDatosPersonaResponse> createActualizarDatosPersonaResponse(ActualizarDatosPersonaResponse value) {
        return new JAXBElement<ActualizarDatosPersonaResponse>(_ActualizarDatosPersonaResponse_QNAME, ActualizarDatosPersonaResponse.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "getSiguienteMidSinProcesar")
    public JAXBElement<GetSiguienteMidSinProcesar> createGetSiguienteMidSinProcesar(GetSiguienteMidSinProcesar value) {
        return new JAXBElement<GetSiguienteMidSinProcesar>(_GetSiguienteMidSinProcesar_QNAME, GetSiguienteMidSinProcesar.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "getSiguienteMidSinProcesarResponse")
    public JAXBElement<GetSiguienteMidSinProcesarResponse> createGetSiguienteMidSinProcesarResponse(GetSiguienteMidSinProcesarResponse value) {
        return new JAXBElement<GetSiguienteMidSinProcesarResponse>(_GetSiguienteMidSinProcesarResponse_QNAME, GetSiguienteMidSinProcesarResponse.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "getSiguienteNumeroContratoSinProcesar")
    public JAXBElement<GetSiguienteNumeroContratoSinProcesar> createGetSiguienteNumeroContratoSinProcesar(GetSiguienteNumeroContratoSinProcesar value) {
        return new JAXBElement<GetSiguienteNumeroContratoSinProcesar>(_GetSiguienteNumeroContratoSinProcesar_QNAME, GetSiguienteNumeroContratoSinProcesar.class, null, value);
    }
	
	@XmlElementDecl(namespace = "http://webservices.logistica.amensg.com.uy/", name = "getSiguienteNumeroContratoSinProcesarResponse")
    public JAXBElement<GetSiguienteNumeroContratoSinProcesarResponse> createGetSiguienteNumeroContratoSinProcesarResponse(GetSiguienteNumeroContratoSinProcesarResponse value) {
        return new JAXBElement<GetSiguienteNumeroContratoSinProcesarResponse>(_GetSiguienteNumeroContratoSinProcesarResponse_QNAME, GetSiguienteNumeroContratoSinProcesarResponse.class, null, value);
    }
}