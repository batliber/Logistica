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
import uy.com.amensg.logistica.bean.EstadoControlBean;
import uy.com.amensg.logistica.bean.IEstadoControlBean;
import uy.com.amensg.logistica.entities.EstadoControl;

@Path("/EstadoControlREST")
public class EstadoControlREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<EstadoControl> list(@Context HttpServletRequest request) {
		Collection<EstadoControl> result = new LinkedList<EstadoControl>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEstadoControlBean iEstadoControlBean = lookupBean();
				
				result = iEstadoControlBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEstadoControlBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoControlBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoControlBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEstadoControlBean) context.lookup(lookupName);
	}
}