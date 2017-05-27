package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sexo")
public class Sexo extends BaseEntity {

	private static final long serialVersionUID = -4717612320128662309L;

	@Column(name = "descripcion")
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}