package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class TasaInteresEfectivaAnualTO extends BaseTO {

	private Long cuotasDesde;
	private Long cuotasHasta;
	private Double montoDesde;
	private Double montoHasta;
	private Date fechaVigenciaHasta;
	private Double valor;
	private TipoTasaInteresEfectivaAnualTO tipoTasaInteresEfectivaAnual;

	public Long getCuotasDesde() {
		return cuotasDesde;
	}

	public void setCuotasDesde(Long cuotasDesde) {
		this.cuotasDesde = cuotasDesde;
	}

	public Long getCuotasHasta() {
		return cuotasHasta;
	}

	public void setCuotasHasta(Long cuotasHasta) {
		this.cuotasHasta = cuotasHasta;
	}

	public Double getMontoDesde() {
		return montoDesde;
	}

	public void setMontoDesde(Double montoDesde) {
		this.montoDesde = montoDesde;
	}

	public Double getMontoHasta() {
		return montoHasta;
	}

	public void setMontoHasta(Double montoHasta) {
		this.montoHasta = montoHasta;
	}

	public Date getFechaVigenciaHasta() {
		return fechaVigenciaHasta;
	}

	public void setFechaVigenciaHasta(Date fechaVigenciaHasta) {
		this.fechaVigenciaHasta = fechaVigenciaHasta;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public TipoTasaInteresEfectivaAnualTO getTipoTasaInteresEfectivaAnual() {
		return tipoTasaInteresEfectivaAnual;
	}

	public void setTipoTasaInteresEfectivaAnual(TipoTasaInteresEfectivaAnualTO tipoTasaInteresEfectivaAnual) {
		this.tipoTasaInteresEfectivaAnual = tipoTasaInteresEfectivaAnual;
	}
}