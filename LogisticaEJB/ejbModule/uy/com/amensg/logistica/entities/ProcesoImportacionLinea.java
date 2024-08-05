package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "proceso_importacion_linea")
public class ProcesoImportacionLinea extends BaseEntity {

	private static final long serialVersionUID = -7535460171604825452L;

	@Column(name = "numero")
	private Long numero;
	
	@Column(name = "linea")
	private String linea;
	
	@Column(name = "clave")
	private String clave;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "proceso_importacion_id", nullable = false)
	private ProcesoImportacion procesoImportacion;
	
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getLinea() {
		return linea;
	}
	public void setLinea(String linea) {
		this.linea = linea;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public ProcesoImportacion getProcesoImportacion() {
		return procesoImportacion;
	}
	public void setProcesoImportacion(ProcesoImportacion procesoImportacion) {
		this.procesoImportacion = procesoImportacion;
	}
}