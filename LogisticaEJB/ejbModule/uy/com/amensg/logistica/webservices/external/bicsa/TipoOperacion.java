package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoOperacion", propOrder = { "idTipoOperacion", "descripcion" })
public class TipoOperacion implements Serializable {

	private static final long serialVersionUID = -4387493709775058836L;
	
	@XmlElement(name = "IdTipoOperacion")
	protected int idTipoOperacion;
	@XmlElement(name = "Descripcion")
	protected String descripcion;

	public int getIdTipoOperacion() {
		return idTipoOperacion;
	}

	public void setIdTipoOperacion(int value) {
		this.idTipoOperacion = value;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String value) {
		this.descripcion = value;
	}
}