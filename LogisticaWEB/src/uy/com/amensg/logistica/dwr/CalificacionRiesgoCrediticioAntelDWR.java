package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.CalificacionRiesgoCrediticioAntelBean;
import uy.com.amensg.logistica.bean.ICalificacionRiesgoCrediticioAntelBean;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioAntel;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioAntelTO;

@RemoteProxy
public class CalificacionRiesgoCrediticioAntelDWR {

	private ICalificacionRiesgoCrediticioAntelBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = CalificacionRiesgoCrediticioAntelBean.class.getSimpleName();
		String remoteInterfaceName = ICalificacionRiesgoCrediticioAntelBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ICalificacionRiesgoCrediticioAntelBean) context.lookup(lookupName);
	}
	
	public Collection<CalificacionRiesgoCrediticioAntelTO> list() {
		Collection<CalificacionRiesgoCrediticioAntelTO> result = new LinkedList<CalificacionRiesgoCrediticioAntelTO>();
		
		try {
			ICalificacionRiesgoCrediticioAntelBean iCalificacionRiesgoCrediticioAntelBean = lookupBean();
			
			for (CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel : iCalificacionRiesgoCrediticioAntelBean.list()) {
				result.add(transform(calificacionRiesgoCrediticioAntel));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static CalificacionRiesgoCrediticioAntelTO transform(CalificacionRiesgoCrediticioAntel calificacionRiesgoCrediticioAntel) {
		CalificacionRiesgoCrediticioAntelTO result = new CalificacionRiesgoCrediticioAntelTO();
		
		result.setDescripcion(calificacionRiesgoCrediticioAntel.getDescripcion());
		
		result.setFact(calificacionRiesgoCrediticioAntel.getFact());
		result.setId(calificacionRiesgoCrediticioAntel.getId());
		result.setTerm(calificacionRiesgoCrediticioAntel.getTerm());
		result.setUact(calificacionRiesgoCrediticioAntel.getUact());
		
		return result;
	}
}