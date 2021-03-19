<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Rol</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./rol_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./rol_edit.css"/>
	
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarRol" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarRol"><input type="submit" id="inputEliminarRol" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Men&uacute;</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Nombre:</div><div id="divRolNombre" class="divFormValue"><input type="text" id="inputRolNombre"/></div>
		<div class="divFormLabelExtended" id="divLabelAgregarMenu">Menu:</div><div class="divFormValue" id="divMenu"><select id="selectMenu"></select><input type="button" id="inputAgregarMenu" value="" onclick="javascript:inputAgregarMenuOnClick(event, this)"/></div>
		<div class="divFormLabel" id="divLabelAgregarSubordinado">Rol:</div><div class="divFormValue" id="divRol"><select id="selectRol"></select><input type="button" id="inputAgregarRol" value="" onclick="javascript:inputAgregarRolOnClick(event, this)"/></div>
		<div class="divFormLabelExtended">Men&uacute;s:</div>
		<div id="divTableMenus">&nbsp;</div>
		<div class="divFormLabel">Subordinados:</div>
		<div id="divTableSubordinados">&nbsp;</div>
	</div>
</body>
</html>