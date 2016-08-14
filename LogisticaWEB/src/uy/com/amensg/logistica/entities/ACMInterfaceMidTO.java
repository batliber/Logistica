package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ACMInterfaceMidTO {

	private Long mid;
	private Long procesoId;
	private Long uact;
	private Date fact;
	private Long term;
	private ACMInterfaceEstadoTO estado;

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public ACMInterfaceEstadoTO getEstado() {
		return estado;
	}

	public void setEstado(ACMInterfaceEstadoTO estado) {
		this.estado = estado;
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
}