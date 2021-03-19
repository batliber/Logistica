package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Institucion", propOrder = { "idInstitucion", "nombre" })
public class Institucion implements Serializable {

	private static final long serialVersionUID = -2612130786737210214L;
	
	@XmlElement(name = "IdInstitucion")
	protected int idInstitucion;
	@XmlElement(name = "Nombre")
	protected String nombre;

	public int getIdInstitucion() {
		return idInstitucion;
	}

	public void setIdInstitucion(int value) {
		this.idInstitucion = value;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String value) {
		this.nombre = value;
	}
}