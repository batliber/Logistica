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
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.IMotivoCambioPlanBean;
import uy.com.amensg.logistica.bean.MotivoCambioPlanBean;
import uy.com.amensg.logistica.entities.MotivoCambioPlan;

@Path("/MotivoCambioPlanREST")
public class MotivoCambioPlanREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<MotivoCambioPlan> list(@Context HttpServletRequest request) {
		Collection<MotivoCambioPlan> result = new LinkedList<MotivoCambioPlan>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IMotivoCambioPlanBean iMotivoCambioPlanBean = lookupBean();
				
				result = iMotivoCambioPlanBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IMotivoCambioPlanBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = MotivoCambioPlanBean.class.getSimpleName();
		String remoteInterfaceName = IMotivoCambioPlanBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IMotivoCambioPlanBean) context.lookup(lookupName);
	}
}