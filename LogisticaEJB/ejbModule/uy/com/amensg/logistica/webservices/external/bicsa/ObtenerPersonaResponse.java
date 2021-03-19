package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "obtenerPersonaResult" })
@XmlRootElement(name = "ObtenerPersonaResponse")
public class ObtenerPersonaResponse implements Serializable {

	private static final long serialVersionUID = 4454358921633381920L;
	
	@XmlElement(name = "ObtenerPersonaResult")
	protected Persona obtenerPersonaResult;

	public Persona getObtenerPersonaResult() {
		return obtenerPersonaResult;
	}

	public void setObtenerPersonaResult(Persona value) {
		this.obtenerPersonaResult = value;
	}
}