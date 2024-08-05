package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "vs_contrato_antel")
public class ContratoANTEL extends BaseEntity {

	private static final long serialVersionUID = 474909670482284928L;

	@Column(name = "mid")
	private Long mid;

	@Column(name = "numero_tramite")
	private Long numeroTramite;
	
	@Column(name = "documento_tipo")
	private Long documentoTipo;

	@Column(name = "documento")
	private String documento;
	
	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;
	
	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;
	
	@Column(name = "codigo_postal")
	private String codigoPostal;
	
	@Column(name = "fecha_entrega")
	private Date fechaEntrega;
	
	@Column(name = "telefono_contacto")
	private String telefonoContacto;

	@Column(name = "email")
	private String email;
	
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
	
	@Column(name = "resultado_entrega_distribucion_fecha")
	private Date resultadoEntregaDistribucionFecha;
	
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
	
	@Column(name = "fecha_pick_up")
	private Date fechaPickUp;
	
	@Column(name = "direccion_entrega_calle")
	private String direccionEntregaCalle;
	
	@Column(name = "direccion_entrega_numero")
	private Long direccionEntregaNumero;
	
	@Column(name = "direccion_entrega_bis")
	private Boolean direccionEntregaBis;
	
	@Column(name = "direccion_entrega_block")
	private String direccionEntregaBlock;
	
	@Column(name = "direccion_entrega_apto")
	private String direccionEntregaApto;
	
	@Column(name = "direccion_entrega_solar")
	private String direccionEntregaSolar;
	
	@Column(name = "direccion_entrega_manzana")
	private Long direccionEntregaManzana;
	
	@Column(name = "direccion_entrega_codigo_postal")
	private Long direccionEntregaCodigoPostal;
	
	@Column(name = "direccion_entrega_localidad")
	private String direccionEntregaLocalidad;
	
	@Column(name = "direccion_entrega_observaciones")
	private String direccionEntregaObservaciones;
	
	@Column(name = "direccion_factura_calle")
	private String direccionFacturaCalle;
	
	@Column(name = "direccion_factura_numero")
	private Long direccionFacturaNumero;
	
	@Column(name = "direccion_factura_bis")
	private Boolean direccionFacturaBis;
	
	@Column(name = "direccion_factura_block")
	private String direccionFacturaBlock;
	
	@Column(name = "direccion_factura_apto")
	private String direccionFacturaApto;
	
	@Column(name = "direccion_factura_solar")
	private String direccionFacturaSolar;
	
	@Column(name = "direccion_factura_manzana")
	private Long direccionFacturaManzana;
	
	@Column(name = "direccion_factura_codigo_postal")
	private Long direccionFacturaCodigoPostal;
	
	@Column(name = "direccion_factura_localidad")
	private String direccionFacturaLocalidad;
	
	@Column(name = "direccion_factura_observaciones")
	private String direccionFacturaObservaciones;
	
	@Column(name = "antel_nro_trn")
	private String antelNroTrn;
	
	@Column(name = "costo_envio")
	private Double costoEnvio;
	
	@Column(name="random")
	private Long random;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "direccion_entrega_departamento_id", nullable = true)
	private Departamento direccionEntregaDepartamento;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "direccion_factura_departamento_id", nullable = true)
	private Departamento direccionFacturaDepartamento;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "tipo_documento_id", nullable = true)
	private TipoDocumento tipoDocumento;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "sexo_id", nullable = true)
	private Sexo sexo;
	
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
	@JoinColumn(name = "estado_id", nullable = true)
	private EstadoANTEL estado;

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

	public String getAntelNroTrn() {
		return antelNroTrn;
	}

	public void setAntelNroTrn(String antelNroTrn) {
		this.antelNroTrn = antelNroTrn;
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
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

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getResultadoEntregaDistribucionObservaciones() {
		return resultadoEntregaDistribucionObservaciones;
	}

	public void setResultadoEntregaDistribucionObservaciones(String resultadoEntregaDistribucionObservaciones) {
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

	public void setResultadoEntregaDistribucionLatitud(Double resultadoEntregaDistribucionLatitud) {
		this.resultadoEntregaDistribucionLatitud = resultadoEntregaDistribucionLatitud;
	}

	public Double getResultadoEntregaDistribucionLongitud() {
		return resultadoEntregaDistribucionLongitud;
	}

	public void setResultadoEntregaDistribucionLongitud(Double resultadoEntregaDistribucionLongitud) {
		this.resultadoEntregaDistribucionLongitud = resultadoEntregaDistribucionLongitud;
	}

	public Double getResultadoEntregaDistribucionPrecision() {
		return resultadoEntregaDistribucionPrecision;
	}

	public void setResultadoEntregaDistribucionPrecision(Double resultadoEntregaDistribucionPrecision) {
		this.resultadoEntregaDistribucionPrecision = resultadoEntregaDistribucionPrecision;
	}

	public Date getResultadoEntregaDistribucionFecha() {
		return resultadoEntregaDistribucionFecha;
	}

	public void setResultadoEntregaDistribucionFecha(Date resultadoEntregaDistribucionFecha) {
		this.resultadoEntregaDistribucionFecha = resultadoEntregaDistribucionFecha;
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

	public Date getFechaPickUp() {
		return fechaPickUp;
	}

	public void setFechaPickUp(Date fechaPickUp) {
		this.fechaPickUp = fechaPickUp;
	}

	public String getDireccionEntregaCalle() {
		return direccionEntregaCalle;
	}

	public void setDireccionEntregaCalle(String direccionEntregaCalle) {
		this.direccionEntregaCalle = direccionEntregaCalle;
	}

	public Long getDireccionEntregaNumero() {
		return direccionEntregaNumero;
	}

	public void setDireccionEntregaNumero(Long direccionEntregaNumero) {
		this.direccionEntregaNumero = direccionEntregaNumero;
	}

	public Boolean getDireccionEntregaBis() {
		return direccionEntregaBis;
	}

	public void setDireccionEntregaBis(Boolean direccionEntregaBis) {
		this.direccionEntregaBis = direccionEntregaBis;
	}

	public String getDireccionEntregaBlock() {
		return direccionEntregaBlock;
	}

	public void setDireccionEntregaBlock(String direccionEntregaBlock) {
		this.direccionEntregaBlock = direccionEntregaBlock;
	}

	public String getDireccionEntregaApto() {
		return direccionEntregaApto;
	}

	public void setDireccionEntregaApto(String direccionEntregaApto) {
		this.direccionEntregaApto = direccionEntregaApto;
	}

	public String getDireccionEntregaSolar() {
		return direccionEntregaSolar;
	}

	public void setDireccionEntregaSolar(String direccionEntregaSolar) {
		this.direccionEntregaSolar = direccionEntregaSolar;
	}

	public Long getDireccionEntregaManzana() {
		return direccionEntregaManzana;
	}

	public void setDireccionEntregaManzana(Long direccionEntregaManzana) {
		this.direccionEntregaManzana = direccionEntregaManzana;
	}

	public Long getDireccionEntregaCodigoPostal() {
		return direccionEntregaCodigoPostal;
	}

	public void setDireccionEntregaCodigoPostal(Long direccionEntregaCodigoPostal) {
		this.direccionEntregaCodigoPostal = direccionEntregaCodigoPostal;
	}

	public String getDireccionEntregaLocalidad() {
		return direccionEntregaLocalidad;
	}

	public void setDireccionEntregaLocalidad(String direccionEntregaLocalidad) {
		this.direccionEntregaLocalidad = direccionEntregaLocalidad;
	}

	public String getDireccionEntregaObservaciones() {
		return direccionEntregaObservaciones;
	}

	public void setDireccionEntregaObservaciones(String direccionEntregaObservaciones) {
		this.direccionEntregaObservaciones = direccionEntregaObservaciones;
	}

	public String getDireccionFacturaCalle() {
		return direccionFacturaCalle;
	}

	public void setDireccionFacturaCalle(String direccionFacturaCalle) {
		this.direccionFacturaCalle = direccionFacturaCalle;
	}

	public Long getDireccionFacturaNumero() {
		return direccionFacturaNumero;
	}

	public void setDireccionFacturaNumero(Long direccionFacturaNumero) {
		this.direccionFacturaNumero = direccionFacturaNumero;
	}

	public Boolean getDireccionFacturaBis() {
		return direccionFacturaBis;
	}

	public void setDireccionFacturaBis(Boolean direccionFacturaBis) {
		this.direccionFacturaBis = direccionFacturaBis;
	}

	public String getDireccionFacturaBlock() {
		return direccionFacturaBlock;
	}

	public void setDireccionFacturaBlock(String direccionFacturaBlock) {
		this.direccionFacturaBlock = direccionFacturaBlock;
	}

	public String getDireccionFacturaApto() {
		return direccionFacturaApto;
	}

	public void setDireccionFacturaApto(String direccionFacturaApto) {
		this.direccionFacturaApto = direccionFacturaApto;
	}

	public String getDireccionFacturaSolar() {
		return direccionFacturaSolar;
	}

	public void setDireccionFacturaSolar(String direccionFacturaSolar) {
		this.direccionFacturaSolar = direccionFacturaSolar;
	}

	public Long getDireccionFacturaManzana() {
		return direccionFacturaManzana;
	}

	public void setDireccionFacturaManzana(Long direccionFacturaManzana) {
		this.direccionFacturaManzana = direccionFacturaManzana;
	}

	public Long getDireccionFacturaCodigoPostal() {
		return direccionFacturaCodigoPostal;
	}

	public void setDireccionFacturaCodigoPostal(Long direccionFacturaCodigoPostal) {
		this.direccionFacturaCodigoPostal = direccionFacturaCodigoPostal;
	}

	public String getDireccionFacturaLocalidad() {
		return direccionFacturaLocalidad;
	}

	public void setDireccionFacturaLocalidad(String direccionFacturaLocalidad) {
		this.direccionFacturaLocalidad = direccionFacturaLocalidad;
	}

	public String getDireccionFacturaObservaciones() {
		return direccionFacturaObservaciones;
	}

	public void setDireccionFacturaObservaciones(String direccionFacturaObservaciones) {
		this.direccionFacturaObservaciones = direccionFacturaObservaciones;
	}

	public Double getCostoEnvio() {
		return costoEnvio;
	}

	public void setCostoEnvio(Double costoEnvio) {
		this.costoEnvio = costoEnvio;
	}

	public Long getRandom() {
		return random;
	}

	public void setRandom(Long random) {
		this.random = random;
	}

	public Departamento getDireccionEntregaDepartamento() {
		return direccionEntregaDepartamento;
	}

	public void setDireccionEntregaDepartamento(Departamento direccionEntregaDepartamento) {
		this.direccionEntregaDepartamento = direccionEntregaDepartamento;
	}

	public Departamento getDireccionFacturaDepartamento() {
		return direccionFacturaDepartamento;
	}

	public void setDireccionFacturaDepartamento(Departamento direccionFacturaDepartamento) {
		this.direccionFacturaDepartamento = direccionFacturaDepartamento;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
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

	public EstadoANTEL getEstado() {
		return estado;
	}

	public void setEstado(EstadoANTEL estado) {
		this.estado = estado;
	}

	public ResultadoEntregaDistribucion getResultadoEntregaDistribucion() {
		return resultadoEntregaDistribucion;
	}

	public void setResultadoEntregaDistribucion(ResultadoEntregaDistribucion resultadoEntregaDistribucion) {
		this.resultadoEntregaDistribucion = resultadoEntregaDistribucion;
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