package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "InfoAdicional", 
	propOrder = { 
		"barrioCiudad", "nombrePadre", "nombreMadre", "nombreConyuge", "sexo", "estadoCivil", "fechaImp", 
		"domicilio", "tipoDoc", "ci", "nombres", "apellidos", "fechaNac", "lugarNacimiento"
	}
)
public class InfoAdicional implements Serializable {

	private static final long serialVersionUID = -2076931790844150831L;
	
	@XmlElement(name = "Barrio_Ciudad")
	protected String barrioCiudad;
	@XmlElement(name = "NombrePadre")
	protected String nombrePadre;
	@XmlElement(name = "NombreMadre")
	protected String nombreMadre;
	@XmlElement(name = "NombreConyuge")
	protected String nombreConyuge;
	@XmlElement(name = "Sexo")
	protected String sexo;
	@XmlElement(name = "EstadoCivil")
	protected String estadoCivil;
	@XmlElement(name = "FechaImp")
	protected String fechaImp;
	@XmlElement(name = "Domicilio")
	protected String domicilio;
	@XmlElement(name = "TipoDoc")
	protected String tipoDoc;
	@XmlElement(name = "CI")
	protected String ci;
	@XmlElement(name = "Nombres")
	protected String nombres;
	@XmlElement(name = "Apellidos")
	protected String apellidos;
	@XmlElement(name = "FechaNac")
	protected String fechaNac;
	@XmlElement(name = "LugarNacimiento")
	protected String lugarNacimiento;

	public String getBarrioCiudad() {
		return barrioCiudad;
	}

	public void setBarrioCiudad(String barrioCiudad) {
		this.barrioCiudad = barrioCiudad;
	}

	public String getNombrePadre() {
		return nombrePadre;
	}

	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}

	public String getNombreMadre() {
		return nombreMadre;
	}

	public void setNombreMadre(String nombreMadre) {
		this.nombreMadre = nombreMadre;
	}

	public String getNombreConyuge() {
		return nombreConyuge;
	}

	public void setNombreConyuge(String nombreConyuge) {
		this.nombreConyuge = nombreConyuge;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	/**
	 * Gets the value of the sexo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * Sets the value of the sexo property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setSexo(String value) {
		this.sexo = value;
	}

	/**
	 * Gets the value of the estadoCivil property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEstadoCivil() {
		return estadoCivil;
	}

	/**
	 * Sets the value of the estadoCivil property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setEstadoCivil(String value) {
		this.estadoCivil = value;
	}

	/**
	 * Gets the value of the fechaImp property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFechaImp() {
		return fechaImp;
	}

	/**
	 * Sets the value of the fechaImp property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setFechaImp(String value) {
		this.fechaImp = value;
	}

	/**
	 * Gets the value of the domicilio property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDomicilio() {
		return domicilio;
	}

	/**
	 * Sets the value of the domicilio property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDomicilio(String value) {
		this.domicilio = value;
	}

	/**
	 * Gets the value of the tipoDoc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTipoDoc() {
		return tipoDoc;
	}

	/**
	 * Sets the value of the tipoDoc property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTipoDoc(String value) {
		this.tipoDoc = value;
	}

	/**
	 * Gets the value of the nombres property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * Sets the value of the nombres property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNombres(String value) {
		this.nombres = value;
	}

	/**
	 * Gets the value of the apellidos property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Sets the value of the apellidos property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setApellidos(String value) {
		this.apellidos = value;
	}

	/**
	 * Gets the value of the fechaNac property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFechaNac() {
		return fechaNac;
	}

	/**
	 * Sets the value of the fechaNac property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setFechaNac(String value) {
		this.fechaNac = value;
	}

	/**
	 * Gets the value of the lugarNacimiento property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLugarNacimiento() {
		return lugarNacimiento;
	}

	/**
	 * Sets the value of the lugarNacimiento property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setLugarNacimiento(String value) {
		this.lugarNacimiento = value;
	}
}