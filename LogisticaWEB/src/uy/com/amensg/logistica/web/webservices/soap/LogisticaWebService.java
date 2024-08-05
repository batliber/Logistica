package uy.com.amensg.logistica.web.webservices.soap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import uy.com.amensg.logistica.bean.ACMInterfaceBean;
import uy.com.amensg.logistica.bean.IACMInterfaceBean;
import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfacePersona;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;

@WebService
public class LogisticaWebService {

	@WebMethod
	public String getSiguienteMidSinProcesar() {
		String result = "";
		
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			String mid = iACMInterfaceBean.getSiguienteMidSinProcesar();
			
			if (mid != null) {
				result = mid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@WebMethod
	public String getSiguienteNumeroContratoSinProcesar() {
		String result = "";
		
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			String numeroContrato = iACMInterfaceBean.getSiguienteNumeroContratoSinProcesar();
			
			if (numeroContrato != null) {
				result = numeroContrato;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@WebMethod
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
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			Random random = new Random();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date fact = GregorianCalendar.getInstance().getTime();
			
			ACMInterfaceContrato acmInterfaceContrato = new ACMInterfaceContrato();
			if (agente != null && !agente.trim().isEmpty()) {
				acmInterfaceContrato.setAgente(agente);
			}
			if (codigoPostal != null && !codigoPostal.trim().isEmpty()) {
				acmInterfaceContrato.setCodigoPostal(codigoPostal);
			}
			if (direccion != null && !direccion.trim().isEmpty()) {
				acmInterfaceContrato.setDireccion(direccion);
			}
			if (documento != null && !documento.trim().isEmpty()) {
				acmInterfaceContrato.setDocumento(documento);
			}
			if (documentoTipo != null) {
				acmInterfaceContrato.setDocumentoTipo(Long.parseLong(documentoTipo));
			}
			if (equipo != null && !equipo.trim().isEmpty()) {
				acmInterfaceContrato.setEquipo(equipo);
			}
			if (estadoContrato != null && !estadoContrato.trim().isEmpty()) {
				acmInterfaceContrato.setEstadoContrato(estadoContrato);
			}
			if (fechaFinContrato != null) {
				acmInterfaceContrato.setFechaFinContrato(format.parse(fechaFinContrato));
			}
			if (localidad != null && !localidad.trim().isEmpty()) {
				acmInterfaceContrato.setLocalidad(localidad);
			}
			if (mid != null) {
				acmInterfaceContrato.setMid(Long.parseLong(mid));
			}
			if (nombre != null && !nombre.trim().isEmpty()) {
				acmInterfaceContrato.setNombre(nombre);
			}
			if (numeroCliente != null) {
				acmInterfaceContrato.setNumeroCliente(Long.parseLong(numeroCliente));
			}
			if (numeroContrato != null) {
				acmInterfaceContrato.setNumeroContrato(numeroContrato);
			}
			acmInterfaceContrato.setRandom(Long.valueOf(random.nextInt()));
			if (tipoContratoCodigo != null && !tipoContratoCodigo.trim().isEmpty()) {
				acmInterfaceContrato.setTipoContratoCodigo(tipoContratoCodigo);
			}
			if (tipoContratoDescripcion != null && !tipoContratoDescripcion.trim().isEmpty()) {
				acmInterfaceContrato.setTipoContratoDescripcion(tipoContratoDescripcion);
			}
			
			acmInterfaceContrato.setFact(fact);
			acmInterfaceContrato.setTerm(Long.valueOf(1));
			acmInterfaceContrato.setUact(Long.valueOf(1));

			iACMInterfaceBean.update(acmInterfaceContrato);
		} catch (Exception e) {
			System.err.println("Error para el MID: " + mid);
			e.printStackTrace();
		}
	}
	
	@WebMethod
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
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			Random random = new Random();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date fact = GregorianCalendar.getInstance().getTime();
			
			Date mesAnoDate = null;
			Double montoMesActualDouble = Double.valueOf(-1);
			Double montoMesAnterior1Double = Double.valueOf(-1);
			Double montoMesAnterior2Double = Double.valueOf(-1);
			Double montoPromedio = Double.valueOf(-1);
			Date fechaActivacionKitDate = null;
			
			try {
				// Parsear los parámetros tipados.
				mesAnoDate = format.parse("01/" + mesAno);
				montoMesActualDouble = Double.valueOf(montoMesActual);
				montoMesAnterior1Double = Double.valueOf(montoMesAnterior1);
				montoMesAnterior2Double = Double.valueOf(montoMesAnterior2);
				montoPromedio = 
					(montoMesActualDouble + montoMesAnterior1Double + montoMesAnterior2Double) / 3;
				fechaActivacionKitDate = format.parse(fechaActivacionKit);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ACMInterfacePrepago acmInterfacePrepago = new ACMInterfacePrepago();
			if (agente != null && !agente.trim().isEmpty()) {
				acmInterfacePrepago.setAgente(agente);
			}
			acmInterfacePrepago.setFechaActivacionKit(fechaActivacionKitDate);
			acmInterfacePrepago.setMesAno(mesAnoDate);
			if (mid != null) {
				acmInterfacePrepago.setMid(Long.parseLong(mid));
			}
			acmInterfacePrepago.setMontoMesActual(montoMesActualDouble);
			acmInterfacePrepago.setMontoMesAnterior1(montoMesAnterior1Double);
			acmInterfacePrepago.setMontoMesAnterior2(montoMesAnterior2Double);
			acmInterfacePrepago.setMontoPromedio(montoPromedio);
			acmInterfacePrepago.setRandom(Long.valueOf(random.nextInt()));
			
			acmInterfacePrepago.setFact(fact);
			acmInterfacePrepago.setTerm(Long.valueOf(1));
			acmInterfacePrepago.setUact(Long.valueOf(1));
			
			iACMInterfaceBean.update(acmInterfacePrepago);
		} catch (Exception e) {
			System.err.println("Error para el MID: " + mid);
			e.printStackTrace();
		}
	}

	@WebMethod
	public void actualizarDatosMidListaVacia(String mid) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			iACMInterfaceBean.actualizarDatosMidListaVacia(Long.parseLong(mid));
		} catch (Exception e) {
			System.err.println("Error para el MID: " + mid);
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public void actualizarDatosMidListaNegra(String mid) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			iACMInterfaceBean.actualizarDatosMidListaNegra(Long.parseLong(mid));
		} catch (Exception e) {
			System.err.println("Error para el MID: " + mid);
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public void actualizarDatosMidNegociando(String mid) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			iACMInterfaceBean.actualizarDatosMidNegociando(Long.parseLong(mid));
		} catch (Exception e) {
			System.err.println("Error para el MID: " + mid);
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public void actualizarDatosMidNoLlamar(String mid) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			iACMInterfaceBean.actualizarDatosMidNoLlamar(Long.parseLong(mid));
		} catch (Exception e) {
			System.err.println("Error para el MID: " + mid);
			e.printStackTrace();
		}
	}
	
	@WebMethod
	public void actualizarDatosNumeroContratoListaVacia(String numeroContrato) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			iACMInterfaceBean.actualizarDatosNumeroContratoListaVacia(numeroContrato);
		} catch (Exception e) {
			System.err.println("Error para el contrato: " + numeroContrato);
			e.printStackTrace();
		}
	}

	@WebMethod
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
			IACMInterfaceBean iACMInterfaceBean = lookupACMInterfaceBean();
			
			Date fact = GregorianCalendar.getInstance().getTime();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			ACMInterfacePersona acmInterfacePersona = new ACMInterfacePersona();
			if (actividad != null && !actividad.trim().isEmpty()) {
				acmInterfacePersona.setActividad(actividad);
			}
			if (direccionApartamento != null && !direccionApartamento.trim().isEmpty()) {
				acmInterfacePersona.setApartamento(direccionApartamento);
			}
			if (apellido != null && !apellido.trim().isEmpty()) {
				acmInterfacePersona.setApellido(apellido);
			}
			if (direccionBis != null && !direccionBis.trim().isEmpty()) {
				acmInterfacePersona.setBis(direccionBis);
			}
			if (direccionBlock != null && !direccionBlock.trim().isEmpty()) {
				acmInterfacePersona.setBlock(direccionBlock);
			}
			if (direccionCalle != null && !direccionCalle.trim().isEmpty()) {
				acmInterfacePersona.setCalle(direccionCalle);
			}
			if (direccionCodigoPostal != null && !direccionCodigoPostal.trim().isEmpty()) {
				acmInterfacePersona.setCodigoPostal(direccionCodigoPostal);
			}
			if (direccionCompleta != null && !direccionCompleta.trim().isEmpty()) {
				acmInterfacePersona.setDireccion(direccionCompleta);
			}
			if (distribuidor != null && !distribuidor.trim().isEmpty()) {
				acmInterfacePersona.setDistribucion(distribuidor);
			}
			if (documento != null && !documento.trim().isEmpty()) {
				acmInterfacePersona.setDocumento(documento);
			}
			if (email != null && !email.trim().isEmpty()) {
				acmInterfacePersona.setEmail(email);
			}
			if (direccionEsquina != null && !direccionEsquina.trim().isEmpty()) {
				acmInterfacePersona.setEsquina(direccionEsquina);
			}
			if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
				try {
					acmInterfacePersona.setFechaNacimiento(format.parse(fechaNacimiento));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (idCliente != null && !idCliente.trim().isEmpty()) {
				acmInterfacePersona.setIdCliente(idCliente);
			}
			if (direccionLocalidad != null && !direccionLocalidad.trim().isEmpty()) {
				acmInterfacePersona.setLocalidad(direccionLocalidad);
			}
			if (direccionManzana != null && !direccionManzana.trim().isEmpty()) {
				acmInterfacePersona.setManzana(direccionManzana);
			}
			if (nombre != null && !nombre.trim().isEmpty()) {
				acmInterfacePersona.setNombre(nombre);
			}
			if (direccionNumero != null && !direccionNumero.trim().isEmpty()) {
				acmInterfacePersona.setNumero(direccionNumero);
			}
			if (pais != null && !pais.trim().isEmpty()) {
				acmInterfacePersona.setPais(pais);
			}
			if (razonSocial != null && !razonSocial.trim().isEmpty()) {
				acmInterfacePersona.setRazonSocial(razonSocial);
			}
			if (sexo != null && !sexo.trim().isEmpty()) {
				acmInterfacePersona.setSexo(sexo);
			}
			if (direccionSolar != null && !direccionSolar.trim().isEmpty()) {
				acmInterfacePersona.setSolar(direccionSolar);
			}
			if (telefonosFijo != null && telefonosFijo.trim().isEmpty()) {
				acmInterfacePersona.setTelefono(telefonosFijo);
			}
			if (tipoCliente != null && !tipoCliente.trim().isEmpty()) {
				acmInterfacePersona.setTipoCliente(tipoCliente);
			}
			if (tipoDocumento != null && !tipoDocumento.trim().isEmpty()) {
				acmInterfacePersona.setTipoDocumento(tipoDocumento);
			}
			
			acmInterfacePersona.setFact(fact);
			acmInterfacePersona.setFcre(fact);
			acmInterfacePersona.setTerm(Long.valueOf(1));
			acmInterfacePersona.setUact(Long.valueOf(1));
			acmInterfacePersona.setUcre(Long.valueOf(1));
			
			if (mid != null) {
				iACMInterfaceBean.update(acmInterfacePersona, Long.parseLong(mid));
			}
		} catch (Exception e) {
			System.err.println("Error para el MID: " + mid);
			e.printStackTrace();
		}
	}
	
	private IACMInterfaceBean lookupACMInterfaceBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfaceBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
				
		return (IACMInterfaceBean) context.lookup(lookupName);
	}
}