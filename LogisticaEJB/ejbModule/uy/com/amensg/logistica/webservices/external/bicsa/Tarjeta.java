package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "Tarjeta", 
	propOrder = { 
		"idTarjeta", "idPersona", "lineaTotal", "lineaUtilizada", "pagoMinimo", "montoAtrasado", "moneda", 
		"institucion"
	}
)
public class Tarjeta implements Serializable {

	private static final long serialVersionUID = -8524920658417283672L;
	
	@XmlElement(name = "IdTarjeta")
	protected double idTarjeta;
	@XmlElement(name = "IdPersona")
	protected double idPersona;
	@XmlElement(name = "LineaTotal")
	protected double lineaTotal;
	@XmlElement(name = "LineaUtilizada")
	protected double lineaUtilizada;
	@XmlElement(name = "PagoMinimo")
	protected double pagoMinimo;
	@XmlElement(name = "MontoAtrasado")
	protected double montoAtrasado;
	@XmlElement(name = "Moneda")
	protected Moneda moneda;
	@XmlElement(name = "Institucion")
	protected Institucion institucion;

	/**
	 * Gets the value of the idTarjeta property.
	 * 
	 */
	public double getIdTarjeta() {
		return idTarjeta;
	}

	/**
	 * Sets the value of the idTarjeta property.
	 * 
	 */
	public void setIdTarjeta(double value) {
		this.idTarjeta = value;
	}

	/**
	 * Gets the value of the idPersona property.
	 * 
	 */
	public double getIdPersona() {
		return idPersona;
	}

	/**
	 * Sets the value of the idPersona property.
	 * 
	 */
	public void setIdPersona(double value) {
		this.idPersona = value;
	}

	/**
	 * Gets the value of the lineaTotal property.
	 * 
	 */
	public double getLineaTotal() {
		return lineaTotal;
	}

	/**
	 * Sets the value of the lineaTotal property.
	 * 
	 */
	public void setLineaTotal(double value) {
		this.lineaTotal = value;
	}

	/**
	 * Gets the value of the lineaUtilizada property.
	 * 
	 */
	public double getLineaUtilizada() {
		return lineaUtilizada;
	}

	/**
	 * Sets the value of the lineaUtilizada property.
	 * 
	 */
	public void setLineaUtilizada(double value) {
		this.lineaUtilizada = value;
	}

	/**
	 * Gets the value of the pagoMinimo property.
	 * 
	 */
	public double getPagoMinimo() {
		return pagoMinimo;
	}

	/**
	 * Sets the value of the pagoMinimo property.
	 * 
	 */
	public void setPagoMinimo(double value) {
		this.pagoMinimo = value;
	}

	/**
	 * Gets the value of the montoAtrasado property.
	 * 
	 */
	public double getMontoAtrasado() {
		return montoAtrasado;
	}

	/**
	 * Sets the value of the montoAtrasado property.
	 * 
	 */
	public void setMontoAtrasado(double value) {
		this.montoAtrasado = value;
	}

	/**
	 * Gets the value of the moneda property.
	 * 
	 * @return possible object is {@link Moneda }
	 * 
	 */
	public Moneda getMoneda() {
		return moneda;
	}

	/**
	 * Sets the value of the moneda property.
	 * 
	 * @param value allowed object is {@link Moneda }
	 * 
	 */
	public void setMoneda(Moneda value) {
		this.moneda = value;
	}

	/**
	 * Gets the value of the institucion property.
	 * 
	 * @return possible object is {@link Institucion }
	 * 
	 */
	public Institucion getInstitucion() {
		return institucion;
	}

	/**
	 * Sets the value of the institucion property.
	 * 
	 * @param value allowed object is {@link Institucion }
	 * 
	 */
	public void setInstitucion(Institucion value) {
		this.institucion = value;
	}
}