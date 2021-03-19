package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Moneda", propOrder = { "idMoneda", "descripcion" })
public class Moneda implements Serializable {

	private static final long serialVersionUID = -4110199807615308219L;
	
	@XmlElement(name = "IdMoneda")
	protected String idMoneda;
	@XmlElement(name = "Descripcion")
	protected String descripcion;

	public String getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(String value) {
		this.idMoneda = value;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String value) {
		this.descripcion = value;
	}
}