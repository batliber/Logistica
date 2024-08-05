package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "visita_punto_venta_distribuidor")
public class VisitaPuntoVentaDistribuidor extends BaseEntity {

	private static final long serialVersionUID = -1134995122108377954L;

	@Column(name = "fecha_asignacion")
	private Date fechaAsignacion;
	
	@Column(name = "fecha_visita")
	private Date fechaVisita;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "punto_venta_id", nullable = true)
	private PuntoVenta puntoVenta;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "distribuidor_id", nullable = true)
	private Usuario distribuidor;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado_visita_punto_venta_distribuidor_id", nullable = true)
	private EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor;

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public Date getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Usuario getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(Usuario distribuidor) {
		this.distribuidor = distribuidor;
	}

	public EstadoVisitaPuntoVentaDistribuidor getEstadoVisitaPuntoVentaDistribuidor() {
		return estadoVisitaPuntoVentaDistribuidor;
	}

	public void setEstadoVisitaPuntoVentaDistribuidor(EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor) {
		this.estadoVisitaPuntoVentaDistribuidor = estadoVisitaPuntoVentaDistribuidor;
	}
}