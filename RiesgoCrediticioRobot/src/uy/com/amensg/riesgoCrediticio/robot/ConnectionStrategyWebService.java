package uy.com.amensg.riesgoCrediticio.robot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import uy.com.amensg.riesgoCrediticio.webservices.RiesgoCrediticioWebService;

public class ConnectionStrategyWebService implements IConnectionStrategy {

	public String getSiguienteDocumentoParaControlar(String wsdlFileName) {
		String result = "";
		
		try {
			Service service = Service.create(
				getClass().getClassLoader().getResource(wsdlFileName),
				new QName("http://soap.webservices.web.logistica.amensg.com.uy/", "RiesgoCrediticioWebServiceService")
			);
			
			RiesgoCrediticioWebService port = service.getPort(RiesgoCrediticioWebService.class);
			
			preparePort(port);
			
			result = port.getSiguienteDocumentoParaControlar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String getSiguienteDocumentoParaControlarRiesgoOnLine(String wsdlFileName) {
		String result = "";
		
		try {
			Service service = Service.create(
				getClass().getClassLoader().getResource(wsdlFileName),
				new QName("http://soap.webservices.web.logistica.amensg.com.uy/", "RiesgoCrediticioWebServiceService")
			);
			
			RiesgoCrediticioWebService port = service.getPort(RiesgoCrediticioWebService.class);
			
			preparePort(port);
			
			result = port.getSiguienteDocumentoParaControlarRiesgoOnLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void actualizarDatosRiesgoCrediticioACM(
		String wsdlFileName,
		String riesgoCrediticioId,
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
				getClass().getClassLoader().getResource(wsdlFileName),
				new QName("http://soap.webservices.web.logistica.amensg.com.uy/", "RiesgoCrediticioWebServiceService")
			);
			
			RiesgoCrediticioWebService port = service.getPort(RiesgoCrediticioWebService.class);
			
			preparePort(port);
			
			port.actualizarDatosRiesgoCrediticioACM(
				riesgoCrediticioId,
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
		String wsdlFileName,
		String riesgoCrediticioId,
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
		String otorgantesGarantias,
		String sinDatos
	) {
		try {
			Service service = Service.create(
				getClass().getClassLoader().getResource(wsdlFileName),
				new QName("http://soap.webservices.web.logistica.amensg.com.uy/", "RiesgoCrediticioWebServiceService")
			);
			
			RiesgoCrediticioWebService port = service.getPort(RiesgoCrediticioWebService.class);
			
			preparePort(port);
			
			port.actualizarDatosRiesgoCrediticioBCU(
				riesgoCrediticioId,
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
				otorgantesGarantias,
				sinDatos
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
		String wsdlFileName,
		String riesgoCrediticioId,
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
				getClass().getClassLoader().getResource(wsdlFileName),
				new QName("http://soap.webservices.web.logistica.amensg.com.uy/", "RiesgoCrediticioWebServiceService")
			);
			
			RiesgoCrediticioWebService port = service.getPort(RiesgoCrediticioWebService.class);
			
			preparePort(port);
			
			port.actualizarDatosRiesgoCrediticioBCUInstitucionFinanciera(
				riesgoCrediticioId,
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
	
	public void preparePort(RiesgoCrediticioWebService port) 
		throws NoSuchAlgorithmException, KeyStoreException, CertificateException, FileNotFoundException, 
		IOException, UnrecoverableKeyException, KeyManagementException {
		BindingProvider bindingProvider = (BindingProvider) port;
		
		TrustManager[] trustAllCerts = new TrustManager[] { 
			new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				}
			
				public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				}
			}
		};
		
		SSLContext sslContext = SSLContext.getInstance("SSL");
		
		sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
		
		bindingProvider.getRequestContext().put(
			"com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory",
			sslContext.getSocketFactory()
		);
	}
}