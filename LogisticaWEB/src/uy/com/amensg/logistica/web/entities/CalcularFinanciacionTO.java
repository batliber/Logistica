package uy.com.amensg.logistica.web.entities;

public class CalcularFinanciacionTO {

	private Long monedaId;
	private Long tipoTasaInteresEfectivaAnualId;
	private Double monto;
	private Long cuotas;

	public Long getMonedaId() {
		return monedaId;
	}

	public void setMonedaId(Long monedaId) {
		this.monedaId = monedaId;
	}

	public Long getTipoTasaInteresEfectivaAnualId() {
		return tipoTasaInteresEfectivaAnualId;
	}

	public void setTipoTasaInteresEfectivaAnualId(Long tipoTasaInteresEfectivaAnualId) {
		this.tipoTasaInteresEfectivaAnualId = tipoTasaInteresEfectivaAnualId;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Long getCuotas() {
		return cuotas;
	}

	public void setCuotas(Long cuotas) {
		this.cuotas = cuotas;
	}
}