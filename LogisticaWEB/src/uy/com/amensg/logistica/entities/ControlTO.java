package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ControlTO extends BaseTO {

	private Long mid;
	private Long cargaInicial;
	private Long montoCargar;
	private Long montoTotal;
	private Date fechaControl;
	private Date mesControl;
	private Date fechaActivacion;
	private Date fechaImportacion;
	private Date fechaVencimiento;
	private Date fechaConexion;
	private Date fechaAsignacionDistribuidor;
	private Date fechaAsignacionPuntoVenta;
	private EmpresaTO empresa;
	private TipoControlTO tipoControl;
	private EstadoControlTO estadoControl;
	private UsuarioTO distribuidor;
	private PuntoVentaTO puntoVenta;

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

	public UsuarioTO getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(UsuarioTO distribuidor) {
		this.distribuidor = distribuidor;
	}

	public PuntoVentaTO getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVentaTO puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public TipoControlTO getTipoControl() {
		return tipoControl;
	}

	public void setTipoControl(TipoControlTO tipoControl) {
		this.tipoControl = tipoControl;
	}

	public EstadoControlTO getEstadoControl() {
		return estadoControl;
	}

	public void setEstadoControl(EstadoControlTO estadoControl) {
		this.estadoControl = estadoControl;
	}
}