<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Login</title>
	<script type="text/javascript">
		var requestedPage = "<%= request.getAttribute("requested_page") != null ? (String)request.getAttribute("requested_page") : "" %>";
	</script>
	<script type="text/javascript" src="/LogisticaWEB/pages/cambio_password_forced/cambio_password_forced.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/cambio_password_forced/cambio_password_forced.css"/>
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