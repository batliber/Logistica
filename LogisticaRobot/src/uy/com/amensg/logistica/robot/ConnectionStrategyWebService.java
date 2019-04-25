package uy.com.amensg.logistica.robot;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import uy.com.amensg.logistica.robot.util.Configuration;
import uy.com.amensg.logistica.webservices.LogisticaWebService;

public class ConnectionStrategyWebService implements IConnectionStrategy {

	public String getSiguienteMidSinProcesar() {
		String result = "";
		
		try {
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("LogisticaWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "LogisticaWebServiceService")
			);
			
			LogisticaWebService webService = service.getPort(LogisticaWebService.class);
			
			result = webService.getSiguienteMidSinProcesar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public String getSiguienteNumeroContratoSinProcesar() {
		String result = "";
		
		try {
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("LogisticaWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "LogisticaWebServiceService")
			);
			
			LogisticaWebService webService = service.getPort(LogisticaWebService.class);
			
			result = webService.getSiguienteNumeroContratoSinProcesar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public void actualizarDatosMidContrato(
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
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("LogisticaWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "LogisticaWebServiceService")
			);
			
			LogisticaWebService webService = service.getPort(LogisticaWebService.class);
			
			webService.actualizarDatosMidContrato(
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosMidPrepago(
		String mesAno, 
		String mid, 
		String montoMesActual, 
		String montoMesAnterior1,
		String montoMesAnterior2, 
		String fechaActivacionKit, 
		String agente
	) {
		try {
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("LogisticaWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "LogisticaWebServiceService")
			);
			
			LogisticaWebService webService = service.getPort(LogisticaWebService.class);
			
			webService.actualizarDatosMidPrepago(
				mesAno, 
				mid, 
				montoMesActual, 
				montoMesAnterior1,
				montoMesAnterior2, 
				fechaActivacionKit, 
				agente
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosMidListaVacia(String mid) {
		try {
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("LogisticaWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "LogisticaWebServiceService")
			);
			
			LogisticaWebService webService = service.getPort(LogisticaWebService.class);
			
			webService.actualizarDatosMidListaVacia(
				mid
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosNumeroContratoListaVacia(String numeroContrato) {
		try {
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("LogisticaWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "LogisticaWebServiceService")
			);
			
			LogisticaWebService webService = service.getPort(LogisticaWebService.class);
			
			webService.actualizarDatosNumeroContratoListaVacia(
				numeroContrato
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosPersona(
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
			Service service = Service.create(
				new URL(Configuration.getInstance().getProperty("LogisticaWebServiceWSDLURL")), 
				new QName("http://webservices.logistica.amensg.com.uy/", "LogisticaWebServiceService")
			);
			
			LogisticaWebService webService = service.getPort(LogisticaWebService.class);
			
			webService.actualizarDatosPersona(
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}