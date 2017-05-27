package uy.com.amensg.logistica.entities;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "activacion_sublote")
public class ActivacionSublote extends BaseEntity {

	private static final long serialVersionUID = -4581356444917410217L;

	@Column(name = "numero")
	private Long numero;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "fecha_asignacion_distribuidor")
	private Date fechaAsignacionDistribuidor;
	
	@Column(name = "fecha_asignacion_punto_venta")
	private Date fechaAsignacionPuntoVenta;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "distribuidor_id", nullable = true)
	private Usuario distribuidor;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "punto_venta_id", nullable = true)
	private PuntoVenta puntoVenta;

	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name = "activacion_sublote_activacion", 
		joinColumns = @JoinColumn(name = "activacion_sublote_id"),
		inverseJoinColumns = @JoinColumn(name = "activacion_id")
	)
	private Collection<Activacion> activaciones;
	
	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaAsignacionDistribuidor() {
		return fechaAsignacionDistribuidor;
	}

	public void setFechaAsignacionDistribuidor(Date fechaAsignacionDistribuidor) {
		this.fechaAsignacionDistribuidor = fechaAsignacionDistribuidor;
	}
	
	public Date getFechaAsignacionPuntoVenta() {
		return fechaAsignacionPuntoVenta;
	}

	public void setFechaAsignacionPuntoVenta(Date fechaAsignacionPuntoVenta) {
		this.fechaAsignacionPuntoVenta = fechaAsignacionPuntoVenta;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Usuario getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(Usuario distribuidor) {
		this.distribuidor = distribuidor;
	}

	public PuntoVenta getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVenta puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Collection<Activacion> getActivaciones() {
		return activaciones;
	}

	public void setActivaciones(Collection<Activacion> activaciones) {
		this.activaciones = activaciones;
	}
}