package uy.com.amensg.logistica.robot;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import uy.com.amensg.logistica.webservices.LogisticaPHWebService;
import uy.com.amensg.logistica.webservices.LogisticaWebService;

public class ConnectionStrategyWebService implements IConnectionStrategy {

	public String getSiguienteMidSinProcesar(String wsdlFileName) {
		String result = "";
		
		try {
			String serviceName = "LogisticaWebServiceService";
			if (wsdlFileName.contains("PH")) {
				serviceName = "LogisticaPHWebServiceService";
				
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaPHWebService port = service.getPort(LogisticaPHWebService.class);
				
				preparePort(port);
				
				result = port.getSiguienteMidSinProcesar();
			} else {
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaWebService port = service.getPort(LogisticaWebService.class);
				
				preparePort(port);
				
				result = port.getSiguienteMidSinProcesar();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String getSiguienteNumeroContratoSinProcesar(String wsdlFileName) {
		String result = "";
		
		try {
			String serviceName = "LogisticaWebServiceService";
			if (wsdlFileName.contains("PH")) {
				serviceName = "LogisticaPHWebServiceService";
				
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaPHWebService port = service.getPort(LogisticaPHWebService.class);
				
				preparePort(port);
				
				result = port.getSiguienteNumeroContratoSinProcesar();
			} else {
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaWebService port = service.getPort(LogisticaWebService.class);
				
				preparePort(port);
				
				result = port.getSiguienteNumeroContratoSinProcesar();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void actualizarDatosMidContrato(
		String wsdlFileName,
		String direccion, 
		String documentoTipo, 
		String documento,
		String fechaFinContrato, 
		String localidad, 
		String codigoPostal, 
		String mid, 
		String nombre,
		String tipoContratoCodigo, 
		String tipoContratoDescripcion, 
		String agente, 
		String equipo,
		String numeroCliente, 
		String numeroContrato, 
		String estadoContrato
	) {
		try {
			String serviceName = "LogisticaWebServiceService";
			if (wsdlFileName.contains("PH")) {
				serviceName = "LogisticaPHWebServiceService";
				
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaPHWebService port = service.getPort(LogisticaPHWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidContrato(
						direccion, 
						documentoTipo, 
						documento,
						fechaFinContrato, 
						localidad, 
						codigoPostal, 
						mid, 
						nombre,
						tipoContratoCodigo, 
						tipoContratoDescripcion, 
						agente, 
						equipo,
						numeroCliente, 
						numeroContrato, 
						estadoContrato
					);
			} else {
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaWebService port = service.getPort(LogisticaWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidContrato(
					direccion, 
					documentoTipo, 
					documento,
					fechaFinContrato, 
					localidad, 
					codigoPostal, 
					mid, 
					nombre,
					tipoContratoCodigo, 
					tipoContratoDescripcion, 
					agente, 
					equipo,
					numeroCliente, 
					numeroContrato, 
					estadoContrato
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosMidPrepago(
		String wsdlFileName,
		String mesAno, 
		String mid, 
		String montoMesActual, 
		String montoMesAnterior1,
		String montoMesAnterior2, 
		String fechaActivacionKit, 
		String agente
	) {
		try {
			String serviceName = "LogisticaWebServiceService";
			if (wsdlFileName.contains("PH")) {
				serviceName = "LogisticaPHWebServiceService";
				
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaPHWebService port = service.getPort(LogisticaPHWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidPrepago(
					mesAno, 
					mid, 
					montoMesActual, 
					montoMesAnterior1,
					montoMesAnterior2, 
					fechaActivacionKit, 
					agente
				);
			} else {
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaWebService port = service.getPort(LogisticaWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidPrepago(
					mesAno, 
					mid, 
					montoMesActual, 
					montoMesAnterior1,
					montoMesAnterior2, 
					fechaActivacionKit, 
					agente
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosMidListaVacia(String wsdlFileName, String mid) {
		try {
			String serviceName = "LogisticaWebServiceService";
			if (wsdlFileName.contains("PH")) {
				serviceName = "LogisticaPHWebServiceService";
				
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaPHWebService port = service.getPort(LogisticaPHWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidListaVacia(
					mid
				);
			} else {
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaWebService port = service.getPort(LogisticaWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidListaVacia(
					mid
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosMidListaNegra(String wsdlFileName, String mid) {
		try {
			String serviceName = "LogisticaWebServiceService";
			if (wsdlFileName.contains("PH")) {
				serviceName = "LogisticaPHWebServiceService";
				
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaPHWebService port = service.getPort(LogisticaPHWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidListaNegra(
					mid
				);
			} else {
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaWebService port = service.getPort(LogisticaWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidListaNegra(
					mid
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosMidNegociando(String wsdlFileName, String mid) {
		try {
			String serviceName = "LogisticaWebServiceService";
			if (wsdlFileName.contains("PH")) {
				serviceName = "LogisticaPHWebServiceService";
				
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaPHWebService port = service.getPort(LogisticaPHWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidNegociando(
					mid
				);
			} else {
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaWebService port = service.getPort(LogisticaWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidNegociando(
					mid
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosMidNoLlamar(String wsdlFileName, String mid) {
		try {
			String serviceName = "LogisticaWebServiceService";
			if (wsdlFileName.contains("PH")) {
				serviceName = "LogisticaPHWebServiceService";
				
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaPHWebService port = service.getPort(LogisticaPHWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidNoLlamar(
					mid
				);
			} else {
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaWebService port = service.getPort(LogisticaWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosMidNoLlamar(
					mid
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosNumeroContratoListaVacia(String wsdlFileName, String numeroContrato) {
		try {
			String serviceName = "LogisticaWebServiceService";
			if (wsdlFileName.contains("PH")) {
				serviceName = "LogisticaPHWebServiceService";
				
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaPHWebService port = service.getPort(LogisticaPHWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosNumeroContratoListaVacia(
					numeroContrato
				);
			} else {
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaWebService port = service.getPort(LogisticaWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosNumeroContratoListaVacia(
					numeroContrato
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosPersona(
		String wsdlFileName,
		String idCliente, 
		String mid, 
		String pais, 
		String tipoDocumento,
		String documento, 
		String apellido, 
		String nombre, 
		String razonSocial, 
		String tipoCliente, 
		String actividad,
		String fechaNacimiento, 
		String sexo, 
		String direccionCalle, 
		String direccionNumero, 
		String direccionBis,
		String direccionApartamento, 
		String direccionEsquina, 
		String direccionBlock, 
		String direccionManzana,
		String direccionSolar, 
		String direccionObservaciones, 
		String direccionLocalidad,
		String direccionCodigoPostal, 
		String direccionCompleta, 
		String distribuidor, 
		String telefonosFijo,
		String telefonosAviso,
		String telefonosFax, 
		String email
	) {
		try {
			String serviceName = "LogisticaWebServiceService";
			if (wsdlFileName.contains("PH")) {
				serviceName = "LogisticaPHWebServiceService";
				
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaPHWebService port = service.getPort(LogisticaPHWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosPersona(
					idCliente, 
					mid, 
					pais, 
					tipoDocumento,
					documento, 
					apellido, 
					nombre, 
					razonSocial, 
					tipoCliente, 
					actividad,
					fechaNacimiento, 
					sexo, 
					direccionCalle, 
					direccionNumero, 
					direccionBis,
					direccionApartamento, 
					direccionEsquina, 
					direccionBlock, 
					direccionManzana,
					direccionSolar, 
					direccionObservaciones, 
					direccionLocalidad,
					direccionCodigoPostal, 
					direccionCompleta, 
					distribuidor, 
					telefonosFijo,
					telefonosAviso,
					telefonosFax, 
					email
				);
			} else {
				Service service = Service.create(
					getClass().getClassLoader().getResource(wsdlFileName), 
					new QName("http://soap.webservices.web.logistica.amensg.com.uy/", serviceName)
				);
				
				LogisticaWebService port = service.getPort(LogisticaWebService.class);
				
				preparePort(port);
				
				port.actualizarDatosPersona(
					idCliente, 
					mid, 
					pais, 
					tipoDocumento,
					documento, 
					apellido, 
					nombre, 
					razonSocial, 
					tipoCliente, 
					actividad,
					fechaNacimiento, 
					sexo, 
					direccionCalle, 
					direccionNumero, 
					direccionBis,
					direccionApartamento, 
					direccionEsquina, 
					direccionBlock, 
					direccionManzana,
					direccionSolar, 
					direccionObservaciones, 
					direccionLocalidad,
					direccionCodigoPostal, 
					direccionCompleta, 
					distribuidor, 
					telefonosFijo,
					telefonosAviso,
					telefonosFax, 
					email
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void preparePort(LogisticaWebService port) 
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
//			"com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory",
			"com.sun.xml.ws.transport.https.client.SSLSocketFactory",
			sslContext.getSocketFactory()
		);
	}
	
	private void preparePort(LogisticaPHWebService port) 
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
		
		Map<String, Object> requestContext = bindingProvider.getRequestContext();
		
		requestContext.put(
//			"com.sun.xml.internal.ws.transport.https.client.SSLSocketFactory",
			"com.sun.xml.ws.transport.https.client.SSLSocketFactory",
			sslContext.getSocketFactory()
		);
		
		HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
			public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
				return true;
			}
		});
	}
}