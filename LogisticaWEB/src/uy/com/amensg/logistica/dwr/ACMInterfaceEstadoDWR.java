package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceEstadoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceEstadoBean;
import uy.com.amensg.logistica.entities.ACMInterfaceEstado;
import uy.com.amensg.logistica.entities.ACMInterfaceEstadoTO;

@RemoteProxy
public class ACMInterfaceEstadoDWR {

	private IACMInterfaceEstadoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IACMInterfaceEstadoBean.class.getName();
		String beanName = ACMInterfaceEstadoBean.class.getSimpleName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceEstadoBean) context.lookup(lookupName);
	}
	
	public Collection<ACMInterfaceEstadoTO> list() {
		Collection<ACMInterfaceEstadoTO> result = new LinkedList<ACMInterfaceEstadoTO>();
		
		try {
			IACMInterfaceEstadoBean iACMInterfaceEstadoBean = lookupBean();
			
			for (ACMInterfaceEstado acmInterfaceEstado : iACMInterfaceEstadoBean.list()) {
				result.add(transform(acmInterfaceEstado));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ACMInterfaceEstadoTO transform(ACMInterfaceEstado aCMInterfaceEstado) {
		ACMInterfaceEstadoTO result = new ACMInterfaceEstadoTO();
		
		result.setDescripcion(aCMInterfaceEstado.getDescripcion());
		
		result.setFcre(aCMInterfaceEstado.getFcre());
		result.setFact(aCMInterfaceEstado.getFact());
		result.setId(aCMInterfaceEstado.getId());
		result.setTerm(aCMInterfaceEstado.getTerm());
		result.setUact(aCMInterfaceEstado.getUact());
		result.setUcre(aCMInterfaceEstado.getUcre());
		
		return result;
	}
}