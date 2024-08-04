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

import uy.com.amensg.logistica.bean.EstadoANTELBean;
import uy.com.amensg.logistica.bean.IEstadoANTELBean;
import uy.com.amensg.logistica.entities.EstadoANTEL;

@Path("/EstadoANTELREST")
public class EstadoANTELREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<EstadoANTEL> list(@Context HttpServletRequest request) {
		Collection<EstadoANTEL> result = new LinkedList<EstadoANTEL>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEstadoANTELBean iEstadoANTELBean = lookupBean();
				
				result = iEstadoANTELBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEstadoANTELBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoANTELBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoANTELBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEstadoANTELBean) context.lookup(lookupName);
	}
}