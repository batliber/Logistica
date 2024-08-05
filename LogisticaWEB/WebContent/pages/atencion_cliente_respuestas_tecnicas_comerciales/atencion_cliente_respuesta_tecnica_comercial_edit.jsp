<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Respuesta t&eacute;cnica/comercial</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./atencion_cliente_respuesta_tecnica_comercial_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./atencion_cliente_respuesta_tecnica_comercial_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarAtencionClienteRespuestaTecnicaComercial" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarAtencionClienteRespuestaTecnicaComercial"><input type="submit" id="inputEliminar" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Resp. t&eacute;c./com.</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Descripci&oacute;n:</div><div id="divAtencionClienteRespuestaTecnicaComercialDescripcion" class="divFormValue"><input type="text" id="inputAtencionClienteRespuestaTecnicaComercialDescripcion"/></div>
	</div>
</body>
</html>