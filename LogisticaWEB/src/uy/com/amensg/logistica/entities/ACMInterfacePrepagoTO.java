package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class ACMInterfacePrepagoTO {

	private Long mid;
	private Date mesAno;
	private Double montoMesActual;
	private Double montoMesAnterior1;
	private Double montoMesAnterior2;
	private Double montoPromedio;
	private Date fechaExportacion;
	private Date fechaActivacionKit;
	private Long uact;
	private Date fact;
	private Long term;

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

	public Date getFechaActivacionKit() {
		return fechaActivacionKit;
	}

	public void setFechaActivacionKit(Date fechaActivacionKit) {
		this.fechaActivacionKit = fechaActivacionKit;
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