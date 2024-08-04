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

import uy.com.amensg.logistica.bean.IModeloBean;
import uy.com.amensg.logistica.bean.ModeloBean;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Modelo;

@Path("/ModeloREST")
public class ModeloREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<Modelo> list(@Context HttpServletRequest request) {
		Collection<Modelo> result = new LinkedList<Modelo>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IModeloBean iModeloBean = lookupBean();
				
				result = iModeloBean.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listVigentes")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<Modelo> listVigentes(@Context HttpServletRequest request) {
		Collection<Modelo> result = new LinkedList<Modelo>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IModeloBean iModeloBean = lookupBean();
				
				result = iModeloBean.listVigentes();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listMinimalByMarcaId/{marcaId}")
	@Produces("application/json")
	public Collection<Modelo> listMinimalByMarcaId(
		@PathParam("marcaId") Long marcaId, @Context HttpServletRequest request
	) {
		Collection<Modelo> result = new LinkedList<Modelo>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IModeloBean iModeloBean = lookupBean();
				
				result = iModeloBean.listMinimalByMarcaId(marcaId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/listVigentesMinimalByMarcaId/{marcaId}")
	@Produces("application/json")
	public Collection<Modelo> listVigentesMinimalByMarcaId(
		@PathParam("marcaId") Long marcaId, @Context HttpServletRequest request
	) {
		Collection<Modelo> result = new LinkedList<Modelo>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IModeloBean iModeloBean = lookupBean();
				
				result = iModeloBean.listVigentesMinimalByMarcaId(marcaId);
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
				
				IModeloBean iModeloBean = lookupBean();
				
				result = iModeloBean.list(metadataConsulta, usuarioId);
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
				
				IModeloBean iModeloBean = lookupBean();
				
				result = iModeloBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Modelo getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Modelo result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IModeloBean iModeloBean = lookupBean();
				
				result = iModeloBean.getById(id);
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
	public Modelo add(Modelo Modelo, @Context HttpServletRequest request) {
		Modelo result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IModeloBean iModeloBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Modelo.setFact(hoy);
				Modelo.setFcre(hoy);
				Modelo.setTerm(Long.valueOf(1));
				Modelo.setUact(loggedUsuarioId);
				Modelo.setUcre(loggedUsuarioId);
				
				result = iModeloBean.save(Modelo);
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
	public void update(Modelo Modelo, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IModeloBean iModeloBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Modelo.setFact(hoy);
				Modelo.setTerm(Long.valueOf(1));
				Modelo.setUact(loggedUsuarioId);
				
				iModeloBean.update(Modelo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(Modelo Modelo, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IModeloBean iModeloBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Modelo.setFact(hoy);
				Modelo.setTerm(Long.valueOf(1));
				Modelo.setUact(loggedUsuarioId);
				
				iModeloBean.remove(Modelo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IModeloBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = ModeloBean.class.getSimpleName();
		String remoteInterfaceName = IModeloBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IModeloBean) context.lookup(lookupName);
	}
}