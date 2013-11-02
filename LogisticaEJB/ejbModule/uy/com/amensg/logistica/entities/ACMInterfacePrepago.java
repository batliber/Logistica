package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "acm_interface_prepago")
public class ACMInterfacePrepago implements Serializable {

	private static final long serialVersionUID = 2102687342044596794L;

	@Id
	@Column(name = "mid")
	private String mid;

	@Column(name = "mes_ano")
	private String mesAno;

	@Column(name = "monto_mes_actual")
	private String montoMesActual;

	@Column(name = "monto_mes_anterior_1")
	private String montoMesAnterior1;

	@Column(name = "monto_mes_anterior_2")
	private String montoMesAnterior2;

	@Column(name = "monto_promedio")
	private String montoPromedio;

	@Column(name = "uact")
	private Long uact;

	@Column(name = "fact")
	private Date fact;

	@Column(name = "term")
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