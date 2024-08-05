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
import uy.com.amensg.logistica.bean.EstadoVisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.bean.IEstadoVisitaPuntoVentaDistribuidorBean;
import uy.com.amensg.logistica.entities.EstadoVisitaPuntoVentaDistribuidor;

@Path("/EstadoVisitaPuntoVentaDistribuidorREST")
public class EstadoVisitaPuntoVentaDistribuidorREST {
	
	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<EstadoVisitaPuntoVentaDistribuidor> list(@Context HttpServletRequest request) {
		Collection<EstadoVisitaPuntoVentaDistribuidor> result = new LinkedList<EstadoVisitaPuntoVentaDistribuidor>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEstadoVisitaPuntoVentaDistribuidorBean iEstadoVisitaPuntoVentaDistribuidorBean = lookupBean();
				
				result = iEstadoVisitaPuntoVentaDistribuidorBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IEstadoVisitaPuntoVentaDistribuidorBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EstadoVisitaPuntoVentaDistribuidorBean.class.getSimpleName();
		String remoteInterfaceName = IEstadoVisitaPuntoVentaDistribuidorBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEstadoVisitaPuntoVentaDistribuidorBean) context.lookup(lookupName);
	}
}