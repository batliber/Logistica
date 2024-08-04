package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
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

import uy.com.amensg.logistica.bean.EmpresaBean;
import uy.com.amensg.logistica.bean.IEmpresaBean;
import uy.com.amensg.logistica.entities.Empresa;
import uy.com.amensg.logistica.entities.FormaPago;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Path("/EmpresaREST")
public class EmpresaREST {

	@GET
	@Path("/listEmpresaUsuarioContratosById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<Usuario> listEmpresaUsuarioContratosById(
		@PathParam("id") Long id, @Context HttpServletRequest request
	) {
		Collection<Usuario> result = new LinkedList<Usuario>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEmpresaBean iEmpresaBean = lookupBean();
				
				result = iEmpresaBean.listEmpresaUsuarioContratosById(id);
				
				for (Usuario usuario : result) {
					usuario.setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listFormasPagoById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<FormaPago> listFormasPagoById(
		@PathParam("id") Long id, @Context HttpServletRequest request
	) {
		Collection<FormaPago> result = new LinkedList<FormaPago>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEmpresaBean iEmpresaBean = lookupBean();
				
				result = iEmpresaBean.listFormasPagoById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Empresa getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Empresa result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEmpresaBean iEmpresaBean = lookupBean();
				
				result = iEmpresaBean.getById(id, true);
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
	public Empresa add(Empresa empresa, @Context HttpServletRequest request) {
		Empresa result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaBean iEmpresaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				empresa.setFact(hoy);
				empresa.setFcre(hoy);
				empresa.setTerm(Long.valueOf(1));
				empresa.setUact(loggedUsuarioId);
				empresa.setUcre(loggedUsuarioId);
				
				result = iEmpresaBean.save(empresa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/update")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void update(Empresa empresa, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaBean iEmpresaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				empresa.setFact(hoy);
				empresa.setTerm(Long.valueOf(1));
				empresa.setUact(loggedUsuarioId);
				
				iEmpresaBean.update(empresa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(Empresa empresa, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaBean iEmpresaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				empresa.setFact(hoy);
				empresa.setTerm(Long.valueOf(1));
				empresa.setUact(loggedUsuarioId);
				
				iEmpresaBean.remove(empresa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IEmpresaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EmpresaBean.class.getSimpleName();
		String remoteInterfaceName = IEmpresaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEmpresaBean) context.lookup(lookupName);
	}
}