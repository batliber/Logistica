package uy.com.amensg.logistica.entities;

public class ListPuntoVentaByBarrioLocationAwareTO {

	private Long barrioId;
	private Double latitud;
	private Double longitud;

	public Long getBarrioId() {
		return barrioId;
	}

	public void setBarrioId(Long barrioId) {
		this.barrioId = barrioId;
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