package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.IUnidadIndexadaBean;
import uy.com.amensg.logistica.bean.UnidadIndexadaBean;
import uy.com.amensg.logistica.entities.UnidadIndexada;

@Path("/UnidadIndexadaREST")
public class UnidadIndexadaREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<UnidadIndexada> list(@Context HttpServletRequest request) {
		Collection<UnidadIndexada> result = new LinkedList<UnidadIndexada>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IUnidadIndexadaBean iUnidadIndexadaBean = lookupBean();
				
				result = iUnidadIndexadaBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public UnidadIndexada getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		UnidadIndexada result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IUnidadIndexadaBean iUnidadIndexadaBean = lookupBean();
				
				result = iUnidadIndexadaBean.getById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public UnidadIndexada add(UnidadIndexada UnidadIndexada, @Context HttpServletRequest request) {
		UnidadIndexada result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IUnidadIndexadaBean iUnidadIndexadaBean = lookupBean();
			
				result = iUnidadIndexadaBean.save(UnidadIndexada);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(UnidadIndexada UnidadIndexada, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IUnidadIndexadaBean iUnidadIndexadaBean = lookupBean();
				
				iUnidadIndexadaBean.remove(UnidadIndexada);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IUnidadIndexadaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = UnidadIndexadaBean.class.getSimpleName();
		String remoteInterfaceName = IUnidadIndexadaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IUnidadIndexadaBean) context.lookup(lookupName);
	}
}