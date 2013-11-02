package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ACMInterfacePrepagoTO {

	private String mid;
	private String mesAno;
	private String montoMesActual;
	private String montoMesAnterior1;
	private String montoMesAnterior2;
	private String montoPromedio;
	private Long uact;
	private Date fact;
	private Long term;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
	}

	public String getMontoMesActual() {
		return montoMesActual;
	}

	public void setMontoMesActual(String montoMesActual) {
		this.montoMesActual = montoMesActual;
	}

	public String getMontoMesAnterior1() {
		return montoMesAnterior1;
	}

	public void setMontoMesAnterior1(String montoMesAnterior1) {
		this.montoMesAnterior1 = montoMesAnterior1;
	}

	public String getMontoMesAnterior2() {
		return montoMesAnterior2;
	}

	public void setMontoMesAnterior2(String montoMesAnterior2) {
		this.montoMesAnterior2 = montoMesAnterior2;
	}

	public String getMontoPromedio() {
		return montoPromedio;
	}

	public void setMontoPromedio(String montoPromedio) {
		this.montoPromedio = montoPromedio;
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