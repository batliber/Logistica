<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

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
			
			usuarioTO = UsuarioDWR.transform(iUsuarioBean.getById(usuarioId), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
%>

	<div id="divMenuContent">
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
		if (menuTO.getUrl().contains("mobile")) {
%>
		<div class="divMenuItem">
			<div class="divMenuItemText"><a href="<%= menuTO.getUrl() %>"><%= menuTO.getTitulo() %></a></div>
		</div>
<%
		}
	}
%>
		<div class="divMenuItem">
			<div class="divMenuItemText"><a href="#" onclick="javascript:divLogoutOnClick(event, this)">Salir</a></div>
		</div>
	</div>
</body>
</html>