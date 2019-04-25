package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceProcesoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceProcesoBean;
import uy.com.amensg.logistica.entities.ACMInterfaceProcesoEstadistica;
import uy.com.amensg.logistica.entities.ACMInterfaceProcesoEstadisticaTO;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.MetadataConsultaResultadoTO;
import uy.com.amensg.logistica.entities.MetadataConsultaTO;

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
	
	public MetadataConsultaResultadoTO listEstadisticas(MetadataConsultaTO metadataConsultaTO) {
		MetadataConsultaResultadoTO result = new MetadataConsultaResultadoTO();
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IACMInterfaceProcesoBean iACMInterfaceProcesoBean = this.lookupBean();
				
				MetadataConsultaResultado metadataConsultaResultado = 
					iACMInterfaceProcesoBean.listEstadisticas(
						MetadataConsultaDWR.transform(metadataConsultaTO)
					);
				
				Collection<Object> registrosMuestra = new LinkedList<Object>();
				
				for (Object object : metadataConsultaResultado.getRegistrosMuestra()) {
					registrosMuestra.add(transform((ACMInterfaceProcesoEstadistica) object));
				}
				
				result.setRegistrosMuestra(registrosMuestra);
				result.setCantidadRegistros(metadataConsultaResultado.getCantidadRegistros());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	public Long countEstadisticas(MetadataConsultaTO metadataConsultaTO) {
		Long result = null;
		
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IACMInterfaceProcesoBean iACMInterfaceProcesoBean = this.lookupBean();
				
				result = 
					iACMInterfaceProcesoBean.countEstadisticas(
						MetadataConsultaDWR.transform(
							metadataConsultaTO
						)
					);
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
		ACMInterfaceProcesoEstadistica acmInterfaceProcesoEstadistica
	) {
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