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

import uy.com.amensg.logistica.bean.ITipoArchivoAdjuntoBean;
import uy.com.amensg.logistica.bean.TipoArchivoAdjuntoBean;
import uy.com.amensg.logistica.entities.TipoArchivoAdjunto;

@Path("/TipoArchivoAdjuntoREST")
public class TipoArchivoAdjuntoREST {
	
	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<TipoArchivoAdjunto> list(@Context HttpServletRequest request) {
		Collection<TipoArchivoAdjunto> result = new LinkedList<TipoArchivoAdjunto>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITipoArchivoAdjuntoBean iTipoArchivoAdjuntoBean = lookupBean();
				
				result = iTipoArchivoAdjuntoBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ITipoArchivoAdjuntoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoArchivoAdjuntoBean.class.getSimpleName();
		String remoteInterfaceName = ITipoArchivoAdjuntoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ITipoArchivoAdjuntoBean) context.lookup(lookupName);
	}
}