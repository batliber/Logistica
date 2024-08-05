package uy.com.amensg.logistica.web.webservices.rest;

import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import uy.com.amensg.logistica.bean.ISeguridadBean;
import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.SeguridadBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.exceptions.UsuarioDebeCambiarContrasenaException;
import uy.com.amensg.logistica.web.dwr.UsuarioDWR;
import uy.com.amensg.logistica.web.entities.LoginTO;
import uy.com.amensg.logistica.web.entities.MenuTO;
import uy.com.amensg.logistica.web.entities.RolTO;
import uy.com.amensg.logistica.web.entities.UsuarioRolEmpresaTO;
import uy.com.amensg.logistica.web.entities.UsuarioTO;

@Path("/SeguridadREST")
public class SeguridadREST {

	@GET
	@Path("/getActiveUserData")
	@Produces("application/json")
	public UsuarioTO getActiveUserData(@Context HttpServletRequest request) {
		UsuarioTO result = null;
		
		HttpSession httpSession = request.getSession(false);
		
		if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			try {
				IUsuarioBean iUsuarioBean = lookupUsuarioBean();
				
				Usuario usuario = iUsuarioBean.getById(usuarioId, true);
				
				result = UsuarioDWR.transform(usuario, true);
				
				for (UsuarioRolEmpresaTO usuarioRolEmpresaTO : result.getUsuarioRolEmpresas()) {
					usuarioRolEmpresaTO.getRol().setMenus(new LinkedList<MenuTO>());
					usuarioRolEmpresaTO.getRol().setSubordinados(new LinkedList<RolTO>());
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public UsuarioTO login(
		LoginTO loginTO, @Context HttpServletRequest request, @Context HttpServletResponse response
	) throws Exception {
		UsuarioTO result = null;
		
		SeguridadAuditoria seguridadAuditoria = null;
		
		try {
			ISeguridadBean iSeguridadBean = lookupBean();
			
			seguridadAuditoria = iSeguridadBean.login(loginTO.getL(), loginTO.getP());
		} catch (UsuarioDebeCambiarContrasenaException e3) {
			e3.printStackTrace();
			
			request.getRequestDispatcher(
				"/pages/cambio_password_forced/cambio_password_forced.jsp"
			).forward(request, response);
		}
		
		if (seguridadAuditoria != null) {
			String userAgent = request.getHeader("User-Agent");
			
			HttpSession session = request.getSession(false);
			if (session == null) {
				session = request.getSession();
			}
			
			if (session.getAttribute("sesion") == null) {
				session.setAttribute("sesion", seguridadAuditoria.getUsuario().getId());
				
				if (userAgent.toLowerCase().contains("mobile")) {
					session.setMaxInactiveInterval(-1);
				}
				
				result = UsuarioDWR.transform(seguridadAuditoria.getUsuario(), true);
			}
		}
		
		return result;
	}
	
	@GET
	@Path("/logout")
	@Produces("application/json")
	public void logout(@Context HttpServletRequest request) {
		try {
			HttpSession httpSession = request.getSession(false);
			
			if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				ISeguridadBean iSeguridadBean = this.lookupBean();
				
				iSeguridadBean.logout(usuarioId);
					
				httpSession.removeAttribute("sesion");
				httpSession.invalidate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ISeguridadBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = SeguridadBean.class.getSimpleName();
		String remoteInterfaceName = ISeguridadBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (ISeguridadBean) context.lookup(lookupName);
	}
	
	private IUsuarioBean lookupUsuarioBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = UsuarioBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioBean.class.getName();
		String lookupName = 
			prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		
		javax.naming.Context context = new InitialContext();
		
		return (IUsuarioBean) context.lookup(lookupName);
	}
}