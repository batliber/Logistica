package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.AtencionClienteTipoContactoBean;
import uy.com.amensg.logistica.bean.IAtencionClienteTipoContactoBean;
import uy.com.amensg.logistica.entities.AtencionClienteTipoContacto;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Path("/AtencionClienteTipoContactoREST")
public class AtencionClienteTipoContactoREST {
	
	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<AtencionClienteTipoContacto> list(@Context HttpServletRequest request) {
		Collection<AtencionClienteTipoContacto> result = new LinkedList<AtencionClienteTipoContacto>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IAtencionClienteTipoContactoBean iAtencionClienteTipoContactoBean = lookupBean();
				
				result = iAtencionClienteTipoContactoBean.list();
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
				
				IAtencionClienteTipoContactoBean iAtencionClienteTipoContactoBean = lookupBean();
				
				result = iAtencionClienteTipoContactoBean.list(metadataConsulta, usuarioId);
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
				
				IAtencionClienteTipoContactoBean iAtencionClienteTipoContactoBean = lookupBean();
				
				result = iAtencionClienteTipoContactoBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public AtencionClienteTipoContacto getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		AtencionClienteTipoContacto result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IAtencionClienteTipoContactoBean iAtencionClienteTipoContactoBean = lookupBean();
				
				result = iAtencionClienteTipoContactoBean.getById(id);
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
	public AtencionClienteTipoContacto add(AtencionClienteTipoContacto atencionClienteTipoContacto, @Context HttpServletRequest request) {
		AtencionClienteTipoContacto result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IAtencionClienteTipoContactoBean iAtencionClienteTipoContactoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				atencionClienteTipoContacto.setFact(hoy);
				atencionClienteTipoContacto.setFcre(hoy);
				atencionClienteTipoContacto.setTerm(Long.valueOf(1));
				atencionClienteTipoContacto.setUact(loggedUsuarioId);
				atencionClienteTipoContacto.setUcre(loggedUsuarioId);
				
				result = iAtencionClienteTipoContactoBean.save(atencionClienteTipoContacto);
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
	public void update(AtencionClienteTipoContacto atencionClienteTipoContacto, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IAtencionClienteTipoContactoBean iAtencionClienteTipoContactoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				atencionClienteTipoContacto.setFact(hoy);
				atencionClienteTipoContacto.setTerm(Long.valueOf(1));
				atencionClienteTipoContacto.setUact(loggedUsuarioId);
				
				iAtencionClienteTipoContactoBean.update(atencionClienteTipoContacto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(AtencionClienteTipoContacto atencionClienteTipoContacto, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IAtencionClienteTipoContactoBean iAtencionClienteTipoContactoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				atencionClienteTipoContacto.setFact(hoy);
				atencionClienteTipoContacto.setTerm(Long.valueOf(1));
				atencionClienteTipoContacto.setUact(loggedUsuarioId);
				
				iAtencionClienteTipoContactoBean.remove(atencionClienteTipoContacto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IAtencionClienteTipoContactoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = AtencionClienteTipoContactoBean.class.getSimpleName();
		String remoteInterfaceName = IAtencionClienteTipoContactoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IAtencionClienteTipoContactoBean) context.lookup(lookupName);
	}
}