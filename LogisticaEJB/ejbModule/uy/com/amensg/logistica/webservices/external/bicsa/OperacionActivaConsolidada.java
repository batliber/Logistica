package uy.com.amensg.logistica.webservices.external.bicsa;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
	name = "OperacionActiva_Consolidada", 
	propOrder = { 
		"idCarga", "fechaCorte", "cuotaMes", "refuerzoMes", "cuotasVencidas", "compromisoMes", "saldoTotal", 
		"cuotasPendientes", "diasAtraso", "fechaInforme"
	}
)
public class OperacionActivaConsolidada implements Serializable {

	private static final long serialVersionUID = 6576718633063335242L;
	
	@XmlElement(name = "IdCarga")
	protected int idCarga;
	@XmlElement(name = "FechaCorte")
	protected String fechaCorte;
	@XmlElement(name = "CuotaMes")
	protected double cuotaMes;
	@XmlElement(name = "Refuerzo_Mes")
	protected double refuerzoMes;
	@XmlElement(name = "Cuotas_Vencidas")
	protected double cuotasVencidas;
	@XmlElement(name = "Compromiso_Mes")
	protected double compromisoMes;
	@XmlElement(name = "Saldo_Total")
	protected double saldoTotal;
	@XmlElement(name = "Cuotas_Pendientes")
	protected int cuotasPendientes;
	@XmlElement(name = "DiasAtraso")
	protected int diasAtraso;
	@XmlElement(name = "Fecha_Informe")
	protected String fechaInforme;

	/**
	 * Gets the value of the idCarga property.
	 * 
	 */
	public int getIdCarga() {
		return idCarga;
	}

	/**
	 * Sets the value of the idCarga property.
	 * 
	 */
	public void setIdCarga(int value) {
		this.idCarga = value;
	}

	/**
	 * Gets the value of the fechaCorte property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFechaCorte() {
		return fechaCorte;
	}

	/**
	 * Sets the value of the fechaCorte property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setFechaCorte(String value) {
		this.fechaCorte = value;
	}

	/**
	 * Gets the value of the cuotaMes property.
	 * 
	 */
	public double getCuotaMes() {
		return cuotaMes;
	}

	/**
	 * Sets the value of the cuotaMes property.
	 * 
	 */
	public void setCuotaMes(double value) {
		this.cuotaMes = value;
	}

	/**
	 * Gets the value of the refuerzoMes property.
	 * 
	 */
	public double getRefuerzoMes() {
		return refuerzoMes;
	}

	/**
	 * Sets the value of the refuerzoMes property.
	 * 
	 */
	public void setRefuerzoMes(double value) {
		this.refuerzoMes = value;
	}

	/**
	 * Gets the value of the cuotasVencidas property.
	 * 
	 */
	public double getCuotasVencidas() {
		return cuotasVencidas;
	}

	/**
	 * Sets the value of the cuotasVencidas property.
	 * 
	 */
	public void setCuotasVencidas(double value) {
		this.cuotasVencidas = value;
	}

	/**
	 * Gets the value of the compromisoMes property.
	 * 
	 */
	public double getCompromisoMes() {
		return compromisoMes;
	}

	/**
	 * Sets the value of the compromisoMes property.
	 * 
	 */
	public void setCompromisoMes(double value) {
		this.compromisoMes = value;
	}

	/**
	 * Gets the value of the saldoTotal property.
	 * 
	 */
	public double getSaldoTotal() {
		return saldoTotal;
	}

	/**
	 * Sets the value of the saldoTotal property.
	 * 
	 */
	public void setSaldoTotal(double value) {
		this.saldoTotal = value;
	}

	/**
	 * Gets the value of the cuotasPendientes property.
	 * 
	 */
	public int getCuotasPendientes() {
		return cuotasPendientes;
	}

	/**
	 * Sets the value of the cuotasPendientes property.
	 * 
	 */
	public void setCuotasPendientes(int value) {
		this.cuotasPendientes = value;
	}

	/**
	 * Gets the value of the diasAtraso property.
	 * 
	 */
	public int getDiasAtraso() {
		return diasAtraso;
	}

	/**
	 * Sets the value of the diasAtraso property.
	 * 
	 */
	public void setDiasAtraso(int value) {
		this.diasAtraso = value;
	}

	/**
	 * Gets the value of the fechaInforme property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFechaInforme() {
		return fechaInforme;
	}

	/**
	 * Sets the value of the fechaInforme property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setFechaInforme(String value) {
		this.fechaInforme = value;
	}
}