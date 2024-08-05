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