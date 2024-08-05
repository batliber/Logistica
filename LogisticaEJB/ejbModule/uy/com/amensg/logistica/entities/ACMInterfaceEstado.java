package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "acm_interface_estado")
public class ACMInterfaceEstado extends BaseEntity {

	private static final long serialVersionUID = -6753417204533044674L;

	@Column(name = "descripcion")
	private String descripcion;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}