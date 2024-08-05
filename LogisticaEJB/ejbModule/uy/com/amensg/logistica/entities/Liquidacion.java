package uy.com.amensg.logistica.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "liquidacion")
public class Liquidacion extends BaseEntity {

	private static final long serialVersionUID = -1407526684467814434L;

	@Column(name = "id_registro")
	private Long idRegistro;
	
	@Column(name = "numero_contrato")
	private String numeroContrato;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "plan")
	private String plan;
	
	@Column(name = "mid")
	private Long mid;
	
	@Column(name = "said")
	private Long said;
	
	@Column(name = "serie")
	private String serie;
	
	@Column(name = "dc")
	private String dc;
	
	@Column(name = "importe")
	private Double importe;
	
	@Column(name = "cant")
	private Long cant;
	
	@Column(name = "fecha_liquidacion")
	private Date fechaLiquidacion;
	
	@Column(name = "id_concepto")
	private Long idConcepto;
	
	@Column(name = "nombre_concepto")
	private String nombreConcepto;
	
	@Column(name = "modelo")
	private String modelo;
	
	@Column(name = "fabricante")
	private String fabricante;
	
	@Column(name = "id_clase_concepto")
	private Long idClaseConcepto;
	
	@Column(name = "nom_clase_concepto")
	private String nomClaseConcepto;
	
	@Column(name = "rlid")
	private Long rlid;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "empresa_id", nullable = true)
	private Empresa empresa;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "moneda_id", nullable = true)
	private Moneda moneda;

	public Long getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getSaid() {
		return said;
	}

	public void setSaid(Long said) {
		this.said = said;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Long getCant() {
		return cant;
	}

	public void setCant(Long cant) {
		this.cant = cant;
	}

	public Date getFechaLiquidacion() {
		return fechaLiquidacion;
	}

	public void setFechaLiquidacion(Date fechaLiquidacion) {
		this.fechaLiquidacion = fechaLiquidacion;
	}

	public Long getIdConcepto() {
		return idConcepto;
	}

	public void setIdConcepto(Long idConcepto) {
		this.idConcepto = idConcepto;
	}

	public String getNombreConcepto() {
		return nombreConcepto;
	}

	public void setNombreConcepto(String nombreConcepto) {
		this.nombreConcepto = nombreConcepto;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Long getIdClaseConcepto() {
		return idClaseConcepto;
	}

	public void setIdClaseConcepto(Long idClaseConcepto) {
		this.idClaseConcepto = idClaseConcepto;
	}

	public String getNomClaseConcepto() {
		return nomClaseConcepto;
	}

	public void setNomClaseConcepto(String nomClaseConcepto) {
		this.nomClaseConcepto = nomClaseConcepto;
	}

	public Long getRlid() {
		return rlid;
	}

	public void setRlid(Long rlid) {
		this.rlid = rlid;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
}