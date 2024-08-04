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

import uy.com.amensg.logistica.bean.ITipoActivacionBean;
import uy.com.amensg.logistica.bean.TipoActivacionBean;
import uy.com.amensg.logistica.entities.TipoActivacion;

@Path("/TipoActivacionREST")
public class TipoActivacionREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<TipoActivacion> list(@Context HttpServletRequest request) {
		Collection<TipoActivacion> result = new LinkedList<TipoActivacion>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITipoActivacionBean iTipoActivacionBean = lookupBean();
				
				result = iTipoActivacionBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ITipoActivacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoActivacionBean.class.getSimpleName();
		String remoteInterfaceName = ITipoActivacionBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ITipoActivacionBean) context.lookup(lookupName);
	}
}