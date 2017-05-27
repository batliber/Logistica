package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "riesgo_crediticio")
public class RiesgoCrediticio extends BaseEntity {

	private static final long serialVersionUID = -338287130296766473L;

	@Column(name = "documento")
	private String documento;
	
	@Column(name = "fecha_vigencia_desde")
	private Date fechaVigenciaDesde;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado_riesgo_crediticio_id", nullable = true)
	private EstadoRiesgoCrediticio estadoRiesgoCrediticio;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "tipo_control_riesgo_crediticio_id", nullable = true)
	private TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "calificacion_riesgo_crediticio_antel_id", nullable = true)
	private CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "calificacion_riesgo_crediticio_bcu_id", nullable = true)
	private CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU;

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getFechaVigenciaDesde() {
		return fechaVigenciaDesde;
	}

	public void setFechaVigenciaDesde(Date fechaVigenciaDesde) {
		this.fechaVigenciaDesde = fechaVigenciaDesde;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public EstadoRiesgoCrediticio getEstadoRiesgoCrediticio() {
		return estadoRiesgoCrediticio;
	}

	public void setEstadoRiesgoCrediticio(EstadoRiesgoCrediticio estadoRiesgoCrediticio) {
		this.estadoRiesgoCrediticio = estadoRiesgoCrediticio;
	}

	public TipoControlRiesgoCrediticio getTipoControlRiesgoCrediticio() {
		return tipoControlRiesgoCrediticio;
	}

	public void setTipoControlRiesgoCrediticio(TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio) {
		this.tipoControlRiesgoCrediticio = tipoControlRiesgoCrediticio;
	}

	public CalificacionRiesgoCrediticioAntel getCalificacionRiesgoCrediticioAntel() {
		return calificacionRiesgoCrediticioAntel;
	}

	public void setCalificacionRiesgoCrediticioAntel(CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel) {
		this.calificacionRiesgoCrediticioAntel = calificacionRiesgoCrediticioAntel;
	}

	public CalificacionRiesgoCrediticioBCU getCalificacionRiesgoCrediticioBCU() {
		return calificacionRiesgoCrediticioBCU;
	}

	public void setCalificacionRiesgoCrediticioBCU(CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU) {
		this.calificacionRiesgoCrediticioBCU = calificacionRiesgoCrediticioBCU;
	}
}