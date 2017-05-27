package uy.com.amensg.logistica.entities;

import java.io.Serializable;

public class DatosElegibilidadFinanciacion implements Serializable {

	private static final long serialVersionUID = 4454909573083137727L;

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