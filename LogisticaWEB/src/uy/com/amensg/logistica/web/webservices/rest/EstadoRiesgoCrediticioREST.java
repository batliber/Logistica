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
import uy.com.amensg.logistica.bean.EstadoRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.IEstadoRiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.EstadoRiesgoCrediticio;

@Path("/EstadoRiesgoCrediticioREST")
public class EstadoRiesgoCrediticioREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<EstadoRiesgoCrediticio> list(@Context HttpServletRequest request) {
		Collection<EstadoRiesgoCrediticio> result = new LinkedList<EstadoRiesgoCrediticio>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEstadoRiesgoCrediticioBean iEstadoRiesgoCrediticioBean = lookupBean();
				
				result = iEstadoRiesgoCrediticioBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEstadoRiesgoCrediticioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoRiesgoCrediticioBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoRiesgoCrediticioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEstadoRiesgoCrediticioBean) context.lookup(lookupName);
	}
}