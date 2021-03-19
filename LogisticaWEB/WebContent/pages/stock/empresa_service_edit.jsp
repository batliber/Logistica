<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Service</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./empresa_service_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./empresa_service_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarEmpresaService" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarEmpresaService"><input type="submit" id="inputEliminarEmpresaService" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Service</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divMainWindow">
		<div class="divFormLabelExtended">Nombre:</div><div id="divEmpresaServiceNombre" class="divFormValue"><input type="text" id="inputEmpresaServiceNombre"/></div>
		<div class="divFormLabelExtended">Direccion:</div><div id="divEmpresaServiceDireccion" class="divFormValue"><input type="text" id="inputEmpresaServiceDireccion"/></div>
		<div class="divFormLabelExtended">Tel&eacute;fono:</div><div id="divEmpresaServiceTelefono" class="divFormValue"><input type="text" id="inputEmpresaServiceTelefono"/></div>
	</div>
</body>
</html>