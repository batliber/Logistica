package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "recarga_movimiento")
public class RecargaMovimiento extends BaseEntity {

	private static final long serialVersionUID = 3840829624611391014L;

	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "monto")
	private Double monto;
    
	@ManyToOne(optional = false)
	@JoinColumn(name = "recarga_empresa_id", nullable = false)
	private RecargaEmpresa recargaEmpresa;

	@ManyToOne(optional = false)
	@JoinColumn(name = "recarga_tipo_movimiento_id", nullable = false)
	private RecargaTipoMovimiento recargaTipoMovimiento;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "punto_venta_id")
	private PuntoVenta puntoVenta;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "moneda_id")
	private Moneda moneda;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public RecargaEmpresa getRecargaEmpresa() {
		return recargaEmpresa;
	}

	public void setRecargaEmpresa(RecargaEmpresa recargaEmpresa) {
		this.recargaEmpresa = recargaEmpresa;
	}

	public RecargaTipoMovimiento getRecargaTipoMovimiento() {
		return recargaTipoMovimiento;
	}

	public void setRecargaTipoMovimiento(RecargaTipoMovimiento recargaTipoMovimiento) {
		this.recargaTipoMovimiento = recargaTipoMovimiento;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
}