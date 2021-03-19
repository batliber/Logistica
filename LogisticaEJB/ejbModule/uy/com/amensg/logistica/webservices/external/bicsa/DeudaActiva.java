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
@XmlType(name = "DeudaActiva", propOrder = { "fechaCorte", "maxDiasAtraso", "promedioPagos", "saldoConsolidado",
		"montoCuotaConsolidado", "montoCuotaVencido", "mayorPlazoCredito", "menorPlazoCredito",
		"maximoCuotasPendientes", "minimoCuotasPendientes", "cuentaConOpR", "entidadesAportantesTotal",
		"entidadesAportantesLibranza", "entidadesAportantesConsumo", "cantConsultasRealizadas", "tipoGarantiaBCP",
		"cantChequesRechazadosTotal", "montosChequesRechazadosTotal", "cantChequesRechazadosFormal",
		"montoChequesRechazadosFormal", "cantChequesRechazadosInformal", "montoCheqyesRechazadosInformal" })
public class DeudaActiva implements Serializable {

	private static final long serialVersionUID = -9145270759654383210L;
	
	@XmlElement(name = "FechaCorte", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar fechaCorte;
	@XmlElement(name = "Max_Dias_Atraso")
	protected int maxDiasAtraso;
	@XmlElement(name = "PromedioPagos", required = true)
	protected BigDecimal promedioPagos;
	@XmlElement(name = "SaldoConsolidado", required = true)
	protected BigDecimal saldoConsolidado;
	@XmlElement(name = "MontoCuota_Consolidado", required = true)
	protected BigDecimal montoCuotaConsolidado;
	@XmlElement(name = "MontoCuota_Vencido", required = true)
	protected BigDecimal montoCuotaVencido;
	@XmlElement(name = "MayorPlazoCredito")
	protected int mayorPlazoCredito;
	@XmlElement(name = "MenorPlazoCredito")
	protected int menorPlazoCredito;
	@XmlElement(name = "MaximoCuotasPendientes")
	protected int maximoCuotasPendientes;
	@XmlElement(name = "MinimoCuotasPendientes")
	protected int minimoCuotasPendientes;
	@XmlElement(name = "CuentaConOpR")
	protected boolean cuentaConOpR;
	@XmlElement(name = "EntidadesAportantesTotal")
	protected int entidadesAportantesTotal;
	@XmlElement(name = "EntidadesAportantesLibranza")
	protected int entidadesAportantesLibranza;
	@XmlElement(name = "EntidadesAportantesConsumo")
	protected int entidadesAportantesConsumo;
	@XmlElement(name = "CantConsultasRealizadas")
	protected int cantConsultasRealizadas;
	@XmlElement(name = "TipoGarantiaBCP")
	protected String tipoGarantiaBCP;
	@XmlElement(name = "CantChequesRechazadosTotal")
	protected int cantChequesRechazadosTotal;
	@XmlElement(name = "MontosChequesRechazadosTotal", required = true)
	protected BigDecimal montosChequesRechazadosTotal;
	@XmlElement(name = "CantChequesRechazadosFormal")
	protected int cantChequesRechazadosFormal;
	@XmlElement(name = "MontoChequesRechazadosFormal", required = true)
	protected BigDecimal montoChequesRechazadosFormal;
	@XmlElement(name = "CantChequesRechazadosInformal")
	protected int cantChequesRechazadosInformal;
	@XmlElement(name = "MontoCheqyesRechazadosInformal", required = true)
	protected BigDecimal montoCheqyesRechazadosInformal;

	public XMLGregorianCalendar getFechaCorte() {
		return fechaCorte;
	}

	public void setFechaCorte(XMLGregorianCalendar value) {
		this.fechaCorte = value;
	}

	public int getMaxDiasAtraso() {
		return maxDiasAtraso;
	}

	public void setMaxDiasAtraso(int value) {
		this.maxDiasAtraso = value;
	}

	public BigDecimal getPromedioPagos() {
		return promedioPagos;
	}

	public void setPromedioPagos(BigDecimal value) {
		this.promedioPagos = value;
	}

	public BigDecimal getSaldoConsolidado() {
		return saldoConsolidado;
	}

	public void setSaldoConsolidado(BigDecimal value) {
		this.saldoConsolidado = value;
	}

	public BigDecimal getMontoCuotaConsolidado() {
		return montoCuotaConsolidado;
	}

	public void setMontoCuotaConsolidado(BigDecimal value) {
		this.montoCuotaConsolidado = value;
	}

	public BigDecimal getMontoCuotaVencido() {
		return montoCuotaVencido;
	}

	public void setMontoCuotaVencido(BigDecimal value) {
		this.montoCuotaVencido = value;
	}

	public int getMayorPlazoCredito() {
		return mayorPlazoCredito;
	}

	public void setMayorPlazoCredito(int value) {
		this.mayorPlazoCredito = value;
	}

	public int getMenorPlazoCredito() {
		return menorPlazoCredito;
	}

	public void setMenorPlazoCredito(int value) {
		this.menorPlazoCredito = value;
	}

	public int getMaximoCuotasPendientes() {
		return maximoCuotasPendientes;
	}

	public void setMaximoCuotasPendientes(int value) {
		this.maximoCuotasPendientes = value;
	}

	public int getMinimoCuotasPendientes() {
		return minimoCuotasPendientes;
	}

	public void setMinimoCuotasPendientes(int value) {
		this.minimoCuotasPendientes = value;
	}

	public boolean isCuentaConOpR() {
		return cuentaConOpR;
	}

	public void setCuentaConOpR(boolean value) {
		this.cuentaConOpR = value;
	}

	public int getEntidadesAportantesTotal() {
		return entidadesAportantesTotal;
	}

	public void setEntidadesAportantesTotal(int value) {
		this.entidadesAportantesTotal = value;
	}

	public int getEntidadesAportantesLibranza() {
		return entidadesAportantesLibranza;
	}

	public void setEntidadesAportantesLibranza(int value) {
		this.entidadesAportantesLibranza = value;
	}

	public int getEntidadesAportantesConsumo() {
		return entidadesAportantesConsumo;
	}

	public void setEntidadesAportantesConsumo(int value) {
		this.entidadesAportantesConsumo = value;
	}

	public int getCantConsultasRealizadas() {
		return cantConsultasRealizadas;
	}

	public void setCantConsultasRealizadas(int value) {
		this.cantConsultasRealizadas = value;
	}

	public String getTipoGarantiaBCP() {
		return tipoGarantiaBCP;
	}

	public void setTipoGarantiaBCP(String value) {
		this.tipoGarantiaBCP = value;
	}

	public int getCantChequesRechazadosTotal() {
		return cantChequesRechazadosTotal;
	}

	public void setCantChequesRechazadosTotal(int value) {
		this.cantChequesRechazadosTotal = value;
	}

	public BigDecimal getMontosChequesRechazadosTotal() {
		return montosChequesRechazadosTotal;
	}

	public void setMontosChequesRechazadosTotal(BigDecimal value) {
		this.montosChequesRechazadosTotal = value;
	}

	public int getCantChequesRechazadosFormal() {
		return cantChequesRechazadosFormal;
	}

	public void setCantChequesRechazadosFormal(int value) {
		this.cantChequesRechazadosFormal = value;
	}

	public BigDecimal getMontoChequesRechazadosFormal() {
		return montoChequesRechazadosFormal;
	}

	public void setMontoChequesRechazadosFormal(BigDecimal value) {
		this.montoChequesRechazadosFormal = value;
	}

	public int getCantChequesRechazadosInformal() {
		return cantChequesRechazadosInformal;
	}

	public void setCantChequesRechazadosInformal(int value) {
		this.cantChequesRechazadosInformal = value;
	}

	public BigDecimal getMontoCheqyesRechazadosInformal() {
		return montoCheqyesRechazadosInformal;
	}

	public void setMontoCheqyesRechazadosInformal(BigDecimal value) {
		this.montoCheqyesRechazadosInformal = value;
	}
}