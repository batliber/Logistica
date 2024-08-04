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

import uy.com.amensg.logistica.bean.EmpresaServiceBean;
import uy.com.amensg.logistica.bean.IEmpresaServiceBean;
import uy.com.amensg.logistica.entities.EmpresaService;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Path("/EmpresaServiceREST")
public class EmpresaServiceREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<EmpresaService> list(@Context HttpServletRequest request) {
		Collection<EmpresaService> result = new LinkedList<EmpresaService>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
			
				result = iEmpresaServiceBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/listContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public MetadataConsultaResultado listContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		MetadataConsultaResultado result = new MetadataConsultaResultado();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
				
				result = iEmpresaServiceBean.list(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/countContextAware")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Long countContextAware(
		MetadataConsulta metadataConsulta, @Context HttpServletRequest request
	) {
		Long result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
				
				result = iEmpresaServiceBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public EmpresaService getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		EmpresaService result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
				
				result = iEmpresaServiceBean.getById(id);
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
	public EmpresaService add(EmpresaService empresaService, @Context HttpServletRequest request) {
		EmpresaService result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				empresaService.setFact(hoy);
				empresaService.setFcre(hoy);
				empresaService.setTerm(Long.valueOf(1));
				empresaService.setUact(loggedUsuarioId);
				empresaService.setUcre(loggedUsuarioId);
				
				result = iEmpresaServiceBean.save(empresaService);
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
	public void update(EmpresaService empresaService, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				empresaService.setFact(hoy);
				empresaService.setTerm(Long.valueOf(1));
				empresaService.setUact(loggedUsuarioId);
				
				iEmpresaServiceBean.update(empresaService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(EmpresaService empresaService, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IEmpresaServiceBean iEmpresaServiceBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				empresaService.setFact(hoy);
				empresaService.setTerm(Long.valueOf(1));
				empresaService.setUact(loggedUsuarioId);
				
				iEmpresaServiceBean.remove(empresaService);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IEmpresaServiceBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = EmpresaServiceBean.class.getSimpleName();
		String remoteInterfaceName = IEmpresaServiceBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IEmpresaServiceBean) context.lookup(lookupName);
	}
}