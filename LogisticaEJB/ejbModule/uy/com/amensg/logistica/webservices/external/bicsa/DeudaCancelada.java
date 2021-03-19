package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "DeudaCancelada", 
	propOrder = { 
		"promedioDiasAtraso", "maximoDiasAtraso", "montoOriginal", "montoCuota", "plazoOriginal", 
		"fechaOriginacion", "fechaCancelacion", "cuentaConOpR", "institucion", "tipoGarantiaBCP" 
	}
)
public class DeudaCancelada implements Serializable {

	private static final long serialVersionUID = 45562297242938560L;
	
	@XmlElement(name = "PromedioDiasAtraso")
	protected int promedioDiasAtraso;
	@XmlElement(name = "MaximoDiasAtraso")
	protected int maximoDiasAtraso;
	@XmlElement(name = "MontoOriginal", required = true)
	protected BigDecimal montoOriginal;
	@XmlElement(name = "MontoCuota", required = true)
	protected BigDecimal montoCuota;
	@XmlElement(name = "PlazoOriginal")
	protected int plazoOriginal;
	@XmlElement(name = "FechaOriginacion", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaOriginacion;
	@XmlElement(name = "FechaCancelacion", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaCancelacion;
	@XmlElement(name = "CuentaConOpR")
	protected boolean cuentaConOpR;
	@XmlElement(name = "Institucion")
	protected String institucion;
	@XmlElement(name = "TipoGarantiaBCP")
	protected String tipoGarantiaBCP;

	/**
	 * Gets the value of the promedioDiasAtraso property.
	 * 
	 */
	public int getPromedioDiasAtraso() {
		return promedioDiasAtraso;
	}

	/**
	 * Sets the value of the promedioDiasAtraso property.
	 * 
	 */
	public void setPromedioDiasAtraso(int value) {
		this.promedioDiasAtraso = value;
	}

	/**
	 * Gets the value of the maximoDiasAtraso property.
	 * 
	 */
	public int getMaximoDiasAtraso() {
		return maximoDiasAtraso;
	}

	/**
	 * Sets the value of the maximoDiasAtraso property.
	 * 
	 */
	public void setMaximoDiasAtraso(int value) {
		this.maximoDiasAtraso = value;
	}

	/**
	 * Gets the value of the montoOriginal property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getMontoOriginal() {
		return montoOriginal;
	}

	/**
	 * Sets the value of the montoOriginal property.
	 * 
	 * @param value allowed object is {@link BigDecimal }
	 * 
	 */
	public void setMontoOriginal(BigDecimal value) {
		this.montoOriginal = value;
	}

	/**
	 * Gets the value of the montoCuota property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getMontoCuota() {
		return montoCuota;
	}

	/**
	 * Sets the value of the montoCuota property.
	 * 
	 * @param value allowed object is {@link BigDecimal }
	 * 
	 */
	public void setMontoCuota(BigDecimal value) {
		this.montoCuota = value;
	}

	/**
	 * Gets the value of the plazoOriginal property.
	 * 
	 */
	public int getPlazoOriginal() {
		return plazoOriginal;
	}

	/**
	 * Sets the value of the plazoOriginal property.
	 * 
	 */
	public void setPlazoOriginal(int value) {
		this.plazoOriginal = value;
	}

	/**
	 * Gets the value of the fechaOriginacion property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getFechaOriginacion() {
		return fechaOriginacion;
	}

	/**
	 * Sets the value of the fechaOriginacion property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setFechaOriginacion(XMLGregorianCalendar value) {
		this.fechaOriginacion = value;
	}

	/**
	 * Gets the value of the fechaCancelacion property.
	 * 
	 * @return possible object is {@link XMLGregorianCalendar }
	 * 
	 */
	public XMLGregorianCalendar getFechaCancelacion() {
		return fechaCancelacion;
	}

	/**
	 * Sets the value of the fechaCancelacion property.
	 * 
	 * @param value allowed object is {@link XMLGregorianCalendar }
	 * 
	 */
	public void setFechaCancelacion(XMLGregorianCalendar value) {
		this.fechaCancelacion = value;
	}

	/**
	 * Gets the value of the cuentaConOpR property.
	 * 
	 */
	public boolean isCuentaConOpR() {
		return cuentaConOpR;
	}

	/**
	 * Sets the value of the cuentaConOpR property.
	 * 
	 */
	public void setCuentaConOpR(boolean value) {
		this.cuentaConOpR = value;
	}

	/**
	 * Gets the value of the institucion property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getInstitucion() {
		return institucion;
	}

	/**
	 * Sets the value of the institucion property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setInstitucion(String value) {
		this.institucion = value;
	}

	/**
	 * Gets the value of the tipoGarantiaBCP property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTipoGarantiaBCP() {
		return tipoGarantiaBCP;
	}

	/**
	 * Sets the value of the tipoGarantiaBCP property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTipoGarantiaBCP(String value) {
		this.tipoGarantiaBCP = value;
	}
}