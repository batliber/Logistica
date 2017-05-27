package uy.com.amensg.logistica.entities;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class TipoControlRiesgoCrediticioTO extends BaseTO {

	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}