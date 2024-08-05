package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recarga")
public class Recarga extends BaseEntity {

	private static final long serialVersionUID = -8094658195735214726L;

	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "monto")
	private Double monto;
	
	@Column(name = "fecha_solicitud")
	private Date fechaSolicitud;
	
	@Column(name = "fecha_aprobacion")
	private Date fechaAprobacion;
	
	@Column(name = "fecha_timeout")
	private Date fechaTimeout;
	
	@Column(name = "fecha_rechazo")
	private Date fechaRechazo;
	
	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "recarga_empresa_id", nullable = false)
	private RecargaEmpresa recargaEmpresa;

	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "moneda_id", nullable = false)
	private Moneda moneda;
	
	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "punto_venta_id", nullable = false)
	private PuntoVenta puntoVenta;

	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "recarga_estado_id", nullable = false)
	private RecargaEstado recargaEstado;
	
	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}

	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}

	public Date getFechaTimeout() {
		return fechaTimeout;
	}

	public void setFechaTimeout(Date fechaTimeout) {
		this.fechaTimeout = fechaTimeout;
	}

	public Date getFechaRechazo() {
		return fechaRechazo;
	}

	public void setFechaRechazo(Date fechaRechazo) {
		this.fechaRechazo = fechaRechazo;
	}

	public RecargaEmpresa getRecargaEmpresa() {
		return recargaEmpresa;
	}

	public void setRecargaEmpresa(RecargaEmpresa recargaEmpresa) {
		this.recargaEmpresa = recargaEmpresa;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public RecargaEstado getRecargaEstado() {
		return recargaEstado;
	}

	public void setRecargaEstado(RecargaEstado recargaEstado) {
		this.recargaEstado = recargaEstado;
	}
}