package uy.com.amensg.logistica.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "plan")
public class Plan extends BaseEntity {

	private static final long serialVersionUID = -9210976864242844583L;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "abreviacion")
	private String abreviacion;

	@Column(name = "consumo_minimo")
	private Double consumoMinimo;

	@Column(name = "duracion")
	private String duracion;

	@Column(name = "precio_minuto_destinos_antel_horario_normal")
	private Double precioMinutoDestinosAntelHorarioNormal;

	@Column(name = "precio_minuto_destinos_antel_horario_reducido")
	private Double precioMinutoDestinosAntelHorarioReducido;

	@Column(name = "rendimiento_minutos_mensual_destinos_antel_horario_normal")
	private Long rendimientoMinutosMensualDestinosAntelHorarioNormal;

	@Column(name = "rendimiento_minutos_mensual_destinos_antel_horario_reducido")
	private Long rendimientoMinutosMensualDestinosAntelHorarioReducido;

	@Column(name = "precio_minuto_otras_operadoras")
	private Double precioMinutoOtrasOperadoras;
	
	@Column(name = "precio_minuto_numeros_amigos")
	private Double precioMinutoNumerosAmigos;
	
	@Column(name = "rendimiento_minutos_mensual_otras_operadoras")
	private Long rendimientoMinutosMensualOtrasOperadoras;

	@Column(name = "precio_sms")
	private Double precioSms;
	
	@Column(name = "monto_navegacion_celular")
	private Double montoNavegacionCelular;
	
	@Column(name = "precio_consumo_fuera_bono")
	private Double precioConsumoFueraBono;
	
	@Column(name = "tope_facturacion_mensual_trafico_datos")
	private Double topeFacturacionMensualTraficoDatos;
	
	@Column(name = "destinos_gratis")
	private String destinosGratis;
	
	@Column(name = "minutos_gratis_mes_celulares_antel")
	private Long minutosGratisMesCelularesAntel;
	
	@Column(name = "sms_gratis_mes_celulares_antel")
	private Long smsGratisMesCelularesAntel;
	
	@Column(name = "cantidad_celulares_antel_sms_gratis")
	private String cantidadCelularesAntelSmsGratis;
	
	@Column(name = "cantidad_celulares_antel_minutos_gratis")
	private Long cantidadCelularesAntelMinutosGratis;
	
	@Column(name = "minutos_gratis_mes_fijos_antel")
	private Long minutosGratisMesFijosAntel;
	
	@Column(name = "cantidad_fijos_antel_minutos_gratis")
	private Long cantidadFijosAntelMinutosGratis;

	@Column(name = "fecha_baja")
	private Date fechaBaja;

	@Column(name = "pie_pagina")
	private String piePagina;
	
	@Column(name = "beneficio_incluido_en_llamadas")
	private Boolean beneficioIncluidoEnLlamadas;
	
	@ManyToOne(optional = true, fetch=FetchType.EAGER)
	@JoinColumn(name = "tipo_plan_id", nullable = true)
	private TipoPlan tipoPlan;
	
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

	public void setPrecioMinutoOtrasOperadoras(Double precioMinutoOtrasOperadoras) {
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

	public TipoPlan getTipoPlan() {
		return tipoPlan;
	}

	public void setTipoPlan(TipoPlan tipoPlan) {
		this.tipoPlan = tipoPlan;
	}
}