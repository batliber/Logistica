package uy.com.amensg.logistica.webservices.rest;

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

import uy.com.amensg.logistica.bean.IRecargaPuntoVentaUsuarioBean;
import uy.com.amensg.logistica.bean.RecargaPuntoVentaUsuarioBean;
import uy.com.amensg.logistica.entities.PuntoVenta;
import uy.com.amensg.logistica.entities.RecargaPuntoVentaUsuario;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresa;

@Path("/RecargaPuntoVentaUsuarioREST")
public class RecargaPuntoVentaUsuarioREST {

	@GET
	@Path("/getPuntoVentaByContext")
	@Produces({ MediaType.APPLICATION_JSON })
	public PuntoVenta getPuntoVentaByContext(@Context HttpServletRequest request) {
		PuntoVenta result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaPuntoVentaUsuarioBean iRecargaPuntoVentaUsuarioBean = lookupBean();
				
				Usuario usuario = new Usuario();
				usuario.setId(loggedUsuarioId);
				
				result = prepareJSON(iRecargaPuntoVentaUsuarioBean.getPuntoVentaByUsuario(usuario));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@GET
	@Path("/getPuntoVentaByUsuarioId/{usuarioId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public PuntoVenta getPuntoVentaByContext(
		@PathParam("usuarioId") Long usuarioId, @Context HttpServletRequest request) {
		PuntoVenta result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
//				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				IRecargaPuntoVentaUsuarioBean iRecargaPuntoVentaUsuarioBean = lookupBean();
				
				Usuario usuario = new Usuario();
				usuario.setId(usuarioId);
				
				result = prepareJSON(iRecargaPuntoVentaUsuarioBean.getPuntoVentaByUsuario(usuario));
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
	public RecargaPuntoVentaUsuario update(
		RecargaPuntoVentaUsuario recargaPuntoVentaUsuario, @Context HttpServletRequest request
	) {
		RecargaPuntoVentaUsuario result = null;
		
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long loggedUsuarioId = (Long) httpSession.getAttribute("sesion");
				
				Date hoy = GregorianCalendar.getInstance().getTime();
				
				recargaPuntoVentaUsuario.setFact(hoy);
				recargaPuntoVentaUsuario.setTerm(Long.valueOf(1));
				recargaPuntoVentaUsuario.setUact(loggedUsuarioId);
				
				IRecargaPuntoVentaUsuarioBean iRecargaPuntoVentaUsuarioBean = lookupBean();
				
				result = prepareJSON(iRecargaPuntoVentaUsuarioBean.update(recargaPuntoVentaUsuario));
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

	private RecargaPuntoVentaUsuario prepareJSON(RecargaPuntoVentaUsuario recargaPuntoVentaUsuario) {
		RecargaPuntoVentaUsuario result = null;
		
		if (recargaPuntoVentaUsuario != null) {
			result = recargaPuntoVentaUsuario;
			
			result.setPuntoVenta(prepareJSON(result.getPuntoVenta()));
		}
		
		return result;
	}
	
	private IRecargaPuntoVentaUsuarioBean lookupBean() throws NamingException {
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