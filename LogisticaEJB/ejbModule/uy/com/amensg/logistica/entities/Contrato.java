package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;
	
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

	@Column(name = "resultado_entrega_distribucion_observaciones")
	private String resultadoEntregaDistribucionObservaciones;
	
	@Column(name = "resultado_entrega_distribucion_url_anverso")
	private String resultadoEntregaDistribucionURLAnverso;
	
	@Column(name = "resultado_entrega_distribucion_url_reverso")
	private String resultadoEntregaDistribucionURLReverso;
	
	@Column(name = "resultado_entrega_distribucion_latitud")
	private Double resultadoEntregaDistribucionLatitud;
	
	@Column(name = "resultado_entrega_distribucion_longitud")
	private Double resultadoEntregaDistribucionLongitud;
	
	@Column(name = "resultado_entrega_distribucion_precision")
	private Double resultadoEntregaDistribucionPrecision;
	
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

	@Column (name = "fecha_envio_antel")
	private Date fechaEnvioAntel;
	
	@Column(name = "fecha_activacion")
	private Date fechaActivacion;

	@Column(name = "fecha_activar_en")
	private Date fechaActivarEn;
	
	@Column(name = "fecha_coordinacion")
	private Date fechaCoordinacion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "barrio_id", nullable = true)
	private Barrio barrio;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "zona_id", nullable = true)
	private Zona zona;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "turno_id", nullable = true)
	private Turno turno;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "producto_id", nullable = true)
	private Producto producto;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado_id", nullable = true)
	private Estado estado;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "resultado_entrega_distribucion_id", nullable = true)
	private ResultadoEntregaDistribucion resultadoEntregaDistribucion;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "rol_id", nullable = true)
	private Rol rol;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = true)
	private Usuario usuario;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "vendedor_id", nullable = true)
	private Usuario vendedor;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "backoffice_id", nullable = true)
	private Usuario backoffice;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "distribuidor_id", nullable = true)
	private Usuario distribuidor;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "activador_id", nullable = true)
	private Usuario activador;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "coordinador_id", nullable = true)
	private Usuario coordinador;

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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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

	public String getResultadoEntregaDistribucionObservaciones() {
		return resultadoEntregaDistribucionObservaciones;
	}

	public void setResultadoEntregaDistribucionObservaciones(
			String resultadoEntregaDistribucionObservaciones) {
		this.resultadoEntregaDistribucionObservaciones = resultadoEntregaDistribucionObservaciones;
	}

	public String getResultadoEntregaDistribucionURLAnverso() {
		return resultadoEntregaDistribucionURLAnverso;
	}

	public void setResultadoEntregaDistribucionURLAnverso(String resultadoEntregaDistribucionURLAnverso) {
		this.resultadoEntregaDistribucionURLAnverso = resultadoEntregaDistribucionURLAnverso;
	}
	
	public String getResultadoEntregaDistribucionURLReverso() {
		return resultadoEntregaDistribucionURLReverso;
	}

	public void setResultadoEntregaDistribucionURLReverso(String resultadoEntregaDistribucionURLReverso) {
		this.resultadoEntregaDistribucionURLReverso = resultadoEntregaDistribucionURLReverso;
	}

	public Double getResultadoEntregaDistribucionLatitud() {
		return resultadoEntregaDistribucionLatitud;
	}

	public void setResultadoEntregaDistribucionLatitud(
			Double resultadoEntregaDistribucionLatitud) {
		this.resultadoEntregaDistribucionLatitud = resultadoEntregaDistribucionLatitud;
	}

	public Double getResultadoEntregaDistribucionLongitud() {
		return resultadoEntregaDistribucionLongitud;
	}

	public void setResultadoEntregaDistribucionLongitud(
			Double resultadoEntregaDistribucionLongitud) {
		this.resultadoEntregaDistribucionLongitud = resultadoEntregaDistribucionLongitud;
	}

	public Double getResultadoEntregaDistribucionPrecision() {
		return resultadoEntregaDistribucionPrecision;
	}

	public void setResultadoEntregaDistribucionPrecision(
			Double resultadoEntregaDistribucionPrecision) {
		this.resultadoEntregaDistribucionPrecision = resultadoEntregaDistribucionPrecision;
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
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

	public ResultadoEntregaDistribucion getResultadoEntregaDistribucion() {
		return resultadoEntregaDistribucion;
	}

	public void setResultadoEntregaDistribucion(
			ResultadoEntregaDistribucion resultadoEntregaDistribucion) {
		this.resultadoEntregaDistribucion = resultadoEntregaDistribucion;
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

	public Date getFechaEnvioAntel() {
		return fechaEnvioAntel;
	}

	public void setFechaEnvioAntel(Date fechaEnvioAntel) {
		this.fechaEnvioAntel = fechaEnvioAntel;
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

	public Date getFechaCoordinacion() {
		return fechaCoordinacion;
	}

	public void setFechaCoordinacion(Date fechaCoordinacion) {
		this.fechaCoordinacion = fechaCoordinacion;
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

	public Usuario getCoordinador() {
		return coordinador;
	}
	
	public void setCoordinador(Usuario coordinador) {
		this.coordinador = coordinador;
	}
}