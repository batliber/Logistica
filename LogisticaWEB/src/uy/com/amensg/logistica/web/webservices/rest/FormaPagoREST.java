package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

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
import uy.com.amensg.logistica.bean.FormaPagoBean;
import uy.com.amensg.logistica.bean.IFormaPagoBean;
import uy.com.amensg.logistica.entities.FormaPago;

@Path("/FormaPagoREST")
public class FormaPagoREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<FormaPago> list(@Context HttpServletRequest request) {
		Collection<FormaPago> result = new LinkedList<FormaPago>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IFormaPagoBean iFormaPagoBean = lookupBean();
				
				result = iFormaPagoBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public FormaPago getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		FormaPago result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IFormaPagoBean iFormaPagoBean = lookupBean();
				
				result = iFormaPagoBean.getById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IFormaPagoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = FormaPagoBean.class.getSimpleName();
		String remoteInterfaceName = IFormaPagoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IFormaPagoBean) context.lookup(lookupName);
	}
}