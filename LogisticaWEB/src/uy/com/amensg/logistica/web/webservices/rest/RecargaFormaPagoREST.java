package uy.com.amensg.logistica.webservices.rest;

import java.io.Serializable;
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

import uy.com.amensg.logistica.bean.BaseBean;
import uy.com.amensg.logistica.bean.IBaseBean;
import uy.com.amensg.logistica.entities.RecargaFormaPago;

@Path("/RecargaFormaPagoREST")
public class RecargaFormaPagoREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<Serializable> list(@Context HttpServletRequest request) {
		Collection<Serializable> result = new LinkedList<Serializable>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IBaseBean<RecargaFormaPago> iBaseBean = lookupBean();
				
				result = iBaseBean.list(RecargaFormaPago.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private IBaseBean<RecargaFormaPago> lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = BaseBean.class.getSimpleName();
		String remoteInterfaceName = IBaseBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IBaseBean<RecargaFormaPago>) context.lookup(lookupName);
	}
}