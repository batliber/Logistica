package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ITipoControlBean;
import uy.com.amensg.logistica.bean.TipoControlBean;
import uy.com.amensg.logistica.entities.TipoControl;
import uy.com.amensg.logistica.entities.TipoControlTO;

@RemoteProxy
public class TipoControlDWR {

	private ITipoControlBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoControlBean.class.getSimpleName();
		String remoteInterfaceName = ITipoControlBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITipoControlBean) context.lookup(lookupName);
	}
	
	public Collection<TipoControlTO> list() {
		Collection<TipoControlTO> result = new LinkedList<TipoControlTO>();
		
		try {
			ITipoControlBean iTipoControlBean = lookupBean();
			
			for (TipoControl tipoControl : iTipoControlBean.list()) {
				result.add(transform(tipoControl));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TipoControlTO transform(TipoControl tipoControl) {
		TipoControlTO tipoControlTO = new TipoControlTO();
		
		tipoControlTO.setDescripcion(tipoControl.getDescripcion());
		
		tipoControlTO.setFact(tipoControl.getFact());
		tipoControlTO.setId(tipoControl.getId());
		tipoControlTO.setTerm(tipoControl.getTerm());
		tipoControlTO.setUact(tipoControl.getUact());
		
		return tipoControlTO;
	}
}