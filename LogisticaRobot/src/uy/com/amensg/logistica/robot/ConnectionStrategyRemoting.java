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
import uy.com.amensg.logistica.entities.ACMInterfaceMid;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.robot.util.Configuration;

public class ConnectionStrategyRemoting implements IConnectionStrategy {

	private IACMInterfaceBean lookupBean() throws NamingException {
		String EARName = Configuration.getInstance().getProperty("EARName");
		String beanName = ACMInterfaceBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		
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
			
			ACMInterfaceMid acmInterfaceMid = iACMInterfaceBean.getNextMidSinProcesar();
			
			result = acmInterfaceMid.getMid();
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
		String equipo
	) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			ACMInterfaceContrato acmInterfaceContrato = new ACMInterfaceContrato();
			acmInterfaceContrato.setDireccion(direccion);
			acmInterfaceContrato.setDocumentoTipo(new Long(documentoTipo));
			acmInterfaceContrato.setDocumento(documento);
			acmInterfaceContrato.setFechaFinContrato(format.parse(fechaFinContrato));
			acmInterfaceContrato.setLocalidad(localidad);
			acmInterfaceContrato.setCodigoPostal(codigoPostal);
			acmInterfaceContrato.setMid(mid);
			acmInterfaceContrato.setNombre(nombre);
			acmInterfaceContrato.setTipoContratoCodigo(tipoContratoCodigo);
			acmInterfaceContrato.setTipoContratoDescripcion(tipoContratoDescripcion);
			acmInterfaceContrato.setAgente(agente);
			acmInterfaceContrato.setEquipo(equipo);
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
		String montoMesAnterior2
	) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			ACMInterfacePrepago acmInterfacePrepago = new ACMInterfacePrepago();
			acmInterfacePrepago.setMesAno(mesAno);
			acmInterfacePrepago.setMid(mid);
			acmInterfacePrepago.setMontoMesActual(montoMesActual);
			acmInterfacePrepago.setMontoMesAnterior1(montoMesAnterior1);
			acmInterfacePrepago.setMontoMesAnterior2(montoMesAnterior2);
			
			try {
				acmInterfacePrepago.setMontoPromedio(
					Long.toString(
						(
							((new Long(acmInterfacePrepago.getMontoMesActual())
								+ new Long(acmInterfacePrepago.getMontoMesAnterior1())
								+ new Long(acmInterfacePrepago.getMontoMesAnterior2()))) / 3
						)
					)
				);
			} catch (Exception e) {
				e.printStackTrace();
				
				acmInterfacePrepago.setMontoPromedio(Long.toString(-1));
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
			
			ACMInterfaceMid acmInterfaceMid = new ACMInterfaceMid();
			acmInterfaceMid.setEstado(new Long(Configuration.getInstance().getProperty("ACMInterfaceEstado.ListaVacia")));
			acmInterfaceMid.setMid(mid);
						
			iACMInterfaceBean.update(acmInterfaceMid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}