<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Asignaci&oacute;n de sub-lotes</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioRolEmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/DepartamentoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/BarrioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/PuntoVentaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EstadoActivacionDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/TipoActivacionDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ActivacionDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ActivacionSubloteDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/menu.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/activaciones_sublote/activaciones_sublotes_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/menu.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/activaciones_sublote/activaciones_sublotes_edit.css"/>
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
		<!-- <div class="divFormLabelExtended" id="divLabelPuntoVenta">Punto de venta:</div><div id="divPuntoVenta" class="divFormValue"><input type="text" id="inputPuntoVenta" keyPress=""/></div>  -->
		<div class="divFormLabelExtended" id="divLabelChip">Chip:</div><div id="divChip" class="divFormValue"><input type="text" id="inputChip" onchange="javascript:inputChipOnChange(event, this)"/></div>
		<div class="divFormLabelExtended">&nbsp;</div><div class="divFormValue">&nbsp;</div>
		<div id="divTableActivaciones">&nbsp;</div>
	</div>
</body>
</html>