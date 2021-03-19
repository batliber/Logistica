package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pais", propOrder = { "idPais", "descripcion" })
public class Pais implements Serializable {

	private static final long serialVersionUID = 7630495306670250627L;
	
	@XmlElement(name = "IdPais")
	protected String idPais;
	@XmlElement(name = "Descripcion")
	protected String descripcion;

	public String getIdPais() {
		return idPais;
	}

	public void setIdPais(String value) {
		this.idPais = value;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String value) {
		this.descripcion = value;
	}
}