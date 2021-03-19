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
	name = "TrabajoDeLaPersona",
	propOrder = { 
		"lugarDeTrabajo", "comprobanteIngreso", "idTrabajo", "esDependiente", "cargoTrabajo", "salario", 
		"fechaInformado" 
	}
)
public class TrabajoDeLaPersona implements Serializable {

	private static final long serialVersionUID = -55531694265488796L;
	
	@XmlElement(name = "LugarDeTrabajo")
	protected String lugarDeTrabajo;
	@XmlElement(name = "ComprobanteIngreso")
	protected boolean comprobanteIngreso;
	@XmlElement(name = "IdTrabajo")
	protected long idTrabajo;
	@XmlElement(name = "EsDependiente")
	protected boolean esDependiente;
	@XmlElement(name = "CargoTrabajo")
	protected String cargoTrabajo;
	@XmlElement(name = "Salario")
	protected double salario;
	@XmlElement(name = "FechaInformado", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaInformado;

	/**
	 * Gets the value of the lugarDeTrabajo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLugarDeTrabajo() {
		return lugarDeTrabajo;
	}

	/**
	 * Sets the value of the lugarDeTrabajo property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setLugarDeTrabajo(String value) {
		this.lugarDeTrabajo = value;
	}

	/**
	 * Gets the value of the comprobanteIngreso property.
	 * 
	 */
	public boolean isComprobanteIngreso() {
		return comprobanteIngreso;
	}

	/**
	 * Sets the value of the comprobanteIngreso property.
	 * 
	 */
	public void setComprobanteIngreso(boolean value) {
		this.comprobanteIngreso = value;
	}

	/**
	 * Gets the value of the idTrabajo property.
	 * 
	 */
	public long getIdTrabajo() {
		return idTrabajo;
	}

	/**
	 * Sets the value of the idTrabajo property.
	 * 
	 */
	public void setIdTrabajo(long value) {
		this.idTrabajo = value;
	}

	/**
	 * Gets the value of the esDependiente property.
	 * 
	 */
	public boolean isEsDependiente() {
		return esDependiente;
	}

	/**
	 * Sets the value of the esDependiente property.
	 * 
	 */
	public void setEsDependiente(boolean value) {
		this.esDependiente = value;
	}

	/**
	 * Gets the value of the cargoTrabajo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCargoTrabajo() {
		return cargoTrabajo;
	}

	/**
	 * Sets the value of the cargoTrabajo property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCargoTrabajo(String value) {
		this.cargoTrabajo = value;
	}

	/**
	 * Gets the value of the salario property.
	 * 
	 */
	public double getSalario() {
		return salario;
	}

	/**
	 * Sets the value of the salario property.
	 * 
	 */
	public void setSalario(double value) {
		this.salario = value;
	}

	/**
	 * Gets the value of the fechaInformado property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getFechaInformado() {
		return fechaInformado;
	}

	/**
	 * Sets the value of the fechaInformado property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setFechaInformado(XMLGregorianCalendar value) {
		this.fechaInformado = value;
	}
}