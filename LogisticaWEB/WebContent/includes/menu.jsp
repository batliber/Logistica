<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="uy.com.amensg.logistica.dwr.UsuarioDWR"  %>
<%@ page import="uy.com.amensg.logistica.entities.UsuarioTO"  %>
<%@ page import="uy.com.amensg.logistica.entities.UsuarioRolEmpresaTO"  %>
<%@ page import="uy.com.amensg.logistica.entities.MenuTO"  %>
<%@ page import="uy.com.amensg.logistica.entities.EmpresaTO"  %>

<%@ page import="java.util.*"  %>

<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>

<%@ page import="uy.com.amensg.logistica.bean.ISeguridadBean"  %>
<%@ page import="uy.com.amensg.logistica.bean.IUsuarioBean"  %>
<%@ page import="uy.com.amensg.logistica.bean.SeguridadBean"  %>
<%@ page import="uy.com.amensg.logistica.bean.UsuarioBean"  %>
<%@ page import="uy.com.amensg.logistica.entities.Usuario"  %>
<%@ page import="uy.com.amensg.logistica.entities.UsuarioTO"  %>

	<div class="divMenuBar">
<%
	HttpSession httpSession = request.getSession(false);
	
	UsuarioTO usuarioTO = null;
	
	if ((httpSession != null) && (httpSession.getAttribute("sesion") != null)) {
		Long usuarioId = (Long) httpSession.getAttribute("sesion");
		
		try {
			String prefix = "java:jboss/exported/";
			String EARName = "Logistica";
			String appName = "LogisticaEJB";
			String beanName = UsuarioBean.class.getSimpleName();
			String remoteInterfaceName = IUsuarioBean.class.getName();
			String lookupName = prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;
			Context context = new InitialContext();
			
			IUsuarioBean iUsuarioBean = (IUsuarioBean) context.lookup(lookupName);
			
			usuarioTO = UsuarioDWR.transform(iUsuarioBean.getById(usuarioId, true), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	EmpresaTO empresaTO = null;
	Collection<Long> empresasId = new LinkedList<Long>();
	for (UsuarioRolEmpresaTO usuarioRolEmpresa : usuarioTO.getUsuarioRolEmpresas()) {
		if (empresaTO == null) {
			empresaTO = usuarioRolEmpresa.getEmpresa();
		}
		
		if (!empresasId.contains(usuarioRolEmpresa.getEmpresa().getId())) {
			empresasId.add(usuarioRolEmpresa.getEmpresa().getId());
		}
	}
%>

		<div class="inactiveMenuBarItemLogo">
			<div id="divLogo">
<%
	if (empresasId.size() > 1 || empresaTO == null || empresaTO.getLogoURL() == null || empresaTO.getLogoURL().isEmpty()) {
%>
				&nbsp;
<%
	} else {
%>
				<img id="imgLogo" src="/LogisticaWEB/Stream?fn=<%= empresaTO.getLogoURL() %>" />
<%
	}
%>
			</div>
		</div>
		<div class="divUserInfo">
			<div class="divActiveUserName" id="divActiveUser">
				<a href="#" onclick="javascript:divLogoutOnClick(event, this)"><%= usuarioTO.getNombre() %></a>
			</div>
		</div>
<%
	boolean first = true;

	List<MenuTO> menus = new LinkedList<MenuTO>();
	menus.addAll(usuarioTO.getMenus());

	Collections.sort(menus, new Comparator<MenuTO>() {
		public int compare(MenuTO arg1, MenuTO arg2) {
			return arg1.getOrden().compareTo(arg2.getOrden());
		}
	});
	
	for (MenuTO menuTO : menus) {
		if (!menuTO.getUrl().contains("mobile")) {
%>
		<div class="<%= request.getRequestURI().equals(menuTO.getUrl()) ? "activeMenuBarItem" : "inactiveMenuBarItem" %>">
			<div>
<%
			if (menuTO.getPadre() != null) {
%>
				<a href="<%= menuTO.getUrl() %>" onclick="javascript:menuItemOnClick(event, this)"
					id="<%= menuTO.getId() %>"
					url="<%= menuTO.getUrl() %>"
					class="aMenuLeaf"> 
					<%= menuTO.getTitulo() %>
				</a>
<%
			} else {
%>
				<div class="divMenuParent">
					<%= menuTO.getTitulo() %>
				</div>
<%
			}
%>
			</div>
		</div>
<%
			first = false;
		}
	}
%>
	</div>