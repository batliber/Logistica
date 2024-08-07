package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "acm_interface_contrato")
public class ACMInterfaceContrato implements Serializable {

	private static final long serialVersionUID = 5858491604040285536L;

	@Id
	@Column(name = "mid")
	private Long mid;

	@Column(name = "fecha_fin_contrato")
	private Date fechaFinContrato;

	@Column(name = "tipo_contrato_codigo")
	private String tipoContratoCodigo;

	@Column(name = "tipo_contrato_descripcion")
	private String tipoContratoDescripcion;

	@Column(name = "documento_tipo")
	private Long documentoTipo;

	@Column(name = "documento")
	private String documento;

	@Column(name = "numero_cliente")
	private Long numeroCliente;

	@Column(name = "numero_contrato")
	private String numeroContrato;
	
	@Column(name = "estado_contrato")
	private String estadoContrato;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "localidad")
	private String localidad;

	@Column(name = "codigo_postal")
	private String codigoPostal;

	@Column(name = "agente")
	private String agente;

	@Column(name = "equipo")
	private String equipo;

	@Column(name = "fecha_exportacion")
	private Date fechaExportacion;

	@Column(name = "fecha_exportacion_anterior")
	private Date fechaExportacionAnterior;
	
	@Column(name = "random")
	private Long random;
	
	@Column(name = "uact")
	private Long uact;

	@Column(name = "fact")
	private Date fact;

	@Column(name = "term")
	private Long term;
	
	@ManyToOne(
		fetch=FetchType.EAGER
	)
	@JoinTable(
		name = "acm_interface_mid_persona", 
		joinColumns = @JoinColumn(name = "mid"),
		inverseJoinColumns = @JoinColumn(name = "persona_id")
	)
	private ACMInterfacePersona acmInterfacePersona;
	
	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Date getFechaFinContrato() {
		return fechaFinContrato;
	}

	public void setFechaFinContrato(Date fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
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

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public String getEstadoContrato() {
		return estadoContrato;
	}

	public void setEstadoContrato(String estadoContrato) {
		this.estadoContrato = estadoContrato;
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

	public Date getFechaExportacion() {
		return fechaExportacion;
	}

	public void setFechaExportacion(Date fechaExportacion) {
		this.fechaExportacion = fechaExportacion;
	}

	public Date getFechaExportacionAnterior() {
		return fechaExportacionAnterior;
	}

	public void setFechaExportacionAnterior(Date fechaExportacionAnterior) {
		this.fechaExportacionAnterior = fechaExportacionAnterior;
	}

	public Long getRandom() {
		return random;
	}

	public void setRandom(Long random) {
		this.random = random;
	}

	public Long getUact() {
		return uact;
	}

	public void setUact(Long uact) {
		this.uact = uact;
	}

	public Date getFact() {
		return fact;
	}

	public void setFact(Date fact) {
		this.fact = fact;
	}

	public Long getTerm() {
		return term;
	}

	public void setTerm(Long term) {
		this.term = term;
	}

	public ACMInterfacePersona getAcmInterfacePersona() {
		return acmInterfacePersona;
	}

	public void setAcmInterfacePersona(ACMInterfacePersona acmInterfacePersona) {
		this.acmInterfacePersona = acmInterfacePersona;
	}
}