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
import uy.com.amensg.logistica.bean.IRolJerarquiaBean;
import uy.com.amensg.logistica.bean.RolJerarquiaBean;
import uy.com.amensg.logistica.entities.RolJerarquia;

@Path("/RolJerarquiaREST")
public class RolJerarquiaREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<RolJerarquia> list(@Context HttpServletRequest request) {
		Collection<RolJerarquia> result = new LinkedList<RolJerarquia>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IRolJerarquiaBean iRolJerarquiaBean = lookupBean();
				
				result = iRolJerarquiaBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IRolJerarquiaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RolJerarquiaBean.class.getSimpleName();
		String remoteInterfaceName = IRolJerarquiaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IRolJerarquiaBean) context.lookup(lookupName);
	}
}