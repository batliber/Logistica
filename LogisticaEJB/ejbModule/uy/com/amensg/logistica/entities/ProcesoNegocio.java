package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "proceso_negocio")
public class ProcesoNegocio extends BaseEntity {

	private static final long serialVersionUID = 392655172915830218L;
	
	@Column(name = "nombre")
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}