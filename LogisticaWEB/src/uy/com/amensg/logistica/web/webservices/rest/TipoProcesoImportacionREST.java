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
import uy.com.amensg.logistica.bean.ITipoProcesoImportacionBean;
import uy.com.amensg.logistica.bean.TipoProcesoImportacionBean;
import uy.com.amensg.logistica.entities.TipoProcesoImportacion;

@Path("/TipoProcesoImportacionREST")
public class TipoProcesoImportacionREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<TipoProcesoImportacion> list(@Context HttpServletRequest request) {
		Collection<TipoProcesoImportacion> result = new LinkedList<TipoProcesoImportacion>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITipoProcesoImportacionBean iTipoProcesoImportacionBean = lookupBean();
				
				result = iTipoProcesoImportacionBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private ITipoProcesoImportacionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoProcesoImportacionBean.class.getSimpleName();
		String remoteInterfaceName = ITipoProcesoImportacionBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ITipoProcesoImportacionBean) context.lookup(lookupName);
	}
}