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

import uy.com.amensg.logistica.bean.EstadoVisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.bean.IEstadoVisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.entities.EstadoVisitaPuntoVentaDistribuidor;
import uy.com.amensg.logistica.entities.EstadoVisitaPuntoVentaDistribuidorTO;

@RemoteProxy
public class EstadoVisitaPuntoVentaDistribuidorDWR {

	private IEstadoVisitaPuntoVentaDistribuidorBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoVisitaPuntoVentaDistribuidorBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoVisitaPuntoVentaDistribuidorBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEstadoVisitaPuntoVentaDistribuidorBean) context.lookup(lookupName);
	}
	
	public Collection<EstadoVisitaPuntoVentaDistribuidorTO> list() {
		Collection<EstadoVisitaPuntoVentaDistribuidorTO> result = new LinkedList<EstadoVisitaPuntoVentaDistribuidorTO>();
		
		try {
			IEstadoVisitaPuntoVentaDistribuidorBean iEstadoVisitaPuntoVentaDistribuidorBean = lookupBean();
			
			for (EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor : iEstadoVisitaPuntoVentaDistribuidorBean.list()) {
				result.add(transform(estadoVisitaPuntoVentaDistribuidor));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static EstadoVisitaPuntoVentaDistribuidorTO transform(EstadoVisitaPuntoVentaDistribuidor estadoVisitaPuntoVentaDistribuidor) {
		EstadoVisitaPuntoVentaDistribuidorTO result = new EstadoVisitaPuntoVentaDistribuidorTO();
		
		result.setNombre(estadoVisitaPuntoVentaDistribuidor.getNombre());
		
		result.setFcre(estadoVisitaPuntoVentaDistribuidor.getFcre());
		result.setFact(estadoVisitaPuntoVentaDistribuidor.getFact());
		result.setId(estadoVisitaPuntoVentaDistribuidor.getId());
		result.setTerm(estadoVisitaPuntoVentaDistribuidor.getTerm());
		result.setUact(estadoVisitaPuntoVentaDistribuidor.getUact());
		result.setUact(estadoVisitaPuntoVentaDistribuidor.getUcre());
		
		return result;
	}
	
	public static EstadoVisitaPuntoVentaDistribuidor transform(EstadoVisitaPuntoVentaDistribuidorTO estadoVisitaPuntoVentaDistribuidorTO) {
		EstadoVisitaPuntoVentaDistribuidor result = new EstadoVisitaPuntoVentaDistribuidor();
		
		result.setNombre(estadoVisitaPuntoVentaDistribuidorTO.getNombre());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(estadoVisitaPuntoVentaDistribuidorTO.getFcre());
		result.setFact(date);
		result.setId(estadoVisitaPuntoVentaDistribuidorTO.getId());
		result.setTerm(estadoVisitaPuntoVentaDistribuidorTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(estadoVisitaPuntoVentaDistribuidorTO.getUcre());
		
		return result;
	}
}