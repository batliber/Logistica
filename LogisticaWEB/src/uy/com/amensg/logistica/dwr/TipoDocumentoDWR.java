package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ITipoDocumentoBean;
import uy.com.amensg.logistica.bean.TipoDocumentoBean;
import uy.com.amensg.logistica.entities.TipoDocumento;
import uy.com.amensg.logistica.entities.TipoDocumentoTO;

@RemoteProxy
public class TipoDocumentoDWR {

	private ITipoDocumentoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoDocumentoBean.class.getSimpleName();
		String remoteInterfaceName = ITipoDocumentoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITipoDocumentoBean) context.lookup(lookupName);
	}
	
	public Collection<TipoDocumentoTO> list() {
		Collection<TipoDocumentoTO> result = new LinkedList<TipoDocumentoTO>();
		
		try {
			ITipoDocumentoBean iTipoDocumentoBean = lookupBean();
			
			for (TipoDocumento tipoDocumento : iTipoDocumentoBean.list()) {
				result.add(transform(tipoDocumento));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TipoDocumentoTO transform(TipoDocumento tipoDocumento) {
		TipoDocumentoTO tipoDocumentoTO = new TipoDocumentoTO();
		
		tipoDocumentoTO.setDescripcion(tipoDocumento.getDescripcion());
		
		tipoDocumentoTO.setFact(tipoDocumento.getFact());
		tipoDocumentoTO.setId(tipoDocumento.getId());
		tipoDocumentoTO.setTerm(tipoDocumento.getTerm());
		tipoDocumentoTO.setUact(tipoDocumento.getUact());
		
		return tipoDocumentoTO;
	}
}