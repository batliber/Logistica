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
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.ContratoANTELTipoOperacionModeloBean;
import uy.com.amensg.logistica.bean.IContratoANTELTipoOperacionModeloBean;
import uy.com.amensg.logistica.entities.ContratoANTELTipoOperacionModelo;

@Path("/ContratoANTELTipoOperacionModeloREST")
public class ContratoANTELTipoOperacionModeloREST {

	@GET
	@Path("/getByTipoOperacion/{tipoOperacion}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ContratoANTELTipoOperacionModelo getByTipoOperacion(@PathParam("tipoOperacion") Long tipoOperacion, @Context HttpServletRequest request) {
		ContratoANTELTipoOperacionModelo result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IContratoANTELTipoOperacionModeloBean iContratoANTELTipoOperacionModeloBean = lookupBean();
				
				result = iContratoANTELTipoOperacionModeloBean.getByTipoOperacion(tipoOperacion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IContratoANTELTipoOperacionModeloBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ContratoANTELTipoOperacionModeloBean.class.getSimpleName();
		String remoteInterfaceName = IContratoANTELTipoOperacionModeloBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IContratoANTELTipoOperacionModeloBean) context.lookup(lookupName);
	}
}