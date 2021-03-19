package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "situacion_riesgo_crediticio_paraguay")
public class SituacionRiesgoCrediticioParaguay extends BaseEntity {

	private static final long serialVersionUID = 1206739249353830880L;

	@Column(name = "descripcion")
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}