package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoRelacion", propOrder = { "idTipoRelacion", "descripcion" })
public class TipoRelacion implements Serializable {

	private static final long serialVersionUID = 1389995852495307233L;
	
	@XmlElement(name = "IdTipoRelacion")
	protected int idTipoRelacion;
	@XmlElement(name = "Descripcion")
	protected String descripcion;

	public int getIdTipoRelacion() {
		return idTipoRelacion;
	}

	public void setIdTipoRelacion(int value) {
		this.idTipoRelacion = value;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String value) {
		this.descripcion = value;
	}
}