package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.CalificacionRiesgoCrediticioBCUBean;
import uy.com.amensg.logistica.bean.ICalificacionRiesgoCrediticioBCUBean;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioBCU;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioBCUTO;

@RemoteProxy
public class CalificacionRiesgoCrediticioBCUDWR {

	private ICalificacionRiesgoCrediticioBCUBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = CalificacionRiesgoCrediticioBCUBean.class.getSimpleName();
		String remoteInterfaceName = ICalificacionRiesgoCrediticioBCUBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ICalificacionRiesgoCrediticioBCUBean) context.lookup(lookupName);
	}
	
	public Collection<CalificacionRiesgoCrediticioBCUTO> list() {
		Collection<CalificacionRiesgoCrediticioBCUTO> result = new LinkedList<CalificacionRiesgoCrediticioBCUTO>();
		
		try {
			ICalificacionRiesgoCrediticioBCUBean iCalificacionRiesgoCrediticioBCUBean = lookupBean();
			
			for (CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU : iCalificacionRiesgoCrediticioBCUBean.list()) {
				result.add(transform(calificacionRiesgoCrediticioBCU));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static CalificacionRiesgoCrediticioBCUTO transform(CalificacionRiesgoCrediticioBCU calificacionRiesgoCrediticioBCU) {
		CalificacionRiesgoCrediticioBCUTO result = new CalificacionRiesgoCrediticioBCUTO();
		
		result.setDescripcion(calificacionRiesgoCrediticioBCU.getDescripcion());
		result.setOrden(calificacionRiesgoCrediticioBCU.getOrden());
		
		result.setFcre(calificacionRiesgoCrediticioBCU.getFcre());
		result.setFact(calificacionRiesgoCrediticioBCU.getFact());
		result.setId(calificacionRiesgoCrediticioBCU.getId());
		result.setTerm(calificacionRiesgoCrediticioBCU.getTerm());
		result.setUact(calificacionRiesgoCrediticioBCU.getUact());
		result.setUcre(calificacionRiesgoCrediticioBCU.getUcre());
		
		return result;
	}
}