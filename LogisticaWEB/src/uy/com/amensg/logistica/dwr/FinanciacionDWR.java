package uy.com.amensg.logistica.dwr;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.FinanciacionBean;
import uy.com.amensg.logistica.bean.IFinanciacionBean;
import uy.com.amensg.logistica.entities.DatosElegibilidadFinanciacion;
import uy.com.amensg.logistica.entities.DatosElegibilidadFinanciacionTO;
import uy.com.amensg.logistica.entities.DatosFinanciacion;
import uy.com.amensg.logistica.entities.DatosFinanciacionTO;
import uy.com.amensg.logistica.entities.EmpresaTO;
import uy.com.amensg.logistica.entities.FormaPagoTO;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.MonedaTO;
import uy.com.amensg.logistica.util.Configuration;

@RemoteProxy
public class FinanciacionDWR {

	private IFinanciacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = FinanciacionBean.class.getSimpleName();
		String remoteInterfaceName = IFinanciacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IFinanciacionBean) context.lookup(lookupName);
	}
	
	public DatosElegibilidadFinanciacionTO analizarElegibilidadFinanaciacion(EmpresaTO empresa, String documento) {
		DatosElegibilidadFinanciacionTO result = null;
		
		try {
			IFinanciacionBean iFinanciacionBean = lookupBean();
			
			DatosElegibilidadFinanciacion datosElegibilidadFinanciacion = 
				iFinanciacionBean.analizarElegibilidadFinanaciacion(
					EmpresaDWR.transform(empresa),
					documento
				);
			
			if (datosElegibilidadFinanciacion != null) {
				result = transform(datosElegibilidadFinanciacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public DatosFinanciacionTO calcularFinanciacion(MonedaTO monedaTO, Double monto, Long cuotas) {
		DatosFinanciacionTO result = null;
		
		try {
			IFinanciacionBean iFinanciacionBean = lookupBean();
			
			Moneda moneda = new Moneda();
			moneda.setId(monedaTO.getId());
			
			result = 
				transform(
					iFinanciacionBean.calcularFinanciacion(
						moneda,
						monto,
						cuotas
					)
				);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String validarFinanciacion(MonedaTO monedaTO, FormaPagoTO formaPago, Double monto) {
		String result = null;
		
		try {
			Long formaPagoNuestroCreditoId = 
				new Long(Configuration.getInstance().getProperty("formaPago.NuestroCredito"));
			
			if (formaPago.getId().equals(formaPagoNuestroCreditoId)) {
				Long monedaPesosUruguayosId =
					new Long(Configuration.getInstance().getProperty("moneda.PesosUruguayos"));
				Long monedaDolaresAmericanosId =
					new Long(Configuration.getInstance().getProperty("moneda.DolaresAmericanos"));
				
				if (monedaTO.getId().equals(monedaPesosUruguayosId)) {
					Double montoMinimoPesosUruguayos = 
						new Double(
							Configuration.getInstance().getProperty(
								"financiacion.creditoDeLaCasa.montoMinimo.PesosUruguayos"
							)
						);
					
					if (monto < montoMinimoPesosUruguayos) {
						result = "No se puede financiar con Nuestro crédito por montos inferiores a $U 2500.";
					}
				} else if (monedaTO.getId().equals(monedaDolaresAmericanosId)) {
					Double montoMinimoDolaresAmericanos =
						new Double(
							Configuration.getInstance().getProperty(
								"financiacion.creditoDeLaCasa.montoMinimo.DolaresAmericanos"
							)
						);
					
					if (monto < montoMinimoDolaresAmericanos) {
						result = "No se puede financiar con Nuestro crédito por montos inferiores a U$S 100.";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public DatosFinanciacionTO transform(DatosFinanciacion datosFinanciacion) {
		DatosFinanciacionTO result = new DatosFinanciacionTO();
		
		result.setGastosAdministrativos(datosFinanciacion.getGastosAdministrativos());
		result.setGastosAdministrativosTotales(datosFinanciacion.getGastosAdministrativosTotales());
		result.setGastosConcesion(datosFinanciacion.getGastosConcesion());
		result.setIntereses(datosFinanciacion.getIntereses());
		result.setMontoCuota(datosFinanciacion.getMontoCuota());
		result.setMontoTotalFinanciado(datosFinanciacion.getMontoTotalFinanciado());
		result.setValorTasaInteresEfectivaAnual(datosFinanciacion.getValorTasaInteresEfectivaAnual());
		result.setValorUnidadIndexada(datosFinanciacion.getValorUnidadIndexada());
		
		return result;
	}

	public DatosElegibilidadFinanciacionTO transform(DatosElegibilidadFinanciacion datosElegibilidadFinanciacion) {
		DatosElegibilidadFinanciacionTO result = new DatosElegibilidadFinanciacionTO();
		
		result.setElegibilidad(datosElegibilidadFinanciacion.getElegibilidad());
		result.setMensaje(datosElegibilidadFinanciacion.getMensaje());
		
		return result;
	}
}