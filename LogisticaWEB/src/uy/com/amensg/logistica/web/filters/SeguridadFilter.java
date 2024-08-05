package uy.com.amensg.logistica.web.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import uy.com.amensg.logistica.bean.IUsuarioBean;
import uy.com.amensg.logistica.bean.UsuarioBean;
import uy.com.amensg.logistica.web.dwr.UsuarioDWR;
import uy.com.amensg.logistica.web.entities.MenuTO;
import uy.com.amensg.logistica.web.entities.UsuarioTO;

public class SeguridadFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			HttpSession httpSession = httpServletRequest.getSession(false);
			String requestedPage = httpServletRequest.getRequestURI();
			String userAgent = httpServletRequest.getHeader("User-Agent");
			String queryString = httpServletRequest.getQueryString();
			
			// Limpio los parámetros del queryString.
			String pageName = requestedPage.substring(requestedPage.lastIndexOf("/") + 1);
			if (pageName.indexOf("?") > 0) {
				pageName = pageName.split("?")[0];
			}
			
			// Calculo la extensión del recurso.
			String ext = pageName.substring(pageName.lastIndexOf(".") + 1);
			
			// Extensiones permitidas.
			Collection<String> allowedFileTypes = new LinkedList<String>();
			allowedFileTypes.add("gif");
			allowedFileTypes.add("jpg");
			allowedFileTypes.add("png");
			allowedFileTypes.add("ico");
			allowedFileTypes.add("js");
			allowedFileTypes.add("css");
			allowedFileTypes.add("json");
			allowedFileTypes.add("dwr");
			allowedFileTypes.add("csv");
			
			// Recursos con acceso anónimo permitido.
			if ((queryString != null && queryString.contains("wsdl"))
				|| requestedPage.contains("Service")
				|| requestedPage.contains("REST")
				|| requestedPage.contains("dwr/test")
				|| requestedPage.contains("Barcode")
				|| requestedPage.endsWith("Upload")
				|| requestedPage.contains("Download")
				|| requestedPage.contains("Stream")
				|| requestedPage.contains("JobInitializer")
				|| requestedPage.contains("login.jsp")
				|| allowedFileTypes.contains(ext)) {
				filterChain.doFilter(request, response);
							
				return;
			}
			
			// Recursos que requieren inicio de sesión.
			if ((httpSession == null) || (httpSession.getAttribute("sesion") == null)) {
				// Si la sesión no está iniciada, redirijo a la página de login.
//				System.out.println(requestedPage);
				httpServletRequest.setAttribute("requested_page", requestedPage);
				
				if (userAgent.toLowerCase().contains("mobile")) {
					httpServletRequest.getRequestDispatcher("/pages/mobile/mlogin/mlogin.jsp")
						.forward(httpServletRequest, httpServletResponse);
				} else {
					httpServletRequest.getRequestDispatcher("/pages/login/login.jsp")
						.forward(httpServletRequest, httpServletResponse);
				}
				
				return;
			} else {
				// Si la sesión está iniciada, obtengo los datos del usuario logueado.
				Long usuarioId = (Long) httpSession.getAttribute("sesion");
				
				IUsuarioBean iUsuarioBean = (IUsuarioBean) this.lookupUsuarioBean();
				
				UsuarioTO usuarioTO = 
					UsuarioDWR.transform(
						iUsuarioBean.getById(usuarioId, true), 
						true
					);
				
				// Si se requiere cambio de contraseña redirijo a la página de cambio de contraseña forzada.
				if (usuarioTO.getCambioContrasenaProximoLogin() != null 
					&& usuarioTO.getCambioContrasenaProximoLogin()
					&& !pageName.contains("cambio_password_forced.jsp")) {
					
					httpServletRequest.setAttribute("requested_page", requestedPage);
					
					if (userAgent.toLowerCase().contains("mobile")) {
						httpServletRequest.getRequestDispatcher("/pages/cambio_password_forced/cambio_password_forced.jsp")
							.forward(httpServletRequest, httpServletResponse);
					} else {
						httpServletRequest.getRequestDispatcher("/pages/cambio_password_forced/cambio_password_forced.jsp")
							.forward(httpServletRequest, httpServletResponse);
					}
					return;
				}
				
				// Chequeo las páginas permitidas para todos los usuarios.
				boolean allowed = 
					requestedPage.equals("/LogisticaWEB/")
					|| pageName.contains("main.jsp")
					|| pageName.contains("menu.jsp")
					|| pageName.contains("contrato.jsp")
					|| pageName.contains("_print.jsp")
					|| pageName.contains("historico.jsp")
					|| pageName.contains("_edit.jsp")
					|| pageName.contains("mobile")
					|| pageName.contains("cambio_password_forced.jsp");
				if (allowed) {
					filterChain.doFilter(request, response);
					
					return;
				}
				
				// Chequeo las páginas habilitadas para el perfil del usuario.
				for (MenuTO menu : usuarioTO.getMenus()) {
					if (menu.getUrl().equals(requestedPage)) {
						allowed = true;
						break;
					}
				}
				
				if (!allowed) {
					httpServletResponse.sendError(403);
				}
				
				filterChain.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
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
}