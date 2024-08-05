package uy.com.amensg.logistica.web.webservices.rest;

import java.io.Serializable;
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
import uy.com.amensg.logistica.bean.BaseBean;
import uy.com.amensg.logistica.bean.IBaseBean;
import uy.com.amensg.logistica.entities.RecargaEstado;

@Path("/RecargaEstadoREST")
public class RecargaEstadoREST {

	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<Serializable> list(@Context HttpServletRequest request) {
		Collection<Serializable> result = new LinkedList<Serializable>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IBaseBean<RecargaEstado> iBaseBean = lookupBean();
				
				result = iBaseBean.list(RecargaEstado.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private IBaseBean<RecargaEstado> lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = BaseBean.class.getSimpleName();
		String remoteInterfaceName = IBaseBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IBaseBean<RecargaEstado>) context.lookup(lookupName);
	}
}