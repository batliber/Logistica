package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.EstadoBean;
import uy.com.amensg.logistica.bean.IEstadoBean;
import uy.com.amensg.logistica.entities.Estado;
import uy.com.amensg.logistica.entities.EstadoTO;

@RemoteProxy
public class EstadoDWR {

	private IEstadoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEstadoBean) context.lookup(lookupName);
	}
	
	public Collection<EstadoTO> list() {
		Collection<EstadoTO> result = new LinkedList<EstadoTO>();
		
		try {
			IEstadoBean iEstadoBean = lookupBean();
			
			for (Estado estado : iEstadoBean.list()) {
				result.add(transform(estado));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static EstadoTO transform(Estado estado) {
		EstadoTO estadoTO = new EstadoTO();
		
		estadoTO.setNombre(estado.getNombre());
		
		estadoTO.setFact(estado.getFact());
		estadoTO.setId(estado.getId());
		estadoTO.setTerm(estado.getTerm());
		estadoTO.setUact(estado.getUact());
		
		return estadoTO;
	}
	
	public static Estado transform(EstadoTO estadoTO) {
		Estado estado = new Estado();
		
		estado.setNombre(estadoTO.getNombre());
		
		estado.setFact(estadoTO.getFact());
		estado.setId(estadoTO.getId());
		estado.setTerm(estadoTO.getTerm());
		estado.setUact(estadoTO.getUact());
		
		return estado;
	}
}