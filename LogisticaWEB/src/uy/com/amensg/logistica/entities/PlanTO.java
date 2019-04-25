package uy.com.amensg.logistica.entities;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class PlanTO extends BaseTO {

	private String descripcion;
	private String abreviacion;
	private Double consumoMinimo;
	private String duracion;
	private Double precioMinutoDestinosAntelHorarioNormal;
	private Double precioMinutoDestinosAntelHorarioReducido;
	private Long rendimientoMinutosMensualDestinosAntelHorarioNormal;
	private Long rendimientoMinutosMensualDestinosAntelHorarioReducido;
	private Double precioMinutoOtrasOperadoras;
	private Double precioMinutoNumerosAmigos;
	private Long rendimientoMinutosMensualOtrasOperadoras;
	private Double precioSms;
	private Double montoNavegacionCelular;
	private Double precioConsumoFueraBono;
	private Double topeFacturacionMensualTraficoDatos;
	private String destinosGratis;
	private Long minutosGratisMesCelularesAntel;
	private Long smsGratisMesCelularesAntel;
	private String cantidadCelularesAntelSmsGratis;
	private Long cantidadCelularesAntelMinutosGratis;
	private Long minutosGratisMesFijosAntel;
	private Long cantidadFijosAntelMinutosGratis;
	private Date fechaBaja;
	private String piePagina;
	private Boolean beneficioIncluidoEnLlamadas;
	private TipoPlanTO tipoPlan;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAbreviacion() {
		return abreviacion;
	}

	public void setAbreviacion(String abreviacion) {
		this.abreviacion = abreviacion;
	}

	public Double getConsumoMinimo() {
		return consumoMinimo;
	}

	public void setConsumoMinimo(Double consumoMinimo) {
		this.consumoMinimo = consumoMinimo;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public Double getPrecioMinutoDestinosAntelHorarioNormal() {
		return precioMinutoDestinosAntelHorarioNormal;
	}

	public void setPrecioMinutoDestinosAntelHorarioNormal(
			Double precioMinutoDestinosAntelHorarioNormal) {
		this.precioMinutoDestinosAntelHorarioNormal = precioMinutoDestinosAntelHorarioNormal;
	}

	public Double getPrecioMinutoDestinosAntelHorarioReducido() {
		return precioMinutoDestinosAntelHorarioReducido;
	}

	public void setPrecioMinutoDestinosAntelHorarioReducido(
			Double precioMinutoDestinosAntelHorarioReducido) {
		this.precioMinutoDestinosAntelHorarioReducido = precioMinutoDestinosAntelHorarioReducido;
	}

	public Long getRendimientoMinutosMensualDestinosAntelHorarioNormal() {
		return rendimientoMinutosMensualDestinosAntelHorarioNormal;
	}

	public void setRendimientoMinutosMensualDestinosAntelHorarioNormal(
			Long rendimientoMinutosMensualDestinosAntelHorarioNormal) {
		this.rendimientoMinutosMensualDestinosAntelHorarioNormal = rendimientoMinutosMensualDestinosAntelHorarioNormal;
	}

	public Long getRendimientoMinutosMensualDestinosAntelHorarioReducido() {
		return rendimientoMinutosMensualDestinosAntelHorarioReducido;
	}

	public void setRendimientoMinutosMensualDestinosAntelHorarioReducido(
			Long rendimientoMinutosMensualDestinosAntelHorarioReducido) {
		this.rendimientoMinutosMensualDestinosAntelHorarioReducido = rendimientoMinutosMensualDestinosAntelHorarioReducido;
	}

	public Double getPrecioMinutoOtrasOperadoras() {
		return precioMinutoOtrasOperadoras;
	}

	public void setPrecioMinutoOtrasOperadoras(
			Double precioMinutoOtrasOperadoras) {
		this.precioMinutoOtrasOperadoras = precioMinutoOtrasOperadoras;
	}

	public Double getPrecioMinutoNumerosAmigos() {
		return precioMinutoNumerosAmigos;
	}

	public void setPrecioMinutoNumerosAmigos(Double precioMinutoNumerosAmigos) {
		this.precioMinutoNumerosAmigos = precioMinutoNumerosAmigos;
	}

	public Long getRendimientoMinutosMensualOtrasOperadoras() {
		return rendimientoMinutosMensualOtrasOperadoras;
	}

	public void setRendimientoMinutosMensualOtrasOperadoras(
			Long rendimientoMinutosMensualOtrasOperadoras) {
		this.rendimientoMinutosMensualOtrasOperadoras = rendimientoMinutosMensualOtrasOperadoras;
	}

	public Double getPrecioSms() {
		return precioSms;
	}

	public void setPrecioSms(Double precioSms) {
		this.precioSms = precioSms;
	}

	public Double getMontoNavegacionCelular() {
		return montoNavegacionCelular;
	}

	public void setMontoNavegacionCelular(Double montoNavegacionCelular) {
		this.montoNavegacionCelular = montoNavegacionCelular;
	}

	public Double getPrecioConsumoFueraBono() {
		return precioConsumoFueraBono;
	}

	public void setPrecioConsumoFueraBono(Double precioConsumoFueraBono) {
		this.precioConsumoFueraBono = precioConsumoFueraBono;
	}

	public Double getTopeFacturacionMensualTraficoDatos() {
		return topeFacturacionMensualTraficoDatos;
	}

	public void setTopeFacturacionMensualTraficoDatos(
			Double topeFacturacionMensualTraficoDatos) {
		this.topeFacturacionMensualTraficoDatos = topeFacturacionMensualTraficoDatos;
	}

	public String getDestinosGratis() {
		return destinosGratis;
	}

	public void setDestinosGratis(String destinosGratis) {
		this.destinosGratis = destinosGratis;
	}

	public Long getMinutosGratisMesCelularesAntel() {
		return minutosGratisMesCelularesAntel;
	}

	public void setMinutosGratisMesCelularesAntel(
			Long minutosGratisMesCelularesAntel) {
		this.minutosGratisMesCelularesAntel = minutosGratisMesCelularesAntel;
	}

	public Long getSmsGratisMesCelularesAntel() {
		return smsGratisMesCelularesAntel;
	}

	public void setSmsGratisMesCelularesAntel(Long smsGratisMesCelularesAntel) {
		this.smsGratisMesCelularesAntel = smsGratisMesCelularesAntel;
	}

	public String getCantidadCelularesAntelSmsGratis() {
		return cantidadCelularesAntelSmsGratis;
	}

	public void setCantidadCelularesAntelSmsGratis(String cantidadCelularesAntelSmsGratis) {
		this.cantidadCelularesAntelSmsGratis = cantidadCelularesAntelSmsGratis;
	}

	public Long getCantidadCelularesAntelMinutosGratis() {
		return cantidadCelularesAntelMinutosGratis;
	}

	public void setCantidadCelularesAntelMinutosGratis(
			Long cantidadCelularesAntelMinutosGratis) {
		this.cantidadCelularesAntelMinutosGratis = cantidadCelularesAntelMinutosGratis;
	}

	public Long getMinutosGratisMesFijosAntel() {
		return minutosGratisMesFijosAntel;
	}

	public void setMinutosGratisMesFijosAntel(Long minutosGratisMesFijosAntel) {
		this.minutosGratisMesFijosAntel = minutosGratisMesFijosAntel;
	}

	public Long getCantidadFijosAntelMinutosGratis() {
		return cantidadFijosAntelMinutosGratis;
	}

	public void setCantidadFijosAntelMinutosGratis(
			Long cantidadFijosAntelMinutosGratis) {
		this.cantidadFijosAntelMinutosGratis = cantidadFijosAntelMinutosGratis;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getPiePagina() {
		return piePagina;
	}

	public void setPiePagina(String piePagina) {
		this.piePagina = piePagina;
	}
	
	public Boolean getBeneficioIncluidoEnLlamadas() {
		return beneficioIncluidoEnLlamadas;
	}

	public void setBeneficioIncluidoEnLlamadas(Boolean beneficioIncluidoEnLlamadas) {
		this.beneficioIncluidoEnLlamadas = beneficioIncluidoEnLlamadas;
	}

	public TipoPlanTO getTipoPlan() {
		return tipoPlan;
	}

	public void setTipoPlan(TipoPlanTO tipoPlan) {
		this.tipoPlan = tipoPlan;
	}
}