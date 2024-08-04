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

import uy.com.amensg.logistica.bean.CalificacionRiesgoCrediticioAntelBean;
import uy.com.amensg.logistica.bean.ICalificacionRiesgoCrediticioAntelBean;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioAntel;

@Path("/CalificacionRiesgoCrediticioAntelREST")
public class CalificacionRiesgoCrediticioAntelREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<CalificacionRiesgoCrediticioAntel> list(@Context HttpServletRequest request) {
		Collection<CalificacionRiesgoCrediticioAntel> result = new LinkedList<CalificacionRiesgoCrediticioAntel>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ICalificacionRiesgoCrediticioAntelBean iCalificacionRiesgoCrediticioAntelBean = lookupBean();
				
				result = iCalificacionRiesgoCrediticioAntelBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ICalificacionRiesgoCrediticioAntelBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = CalificacionRiesgoCrediticioAntelBean.class.getSimpleName();
		String remoteInterfaceName = ICalificacionRiesgoCrediticioAntelBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ICalificacionRiesgoCrediticioAntelBean) context.lookup(lookupName);
	}
}