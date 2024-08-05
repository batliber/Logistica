package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "acm_interface_riesgo_crediticio")
public class ACMInterfaceRiesgoCrediticio extends BaseEntity {

	private static final long serialVersionUID = 2189778403684327394L;

	@Column(name = "fecha_analisis")
	private Date fechaAnalisis;
	
	@Column(name = "documento")
	private String documento;
	
	@Column(name = "fecha_celular")
	private Date fechaCelular;
	
	@Column(name = "deuda_celular")
	private String deudaCelular;
	
	@Column(name = "riesgo_crediticio_celular")
	private String riesgoCrediticioCelular;
			
	@Column(name = "contratos_celular")
	private Long contratosCelular;
	
	@Column(name = "contratos_sola_firma_celular")
	private Long contratosSolaFirmaCelular;
	
	@Column(name = "contratos_garantia_celular")
	private Long contratosGarantiaCelular;
	
	@Column(name = "saldo_ayuda_economica_celular")
	private Double saldoAyudaEconomicaCelular;
	
	@Column(name = "numero_cliente_fijo")
	private Long numeroClienteFijo;
	
	@Column(name = "nombre_cliente_fijo")
	private String nombreClienteFijo;
	
	@Column(name = "estado_deuda_cliente_fijo")
	private String estadoDeudaClienteFijo;
	
	@Column(name = "numero_cliente_movil")
	private Long numeroClienteMovil;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
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

	public Date getFechaAnalisis() {
		return fechaAnalisis;
	}

	public void setFechaAnalisis(Date fechaAnalisis) {
		this.fechaAnalisis = fechaAnalisis;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}