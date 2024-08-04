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
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.IResultadoEntregaDistribucionBean;
import uy.com.amensg.logistica.bean.ResultadoEntregaDistribucionBean;
import uy.com.amensg.logistica.entities.ResultadoEntregaDistribucion;

@Path("/ResultadoEntregaDistribucionREST")
public class ResultadoEntregaDistribucionREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<ResultadoEntregaDistribucion> list(@Context HttpServletRequest request) {
		Collection<ResultadoEntregaDistribucion> result = new LinkedList<ResultadoEntregaDistribucion>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IResultadoEntregaDistribucionBean iResultadoEntregaDistribucionBean = lookupBean();
				
				result = iResultadoEntregaDistribucionBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IResultadoEntregaDistribucionBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ResultadoEntregaDistribucionBean.class.getSimpleName();
		String remoteInterfaceName = IResultadoEntregaDistribucionBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IResultadoEntregaDistribucionBean) context.lookup(lookupName);
	}
}