<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML Basic 1.1//EN" "http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
	
	<title>ELARED :: Log&iacute;stica</title>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/mobile/mlogin.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/mobile/mlogin.css"/>
</head>
<body>
	<div class="divTitleBar">
		<div class="divTitleBarText">Iniciar sesi&oacute;n</div>
	</div>
	<div class="divLogo">
		&nbsp;
		<!-- <img class="imgLogo" src="/LogisticaWEB/images/logo-vos.png"></img> -->
	</div>
	<div id="divError">&nbsp;</div>
	<div class="divFormLabel">Usuario:</div>
	<div class="divFormValue"><input type="text" id="inputUsuario" onkeydown="javascript:inputUsuarioOnKeyDown(event, this)"/></div>
	<div class="divFormLabel">Contrase&ntilde;a:</div>
	<div class="divFormValue"><input type="password" id="inputContrasena" onkeydown="javascript:inputContrasenaOnKeyDown(event, this)"/></div>
	<div class="divFormLabel">&nbsp;</div> 
	<div class="divFormValue"><input type="submit" id="inputAcceder" value="Acceder" onclick="javascript:inputAccederOnClick(event, this)"/></div>
	<div class="divFormLabel">&nbsp;</div>
</body>
</html>