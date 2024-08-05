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
import uy.com.amensg.logistica.bean.ITipoProductoBean;
import uy.com.amensg.logistica.bean.TipoProductoBean;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.TipoProducto;

@Path("/TipoProductoREST")
public class TipoProductoREST {

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Collection<TipoProducto> list(@Context HttpServletRequest request) {
		Collection<TipoProducto> result = new LinkedList<TipoProducto>();
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITipoProductoBean iTipoProductoBean = lookupBean();
				
				result = iTipoProductoBean.list();
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
				
				ITipoProductoBean iTipoProductoBean = lookupBean();
				
				result = iTipoProductoBean.list(metadataConsulta, usuarioId);
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
				
				ITipoProductoBean iTipoProductoBean = lookupBean();
				
				result = iTipoProductoBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public TipoProducto getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		TipoProducto result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				ITipoProductoBean iTipoProductoBean = lookupBean();
				
				result = iTipoProductoBean.getById(id);
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
	public TipoProducto add(TipoProducto tipoProducto, @Context HttpServletRequest request) {
		TipoProducto result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				ITipoProductoBean iTipoProductoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				tipoProducto.setFact(hoy);
				tipoProducto.setFcre(hoy);
				tipoProducto.setTerm(Long.valueOf(1));
				tipoProducto.setUact(loggedUsuarioId);
				tipoProducto.setUcre(loggedUsuarioId);
				
				result = iTipoProductoBean.save(tipoProducto);
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
	public void update(TipoProducto tipoProducto, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				ITipoProductoBean iTipoProductoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				tipoProducto.setFact(hoy);
				tipoProducto.setTerm(Long.valueOf(1));
				tipoProducto.setUact(loggedUsuarioId);
				
				iTipoProductoBean.update(tipoProducto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@POST
	@Path("/remove")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public void remove(TipoProducto tipoProducto, @Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				ITipoProductoBean iTipoProductoBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				tipoProducto.setFact(hoy);
				tipoProducto.setTerm(Long.valueOf(1));
				tipoProducto.setUact(loggedUsuarioId);
				
				iTipoProductoBean.remove(tipoProducto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ITipoProductoBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = TipoProductoBean.class.getSimpleName();
		String remoteInterfaceName = ITipoProductoBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (ITipoProductoBean) context.lookup(lookupName);
	}
}