package uy.com.amensg.logistica.entities;

import java.util.Collection;
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
	private String apellido;
	private Date fechaNacimiento;
	private String direccion;
	private String localidad;
	private String codigoPostal;
	private String tipoContratoCodigo;
	private String tipoContratoDescripcion;
	private Long numeroContrato;
	private String nuevoPlanString;
	private String agente;
	private String equipo;
	private Date fechaEntrega;
	private String direccionEntrega;
	private String direccionFactura;
	private String telefonoContacto;
	private String email;
	private String numeroFactura;
	private Double precio;
<<<<<<< HEAD
	private Long cuotas;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	private String numeroSerie;
	private String numeroChip;
	private String numeroBloqueo;
	private String numeroFacturaRiverGreen;
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
	private String direccionEntregaCalle;
	private Long direccionEntregaNumero;
	private Boolean direccionEntregaBis;
	private String direccionEntregaBlock;
	private String direccionEntregaApto;
	private String direccionEntregaSolar;
	private Long direccionEntregaManzana;
	private Long direccionEntregaCodigoPostal;
	private String direccionEntregaLocalidad;
	private String direccionEntregaObservaciones;
	private String direccionFacturaCalle;
	private Long direccionFacturaNumero;
	private Boolean direccionFacturaBis;
	private String direccionFacturaBlock;
	private String direccionFacturaApto;
	private String direccionFacturaSolar;
	private Long direccionFacturaManzana;
	private Long direccionFacturaCodigoPostal;
	private String direccionFacturaLocalidad;
	private String direccionFacturaObservaciones;
<<<<<<< HEAD
	private Double intereses;
	private Double gastosAdministrativos;
	private Double gastosConcesion;
	private Double gastosAdministrativosTotales;
	private Double valorCuota;
	private Double valorUnidadIndexada;
	private Double valorTasaInteresEfectivaAnual;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	private DepartamentoTO direccionEntregaDepartamento;
	private DepartamentoTO direccionFacturaDepartamento;
	private TipoDocumentoTO tipoDocumento;
	private SexoTO sexo;
	private BarrioTO barrio;
	private ZonaTO zona;
	private TurnoTO turno;
	private MarcaTO marca;
	private ModeloTO modelo;
	private ProductoTO producto;
	private PlanTO nuevoPlan;
	private MotivoCambioPlanTO motivoCambioPlan;
<<<<<<< HEAD
	private MonedaTO moneda;
	private FormaPagoTO formaPago;
	private TarjetaCreditoTO tarjetaCredito;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
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
	private Collection<ContratoArchivoAdjuntoTO> archivosAdjuntos;

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

	public String getNuevoPlanString() {
		return nuevoPlanString;
	}

	public void setNuevoPlanString(String nuevoPlanString) {
		this.nuevoPlanString = nuevoPlanString;
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

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Long getCuotas() {
		return cuotas;
	}

	public void setCuotas(Long cuotas) {
		this.cuotas = cuotas;
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

	public PlanTO getNuevoPlan() {
		return nuevoPlan;
	}

	public void setNuevoPlan(PlanTO nuevoPlan) {
		this.nuevoPlan = nuevoPlan;
	}

	public MotivoCambioPlanTO getMotivoCambioPlan() {
		return motivoCambioPlan;
	}

	public void setMotivoCambioPlan(MotivoCambioPlanTO motivoCambioPlan) {
		this.motivoCambioPlan = motivoCambioPlan;
<<<<<<< HEAD
	}

	public MonedaTO getMoneda() {
		return moneda;
	}

	public void setMoneda(MonedaTO moneda) {
		this.moneda = moneda;
	}

	public FormaPagoTO getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPagoTO formaPago) {
		this.formaPago = formaPago;
	}

	public TarjetaCreditoTO getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(TarjetaCreditoTO tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getNumeroFacturaRiverGreen() {
		return numeroFacturaRiverGreen;
	}

	public void setNumeroFacturaRiverGreen(String numeroFacturaRiverGreen) {
		this.numeroFacturaRiverGreen = numeroFacturaRiverGreen;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getNumeroChip() {
		return numeroChip;
	}

	public void setNumeroChip(String numeroChip) {
		this.numeroChip = numeroChip;
	}

	public String getNumeroBloqueo() {
		return numeroBloqueo;
	}

	public void setNumeroBloqueo(String numeroBloqueo) {
		this.numeroBloqueo = numeroBloqueo;
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

	public void setDireccionEntregaObservaciones(
			String direccionEntregaObservaciones) {
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

	public void setDireccionFacturaObservaciones(
			String direccionFacturaObservaciones) {
		this.direccionFacturaObservaciones = direccionFacturaObservaciones;
	}

<<<<<<< HEAD
	public Double getIntereses() {
		return intereses;
	}

	public void setIntereses(Double intereses) {
		this.intereses = intereses;
	}

	public Double getGastosAdministrativos() {
		return gastosAdministrativos;
	}

	public void setGastosAdministrativos(Double gastosAdministrativos) {
		this.gastosAdministrativos = gastosAdministrativos;
	}

	public Double getGastosConcesion() {
		return gastosConcesion;
	}

	public void setGastosConcesion(Double gastosConcesion) {
		this.gastosConcesion = gastosConcesion;
	}

	public Double getGastosAdministrativosTotales() {
		return gastosAdministrativosTotales;
	}

	public void setGastosAdministrativosTotales(Double gastosAdministrativosTotales) {
		this.gastosAdministrativosTotales = gastosAdministrativosTotales;
	}

	public Double getValorCuota() {
		return valorCuota;
	}

	public void setValorCuota(Double valorCuota) {
		this.valorCuota = valorCuota;
	}

	public Double getValorUnidadIndexada() {
		return valorUnidadIndexada;
	}

	public void setValorUnidadIndexada(Double valorUnidadIndexada) {
		this.valorUnidadIndexada = valorUnidadIndexada;
	}

	public Double getValorTasaInteresEfectivaAnual() {
		return valorTasaInteresEfectivaAnual;
	}

	public void setValorTasaInteresEfectivaAnual(Double valorTasaInteresEfectivaAnual) {
		this.valorTasaInteresEfectivaAnual = valorTasaInteresEfectivaAnual;
	}

=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
	public DepartamentoTO getDireccionEntregaDepartamento() {
		return direccionEntregaDepartamento;
	}

	public void setDireccionEntregaDepartamento(
			DepartamentoTO direccionEntregaDepartamento) {
		this.direccionEntregaDepartamento = direccionEntregaDepartamento;
	}

	public DepartamentoTO getDireccionFacturaDepartamento() {
		return direccionFacturaDepartamento;
	}

	public void setDireccionFacturaDepartamento(
			DepartamentoTO direccionFacturaDepartamento) {
		this.direccionFacturaDepartamento = direccionFacturaDepartamento;
	}

	public TipoDocumentoTO getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumentoTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public SexoTO getSexo() {
		return sexo;
	}

	public void setSexo(SexoTO sexo) {
		this.sexo = sexo;
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

	public MarcaTO getMarca() {
		return marca;
	}

	public void setMarca(MarcaTO marca) {
		this.marca = marca;
	}

	public ModeloTO getModelo() {
		return modelo;
	}

	public void setModelo(ModeloTO modelo) {
		this.modelo = modelo;
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

	public Collection<ContratoArchivoAdjuntoTO> getArchivosAdjuntos() {
		return archivosAdjuntos;
	}

	public void setArchivosAdjuntos(Collection<ContratoArchivoAdjuntoTO> archivosAdjuntos) {
		this.archivosAdjuntos = archivosAdjuntos;
	}
}