package uy.com.amensg.logistica.entities;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class DatosElegibilidadFinanciacionTO {

	private Long elegibilidad;
	private String mensaje;

	public Long getElegibilidad() {
		return elegibilidad;
	}

	public void setElegibilidad(Long elegibilidad) {
		this.elegibilidad = elegibilidad;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}