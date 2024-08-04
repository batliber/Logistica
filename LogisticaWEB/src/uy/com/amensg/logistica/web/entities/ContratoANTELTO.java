package uy.com.amensg.logistica.entities;

public class ContratoANTELTO {

	private String numeroTramite;
	private String numeroOrden;
	private String direccionEntregaCalle;
	private String direccionEntregaNumero;
	private String direccionEntregaDepartamento;
	private String direccionEntregaLocalidad;
	private EstadoANTELTO estado;
	private String fechaPickUp;
	private String fechaEntrega;

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

	public String getDireccionEntregaCalle() {
		return direccionEntregaCalle;
	}

	public void setDireccionEntregaCalle(String direccionEntregaCalle) {
		this.direccionEntregaCalle = direccionEntregaCalle;
	}

	public String getDireccionEntregaNumero() {
		return direccionEntregaNumero;
	}

	public void setDireccionEntregaNumero(String direccionEntregaNumero) {
		this.direccionEntregaNumero = direccionEntregaNumero;
	}

	public String getDireccionEntregaDepartamento() {
		return direccionEntregaDepartamento;
	}

	public void setDireccionEntregaDepartamento(String direccionEntregaDepartamento) {
		this.direccionEntregaDepartamento = direccionEntregaDepartamento;
	}

	public String getDireccionEntregaLocalidad() {
		return direccionEntregaLocalidad;
	}

	public void setDireccionEntregaLocalidad(String direccionEntregaLocalidad) {
		this.direccionEntregaLocalidad = direccionEntregaLocalidad;
	}

	public EstadoANTELTO getEstado() {
		return estado;
	}

	public void setEstado(EstadoANTELTO estado) {
		this.estado = estado;
	}

	public String getFechaPickUp() {
		return fechaPickUp;
	}

	public void setFechaPickUp(String fechaPickUp) {
		this.fechaPickUp = fechaPickUp;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
}