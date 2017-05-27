<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Punto de venta</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/DepartamentoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/BarrioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/PuntoVentaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./punto_venta_edit.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?libraries=places"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./punto_venta_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarPuntoVenta" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarPuntoVenta"><input type="submit" id="inputEliminarPuntoVenta" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Nombre:</div><div id="divPuntoVentaNombre" class="divFormValue"><input type="text" id="inputPuntoVentaNombre"/></div>
		<div class="divFormLabelExtended">Departamento:</div><div id="divPuntoVentaDepartamento" class="divFormValue"><select id="selectPuntoVentaDepartamento" onchange="javascript:selectDepartamentoOnChange(event, this)"></select></div>
		<div class="divFormLabelExtended">Barrio:</div><div id="divPuntoVentaBarrio" class="divFormValue"><select id="selectPuntoVentaBarrio"></select></div>
		<div class="divFormLabelExtended">Direcci&oacute;n:</div><div id="divPuntoVentaDireccion" class="divFormValue"><input type="text" id="inputPuntoVentaDireccion"/></div>
		<div class="divFormLabelExtended">Tel&eacute;fono:</div><div id="divPuntoVentaTelefono" class="divFormValue"><input type="text" id="inputPuntoVentaTelefono"/></div>
		<div class="divFormLabelExtended">Contacto:</div><div id="divPuntoVentaContacto" class="divFormValue"><input type="text" id="inputPuntoVentaContacto"/></div>
		<div class="divFormLabelExtended">Documento:</div><div id="divPuntoVentaDocumento" class="divFormValue"><input type="text" id="inputPuntoVentaDocumento"/></div>
		<input type="text" id="inputBusqueda"/>
		<div id="divMapa">&nbsp;</div>
	</div>
</body>
</html>