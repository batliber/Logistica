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
		String EARName = "Logistica";
		String beanName = ACMInterfaceEstadoBean.class.getSimpleName();
		String remoteInterfaceName = IACMInterfaceEstadoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
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
		ACMInterfaceEstadoTO aCMInterfaceEstadoTO = new ACMInterfaceEstadoTO();
		
		aCMInterfaceEstadoTO.setDescripcion(aCMInterfaceEstado.getDescripcion());
		
		aCMInterfaceEstadoTO.setFact(aCMInterfaceEstado.getFact());
		aCMInterfaceEstadoTO.setId(aCMInterfaceEstado.getId());
		aCMInterfaceEstadoTO.setTerm(aCMInterfaceEstado.getTerm());
		aCMInterfaceEstadoTO.setUact(aCMInterfaceEstado.getUact());
		
		return aCMInterfaceEstadoTO;
	}
}