package uy.com.amensg.logistica.entities;

import java.io.Serializable;

public class DatosFinanciacion implements Serializable {

	private static final long serialVersionUID = 6311467580527095731L;
	
	private Double gastosAdministrativos;
	private Double gastosConcesion;
	private Double gastosAdministrativosTotales;
	private Double intereses;
	private Double montoTotalFinanciado;
	private Double montoCuota;
	private Double valorUnidadIndexada;
	private Double valorTasaInteresEfectivaAnual;

	public Double getGastosAdministrativos() {
		return gastosAdministrativos;
	}

	public void setGastosAdministrativos(Double gastosAdministrativos) {
		this.gastosAdministrativos = gastosAdministrativos;
	}

	public Double getGastosConcesion() {
		return gastosConcesion;
	}

	public void setGastosConcesion(Double gastosConcesion) {
		this.gastosConcesion = gastosConcesion;
	}

	public Double getGastosAdministrativosTotales() {
		return gastosAdministrativosTotales;
	}

	public void setGastosAdministrativosTotales(Double gastosAdministrativosTotales) {
		this.gastosAdministrativosTotales = gastosAdministrativosTotales;
	}

	public Double getIntereses() {
		return intereses;
	}

	public void setIntereses(Double intereses) {
		this.intereses = intereses;
	}

	public Double getMontoTotalFinanciado() {
		return montoTotalFinanciado;
	}

	public void setMontoTotalFinanciado(Double montoTotalFinanciado) {
		this.montoTotalFinanciado = montoTotalFinanciado;
	}

	public Double getMontoCuota() {
		return montoCuota;
	}

	public void setMontoCuota(Double montoCuota) {
		this.montoCuota = montoCuota;
	}

	public Double getValorUnidadIndexada() {
		return valorUnidadIndexada;
	}

	public void setValorUnidadIndexada(Double valorUnidadIndexada) {
		this.valorUnidadIndexada = valorUnidadIndexada;
	}

	public Double getValorTasaInteresEfectivaAnual() {
		return valorTasaInteresEfectivaAnual;
	}

	public void setValorTasaInteresEfectivaAnual(Double valorTasaInteresEfectivaAnual) {
		this.valorTasaInteresEfectivaAnual = valorTasaInteresEfectivaAnual;
	}
}