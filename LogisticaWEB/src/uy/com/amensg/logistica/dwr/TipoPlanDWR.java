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

import uy.com.amensg.logistica.bean.ITipoPlanBean;
import uy.com.amensg.logistica.bean.TipoPlanBean;
import uy.com.amensg.logistica.entities.TipoPlan;
import uy.com.amensg.logistica.entities.TipoPlanTO;

@RemoteProxy
public class TipoPlanDWR {

	private ITipoPlanBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoPlanBean.class.getSimpleName();
		String remoteInterfaceName = ITipoPlanBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITipoPlanBean) context.lookup(lookupName);
	}
	
	public Collection<TipoPlanTO> list() {
		Collection<TipoPlanTO> result = new LinkedList<TipoPlanTO>();
		
		try {
			ITipoPlanBean iTipoPlanBean = lookupBean();
			
			for (TipoPlan tipoPlan : iTipoPlanBean.list()) {
				result.add(transform(tipoPlan));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TipoPlanTO transform(TipoPlan tipoPlan) {
		TipoPlanTO result = new TipoPlanTO();
		
		result.setDescripcion(tipoPlan.getDescripcion());
		
		result.setFcre(tipoPlan.getFcre());
		result.setFact(tipoPlan.getFact());
		result.setId(tipoPlan.getId());
		result.setTerm(tipoPlan.getTerm());
		result.setUact(tipoPlan.getUact());
		result.setUcre(tipoPlan.getUcre());
		
		return result;
	}

	public static TipoPlan transform(TipoPlanTO tipoPlanTO) {
		TipoPlan result = new TipoPlan();
		
		result.setDescripcion(tipoPlanTO.getDescripcion());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(tipoPlanTO.getFcre());
		result.setFact(date);
		result.setId(tipoPlanTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(tipoPlanTO.getUcre());
		
		return result;
	}
}