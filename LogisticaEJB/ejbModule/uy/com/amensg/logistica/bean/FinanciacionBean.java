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
					new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioAntel.OK"))
				)) {
					// Sin deuda ANTEL
					
					if (riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDATOS"))
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
						result.setMensaje("Atención: debe realizar comprobación de Clearing.");
					} else {
						// Rechazar.
						result.setElegibilidad(new Long(0));
						result.setMensaje("Rechazado");
					}
				} else {
					// Con deuda ANTEL.
					
					if (riesgoCrediticio.getCalificacionRiesgoCrediticioBCU().getId().equals(
							new Long(Configuration.getInstance().getProperty("calificacionRiesgoCrediticioBCU.SINDATOS"))
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
						result.setMensaje("Atención: debe realizar comprobación de Clearing.");
					} else {
						// Rechazar.
						result.setElegibilidad(new Long(0));
						result.setMensaje("Rechazado");
					}
				}
			} else {
				result = new DatosElegibilidadFinanciacion();
				
				// Rechazar.
				result.setElegibilidad(new Long(-1));
				result.setMensaje("No hay datos de análisis de riesgo.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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