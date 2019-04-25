package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class LiquidacionTO extends BaseTO {

	private Long idRegistro;
	private Long numeroContrato;
	private Date fecha;
	private String plan;
	private Long mid;
	private Long said;
	private String serie;
	private String dc;
	private Double importe;
	private Long cant;
	private Date fechaLiquidacion;
	private Long idConcepto;
	private String nombreConcepto;
	private String modelo;
	private String fabricante;
	private Long idClaseConcepto;
	private String nomClaseConcepto;
	private Long rlid;
	private EmpresaTO empresa;
	private MonedaTO moneda;

	public Long getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}

	public Long getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(Long numeroContrato) {
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

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public MonedaTO getMoneda() {
		return moneda;
	}

	public void setMoneda(MonedaTO moneda) {
		this.moneda = moneda;
	}
}