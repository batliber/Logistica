package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ITipoProcesoImportacionBean;
import uy.com.amensg.logistica.bean.TipoProcesoImportacionBean;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;
import uy.com.amensg.logistica.entities.TipoProcesoImportacionTO;

@RemoteProxy
public class TipoProcesoImportacionDWR {

	private ITipoProcesoImportacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoProcesoImportacionBean.class.getSimpleName();
		String remoteInterfaceName = ITipoProcesoImportacionBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITipoProcesoImportacionBean) context.lookup(lookupName);
	}
	
	public Collection<TipoProcesoImportacionTO> list() {
		Collection<TipoProcesoImportacionTO> result = new LinkedList<TipoProcesoImportacionTO>();
		
		try {
			ITipoProcesoImportacionBean iTipoProcesoImportacionBean = lookupBean();
			
			for (TipoProcesoImportacion tipoProcesoImportacion : iTipoProcesoImportacionBean.list()) {
				result.add(transform(tipoProcesoImportacion));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TipoProcesoImportacionTO transform(TipoProcesoImportacion tipoProcesoImportacion) {
		TipoProcesoImportacionTO result = new TipoProcesoImportacionTO();
		
		result.setDescripcion(tipoProcesoImportacion.getDescripcion());
		
		result.setFcre(tipoProcesoImportacion.getFcre());
		result.setFact(tipoProcesoImportacion.getFact());
		result.setId(tipoProcesoImportacion.getId());
		result.setTerm(tipoProcesoImportacion.getTerm());
		result.setUact(tipoProcesoImportacion.getUact());
		result.setUcre(tipoProcesoImportacion.getUcre());
		
		return result;
	}
}