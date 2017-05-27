package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ITipoControlRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.TipoControlRiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticioTO;

@RemoteProxy
public class TipoControlRiesgoCrediticioDWR {

	private ITipoControlRiesgoCrediticioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoControlRiesgoCrediticioBean.class.getSimpleName();
		String remoteInterfaceName = ITipoControlRiesgoCrediticioBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITipoControlRiesgoCrediticioBean) context.lookup(lookupName);
	}
	
	public Collection<TipoControlRiesgoCrediticioTO> list() {
		Collection<TipoControlRiesgoCrediticioTO> result = new LinkedList<TipoControlRiesgoCrediticioTO>();
		
		try {
			ITipoControlRiesgoCrediticioBean iTipoControlRiesgoCrediticioBean = lookupBean();
			
			for (TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio : iTipoControlRiesgoCrediticioBean.list()) {
				result.add(transform(tipoControlRiesgoCrediticio));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TipoControlRiesgoCrediticioTO transform(TipoControlRiesgoCrediticio tipoControlRiesgoCrediticio) {
		TipoControlRiesgoCrediticioTO result = new TipoControlRiesgoCrediticioTO();
		
		result.setDescripcion(tipoControlRiesgoCrediticio.getDescripcion());
		
		result.setFact(tipoControlRiesgoCrediticio.getFact());
		result.setId(tipoControlRiesgoCrediticio.getId());
		result.setTerm(tipoControlRiesgoCrediticio.getTerm());
		result.setUact(tipoControlRiesgoCrediticio.getUact());
		
		return result;
	}
}