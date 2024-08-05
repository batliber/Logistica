package uy.com.amensg.logistica.web.entities;

import java.util.Date;

public class BaseTO {

	private Long id;
	private Long ucre;
	private Date fcre;
	private Long uact;
	private Date fact;
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