package uy.com.amensg.logistica.robot;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.amensg.logistica.bean.ACMInterfaceBean;
import uy.com.amensg.logistica.bean.IACMInterfaceBean;
import uy.com.amensg.logistica.entities.ACMInterfaceContrato;
import uy.com.amensg.logistica.entities.ACMInterfacePrepago;
import uy.com.amensg.logistica.robot.util.Configuration;

public class LogisticaProxy {

	private void getSiguienteMidContratoSinProcesar() {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			ACMInterfaceContrato acmInterfaceContrato = iACMInterfaceBean.getNextContratoSinProcesar();
			
			System.out.println(
				acmInterfaceContrato.getMid()
				+ " " + 
				acmInterfaceContrato.getProcesoId()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getSiguienteMidPrepagoSinProcesar() {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			ACMInterfacePrepago acmInterfacePrepago = iACMInterfaceBean.getNextPrepagoSinProcesar();
			
			System.out.println(
				acmInterfacePrepago.getMid()
				+ " " + 
				acmInterfacePrepago.getProcesoId()
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void actualizarDatosMidContrato(
		String direccion,
		String documento,
		String fechaFinContrato,
		String localidad,
		String mid,
		String nombre,
		String procesoId,
		String tipoContrato) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			ACMInterfaceContrato acmInterfaceContrato = new ACMInterfaceContrato();
			acmInterfaceContrato.setDireccion(direccion);
			acmInterfaceContrato.setDocumento(documento);
			acmInterfaceContrato.setFechaFinContrato(fechaFinContrato);
			acmInterfaceContrato.setLocalidad(localidad);
			acmInterfaceContrato.setMid(mid);
			acmInterfaceContrato.setNombre(nombre);
			acmInterfaceContrato.setProcesoId(new Long(procesoId));
			acmInterfaceContrato.setTipoContrato(tipoContrato);
			
			iACMInterfaceBean.update(acmInterfaceContrato);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void actualizarDatosMidPrepago(
		String mesAno,
		String mid,
		String montoMesActual,
		String montoMesAnterior1,
		String montoMesAnterior2,
		String procesoId
		) {
		try {
			IACMInterfaceBean iACMInterfaceBean = lookupBean();
			
			ACMInterfacePrepago acmInterfacePrepago = new ACMInterfacePrepago();
			acmInterfacePrepago.setMesAno(mesAno);
			acmInterfacePrepago.setMid(mid);
			acmInterfacePrepago.setMontoMesActual(montoMesActual);
			acmInterfacePrepago.setMontoMesAnterior1(montoMesAnterior1);
			acmInterfacePrepago.setMontoMesAnterior2(montoMesAnterior2);
			acmInterfacePrepago.setProcesoId(new Long(procesoId));
			
			iACMInterfaceBean.update(acmInterfacePrepago);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	
	public static void main(String[] args) {
		if (args[0].equals("getSiguienteMidSinProcesar")) {
			if (args[1].equals("contrato")) {
				new LogisticaProxy().getSiguienteMidContratoSinProcesar();
			} else if (args[1].equals("prepago")) {
				new LogisticaProxy().getSiguienteMidPrepagoSinProcesar();
			}
		} else if (args[0].equals("actualizarDatosMid")) {
			if (args[1].equals("contrato")) {
				new LogisticaProxy().actualizarDatosMidContrato(
					args[2],
					args[3],
					args[4],
					args[5],
					args[6],
					args[7],
					args[8],
					args[9]
				);
			} else if (args[1].equals("prepago")) {
				new LogisticaProxy().actualizarDatosMidPrepago(
					args[2],
					args[3],
					args[4],
					args[5],
					args[6],
					args[7]
				);
			}
		}
	}
}