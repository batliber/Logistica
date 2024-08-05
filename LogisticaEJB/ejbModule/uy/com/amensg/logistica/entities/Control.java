package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
	
	@Column(name = "monto_total")
	private Long montoTotal;
	
	@Column(name = "fecha_control")
	private Date fechaControl;
	
	@Column(name = "mes_control")
	private Date mesControl;
	
	@Column(name = "fecha_activacion")
	private Date fechaActivacion;
	
	@Column(name = "fecha_importacion")
	private Date fechaImportacion;
	
	@Column(name = "fecha_vencimiento")
	private Date fechaVencimiento;
	
	@Column(name = "fecha_conexion")
	private Date fechaConexion;
	
	@Column(name = "fecha_asignacion_distribuidor")
	private Date fechaAsignacionDistribuidor;
	
	@Column(name = "fecha_asignacion_punto_venta")
	private Date fechaAsignacionPuntoVenta;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "tipo_control_id", nullable = true)
	private TipoControl tipoControl;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado_control_id", nullable = true)
	private EstadoControl estadoControl;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "distribuidor_id", nullable = true)
	private Usuario distribuidor;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "punto_venta_id", nullable = true)
	private PuntoVenta puntoVenta;
	
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

	public Long getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Long montoTotal) {
		this.montoTotal = montoTotal;
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

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Date getFechaConexion() {
		return fechaConexion;
	}

	public void setFechaConexion(Date fechaConexion) {
		this.fechaConexion = fechaConexion;
	}

	public Date getFechaAsignacionDistribuidor() {
		return fechaAsignacionDistribuidor;
	}

	public void setFechaAsignacionDistribuidor(Date fechaAsignacionDistribuidor) {
		this.fechaAsignacionDistribuidor = fechaAsignacionDistribuidor;
	}

	public Date getFechaAsignacionPuntoVenta() {
		return fechaAsignacionPuntoVenta;
	}

	public void setFechaAsignacionPuntoVenta(Date fechaAsignacionPuntoVenta) {
		this.fechaAsignacionPuntoVenta = fechaAsignacionPuntoVenta;
	}

	public Usuario getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(Usuario distribuidor) {
		this.distribuidor = distribuidor;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
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