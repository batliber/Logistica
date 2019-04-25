package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ACMInterfaceNumeroContratoTO {

	private Long numeroContrato;
	private Long procesoId;
	private Long uact;
	private Date fact;
	private Long term;
	private Long ucre;
	private Date fcre;
	private ACMInterfaceEstadoTO estado;

	public Long getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(Long numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public Long getProcesoId() {
		return procesoId;
	}

	public void setProcesoId(Long procesoId) {
		this.procesoId = procesoId;
	}

	public Long getUact() {
		return uact;
	}

	public void setUact(Long uact) {
		this.uact = uact;
	}

	public Date getFact() {
		return fact;
	}

	public void setFact(Date fact) {
		this.fact = fact;
	}

	public Long getTerm() {
		return term;
	}

	public void setTerm(Long term) {
		this.term = term;
	}

	public Long getUcre() {
		return ucre;
	}

	public void setUcre(Long ucre) {
		this.ucre = ucre;
	}

	public Date getFcre() {
		return fcre;
	}

	public void setFcre(Date fcre) {
		this.fcre = fcre;
	}

	public ACMInterfaceEstadoTO getEstado() {
		return estado;
	}

	public void setEstado(ACMInterfaceEstadoTO estado) {
		this.estado = estado;
	}
}