package uy.com.amensg.logistica.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contrato_direccion")
public class ContratoDireccion extends BaseEntity {

	private static final long serialVersionUID = 416624884374235464L;

	@Column(name = "calle")
	private String calle;
	
	@Column(name = "numero")
	private Long numero;
	
	@Column(name = "bis")
	private Boolean bis;
	
	@Column(name = "block")
	private String block;
	
	@Column(name = "apto")
	private String apto;
	
	@Column(name = "solar")
	private String solar;
	
	@Column(name = "manzana")
	private Long manzana;
	
	@Column(name = "codigo_postal")
	private Long codigoPostal;
	
	@Column(name = "localidad")
	private String localidad;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "departamento_id", nullable = true)
	private Departamento departamento;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_direccion_id")
	private TipoDireccion tipoDireccion;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "contrato_id", nullable = false)
	private Contrato contrato;

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Boolean getBis() {
		return bis;
	}

	public void setBis(Boolean bis) {
		this.bis = bis;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getApto() {
		return apto;
	}

	public void setApto(String apto) {
		this.apto = apto;
	}

	public String getSolar() {
		return solar;
	}

	public void setSolar(String solar) {
		this.solar = solar;
	}

	public Long getManzana() {
		return manzana;
	}

	public void setManzana(Long manzana) {
		this.manzana = manzana;
	}

	public Long getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Long codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public TipoDireccion getTipoDireccion() {
		return tipoDireccion;
	}

	public void setTipoDireccion(TipoDireccion tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
}