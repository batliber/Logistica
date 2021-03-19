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

import uy.com.amensg.logistica.bean.BarrioBean;
import uy.com.amensg.logistica.bean.IBarrioBean;
import uy.com.amensg.logistica.entities.Barrio;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Path("/BarrioREST")
public class BarrioREST {

	@GET
	@Path("/listMinimal")
	@Produces("application/json")
	public Collection<Barrio> listMinimal(@Context HttpServletRequest request) {
		Collection<Barrio> result = new LinkedList<Barrio>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IBarrioBean iBarrioBean = lookupBean();
				
				result = iBarrioBean.listMinimal();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listByDepartamentoId/{departamentoId}")
	@Produces("application/json")
	public Collection<Barrio> listByDepartamentoId(
		@PathParam("departamentoId") Long departamentoId, @Context HttpServletRequest request
	) {
		Collection<Barrio> result = new LinkedList<Barrio>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IBarrioBean iBarrioBean = lookupBean();
				
				result = iBarrioBean.listByDepartamentoId(departamentoId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listMinimalByDepartamentoId/{departamentoId}")
	@Produces("application/json")
	public Collection<Barrio> listMinimalByDepartamentoId(
		@PathParam("departamentoId") Long departamentoId, @Context HttpServletRequest request
	) {
		Collection<Barrio> result = new LinkedList<Barrio>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IBarrioBean iBarrioBean = lookupBean();
				
				result = iBarrioBean.listMinimalByDepartamentoId(departamentoId);
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
				
				IBarrioBean iBarrioBean = lookupBean();
				
				result = iBarrioBean.list(metadataConsulta, usuarioId);
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
				
				IBarrioBean iBarrioBean = lookupBean();
				
				result = iBarrioBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Barrio getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Barrio result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IBarrioBean iBarrioBean = lookupBean();
				
				result = iBarrioBean.getById(id);
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
	public Barrio add(Barrio Barrio, @Context HttpServletRequest request) {
		Barrio result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IBarrioBean iBarrioBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Barrio.setFact(hoy);
				Barrio.setFcre(hoy);
				Barrio.setTerm(Long.valueOf(1));
				Barrio.setUact(loggedUsuarioId);
				Barrio.setUcre(loggedUsuarioId);
				
				result = iBarrioBean.save(Barrio);
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
	public void update(Barrio Barrio, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IBarrioBean iBarrioBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Barrio.setFact(hoy);
				Barrio.setTerm(Long.valueOf(1));
				Barrio.setUact(loggedUsuarioId);
				
				iBarrioBean.update(Barrio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(Barrio Barrio, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IBarrioBean iBarrioBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Barrio.setFact(hoy);
				Barrio.setTerm(Long.valueOf(1));
				Barrio.setUact(loggedUsuarioId);
				
				iBarrioBean.remove(Barrio);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IBarrioBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = BarrioBean.class.getSimpleName();
		String remoteInterfaceName = IBarrioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IBarrioBean) context.lookup(lookupName);
	}
}