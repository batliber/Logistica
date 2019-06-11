package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "calculo_porcentaje_activacion_punto_venta")
public class CalculoPorcentajeActivacionPuntoVenta extends BaseEntity {

	private static final long serialVersionUID = 2465837073399095555L;

	@Column(name = "fecha_calculo")
	private Date fechaCalculo;
	
	@Column(name = "fecha_liquidacion")
	private Date fechaLiquidacion;
	
	@Column(name = "porcentaje_activacion")
	private Double porcentajeActivacion;
	
	@ManyToOne(optional=true, fetch=FetchType.EAGER)
	@JoinColumn(name="punto_venta_id", nullable=true)
	private PuntoVenta puntoVenta;

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

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}
}