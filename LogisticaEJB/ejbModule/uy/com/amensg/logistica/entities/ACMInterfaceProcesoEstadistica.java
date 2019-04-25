package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vs_acm_interface_proceso_estadistica")
public class ACMInterfaceProcesoEstadistica implements Serializable {

	private static final long serialVersionUID = 2201062222502297785L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "fecha_inicio")
	private Date fechaInicio;
	
	@Column(name = "fecha_fin")
	private Date fechaFin;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@Column(name = "cantidad_registros_para_procesar")
	private Long cantidadRegistrosParaProcesar;
	
	@Column(name = "cantidad_registros_para_procesar_prioritario")
	private Long cantidadRegistrosParaProcesarPrioritario;
	
	@Column(name = "cantidad_registros_procesado")
	private Long cantidadRegistrosProcesado;
	
	@Column(name = "cantidad_registros_en_proceso")
	private Long cantidadRegistrosEnProceso;
	
	@Column(name = "cantidad_registros_lista_vacia")
	private Long cantidadRegistrosListaVacia;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCantidadRegistrosParaProcesar() {
		return cantidadRegistrosParaProcesar;
	}

	public void setCantidadRegistrosParaProcesar(
			Long cantidadRegistrosParaProcesar) {
		this.cantidadRegistrosParaProcesar = cantidadRegistrosParaProcesar;
	}

	public Long getCantidadRegistrosParaProcesarPrioritario() {
		return cantidadRegistrosParaProcesarPrioritario;
	}

	public void setCantidadRegistrosParaProcesarPrioritario(
			Long cantidadRegistrosParaProcesarPrioritario) {
		this.cantidadRegistrosParaProcesarPrioritario = cantidadRegistrosParaProcesarPrioritario;
	}

	public Long getCantidadRegistrosProcesado() {
		return cantidadRegistrosProcesado;
	}

	public void setCantidadRegistrosProcesado(Long cantidadRegistrosProcesado) {
		this.cantidadRegistrosProcesado = cantidadRegistrosProcesado;
	}

	public Long getCantidadRegistrosEnProceso() {
		return cantidadRegistrosEnProceso;
	}

	public void setCantidadRegistrosEnProceso(Long cantidadRegistrosEnProceso) {
		this.cantidadRegistrosEnProceso = cantidadRegistrosEnProceso;
	}

	public Long getCantidadRegistrosListaVacia() {
		return cantidadRegistrosListaVacia;
	}

	public void setCantidadRegistrosListaVacia(Long cantidadRegistrosListaVacia) {
		this.cantidadRegistrosListaVacia = cantidadRegistrosListaVacia;
	}
}