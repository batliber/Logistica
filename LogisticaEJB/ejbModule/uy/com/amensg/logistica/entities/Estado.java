package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "estado")
public class Estado extends BaseEntity {

	private static final long serialVersionUID = -6973921344515878539L;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "vendido")
	private Boolean vendido;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getVendido() {
		return vendido;
	}

	public void setVendido(Boolean vendido) {
		this.vendido = vendido;
	}
}