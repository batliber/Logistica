package uy.com.amensg.logistica.entities;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

	@Column(name = "apellido")
	private String apellido;

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
	private String numeroContrato;

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
	private String numeroFactura;

	@Column(name = "precio")
	private Double precio;

	@Column(name = "cuotas")
	private Long cuotas;

	@Column(name = "numero_serie")
	private String numeroSerie;

	@Column(name = "nuevo_plan")
	private String nuevoPlanString;

	@Column(name = "numero_factura_river_green")
	private String numeroFacturaRiverGreen;

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

	@Column(name = "fecha_envio_antel")
	private Date fechaEnvioAntel;

	@Column(name = "fecha_activacion")
	private Date fechaActivacion;

	@Column(name = "fecha_activar_en")
	private Date fechaActivarEn;

	@Column(name = "fecha_coordinacion")
	private Date fechaCoordinacion;

	@Column(name = "fecha_envio_a_nucleo")
	private Date fechaEnvioANucleo;

	@Column(name = "fecha_envio_a_izi")
	private Date fechaEnvioAIZI;

	@Column(name = "fecha_envio_a_gla")
	private Date fechaEnvioAGLA;

	@Column(name = "fecha_pick_up")
	private Date fechaPickUp;

	@Column(name = "fecha_operador_ac")
	private Date fechaAtencionClienteOperador;
	
	@Column(name = "fecha_gestionador_ac")
	private Date fechaAtencionClienteGestionador;
	
	@Column(name = "fecha_devuelto_ac")
	private Date fechaAtencionClienteDevuelto;
	
	@Column(name = "fecha_cierre_ac")
	private Date fechaAtencionClienteCierre;
	
	@Column(name = "numero_chip")
	private String numeroChip;

	@Column(name = "numero_bloqueo")
	private String numeroBloqueo;

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

	@Column(name = "numero_vale")
	private Long numeroVale;

	@Column(name = "intereses")
	private Double intereses;

	@Column(name = "gastos_administrativos")
	private Double gastosAdministrativos;

	@Column(name = "gastos_concesion")
	private Double gastosConcesion;

	@Column(name = "gastos_administrativos_totales")
	private Double gastosAdministrativosTotales;

	@Column(name = "valor_cuota")
	private Double valorCuota;

	@Column(name = "valor_unidad_indexada")
	private Double valorUnidadIndexada;

	@Column(name = "valor_tasa_interes_efectiva_anual")
	private Double valorTasaInteresEfectivaAnual;

	@Column(name = "antel_nro_trn")
	private String antelNroTrn;

	@Column(name = "antel_forma_pago")
	private String antelFormaPago;

	@Column(name = "antel_nro_servicio_cuenta")
	private String antelNroServicioCuenta;

	@Column(name = "antel_importe")
	private Double antelImporte;

	@Column(name = "incluir_chip")
	private Boolean incluirChip;

	@Column(name = "costo_envio")
	private Double costoEnvio;

	@Column(name = "cantidad_veces_ac")
	private Long atencionClienteCantidadVeces;
	
	@Column(name = "random")
	private Long random;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "direccion_entrega_departamento_id", nullable = true)
	private Departamento direccionEntregaDepartamento;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "direccion_factura_departamento_id", nullable = true)
	private Departamento direccionFacturaDepartamento;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_documento_id", nullable = true)
	private TipoDocumento tipoDocumento;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "sexo_id", nullable = true)
	private Sexo sexo;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "barrio_id", nullable = true)
	private Barrio barrio;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "zona_id", nullable = true)
	private Zona zona;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "turno_id", nullable = true)
	private Turno turno;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_producto_id", nullable = true)
	private TipoProducto tipoProducto;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "marca_id", nullable = true)
	private Marca marca;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "modelo_id", nullable = true)
	private Modelo modelo;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "producto_id", nullable = true)
	private Producto producto;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "nuevo_plan_id", nullable = true)
	private Plan nuevoPlan;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "motivo_cambio_plan_id", nullable = true)
	private MotivoCambioPlan motivoCambioPlan;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "moneda_id", nullable = true)
	private Moneda moneda;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "forma_pago_id", nullable = true)
	private FormaPago formaPago;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_tasa_interes_efectiva_anual_id", nullable = true)
	private TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "tarjeta_credito_id", nullable = true)
	private TarjetaCredito tarjetaCredito;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "estado_id", nullable = true)
	private Estado estado;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "resultado_entrega_distribucion_id", nullable = true)
	private ResultadoEntregaDistribucion resultadoEntregaDistribucion;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "modalidad_venta_id", nullable = true)
	private ModalidadVenta modalidadVenta;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "atencion_cliente_tipo_contacto_id", nullable = true)
	private AtencionClienteTipoContacto atencionClienteTipoContacto;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "atencion_cliente_concepto_id", nullable = true)
	private AtencionClienteConcepto atencionClienteConcepto;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "atencion_cliente_respuesta_tecnica_comercial_id", nullable = true)
	private AtencionClienteRespuestaTecnicaComercial atencionClienteRespuestaTecnicaComercial;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "rol_id", nullable = true)
	private Rol rol;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = true)
	private Usuario usuario;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "vendedor_id", nullable = true)
	private Usuario vendedor;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "backoffice_id", nullable = true)
	private Usuario backoffice;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "distribuidor_id", nullable = true)
	private Usuario distribuidor;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "activador_id", nullable = true)
	private Usuario activador;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "coordinador_id", nullable = true)
	private Usuario coordinador;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "operador_id", nullable = true)
	private Usuario atencionClienteOperador;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "gestionador_id", nullable = true)
	private Usuario atencionClienteGestionador;

	@OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ContratoArchivoAdjunto> archivosAdjuntos;

	@OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ContratoDireccion> direcciones;

	public Contrato() {
		
	}
	
	public Contrato(
		Long id,
		Long mid,
		Long numeroTramite,
		Date fechaFinContrato,
		Long documentoTipo,
		String documento,
		Long numeroCliente,
		String nombre,
		String apellido,
		Date fechaNacimiento,
		String direccion,
		String localidad,
		String codigoPostal,
		String tipoContratoCodigo,
		String tipoContratoDescripcion,
		String numeroContrato,
		String agente,
		String equipo,
		Date fechaEntrega,
		String direccionEntrega,
		String direccionFactura,
		String telefonoContacto,
		String email,
		String numeroFactura,
		Double precio,
		Long cuotas,
		String numeroSerie,
		String nuevoPlanString,
		String numeroFacturaRiverGreen,
		String observaciones,
		String resultadoEntregaDistribucionObservaciones,
		String resultadoEntregaDistribucionURLAnverso,
		String resultadoEntregaDistribucionURLReverso,
		Double resultadoEntregaDistribucionLatitud,
		Double resultadoEntregaDistribucionLongitud,
		Double resultadoEntregaDistribucionPrecision,
		Date resultadoEntregaDistribucionFecha,
		Date fechaVenta,
		Date fechaRechazo,
		Date fechaBackoffice,
		Date fechaEntregaDistribuidor,
		Date fechaDevolucionDistribuidor,
		Date fechaEnvioAntel,
		Date fechaActivacion,
		Date fechaActivarEn,
		Date fechaCoordinacion,
		Date fechaEnvioANucleo,
		Date fechaEnvioAIZI,
		Date fechaEnvioAGLA,
		Date fechaPickUp,
		Date fechaAtencionClienteOperador,
		Date fechaAtencionClienteGestionador,
		Date fechaAtencionClienteDevuelto,
		Date fechaAtencionClienteCierre,
		String numeroChip,
		String numeroBloqueo,
		String direccionEntregaCalle,
		Long direccionEntregaNumero,
		Boolean direccionEntregaBis,
		String direccionEntregaBlock,
		String direccionEntregaApto,
		String direccionEntregaSolar,
		Long direccionEntregaManzana,
		Long direccionEntregaCodigoPostal,
		String direccionEntregaLocalidad,
		String direccionEntregaObservaciones,
		String direccionFacturaCalle,
		Long direccionFacturaNumero,
		Boolean direccionFacturaBis,
		String direccionFacturaBlock,
		String direccionFacturaApto,
		String direccionFacturaSolar,
		Long direccionFacturaManzana,
		Long direccionFacturaCodigoPostal,
		String direccionFacturaLocalidad,
		String direccionFacturaObservaciones,
		Long numeroVale,
		Double intereses,
		Double gastosAdministrativos,
		Double gastosConcesion,
		Double gastosAdministrativosTotales,
		Double valorCuota,
		Double valorUnidadIndexada,
		Double valorTasaInteresEfectivaAnual,
		String antelNroTrn,
		String antelFormaPago,
		String antelNroServicioCuenta,
		Double antelImporte,
		Boolean incluirChip,
		Double costoEnvio,
		Long atencionClienteCantidadVeces,
		Long random,
		Date fcre,
		Long ucre,
		Date fact,
		Long uact,
		Long term,
		
		Long direccionEntregaDepartamentoId,
		String direccionEntregaDepartamentoNombre,
		Long direccionFacturaDepartamentoId,
		String direccionFacturaDepartamentoNombre,
		Long tipoDocumentoId,
		String tipoDocumentoDescripcion,
		Long sexoId,
		String sexoDescripcion,
		Long barrioId,
		String barrioNombre,
		Long barrioDepartamentoId,
		String barrioDepartamentoNombre,
		Long zonaId,
		String zonaNombre,
		Long turnoId,
		String turnoNombre,
		Long tipoProductoId,
		String tipoProductoDescripcion,
		Long marcaId,
		String marcaNombre,
		Long modeloId,
		String modeloDescripcion,
		Long productoId,
		String productoDescripcion,
		Long productoModeloId,
		String productoModeloDescripcion,
		Long nuevoPlanId,
		String nuevoPlanDescripcion,
		String nuevoPlanAbreviacion,
		Long motivoCambioPlanId,
		String motivoCambioPlanDescripcion,
		Long monedaId,
		String monedaNombre,
		Long formaPagoId,
		String formaPagoDescripcion,
		Long tipoTasaInteresEfectivaAnualId,
		String tipoTasaInteresEfectivaAnualDescripcion,
		Long tarjetaCreditoId,
		String tarjetaCreditoNombre,
		Long estadoId,
		String estadoNombre,
		Long resultadoEntregaDistribucionId,
		String resultadoEntregaDistribucionDescripcion,
		Long modalidadVentaId,
		String modalidadVentaDescripcion,
		Long atencionClienteTipoContactoId,
		String atencionClienteTipoContactoDescripcion,
		Long atencionClienteConceptoId,
		String atencionClienteConceptoDescripcion,
		Long atencionClienteRespuestaTecnicaComercialId,
		String atencionClienteRespuestaTecnicaComercialDescripcion,
		Long empresaId,
		String empresaNombre,
		Long rolId,
		String rolNombre,
		Long usuarioId,
		String usuarioNombre,
		Long vendedorId,
		String vendedorNombre,
		Long backofficeId,
		String backofficeNombre,
		Long distribuidorId,
		String distribuidorNombre,
		Long activadorId,
		String activadorNombre,
		Long coordinadorId,
		String coordinadorNombre,
		Long atencionClienteOperadorId,
		String atencionClienteOperadorNombre,
		Long atencionClienteGestionadorId,
		String atencionClienteGestionadorNombre
	) {
		this.id = id;
		this.mid = mid;
		this.numeroTramite = numeroTramite;
		this.fechaFinContrato = fechaFinContrato;
		this.documentoTipo = documentoTipo;
		this.documento = documento;
		this.numeroCliente = numeroCliente;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.localidad = localidad;
		this.codigoPostal = codigoPostal;
		this.tipoContratoCodigo = tipoContratoCodigo;
		this.tipoContratoDescripcion = tipoContratoDescripcion;
		this.numeroContrato = numeroContrato;
		this.agente = agente;
		this.equipo = equipo;
		this.fechaEntrega = fechaEntrega;
		this.direccionEntrega = direccionEntrega;
		this.direccionFactura = direccionFactura;
		this.telefonoContacto = telefonoContacto;
		this.email = email;
		this.numeroFactura = numeroFactura;
		this.precio = precio;
		this.cuotas = cuotas;
		this.numeroSerie = numeroSerie;
		this.nuevoPlanString = nuevoPlanString;
		this.numeroFacturaRiverGreen = numeroFacturaRiverGreen;
		this.observaciones = observaciones;
		this.resultadoEntregaDistribucionObservaciones = resultadoEntregaDistribucionObservaciones;
		this.resultadoEntregaDistribucionURLAnverso = resultadoEntregaDistribucionURLAnverso;
		this.resultadoEntregaDistribucionURLReverso = resultadoEntregaDistribucionURLReverso;
		this.resultadoEntregaDistribucionLatitud = resultadoEntregaDistribucionLatitud;
		this.resultadoEntregaDistribucionLongitud = resultadoEntregaDistribucionLongitud;
		this.resultadoEntregaDistribucionPrecision = resultadoEntregaDistribucionPrecision;
		this.resultadoEntregaDistribucionFecha = resultadoEntregaDistribucionFecha;
		this.fechaVenta = fechaVenta;
		this.fechaRechazo = fechaRechazo;
		this.fechaBackoffice = fechaBackoffice;
		this.fechaEntregaDistribuidor = fechaEntregaDistribuidor;
		this.fechaDevolucionDistribuidor = fechaDevolucionDistribuidor;
		this.fechaEnvioAntel = fechaEnvioAntel;
		this.fechaActivacion = fechaActivacion;
		this.fechaActivarEn = fechaActivarEn;
		this.fechaCoordinacion = fechaCoordinacion;
		this.fechaEnvioANucleo = fechaEnvioANucleo;
		this.fechaEnvioAIZI = fechaEnvioAIZI;
		this.fechaEnvioAGLA = fechaEnvioAGLA;
		this.fechaPickUp = fechaPickUp;
		this.fechaAtencionClienteOperador = fechaAtencionClienteOperador;
		this.fechaAtencionClienteGestionador = fechaAtencionClienteGestionador;
		this.fechaAtencionClienteDevuelto = fechaAtencionClienteDevuelto;
		this.fechaAtencionClienteCierre = fechaAtencionClienteCierre;
		this.numeroChip = numeroChip;
		this.numeroBloqueo = numeroBloqueo;
		this.direccionEntregaCalle = direccionEntregaCalle;
		this.direccionEntregaNumero = direccionEntregaNumero;
		this.direccionEntregaBis = direccionEntregaBis;
		this.direccionEntregaBlock = direccionEntregaBlock;
		this.direccionEntregaApto = direccionEntregaApto;
		this.direccionEntregaSolar = direccionEntregaSolar;
		this.direccionEntregaManzana = direccionEntregaManzana;
		this.direccionEntregaCodigoPostal = direccionEntregaCodigoPostal;
		this.direccionEntregaLocalidad = direccionEntregaLocalidad;
		this.direccionEntregaObservaciones = direccionEntregaObservaciones;
		this.direccionFacturaCalle = direccionFacturaCalle;
		this.direccionFacturaNumero = direccionFacturaNumero;
		this.direccionFacturaBis = direccionFacturaBis;
		this.direccionFacturaBlock = direccionFacturaBlock;
		this.direccionFacturaApto = direccionFacturaApto;
		this.direccionFacturaSolar = direccionFacturaSolar;
		this.direccionFacturaManzana = direccionFacturaManzana;
		this.direccionFacturaCodigoPostal = direccionFacturaCodigoPostal;
		this.direccionFacturaLocalidad = direccionFacturaLocalidad;
		this.direccionFacturaObservaciones = direccionFacturaObservaciones;
		this.numeroVale = numeroVale;
		this.intereses = intereses;
		this.gastosAdministrativos = gastosAdministrativos;
		this.gastosConcesion = gastosConcesion;
		this.gastosAdministrativosTotales = gastosAdministrativosTotales;
		this.valorCuota = valorCuota;
		this.valorUnidadIndexada = valorUnidadIndexada;
		this.valorTasaInteresEfectivaAnual = valorTasaInteresEfectivaAnual;
		this.antelNroTrn = antelNroTrn;
		this.antelFormaPago = antelFormaPago;
		this.antelNroServicioCuenta = antelNroServicioCuenta;
		this.antelImporte = antelImporte;
		this.incluirChip = incluirChip;
		this.costoEnvio = costoEnvio;
		this.atencionClienteCantidadVeces = atencionClienteCantidadVeces;
		this.random = random;
		this.fcre = fcre;
		this.ucre = ucre;
		this.fact = fact;
		this.uact = uact;
		this.term = term;
		
		if (direccionEntregaDepartamentoId != null) {
			direccionEntregaDepartamento = new Departamento();
			direccionEntregaDepartamento.setId(direccionEntregaDepartamentoId);
			direccionEntregaDepartamento.setNombre(direccionEntregaDepartamentoNombre);
		}
		
		if (direccionFacturaDepartamentoId != null) {
			direccionFacturaDepartamento = new Departamento();
			direccionFacturaDepartamento.setId(direccionFacturaDepartamentoId);
			direccionFacturaDepartamento.setNombre(direccionFacturaDepartamentoNombre);
		}
		
		if (tipoDocumentoId != null) {
			tipoDocumento = new TipoDocumento();
			tipoDocumento.setId(tipoDocumentoId);
			tipoDocumento.setDescripcion(tipoDocumentoDescripcion);
		}
		
		if (sexoId != null) {
			sexo = new Sexo();
			sexo.setId(sexoId);
			sexo.setDescripcion(sexoDescripcion);
		}
		
		if (barrioId != null) {
			barrio = new Barrio();
			barrio.setId(barrioId);
			barrio.setNombre(barrioNombre);
		}
		
		if (barrioDepartamentoId != null) {
			Departamento barrioDepartamento = new Departamento();
			barrioDepartamento.setId(barrioDepartamentoId);
			barrioDepartamento.setNombre(barrioDepartamentoNombre);
	
			barrio.setDepartamento(barrioDepartamento);
		}
		
		if (zonaId != null) {
			zona = new Zona();
			zona.setId(zonaId);
			zona.setNombre(zonaNombre);
		}
		
		if (turnoId != null) {
			turno = new Turno();
			turno.setId(turnoId);
			turno.setNombre(turnoNombre);
		}
		
		if (tipoProductoId != null) {
			tipoProducto = new TipoProducto();
			tipoProducto.setId(tipoProductoId);
			tipoProducto.setDescripcion(tipoProductoDescripcion);
		}
		
		if (marcaId != null) {
			marca = new Marca();
			marca.setId(marcaId);
			marca.setNombre(marcaNombre);
		}
		
		if (modeloId != null) {
			modelo = new Modelo();
			modelo.setId(modeloId);
			modelo.setDescripcion(modeloDescripcion);
		}
		
		if (productoId != null) {
			producto = new Producto();
			producto.setId(productoId);
			producto.setDescripcion(productoDescripcion);
		}
		
		if (productoModeloId != null) {
			Modelo productoModelo = new Modelo();
			productoModelo.setId(productoModeloId);
			productoModelo.setDescripcion(productoModeloDescripcion);
			
			producto.setModelo(productoModelo);
		}
		
		if (nuevoPlanId != null) {
			nuevoPlan = new Plan();
			nuevoPlan.setId(nuevoPlanId);
			nuevoPlan.setDescripcion(nuevoPlanDescripcion);
			nuevoPlan.setAbreviacion(nuevoPlanAbreviacion);
		}
		
		if (motivoCambioPlanId != null) {
			motivoCambioPlan = new MotivoCambioPlan();
			motivoCambioPlan.setId(motivoCambioPlanId);
			motivoCambioPlan.setDescripcion(motivoCambioPlanDescripcion);
		}
		
		if (monedaId != null) {
			moneda = new Moneda();
			moneda.setId(monedaId);
			moneda.setNombre(monedaNombre);
		}
		
		if (formaPagoId != null) {
			formaPago = new FormaPago();
			formaPago.setId(formaPagoId);
			formaPago.setDescripcion(formaPagoDescripcion);
		}
		
		if (tipoTasaInteresEfectivaAnualId != null) {
			tipoTasaInteresEfectivaAnual = new TipoTasaInteresEfectivaAnual();
			tipoTasaInteresEfectivaAnual.setId(tipoTasaInteresEfectivaAnualId);
			tipoTasaInteresEfectivaAnual.setDescripcion(tipoTasaInteresEfectivaAnualDescripcion);
		}
		
		if (tarjetaCreditoId != null) {
			tarjetaCredito = new TarjetaCredito();
			tarjetaCredito.setId(tarjetaCreditoId);
			tarjetaCredito.setNombre(tarjetaCreditoNombre);
		}
		
		if (estadoId != null) {
			estado = new Estado();
			estado.setId(estadoId);
			estado.setNombre(estadoNombre);
		}
		
		if (resultadoEntregaDistribucionId != null) {
			resultadoEntregaDistribucion = new ResultadoEntregaDistribucion();
			resultadoEntregaDistribucion.setId(resultadoEntregaDistribucionId);
			resultadoEntregaDistribucion.setDescripcion(resultadoEntregaDistribucionDescripcion);
		}
		
		if (modalidadVentaId != null) {
			modalidadVenta = new ModalidadVenta();
			modalidadVenta.setId(modalidadVentaId);
			modalidadVenta.setDescripcion(modalidadVentaDescripcion);
		}
		
		if (atencionClienteTipoContactoId != null) {
			atencionClienteTipoContacto = new AtencionClienteTipoContacto();
			atencionClienteTipoContacto.setId(atencionClienteTipoContactoId);
			atencionClienteTipoContacto.setDescripcion(atencionClienteTipoContactoDescripcion);
		}
		
		if (atencionClienteConceptoId != null) {
			atencionClienteConcepto = new AtencionClienteConcepto();
			atencionClienteConcepto.setId(atencionClienteConceptoId);
			atencionClienteConcepto.setDescripcion(atencionClienteConceptoDescripcion);
		}
		
		if (atencionClienteRespuestaTecnicaComercialId != null) {
			atencionClienteRespuestaTecnicaComercial = new AtencionClienteRespuestaTecnicaComercial();
			atencionClienteRespuestaTecnicaComercial.setId(atencionClienteRespuestaTecnicaComercialId);
			atencionClienteRespuestaTecnicaComercial.setDescripcion(atencionClienteRespuestaTecnicaComercialDescripcion);
		}
		
		if (empresaId != null) {
			empresa = new Empresa();
			empresa.setId(empresaId);
			empresa.setNombre(empresaNombre);
		}
		
		if (rolId != null) {
			rol = new Rol();
			rol.setId(rolId);
			rol.setNombre(rolNombre);
		}
		
		if (usuarioId != null) {
			usuario = new Usuario();
			usuario.setId(usuarioId);
			usuario.setNombre(usuarioNombre);
		}
		
		if (vendedorId != null) {
			vendedor = new Usuario();
			vendedor.setId(vendedorId);
			vendedor.setNombre(vendedorNombre);
		}
		
		if (backofficeId != null) {
			backoffice = new Usuario();
			backoffice.setId(backofficeId);
			backoffice.setNombre(backofficeNombre);
		}
		
		if (distribuidorId != null) {
			distribuidor = new Usuario();
			distribuidor.setId(distribuidorId);
			distribuidor.setNombre(distribuidorNombre);
		}
		
		if (activadorId != null) {
			activador = new Usuario();
			activador.setId(activadorId);
			activador.setNombre(activadorNombre);
		}
		
		if (coordinadorId != null) {
			coordinador = new Usuario();
			coordinador.setId(coordinadorId);
			coordinador.setNombre(coordinadorNombre);
		}
		
		if (atencionClienteOperadorId != null) {
			atencionClienteOperador = new Usuario();
			atencionClienteOperador.setId(atencionClienteOperadorId);
			atencionClienteOperador.setNombre(atencionClienteOperadorNombre);
		}
		
		if (atencionClienteGestionadorId != null) {
			atencionClienteGestionador = new Usuario();
			atencionClienteGestionador.setId(atencionClienteGestionadorId);
			atencionClienteGestionador.setNombre(atencionClienteGestionadorNombre);
		}
	}
	
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

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
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

	public String getNumeroFacturaRiverGreen() {
		return numeroFacturaRiverGreen;
	}

	public void setNumeroFacturaRiverGreen(String numeroFacturaRiverGreen) {
		this.numeroFacturaRiverGreen = numeroFacturaRiverGreen;
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

	public Date getFechaPickUp() {
		return fechaPickUp;
	}

	public void setFechaPickUp(Date fechaPickUp) {
		this.fechaPickUp = fechaPickUp;
	}

	public Plan getNuevoPlan() {
		return nuevoPlan;
	}

	public void setNuevoPlan(Plan nuevoPlan) {
		this.nuevoPlan = nuevoPlan;
	}

	public MotivoCambioPlan getMotivoCambioPlan() {
		return motivoCambioPlan;
	}

	public void setMotivoCambioPlan(MotivoCambioPlan motivoCambioPlan) {
		this.motivoCambioPlan = motivoCambioPlan;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public TipoTasaInteresEfectivaAnual getTipoTasaInteresEfectivaAnual() {
		return tipoTasaInteresEfectivaAnual;
	}

	public void setTipoTasaInteresEfectivaAnual(TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual) {
		this.tipoTasaInteresEfectivaAnual = tipoTasaInteresEfectivaAnual;
	}

	public TarjetaCredito getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(TarjetaCredito tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
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

	public Long getNumeroVale() {
		return numeroVale;
	}

	public void setNumeroVale(Long numeroVale) {
		this.numeroVale = numeroVale;
	}

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

	public String getAntelNroTrn() {
		return antelNroTrn;
	}

	public void setAntelNroTrn(String antelNroTrn) {
		this.antelNroTrn = antelNroTrn;
	}

	public String getAntelFormaPago() {
		return antelFormaPago;
	}

	public void setAntelFormaPago(String antelFormaPago) {
		this.antelFormaPago = antelFormaPago;
	}

	public String getAntelNroServicioCuenta() {
		return antelNroServicioCuenta;
	}

	public void setAntelNroServicioCuenta(String antelNroServicioCuenta) {
		this.antelNroServicioCuenta = antelNroServicioCuenta;
	}

	public Double getAntelImporte() {
		return antelImporte;
	}

	public void setAntelImporte(Double antelImporte) {
		this.antelImporte = antelImporte;
	}

	public Boolean getIncluirChip() {
		return incluirChip;
	}

	public void setIncluirChip(Boolean incluirChip) {
		this.incluirChip = incluirChip;
	}

	public Double getCostoEnvio() {
		return costoEnvio;
	}

	public void setCostoEnvio(Double costoEnvio) {
		this.costoEnvio = costoEnvio;
	}

	public Long getAtencionClienteCantidadVeces() {
		return atencionClienteCantidadVeces;
	}

	public void setAtencionClienteCantidadVeces(Long atencionClienteCantidadVeces) {
		this.atencionClienteCantidadVeces = atencionClienteCantidadVeces;
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

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
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

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public ResultadoEntregaDistribucion getResultadoEntregaDistribucion() {
		return resultadoEntregaDistribucion;
	}

	public ModalidadVenta getModalidadVenta() {
		return modalidadVenta;
	}

	public void setModalidadVenta(ModalidadVenta modalidadVenta) {
		this.modalidadVenta = modalidadVenta;
	}

	public AtencionClienteTipoContacto getAtencionClienteTipoContacto() {
		return atencionClienteTipoContacto;
	}

	public void setAtencionClienteTipoContacto(AtencionClienteTipoContacto atencionClienteTipoContacto) {
		this.atencionClienteTipoContacto = atencionClienteTipoContacto;
	}

	public AtencionClienteConcepto getAtencionClienteConcepto() {
		return atencionClienteConcepto;
	}

	public void setAtencionClienteConcepto(AtencionClienteConcepto atencionClienteConcepto) {
		this.atencionClienteConcepto = atencionClienteConcepto;
	}

	public void setResultadoEntregaDistribucion(ResultadoEntregaDistribucion resultadoEntregaDistribucion) {
		this.resultadoEntregaDistribucion = resultadoEntregaDistribucion;
	}

	public AtencionClienteRespuestaTecnicaComercial getAtencionClienteRespuestaTecnicaComercial() {
		return atencionClienteRespuestaTecnicaComercial;
	}

	public void setAtencionClienteRespuestaTecnicaComercial(
			AtencionClienteRespuestaTecnicaComercial atencionClienteRespuestaTecnicaComercial) {
		this.atencionClienteRespuestaTecnicaComercial = atencionClienteRespuestaTecnicaComercial;
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

	public Date getFechaEnvioANucleo() {
		return fechaEnvioANucleo;
	}

	public void setFechaEnvioANucleo(Date fechaEnvioANucleo) {
		this.fechaEnvioANucleo = fechaEnvioANucleo;
	}

	public Date getFechaEnvioAIZI() {
		return fechaEnvioAIZI;
	}

	public void setFechaEnvioAIZI(Date fechaEnvioAIZI) {
		this.fechaEnvioAIZI = fechaEnvioAIZI;
	}

	public Date getFechaEnvioAGLA() {
		return fechaEnvioAGLA;
	}

	public void setFechaEnvioAGLA(Date fechaEnvioAGLA) {
		this.fechaEnvioAGLA = fechaEnvioAGLA;
	}

	public Date getFechaAtencionClienteGestionador() {
		return fechaAtencionClienteGestionador;
	}

	public void setFechaAtencionClienteGestionador(Date fechaAtencionClienteGestionador) {
		this.fechaAtencionClienteGestionador = fechaAtencionClienteGestionador;
	}
	
	public Date getFechaAtencionClienteOperador() {
		return fechaAtencionClienteOperador;
	}

	public void setFechaAtencionClienteOperador(Date fechaAtencionClienteOperador) {
		this.fechaAtencionClienteOperador = fechaAtencionClienteOperador;
	}

	public Date getFechaAtencionClienteDevuelto() {
		return fechaAtencionClienteDevuelto;
	}

	public void setFechaAtencionClienteDevuelto(Date fechaAtencionClienteDevuelto) {
		this.fechaAtencionClienteDevuelto = fechaAtencionClienteDevuelto;
	}

	public Date getFechaAtencionClienteCierre() {
		return fechaAtencionClienteCierre;
	}

	public void setFechaAtencionClienteCierre(Date fechaAtencionClienteCierre) {
		this.fechaAtencionClienteCierre = fechaAtencionClienteCierre;
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

	public Usuario getAtencionClienteOperador() {
		return atencionClienteOperador;
	}

	public void setAtencionClienteOperador(Usuario atencionClienteOperador) {
		this.atencionClienteOperador = atencionClienteOperador;
	}

	public Usuario getAtencionClienteGestionador() {
		return atencionClienteGestionador;
	}

	public void setAtencionClienteGestionador(Usuario atencionClienteGestionador) {
		this.atencionClienteGestionador = atencionClienteGestionador;
	}

	public Set<ContratoArchivoAdjunto> getArchivosAdjuntos() {
		return archivosAdjuntos;
	}

	public void setArchivosAdjuntos(Set<ContratoArchivoAdjunto> archivosAdjuntos) {
		this.archivosAdjuntos = archivosAdjuntos;
	}

	public Set<ContratoDireccion> getDirecciones() {
		return direcciones;
	}
	
	public void setDirecciones(Set<ContratoDireccion> direcciones) {
		this.direcciones = direcciones;
	}
}