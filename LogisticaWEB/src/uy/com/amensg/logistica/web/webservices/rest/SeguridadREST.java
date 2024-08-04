package uy.com.amensg.logistica.webservices.rest;

import java.util.LinkedList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import uy.com.amensg.logistica.bean.ISeguridadBean;
import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.SeguridadBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.dwr.UsuarioDWR;
import uy.com.amensg.logistica.entities.LoginTO;
import uy.com.amensg.logistica.entities.MenuTO;
import uy.com.amensg.logistica.entities.RolTO;
import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioRolEmpresaTO;
import uy.com.amensg.logistica.entities.UsuarioTO;
import uy.com.amensg.logistica.exceptions.UsuarioDebeCambiarContrasenaException;

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