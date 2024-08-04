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

import uy.com.amensg.logistica.bean.EstadoBean;
import uy.com.amensg.logistica.bean.IEstadoBean;
import uy.com.amensg.logistica.entities.Estado;

@Path("/EstadoREST")
public class EstadoREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<Estado> list(@Context HttpServletRequest request) {
		Collection<Estado> result = new LinkedList<Estado>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEstadoBean iEstadoBean = lookupBean();
				
				result = iEstadoBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEstadoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEstadoBean) context.lookup(lookupName);
	}
}