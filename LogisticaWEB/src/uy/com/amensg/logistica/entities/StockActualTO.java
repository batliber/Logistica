package uy.com.amensg.logistica.entities;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class StockActualTO {

	private Long cantidad;
	private MarcaTO marca;
	private ModeloTO modelo;
	private TipoProductoTO tipoProducto;
	private ProductoTO producto;
	private EmpresaTO empresa;

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
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

	public TipoProductoTO getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProductoTO tipoProducto) {
		this.tipoProducto = tipoProducto;
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