package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stock_movimiento")
public class StockMovimiento extends BaseEntity {

	private static final long serialVersionUID = -5001898193440708588L;

	@Column(name = "fecha")
	private Date fecha;

	@Column(name = "cantidad")
	private Long cantidad;

	@Column(name = "documento_id")
	private Long documentoId;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "producto_id", nullable = false)
	private Producto producto;

	@ManyToOne(optional = false)
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;

	@ManyToOne(optional = false)
	@JoinColumn(name = "stock_tipo_movimiento_id", nullable = false)
	private StockTipoMovimiento stockTipoMovimiento;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public StockTipoMovimiento getStockTipoMovimiento() {
		return stockTipoMovimiento;
	}

	public void setStockTipoMovimiento(StockTipoMovimiento stockTipoMovimiento) {
		this.stockTipoMovimiento = stockTipoMovimiento;
	}
}