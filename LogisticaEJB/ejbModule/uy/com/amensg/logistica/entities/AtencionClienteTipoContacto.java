package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "atencion_cliente_tipo_contacto")
public class AtencionClienteTipoContacto extends BaseEntity {

	private static final long serialVersionUID = -9165826976769643099L;
	
	@Column(name = "descripcion")
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}