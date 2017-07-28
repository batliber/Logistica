package uy.com.amensg.logistica.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vs_stock_actual")
public class StockActual implements Serializable {

	private static final long serialVersionUID = -531729615655852765L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "cantidad")
	private Long cantidad;
	
	@ManyToOne
	@JoinColumn(name = "marca_id")
	private Marca marca;
	
	@ManyToOne
	@JoinColumn(name = "modelo_id")
	private Modelo modelo;
	
	@ManyToOne
	@JoinColumn(name = "tipo_producto_id")
	private TipoProducto tipoProducto;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;

	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
}