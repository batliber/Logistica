package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "calificacion_riesgo_crediticio_bcu")
public class CalificacionRiesgoCrediticioBCU extends BaseEntity {

	private static final long serialVersionUID = -5377457251993009674L;
	
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "orden")
	private Long orden;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}
}