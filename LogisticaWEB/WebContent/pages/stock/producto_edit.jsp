<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Equipo</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./producto_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./producto_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarProducto" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarProducto"><input type="submit" id="inputEliminarProducto" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Equipo</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Descripci&oacute;n:</div><div id="divProductoDescripcion" class="divFormValue"><input type="text" id="inputProductoDescripcion"/></div>
		<div class="divFormLabelExtended">Marca:</div><div id="divProductoMarca" class="divFormValue"><select id="selectProductoMarca"/></select></div>
		<div class="divFormLabelExtended">Service:</div><div id="divProductoEmpresaService" class="divFormValue"><select id="selectProductoEmpresaService"/></select></div>
	</div>
</body>
</html>