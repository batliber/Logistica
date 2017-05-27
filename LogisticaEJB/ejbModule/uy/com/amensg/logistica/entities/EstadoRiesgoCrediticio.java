package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "estado_riesgo_crediticio")
public class EstadoRiesgoCrediticio extends BaseEntity {

	private static final long serialVersionUID = 8108589699989800054L;
	
	@Column(name = "nombre")
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}