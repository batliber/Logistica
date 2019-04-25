package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ACMInterfaceRiesgoCrediticioTO extends BaseTO {

	private Date fechaAnalisis;
	private String documento;
	private Date fechaCelular;
	private String deudaCelular;
	private String riesgoCrediticioCelular;
	private Long contratosCelular;
	private Long contratosSolaFirmaCelular;
	private Long contratosGarantiaCelular;
	private Double saldoAyudaEconomicaCelular;
	private Long numeroClienteFijo;
	private String nombreClienteFijo;
	private String estadoDeudaClienteFijo;
	private Long numeroClienteMovil;
	private EmpresaTO empresa;

	public Date getFechaAnalisis() {
		return fechaAnalisis;
	}

	public void setFechaAnalisis(Date fechaAnalisis) {
		this.fechaAnalisis = fechaAnalisis;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getFechaCelular() {
		return fechaCelular;
	}

	public void setFechaCelular(Date fechaCelular) {
		this.fechaCelular = fechaCelular;
	}

	public String getDeudaCelular() {
		return deudaCelular;
	}

	public void setDeudaCelular(String deudaCelular) {
		this.deudaCelular = deudaCelular;
	}

	public String getRiesgoCrediticioCelular() {
		return riesgoCrediticioCelular;
	}

	public void setRiesgoCrediticioCelular(String riesgoCrediticioCelular) {
		this.riesgoCrediticioCelular = riesgoCrediticioCelular;
	}

	public Long getContratosCelular() {
		return contratosCelular;
	}

	public void setContratosCelular(Long contratosCelular) {
		this.contratosCelular = contratosCelular;
	}

	public Long getContratosSolaFirmaCelular() {
		return contratosSolaFirmaCelular;
	}

	public void setContratosSolaFirmaCelular(Long contratosSolaFirmaCelular) {
		this.contratosSolaFirmaCelular = contratosSolaFirmaCelular;
	}

	public Long getContratosGarantiaCelular() {
		return contratosGarantiaCelular;
	}

	public void setContratosGarantiaCelular(Long contratosGarantiaCelular) {
		this.contratosGarantiaCelular = contratosGarantiaCelular;
	}

	public Double getSaldoAyudaEconomicaCelular() {
		return saldoAyudaEconomicaCelular;
	}

	public void setSaldoAyudaEconomicaCelular(Double saldoAyudaEconomicaCelular) {
		this.saldoAyudaEconomicaCelular = saldoAyudaEconomicaCelular;
	}

	public Long getNumeroClienteFijo() {
		return numeroClienteFijo;
	}

	public void setNumeroClienteFijo(Long numeroClienteFijo) {
		this.numeroClienteFijo = numeroClienteFijo;
	}

	public String getNombreClienteFijo() {
		return nombreClienteFijo;
	}

	public void setNombreClienteFijo(String nombreClienteFijo) {
		this.nombreClienteFijo = nombreClienteFijo;
	}

	public String getEstadoDeudaClienteFijo() {
		return estadoDeudaClienteFijo;
	}

	public void setEstadoDeudaClienteFijo(String estadoDeudaClienteFijo) {
		this.estadoDeudaClienteFijo = estadoDeudaClienteFijo;
	}

	public Long getNumeroClienteMovil() {
		return numeroClienteMovil;
	}

	public void setNumeroClienteMovil(Long numeroClienteMovil) {
		this.numeroClienteMovil = numeroClienteMovil;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}
}