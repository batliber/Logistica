<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Cambio contrase&ntilde;a</title>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/menu.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/cambio_password/cambio_password.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/menu.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/cambio_password/cambio_password.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" id="inputGuardarUsuario" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div class="divFormLabelExtended">Login:</div><div id="divUsuarioLogin" class="divFormValue"><input type="text" id="inputUsuarioLogin"/></div>
				<div class="divFormLabelExtended">Contrase&ntilde; actual:</div><div id="divContrasenaActual" class="divFormValue"><input type="password" id="inputContrasenaActual"/></div>
				<div class="divFormLabelExtended">Nueva contrase&ntilde;a:</div><div id="divNuevaContrasena" class="divFormValue"><input type="password" id="inputNuevaContrasena"/></div>
				<div class="divFormLabelExtended">Confirma contrase&ntilde;a:</div><div id="divConfirmaContrasena" class="divFormValue"><input type="password" id="inputConfirmaContrasena" onchange="javascript:inputConfirmaContrasenaOnChange(event, this)"/></div>
				</div>
			</div>
		</div>
	</div>
<%@ include file="/includes/footer.jsp" %>