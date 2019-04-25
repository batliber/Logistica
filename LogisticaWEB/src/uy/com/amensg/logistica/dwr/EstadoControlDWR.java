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
		EstadoControlTO result = new EstadoControlTO();
		
		result.setNombre(estadoControl.getNombre());
		
		result.setFcre(estadoControl.getFcre());
		result.setFact(estadoControl.getFact());
		result.setId(estadoControl.getId());
		result.setTerm(estadoControl.getTerm());
		result.setUact(estadoControl.getUact());
		result.setUact(estadoControl.getUcre());
		
		return result;
	}
	
	public static EstadoControl transform(EstadoControlTO estadoControlTO) {
		EstadoControl result = new EstadoControl();
		
		result.setNombre(estadoControlTO.getNombre());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(estadoControlTO.getFcre());
		result.setFact(date);
		result.setId(estadoControlTO.getId());
		result.setTerm(estadoControlTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(estadoControlTO.getUcre());
		
		return result;
	}
}