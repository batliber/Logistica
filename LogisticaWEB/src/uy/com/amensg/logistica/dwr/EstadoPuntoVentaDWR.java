package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.EstadoPuntoVentaBean;
import uy.com.amensg.logistica.bean.IEstadoPuntoVentaBean;
import uy.com.amensg.logistica.entities.EstadoPuntoVenta;
import uy.com.amensg.logistica.entities.EstadoPuntoVentaTO;

@RemoteProxy
public class EstadoPuntoVentaDWR {

	private IEstadoPuntoVentaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoPuntoVentaBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoPuntoVentaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IEstadoPuntoVentaBean) context.lookup(lookupName);
	}
	
	public Collection<EstadoPuntoVentaTO> list() {
		Collection<EstadoPuntoVentaTO> result = new LinkedList<EstadoPuntoVentaTO>();
		
		try {
			IEstadoPuntoVentaBean iEstadoPuntoVentaBean = lookupBean();
			
			for (EstadoPuntoVenta estadoPuntoVenta : iEstadoPuntoVentaBean.list()) {
				result.add(transform(estadoPuntoVenta));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static EstadoPuntoVentaTO transform(EstadoPuntoVenta estadoPuntoVenta) {
		EstadoPuntoVentaTO result = new EstadoPuntoVentaTO();
		
		result.setNombre(estadoPuntoVenta.getNombre());
		
		result.setFcre(estadoPuntoVenta.getFcre());
		result.setFact(estadoPuntoVenta.getFact());
		result.setId(estadoPuntoVenta.getId());
		result.setTerm(estadoPuntoVenta.getTerm());
		result.setUact(estadoPuntoVenta.getUact());
		result.setUcre(estadoPuntoVenta.getUcre());
		
		return result;
	}
}