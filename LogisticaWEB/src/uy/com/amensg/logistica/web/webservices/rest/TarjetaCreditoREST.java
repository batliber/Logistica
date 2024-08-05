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
import uy.com.amensg.logistica.bean.ITarjetaCreditoBean;
import uy.com.amensg.logistica.bean.TarjetaCreditoBean;
import uy.com.amensg.logistica.entities.TarjetaCredito;

@Path("/TarjetaCreditoREST")
public class TarjetaCreditoREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<TarjetaCredito> list(@Context HttpServletRequest request) {
		Collection<TarjetaCredito> result = new LinkedList<TarjetaCredito>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITarjetaCreditoBean iTarjetaCreditoBean = lookupBean();
				
				result = iTarjetaCreditoBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ITarjetaCreditoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TarjetaCreditoBean.class.getSimpleName();
		String remoteInterfaceName = ITarjetaCreditoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (ITarjetaCreditoBean) context.lookup(lookupName);
	}
}