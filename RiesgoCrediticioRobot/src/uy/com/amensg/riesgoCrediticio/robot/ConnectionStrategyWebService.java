package uy.com.amensg.riesgoCrediticio.robot;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import uy.com.amensg.riesgoCrediticio.robot.util.Configuration;
import uy.com.amensg.riesgoCrediticio.webservices.RiesgoCrediticioWebService;

public class ConnectionStrategyWebService implements IConnectionStrategy {

	public String getSiguienteDocumentoParaControlar() {
		String result = "";
		
		try {
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("RiesgoCrediticioWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "RiesgoCrediticioWebServiceService")
			);
			
			RiesgoCrediticioWebService webService = service.getPort(RiesgoCrediticioWebService.class);
			
			result = webService.getSiguienteDocumentoParaControlar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void actualizarDatosRiesgoCrediticioACM(
		String empresaId,
		String documento,
		String fechaCelular,
		String deudaCelular,
		String riesgoCrediticioCelular,
		String contratosCelular,
		String contratosSolaFirmaCelular,
		String contratosGarantiaCelular,
		String saldoAyudaEconomicaCelular,
		String numeroClienteFijo,
		String nombreClienteFijo,
		String estadoDeudaClienteFijo,
		String numeroClienteMovil) {
		try {
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("RiesgoCrediticioWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "RiesgoCrediticioWebServiceService")
			);
			
			RiesgoCrediticioWebService webService = service.getPort(RiesgoCrediticioWebService.class);
			
			webService.actualizarDatosRiesgoCrediticioACM(
				empresaId,
				documento,
				fechaCelular,
				deudaCelular,
				riesgoCrediticioCelular,
				contratosCelular,
				contratosSolaFirmaCelular,
				contratosGarantiaCelular,
				saldoAyudaEconomicaCelular,
				numeroClienteFijo,
				nombreClienteFijo,
				estadoDeudaClienteFijo,
				numeroClienteMovil
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosRiesgoCrediticioBCU(
		String empresaId,
		String documento,
		String periodo,
		String nombreCompleto,
		String actividad,
		String vigente,
		String vigenteNoAutoliquidable,
		String garantiasComputables,
		String garantiasNoComputables,
		String castigadoPorAtraso,
		String castigadoPorQuitasYDesistimiento,
		String previsionesTotales,
		String contingencias,
		String otorgantesGarantias
	) {
		try {
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("RiesgoCrediticioWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "RiesgoCrediticioWebServiceService")
			);
			
			RiesgoCrediticioWebService webService = service.getPort(RiesgoCrediticioWebService.class);
			
			webService.actualizarDatosRiesgoCrediticioBCU(
				empresaId,
				documento,
				periodo,
				nombreCompleto,
				actividad,
				vigente,
				vigenteNoAutoliquidable,
				garantiasComputables,
				garantiasNoComputables,
				castigadoPorAtraso,
				castigadoPorQuitasYDesistimiento,
				previsionesTotales,
				contingencias,
				otorgantesGarantias
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
		String empresaId,
		String documento,
		String institucionFinanciera,
		String calificacion,
		String vigente,
		String vigenteNoAutoliquidable,
		String previsionesTotales,
		String contingencias
	) {
		try {
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("RiesgoCrediticioWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "RiesgoCrediticioWebServiceService")
			);
			
			RiesgoCrediticioWebService webService = service.getPort(RiesgoCrediticioWebService.class);
			
			webService.actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
				empresaId,
				documento,
				institucionFinanciera,
				calificacion,
				vigente,
				vigenteNoAutoliquidable,
				previsionesTotales,
				contingencias
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}