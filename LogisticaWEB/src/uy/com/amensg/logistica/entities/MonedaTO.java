package uy.com.amensg.logistica.entities;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class MonedaTO extends BaseTO {

	private String nombre;
	private String simbolo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
}