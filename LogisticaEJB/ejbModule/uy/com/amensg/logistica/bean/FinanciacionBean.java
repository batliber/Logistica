package uy.com.amensg.logistica.bean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.com.amensg.logistica.entities.DatosElegibilidadFinanciacion;
import uy.com.amensg.logistica.entities.DatosFinanciacion;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.RiesgoCrediticio;
import uy.com.amensg.logistica.entities.TasaInteresEfectivaAnual;
import uy.com.amensg.logistica.util.Configuration;

@Stateless
public class FinanciacionBean implements IFinanciacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnitLogisticaXA")
	private EntityManager entityManager;
	
	@EJB
	private IUnidadIndexadaBean iUnidadIndexadaBean;
	
	@EJB
	private ITasaInteresEfectivaAnualBean iTasaInteresEfectivaAnualBean;
	
	@EJB
	private IRiesgoCrediticioBean iRiesgoCrediticioBean;
	
	public DatosElegibilidadFinanciacion analizarElegibilidadFinanaciacion(Empresa empresa, String documento) {
		DatosElegibilidadFinanciacion result = null;
		
		try {
			RiesgoCrediticio riesgoCrediticio = iRiesgoCrediticioBean.getLastByEmpresaDocumento(empresa, documento);
			
			if (riesgoCrediticio != null
				&& riesgoCrediticio.getCalificacionRiesgoCrediticioAntel() != null
				&& riesgoCrediticio.getCalificacionRiesgoCrediticioBCU() != null) {
				result = new DatosElegibilidadFinanciacion();
				
				if (riesgoCrediticio.getCalificacionRiesgoCrediticioAntel().getId().equals(
					new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.OK"))
				)) {
					// Sin deuda ANTEL
					
					if (riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDETERMINAR"))
						) ||
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.1C"))
						) ||
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.2A"))
						)
					) {
						// No realizar clearing.
						result.setElegibilidad(new Long(2));
						result.setMensaje("OK");
					} else if (
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.2B"))
						)
					) {
						// Realizar clearing.
						result.setElegibilidad(new Long(1));
						result.setMensaje("Atenci�n: debe realizar comprobaci�n de Clearing.");
					} else {
						// Rechazar.
						result.setElegibilidad(new Long(0));
						result.setMensaje("Rechazado");
					}
				} else {
					// Con deuda ANTEL.
					
					if (riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDETERMINAR"))
						) ||
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.1C"))
						) ||
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.2A"))
						) ||
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.2B"))
						)
					) {
						// Realizar clearing.
						result.setElegibilidad(new Long(1));
						result.setMensaje("Atenci�n: debe realizar comprobaci�n de Clearing.");
					} else {
						// Rechazar.
						result.setElegibilidad(new Long(0));
						result.setMensaje("Rechazado");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public DatosFinanciacion calcularFinanciacionOriginal(Moneda moneda, Double monto, Long cuotas) {
		DatosFinanciacion result = new DatosFinanciacion();
		
		Long diasPorMes = new Long(30);
		Long diasPorAno = new Long(360);
		
		Double iva = new Double(Configuration.getInstance().getProperty("impuestos.IVA"));
		Double ui = iUnidadIndexadaBean.getVigente().getValor();
		Boolean primeraCuotaAlInicio = new Boolean(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.primeraCuotaAlInicio"));
		Boolean aplicarGastosConcesion = new Boolean(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.aplicarGastosConcesion"));
		Boolean aplicarSeguro = new Boolean(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.aplicarSeguro"));
		Double maximoConGastosConcesion = new Long(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.maximoConGastosConcesion")) * ui;
		Double maximoSinGastosConcesion = new Long(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.maximoSinGastosConcesion")) * ui;
		Double maximoGastosAdministrativos = new Long(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.maximoGastosAdministrativos")) * ui;
		Double porcentajeSeguro = new Double(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.porcentajeSeguro"));
		
		Double teaFinanciacion = new Double(0);
		for (TasaInteresEfectivaAnual tasaInteresEfectivaAnual : iTasaInteresEfectivaAnualBean.listVigentes()) {
			Long cuotasDesde = tasaInteresEfectivaAnual.getCuotasDesde() != null ? tasaInteresEfectivaAnual.getCuotasDesde() : 0;
			Long cuotasHasta = tasaInteresEfectivaAnual.getCuotasHasta() != null ? tasaInteresEfectivaAnual.getCuotasHasta() : Long.MAX_VALUE;
			Double montoDesde = tasaInteresEfectivaAnual.getMontoDesde() != null ? tasaInteresEfectivaAnual.getMontoDesde() * ui : 0;
			Double montoHasta = tasaInteresEfectivaAnual.getMontoHasta() != null ? tasaInteresEfectivaAnual.getMontoHasta() * ui : Double.MAX_VALUE;
			Double valor = tasaInteresEfectivaAnual.getValor();
			
			if (cuotas > cuotasDesde && cuotas <= cuotasHasta) {
				if (monto > montoDesde && monto <= montoHasta) {
					teaFinanciacion = valor;
					break;
				}
			}
		}

//		Double teaFinanciacionIVA = teaFinanciacion * iva;
		Double temFinanciacion = Math.pow(1 + teaFinanciacion, new Double(diasPorMes)/diasPorAno) - 1;
		Double temFinanciacionIVA = temFinanciacion * iva;
		Double tedFinanciacionIVA = Math.pow(1 + temFinanciacionIVA, new Double(1)/diasPorMes) - 1;
		Double valorCuotaA = this.pago(temFinanciacionIVA, new Double(cuotas), monto, primeraCuotaAlInicio);
		
		Double minimoMensualSeguro = new Long(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.minimoMensualSeguro")) * ui;
		
		Double decisorGastosAdministrativosGastosConcesion = 
			new Long(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.decisorGastosAdministrativosGastosConcesion")) * cuotas * ui;
		
		Double valorGAV = 
			aplicarGastosConcesion ?
				iva * Math.min(decisorGastosAdministrativosGastosConcesion, maximoConGastosConcesion) :
				iva * Math.min(decisorGastosAdministrativosGastosConcesion, maximoSinGastosConcesion);
		
		
//		Long maximoMeses = new Long(18);
		
		Double capitalAcumulado = monto;
		Double interesesIVA = new Double(0);
		Double interesesAcumuladoIVA = new Double(0);
		Double gastosAdministrativosAcumuladoIVA = new Double(0);
		Double gastosConcesionAcumuladoIVA = new Double(0);
		for (int i=1; i<=cuotas; i++) {
			Double gastosAdministrativosIVA = valorGAV / cuotas / diasPorMes;
			
			Double gastosConcesionIVA = new Double(0);
			if (aplicarGastosConcesion) {
				gastosConcesionIVA = (((new Double(40) * ui) / cuotas) * iva) / diasPorMes;
			}
			
			for (int j=1; j<=diasPorMes; j++) {
				capitalAcumulado += interesesIVA;
				interesesIVA = capitalAcumulado * tedFinanciacionIVA;
				interesesAcumuladoIVA += interesesIVA;
				gastosAdministrativosAcumuladoIVA += gastosAdministrativosIVA;
				gastosConcesionAcumuladoIVA += gastosConcesionIVA;
			}
			
			capitalAcumulado -= valorCuotaA;
		}
		
		Double cuotaCapitalInteresesAcumuladoIVA = monto + interesesAcumuladoIVA;
		
		Double seguro = new Double(0);
		if (aplicarSeguro) {
			seguro += porcentajeSeguro * (cuotaCapitalInteresesAcumuladoIVA + gastosAdministrativosAcumuladoIVA);
		}
		
		if (aplicarGastosConcesion) {
			seguro += porcentajeSeguro * gastosConcesionAcumuladoIVA;
		}
		
		Double seguroMensual = new Double(0);
		Double seguroAcumulado = new Double(0);
		if (aplicarSeguro) {
			seguroMensual = Math.max(minimoMensualSeguro, seguro);
			seguroAcumulado = seguroMensual * cuotas;
		}
		
		System.err.println(maximoGastosAdministrativos + " | " + gastosAdministrativosAcumuladoIVA);
		
		Double gastosAdministrativosYConcesionAcumuladoIVA =
			Math.min(
				maximoGastosAdministrativos,
				gastosAdministrativosAcumuladoIVA + gastosConcesionAcumuladoIVA
			);
		
		Double valorCuotaB = 
			(cuotaCapitalInteresesAcumuladoIVA + gastosAdministrativosYConcesionAcumuladoIVA + seguroAcumulado) / cuotas;
		Double montoTotalFinanciado =
			cuotaCapitalInteresesAcumuladoIVA + gastosAdministrativosYConcesionAcumuladoIVA + seguroAcumulado;

		result.setGastosAdministrativos(gastosAdministrativosYConcesionAcumuladoIVA);
//		result.setGastosConcesion(new Double(0));
		result.setGastosConcesion(gastosConcesionAcumuladoIVA);
		result.setIntereses(interesesAcumuladoIVA);
//		result.setMontoCuota(valorCuotaA);
		result.setMontoCuota(valorCuotaB);
		result.setMontoTotalFinanciado(montoTotalFinanciado);
		result.setValorTasaInteresEfectivaAnual(teaFinanciacion);
		result.setValorUnidadIndexada(ui);
		
		return result;
	}
	
	public DatosFinanciacion calcularFinanciacion(Moneda moneda, Double monto, Long cuotas) {
		DatosFinanciacion result = new DatosFinanciacion();
		
		Long diasPorMes = new Long(30);
		Long diasPorAno = new Long(360);
		
		Double iva = new Double(Configuration.getInstance().getProperty("impuestos.IVA"));
		Double ui = iUnidadIndexadaBean.getVigente().getValor();
		Boolean primeraCuotaAlInicio = new Boolean(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.primeraCuotaAlInicio"));
		Boolean aplicarGastosConcesion = new Boolean(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.aplicarGastosConcesion"));
		Boolean aplicarSeguro = new Boolean(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.aplicarSeguro"));
		Double gastosConcesion = new Long(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.gastosConcesion")) * ui;
		Double gastosConcesionIVA = gastosConcesion * iva;
		Double gastosAdministrativosCuota = new Long(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.gastosAdministrativosCuota")) * ui;
		Long maximoCuotasGastosAdministrativos = 
			new Long(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.maximoCuotasGastosAdministrativos"));
		Double gastosAdministrativosCuotaIVA = gastosAdministrativosCuota * iva;
		Double gastosAdministrativosIVA = Math.min(cuotas, maximoCuotasGastosAdministrativos) * gastosAdministrativosCuotaIVA;
		
		Double porcentajeSeguro = new Double(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.porcentajeSeguro"));
		
		Double teaFinanciacion = new Double(0);
		for (TasaInteresEfectivaAnual tasaInteresEfectivaAnual : iTasaInteresEfectivaAnualBean.listVigentes()) {
			Long cuotasDesde = tasaInteresEfectivaAnual.getCuotasDesde() != null ? tasaInteresEfectivaAnual.getCuotasDesde() : 0;
			Long cuotasHasta = tasaInteresEfectivaAnual.getCuotasHasta() != null ? tasaInteresEfectivaAnual.getCuotasHasta() : Long.MAX_VALUE;
			Double montoDesde = tasaInteresEfectivaAnual.getMontoDesde() != null ? tasaInteresEfectivaAnual.getMontoDesde() * ui : 0;
			Double montoHasta = tasaInteresEfectivaAnual.getMontoHasta() != null ? tasaInteresEfectivaAnual.getMontoHasta() * ui : Double.MAX_VALUE;
			Double valor = tasaInteresEfectivaAnual.getValor();
			
			if (cuotas > cuotasDesde && cuotas <= cuotasHasta) {
				if (monto > montoDesde && monto <= montoHasta) {
					teaFinanciacion = valor;
					break;
				}
			}
		}

//		Double teaFinanciacionIVA = teaFinanciacion * iva;
		Double temFinanciacion = Math.pow(1 + teaFinanciacion, new Double(diasPorMes) / diasPorAno) - 1;
		Double temFinanciacionIVA = temFinanciacion * iva;
		Double tedFinanciacionIVA = Math.pow(1 + temFinanciacionIVA, new Double(1) / diasPorMes) - 1;
		Double valorCuotaA = this.pago(temFinanciacionIVA, new Double(cuotas), monto, primeraCuotaAlInicio);
		
		Double minimoMensualSeguro = new Long(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.minimoMensualSeguro")) * ui;
		
		Double capitalAcumulado = monto;
		Double interesesIVA = new Double(0);
		Double interesesAcumuladoIVA = new Double(0);
		for (int i=1; i<=cuotas; i++) {
			for (int j=1; j<=diasPorMes; j++) {
				capitalAcumulado += interesesIVA;
				interesesIVA = capitalAcumulado * tedFinanciacionIVA;
				interesesAcumuladoIVA += interesesIVA;
			}
			
			capitalAcumulado -= valorCuotaA;
		}
		
		Double cuotaCapitalInteresesAcumuladoIVA = monto + interesesAcumuladoIVA;
		
		Double seguro = new Double(0);
		if (aplicarSeguro) {
			seguro += porcentajeSeguro * (cuotaCapitalInteresesAcumuladoIVA + gastosAdministrativosIVA);
		}
		
		if (aplicarGastosConcesion) {
			seguro += porcentajeSeguro * gastosConcesionIVA;
		}
		
		Double seguroMensual = new Double(0);
		Double seguroAcumulado = new Double(0);
		if (aplicarSeguro) {
			seguroMensual = Math.max(minimoMensualSeguro, seguro);
			seguroAcumulado = seguroMensual * cuotas;
		}
		
		Double valorCuotaB = 
			(cuotaCapitalInteresesAcumuladoIVA + gastosAdministrativosIVA + gastosConcesionIVA+ seguroAcumulado) / cuotas;
		Double montoTotalFinanciado =
			cuotaCapitalInteresesAcumuladoIVA + gastosAdministrativosIVA + gastosConcesionIVA + seguroAcumulado;

		result.setGastosAdministrativos(gastosAdministrativosIVA);
		result.setGastosConcesion(gastosConcesionIVA);
		result.setGastosAdministrativosTotales(gastosAdministrativosIVA + gastosConcesionIVA);
		result.setIntereses(interesesAcumuladoIVA);
//		result.setMontoCuota(valorCuotaA);
		result.setMontoCuota(valorCuotaB);
		result.setMontoTotalFinanciado(montoTotalFinanciado);
		result.setValorTasaInteresEfectivaAnual(teaFinanciacion);
		result.setValorUnidadIndexada(ui);
		
		return result;
	}
	
	private Double pago(Double tasa, Double pagos, Double valorActual, Boolean primerPagoAlInicio) {
		Double result = (valorActual * tasa) / (1 - Math.pow(1 + tasa, -1 * pagos));
		
		return result;
	}
}