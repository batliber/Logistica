package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activacion")
public class Activacion extends BaseEntity {

	private static final long serialVersionUID = 1796252337841102923L;

	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "fecha_activacion")
	private Date fechaActivacion;
	
	@Column(name = "fecha_vencimiento")
	private Date fechaVencimiento;
	
	@Column(name = "chip")
	private String chip;
	
	@Column(name = "fecha_importacion")
	private Date fechaImportacion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "tipo_activacion_id", nullable = true)
	private TipoActivacion tipoActivacion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado_activacion_id", nullable = true)
	private EstadoActivacion estadoActivacion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "activacion_lote_id", nullable = true)
	private ActivacionLote activacionLote;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinTable(
		name = "activacion_sublote_activacion", 
		joinColumns = @JoinColumn(name = "activacion_id"),
		inverseJoinColumns = @JoinColumn(name = "activacion_sublote_id")
	)
	private ActivacionSublote activacionSublote;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "liquidacion_id", nullable = true)
	private Liquidacion liquidacion;
	
	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getChip() {
		return chip;
	}

	public void setChip(String chip) {
		this.chip = chip;
	}

	public Date getFechaImportacion() {
		return fechaImportacion;
	}

	public void setFechaImportacion(Date fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public TipoActivacion getTipoActivacion() {
		return tipoActivacion;
	}

	public void setTipoActivacion(TipoActivacion tipoActivacion) {
		this.tipoActivacion = tipoActivacion;
	}

	public EstadoActivacion getEstadoActivacion() {
		return estadoActivacion;
	}

	public void setEstadoActivacion(EstadoActivacion estadoActivacion) {
		this.estadoActivacion = estadoActivacion;
	}

	public ActivacionLote getActivacionLote() {
		return activacionLote;
	}

	public void setActivacionLote(ActivacionLote activacionLote) {
		this.activacionLote = activacionLote;
	}

	public ActivacionSublote getActivacionSublote() {
		return activacionSublote;
	}

	public void setActivacionSublote(ActivacionSublote activacionSublote) {
		this.activacionSublote = activacionSublote;
	}

	public Liquidacion getLiquidacion() {
		return liquidacion;
	}

	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}
}