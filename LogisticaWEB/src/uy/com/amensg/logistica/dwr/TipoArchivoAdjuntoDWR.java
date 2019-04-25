package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ITipoArchivoAdjuntoBean;
import uy.com.amensg.logistica.bean.TipoArchivoAdjuntoBean;
import uy.com.amensg.logistica.entities.TipoArchivoAdjunto;
import uy.com.amensg.logistica.entities.TipoArchivoAdjuntoTO;

@RemoteProxy
public class TipoArchivoAdjuntoDWR {

	private ITipoArchivoAdjuntoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoArchivoAdjuntoBean.class.getSimpleName();
		String remoteInterfaceName = ITipoArchivoAdjuntoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITipoArchivoAdjuntoBean) context.lookup(lookupName);
	}
	
	public Collection<TipoArchivoAdjuntoTO> list() {
		Collection<TipoArchivoAdjuntoTO> result = new LinkedList<TipoArchivoAdjuntoTO>();
		
		try {
			ITipoArchivoAdjuntoBean iTipoArchivoAdjuntoBean = lookupBean();
			
			for (TipoArchivoAdjunto tipoArchivoAdjunto : iTipoArchivoAdjuntoBean.list()) {
				result.add(transform(tipoArchivoAdjunto));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TipoArchivoAdjuntoTO transform(TipoArchivoAdjunto tipoArchivoAdjunto) {
		TipoArchivoAdjuntoTO result = new TipoArchivoAdjuntoTO();
		
		result.setDescripcion(tipoArchivoAdjunto.getDescripcion());
		
		result.setFcre(tipoArchivoAdjunto.getFcre());
		result.setFact(tipoArchivoAdjunto.getFact());
		result.setId(tipoArchivoAdjunto.getId());
		result.setTerm(tipoArchivoAdjunto.getTerm());
		result.setUact(tipoArchivoAdjunto.getUact());
		result.setUcre(tipoArchivoAdjunto.getUcre());
		
		return result;
	}
}