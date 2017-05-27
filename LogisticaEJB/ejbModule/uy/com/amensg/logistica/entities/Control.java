package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "control")
public class Control extends BaseEntity {

	private static final long serialVersionUID = 1699513562544028312L;

	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "carga_inicial")
	private Long cargaInicial;
	
	@Column(name = "monto_cargar")
	private Long montoCargar;
	
	@Column(name = "fecha_control")
	private Date fechaControl;
	
	@Column(name = "mes_control")
	private Date mesControl;
	
	@Column(name = "fecha_activacion")
	private Date fechaActivacion;
	
	@Column(name = "fecha_importacion")
	private Date fechaImportacion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "tipo_control_id", nullable = true)
	private TipoControl tipoControl;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado_control_id", nullable = true)
	private EstadoControl estadoControl;

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getCargaInicial() {
		return cargaInicial;
	}

	public void setCargaInicial(Long cargaInicial) {
		this.cargaInicial = cargaInicial;
	}

	public Long getMontoCargar() {
		return montoCargar;
	}

	public void setMontoCargar(Long montoCargar) {
		this.montoCargar = montoCargar;
	}

	public Date getFechaControl() {
		return fechaControl;
	}

	public void setFechaControl(Date fechaControl) {
		this.fechaControl = fechaControl;
	}

	public Date getMesControl() {
		return mesControl;
	}

	public void setMesControl(Date mesControl) {
		this.mesControl = mesControl;
	}

	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
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

	public TipoControl getTipoControl() {
		return tipoControl;
	}

	public void setTipoControl(TipoControl tipoControl) {
		this.tipoControl = tipoControl;
	}

	public EstadoControl getEstadoControl() {
		return estadoControl;
	}

	public void setEstadoControl(EstadoControl estadoControl) {
		this.estadoControl = estadoControl;
	}
}