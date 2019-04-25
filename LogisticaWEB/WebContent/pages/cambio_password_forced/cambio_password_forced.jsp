<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Login</title>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="./cambio_password_forced.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./cambio_password_forced.css"/>
</head>
<body>
	<div class="divMainWindow">
		<div id="divFormulario">
			<div class="divFormLabel">Usuario:</div>
			<div class="divFormValue" id="divUsuario">&nbsp;</div>
			<div class="divFormLabel">Actual:</div>
			<div class="divFormValue" id="divActual"><input type="password" id="inputActual"/></div>
			<div class="divFormLabel">Nueva:</div>
			<div class="divFormValue" id="divNueva"><input type="password" id="inputNueva"/></div>
			<div class="divFormLabel">Confirma:</div>
			<div class="divFormValue" id="divConfirma"><input type="password" id="inputConfirma" onchange="javascript:inputConfirmaContrasenaOnChange(event, this)"/></div>
			<div class="divFormLabel">&nbsp;</div><div class="divFormValue">
				<input type="button" id="inputGuardar" value="Enviar" onclick="javascript:inputGuardarOnClick(event, this)"/>
			</div>
		</div>
	</div>
</body>
</html>