package uy.com.amensg.logistica.robot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.amensg.logistica.bean.ACMInterfaceBean;
import uy.com.amensg.logistica.bean.IACMInterfaceBean;
import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfaceEstado;
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfaceNumeroContrato;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.robot.util.Configuration;

public class ConnectionStrategyRemoting implements IConnectionStrategy {

	private IACMInterfaceBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfaceBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		Properties properties = new Properties();
		properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		properties.put(javax.naming.Context.PROVIDER_URL, "jnp://" + Configuration.getInstance().getProperty("providerURL"));

		Context context = new InitialContext(properties);
		
		return (IACMInterfaceBean) context.lookup(lookupName);
	}
	
	public String getSiguienteMidSinProcesar() {
		String result = null;
		
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			String mid = iACMInterfaceBean.getSiguienteMidSinProcesar();
			
			if (mid != null) {
				result = mid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String getSiguienteNumeroContratoSinProcesar() {
		String result = null;
		
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			String numeroContrato = iACMInterfaceBean.getSiguienteNumeroContratoSinProcesar();
			
			if (numeroContrato != null) {
				result = numeroContrato;
			}
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
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			ACMInterfaceContrato acmInterfaceContrato = new ACMInterfaceContrato();
			acmInterfaceContrato.setDireccion(direccion);
			acmInterfaceContrato.setDocumentoTipo(new Long(documentoTipo));
			acmInterfaceContrato.setDocumento(documento);
			acmInterfaceContrato.setEstadoContrato(estadoContrato);
			acmInterfaceContrato.setFechaFinContrato(format.parse(fechaFinContrato));
			acmInterfaceContrato.setLocalidad(localidad);
			acmInterfaceContrato.setCodigoPostal(codigoPostal);
			acmInterfaceContrato.setMid(new Long(mid));
			acmInterfaceContrato.setNombre(nombre);
			acmInterfaceContrato.setTipoContratoCodigo(tipoContratoCodigo);
			acmInterfaceContrato.setTipoContratoDescripcion(tipoContratoDescripcion);
			acmInterfaceContrato.setAgente(agente);
			acmInterfaceContrato.setEquipo(equipo);
			acmInterfaceContrato.setNumeroCliente(new Long(numeroCliente));
			acmInterfaceContrato.setNumeroContrato(new Long(numeroContrato));
			acmInterfaceContrato.setUact(new Long(1));
			acmInterfaceContrato.setFact(new Date());
			acmInterfaceContrato.setTerm(new Long(1));
			
			iACMInterfaceBean.update(acmInterfaceContrato);
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
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			ACMInterfacePrepago acmInterfacePrepago = new ACMInterfacePrepago();
			acmInterfacePrepago.setMid(new Long(mid));
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			try {
				acmInterfacePrepago.setAgente(agente);
				acmInterfacePrepago.setMesAno(format.parse("01/" + mesAno));
				acmInterfacePrepago.setMontoMesActual(new Double(montoMesActual));
				acmInterfacePrepago.setMontoMesAnterior1(new Double(montoMesAnterior1));
				acmInterfacePrepago.setMontoMesAnterior2(new Double(montoMesAnterior2));
				
				acmInterfacePrepago.setMontoPromedio(
					(acmInterfacePrepago.getMontoMesActual() 
						+ acmInterfacePrepago.getMontoMesAnterior1()
						+ acmInterfacePrepago.getMontoMesAnterior2()) / 3
				);
				
				acmInterfacePrepago.setFechaActivacionKit(format.parse(fechaActivacionKit));
			} catch (Exception e) {
				e.printStackTrace();
				
				acmInterfacePrepago.setMontoMesActual(new Double(-1));
				acmInterfacePrepago.setMontoMesAnterior1(new Double(-1));
				acmInterfacePrepago.setMontoMesAnterior2(new Double(-1));
				acmInterfacePrepago.setMontoPromedio(new Double(-1));
			}
			
			acmInterfacePrepago.setUact(new Long(1));
			acmInterfacePrepago.setFact(new Date());
			acmInterfacePrepago.setTerm(new Long(1));
			
			iACMInterfaceBean.update(acmInterfacePrepago);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actualizarDatosMidListaVacia(
		String mid
	) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			ACMInterfaceEstado estado = new ACMInterfaceEstado();
			estado.setId(new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ListaVacia")));
			
			ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
			acmInterfaceMid.setEstado(estado);
			acmInterfaceMid.setMid(new Long(mid));
						
			iACMInterfaceBean.update(acmInterfaceMid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarDatosNumeroContratoListaVacia(
		String numeroContrato
	) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			ACMInterfaceEstado estado = new ACMInterfaceEstado();
			estado.setId(new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ListaVacia")));
			
			ACMInterfaceNumeroContrato acmInterfaceNumeroContrato = new ACMInterfaceNumeroContrato();
			acmInterfaceNumeroContrato.setEstado(estado);
			acmInterfaceNumeroContrato.setNumeroContrato(new Long(numeroContrato));
						
			iACMInterfaceBean.update(acmInterfaceNumeroContrato);
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
		String email) {
		try {
			// TODO
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}