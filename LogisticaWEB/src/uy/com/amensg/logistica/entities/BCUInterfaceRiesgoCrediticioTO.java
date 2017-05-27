package uy.com.amensg.logistica.entities;

import java.util.Collection;
import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class BCUInterfaceRiesgoCrediticioTO extends BaseTO {

	private Date fechaAnalisis;
	private String documento;
	private String periodo;
	private String nombreCompleto;
	private String actividad;
	private Double vigente;
	private Double vigenteNoAutoliquidable;
	private Double garantiasComputables;
	private Double garantiasNoComputables;
	private Boolean castigadoPorAtraso;
	private Boolean castigadoPorQuitasYDesistimiento;
	private Double previsionesTotales;
	private Double contingencias;
	private Double otorgantesGarantias;
	private EmpresaTO empresa;
	private Collection<BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO> bcuInterfaceRiesgoCrediticioInstitucionFinancieras;

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

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public Collection<BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO> getBcuInterfaceRiesgoCrediticioInstitucionFinancieras() {
		return bcuInterfaceRiesgoCrediticioInstitucionFinancieras;
	}

	public void setBcuInterfaceRiesgoCrediticioInstitucionFinancieras(
			Collection<BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO> bcuInterfaceRiesgoCrediticioInstitucionFinancieras) {
		this.bcuInterfaceRiesgoCrediticioInstitucionFinancieras = bcuInterfaceRiesgoCrediticioInstitucionFinancieras;
	}
}