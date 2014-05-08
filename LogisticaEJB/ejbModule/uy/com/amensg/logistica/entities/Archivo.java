package uy.com.amensg.logistica.entities;

import java.io.Serializable;

public class Archivo implements Serializable {

	private static final long serialVersionUID = 780344040522645129L;

	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}