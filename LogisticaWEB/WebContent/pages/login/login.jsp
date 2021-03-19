<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Login</title>
	<script type="text/javascript">
		var requestedPage = "<%= request.getAttribute("requested_page") != null ? (String)request.getAttribute("requested_page") : "" %>";
	</script>
	<script type="text/javascript" src="/LogisticaWEB/pages/login/login.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/login/login.css"/>
</head>
<body>
	<div class="divLoginMainWindow">
		<div class="divLoginForm">
			<div class="divLoginTitleBar">
				<div class="divTitleBarText">Iniciar sesi&oacute;n</div>
			</div>
			<div class="divForm">
				<div class="divLogo">
					&nbsp;
					<!-- <img src="/LogisticaWEB/images/blank-logo.png"></img> -->
				</div>
				<div class="divFormLabel">Usuario:</div>
				<div class="divFormInput"><input type="text" id="inputUsuario" onkeydown="javascript:inputUsuarioOnKeyDown(event, this)"/></div>
				<div class="divFormLabel">Contrase&ntilde;a:</div>
				<div class="divFormInput"><input type="password" id="inputContrasena" onkeydown="javascript:inputContrasenaOnKeyDown(event, this)"/></div>
				<div class="divButtons">
					<div style="float: left;width: 70px;"><input type="submit" id="inputAcceder" value="Acceder" onclick="javascript:inputAccederOnClick(event, this)"/></div>
					<div id="divError">&nbsp;</div>
				</div>
			</div>
			<div class="divLoginVersionBar">
				<div class="divLoginVersionBarText">&nbsp;</div>
			</div>
		</div>
	</div>
<!-- 
<script type="text/javascript"> //<![CDATA[
  var tlJsHost = ((window.location.protocol == "https:") ? "https://secure.trust-provider.com/" : "http://www.trustlogo.com/");
  document.write(unescape("%3Cscript src='" + tlJsHost + "trustlogo/javascript/trustlogo.js' type='text/javascript'%3E%3C/script%3E"));
//]]></script>
<script language="JavaScript" type="text/javascript">
  TrustLogo("https://www.positivessl.com/images/seals/positivessl_trust_seal_lg_222x54.png", "POSDV", "none");
</script>
 -->
</body>
</html>