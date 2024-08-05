package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;

@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "hibernate_sequence"
	)
	@SequenceGenerator(
		name = "hibernate_sequence",
		sequenceName = "hibernate_sequence",
		allocationSize = 1
	)
	protected Long id;
	
	@Column(name = "ucre")
	protected Long ucre;
	
	@Column(name = "fcre")
	protected Date fcre;
	
	@Column(name = "uact")
	protected Long uact;
	
	@Column(name = "fact")
	protected Date fact;
	
	@Column(name = "term")
	protected Long term;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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