<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Usuario</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/RolDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioRolEmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="./usuario_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/grid.css"/>
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
		<div class="divFormLabel" id="divLabelAgregarEmpresa">Empresa:</div><div class="divFormValue" id="divEmpresa"><select id="selectEmpresa"></select><input type="button" id="inputAgregarEmpresa" value="" onclick="javascript:inputAgregarEmpresaOnClick(event, this)"/></div>
		<div class="divFormLabel" id="divLabelAgregarRol">Rol:</div><div class="divFormValue" id="divRol"><select id="selectRol"></select><input type="button" id="inputAgregarRol" value="" onclick="javascript:inputAgregarRolOnClick(event, this)"/></div>
		<div class="divFormLabel">Empresas:</div>
		<div id="divTableEmpresas">&nbsp;</div>
		<div class="divFormLabel">Roles:</div>
		<div id="divTableRoles">&nbsp;</div>
	</div>
</body>
</html>