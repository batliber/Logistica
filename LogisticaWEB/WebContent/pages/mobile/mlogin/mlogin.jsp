<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML Basic 1.1//EN" "http://www.w3.org/TR/xhtml-basic/xhtml-basic11.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
	
	<title>ELARED :: Log&iacute;stica</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript">
		var requestedPage = "<%= request.getAttribute("requested_page") != null ? (String)request.getAttribute("requested_page") : "" %>";
	</script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/mobile/mlogin/mlogin.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/mobile/mlogin/mlogin.css"/>
</head>
<body>
	<div class="divLoginMainWindow">
		<div class="divTitleBar">
			<div class="divTitleBarText">Iniciar sesi&oacute;n</div>
		</div>
		<div class="divLogo">
			&nbsp;
			<!-- <img class="imgLogo" src="/LogisticaWEB/images/logo-vos.png"></img> -->
		</div>
		<div id="divError">&nbsp;</div>
		<div class="divUsuario">
			<div class="divLabelLeftColumn">
				<div class="divLabelLeftColumnTopRow">&nbsp;</div>
				<div class="divLabelLeftColumnMiddleRow">&nbsp;</div>
				<div class="divLabelLeftColumnBottomRow">&nbsp;</div>
			</div>
			<div class="divLabelMiddleColumn">
				<div class="divLabelMiddleColumnLeft">
					<div class="divLabelMiddleColumnLeftTopRow">&nbsp;</div>
					<div class="divLabelUsuario">Usuario</div>
				</div>
				<div class="divLabelMiddleColumnRight">
					<div class="divLabelMiddleColumnRightTopRow">&nbsp;</div>
					<div class="divLabelMiddleColumnRightBottomRow">&nbsp;</div>
				</div>
				<div class="divInput">
					<input type="text" id="inputUsuario" onkeydown="javascript:inputUsuarioOnKeyDown(event, this)"/>
				</div>
			</div>
			<div class="divLabelRightColumn">
				<div class="divLabelRightColumnTopRow">&nbsp;</div>
				<div class="divLabelRightColumnMiddleRow">&nbsp;</div>
				<div class="divLabelRightColumnBottomRow">&nbsp;</div>
			</div>
		</div>
		<div class="divContrasena">
			<div class="divLabelLeftColumn">
				<div class="divLabelLeftColumnTopRow">&nbsp;</div>
				<div class="divLabelLeftColumnMiddleRow">&nbsp;</div>
				<div class="divLabelLeftColumnBottomRow">&nbsp;</div>
			</div>
			<div class="divLabelMiddleColumn">
				<div class="divLabelMiddleColumnLeft">
					<div class="divLabelMiddleColumnLeftTopRow">&nbsp;</div>
					<div class="divLabelContrasena">Contrase&ntilde;a</div>
				</div>
				<div class="divLabelMiddleColumnRight">
					<div class="divLabelMiddleColumnRightTopRow">&nbsp;</div>
					<div class="divLabelMiddleColumnRightBottomRow">&nbsp;</div>
				</div>
				<div class="divInput">
					<input type="password" id="inputContrasena" onkeydown="javascript:inputContrasenaOnKeyDown(event, this)"/>
				</div>
			</div>
			<div class="divLabelRightColumn">
				<div class="divLabelRightColumnTopRow">&nbsp;</div>
				<div class="divLabelRightColumnMiddleRow">&nbsp;</div>
				<div class="divLabelRightColumnBottomRow">&nbsp;</div>
			</div>
		</div>
		<div class="divButtonBar">
			<input type="submit" id="inputAcceder" value="Acceder" onclick="javascript:inputAccederOnClick(event, this)"/>
		</div>
	</div>
</body>
</html>