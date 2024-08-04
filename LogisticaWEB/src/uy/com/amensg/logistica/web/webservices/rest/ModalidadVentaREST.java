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

import uy.com.amensg.logistica.bean.IModalidadVentaBean;
import uy.com.amensg.logistica.bean.ModalidadVentaBean;
import uy.com.amensg.logistica.entities.ModalidadVenta;

@Path("/ModalidadVentaREST")
public class ModalidadVentaREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<ModalidadVenta> list(@Context HttpServletRequest request) {
		Collection<ModalidadVenta> result = new LinkedList<ModalidadVenta>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IModalidadVentaBean iModalidadVentaBean = lookupBean();
				
				result = iModalidadVentaBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IModalidadVentaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ModalidadVentaBean.class.getSimpleName();
		String remoteInterfaceName = IModalidadVentaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IModalidadVentaBean) context.lookup(lookupName);
	}
}