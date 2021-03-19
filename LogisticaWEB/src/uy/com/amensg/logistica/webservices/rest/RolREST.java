package uy.com.amensg.logistica.webservices.rest;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
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

import uy.com.amensg.logistica.bean.IRolBean;
import uy.com.amensg.logistica.bean.RolBean;
import uy.com.amensg.logistica.entities.Menu;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.Rol;

@Path("/RolREST")
public class RolREST {

	@GET
	@Path("/listMinimal")
	@Produces("application/json")
	public Collection<Rol> listMinimal(@Context HttpServletRequest request) {
		Collection<Rol> result = new LinkedList<Rol>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IRolBean iRolBean = lookupBean();
			
				result = iRolBean.listMinimal();
				
				for (Rol rol : result) {
					rol.setMenus(new HashSet<Menu>());
					rol.setSubordinados(new HashSet<Rol>());
				}
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
				
				IRolBean iRolBean = lookupBean();
				
				result = iRolBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					Rol rol = (Rol) object;
					
					rol.setMenus(new HashSet<Menu>());
					rol.setSubordinados(new HashSet<Rol>());
				}
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
				
				IRolBean iRolBean = lookupBean();
				
				result = iRolBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Rol getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Rol result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IRolBean iRolBean = lookupBean();
				
				result = iRolBean.getById(id, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getByNombre/{nombre}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Rol getByNombre(@PathParam("nombre") String nombre, @Context HttpServletRequest request) {
		Rol result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				IRolBean iRolBean = lookupBean();
				
				result = iRolBean.getByNombre(nombre, false);
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
	public Rol add(Rol Rol, @Context HttpServletRequest request) {
		Rol result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRolBean iRolBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Rol.setFact(hoy);
				Rol.setFcre(hoy);
				Rol.setTerm(Long.valueOf(1));
				Rol.setUact(loggedUsuarioId);
				Rol.setUcre(loggedUsuarioId);
				
				result = iRolBean.save(Rol);
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
	public void update(Rol Rol, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRolBean iRolBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Rol.setFact(hoy);
				Rol.setTerm(Long.valueOf(1));
				Rol.setUact(loggedUsuarioId);
				
				iRolBean.update(Rol);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(Rol Rol, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRolBean iRolBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				Rol.setFact(hoy);
				Rol.setTerm(Long.valueOf(1));
				Rol.setUact(loggedUsuarioId);
				
				iRolBean.remove(Rol);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private IRolBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RolBean.class.getSimpleName();
		String remoteInterfaceName = IRolBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IRolBean) context.lookup(lookupName);
	}
}