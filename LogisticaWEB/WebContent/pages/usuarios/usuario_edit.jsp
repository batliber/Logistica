<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Usuario</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./usuario_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./usuario_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarUsuario" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarUsuario"><input type="submit" id="inputEliminarUsuario" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Usuario</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabel">Login:</div><div id="divUsuarioLogin" class="divFormValue"><input type="text" id="inputUsuarioLogin"/></div>
		<div class="divFormLabel">Nombre:</div><div id="divUsuarioNombre" class="divFormValue"><input type="text" id="inputUsuarioNombre"/></div>
		<div class="divFormLabel">Documento:</div><div id="divUsuarioDocumento" class="divFormValue"><input type="text" id="inputUsuarioDocumento"/></div>
		<div class="divFormLabel">Contrase&ntilde;a:</div>
		<div id="divUsuarioContrasena" class="divFormValue">
			<input type="password" id="inputUsuarioContrasena" disabled="disabled"/>
			<input type="checkbox" id="inputCambiarContrasena" onchange="javascript:inputCambiarContrasenaOnChange(event, this)"/>
		</div>
		<div class="divFormLabel">Bloqueado:</div><div id="divUsuarioBloqueado" class="divFormValue"><input type="checkbox" id="inputUsuarioBloqueado"/></div>
		<div class="divFormLabel">Punto de venta:</div><div id="divUsuarioPuntoVenta" class="divFormValue"><select id="selectUsuarioPuntoVenta"></select></div>
		<div class="divFormLabel" id="divLabelAgregarEmpresa">Empresa:</div><div class="divFormValue" id="divEmpresa"><select id="selectEmpresa"></select><input type="button" id="inputAgregarEmpresa" value="" onclick="javascript:inputAgregarEmpresaOnClick(event, this)"/></div>
		<div class="divFormLabel" id="divLabelAgregarRol">Rol:</div><div class="divFormValue" id="divRol"><select id="selectRol"></select><input type="button" id="inputAgregarRol" value="" onclick="javascript:inputAgregarRolOnClick(event, this)"/></div>
		<div class="divFormLabel">Empresas:</div>
		<div id="divTableEmpresas">&nbsp;</div>
		<div class="divFormLabel">Roles:</div>
		<div id="divTableRoles">&nbsp;</div>
	</div>
</body>
</html>