package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activacion_lote")
public class ActivacionLote extends BaseEntity {

	private static final long serialVersionUID = -3007520803708474251L;

	@Column(name = "numero")
	private Long numero;
	
	@Column(name = "nombre_archivo")
	private String nombreArchivo;
	
	@Column(name = "fecha_importacion")
	private Date fechaImportacion;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Date getFechaImportacion() {
		return fechaImportacion;
	}

	public void setFechaImportacion(Date fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}