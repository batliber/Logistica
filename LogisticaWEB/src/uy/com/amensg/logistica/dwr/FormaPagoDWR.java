package uy.com.amensg.logistica.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
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
			
			for (FormaPago formaPago : iFormaPagoBean.list()) {
				result.add(transform(formaPago));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public FormaPagoTO getById(Long id) {
		FormaPagoTO result = null;
		
		try {
			IFormaPagoBean iFormaPagoBean = lookupBean();
			
			result = transform(iFormaPagoBean.getById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static FormaPagoTO transform(FormaPago formaPago) {
		FormaPagoTO result = new FormaPagoTO();
		
		result.setDescripcion(formaPago.getDescripcion());
		result.setOrden(formaPago.getOrden());
		
		result.setFcre(formaPago.getFcre());
		result.setFact(formaPago.getFact());
		result.setId(formaPago.getId());
		result.setTerm(formaPago.getTerm());
		result.setUact(formaPago.getUact());
		result.setUcre(formaPago.getUcre());
		
		return result;
	}
	
	public static FormaPago transform(FormaPagoTO formaPagoTO) {
		FormaPago result = new FormaPago();
		
		result.setDescripcion(formaPagoTO.getDescripcion());
		result.setOrden(formaPagoTO.getOrden());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(formaPagoTO.getFcre());
		result.setFact(date);
		result.setId(formaPagoTO.getId());
		result.setTerm(formaPagoTO.getTerm());
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(formaPagoTO.getUcre());
		
		return result;
	}
}