package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ProcesoImportacionTO extends BaseTO {

	private String nombreArchivo;
	private Date fechaInicio;
	private Date fechaFin;
	private String observaciones;
	private EstadoProcesoImportacionTO estadoProcesoImportacion;
	private TipoProcesoImportacionTO tipoProcesoImportacion;
	private UsuarioTO usuario;

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

	public EstadoProcesoImportacionTO getEstadoProcesoImportacion() {
		return estadoProcesoImportacion;
	}

	public void setEstadoProcesoImportacion(EstadoProcesoImportacionTO estadoProcesoImportacion) {
		this.estadoProcesoImportacion = estadoProcesoImportacion;
	}

	public TipoProcesoImportacionTO getTipoProcesoImportacion() {
		return tipoProcesoImportacion;
	}

	public void setTipoProcesoImportacion(TipoProcesoImportacionTO tipoProcesoImportacion) {
		this.tipoProcesoImportacion = tipoProcesoImportacion;
	}

	public UsuarioTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioTO usuario) {
		this.usuario = usuario;
	}
}