package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
	
	public static TarjetaCreditoTO transform(TarjetaCredito TarjetaCredito) {
		TarjetaCreditoTO TarjetaCreditoTO = new TarjetaCreditoTO();
		
		TarjetaCreditoTO.setNombre(TarjetaCredito.getNombre());
		
		TarjetaCreditoTO.setFact(TarjetaCredito.getFact());
		TarjetaCreditoTO.setId(TarjetaCredito.getId());
		TarjetaCreditoTO.setTerm(TarjetaCredito.getTerm());
		TarjetaCreditoTO.setUact(TarjetaCredito.getUact());
		
		return TarjetaCreditoTO;
	}
	
	public static TarjetaCredito transform(TarjetaCreditoTO TarjetaCreditoTO) {
		TarjetaCredito TarjetaCredito = new TarjetaCredito();
		
		TarjetaCredito.setNombre(TarjetaCreditoTO.getNombre());
		
		TarjetaCredito.setFact(TarjetaCreditoTO.getFact());
		TarjetaCredito.setId(TarjetaCreditoTO.getId());
		TarjetaCredito.setTerm(TarjetaCreditoTO.getTerm());
		TarjetaCredito.setUact(TarjetaCreditoTO.getUact());
		
		return TarjetaCredito;
	}
}