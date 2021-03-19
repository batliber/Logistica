package uy.com.amensg.logistica.bean;

import java.util.Collection;

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
import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnual;
import uy.com.amensg.logistica.util.Configuration;

@Stateless
public class FinanciacionBean implements IFinanciacionBean {

	@PersistenceContext(unitName = "uy.com.amensg.logistica.persistenceUnit")
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
//			Se decide utilizar el análisis de riesgo de cualquier empresa.
//			RiesgoCrediticio riesgoCrediticio = iRiesgoCrediticioBean.getLastByEmpresaDocumento(empresa, documento);
			RiesgoCrediticio riesgoCrediticio = iRiesgoCrediticioBean.getLastByDocumento(documento);
			
			if (riesgoCrediticio != null
				&& riesgoCrediticio.getCalificacionRiesgoCrediticioAntel() != null
				&& riesgoCrediticio.getCalificacionRiesgoCrediticioBCU() != null) {
				result = new DatosElegibilidadFinanciacion();
				
				if (riesgoCrediticio.getCalificacionRiesgoCrediticioAntel().getId().equals(
					Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.OK"))
				)) {
					// Sin deuda ANTEL
					
					if (riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDATOS"))
						) ||
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.1C"))
						) ||
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.2A"))
						)
					) {
						// No realizar clearing.
						result.setElegibilidad(Long.valueOf(2));
						result.setMensaje("OK");
					} else if (
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.2B"))
						)
					) {
						// Realizar clearing.
						result.setElegibilidad(Long.valueOf(1));
						result.setMensaje("Atención: debe realizar comprobación de Clearing.");
					} else {
						// Rechazar.
						result.setElegibilidad(Long.valueOf(0));
						result.setMensaje("Rechazado");
					}
				} else {
					// Con deuda ANTEL.
					
					if (riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDATOS"))
						) ||
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.1C"))
						) ||
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.2A"))
						) ||
						riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							Long.parseLong(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.2B"))
						)
					) {
						// Realizar clearing.
						result.setElegibilidad(Long.valueOf(1));
						result.setMensaje("Atención: debe realizar comprobación de Clearing.");
					} else {
						// Rechazar.
						result.setElegibilidad(Long.valueOf(0));
						result.setMensaje("Rechazado");
					}
				}
			} else {
				result = new DatosElegibilidadFinanciacion();
				
				// Rechazar.
				result.setElegibilidad(Long.valueOf(-1));
				result.setMensaje("No hay datos de análisis de riesgo.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
		
	public DatosFinanciacion calcularFinanciacion(
		Moneda moneda, 
		TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual,
		Double monto, 
		Long cuotas
	) {
		DatosFinanciacion result = new DatosFinanciacion();
		
		Long diasPorMes = Long.valueOf(30);
		Long diasPorAno = Long.valueOf(360);
		
		Double iva = Double.valueOf(Configuration.getInstance().getProperty("impuestos.IVA"));
		Double ui = iUnidadIndexadaBean.getVigente().getValor();
		Boolean primeraCuotaAlInicio = Boolean.valueOf(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.primeraCuotaAlInicio"));
		Boolean aplicarGastosConcesion = Boolean.valueOf(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.aplicarGastosConcesion"));
		Boolean aplicarSeguro = Boolean.valueOf(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.aplicarSeguro"));
		Double gastosConcesion = Long.parseLong(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.gastosConcesion")) * ui;
		Double gastosConcesionIVA = gastosConcesion * iva;
		Double gastosAdministrativosCuota = Long.parseLong(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.gastosAdministrativosCuota")) * ui;
		Long maximoCuotasGastosAdministrativos = 
			Long.parseLong(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.maximoCuotasGastosAdministrativos"));
		Double gastosAdministrativosCuotaIVA = gastosAdministrativosCuota * iva;
		Double gastosAdministrativosIVA = Math.min(cuotas, maximoCuotasGastosAdministrativos) * gastosAdministrativosCuotaIVA;
		
		Double porcentajeSeguro = 
			Double.valueOf(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.porcentajeSeguro"));
		
		Collection<TasaInteresEfectivaAnual> tasaInteresEfectivaAnuales = 
			iTasaInteresEfectivaAnualBean.listVigentesByTipo(tipoTasaInteresEfectivaAnual);
		
		Double teaFinanciacion = Double.valueOf(0);
		for (TasaInteresEfectivaAnual tasaInteresEfectivaAnual : tasaInteresEfectivaAnuales) {
			Long cuotasDesde = 
				tasaInteresEfectivaAnual.getCuotasDesde() != null ? 
					tasaInteresEfectivaAnual.getCuotasDesde() : 0;
			Long cuotasHasta = 
				tasaInteresEfectivaAnual.getCuotasHasta() != null ? 
					tasaInteresEfectivaAnual.getCuotasHasta() : Long.MAX_VALUE;
			Double montoDesde = 
				tasaInteresEfectivaAnual.getMontoDesde() != null ? 
					tasaInteresEfectivaAnual.getMontoDesde() * ui : 0;
			Double montoHasta = 
				tasaInteresEfectivaAnual.getMontoHasta() != null ? 
					tasaInteresEfectivaAnual.getMontoHasta() * ui : Double.MAX_VALUE;
			Double valor = tasaInteresEfectivaAnual.getValor();
			
			if (cuotas > cuotasDesde && cuotas <= cuotasHasta) {
				if (monto > montoDesde && monto <= montoHasta) {
					teaFinanciacion = valor;
					break;
				}
			}
		}

//		Double teaFinanciacionIVA = teaFinanciacion * iva;
		Double temFinanciacion = Math.pow(1 + teaFinanciacion, Double.valueOf(diasPorMes) / diasPorAno) - 1;
		Double temFinanciacionIVA = temFinanciacion * iva;
		Double tedFinanciacionIVA = Math.pow(1 + temFinanciacionIVA, Double.valueOf(1) / diasPorMes) - 1;
		Double valorCuotaA = this.pago(temFinanciacionIVA, Double.valueOf(cuotas), monto, primeraCuotaAlInicio);
		
		Double minimoMensualSeguro = Long.parseLong(Configuration.getInstance().getProperty("financiacion.creditoDeLaCasa.minimoMensualSeguro")) * ui;
		
		Double capitalAcumulado = monto;
		Double interesesIVA = Double.valueOf(0);
		Double interesesAcumuladoIVA = Double.valueOf(0);
		for (int i=1; i<=cuotas; i++) {
			for (int j=1; j<=diasPorMes; j++) {
				capitalAcumulado += interesesIVA;
				interesesIVA = capitalAcumulado * tedFinanciacionIVA;
				interesesAcumuladoIVA += interesesIVA;
			}
			
			capitalAcumulado -= valorCuotaA;
		}
		
		Double cuotaCapitalInteresesAcumuladoIVA = monto + interesesAcumuladoIVA;
		
		Double seguro = Double.valueOf(0);
		if (aplicarSeguro) {
			seguro += porcentajeSeguro * (cuotaCapitalInteresesAcumuladoIVA + gastosAdministrativosIVA);
		}
		
		if (aplicarGastosConcesion) {
			seguro += porcentajeSeguro * gastosConcesionIVA;
		}
		
		Double seguroMensual = Double.valueOf(0);
		Double seguroAcumulado = Double.valueOf(0);
		if (aplicarSeguro) {
			seguroMensual = Math.max(minimoMensualSeguro, seguro);
			seguroAcumulado = seguroMensual * cuotas;
		}
		
		Double valorCuotaB = 
			(cuotaCapitalInteresesAcumuladoIVA + gastosAdministrativosIVA + gastosConcesionIVA + seguroAcumulado) / cuotas;
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