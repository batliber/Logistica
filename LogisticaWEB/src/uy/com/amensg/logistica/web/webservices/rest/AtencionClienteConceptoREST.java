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
import uy.com.amensg.logistica.bean.AtencionClienteConceptoBean;
import uy.com.amensg.logistica.bean.IAtencionClienteConceptoBean;
import uy.com.amensg.logistica.entities.AtencionClienteConcepto;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Path("/AtencionClienteConceptoREST")
public class AtencionClienteConceptoREST {
	
	@GET
	@Path("/list")
	@Produces("application/json")
	public Collection<AtencionClienteConcepto> list(@Context HttpServletRequest request) {
		Collection<AtencionClienteConcepto> result = new LinkedList<AtencionClienteConcepto>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IAtencionClienteConceptoBean iAtencionClienteConceptoBean = lookupBean();
				
				result = iAtencionClienteConceptoBean.list();
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
				
				IAtencionClienteConceptoBean iAtencionClienteConceptoBean = lookupBean();
				
				result = iAtencionClienteConceptoBean.list(metadataConsulta, usuarioId);
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
				
				IAtencionClienteConceptoBean iAtencionClienteConceptoBean = lookupBean();
				
				result = iAtencionClienteConceptoBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public AtencionClienteConcepto getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		AtencionClienteConcepto result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IAtencionClienteConceptoBean iAtencionClienteConceptoBean = lookupBean();
				
				result = iAtencionClienteConceptoBean.getById(id);
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
	public AtencionClienteConcepto add(AtencionClienteConcepto atencionClienteConcepto, @Context HttpServletRequest request) {
		AtencionClienteConcepto result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IAtencionClienteConceptoBean iAtencionClienteConceptoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				atencionClienteConcepto.setFact(hoy);
				atencionClienteConcepto.setFcre(hoy);
				atencionClienteConcepto.setTerm(Long.valueOf(1));
				atencionClienteConcepto.setUact(loggedUsuarioId);
				atencionClienteConcepto.setUcre(loggedUsuarioId);
				
				result = iAtencionClienteConceptoBean.save(atencionClienteConcepto);
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
	public void update(AtencionClienteConcepto atencionClienteConcepto, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IAtencionClienteConceptoBean iAtencionClienteConceptoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				atencionClienteConcepto.setFact(hoy);
				atencionClienteConcepto.setTerm(Long.valueOf(1));
				atencionClienteConcepto.setUact(loggedUsuarioId);
				
				iAtencionClienteConceptoBean.update(atencionClienteConcepto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(AtencionClienteConcepto atencionClienteConcepto, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IAtencionClienteConceptoBean iAtencionClienteConceptoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				atencionClienteConcepto.setFact(hoy);
				atencionClienteConcepto.setTerm(Long.valueOf(1));
				atencionClienteConcepto.setUact(loggedUsuarioId);
				
				iAtencionClienteConceptoBean.remove(atencionClienteConcepto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IAtencionClienteConceptoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = AtencionClienteConceptoBean.class.getSimpleName();
		String remoteInterfaceName = IAtencionClienteConceptoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IAtencionClienteConceptoBean) context.lookup(lookupName);
	}
}