package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "InfoFuncionarioPublico", 
	propOrder = { "anho", "mes", "cedula", "nombres", "entidad", "categoria", "vinculo", "objetoGasto", "monto" }
)
public class InfoFuncionarioPublico implements Serializable {

	private static final long serialVersionUID = 5409388245653581567L;
	
	@XmlElement(name = "Anho")
	protected int anho;
	@XmlElement(name = "Mes")
	protected int mes;
	@XmlElement(name = "Cedula")
	protected String cedula;
	@XmlElement(name = "Nombres")
	protected String nombres;
	@XmlElement(name = "Entidad")
	protected String entidad;
	@XmlElement(name = "Categoria")
	protected String categoria;
	@XmlElement(name = "Vinculo")
	protected String vinculo;
	@XmlElement(name = "ObjetoGasto")
	protected String objetoGasto;
	@XmlElement(name = "Monto")
	protected double monto;

	/**
	 * Gets the value of the anho property.
	 * 
	 */
	public int getAnho() {
		return anho;
	}

	/**
	 * Sets the value of the anho property.
	 * 
	 */
	public void setAnho(int value) {
		this.anho = value;
	}

	/**
	 * Gets the value of the mes property.
	 * 
	 */
	public int getMes() {
		return mes;
	}

	/**
	 * Sets the value of the mes property.
	 * 
	 */
	public void setMes(int value) {
		this.mes = value;
	}

	/**
	 * Gets the value of the cedula property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCedula() {
		return cedula;
	}

	/**
	 * Sets the value of the cedula property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCedula(String value) {
		this.cedula = value;
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
	 * Gets the value of the entidad property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * Sets the value of the entidad property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setEntidad(String value) {
		this.entidad = value;
	}

	/**
	 * Gets the value of the categoria property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCategoria() {
		return categoria;
	}

	/**
	 * Sets the value of the categoria property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCategoria(String value) {
		this.categoria = value;
	}

	/**
	 * Gets the value of the vinculo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getVinculo() {
		return vinculo;
	}

	/**
	 * Sets the value of the vinculo property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setVinculo(String value) {
		this.vinculo = value;
	}

	/**
	 * Gets the value of the objetoGasto property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getObjetoGasto() {
		return objetoGasto;
	}

	/**
	 * Sets the value of the objetoGasto property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setObjetoGasto(String value) {
		this.objetoGasto = value;
	}

	/**
	 * Gets the value of the monto property.
	 * 
	 */
	public double getMonto() {
		return monto;
	}

	/**
	 * Sets the value of the monto property.
	 * 
	 */
	public void setMonto(double value) {
		this.monto = value;
	}
}