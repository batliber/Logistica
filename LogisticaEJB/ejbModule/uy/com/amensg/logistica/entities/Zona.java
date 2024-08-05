package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "zona")
public class Zona extends BaseEntity {

	private static final long serialVersionUID = -3393907265198152521L;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "detalle")
	private String detalle;
	
	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name = "departamento_id", nullable = false)
	private Departamento departamento;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
}