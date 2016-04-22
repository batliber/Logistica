package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "producto")
public class Producto extends BaseEntity {

	private static final long serialVersionUID = 8869650120153892610L;
	
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fecha_baja")
	private Date fechaBaja;
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}
}