<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Configuraci&oacute;n</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./configuracion_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./configuracion_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarConfiguracion" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarConfiguracion"><input type="submit" id="inputEliminarConfiguracion" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Configuraci&oacute;n</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Clave:</div><div id="divConfiguracionClave" class="divFormValue"><input type="text" id="inputConfiguracionClave"/></div>
		<div class="divFormLabelExtended">Valor:</div><div id="divConfiguracionValor" class="divFormValue"><input type="text" id="inputConfiguracionValor"/></div>
	</div>
</body>
</html>