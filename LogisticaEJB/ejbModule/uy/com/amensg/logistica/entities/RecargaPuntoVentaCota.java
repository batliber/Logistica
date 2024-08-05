package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recarga_punto_venta_cota")
public class RecargaPuntoVentaCota extends BaseEntity {

	private static final long serialVersionUID = -5391279889518002094L;

	@Column(name = "tope_alarma_saldo")
	private Double topeAlarmaSaldo;
	
	@Column(name = "tope_total_por_dia")
	private Double topeTotalPorDia;
	
	@Column(name = "tope_total_por_mes")
	private Double topeTotalPorMes;
	
	@Column(name = "tope_por_mid")
	private Double topePorMid;
	
	@Column(name = "tope_porcentaje_descuento")
	private Double topePorcentajeDescuento;
    
	@OneToOne(optional = false)
    @JoinColumn(name = "punto_venta_id", nullable = false)
	private PuntoVenta puntoVenta;

	public Double getTopeAlarmaSaldo() {
		return topeAlarmaSaldo;
	}

	public void setTopeAlarmaSaldo(Double topeAlarmaSaldo) {
		this.topeAlarmaSaldo = topeAlarmaSaldo;
	}

	public Double getTopeTotalPorDia() {
		return topeTotalPorDia;
	}

	public void setTopeTotalPorDia(Double topeTotalPorDia) {
		this.topeTotalPorDia = topeTotalPorDia;
	}

	public Double getTopeTotalPorMes() {
		return topeTotalPorMes;
	}

	public void setTopeTotalPorMes(Double topeTotalPorMes) {
		this.topeTotalPorMes = topeTotalPorMes;
	}

	public Double getTopePorMid() {
		return topePorMid;
	}

	public void setTopePorMid(Double topePorMid) {
		this.topePorMid = topePorMid;
	}

	public Double getTopePorcentajeDescuento() {
		return topePorcentajeDescuento;
	}

	public void setTopePorcentajeDescuento(Double topePorcentajeDescuento) {
		this.topePorcentajeDescuento = topePorcentajeDescuento;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}
}