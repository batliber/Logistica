package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoConsultaProducto", propOrder = { "idTipoConsultaProducto", "descripcion" })
public class TipoConsultaProducto implements Serializable {

	private static final long serialVersionUID = 3252390046084914353L;
	
	@XmlElement(name = "IdTipoConsultaProducto")
	protected int idTipoConsultaProducto;
	@XmlElement(name = "Descripcion")
	protected String descripcion;

	public int getIdTipoConsultaProducto() {
		return idTipoConsultaProducto;
	}

	public void setIdTipoConsultaProducto(int value) {
		this.idTipoConsultaProducto = value;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String value) {
		this.descripcion = value;
	}
}