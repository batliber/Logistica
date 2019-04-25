package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.EstadoActivacionBean;
import uy.com.amensg.logistica.bean.IEstadoActivacionBean;
import uy.com.amensg.logistica.entities.EstadoActivacion;
import uy.com.amensg.logistica.entities.EstadoActivacionTO;

@RemoteProxy
public class EstadoActivacionDWR {

	private IEstadoActivacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoActivacionBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoActivacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEstadoActivacionBean) context.lookup(lookupName);
	}
	
	public Collection<EstadoActivacionTO> list() {
		Collection<EstadoActivacionTO> result = new LinkedList<EstadoActivacionTO>();
		
		try {
			IEstadoActivacionBean iEstadoActivacionBean = lookupBean();
			
			for (EstadoActivacion estadoActivacion : iEstadoActivacionBean.list()) {
				result.add(transform(estadoActivacion));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static EstadoActivacionTO transform(EstadoActivacion estadoActivacion) {
		EstadoActivacionTO result = new EstadoActivacionTO();
		
		result.setNombre(estadoActivacion.getNombre());
		
		result.setFcre(estadoActivacion.getFcre());
		result.setFact(estadoActivacion.getFact());
		result.setId(estadoActivacion.getId());
		result.setTerm(estadoActivacion.getTerm());
		result.setUact(estadoActivacion.getUact());
		result.setUact(estadoActivacion.getUcre());
		
		return result;
	}
	
	public static EstadoActivacion transform(EstadoActivacionTO estadoActivacionTO) {
		EstadoActivacion result = new EstadoActivacion();
		
		result.setNombre(estadoActivacionTO.getNombre());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(estadoActivacionTO.getFcre());
		result.setFact(date);
		result.setId(estadoActivacionTO.getId());
		result.setTerm(estadoActivacionTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(estadoActivacionTO.getUcre());
		
		return result;
	}
}