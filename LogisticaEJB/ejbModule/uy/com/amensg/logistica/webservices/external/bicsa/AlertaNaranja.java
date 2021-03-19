package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlertaNaranja", propOrder = { "usuario", "institucionDesc", "fecha", "infoContacto" })
public class AlertaNaranja implements Serializable {

	private static final long serialVersionUID = 2908895816454718183L;
	
	@XmlElement(name = "Usuario")
	protected String usuario;
	@XmlElement(name = "Institucion_Desc")
	protected String institucionDesc;
	@XmlElement(name = "Fecha")
	protected String fecha;
	@XmlElement(name = "InfoContacto")
	protected String infoContacto;

	/**
	 * Gets the value of the usuario property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Sets the value of the usuario property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setUsuario(String value) {
		this.usuario = value;
	}

	/**
	 * Gets the value of the institucionDesc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getInstitucionDesc() {
		return institucionDesc;
	}

	/**
	 * Sets the value of the institucionDesc property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setInstitucionDesc(String value) {
		this.institucionDesc = value;
	}

	/**
	 * Gets the value of the fecha property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * Sets the value of the fecha property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setFecha(String value) {
		this.fecha = value;
	}

	/**
	 * Gets the value of the infoContacto property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getInfoContacto() {
		return infoContacto;
	}

	/**
	 * Sets the value of the infoContacto property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setInfoContacto(String value) {
		this.infoContacto = value;
	}
}
