package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.EstadoProcesoImportacionBean;
import uy.com.amensg.logistica.bean.IEstadoProcesoImportacionBean;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacionTO;

@RemoteProxy
public class EstadoProcesoImportacionDWR {

	private IEstadoProcesoImportacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoProcesoImportacionBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoProcesoImportacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEstadoProcesoImportacionBean) context.lookup(lookupName);
	}
	
	public Collection<EstadoProcesoImportacionTO> list() {
		Collection<EstadoProcesoImportacionTO> result = new LinkedList<EstadoProcesoImportacionTO>();
		
		try {
			IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean = lookupBean();
			
			for (EstadoProcesoImportacion estadoProcesoImportacion : iEstadoProcesoImportacionBean.list()) {
				result.add(transform(estadoProcesoImportacion));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static EstadoProcesoImportacionTO transform(EstadoProcesoImportacion estadoProcesoImportacion) {
		EstadoProcesoImportacionTO result = new EstadoProcesoImportacionTO();
		
		result.setNombre(estadoProcesoImportacion.getNombre());
		
		result.setFcre(estadoProcesoImportacion.getFcre());
		result.setFact(estadoProcesoImportacion.getFact());
		result.setId(estadoProcesoImportacion.getId());
		result.setTerm(estadoProcesoImportacion.getTerm());
		result.setUact(estadoProcesoImportacion.getUact());
		result.setUcre(estadoProcesoImportacion.getUcre());
		
		return result;
	}
}