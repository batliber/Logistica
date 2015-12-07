package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class StockMovimientoTO extends BaseTO {

	private Date fecha;
	private Long cantidad;
	private ProductoTO producto;
	private EmpresaTO empresa;

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
}