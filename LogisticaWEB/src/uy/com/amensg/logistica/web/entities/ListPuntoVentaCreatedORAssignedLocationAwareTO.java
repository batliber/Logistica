package uy.com.amensg.logistica.entities;

public class ListPuntoVentaCreatedORAssignedLocationAwareTO {

	private Long departamentoId;
	private Long barrioId;
	private Long puntoVentaId;
	private Long estadoVisitaPuntoVentaDistribuidorId;
	private Double latitud;
	private Double longitud;

	public Long getDepartamentoId() {
		return departamentoId;
	}

	public void setDepartamentoId(Long departamentoId) {
		this.departamentoId = departamentoId;
	}

	public Long getBarrioId() {
		return barrioId;
	}

	public void setBarrioId(Long barrioId) {
		this.barrioId = barrioId;
	}

	public Long getPuntoVentaId() {
		return puntoVentaId;
	}

	public void setPuntoVentaId(Long puntoVentaId) {
		this.puntoVentaId = puntoVentaId;
	}

	public Long getEstadoVisitaPuntoVentaDistribuidorId() {
		return estadoVisitaPuntoVentaDistribuidorId;
	}

	public void setEstadoVisitaPuntoVentaDistribuidorId(Long estadoVisitaPuntoVentaDistribuidorId) {
		this.estadoVisitaPuntoVentaDistribuidorId = estadoVisitaPuntoVentaDistribuidorId;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
}