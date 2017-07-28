package uy.com.amensg.logistica.entities;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class TipoProductoTO extends BaseTO {

	private String descripcion;
	private FormaPagoCuotaTO formaPagoCuota;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public FormaPagoCuotaTO getFormaPagoCuota() {
		return formaPagoCuota;
	}

	public void setFormaPagoCuota(FormaPagoCuotaTO formaPagoCuota) {
		this.formaPagoCuota = formaPagoCuota;
	}
}