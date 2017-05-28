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
	<script type="text/javascript" src="./usuario_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
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
<<<<<<< HEAD
	<div class="divPopupWindow">
=======
	<div class="divMainWindow">
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
		<div class="divFormLabel">Login:</div><div id="divUsuarioLogin" class="divFormValue"><input type="text" id="inputUsuarioLogin"/></div>
		<div class="divFormLabel">Nombre:</div><div id="divUsuarioNombre" class="divFormValue"><input type="text" id="inputUsuarioNombre"/></div>
		<div class="divFormLabel">Documento:</div><div id="divUsuarioDocumento" class="divFormValue"><input type="text" id="inputUsuarioDocumento"/></div>
		<div class="divFormLabel">Contrase&ntilde;a:</div>
		<div id="divUsuarioContrasena" class="divFormValue">
			<input type="password" id="inputUsuarioContrasena" disabled="disabled"/>
			<input type="checkbox" id="inputCambiarContrasena" onchange="javascript:inputCambiarContrasenaOnChange(event, this)"/>
		</div>
		<div class="divFormLabel">Roles:</div>
		<div class="divEmpresasRoles">
			<div id="divEmpresas">
				<table id="tableEmpresas" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<td class="tdEmpresaNombre">Empresa</td>
							<td class="tdEmpresaCheck">&nbsp;</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="divRoles">
				<table id="tableRoles" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<td class="tdRolNombre">Rol</td>
							<td class="tdRolCheck">&nbsp;</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>