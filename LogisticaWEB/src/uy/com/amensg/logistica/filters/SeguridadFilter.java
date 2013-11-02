package uy.com.amensg.logistica.filters;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SeguridadFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession httpSession = httpServletRequest.getSession(false);
		String requestedPage = httpServletRequest.getRequestURI();
		
		String pageName = requestedPage.substring(requestedPage.lastIndexOf("/") + 1);
		String ext = pageName.substring(pageName.lastIndexOf(".") + 1);
		
		Collection<String> allowedFileTypes = new LinkedList<String>();
		allowedFileTypes.add("gif");
		allowedFileTypes.add("jpg");
		allowedFileTypes.add("png");
		allowedFileTypes.add("ico");
		allowedFileTypes.add("js");
		allowedFileTypes.add("css");
		allowedFileTypes.add("json");
		allowedFileTypes.add("dwr");
		
		if (pageName.indexOf("?") > 0) {
			pageName = pageName.split("?")[0];
		}
        
		if (!allowedFileTypes.contains(ext) && !requestedPage.contains("dwr/test") 
			&& !requestedPage.contains("login.jsp")) {
			if ((httpSession == null) || (httpSession.getAttribute("sesion") == null)) {
				httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/pages/login/login.jsp");
			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}
}