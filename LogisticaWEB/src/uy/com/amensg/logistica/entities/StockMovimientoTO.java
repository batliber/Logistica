package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class StockMovimientoTO extends BaseTO {

	private Date fecha;
	private Long cantidad;
	private Long documentoId;
	private MarcaTO marca;
	private ModeloTO modelo;
	private ProductoTO producto;
	private EmpresaTO empresa;
	private StockTipoMovimientoTO stockTipoMovimiento;

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

	public MarcaTO getMarca() {
		return marca;
	}

	public void setMarca(MarcaTO marca) {
		this.marca = marca;
	}

	public ModeloTO getModelo() {
		return modelo;
	}

	public void setModelo(ModeloTO modelo) {
		this.modelo = modelo;
	}
	
	public ProductoTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoTO producto) {
		this.producto = producto;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public StockTipoMovimientoTO getStockTipoMovimiento() {
		return stockTipoMovimiento;
	}
	
	public void setStockTipoMovimiento(StockTipoMovimientoTO stockTipoMovimiento) {
		this.stockTipoMovimiento = stockTipoMovimiento;
	}	
}