package uy.com.amensg.logistica.web.entities;

import java.util.Collection;

public class EmpresaTO extends BaseTO {

	private String nombre;
	private String logoURL;
	private Long codigoPromotor;
	private String nombreContrato;
	private String nombreSucursal;
	private String direccion;
	private Boolean omitirControlVendidos;
	private Collection<FormaPagoTO> formaPagos;
	private Collection<UsuarioTO> empresaUsuarioContratos;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	public Long getCodigoPromotor() {
		return codigoPromotor;
	}

	public void setCodigoPromotor(Long codigoPromotor) {
		this.codigoPromotor = codigoPromotor;
	}

	public String getNombreContrato() {
		return nombreContrato;
	}

	public void setNombreContrato(String nombreContrato) {
		this.nombreContrato = nombreContrato;
	}

	public String getNombreSucursal() {
		return nombreSucursal;
	}

	public void setNombreSucursal(String nombreSucursal) {
		this.nombreSucursal = nombreSucursal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getOmitirControlVendidos() {
		return omitirControlVendidos;
	}

	public void setOmitirControlVendidos(Boolean omitirControlVendidos) {
		this.omitirControlVendidos = omitirControlVendidos;
	}

	public Collection<FormaPagoTO> getFormaPagos() {
		return formaPagos;
	}

	public void setFormaPagos(Collection<FormaPagoTO> formaPagos) {
		this.formaPagos = formaPagos;
	}

	public Collection<UsuarioTO> getEmpresaUsuarioContratos() {
		return empresaUsuarioContratos;
	}

	public void setEmpresaUsuarioContratos(Collection<UsuarioTO> empresaUsuarioContratos) {
		this.empresaUsuarioContratos = empresaUsuarioContratos;
	}
}