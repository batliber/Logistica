package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class VisitaPuntoVentaDistribuidorTO extends BaseTO {

	private Date fechaAsignacion;
	private Date fechaVisita;
	private String observaciones;
	private PuntoVentaTO puntoVenta;
	private UsuarioTO distribuidor;
	private EstadoVisitaPuntoVentaDistribuidorTO estadoVisitaPuntoVentaDistribuidor;

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

	public PuntoVentaTO getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVentaTO puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public UsuarioTO getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(UsuarioTO distribuidor) {
		this.distribuidor = distribuidor;
	}

	public EstadoVisitaPuntoVentaDistribuidorTO getEstadoVisitaPuntoVentaDistribuidor() {
		return estadoVisitaPuntoVentaDistribuidor;
	}

	public void setEstadoVisitaPuntoVentaDistribuidor(EstadoVisitaPuntoVentaDistribuidorTO estadoVisitaPuntoVentaDistribuidor) {
		this.estadoVisitaPuntoVentaDistribuidor = estadoVisitaPuntoVentaDistribuidor;
	}
}