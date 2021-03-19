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

import uy.com.amensg.logistica.bean.EstadoProcesoImportacionBean;
import uy.com.amensg.logistica.bean.IEstadoProcesoImportacionBean;
import uy.com.amensg.logistica.entities.EstadoProcesoImportacion;

@Path("/EstadoProcesoImportacionREST")
public class EstadoProcesoImportacionREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<EstadoProcesoImportacion> list(@Context HttpServletRequest request) {
		Collection<EstadoProcesoImportacion> result = new LinkedList<EstadoProcesoImportacion>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEstadoProcesoImportacionBean iEstadoProcesoImportacionBean = lookupBean();
				
				result = iEstadoProcesoImportacionBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEstadoProcesoImportacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoProcesoImportacionBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoProcesoImportacionBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEstadoProcesoImportacionBean) context.lookup(lookupName);
	}
}