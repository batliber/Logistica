package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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