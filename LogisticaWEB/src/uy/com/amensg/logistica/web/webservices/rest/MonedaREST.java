package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.IMonedaBean;
import uy.com.amensg.logistica.bean.MonedaBean;
import uy.com.amensg.logistica.entities.Moneda;

@Path("/MonedaREST")
public class MonedaREST {
	
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<Moneda> list(@Context HttpServletRequest request) {
		Collection<Moneda> result = new LinkedList<Moneda>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IMonedaBean iMonedaBean = lookupBean();
				
				result = iMonedaBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IMonedaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = MonedaBean.class.getSimpleName();
		String remoteInterfaceName = IMonedaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IMonedaBean) context.lookup(lookupName);
	}
}