package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class BCUInterfaceRiesgoCrediticioInstitucionFinancieraTO extends BaseTO {

	private Date fechaAnalisis;
	private String documento;
	private String institucionFinanciera;
	private String calificacion;
	private Double vigente;
	private Double vigenteNoAutoliquidable;
	private Double previsionesTotales;
	private Double contingencias;
	private EmpresaTO empresa;
	private BCUInterfaceRiesgoCrediticioTO bcuInterfaceRiesgoCrediticio;

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

	public String getInstitucionFinanciera() {
		return institucionFinanciera;
	}

	public void setInstitucionFinanciera(String institucionFinanciera) {
		this.institucionFinanciera = institucionFinanciera;
	}

	public String getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(String calificacion) {
		this.calificacion = calificacion;
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

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public BCUInterfaceRiesgoCrediticioTO getBcuInterfaceRiesgoCrediticio() {
		return bcuInterfaceRiesgoCrediticio;
	}

	public void setBcuInterfaceRiesgoCrediticio(BCUInterfaceRiesgoCrediticioTO bcuInterfaceRiesgoCrediticio) {
		this.bcuInterfaceRiesgoCrediticio = bcuInterfaceRiesgoCrediticio;
	}
}