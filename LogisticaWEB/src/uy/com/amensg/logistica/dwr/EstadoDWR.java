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
		EstadoTO result = new EstadoTO();
		
		result.setNombre(estado.getNombre());
		
		result.setFcre(estado.getFcre());
		result.setFact(estado.getFact());
		result.setId(estado.getId());
		result.setTerm(estado.getTerm());
		result.setUact(estado.getUact());
		result.setUact(estado.getUcre());
		
		return result;
	}
	
	public static Estado transform(EstadoTO estadoTO) {
		Estado result = new Estado();
		
		result.setNombre(estadoTO.getNombre());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(estadoTO.getFcre());
		result.setFact(date);
		result.setId(estadoTO.getId());
		result.setTerm(estadoTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(estadoTO.getUcre());
		
		return result;
	}
}