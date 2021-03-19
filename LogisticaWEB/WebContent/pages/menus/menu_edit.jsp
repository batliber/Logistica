<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Men&uacute;</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./menu_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./menu_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarMenu" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarMenu"><input type="submit" id="inputEliminarMenu" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Men&uacute;</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">T&iacute;tulo:</div><div id="divMenuTitulo" class="divFormValue"><input type="text" id="inputMenuTitulo"/></div>
		<div class="divFormLabelExtended">URL:</div><div id="divMenuURL" class="divFormValue"><input type="text" id="inputMenuURL"/></div>
		<div class="divFormLabelExtended">Orden:</div><div id="divMenuOrden" class="divFormValue"><input type="text" id="inputMenuOrden"/></div>
		<div class="divFormLabelExtended">Padre:</div><div id="divMenuPadre" class="divFormValue"><select id="selectMenuPadre"></select></div>
	</div>
</body>
</html>