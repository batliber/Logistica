package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ACMInterfaceContratoTO {

	private String mid;
	private Date fechaFinContrato;
	private String tipoContratoCodigo;
	private String tipoContratoDescripcion;
	private Long documentoTipo;
	private String documento;
	private String nombre;
	private String direccion;
	private String localidad;
	private String codigoPostal;
	private String agente;
	private String equipo;
	private Date fechaExportacion;
	private Long uact;
	private Date fact;
	private Long term;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
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
}