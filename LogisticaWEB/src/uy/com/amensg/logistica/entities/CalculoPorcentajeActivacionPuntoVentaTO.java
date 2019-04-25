package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class CalculoPorcentajeActivacionPuntoVentaTO extends BaseTO {

	private Date fechaCalculo;
	private Date fechaLiquidacion;
	private Double porcentajeActivacion;
	private PuntoVentaTO puntoVenta;

	public Date getFechaCalculo() {
		return fechaCalculo;
	}

	public void setFechaCalculo(Date fechaCalculo) {
		this.fechaCalculo = fechaCalculo;
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public Double getPorcentajeActivacion() {
		return porcentajeActivacion;
	}

	public void setPorcentajeActivacion(Double porcentajeActivacion) {
		this.porcentajeActivacion = porcentajeActivacion;
	}

	public PuntoVentaTO getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVentaTO puntoVenta) {
		this.puntoVenta = puntoVenta;
	}
}