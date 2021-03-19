package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "obtenerPersonaPDFResult" })
@XmlRootElement(name = "ObtenerPersona_PDFResponse")
public class ObtenerPersonaPDFResponse implements Serializable {

	private static final long serialVersionUID = 1652633679666404133L;
	
	@XmlElement(name = "ObtenerPersona_PDFResult")
	protected Persona obtenerPersonaPDFResult;

	public Persona getObtenerPersonaPDFResult() {
		return obtenerPersonaPDFResult;
	}

	public void setObtenerPersonaPDFResult(Persona value) {
		this.obtenerPersonaPDFResult = value;
	}
}