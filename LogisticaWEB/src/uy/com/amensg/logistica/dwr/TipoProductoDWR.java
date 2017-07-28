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

import uy.com.amensg.logistica.bean.ITipoProductoBean;
import uy.com.amensg.logistica.bean.TipoProductoBean;
import uy.com.amensg.logistica.entities.FormaPagoCuota;
import uy.com.amensg.logistica.entities.TipoProducto;
import uy.com.amensg.logistica.entities.TipoProductoTO;

@RemoteProxy
public class TipoProductoDWR {

	private ITipoProductoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoProductoBean.class.getSimpleName();
		String remoteInterfaceName = ITipoProductoBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ITipoProductoBean) context.lookup(lookupName);
	}
	
	public Collection<TipoProductoTO> list() {
		Collection<TipoProductoTO> result = new LinkedList<TipoProductoTO>();
		
		try {
			ITipoProductoBean iTipoProductoBean = lookupBean();
			
			for (TipoProducto tipoProducto : iTipoProductoBean.list()) {
				result.add(transform(tipoProducto));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static TipoProductoTO transform(TipoProducto tipoProducto) {
		TipoProductoTO result = new TipoProductoTO();
		
		result.setDescripcion(tipoProducto.getDescripcion());
		
		if (tipoProducto.getFormaPagoCuota() != null) {
			result.setFormaPagoCuota(FormaPagoCuotaDWR.transform(tipoProducto.getFormaPagoCuota()));
		}
		
		result.setFact(tipoProducto.getFact());
		result.setId(tipoProducto.getId());
		result.setTerm(tipoProducto.getTerm());
		result.setUact(tipoProducto.getUact());
		
		return result;
	}

	public static TipoProducto transform(TipoProductoTO tipoProductoTO) {
		TipoProducto result = new TipoProducto();
		
		result.setDescripcion(tipoProductoTO.getDescripcion());
		
		if (tipoProductoTO.getFormaPagoCuota() != null) {
			FormaPagoCuota formaPagoCuota = new FormaPagoCuota();
			formaPagoCuota.setId(tipoProductoTO.getFormaPagoCuota().getId());
			
			result.setFormaPagoCuota(formaPagoCuota);
		}
		
		Date date = GregorianCalendar.getInstance().getTime();
		
		result.setFact(date);
		result.setId(tipoProductoTO.getId());
		result.setTerm(new Long(1));
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		result.setUact(usuarioId);
		
		return result;
	}
}