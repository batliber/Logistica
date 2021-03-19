package uy.com.amensg.logistica.entities;

import java.io.Serializable;

public class DatosEstadisticasResultadoEntregasANTEL implements Serializable {

	private static final long serialVersionUID = -6748886880776972587L;
	
	private String resultadoEntregaDistribucion;
	private Long cantidad;

	public String getResultadoEntregaDistribucion() {
		return resultadoEntregaDistribucion;
	}

	public void setResultadoEntregaDistribucion(String resultadoEntregaDistribucion) {
		this.resultadoEntregaDistribucion = resultadoEntregaDistribucion;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
}