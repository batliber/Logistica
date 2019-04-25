package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "punto_venta")
public class PuntoVenta extends BaseEntity {

	private static final long serialVersionUID = -291888610504577739L;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@Column(name = "latitud")
	private Double latitud;

	@Column(name = "longitud")
	private Double longitud;

	@Column(name = "precision")
	private Double precision;

	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "documento")
	private String documento;
	
	@Column(name = "contacto")
	private String contacto;
	
	@Column(name = "fecha_asignacion_distribuidor")
	private Date fechaAsignacionDistribuidor;
	
	@Column(name = "fecha_visita_distribuidor")
	private Date fechaVisitaDistribuidor;
	
	@Column(name = "fecha_ultimo_cambio_estado_visita_punto_venta_distribuidor")
	private Date fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "departamento_id", nullable = true)
	private Departamento departamento;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "barrio_id", nullable = true)
	private Barrio barrio;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado_punto_venta_id", nullable = true)
	private EstadoPuntoVenta estadoPuntoVenta;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "distribuidor_id", nullable = true)
	private Usuario distribuidor;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado_visita_punto_venta_distribuidor_id", nullable = true)
	private EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor;
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public Date getFechaAsignacionDistribuidor() {
		return fechaAsignacionDistribuidor;
	}

	public void setFechaAsignacionDistribuidor(Date fechaAsignacionDistribuidor) {
		this.fechaAsignacionDistribuidor = fechaAsignacionDistribuidor;
	}

	public Date getFechaVisitaDistribuidor() {
		return fechaVisitaDistribuidor;
	}

	public void setFechaVisitaDistribuidor(Date fechaVisitaDistribuidor) {
		this.fechaVisitaDistribuidor = fechaVisitaDistribuidor;
	}

	public Date getFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor() {
		return fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor;
	}

	public void setFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor(
			Date fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor) {
		this.fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor = fechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor;
	}

	public Usuario getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(Usuario distribuidor) {
		this.distribuidor = distribuidor;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public EstadoPuntoVenta getEstadoPuntoVenta() {
		return estadoPuntoVenta;
	}

	public void setEstadoPuntoVenta(EstadoPuntoVenta estadoPuntoVenta) {
		this.estadoPuntoVenta = estadoPuntoVenta;
	}

	public EstadoVisitaPuntoVentaDistribuidor getEstadoVisitaPuntoVentaDistribuidor() {
		return estadoVisitaPuntoVentaDistribuidor;
	}

	public void setEstadoVisitaPuntoVentaDistribuidor(
			EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor) {
		this.estadoVisitaPuntoVentaDistribuidor = estadoVisitaPuntoVentaDistribuidor;
	}
}