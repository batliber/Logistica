package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ITipoActivacionBean;
import uy.com.amensg.logistica.bean.TipoActivacionBean;
import uy.com.amensg.logistica.entities.TipoActivacion;
import uy.com.amensg.logistica.entities.TipoActivacionTO;

@RemoteProxy
public class TipoActivacionDWR {

	private ITipoActivacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoActivacionBean.class.getSimpleName();
		String remoteInterfaceName = ITipoActivacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITipoActivacionBean) context.lookup(lookupName);
	}
	
	public Collection<TipoActivacionTO> list() {
		Collection<TipoActivacionTO> result = new LinkedList<TipoActivacionTO>();
		
		try {
			ITipoActivacionBean iTipoActivacionBean = lookupBean();
			
			for (TipoActivacion tipoActivacion : iTipoActivacionBean.list()) {
				result.add(transform(tipoActivacion));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TipoActivacionTO transform(TipoActivacion tipoActivacion) {
		TipoActivacionTO tipoActivacionTO = new TipoActivacionTO();
		
		tipoActivacionTO.setDescripcion(tipoActivacion.getDescripcion());
		
		tipoActivacionTO.setFact(tipoActivacion.getFact());
		tipoActivacionTO.setId(tipoActivacion.getId());
		tipoActivacionTO.setTerm(tipoActivacion.getTerm());
		tipoActivacionTO.setUact(tipoActivacion.getUact());
		
		return tipoActivacionTO;
	}
}