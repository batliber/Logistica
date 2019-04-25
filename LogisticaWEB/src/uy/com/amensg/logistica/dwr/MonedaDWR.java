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

import uy.com.amensg.logistica.bean.IMonedaBean;
import uy.com.amensg.logistica.bean.MonedaBean;
import uy.com.amensg.logistica.entities.Moneda;
import uy.com.amensg.logistica.entities.MonedaTO;

@RemoteProxy
public class MonedaDWR {

	private IMonedaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = MonedaBean.class.getSimpleName();
		String remoteInterfaceName = IMonedaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IMonedaBean) context.lookup(lookupName);
	}
	
	public Collection<MonedaTO> list() {
		Collection<MonedaTO> result = new LinkedList<MonedaTO>();
		
		try {
			IMonedaBean iMonedaBean = lookupBean();
			
			for (Moneda moneda : iMonedaBean.list()) {
				result.add(transform(moneda));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static MonedaTO transform(Moneda moneda) {
		MonedaTO result = new MonedaTO();
		
		result.setCodigoISO(moneda.getCodigoISO());
		result.setNombre(moneda.getNombre());
		result.setSimbolo(moneda.getSimbolo());
		
		result.setFcre(moneda.getFcre());
		result.setFact(moneda.getFact());
		result.setId(moneda.getId());
		result.setTerm(moneda.getTerm());
		result.setUact(moneda.getUact());
		
		return result;
	}

	public static Moneda transform(MonedaTO monedaTO) {
		Moneda result = new Moneda();
		
		result.setCodigoISO(monedaTO.getCodigoISO());
		result.setNombre(monedaTO.getNombre());
		result.setSimbolo(monedaTO.getSimbolo());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(monedaTO.getFcre());
		result.setFact(date);
		result.setId(monedaTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(monedaTO.getUcre());
		
		return result;
	}
}