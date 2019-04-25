package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "precio")
public class Precio extends BaseEntity {

	private static final long serialVersionUID = -8872252275110799787L;

	@Column(name = "cuotas")
	private Long cuotas;
	
	@Column(name = "precio")
	private Double precio;

	@Column(name = "fecha_hasta")
	private Date fechaHasta;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "marca_id", nullable = true)
	private Marca marca;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "modelo_id", nullable = true)
	private Modelo modelo;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "moneda_id", nullable = true)
	private Moneda moneda;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_producto_id", nullable = true)
	private TipoProducto tipoProducto;

	public Long getCuotas() {
		return cuotas;
	}

	public void setCuotas(Long cuotas) {
		this.cuotas = cuotas;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
}