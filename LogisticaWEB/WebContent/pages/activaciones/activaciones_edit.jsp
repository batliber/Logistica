<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Activaci&oacute;n</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./activaciones_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./activaciones_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarActivacion" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Empresa:</div><div class="divFormValue" id="divEmpresa">&nbsp;</div>
		<div class="divFormLabelExtended">MID:</div><div class="divFormValue" id="divMid">&nbsp;</div>
		<div class="divFormLabelExtended">Fecha de importaci&oacute;n:</div><div class="divFormValue" id="divFechaImportacion">&nbsp;</div>
		<div class="divFormLabelExtended">Lote:</div><div class="divFormValue" id="divActivacionLote">&nbsp;</div>
		<div class="divFormLabelExtended">Fecha de activaci&oacute;n:</div><div class="divFormValue" id="divFechaActivacion"><input type="text" id="inputFechaActivacion"/></div>
		<div class="divFormLabelExtended">Fecha de vencimiento:</div><div class="divFormValue" id="divFechaVencimiento">&nbsp;</div>
		<div class="divFormLabelExtended">Chip:</div><div class="divFormValue" id="divChip">&nbsp;</div>
		<div class="divFormLabelExtended">Tipo de activaci&oacute;n:</div><div class="divFormValue" id="divTipoActivacion">&nbsp;</div>
		<div class="divFormLabelExtended">Estado de activaci&oacute;n:</div><div class="divFormValue" id="divEstadoActivacion"><select id="selectEstadoActivacion"></select></div>
	</div>
</body>
</html>