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
import uy.com.amensg.logistica.bean.ITipoControlBean;
import uy.com.amensg.logistica.bean.TipoControlBean;
import uy.com.amensg.logistica.entities.TipoControl;

@Path("/TipoControlREST")
public class TipoControlREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<TipoControl> list(@Context HttpServletRequest request) {
		Collection<TipoControl> result = new LinkedList<TipoControl>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITipoControlBean iTipoControlBean = lookupBean();
				
				result = iTipoControlBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ITipoControlBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoControlBean.class.getSimpleName();
		String remoteInterfaceName = ITipoControlBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ITipoControlBean) context.lookup(lookupName);
	}
}