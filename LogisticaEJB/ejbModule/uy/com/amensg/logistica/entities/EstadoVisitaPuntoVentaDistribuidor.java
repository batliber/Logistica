package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "estado_visita_punto_venta_distribuidor")
public class EstadoVisitaPuntoVentaDistribuidor extends BaseEntity {

	private static final long serialVersionUID = -4730844824563248324L;
	
	@Column(name = "nombre")
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}