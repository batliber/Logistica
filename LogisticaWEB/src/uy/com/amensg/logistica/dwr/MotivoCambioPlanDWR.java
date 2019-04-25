package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.IMotivoCambioPlanBean;
import uy.com.amensg.logistica.bean.MotivoCambioPlanBean;
import uy.com.amensg.logistica.entities.MotivoCambioPlan;
import uy.com.amensg.logistica.entities.MotivoCambioPlanTO;

@RemoteProxy
public class MotivoCambioPlanDWR {

	private IMotivoCambioPlanBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = MotivoCambioPlanBean.class.getSimpleName();
		String remoteInterfaceName = IMotivoCambioPlanBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		Context context = new InitialContext();
		
		return (IMotivoCambioPlanBean) context.lookup(lookupName);
	}
	
	public Collection<MotivoCambioPlanTO> list() {
		Collection<MotivoCambioPlanTO> result = new LinkedList<MotivoCambioPlanTO>();
		
		try {
			IMotivoCambioPlanBean iMotivoCambioPlanBean = lookupBean();
			
			for (MotivoCambioPlan motivoCambioPlan : iMotivoCambioPlanBean.list()) {
				result.add(transform(motivoCambioPlan));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static MotivoCambioPlanTO transform(MotivoCambioPlan motivoCambioPlan) {
		MotivoCambioPlanTO result = new MotivoCambioPlanTO();
		
		result.setDescripcion(motivoCambioPlan.getDescripcion());
		
		result.setFcre(motivoCambioPlan.getFcre());
		result.setFact(motivoCambioPlan.getFact());
		result.setId(motivoCambioPlan.getId());
		result.setTerm(motivoCambioPlan.getTerm());
		result.setUact(motivoCambioPlan.getUact());
		result.setUcre(motivoCambioPlan.getUcre());
		
		return result;
	}
}