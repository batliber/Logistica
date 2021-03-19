<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Unidad Indexada</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./unidad_indexada_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./unidad_indexada_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardar" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarUnidadIndexada"><input type="submit" id="inputEliminarUnidadIndexada" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Vigente hasta:</div><div id="divUnidadIndexadaFechaVigenciaHasta" class="divFormValue"><input type="text" id="inputUnidadIndexadaVigenciaHasta"/></div>
		<div class="divFormLabelExtended">Valor:</div><div id="divUnidadIndexadaValor" class="divFormValue"><input type="text" id="inputUnidadIndexadaValor"/></div>
	</div>
</body>
</html>