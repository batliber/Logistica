package uy.com.amensg.logistica.entities;

public class ValidarFinanciacionTO {

	private Long monedaId;
	private Long formaPagoId;
	private Double monto;

	public Long getMonedaId() {
		return monedaId;
	}

	public void setMonedaId(Long monedaId) {
		this.monedaId = monedaId;
	}

	public Long getFormaPagoId() {
		return formaPagoId;
	}

	public void setFormaPagoId(Long formaPagoId) {
		this.formaPagoId = formaPagoId;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}
}