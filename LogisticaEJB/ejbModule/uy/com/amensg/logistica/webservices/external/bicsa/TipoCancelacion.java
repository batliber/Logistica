package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoCancelacion", propOrder = { "idTipoCancelacion", "descripcion" })
public class TipoCancelacion implements Serializable {

	private static final long serialVersionUID = 4409715268618197114L;
	
	@XmlElement(name = "IdTipoCancelacion")
	protected int idTipoCancelacion;
	@XmlElement(name = "Descripcion")
	protected String descripcion;

	public int getIdTipoCancelacion() {
		return idTipoCancelacion;
	}

	public void setIdTipoCancelacion(int value) {
		this.idTipoCancelacion = value;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String value) {
		this.descripcion = value;
	}
}