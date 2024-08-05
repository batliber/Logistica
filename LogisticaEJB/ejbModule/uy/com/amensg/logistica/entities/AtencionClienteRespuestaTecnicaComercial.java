package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "atencion_cliente_respuesta_tecnica_comercial")
public class AtencionClienteRespuestaTecnicaComercial extends BaseEntity {

	private static final long serialVersionUID = 8149328281473717765L;
	
	@Column(name = "descripcion")
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}