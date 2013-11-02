<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ELARED :: Log&iacute;stica</title>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/main.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/main.css"/>
</head>
<body>
	<div class="divMenuBar">
		<div class="inactiveMenuBarItem"><div id="divLogo"><img id="imgLogo" src="/LogisticaWEB/images/logo-vos-bw.png"></img></div></div>
		<div class="activeMenuBarItem"><div><a href="#" onclick="javascript:menuItemOnClick(event, this)" id="acm">ACM</a></div></div>
		<div class="inactiveMenuBarItem"><div><a href="#" onclick="javascript:menuItemOnClick(event, this)" id="procesos">Procesos</a></div></div>
		<div class="divUserInfo">
			<div class="divLogout" style="float: right;" onclick="javascript:divLogoutOnClick(event, this)">&nbsp;</div>
			<div id="divActiveUser" style="float: right;">&nbsp;</div>
			<div style="float: right;">Usuario:</div>
		</div>
	</div>
	<div id="divIFrameActivePage"><iframe id="iFrameActivePage" src="/LogisticaWEB/pages/acm/acm.jsp" frameborder="0"></iframe></div>
</body>
</html>