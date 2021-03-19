package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoDocumento", propOrder = { "idTipoDoc", "descripcion" })
public class TipoDocumento implements Serializable {

	
	private static final long serialVersionUID = 3514159399296014875L;
	
	@XmlElement(name = "IdTipoDoc")
	protected int idTipoDoc;
	@XmlElement(name = "Descripcion")
	protected String descripcion;

	public int getIdTipoDoc() {
		return idTipoDoc;
	}

	public void setIdTipoDoc(int value) {
		this.idTipoDoc = value;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String value) {
		this.descripcion = value;
	}
}