package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "calificacion_riesgo_crediticio_antel")
public class CalificacionRiesgoCrediticioAntel extends BaseEntity {

	private static final long serialVersionUID = 571391418686706622L;

	@Column(name = "descripcion")
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}