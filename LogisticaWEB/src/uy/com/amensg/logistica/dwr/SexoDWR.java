package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ISexoBean;
import uy.com.amensg.logistica.bean.SexoBean;
import uy.com.amensg.logistica.entities.Sexo;
import uy.com.amensg.logistica.entities.SexoTO;

@RemoteProxy
public class SexoDWR {

	private ISexoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = SexoBean.class.getSimpleName();
		String remoteInterfaceName = ISexoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		Context context = new InitialContext();
		
		return (ISexoBean) context.lookup(lookupName);
	}
	
	public Collection<SexoTO> list() {
		Collection<SexoTO> result = new LinkedList<SexoTO>();
		
		try {
			ISexoBean iSexoBean = lookupBean();
			
			for (Sexo sexo : iSexoBean.list()) {
				result.add(transform(sexo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static SexoTO transform(Sexo sexo) {
		SexoTO result = new SexoTO();
		
		result.setDescripcion(sexo.getDescripcion());
		
		result.setFcre(sexo.getFcre());
		result.setFact(sexo.getFact());
		result.setId(sexo.getId());
		result.setTerm(sexo.getTerm());
		result.setUact(sexo.getUact());
		result.setUcre(sexo.getUcre());
		
		return result;
	}
}