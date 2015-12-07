package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ITurnoBean;
import uy.com.amensg.logistica.bean.TurnoBean;
import uy.com.amensg.logistica.entities.Turno;
import uy.com.amensg.logistica.entities.TurnoTO;

@RemoteProxy
public class TurnoDWR {

	private ITurnoBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = TurnoBean.class.getSimpleName();
		String remoteInterfaceName = ITurnoBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
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
		TurnoTO turnoTO = new TurnoTO();
		
		turnoTO.setNombre(turno.getNombre());
		
		turnoTO.setFact(turno.getFact());
		turnoTO.setId(turno.getId());
		turnoTO.setTerm(turno.getTerm());
		turnoTO.setUact(turno.getUact());
		
		return turnoTO;
	}
	
	public static Turno transform(TurnoTO turnoTO) {
		Turno turno = new Turno();
		
		turno.setNombre(turnoTO.getNombre());
		
		turno.setFact(turnoTO.getFact());
		turno.setId(turnoTO.getId());
		turno.setTerm(turnoTO.getTerm());
		turno.setUact(turnoTO.getUact());
		
		return turno;
	}
}