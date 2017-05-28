package uy.com.amensg.logistica.entities;

import java.util.Collection;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class EmpresaTO extends BaseTO {

	private String nombre;
	private String logoURL;
	private Long codigoPromotor;
	private String nombreContrato;
	private String nombreSucursal;
<<<<<<< HEAD
	private Collection<FormaPagoTO> formaPagos;
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git

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
<<<<<<< HEAD

	public Collection<FormaPagoTO> getFormaPagos() {
		return formaPagos;
	}

	public void setFormaPagos(Collection<FormaPagoTO> formaPagos) {
		this.formaPagos = formaPagos;
	}
=======
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
}