package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ContratoArchivoAdjuntoTO extends BaseTO {

	private String url;
	private Date fechaSubida;
	private ContratoTO contrato;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getFechaSubida() {
		return fechaSubida;
	}

	public void setFechaSubida(Date fechaSubida) {
		this.fechaSubida = fechaSubida;
	}

	public ContratoTO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoTO contrato) {
		this.contrato = contrato;
	}
}