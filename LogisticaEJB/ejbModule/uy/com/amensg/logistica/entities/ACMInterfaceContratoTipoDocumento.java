package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table (name = "acm_interface_contrato_tipo_documento")
public class ACMInterfaceContratoTipoDocumento extends BaseEntity {

	private static final long serialVersionUID = -7124761028056869018L;

	@Column(name = "descripcion")
	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}