package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "marca")
public class Marca extends BaseEntity {

	private static final long serialVersionUID = 8932078783529342384L;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "fecha_baja")
	private Date fechaBaja;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
}