package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "acm_interface_numero_contrato")
public class ACMInterfaceNumeroContrato implements Serializable {

	private static final long serialVersionUID = 760315227588668424L;

	@Id
	@Column(name = "numero_contrato")
	private Long numeroContrato;

	@Column(name = "proceso_id")
	private Long procesoId;

	@Column(name = "uact")
	private Long uact;

	@Column(name = "fact")
	private Date fact;

	@Column(name = "term")
	private Long term;
	
	@Column(name = "ucre")
	private Long ucre;

	@Column(name = "fcre")
	private Date fcre;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado", nullable = true)
	private ACMInterfaceEstado estado;

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

	public ACMInterfaceEstado getEstado() {
		return estado;
	}

	public void setEstado(ACMInterfaceEstado estado) {
		this.estado = estado;
	}
}