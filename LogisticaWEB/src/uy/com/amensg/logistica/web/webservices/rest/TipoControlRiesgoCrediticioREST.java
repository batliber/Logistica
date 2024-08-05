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
import uy.com.amensg.logistica.bean.ITipoControlRiesgoCrediticioBean;
import uy.com.amensg.logistica.bean.TipoControlRiesgoCrediticioBean;
import uy.com.amensg.logistica.entities.TipoControlRiesgoCrediticio;

@Path("/TipoControlRiesgoCrediticioREST")
public class TipoControlRiesgoCrediticioREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<TipoControlRiesgoCrediticio> list(@Context HttpServletRequest request) {
		Collection<TipoControlRiesgoCrediticio> result = new LinkedList<TipoControlRiesgoCrediticio>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITipoControlRiesgoCrediticioBean iTipoControlRiesgoCrediticioBean = lookupBean();
				
				result = iTipoControlRiesgoCrediticioBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ITipoControlRiesgoCrediticioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoControlRiesgoCrediticioBean.class.getSimpleName();
		String remoteInterfaceName = ITipoControlRiesgoCrediticioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ITipoControlRiesgoCrediticioBean) context.lookup(lookupName);
	}
}