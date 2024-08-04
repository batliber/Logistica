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

import uy.com.amensg.logistica.bean.ITipoPlanBean;
import uy.com.amensg.logistica.bean.TipoPlanBean;
import uy.com.amensg.logistica.entities.TipoPlan;

@Path("/TipoPlanREST")
public class TipoPlanREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<TipoPlan> list(@Context HttpServletRequest request) {
		Collection<TipoPlan> result = new LinkedList<TipoPlan>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITipoPlanBean iTipoPlanBean = lookupBean();
				
				result = iTipoPlanBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ITipoPlanBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoPlanBean.class.getSimpleName();
		String remoteInterfaceName = ITipoPlanBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ITipoPlanBean) context.lookup(lookupName);
	}
}