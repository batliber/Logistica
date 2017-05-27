package uy.com.amensg.logistica.entities;

import java.util.Collection;
import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ActivacionSubloteTO extends BaseTO {

	private Long numero;
	private String descripcion;
	private Date fechaAsignacionDistribuidor;
	private Date fechaAsignacionPuntoVenta;
	private EmpresaTO empresa;
	private UsuarioTO distribuidor;
	private PuntoVentaTO puntoVenta;
	private Collection<ActivacionTO> activaciones;

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaAsignacionDistribuidor() {
		return fechaAsignacionDistribuidor;
	}

	public void setFechaAsignacionDistribuidor(Date fechaAsignacionDistribuidor) {
		this.fechaAsignacionDistribuidor = fechaAsignacionDistribuidor;
	}

	public Date getFechaAsignacionPuntoVenta() {
		return fechaAsignacionPuntoVenta;
	}

	public void setFechaAsignacionPuntoVenta(Date fechaAsignacionPuntoVenta) {
		this.fechaAsignacionPuntoVenta = fechaAsignacionPuntoVenta;
	}

	public EmpresaTO getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaTO empresa) {
		this.empresa = empresa;
	}

	public UsuarioTO getDistribuidor() {
		return distribuidor;
	}

	public void setDistribuidor(UsuarioTO distribuidor) {
		this.distribuidor = distribuidor;
	}

	public PuntoVentaTO getPuntoVenta() {
		return puntoVenta;
	}

	public void setPuntoVenta(PuntoVentaTO puntoVenta) {
		this.puntoVenta = puntoVenta;
	}

	public Collection<ActivacionTO> getActivaciones() {
		return activaciones;
	}

	public void setActivaciones(Collection<ActivacionTO> activaciones) {
		this.activaciones = activaciones;
	}
}