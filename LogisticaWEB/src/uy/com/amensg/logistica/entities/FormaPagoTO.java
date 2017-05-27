package uy.com.amensg.logistica.entities;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class FormaPagoTO extends BaseTO {

	private String descripcion;
	private Long orden;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}
}