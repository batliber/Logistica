package uy.com.amensg.logistica.web.webservices.rest;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import uy.com.amensg.logistica.bean.EntidadVistaBean;
import uy.com.amensg.logistica.bean.IEntidadVistaBean;
import uy.com.amensg.logistica.entities.EntidadVista;
import uy.com.amensg.logistica.entities.EntidadVistaAccion;
import uy.com.amensg.logistica.entities.EntidadVistaCampo;

@Path("/EntidadVistaREST")
public class EntidadVistaREST {

	@GET
	@Path("/getById/{id}")
	@Produces("application/json")
	public EntidadVista getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		EntidadVista result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEntidadVistaBean iEntidadVistaBean = lookupBean();
				
				result = iEntidadVistaBean.getById(id);
				
				for (EntidadVistaCampo entidadVistaCampo : result.getEntidadVistaCampos()) {
					entidadVistaCampo.setEntidadVista(null);
				}
				
				for (EntidadVistaAccion entidadVistaAccion : result.getEntidadVistaAcciones()) {
					entidadVistaAccion.setEntidadVista(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEntidadVistaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EntidadVistaBean.class.getSimpleName();
		String remoteInterfaceName = IEntidadVistaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEntidadVistaBean) context.lookup(lookupName);
	}
}