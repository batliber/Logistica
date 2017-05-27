package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_control_riesgo_crediticio")
public class TipoControlRiesgoCrediticio extends BaseEntity {

	private static final long serialVersionUID = -4435150898044639870L;

	@Column(name = "descripcion")
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}