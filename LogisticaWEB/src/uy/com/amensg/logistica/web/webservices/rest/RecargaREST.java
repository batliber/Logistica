package uy.com.amensg.logistica.web.webservices.rest;

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
import uy.com.amensg.logistica.bean.IRecargaBean;
import uy.com.amensg.logistica.bean.IRecargaPuntoVentaUsuarioBean;
import uy.com.amensg.logistica.bean.RecargaBean;
import uy.com.amensg.logistica.bean.RecargaPuntoVentaUsuarioBean;
import uy.com.amensg.logistica.entities.MetadataConsulta;
import uy.com.amensg.logistica.entities.MetadataConsultaResultado;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.Recarga;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Path("/RecargaREST")
public class RecargaREST {

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
				
				IRecargaBean iRecargaBean = lookupBean();
				
				result = iRecargaBean.list(metadataConsulta, usuarioId);
				
				for (Object object : result.getRegistrosMuestra()) {
					Recarga recarga = (Recarga) object;
					
					prepareJSON(recarga);
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
				
				IRecargaBean iRecargaBean = lookupBean();
				
				result = iRecargaBean.count(metadataConsulta, usuarioId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getById/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Recarga getById(@PathParam("id") Long id, @Context HttpServletRequest request) {
		Recarga result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaBean iRecargaBean = lookupBean();
				
				result = prepareJSON(iRecargaBean.getById(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/recargar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Recarga recargar(Recarga recarga, @Context HttpServletRequest request) {
		Recarga result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(loggedUsuarioId);
				
				IRecargaBean iRecargaBean = lookupBean();
				IRecargaPuntoVentaUsuarioBean iRecargaPuntoVentaUsuarioBean = lookupRecargaPuntoVentaUsuarioBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				recarga.setFechaSolicitud(hoy);
				
				recarga.setFact(hoy);
				recarga.setFcre(hoy);
				recarga.setTerm(Long.valueOf(1));
				recarga.setUact(loggedUsuarioId);
				recarga.setUcre(loggedUsuarioId);
				
				recarga.setPuntoVenta(
					iRecargaPuntoVentaUsuarioBean.getPuntoVentaByUsuario(usuario)
				);
				
				result = prepareJSON(iRecargaBean.recargar(recarga));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/aprobar")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Recarga aprobar(
		Recarga recarga, @Context HttpServletRequest request
	) {
		Recarga result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				Usuario usuario = new Usuario();
				usuario.setId(loggedUsuarioId);
				
				IRecargaBean iRecargaBean = lookupBean();
				IRecargaPuntoVentaUsuarioBean iRecargaPuntoVentaUsuarioBean = lookupRecargaPuntoVentaUsuarioBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				recarga.setFact(hoy);
				recarga.setTerm(Long.valueOf(1));
				recarga.setUact(loggedUsuarioId);
				
				recarga.setPuntoVenta(
					iRecargaPuntoVentaUsuarioBean.getPuntoVentaByUsuario(usuario)
				);
				
				result = prepareJSON(iRecargaBean.aprobar(recarga));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@POST
	@Path("/timeout")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Recarga timeout(
		Recarga recarga, @Context HttpServletRequest request
	) {
		Recarga result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaBean iRecargaBean = lookupBean();
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				recarga.setFact(hoy);
				recarga.setTerm(Long.valueOf(1));
				recarga.setUact(loggedUsuarioId);
				
				result = prepareJSON(iRecargaBean.timeout(recarga));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private PuntoVenta prepareJSON(PuntoVenta puntoVenta) {
		PuntoVenta result = puntoVenta;
		
		if (result != null) {
			if (result.getDistribuidor() != null) {
				result.getDistribuidor().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
			}
			
			if (result.getCreador() != null) {
				result.getCreador().setUsuarioRolEmpresas(new LinkedList<UsuarioRolEmpresa>());
			}
			
			if (result.getRecargaPuntoVentaCota() != null) {
				result.getRecargaPuntoVentaCota().setPuntoVenta(null);
			}
		}
		
		return result;
	}
	
	private Recarga prepareJSON(Recarga recarga) {
		Recarga result = null;
		
		if (recarga != null) {
			result = recarga;
			
			result.setPuntoVenta(prepareJSON(result.getPuntoVenta()));
		}
		
		return result;
	}
	
	private IRecargaBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RecargaBean.class.getSimpleName();
		String remoteInterfaceName = IRecargaBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IRecargaBean) context.lookup(lookupName);
	}
	
	private IRecargaPuntoVentaUsuarioBean lookupRecargaPuntoVentaUsuarioBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = RecargaPuntoVentaUsuarioBean.class.getSimpleName();
		String remoteInterfaceName = IRecargaPuntoVentaUsuarioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

		javax.naming.Context context = new InitialContext();
		
		return (IRecargaPuntoVentaUsuarioBean) context.lookup(lookupName);
	}
}