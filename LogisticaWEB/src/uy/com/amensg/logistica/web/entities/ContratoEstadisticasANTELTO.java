package uy.com.amensg.logistica.web.entities;

public class ContratoEstadisticasANTELTO {

	private String empresa;
	private String numeroTramite;
	private String numeroOrden;
	private String fechaCreacion;
	private String fechaPickUp;
	private String fechaEntregaDistribuidor;
	private String fechaResultadoEntregaDistribuidor;
	private String fechaDocumentacionFinalizada;
	private String estado;
	private String departamento;
	private String barrio;
	private String resultadoEntrega;

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getNumeroTramite() {
		return numeroTramite;
	}

	public void setNumeroTramite(String numeroTramite) {
		this.numeroTramite = numeroTramite;
	}

	public String getNumeroOrden() {
		return numeroOrden;
	}

	public void setNumeroOrden(String numeroOrden) {
		this.numeroOrden = numeroOrden;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getFechaPickUp() {
		return fechaPickUp;
	}

	public void setFechaPickUp(String fechaPickUp) {
		this.fechaPickUp = fechaPickUp;
	}

	public String getFechaEntregaDistribuidor() {
		return fechaEntregaDistribuidor;
	}

	public void setFechaEntregaDistribuidor(String fechaEntregaDistribuidor) {
		this.fechaEntregaDistribuidor = fechaEntregaDistribuidor;
	}

	public String getFechaResultadoEntregaDistribuidor() {
		return fechaResultadoEntregaDistribuidor;
	}

	public void setFechaResultadoEntregaDistribuidor(String fechaResultadoEntregaDistribuidor) {
		this.fechaResultadoEntregaDistribuidor = fechaResultadoEntregaDistribuidor;
	}

	public String getFechaDocumentacionFinalizada() {
		return fechaDocumentacionFinalizada;
	}

	public void setFechaDocumentacionFinalizada(String fechaDocumentacionFinalizada) {
		this.fechaDocumentacionFinalizada = fechaDocumentacionFinalizada;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getBarrio() {
		return barrio;
	}

	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}

	public String getResultadoEntrega() {
		return resultadoEntrega;
	}

	public void setResultadoEntrega(String resultadoEntrega) {
		this.resultadoEntrega = resultadoEntrega;
	}
}