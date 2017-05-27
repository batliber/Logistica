package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tarjeta_credito")
public class TarjetaCredito extends BaseEntity {

	private static final long serialVersionUID = 5192716199416723327L;
	
	@Column(name = "nombre")
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}