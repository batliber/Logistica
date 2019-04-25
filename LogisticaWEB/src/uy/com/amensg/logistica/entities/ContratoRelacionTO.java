package uy.com.amensg.logistica.entities;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ContratoRelacionTO extends BaseTO {

	private ContratoTO contrato;
	private ContratoTO contratoRelacionado;

	public ContratoTO getContrato() {
		return contrato;
	}

	public void setContrato(ContratoTO contrato) {
		this.contrato = contrato;
	}

	public ContratoTO getContratoRelacionado() {
		return contratoRelacionado;
	}

	public void setContratoRelacionado(ContratoTO contratoRelacionado) {
		this.contratoRelacionado = contratoRelacionado;
	}
}