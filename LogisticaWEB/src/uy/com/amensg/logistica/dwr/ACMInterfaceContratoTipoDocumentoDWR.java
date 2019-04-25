package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ACMInterfaceContratoTipoDocumentoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceContratoTipoDocumentoBean;
import uy.com.amensg.logistica.entities.ACMInterfaceContratoTipoDocumento;
import uy.com.amensg.logistica.entities.ACMInterfaceContratoTipoDocumentoTO;

@RemoteProxy
public class ACMInterfaceContratoTipoDocumentoDWR {

	private IACMInterfaceContratoTipoDocumentoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IACMInterfaceContratoTipoDocumentoBean.class.getName();
		String beanName = ACMInterfaceContratoTipoDocumentoBean.class.getSimpleName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IACMInterfaceContratoTipoDocumentoBean) context.lookup(lookupName);
	}
	
	public Collection<ACMInterfaceContratoTipoDocumentoTO> list() {
		Collection<ACMInterfaceContratoTipoDocumentoTO> result = new LinkedList<ACMInterfaceContratoTipoDocumentoTO>();
		
		try {
			IACMInterfaceContratoTipoDocumentoBean iACMInterfaceContratoTipoDocumentoBean = lookupBean();
			
			for (ACMInterfaceContratoTipoDocumento acmInterfaceContratoTipoDocumento : iACMInterfaceContratoTipoDocumentoBean.list()) {
				result.add(transform(acmInterfaceContratoTipoDocumento));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ACMInterfaceContratoTipoDocumentoTO transform(ACMInterfaceContratoTipoDocumento acmInterfaceContratoTipoDocumento) {
		ACMInterfaceContratoTipoDocumentoTO result = new ACMInterfaceContratoTipoDocumentoTO();
		
		result.setDescripcion(acmInterfaceContratoTipoDocumento.getDescripcion());
		
		result.setFcre(acmInterfaceContratoTipoDocumento.getFcre());
		result.setFact(acmInterfaceContratoTipoDocumento.getFact());
		result.setId(acmInterfaceContratoTipoDocumento.getId());
		result.setTerm(acmInterfaceContratoTipoDocumento.getTerm());
		result.setUact(acmInterfaceContratoTipoDocumento.getUact());
		result.setUcre(acmInterfaceContratoTipoDocumento.getUcre());
		
		return result;
	}
}