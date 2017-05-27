package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bcu_interface_riesgo_crediticio_institucion_financiera")
public class BCUInterfaceRiesgoCrediticioInstitucionFinanciera extends BaseEntity {
	
	private static final long serialVersionUID = 190453516376665359L;

	@Column(name = "fecha_analisis")
	private Date fechaAnalisis;
	
	@Column(name = "documento")
	private String documento;
	
	@Column(name = "institucion_financiera")
	private String institucionFinanciera;
	
	@Column(name = "calificacion")
	private String calificacion;
	
	@Column(name = "vigente")
	private Double vigente;
	
	@Column(name = "vigente_no_autoliquidable")
	private Double vigenteNoAutoliquidable;
	
	@Column(name = "previsiones_totales")
	private Double previsionesTotales;
	
	@Column(name = "contingencias")
	private Double contingencias;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "bcu_interface_riesgo_crediticio_id", nullable = false)
	private BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio;
	
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getActividad() {
		return calificacion;
	}

	public void setActividad(String actividad) {
		this.calificacion = actividad;
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

	public BCUInterfaceRiesgoCrediticio getBcuInterfaceRiesgoCrediticio() {
		return bcuInterfaceRiesgoCrediticio;
	}

	public void setBcuInterfaceRiesgoCrediticio(BCUInterfaceRiesgoCrediticio bcuInterfaceRiesgoCrediticio) {
		this.bcuInterfaceRiesgoCrediticio = bcuInterfaceRiesgoCrediticio;
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