package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoTitular", propOrder = { "idTipoTitular", "descripcion" })
public class TipoTitular implements Serializable {

	private static final long serialVersionUID = 3800290647701938040L;
	
	@XmlElement(name = "IdTipoTitular")
	protected int idTipoTitular;
	@XmlElement(name = "Descripcion")
	protected String descripcion;

	public int getIdTipoTitular() {
		return idTipoTitular;
	}

	public void setIdTipoTitular(int value) {
		this.idTipoTitular = value;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String value) {
		this.descripcion = value;
	}
}