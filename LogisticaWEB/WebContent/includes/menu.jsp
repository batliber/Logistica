<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ page import="uy.com.amensg.logistica.dwr.UsuarioDWR"  %>
<%@ page import="uy.com.amensg.logistica.entities.UsuarioTO"  %>
<%@ page import="uy.com.amensg.logistica.entities.MenuTO"  %>

<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>

<%@ page import="uy.com.amensg.logistica.bean.ISeguridadBean"  %>
<%@ page import="uy.com.amensg.logistica.bean.IUsuarioBean"  %>
<%@ page import="uy.com.amensg.logistica.bean.SeguridadBean"  %>
<%@ page import="uy.com.amensg.logistica.bean.UsuarioBean"  %>
<%@ page import="uy.com.amensg.logistica.entities.Usuario"  %>
<%@ page import="uy.com.amensg.logistica.entities.UsuarioTO"  %>

<%
	HttpSession httpSession = request.getSession(false);
	
	UsuarioTO usuarioTO = null;
	
	if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		try {
			String EARName = "Logistica";
			String beanName = UsuarioBean.class.getSimpleName();
			String remoteInterfaceName = IUsuarioBean.class.getName();
			String lookupName = EARName + "/" + beanName + "/remote-" + remoteInterfaceName;
			Context context = new InitialContext();
			
			IUsuarioBean iUsuarioBean = (IUsuarioBean) context.lookup(lookupName);
			
			usuarioTO = UsuarioDWR.transform(iUsuarioBean.getById(usuarioId), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
%>

	<div class="inactiveMenuBarItem">
		<div id="divLogo"><img id="imgLogo" src="/LogisticaWEB/images/logo-vos-bw.png"/></div>
	</div>

<%
	boolean first = true;
	for (MenuTO menuTO : usuarioTO.getMenus()) {
		if (!menuTO.getUrl().contains("mobile")) {
%>
	<div class="<%= request.getRequestURI().equals(menuTO.getUrl()) ? "activeMenuBarItem" : "inactiveMenuBarItem" %>">
		<div>
			<a href="<%= menuTO.getUrl() %>" onclick="javascript:menuItemOnClick(event, this)"
				id="<%= menuTO.getId() %>"
				url="<%= menuTO.getUrl() %>"> 
				<%= menuTO.getTitulo() %>
			</a>
		</div>
	</div>
<%
			first = false;
		}
	}
%>
	<div class="divUserInfo">
		<!-- <div class="divLogout" onclick="javascript:divLogoutOnClick(event, this)">&nbsp;</div> -->
		<div class="divActiveUserName" id="divActiveUser">
			<a href="#" onclick="javascript:divLogoutOnClick(event, this)"><%= usuarioTO.getNombre() %></a>
		</div>
	</div>