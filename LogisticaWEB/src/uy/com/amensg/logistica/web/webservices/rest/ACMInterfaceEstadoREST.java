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

import uy.com.amensg.logistica.bean.ACMInterfaceEstadoBean;
import uy.com.amensg.logistica.bean.IACMInterfaceEstadoBean;
import uy.com.amensg.logistica.entities.ACMInterfaceEstado;

@Path("/ACMInterfaceEstadoREST")
public class ACMInterfaceEstadoREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<ACMInterfaceEstado> list(@Context HttpServletRequest request) {
		Collection<ACMInterfaceEstado> result = new LinkedList<ACMInterfaceEstado>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IACMInterfaceEstadoBean iACMInterfaceEstadoBean = lookupBean();
				
				result = iACMInterfaceEstadoBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IACMInterfaceEstadoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IACMInterfaceEstadoBean.class.getName();
		String beanName = ACMInterfaceEstadoBean.class.getSimpleName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IACMInterfaceEstadoBean) context.lookup(lookupName);
	}
}