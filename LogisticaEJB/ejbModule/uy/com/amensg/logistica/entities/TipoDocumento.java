package uy.com.amensg.logistica.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_documento")
public class TipoDocumento extends BaseEntity {

	private static final long serialVersionUID = 2942192169629641234L;
	
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "abreviacion")
	private String abreviacion;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAbreviacion() {
		return abreviacion;
	}

	public void setAbreviacion(String abreviacion) {
		this.abreviacion = abreviacion;
	}
}