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
import uy.com.amensg.logistica.bean.EstadoServicioInfraestructuraBean;
import uy.com.amensg.logistica.bean.IEstadoServicioInfraestructuraBean;
import uy.com.amensg.logistica.entities.EstadoServicioInfraestructura;

@Path("/EstadoServicioInfraestructuraREST")
public class EstadoServicioInfraestructuraREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<EstadoServicioInfraestructura> list(@Context HttpServletRequest request) {
		Collection<EstadoServicioInfraestructura> result = new LinkedList<EstadoServicioInfraestructura>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEstadoServicioInfraestructuraBean iEstadoServicioInfraestructuraBean = lookupBean();
				
				result = iEstadoServicioInfraestructuraBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEstadoServicioInfraestructuraBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoServicioInfraestructuraBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoServicioInfraestructuraBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEstadoServicioInfraestructuraBean) context.lookup(lookupName);
	}
}