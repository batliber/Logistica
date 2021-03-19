package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "Direccion", 
	propOrder = { 
		"idDireccion", "direccionLibre", "calle", "nroPuerta", "nroApartamento", "edificio", "codigoPostal", 
		"ciudad", "barrio", "telefono", "fechaRegistrada" 
	}
)
public class Direccion implements Serializable {

	private static final long serialVersionUID = -811409959802213597L;
	
	@XmlElement(name = "IdDireccion")
	protected long idDireccion;
	@XmlElement(name = "Direccion_Libre")
	protected String direccionLibre;
	@XmlElement(name = "Calle")
	protected String calle;
	@XmlElement(name = "NroPuerta")
	protected String nroPuerta;
	@XmlElement(name = "NroApartamento")
	protected String nroApartamento;
	@XmlElement(name = "Edificio")
	protected String edificio;
	@XmlElement(name = "CodigoPostal")
	protected String codigoPostal;
	@XmlElement(name = "Ciudad")
	protected String ciudad;
	@XmlElement(name = "Barrio")
	protected String barrio;
	@XmlElement(name = "Telefono")
	protected String telefono;
	@XmlElement(name = "FechaRegistrada", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaRegistrada;

	/**
	 * Gets the value of the idDireccion property.
	 * 
	 */
	public long getIdDireccion() {
		return idDireccion;
	}

	/**
	 * Sets the value of the idDireccion property.
	 * 
	 */
	public void setIdDireccion(long value) {
		this.idDireccion = value;
	}

	/**
	 * Gets the value of the direccionLibre property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDireccionLibre() {
		return direccionLibre;
	}

	/**
	 * Sets the value of the direccionLibre property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setDireccionLibre(String value) {
		this.direccionLibre = value;
	}

	/**
	 * Gets the value of the calle property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * Sets the value of the calle property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCalle(String value) {
		this.calle = value;
	}

	/**
	 * Gets the value of the nroPuerta property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNroPuerta() {
		return nroPuerta;
	}

	/**
	 * Sets the value of the nroPuerta property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNroPuerta(String value) {
		this.nroPuerta = value;
	}

	/**
	 * Gets the value of the nroApartamento property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNroApartamento() {
		return nroApartamento;
	}

	/**
	 * Sets the value of the nroApartamento property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setNroApartamento(String value) {
		this.nroApartamento = value;
	}

	/**
	 * Gets the value of the edificio property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEdificio() {
		return edificio;
	}

	/**
	 * Sets the value of the edificio property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setEdificio(String value) {
		this.edificio = value;
	}

	/**
	 * Gets the value of the codigoPostal property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * Sets the value of the codigoPostal property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCodigoPostal(String value) {
		this.codigoPostal = value;
	}

	/**
	 * Gets the value of the ciudad property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * Sets the value of the ciudad property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCiudad(String value) {
		this.ciudad = value;
	}

	/**
	 * Gets the value of the barrio property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getBarrio() {
		return barrio;
	}

	/**
	 * Sets the value of the barrio property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setBarrio(String value) {
		this.barrio = value;
	}

	/**
	 * Gets the value of the telefono property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Sets the value of the telefono property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTelefono(String value) {
		this.telefono = value;
	}

	/**
	 * Gets the value of the fechaRegistrada property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getFechaRegistrada() {
		return fechaRegistrada;
	}

	/**
	 * Sets the value of the fechaRegistrada property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setFechaRegistrada(XMLGregorianCalendar value) {
		this.fechaRegistrada = value;
	}
}