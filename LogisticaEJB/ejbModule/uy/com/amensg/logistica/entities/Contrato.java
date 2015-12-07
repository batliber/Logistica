package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contrato")
public class Contrato extends BaseEntity {

	private static final long serialVersionUID = -1172623644474818396L;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "numero_tramite")
	private Long numeroTramite;
	
	@Column(name = "fecha_fin_contrato")
	private Date fechaFinContrato;

	@Column(name = "documento_tipo")
	private Long documentoTipo;

	@Column(name = "documento")
	private String documento;

	@Column(name = "numero_cliente")
	private Long numeroCliente;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "localidad")
	private String localidad;

	@Column(name = "codigo_postal")
	private String codigoPostal;

	@Column(name = "tipo_contrato_codigo")
	private String tipoContratoCodigo;

	@Column(name = "tipo_contrato_descripcion")
	private String tipoContratoDescripcion;

	@Column(name = "numero_contrato")
	private Long numeroContrato;

	@Column(name = "agente")
	private String agente;

	@Column(name = "equipo")
	private String equipo;

	@Column(name = "fecha_entrega")
	private Date fechaEntrega;

	@Column(name = "direccion_entrega")
	private String direccionEntrega;

	@Column(name = "direccion_factura")
	private String direccionFactura;

	@Column(name = "telefono_contacto")
	private String telefonoContacto;

	@Column(name = "email")
	private String email;
	
	@Column(name = "numero_factura")
	private Long numeroFactura;

	@Column(name = "precio")
	private Double precio;

	@Column(name = "nuevo_plan")
	private String nuevoPlan;

	@Column(name = "numero_serie")
	private String numeroSerie;

	@Column(name = "observaciones")
	private String observaciones;

	@Column(name = "fecha_venta")
	private Date fechaVenta;

	@Column(name = "fecha_rechazo")
	private Date fechaRechazo;
	
	@Column(name = "fecha_backoffice")
	private Date fechaBackoffice;

	@Column(name = "fecha_entrega_distribuidor")
	private Date fechaEntregaDistribuidor;

	@Column(name = "fecha_devolucion_distribuidor")
	private Date fechaDevolucionDistribuidor;

	@Column(name = "fecha_activacion")
	private Date fechaActivacion;

	@Column(name = "fecha_activar_en")
	private Date fechaActivarEn;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "zona_id", nullable = true)
	private Zona zona;

	@ManyToOne(optional = true)
	@JoinColumn(name = "turno_id", nullable = true)
	private Turno turno;

	@ManyToOne(optional = true)
	@JoinColumn(name = "producto_id", nullable = true)
	private Producto producto;

	@ManyToOne(optional = true)
	@JoinColumn(name = "estado_id", nullable = true)
	private Estado estado;

	@ManyToOne(optional = true)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;

	@ManyToOne(optional = true)
	@JoinColumn(name = "rol_id", nullable = true)
	private Rol rol;

	@ManyToOne(optional = true)
	@JoinColumn(name = "usuario_id", nullable = true)
	private Usuario usuario;

	@ManyToOne(optional = true)
	@JoinColumn(name = "vendedor_id", nullable = true)
	private Usuario vendedor;

	@ManyToOne(optional = true)
	@JoinColumn(name = "backoffice_id", nullable = true)
	private Usuario backoffice;

	@ManyToOne(optional = true)
	@JoinColumn(name = "distribuidor_id", nullable = true)
	private Usuario distribuidor;

	@ManyToOne(optional = true)
	@JoinColumn(name = "activador_id", nullable = true)
	private Usuario activador;

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getNumeroTramite() {
		return numeroTramite;
	}

	public void setNumeroTramite(Long numeroTramite) {
		this.numeroTramite = numeroTramite;
	}

	public Date getFechaFinContrato() {
		return fechaFinContrato;
	}

	public void setFechaFinContrato(Date fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}

	public Long getDocumentoTipo() {
		return documentoTipo;
	}

	public void setDocumentoTipo(Long documentoTipo) {
		this.documentoTipo = documentoTipo;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Long getNumeroCliente() {
		return numeroCliente;
	}

	public void setNumeroCliente(Long numeroCliente) {
		this.numeroCliente = numeroCliente;
	}

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

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getTipoContratoCodigo() {
		return tipoContratoCodigo;
	}

	public void setTipoContratoCodigo(String tipoContratoCodigo) {
		this.tipoContratoCodigo = tipoContratoCodigo;
	}

	public String getTipoContratoDescripcion() {
		return tipoContratoDescripcion;
	}

	public void setTipoContratoDescripcion(String tipoContratoDescripcion) {
		this.tipoContratoDescripcion = tipoContratoDescripcion;
	}

	public Long getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(Long numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getDireccionEntrega() {
		return direccionEntrega;
	}

	public void setDireccionEntrega(String direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}

	public String getDireccionFactura() {
		return direccionFactura;
	}

	public void setDireccionFactura(String direccionFactura) {
		this.direccionFactura = direccionFactura;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(Long numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Date getFechaRechazo() {
		return fechaRechazo;
	}

	public void setFechaRechazo(Date fechaRechazo) {
		this.fechaRechazo = fechaRechazo;
	}

	public String getNuevoPlan() {
		return nuevoPlan;
	}

	public void setNuevoPlan(String nuevoPlan) {
		this.nuevoPlan = nuevoPlan;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFechaBackoffice() {
		return fechaBackoffice;
	}

	public void setFechaBackoffice(Date fechaBackoffice) {
		this.fechaBackoffice = fechaBackoffice;
	}

	public Date getFechaEntregaDistribuidor() {
		return fechaEntregaDistribuidor;
	}

	public void setFechaEntregaDistribuidor(Date fechaEntregaDistribuidor) {
		this.fechaEntregaDistribuidor = fechaEntregaDistribuidor;
	}

	public Date getFechaDevolucionDistribuidor() {
		return fechaDevolucionDistribuidor;
	}

	public void setFechaDevolucionDistribuidor(Date fechaDevolucionDistribuidor) {
		this.fechaDevolucionDistribuidor = fechaDevolucionDistribuidor;
	}

	public Date getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Date getFechaActivarEn() {
		return fechaActivarEn;
	}

	public void setFechaActivarEn(Date fechaActivarEn) {
		this.fechaActivarEn = fechaActivarEn;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public Usuario getBackoffice() {
		return backoffice;
	}

	public void setBackoffice(Usuario backoffice) {
		this.backoffice = backoffice;
	}

	public Usuario getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(Usuario distribuidor) {
		this.distribuidor = distribuidor;
	}

	public Usuario getActivador() {
		return activador;
	}

	public void setActivador(Usuario activador) {
		this.activador = activador;
	}
}