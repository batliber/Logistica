package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "zona")
public class Zona extends BaseEntity {

	private static final long serialVersionUID = -3393907265198152521L;

	@Column(name = "nombre")
	private String nombre;

	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "departamento_id", nullable = false)
	private Departamento departamento;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
}