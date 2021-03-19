package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import uy.com.amensg.logistica.bean.DepartamentoBean;
import uy.com.amensg.logistica.bean.IDepartamentoBean;
import uy.com.amensg.logistica.entities.Departamento;

@Path("/DepartamentoREST")
public class DepartamentoREST {
	
	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<Departamento> list(@Context HttpServletRequest request) {
		Collection<Departamento> result = new LinkedList<Departamento>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IDepartamentoBean iDepartamentoBean = lookupBean();
				
				result = iDepartamentoBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getByNombre/{nombre}")
	@Produces("application/json")
	public Departamento getByNombre(@PathParam("nombre") String nombre, @Context HttpServletRequest request) {
		Departamento result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IDepartamentoBean iDepartamentoBean = lookupBean();
				
				result = iDepartamentoBean.getByNombre(nombre);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private IDepartamentoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = DepartamentoBean.class.getSimpleName();
		String remoteInterfaceName = IDepartamentoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IDepartamentoBean) context.lookup(lookupName);
	}
}