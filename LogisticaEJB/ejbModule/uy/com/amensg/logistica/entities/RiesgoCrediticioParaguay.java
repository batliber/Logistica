package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "riesgo_crediticio_paraguay")
public class RiesgoCrediticioParaguay extends BaseEntity {

	private static final long serialVersionUID = -2480378853695376254L;

	@Column(name = "documento")
	private String documento;
	
	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;
	
	@Column(name = "fecha_vigencia_desde")
	private Date fechaVigenciaDesde;
	
	@Column(name = "fecha_importacion")
	private Date fechaImportacion;
	
	@Column(name = "condicion")
	private String condicion;
	
	@Column(name = "motivo")
	private String motivo;
	
	@Column(name = "ips_documento")
	private String ipsDocumento;
	
	@Column(name = "ips_nombres")
	private String ipsNombres;
	
	@Column(name = "ips_apellidos")
	private String ipsApellidos;
	
	@Column(name = "ips_fnac")
	private String ipsFnac;
	
	@Column(name = "ips_sexo")
	private String ipsSexo;
	
	@Column(name = "ips_tipo_aseg")
	private String ipsTipoAseg;
	
	@Column(name = "ips_empleador")
	private String ipsEmpleador;
	
	@Column(name = "ips_estado")
	private String ipsEstado;
	
	@Column(name = "ips_meses_aporte")
	private String ipsMesesAporte;
	
	@Column(name = "ips_nu_patronal")
	private String ipsNuPatronal;
	
	@Column(name = "ips_upa")
	private String ipsUPA;
	
	@Column(name = "set_documento")
	private String setDocumento;
	
	@Column(name = "set_dv")
	private String setDV;
	
	@Column(name = "set_nombre_completo")
	private String setNombreCompleto;
	
	@Column(name = "set_estado")
	private String setEstado;
	
	@Column(name = "set_situacion")
	private String setSituacion;
	
	@Column(name = "sfp_entidad")
	private String sfpEntidad;
	
	@Column(name = "sfp_cedula")
	private String sfpCedula;
	
	@Column(name = "sfp_nombres")
	private String sfpNombres;
	
	@Column(name = "sfp_apellidos")
	private String sfpApellidos;
	
	@Column(name = "sfp_presupuesto")
	private String sfpPresupuesto;
	
	@Column(name = "sfp_fnac")
	private String sfpFnac;
	
	@Column(name = "respuesta_externa")
	private String respuestaExterna;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "estado_riesgo_crediticio_id", nullable = false)
	private EstadoRiesgoCrediticio estadoRiesgoCrediticio;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "situacion_riesgo_crediticio_paraguay_id", nullable = false)
	private SituacionRiesgoCrediticioParaguay situacionRiesgoCrediticioParaguay;

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Date getFechaVigenciaDesde() {
		return fechaVigenciaDesde;
	}

	public void setFechaVigenciaDesde(Date fechaVigenciaDesde) {
		this.fechaVigenciaDesde = fechaVigenciaDesde;
	}

	public Date getFechaImportacion() {
		return fechaImportacion;
	}

	public void setFechaImportacion(Date fechaImportacion) {
		this.fechaImportacion = fechaImportacion;
	}

	public String getCondicion() {
		return condicion;
	}

	public void setCondicion(String condicion) {
		this.condicion = condicion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getIpsDocumento() {
		return ipsDocumento;
	}

	public void setIpsDocumento(String ipsDocumento) {
		this.ipsDocumento = ipsDocumento;
	}

	public String getIpsNombres() {
		return ipsNombres;
	}

	public void setIpsNombres(String ipsNombres) {
		this.ipsNombres = ipsNombres;
	}

	public String getIpsApellidos() {
		return ipsApellidos;
	}

	public void setIpsApellidos(String ipsApellidos) {
		this.ipsApellidos = ipsApellidos;
	}

	public String getIpsFnac() {
		return ipsFnac;
	}

	public void setIpsFnac(String ipsFnac) {
		this.ipsFnac = ipsFnac;
	}

	public String getIpsSexo() {
		return ipsSexo;
	}

	public void setIpsSexo(String ipsSexo) {
		this.ipsSexo = ipsSexo;
	}

	public String getIpsTipoAseg() {
		return ipsTipoAseg;
	}

	public void setIpsTipoAseg(String ipsTipoAseg) {
		this.ipsTipoAseg = ipsTipoAseg;
	}

	public String getIpsEmpleador() {
		return ipsEmpleador;
	}

	public void setIpsEmpleador(String ipsEmpleador) {
		this.ipsEmpleador = ipsEmpleador;
	}

	public String getIpsEstado() {
		return ipsEstado;
	}

	public void setIpsEstado(String ipsEstado) {
		this.ipsEstado = ipsEstado;
	}

	public String getIpsMesesAporte() {
		return ipsMesesAporte;
	}

	public void setIpsMesesAporte(String ipsMesesAporte) {
		this.ipsMesesAporte = ipsMesesAporte;
	}

	public String getIpsNuPatronal() {
		return ipsNuPatronal;
	}

	public void setIpsNuPatronal(String ipsNuPatronal) {
		this.ipsNuPatronal = ipsNuPatronal;
	}

	public String getIpsUPA() {
		return ipsUPA;
	}

	public void setIpsUPA(String ipsUPA) {
		this.ipsUPA = ipsUPA;
	}

	public String getSetDocumento() {
		return setDocumento;
	}

	public void setSetDocumento(String setDocumento) {
		this.setDocumento = setDocumento;
	}

	public String getSetDV() {
		return setDV;
	}

	public void setSetDV(String setDV) {
		this.setDV = setDV;
	}

	public String getSetNombreCompleto() {
		return setNombreCompleto;
	}

	public void setSetNombreCompleto(String setNombreCompleto) {
		this.setNombreCompleto = setNombreCompleto;
	}

	public String getSetEstado() {
		return setEstado;
	}

	public void setSetEstado(String setEstado) {
		this.setEstado = setEstado;
	}

	public String getSetSituacion() {
		return setSituacion;
	}

	public void setSetSituacion(String setSituacion) {
		this.setSituacion = setSituacion;
	}

	public String getSfpEntidad() {
		return sfpEntidad;
	}

	public void setSfpEntidad(String sfpEntidad) {
		this.sfpEntidad = sfpEntidad;
	}

	public String getSfpCedula() {
		return sfpCedula;
	}

	public void setSfpCedula(String sfpCedula) {
		this.sfpCedula = sfpCedula;
	}

	public String getSfpNombres() {
		return sfpNombres;
	}

	public void setSfpNombres(String sfpNombres) {
		this.sfpNombres = sfpNombres;
	}

	public String getSfpApellidos() {
		return sfpApellidos;
	}

	public void setSfpApellidos(String sfpApellidos) {
		this.sfpApellidos = sfpApellidos;
	}

	public String getSfpPresupuesto() {
		return sfpPresupuesto;
	}

	public void setSfpPresupuesto(String sfpPresupuesto) {
		this.sfpPresupuesto = sfpPresupuesto;
	}

	public String getSfpFnac() {
		return sfpFnac;
	}

	public void setSfpFnac(String sfpFnac) {
		this.sfpFnac = sfpFnac;
	}

	public String getRespuestaExterna() {
		return respuestaExterna;
	}

	public void setRespuestaExterna(String respuestaExterna) {
		this.respuestaExterna = respuestaExterna;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public EstadoRiesgoCrediticio getEstadoRiesgoCrediticio() {
		return estadoRiesgoCrediticio;
	}

	public void setEstadoRiesgoCrediticio(EstadoRiesgoCrediticio estadoRiesgoCrediticio) {
		this.estadoRiesgoCrediticio = estadoRiesgoCrediticio;
	}

	public SituacionRiesgoCrediticioParaguay getSituacionRiesgoCrediticioParaguay() {
		return situacionRiesgoCrediticioParaguay;
	}

	public void setSituacionRiesgoCrediticioParaguay(SituacionRiesgoCrediticioParaguay situacionRiesgoCrediticioParaguay) {
		this.situacionRiesgoCrediticioParaguay = situacionRiesgoCrediticioParaguay;
	}
}