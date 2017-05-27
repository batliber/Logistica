package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.EstadoRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.IEstadoRiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticio;
import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticioTO;

@RemoteProxy
public class EstadoRiesgoCrediticioDWR {

	private IEstadoRiesgoCrediticioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoRiesgoCrediticioBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoRiesgoCrediticioBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEstadoRiesgoCrediticioBean) context.lookup(lookupName);
	}
	
	public Collection<EstadoRiesgoCrediticioTO> list() {
		Collection<EstadoRiesgoCrediticioTO> result = new LinkedList<EstadoRiesgoCrediticioTO>();
		
		try {
			IEstadoRiesgoCrediticioBean iEstadoRiesgoCrediticioBean = lookupBean();
			
			for (EstadoRiesgoCrediticio estadoRiesgoCrediticio : iEstadoRiesgoCrediticioBean.list()) {
				result.add(transform(estadoRiesgoCrediticio));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static EstadoRiesgoCrediticioTO transform(EstadoRiesgoCrediticio estadoRiesgoCrediticio) {
		EstadoRiesgoCrediticioTO result = new EstadoRiesgoCrediticioTO();
		
		result.setNombre(estadoRiesgoCrediticio.getNombre());
		
		result.setFact(estadoRiesgoCrediticio.getFact());
		result.setId(estadoRiesgoCrediticio.getId());
		result.setTerm(estadoRiesgoCrediticio.getTerm());
		result.setUact(estadoRiesgoCrediticio.getUact());
		
		return result;
	}
}