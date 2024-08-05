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
import uy.com.amensg.logistica.bean.EstadoActivacionBean;
import uy.com.amensg.logistica.bean.IEstadoActivacionBean;
import uy.com.amensg.logistica.entities.EstadoActivacion;

@Path("/EstadoActivacionREST")
public class EstadoActivacionREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<EstadoActivacion> list(@Context HttpServletRequest request) {
		Collection<EstadoActivacion> result = new LinkedList<EstadoActivacion>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEstadoActivacionBean iEstadoActivacionBean = lookupBean();
				
				result = iEstadoActivacionBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEstadoActivacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoActivacionBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoActivacionBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEstadoActivacionBean) context.lookup(lookupName);
	}
}