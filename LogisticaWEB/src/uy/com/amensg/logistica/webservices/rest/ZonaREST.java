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

import uy.com.amensg.logistica.bean.IZonaBean;
import uy.com.amensg.logistica.bean.ZonaBean;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Zona;

@Path("/ZonaREST")
public class ZonaREST {

	@GET
	@Path("/listMinimal")
	@Produces("application/json")
	public Collection<Zona> listMinimal(@Context HttpServletRequest request) {
		Collection<Zona> result = new LinkedList<Zona>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IZonaBean iZonaBean = lookupBean();
				
				result = iZonaBean.listMinimal();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listMinimalByDepartamentoId/{departamentoId}")
	@Produces("application/json")
	public Collection<Zona> listMinimalByDepartamentoId(
		@PathParam("departamentoId") Long departamentoId, @Context HttpServletRequest request
	) {
		Collection<Zona> result = new LinkedList<Zona>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IZonaBean iZonaBean = lookupBean();
				
				result = iZonaBean.listMinimalByDepartamentoId(departamentoId);
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
				
				IZonaBean iZonaBean = lookupBean();
				
				result = iZonaBean.list(metadataConsulta, usuarioId);
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
				
				IZonaBean iZonaBean = lookupBean();
				
				result = iZonaBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Zona getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Zona result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IZonaBean iZonaBean = lookupBean();
				
				result = iZonaBean.getById(id);
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
	public Zona add(Zona Zona, @Context HttpServletRequest request) {
		Zona result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IZonaBean iZonaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Zona.setFact(hoy);
				Zona.setFcre(hoy);
				Zona.setTerm(Long.valueOf(1));
				Zona.setUact(loggedUsuarioId);
				Zona.setUcre(loggedUsuarioId);
				
				result = iZonaBean.save(Zona);
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
	public void update(Zona Zona, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IZonaBean iZonaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Zona.setFact(hoy);
				Zona.setTerm(Long.valueOf(1));
				Zona.setUact(loggedUsuarioId);
				
				iZonaBean.update(Zona);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(Zona Zona, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IZonaBean iZonaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Zona.setFact(hoy);
				Zona.setTerm(Long.valueOf(1));
				Zona.setUact(loggedUsuarioId);
				
				iZonaBean.remove(Zona);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IZonaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ZonaBean.class.getSimpleName();
		String remoteInterfaceName = IZonaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IZonaBean) context.lookup(lookupName);
	}
}