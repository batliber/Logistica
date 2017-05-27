package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.FormaPagoBean;
import uy.com.amensg.logistica.bean.IFormaPagoBean;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.FormaPagoTO;

@RemoteProxy
public class FormaPagoDWR {

	private IFormaPagoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = FormaPagoBean.class.getSimpleName();
		String remoteInterfaceName = IFormaPagoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IFormaPagoBean) context.lookup(lookupName);
	}
	
	public Collection<FormaPagoTO> list() {
		Collection<FormaPagoTO> result = new LinkedList<FormaPagoTO>();
		
		try {
			IFormaPagoBean iFormaPagoBean = lookupBean();
			
			for (FormaPago FormaPago : iFormaPagoBean.list()) {
				result.add(transform(FormaPago));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static FormaPagoTO transform(FormaPago formaPago) {
		FormaPagoTO result = new FormaPagoTO();
		
		result.setDescripcion(formaPago.getDescripcion());
		result.setOrden(formaPago.getOrden());
		
		result.setFact(formaPago.getFact());
		result.setId(formaPago.getId());
		result.setTerm(formaPago.getTerm());
		result.setUact(formaPago.getUact());
		
		return result;
	}
	
	public static FormaPago transform(FormaPagoTO formaPagoTO) {
		FormaPago result = new FormaPago();
		
		result.setDescripcion(formaPagoTO.getDescripcion());
		result.setOrden(formaPagoTO.getOrden());
		
		result.setFact(formaPagoTO.getFact());
		result.setId(formaPagoTO.getId());
		result.setTerm(formaPagoTO.getTerm());
		result.setUact(formaPagoTO.getUact());
		
		return result;
	}
}