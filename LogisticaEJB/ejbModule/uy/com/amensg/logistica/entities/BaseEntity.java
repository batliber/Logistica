package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue
	private Long id;
	
	@Column(name = "ucre")
	private Long ucre;
	
	@Column(name = "fcre")
	private Date fcre;
	
	@Column(name = "uact")
	private Long uact;
	
	@Column(name = "fact")
	private Date fact;
	
	@Column(name = "term")
	private Long term;

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