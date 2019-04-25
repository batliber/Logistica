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

import uy.com.amensg.logistica.bean.ITarjetaCreditoBean;
import uy.com.amensg.logistica.bean.TarjetaCreditoBean;
import uy.com.amensg.logistica.entities.TarjetaCredito;
import uy.com.amensg.logistica.entities.TarjetaCreditoTO;

@RemoteProxy
public class TarjetaCreditoDWR {

	private ITarjetaCreditoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TarjetaCreditoBean.class.getSimpleName();
		String remoteInterfaceName = ITarjetaCreditoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITarjetaCreditoBean) context.lookup(lookupName);
	}
	
	public Collection<TarjetaCreditoTO> list() {
		Collection<TarjetaCreditoTO> result = new LinkedList<TarjetaCreditoTO>();
		
		try {
			ITarjetaCreditoBean iTarjetaCreditoBean = lookupBean();
			
			for (TarjetaCredito TarjetaCredito : iTarjetaCreditoBean.list()) {
				result.add(transform(TarjetaCredito));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TarjetaCreditoTO transform(TarjetaCredito tarjetaCredito) {
		TarjetaCreditoTO result = new TarjetaCreditoTO();
		
		result.setNombre(tarjetaCredito.getNombre());
		
		result.setFcre(tarjetaCredito.getFcre());
		result.setFact(tarjetaCredito.getFact());
		result.setId(tarjetaCredito.getId());
		result.setTerm(tarjetaCredito.getTerm());
		result.setUact(tarjetaCredito.getUact());
		result.setUcre(tarjetaCredito.getUcre());
		
		return result;
	}
	
	public static TarjetaCredito transform(TarjetaCreditoTO tarjetaCreditoTO) {
		TarjetaCredito result = new TarjetaCredito();
		
		result.setNombre(tarjetaCreditoTO.getNombre());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(tarjetaCreditoTO.getFcre());
		result.setFact(date);
		result.setId(tarjetaCreditoTO.getId());
		result.setTerm(tarjetaCreditoTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(tarjetaCreditoTO.getUcre());
		
		return result;
	}
}