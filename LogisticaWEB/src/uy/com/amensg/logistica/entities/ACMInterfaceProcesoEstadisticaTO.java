package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ACMInterfaceProcesoEstadisticaTO {

	private Long id;
	private Date fechaInicio;
	private Date fechaFin;
	private Long cantidadRegistrosParaProcesar;
	private Long cantidadRegistrosParaProcesarPrioritario;
	private Long cantidadRegistrosProcesado;
	private Long cantidadRegistrosEnProceso;

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
}