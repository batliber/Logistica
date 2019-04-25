package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ITipoTasaInteresEfectivaAnualBean;
import uy.com.amensg.logistica.bean.TipoTasaInteresEfectivaAnualBean;
import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnual;
import uy.com.amensg.logistica.entities.TipoTasaInteresEfectivaAnualTO;

@RemoteProxy
public class TipoTasaInteresEfectivaAnualDWR {

	private ITipoTasaInteresEfectivaAnualBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoTasaInteresEfectivaAnualBean.class.getSimpleName();
		String remoteInterfaceName = ITipoTasaInteresEfectivaAnualBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITipoTasaInteresEfectivaAnualBean) context.lookup(lookupName);
	}
	
	public Collection<TipoTasaInteresEfectivaAnualTO> list() {
		Collection<TipoTasaInteresEfectivaAnualTO> result = new LinkedList<TipoTasaInteresEfectivaAnualTO>();
		
		try {
			ITipoTasaInteresEfectivaAnualBean iTipoTasaInteresEfectivaAnualBean = lookupBean();
			
			for (TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual : iTipoTasaInteresEfectivaAnualBean.list()) {
				result.add(transform(tipoTasaInteresEfectivaAnual));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TipoTasaInteresEfectivaAnualTO transform(TipoTasaInteresEfectivaAnual tipoTasaInteresEfectivaAnual) {
		TipoTasaInteresEfectivaAnualTO result = new TipoTasaInteresEfectivaAnualTO();
		
		result.setDescripcion(tipoTasaInteresEfectivaAnual.getDescripcion());
		
		result.setFcre(tipoTasaInteresEfectivaAnual.getFcre());
		result.setFact(tipoTasaInteresEfectivaAnual.getFact());
		result.setId(tipoTasaInteresEfectivaAnual.getId());
		result.setTerm(tipoTasaInteresEfectivaAnual.getTerm());
		result.setUact(tipoTasaInteresEfectivaAnual.getUact());
		result.setUcre(tipoTasaInteresEfectivaAnual.getUcre());
		
		return result;
	}
}