package uy.com.amensg.logistica.web.entities;

public class DatosGraficoResultadoEntregasANTELTO {

	private String resultadoEntregaDistribucionDescripcion;
	private Long cantidad;

	public String getResultadoEntregaDistribucionDescripcion() {
		return resultadoEntregaDistribucionDescripcion;
	}

	public void setResultadoEntregaDistribucionDescripcion(String resultadoEntregaDistribucionDescripcion) {
		this.resultadoEntregaDistribucionDescripcion = resultadoEntregaDistribucionDescripcion;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
}