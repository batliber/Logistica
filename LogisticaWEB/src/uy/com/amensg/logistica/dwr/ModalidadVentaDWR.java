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

import uy.com.amensg.logistica.bean.IModalidadVentaBean;
import uy.com.amensg.logistica.bean.ModalidadVentaBean;
import uy.com.amensg.logistica.entities.ModalidadVenta;
import uy.com.amensg.logistica.entities.ModalidadVentaTO;

@RemoteProxy
public class ModalidadVentaDWR {

	private IModalidadVentaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ModalidadVentaBean.class.getSimpleName();
		String remoteInterfaceName = IModalidadVentaBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IModalidadVentaBean) context.lookup(lookupName);
	}
	
	public Collection<ModalidadVentaTO> list() {
		Collection<ModalidadVentaTO> result = new LinkedList<ModalidadVentaTO>();
		
		try {
			IModalidadVentaBean iModalidadVentaBean = lookupBean();
			
			for (ModalidadVenta modalidadVenta : iModalidadVentaBean.list()) {
				result.add(transform(modalidadVenta));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static ModalidadVentaTO transform(ModalidadVenta modalidadVenta) {
		ModalidadVentaTO result = new ModalidadVentaTO();
		
		result.setDescripcion(modalidadVenta.getDescripcion());
		
		result.setFcre(modalidadVenta.getFcre());
		result.setFact(modalidadVenta.getFact());
		result.setId(modalidadVenta.getId());
		result.setTerm(modalidadVenta.getTerm());
		result.setUact(modalidadVenta.getUact());
		
		return result;
	}

	public static ModalidadVenta transform(ModalidadVentaTO modalidadVentaTO) {
		ModalidadVenta result = new ModalidadVenta();
		
		result.setDescripcion(modalidadVentaTO.getDescripcion());
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFcre(modalidadVentaTO.getFcre());
		result.setFact(date);
		result.setId(modalidadVentaTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		result.setUcre(modalidadVentaTO.getUcre());
		
		return result;
	}
}