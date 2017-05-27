package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceProcesoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceProcesoBean;
import uy.com.amensg.logistica.entities.ACMInterfaceProcesoEstadistica;
import uy.com.amensg.logistica.entities.ACMInterfaceProcesoEstadisticaTO;

@RemoteProxy
public class ACMInterfaceProcesoDWR {

	private IACMInterfaceProcesoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ACMInterfaceProcesoBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceProcesoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceProcesoBean) context.lookup(lookupName);
	}
	
	public Collection<ACMInterfaceProcesoEstadisticaTO> listEstadisticas() {
		Collection<ACMInterfaceProcesoEstadisticaTO> result = new LinkedList<ACMInterfaceProcesoEstadisticaTO>();
		
		try {
			IACMInterfaceProcesoBean iACMInterfaceProcesoBean = this.lookupBean();
			
			for (ACMInterfaceProcesoEstadistica acmInterfaceProcesoEstadistica : iACMInterfaceProcesoBean.listEstadisticas()) {
				result.add(transform(acmInterfaceProcesoEstadistica));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void finalizarProcesos() {
		try {
			IACMInterfaceProcesoBean iACMInterfaceProcesoBean = this.lookupBean();
			
			iACMInterfaceProcesoBean.finalizarProcesos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ACMInterfaceProcesoEstadisticaTO transform(
		ACMInterfaceProcesoEstadistica acmInterfaceProcesoEstadistica) {
		ACMInterfaceProcesoEstadisticaTO result = new ACMInterfaceProcesoEstadisticaTO();
		
		result.setCantidadRegistrosEnProceso(acmInterfaceProcesoEstadistica.getCantidadRegistrosEnProceso());
		result.setCantidadRegistrosListaVacia(acmInterfaceProcesoEstadistica.getCantidadRegistrosListaVacia());
		result.setCantidadRegistrosParaProcesar(acmInterfaceProcesoEstadistica.getCantidadRegistrosParaProcesar());
		result.setCantidadRegistrosParaProcesarPrioritario(acmInterfaceProcesoEstadistica.getCantidadRegistrosParaProcesarPrioritario());
		result.setCantidadRegistrosProcesado(acmInterfaceProcesoEstadistica.getCantidadRegistrosProcesado());
		result.setFechaFin(acmInterfaceProcesoEstadistica.getFechaFin());
		result.setFechaInicio(acmInterfaceProcesoEstadistica.getFechaInicio());
		result.setId(acmInterfaceProcesoEstadistica.getId());
		result.setObservaciones(acmInterfaceProcesoEstadistica.getObservaciones());
		
		return result;
	}
}