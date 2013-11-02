package uy.com.amensg.logistica.dwr;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;

import uy.com.amensg.logistica.bean.ISeguridadBean;
import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.SeguridadBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.entities.SeguridadAuditoria;
import uy.com.amensg.logistica.entities.Usuario;

@RemoteProxy
public class SeguridadDWR {

	private ISeguridadBean lookupBean() throws NamingException {
		String EARName = "Logistica";
		String beanName = SeguridadBean.class.getSimpleName();
		String remoteInterfaceName = ISeguridadBean.class.getName();
		String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
		Context context = new InitialContext();
		
		return (ISeguridadBean) context.lookup(lookupName);
	}
	
	public String getActiveUserData() {
		String result = null;
		
		HttpSession httpSession = WebContextFactory.get().getSession(false);
		
		if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			try {
				String EARName = "Logistica";
				String beanName = UsuarioBean.class.getSimpleName();
				String remoteInterfaceName = IUsuarioBean.class.getName();
				String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
				Context context = new InitialContext();
				
				IUsuarioBean iUsuarioBean = (IUsuarioBean) context.lookup(lookupName);
				
				Usuario usuario = iUsuarioBean.getById(usuarioId);
				
				result = usuario.getNombre();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public void login(String login, String contrasena) throws Exception {
		try {
			ISeguridadBean iSeguridadBean = this.lookupBean();
			
			SeguridadAuditoria seguridadAuditoria = iSeguridadBean.login(login, contrasena);
			
			if (seguridadAuditoria != null) {
				HttpSession httpSession = WebContextFactory.get().getSession(true);
				
				if (httpSession.getAttribute("sesion") == null) {
					httpSession.setAttribute("sesion", seguridadAuditoria.getUsuario().getId());
				}
			} else {
				throw new Exception("Usuario o contraseņa incorrecta.");
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void logout() {
		try {
			HttpSession httpSession = WebContextFactory.get().getSession(false);
			
			Long usuarioId = (Long) httpSession.getAttribute("sesion");
			
			if (usuarioId != null) {
				ISeguridadBean iSeguridadBean = this.lookupBean();
				
				iSeguridadBean.logout(usuarioId);
				
				httpSession.removeAttribute("sesion");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}