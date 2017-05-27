package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.EstadoControlBean;
import uy.com.amensg.logistica.bean.IEstadoControlBean;
import uy.com.amensg.logistica.entities.EstadoControl;
import uy.com.amensg.logistica.entities.EstadoControlTO;

@RemoteProxy
public class EstadoControlDWR {

	private IEstadoControlBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoControlBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoControlBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEstadoControlBean) context.lookup(lookupName);
	}
	
	public Collection<EstadoControlTO> list() {
		Collection<EstadoControlTO> result = new LinkedList<EstadoControlTO>();
		
		try {
			IEstadoControlBean iEstadoControlBean = lookupBean();
			
			for (EstadoControl estadoControl : iEstadoControlBean.list()) {
				result.add(transform(estadoControl));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static EstadoControlTO transform(EstadoControl estadoControl) {
		EstadoControlTO estadoControlTO = new EstadoControlTO();
		
		estadoControlTO.setNombre(estadoControl.getNombre());
		
		estadoControlTO.setFact(estadoControl.getFact());
		estadoControlTO.setId(estadoControl.getId());
		estadoControlTO.setTerm(estadoControl.getTerm());
		estadoControlTO.setUact(estadoControl.getUact());
		
		return estadoControlTO;
	}
	
	public static EstadoControl transform(EstadoControlTO estadoControlTO) {
		EstadoControl estadoControl = new EstadoControl();
		
		estadoControl.setNombre(estadoControlTO.getNombre());
		
		estadoControl.setFact(estadoControlTO.getFact());
		estadoControl.setId(estadoControlTO.getId());
		estadoControl.setTerm(estadoControlTO.getTerm());
		estadoControl.setUact(estadoControlTO.getUact());
		
		return estadoControl;
	}
}