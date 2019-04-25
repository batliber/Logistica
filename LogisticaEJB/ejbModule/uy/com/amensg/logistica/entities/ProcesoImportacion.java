package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "proceso_importacion")
public class ProcesoImportacion extends BaseEntity {

	private static final long serialVersionUID = 6885286271464245015L;

	@Column(name = "nombre_archivo")
	private String nombreArchivo;
	
	@Column(name = "fecha_inicio")
	private Date fechaInicio;
	
	@Column(name = "fecha_fin")
	private Date fechaFin;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado_proceso_importacion_id", nullable = true)
	private EstadoProcesoImportacion estadoProcesoImportacion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "tipo_proceso_importacion_id", nullable = true)
	private TipoProcesoImportacion tipoProcesoImportacion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = true)
	private Usuario usuario;

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public EstadoProcesoImportacion getEstadoProcesoImportacion() {
		return estadoProcesoImportacion;
	}

	public void setEstadoProcesoImportacion(EstadoProcesoImportacion estadoProcesoImportacion) {
		this.estadoProcesoImportacion = estadoProcesoImportacion;
	}

	public TipoProcesoImportacion getTipoProcesoImportacion() {
		return tipoProcesoImportacion;
	}

	public void setTipoProcesoImportacion(TipoProcesoImportacion tipoProcesoImportacion) {
		this.tipoProcesoImportacion = tipoProcesoImportacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}