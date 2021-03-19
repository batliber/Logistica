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

import uy.com.amensg.logistica.bean.ISeguridadTipoEventoBean;
import uy.com.amensg.logistica.bean.SeguridadTipoEventoBean;
import uy.com.amensg.logistica.entities.SeguridadTipoEvento;

@Path("/SeguridadTipoEventoREST")
public class SeguridadTipoEventoREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<SeguridadTipoEvento> list(@Context HttpServletRequest request) {
		Collection<SeguridadTipoEvento> result = new LinkedList<SeguridadTipoEvento>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ISeguridadTipoEventoBean iSeguridadTipoEventoBean = lookupBean();
				
				result = iSeguridadTipoEventoBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ISeguridadTipoEventoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = SeguridadTipoEventoBean.class.getSimpleName();
		String remoteInterfaceName = ISeguridadTipoEventoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ISeguridadTipoEventoBean) context.lookup(lookupName);
	}
}