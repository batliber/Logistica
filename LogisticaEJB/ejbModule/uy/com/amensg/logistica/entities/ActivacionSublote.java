package uy.com.amensg.logistica.entities;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "activacion_sublote")
public class ActivacionSublote extends BaseEntity {

	private static final long serialVersionUID = -4581356444917410217L;

	@Column(name = "numero")
	private Long numero;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "fecha_vencimiento_chip_mas_viejo")
	private Date fechaVencimientoChipMasViejo;
	
	@Column(name = "fecha_asignacion_distribuidor")
	private Date fechaAsignacionDistribuidor;
	
	@Column(name = "fecha_asignacion_punto_venta")
	private Date fechaAsignacionPuntoVenta;
	
	@Column(name = "porcentaje_activacion")
	private Double porcentajeActivacion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "distribuidor_id", nullable = true)
	private Usuario distribuidor;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "punto_venta_id", nullable = true)
	private PuntoVenta puntoVenta;

	@OneToMany(mappedBy="activacionSublote")
	private Set<Activacion> activaciones;
	
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

	public Date getFechaVencimientoChipMasViejo() {
		return fechaVencimientoChipMasViejo;
	}

	public void setFechaVencimientoChipMasViejo(Date fechaVencimientoChipMasViejo) {
		this.fechaVencimientoChipMasViejo = fechaVencimientoChipMasViejo;
	}

	public Double getPorcentajeActivacion() {
		return porcentajeActivacion;
	}

	public void setPorcentajeActivacion(Double porcentajeActivacion) {
		this.porcentajeActivacion = porcentajeActivacion;
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

	public Set<Activacion> getActivaciones() {
		return activaciones;
	}

	public void setActivaciones(Set<Activacion> activaciones) {
		this.activaciones = activaciones;
	}
}