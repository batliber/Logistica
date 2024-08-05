package uy.com.amensg.logistica.web.webservices.rest;

import java.util.Date;
import java.util.GregorianCalendar;

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
import uy.com.amensg.logistica.bean.ConfiguracionPHBean;
import uy.com.amensg.logistica.bean.IConfiguracionPHBean;
import uy.com.amensg.logistica.entities.Configuracion;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;

@Path("/ConfigurationPHREST")
public class ConfigurationPHREST {

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
				IConfiguracionPHBean iConfiguracionPHBean = lookupBean();
				
				result = iConfiguracionPHBean.list(metadataConsulta);
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
				IConfiguracionPHBean iConfiguracionPHBean = lookupBean();
				
				result = iConfiguracionPHBean.count(metadataConsulta);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Configuracion getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Configuracion result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IConfiguracionPHBean iConfiguracionPHBean = lookupBean();
				
				result = iConfiguracionPHBean.getById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getByClave/{clave}")
	@Produces("application/json")
	public Configuracion getByClave(@PathParam("clave") String clave) {
		Configuracion result = null;
			
		try {
			IConfiguracionPHBean iConfiguracionPHBean = lookupBean();
			
			result = iConfiguracionPHBean.getByClave(clave);
		} catch (NamingException e) {
			e.printStackTrace();
		}
			
		return result;
	}
	
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Configuracion add(Configuracion configuracion, @Context HttpServletRequest request) {
		Configuracion result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IConfiguracionPHBean iConfiguracionPHBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				configuracion.setFact(hoy);
				configuracion.setFcre(hoy);
				configuracion.setTerm(Long.valueOf(1));
				configuracion.setUact(loggedUsuarioId);
				configuracion.setUcre(loggedUsuarioId);
				
				result = iConfiguracionPHBean.save(configuracion);
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
	public void update(Configuracion configuracion, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IConfiguracionPHBean iConfiguracionPHBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				configuracion.setFact(hoy);
				configuracion.setTerm(Long.valueOf(1));
				configuracion.setUact(loggedUsuarioId);
				
				iConfiguracionPHBean.update(configuracion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(Configuracion configuracion, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IConfiguracionPHBean iConfiguracionPHBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				configuracion.setFact(hoy);
				configuracion.setTerm(Long.valueOf(1));
				configuracion.setUact(loggedUsuarioId);
				
				iConfiguracionPHBean.remove(configuracion);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IConfiguracionPHBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String remoteInterfaceName = IConfiguracionPHBean.class.getName();
		String beanName = ConfiguracionPHBean.class.getSimpleName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IConfiguracionPHBean) context.lookup(lookupName);
	}
}