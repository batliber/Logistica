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
import uy.com.amensg.logistica.bean.CalificacionRiesgoCrediticioBCUBean;
import uy.com.amensg.logistica.bean.ICalificacionRiesgoCrediticioBCUBean;
import uy.com.amensg.logistica.entities.CalificacionRiesgoCrediticioBCU;

@Path("/CalificacionRiesgoCrediticioBCUREST")
public class CalificacionRiesgoCrediticioBCUREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<CalificacionRiesgoCrediticioBCU> list(@Context HttpServletRequest request) {
		Collection<CalificacionRiesgoCrediticioBCU> result = new LinkedList<CalificacionRiesgoCrediticioBCU>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ICalificacionRiesgoCrediticioBCUBean iCalificacionRiesgoCrediticioBCUBean = lookupBean();
			
				result = iCalificacionRiesgoCrediticioBCUBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ICalificacionRiesgoCrediticioBCUBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = CalificacionRiesgoCrediticioBCUBean.class.getSimpleName();
		String remoteInterfaceName = ICalificacionRiesgoCrediticioBCUBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ICalificacionRiesgoCrediticioBCUBean) context.lookup(lookupName);
	}
}