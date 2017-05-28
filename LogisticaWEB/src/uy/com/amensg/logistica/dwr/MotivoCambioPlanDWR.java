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
<<<<<<< HEAD
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = MotivoCambioPlanBean.class.getSimpleName();
		String remoteInterfaceName = IMotivoCambioPlanBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
=======
		String EARName = "Logistica";
		String beanName = MotivoCambioPlanBean.class.getSimpleName();
		String remoteInterfaceName = IMotivoCambioPlanBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
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
		MotivoCambioPlanTO motivoCambioPlanTO = new MotivoCambioPlanTO();
		
		motivoCambioPlanTO.setDescripcion(motivoCambioPlan.getDescripcion());
		
		motivoCambioPlanTO.setFact(motivoCambioPlan.getFact());
		motivoCambioPlanTO.setId(motivoCambioPlan.getId());
		motivoCambioPlanTO.setTerm(motivoCambioPlan.getTerm());
		motivoCambioPlanTO.setUact(motivoCambioPlan.getUact());
		
		return motivoCambioPlanTO;
	}
}