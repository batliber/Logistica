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
@Table(name = "acm_interface_mid")
public class ACMInterfaceMid implements Serializable {

	private static final long serialVersionUID = -5665636107145717107L;

	@Id
	@Column(name = "mid")
	private Long mid;

	@Column(name = "proceso_id")
	private Long procesoId;

	@Column(name = "random")
	private Long random;
	
	@Column(name = "uact")
	private Long uact;

	@Column(name = "fact")
	private Date fact;

	@Column(name = "term")
	private Long term;

	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "estado", nullable = true)
	private ACMInterfaceEstado estado;
	
	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public ACMInterfaceEstado getEstado() {
		return estado;
	}

	public void setEstado(ACMInterfaceEstado estado) {
		this.estado = estado;
	}

	public Long getProcesoId() {
		return procesoId;
	}

	public void setProcesoId(Long procesoId) {
		this.procesoId = procesoId;
	}

	public Long getRandom() {
		return random;
	}

	public void setRandom(Long random) {
		this.random = random;
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