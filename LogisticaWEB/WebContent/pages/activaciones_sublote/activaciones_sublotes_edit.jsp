<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Asignaci&oacute;n de sub-lotes</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./activaciones_sublotes_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./activaciones_sublotes_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton"><input type="submit" value="Imprimir" onclick="javascript:inputImprimirOnClick(event, this)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended" id="divLabelNumero">N&uacute;mero:</div><div id="divNumero" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended" id="divLabelEmpresa">Empresa:</div><div id="divEmpresa" class="divFormValue"><!-- <select id="selectEmpresa"></select> -->&nbsp;</div>
		<div class="divFormLabelExtended" id="divLabelNumero">Descripci&oacute;n:</div><div id="divDescripcion" class="divFormValue"><input type="text" id="inputDescripcion"/></div>
		<div class="divFormLabelExtended" id="divLabelFechaAsignacionDistribuidor">Fecha asign. Distr.:</div><div id="divFechaAsignacionDistribuidor" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended" id="divLabelFechaAsignacionPuntoVenta">Fecha asign. Pto. Vta.:</div><div id="divFechaAsignacionPuntoVenta" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended" id="divLabelDistribuidor">Distribuidor:</div><div id="divDistribuidor" class="divFormValue"><select id="selectDistribuidor"></select></div>
		<div class="divFormLabelExtended" id="divLabelPuntoVentaDepartamento">Departamento:</div><div id="divPuntoVentaDepartamento" class="divFormValue"><select id="selectPuntoVentaDepartamento" onchange="javascript:selectPuntoVentaDepartamentoOnChange(event, this)"></select></div>
		<div class="divFormLabelExtended" id="divLabelPuntoVentaBarrio">Barrio:</div><div id="divPuntoVentaBarrio" class="divFormValue"><select id="selectPuntoVentaBarrio" onchange="javascript:selectPuntoVentaBarrioOnChange(event, this)"></select></div>
		<div class="divFormLabelExtended" id="divLabelPuntoVenta">Punto de venta:</div><div id="divPuntoVenta" class="divFormValue"><select id="selectPuntoVenta"></select></div>
		<div class="divFormLabelExtended" id="divLabelChip">Chip:</div><div id="divChip" class="divFormValue"><input type="text" id="inputChip" onchange="javascript:inputChipOnChange(event, this)"/></div>
		<div class="divFormLabelExtended">&nbsp;</div><div class="divFormValue">&nbsp;</div>
		<div id="divTableActivaciones">&nbsp;</div>
	</div>
</body>
</html>