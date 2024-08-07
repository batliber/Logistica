package uy.com.amensg.logistica.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "acm_interface_prepago")
public class ACMInterfacePrepago implements Serializable {

	private static final long serialVersionUID = 2102687342044596794L;

	@Id
	@Column(name = "mid")
	private Long mid;

	@Column(name = "mes_ano")
	private Date mesAno;

	@Column(name = "monto_mes_actual")
	private Double montoMesActual;

	@Column(name = "monto_mes_anterior_1")
	private Double montoMesAnterior1;

	@Column(name = "monto_mes_anterior_2")
	private Double montoMesAnterior2;

	@Column(name = "monto_promedio")
	private Double montoPromedio;

	@Column(name = "fecha_exportacion")
	private Date fechaExportacion;

	@Column(name = "fecha_exportacion_anterior")
	private Date fechaExportacionAnterior;

	@Column(name = "fecha_activacion_kit")
	private Date fechaActivacionKit;

	@Column(name = "agente")
	private String agente;
	
	@Column(name = "random")
	private Long random;
	
	@Column(name = "uact")
	private Long uact;

	@Column(name = "fact")
	private Date fact;

	@Column(name = "term")
	private Long term;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinTable(
		name = "acm_interface_mid_persona", 
		joinColumns = @JoinColumn(name = "mid"),
		inverseJoinColumns = @JoinColumn(name = "persona_id")
	)
	private ACMInterfacePersona acmInterfacePersona;
	
	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Date getMesAno() {
		return mesAno;
	}

	public void setMesAno(Date mesAno) {
		this.mesAno = mesAno;
	}

	public Double getMontoMesActual() {
		return montoMesActual;
	}

	public void setMontoMesActual(Double montoMesActual) {
		this.montoMesActual = montoMesActual;
	}

	public Double getMontoMesAnterior1() {
		return montoMesAnterior1;
	}

	public void setMontoMesAnterior1(Double montoMesAnterior1) {
		this.montoMesAnterior1 = montoMesAnterior1;
	}

	public Double getMontoMesAnterior2() {
		return montoMesAnterior2;
	}

	public void setMontoMesAnterior2(Double montoMesAnterior2) {
		this.montoMesAnterior2 = montoMesAnterior2;
	}

	public Double getMontoPromedio() {
		return montoPromedio;
	}

	public void setMontoPromedio(Double montoPromedio) {
		this.montoPromedio = montoPromedio;
	}

	public Date getFechaExportacion() {
		return fechaExportacion;
	}

	public void setFechaExportacion(Date fechaExportacion) {
		this.fechaExportacion = fechaExportacion;
	}

	public Date getFechaExportacionAnterior() {
		return fechaExportacionAnterior;
	}

	public void setFechaExportacionAnterior(Date fechaExportacionAnterior) {
		this.fechaExportacionAnterior = fechaExportacionAnterior;
	}

	public Date getFechaActivacionKit() {
		return fechaActivacionKit;
	}

	public void setFechaActivacionKit(Date fechaActivacionKit) {
		this.fechaActivacionKit = fechaActivacionKit;
	}

	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
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

	public ACMInterfacePersona getAcmInterfacePersona() {
		return acmInterfacePersona;
	}

	public void setAcmInterfacePersona(ACMInterfacePersona acmInterfacePersona) {
		this.acmInterfacePersona = acmInterfacePersona;
	}
}