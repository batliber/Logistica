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

import uy.com.amensg.logistica.bean.ITurnoBean;
import uy.com.amensg.logistica.bean.TurnoBean;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.TurnoTO;

@RemoteProxy
public class TurnoDWR {

	private ITurnoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TurnoBean.class.getSimpleName();
		String remoteInterfaceName = ITurnoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITurnoBean) context.lookup(lookupName);
	}
	
	public Collection<TurnoTO> list() {
		Collection<TurnoTO> result = new LinkedList<TurnoTO>();
		
		try {
			ITurnoBean iTurnoBean = lookupBean();
			
			for (Turno turno : iTurnoBean.list()) {
				result.add(transform(turno));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TurnoTO transform(Turno turno) {
		TurnoTO result = new TurnoTO();
		
		result.setNombre(turno.getNombre());
		
		result.setFcre(turno.getFcre());
		result.setFact(turno.getFact());
		result.setId(turno.getId());
		result.setTerm(turno.getTerm());
		result.setUact(turno.getUact());
		result.setUcre(turno.getUcre());
		
		return result;
	}
	
	public static Turno transform(TurnoTO turnoTO) {
		Turno result = new Turno();
		
		result.setNombre(turnoTO.getNombre());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(turnoTO.getFcre());
		result.setFact(date);
		result.setId(turnoTO.getId());
		result.setTerm(turnoTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(turnoTO.getUcre());
		
		return result;
	}
}