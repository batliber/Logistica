package uy.com.amensg.logistica.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bcu_interface_riesgo_crediticio")
public class BCUInterfaceRiesgoCrediticio extends BaseEntity {
	
	private static final long serialVersionUID = -1829035393172844383L;

	@Column(name = "fecha_analisis")
	private Date fechaAnalisis;
	
	@Column(name = "documento")
	private String documento;
	
	@Column(name = "periodo")
	private String periodo;
	
	@Column(name = "nombre_completo")
	private String nombreCompleto;
	
	@Column(name = "actividad")
	private String actividad;
	
	@Column(name = "vigente")
	private Double vigente;
	
	@Column(name = "vigente_no_autoliquidable")
	private Double vigenteNoAutoliquidable;
	
	@Column(name = "garantias_computables")
	private Double garantiasComputables;
	
	@Column(name = "garantias_no_computables")
	private Double garantiasNoComputables;
	
	@Column(name = "castigado_por_atraso")
	private Boolean castigadoPorAtraso;
	
	@Column(name = "castigado_por_quitas_y_desistimiento")
	private Boolean castigadoPorQuitasYDesistimiento;
	
	@Column(name = "previsiones_totales")
	private Double previsionesTotales;
	
	@Column(name = "contingencias")
	private Double contingencias;
			
	@Column(name = "otorgantes_garantias")
	private Double otorgantesGarantias;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
	@OneToMany(mappedBy = "bcuInterfaceRiesgoCrediticio", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<BCUInterfaceRiesgoCrediticioInstitucionFinanciera> bcuInterfaceRiesgoCrediticioInstitucionFinancieras;
	
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public Double getVigente() {
		return vigente;
	}

	public void setVigente(Double vigente) {
		this.vigente = vigente;
	}

	public Double getVigenteNoAutoliquidable() {
		return vigenteNoAutoliquidable;
	}

	public void setVigenteNoAutoliquidable(Double vigenteNoAutoliquidable) {
		this.vigenteNoAutoliquidable = vigenteNoAutoliquidable;
	}

	public Double getGarantiasComputables() {
		return garantiasComputables;
	}

	public void setGarantiasComputables(Double garantiasComputables) {
		this.garantiasComputables = garantiasComputables;
	}

	public Double getGarantiasNoComputables() {
		return garantiasNoComputables;
	}

	public void setGarantiasNoComputables(Double garantiasNoComputables) {
		this.garantiasNoComputables = garantiasNoComputables;
	}

	public Boolean getCastigadoPorAtraso() {
		return castigadoPorAtraso;
	}

	public void setCastigadoPorAtraso(Boolean castigadoPorAtraso) {
		this.castigadoPorAtraso = castigadoPorAtraso;
	}

	public Boolean getCastigadoPorQuitasYDesistimiento() {
		return castigadoPorQuitasYDesistimiento;
	}

	public void setCastigadoPorQuitasYDesistimiento(Boolean castigadoPorQuitasYDesistimiento) {
		this.castigadoPorQuitasYDesistimiento = castigadoPorQuitasYDesistimiento;
	}

	public Double getPrevisionesTotales() {
		return previsionesTotales;
	}

	public void setPrevisionesTotales(Double previsionesTotales) {
		this.previsionesTotales = previsionesTotales;
	}

	public Double getContingencias() {
		return contingencias;
	}

	public void setContingencias(Double contingencias) {
		this.contingencias = contingencias;
	}

	public Double getOtorgantesGarantias() {
		return otorgantesGarantias;
	}

	public void setOtorgantesGarantias(Double otorgantesGarantias) {
		this.otorgantesGarantias = otorgantesGarantias;
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

	public Collection<BCUInterfaceRiesgoCrediticioInstitucionFinanciera> getBcuInterfaceRiesgoCrediticioInstitucionFinancieras() {
		return bcuInterfaceRiesgoCrediticioInstitucionFinancieras;
	}

	public void setBcuInterfaceRiesgoCrediticioInstitucionFinancieras(
			Collection<BCUInterfaceRiesgoCrediticioInstitucionFinanciera> bcuInterfaceRiesgoCrediticioInstitucionFinancieras) {
		this.bcuInterfaceRiesgoCrediticioInstitucionFinancieras = bcuInterfaceRiesgoCrediticioInstitucionFinancieras;
	}
}