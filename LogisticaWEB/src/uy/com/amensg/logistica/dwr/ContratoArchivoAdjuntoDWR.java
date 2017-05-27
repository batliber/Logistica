package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ContratoArchivoAdjuntoBean;
import uy.com.amensg.logistica.bean.IContratoArchivoAdjuntoBean;
import uy.com.amensg.logistica.entities.ContratoArchivoAdjunto;
import uy.com.amensg.logistica.entities.ContratoArchivoAdjuntoTO;

@RemoteProxy
public class ContratoArchivoAdjuntoDWR {

	private IContratoArchivoAdjuntoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoArchivoAdjuntoBean.class.getSimpleName();
		String remoteInterfaceName = IContratoArchivoAdjuntoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IContratoArchivoAdjuntoBean) context.lookup(lookupName);
	}
	
	public Collection<ContratoArchivoAdjuntoTO> listByContratoId(Long id) {
		Collection<ContratoArchivoAdjuntoTO> result = new LinkedList<ContratoArchivoAdjuntoTO>();
		
		try {
			IContratoArchivoAdjuntoBean iContratoArchivoAdjuntoBean = lookupBean();
			
			for (ContratoArchivoAdjunto contratoArchivoAdjunto : iContratoArchivoAdjuntoBean.listByContratoId(id)) {
				result.add(transform(contratoArchivoAdjunto));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ContratoArchivoAdjuntoTO transform(ContratoArchivoAdjunto contratoArchivoAdjunto) {
		ContratoArchivoAdjuntoTO result = new ContratoArchivoAdjuntoTO();
		
		result.setFechaSubida(contratoArchivoAdjunto.getFechaSubida());
		result.setUrl(contratoArchivoAdjunto.getUrl());
		
		result.setFact(contratoArchivoAdjunto.getFact());
		result.setId(contratoArchivoAdjunto.getId());
		result.setTerm(contratoArchivoAdjunto.getTerm());
		result.setUact(contratoArchivoAdjunto.getUact());
		
		return result;
	}
}