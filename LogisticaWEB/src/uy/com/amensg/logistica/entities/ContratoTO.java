package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ContratoTO extends BaseTO {

	private Long mid;
	private Long numeroTramite;
	private Date fechaFinContrato;
	private Long documentoTipo;
	private String documento;
	private Long numeroCliente;
	private String nombre;
	private Date fechaNacimiento;
	private String direccion;
	private String localidad;
	private String codigoPostal;
	private String tipoContratoCodigo;
	private String tipoContratoDescripcion;
	private Long numeroContrato;
	private String agente;
	private String equipo;
	private Date fechaEntrega;
	private String direccionEntrega;
	private String direccionFactura;
	private String telefonoContacto;
	private String email;
	private Long numeroFactura;
	private Double precio;
	private String nuevoPlan;
	private String numeroSerie;
	private String observaciones;
	private String resultadoEntregaDistribucionObservaciones;
	private String resultadoEntregaDistribucionURLAnverso;
	private String resultadoEntregaDistribucionURLReverso;
	private Double resultadoEntregaDistribucionLatitud;
	private Double resultadoEntregaDistribucionLongitud;
	private Double resultadoEntregaDistribucionPrecision;
	private Date fechaVenta;
	private Date fechaRechazo;
	private Date fechaBackoffice;
	private Date fechaEntregaDistribuidor;
	private Date fechaDevolucionDistribuidor;
	private Date fechaEnvioAntel;
	private Date fechaActivacion;
	private Date fechaActivarEn;
	private Date fechaCoordinacion;
	private BarrioTO barrio;
	private ZonaTO zona;
	private TurnoTO turno;
	private ProductoTO producto;
	private ResultadoEntregaDistribucionTO resultadoEntregaDistribucion;
	private EstadoTO estado;
	private EmpresaTO empresa;
	private RolTO rol;
	private UsuarioTO usuario;
	private UsuarioTO vendedor;
	private UsuarioTO backoffice;
	private UsuarioTO distribuidor;
	private UsuarioTO activador;
	private UsuarioTO coordinador;

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

	public void setResultadoEntregaDistribucionURLReverso(
			String resultadoEntregaDistribucionURLReverso) {
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

	public BarrioTO getBarrio() {
		return barrio;
	}

	public void setBarrio(BarrioTO barrio) {
		this.barrio = barrio;
	}

	public ZonaTO getZona() {
		return zona;
	}

	public void setZona(ZonaTO zona) {
		this.zona = zona;
	}

	public TurnoTO getTurno() {
		return turno;
	}

	public void setTurno(TurnoTO turno) {
		this.turno = turno;
	}

	public ProductoTO getProducto() {
		return producto;
	}

	public void setProducto(ProductoTO producto) {
		this.producto = producto;
	}

	public ResultadoEntregaDistribucionTO getResultadoEntregaDistribucion() {
		return resultadoEntregaDistribucion;
	}

	public void setResultadoEntregaDistribucion(
			ResultadoEntregaDistribucionTO resultadoEntregaDistribucion) {
		this.resultadoEntregaDistribucion = resultadoEntregaDistribucion;
	}

	public EstadoTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoTO estado) {
		this.estado = estado;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public RolTO getRol() {
		return rol;
	}

	public void setRol(RolTO rol) {
		this.rol = rol;
	}

	public UsuarioTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioTO usuario) {
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
	
	public UsuarioTO getVendedor() {
		return vendedor;
	}

	public void setVendedor(UsuarioTO vendedor) {
		this.vendedor = vendedor;
	}

	public UsuarioTO getBackoffice() {
		return backoffice;
	}

	public void setBackoffice(UsuarioTO backoffice) {
		this.backoffice = backoffice;
	}

	public UsuarioTO getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(UsuarioTO distribuidor) {
		this.distribuidor = distribuidor;
	}

	public UsuarioTO getActivador() {
		return activador;
	}

	public void setActivador(UsuarioTO activador) {
		this.activador = activador;
	}

	public UsuarioTO getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(UsuarioTO coordinador) {
		this.coordinador = coordinador;
	}	
}