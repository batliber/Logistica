package uy.com.amensg.logistica.dwr;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ISeguridadBean;
import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.SeguridadBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.entities.Usuario;
import uy.com.amensg.logistica.entities.UsuarioTO;
import uy.com.amensg.logistica.exceptions.UsuarioBloqueadoException;
import uy.com.amensg.logistica.exceptions.UsuarioContrasenaIncorrectaException;
import uy.com.amensg.logistica.exceptions.UsuarioDebeCambiarContrasenaException;
import uy.com.amensg.logistica.exceptions.UsuarioNoExisteException;

@RemoteProxy
public class SeguridadDWR {

	private ISeguridadBean lookupBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = SeguridadBean.class.getSimpleName();
		String remoteInterfaceName = ISeguridadBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ISeguridadBean) context.lookup(lookupName);
	}
	
	private IUsuarioBean lookupUsuarioBean() throws NamingException {
		String prefix = "java:jboss/exported/";
		String EARName = "Logistica";
		String appName = "LogisticaEJB";
		String beanName = UsuarioBean.class.getSimpleName();
		String remoteInterfaceName = IUsuarioBean.class.getName();
		String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (IUsuarioBean) context.lookup(lookupName);
	}
	
	public UsuarioTO getActiveUserData() {
		UsuarioTO result = null;
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		
		if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			try {
				IUsuarioBean iUsuarioBean = lookupUsuarioBean();
				
				Usuario usuario = iUsuarioBean.getById(usuarioId, true);
				
				result = UsuarioDWR.transform(usuario, true);
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public UsuarioTO login(String login, String contrasena) throws Exception {
		UsuarioTO result = null;
		
		SeguridadAuditoria seguridadAuditoria = null;
		
		try {
			ISeguridadBean iSeguridadBean = this.lookupBean();
			
			seguridadAuditoria = iSeguridadBean.login(login, contrasena);
		} catch (UsuarioBloqueadoException e1) {
			e1.printStackTrace();
			
			throw new Exception("Usuario bloqueado.");
		} catch (UsuarioContrasenaIncorrectaException e2) {
			e2.printStackTrace();
			
			throw new Exception("Usuario o contraseña incorrecta.");
		} catch (UsuarioDebeCambiarContrasenaException e3) {
			e3.printStackTrace();
			
			HttpServletRequest httpRequest = WebContextFactory.get().getHttpServletRequest();
			HttpServletResponse httpResponse = WebContextFactory.get().getHttpServletResponse();
			httpRequest.getRequestDispatcher("/pages/cambio_password_forced/cambio_password_forced.jsp")
				.forward(httpRequest, httpResponse);
		} catch (UsuarioNoExisteException e4) {
			e4.printStackTrace();
			
			throw new Exception("El usuario no existe.");
		}
		
		if (seguridadAuditoria != null) {
			HttpServletRequest httpRequest = WebContextFactory.get().getHttpServletRequest();
			HttpSession httpSession = WebContextFactory.get().getSession(true);
			
			String userAgent = httpRequest.getHeader("User-Agent");
			
			if (httpSession.getAttribute("sesion") == null) {
				httpSession.setAttribute("sesion", seguridadAuditoria.getUsuario().getId());
				
				if (userAgent.toLowerCase().contains("mobile")) {
					httpSession.setMaxInactiveInterval(-1);
				}
				
				result = UsuarioDWR.transform(seguridadAuditoria.getUsuario(), true);
			}
		}
		
		return result;
	}
	
	public void logout() {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			if (httpSession != null) {
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				if (usuarioId != null) {
					ISeguridadBean iSeguridadBean = this.lookupBean();
					
					iSeguridadBean.logout(usuarioId);
					
					httpSession.removeAttribute("sesion");
					httpSession.invalidate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}