<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Visita a Punto de Venta</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioRolEmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/PuntoVentaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EstadoVisitaPuntoVentaDistribuidorDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/VisitaPuntoVentaDistribuidorDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./visita_punto_venta_distribuidor_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./visita_punto_venta_distribuidor_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarVisitaPuntoVentaDistribuidor" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarVisitaPuntoVentaDistribuidor"><input type="submit" id="inputEliminarVisitaPuntoVentaDistribuidor" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Fecha de asignaci&oacute;n:</div><div id="divFechaAsignacion" class="divFormValue"><input type="text" id="inputFechaAsignacion"/></div>
		<div class="divFormLabelExtended">Punto de venta:</div><div id="divPuntoVenta" class="divFormValue"><select id="selectPuntoVenta"></select></div>
		<div class="divFormLabelExtended">Estado:</div><div id="divEstadoVisitaPuntoVentaDistribuidor" class="divFormValue"><select id="selectEstadoVisitaPuntoVentaDistribuidor"></select></div>
		<div class="divFormLabelExtended">Fecha de visita:</div><div id="divFechaVisita" class="divFormValue"><input type="text" id="inputFechaVisita"/></div>
		<div class="divFormLabelExtended">Distribuidor:</div><div id="divDistribuidor" class="divFormValue"><select id="selectDistribuidor"></select></div>
		<div class="divFormLabelExtended">Observaciones:</div><div id="divObservaciones" class="divFormValue"><textarea id="textareaObservaciones"></textarea></div>
	</div>
</body>
</html>