<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Tasa de Inter&eacute;s Efectiva Anual</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/TasaInteresEfectivaAnualDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./tasa_interes_efectiva_anual_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./tasa_interes_efectiva_anual_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardar" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarTasaInteresEfectivaAnual"><input type="submit" id="inputEliminarTasaInteresEfectivaAnual" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Cuotas desde:</div><div id="divTasaInteresEfectivaAnualCuotasDesde" class="divFormValue"><input type="text" id="inputTasaInteresEfectivaAnualCuotasDesde"/></div>
		<div class="divFormLabelExtended">Cuotas hasta:</div><div id="divTasaInteresEfectivaAnualCuotasHasta" class="divFormValue"><input type="text" id="inputTasaInteresEfectivaAnualCuotasHasta"/></div>
		<div class="divFormLabelExtended">Monto desde:</div><div id="divTasaInteresEfectivaAnualMontoDesde" class="divFormValue"><input type="text" id="inputTasaInteresEfectivaAnualMontoDesde"/></div>
		<div class="divFormLabelExtended">Monto hasta:</div><div id="divTasaInteresEfectivaAnualMontoHasta" class="divFormValue"><input type="text" id="inputTasaInteresEfectivaAnualMontoHasta"/></div>
		<div class="divFormLabelExtended">Valor:</div><div id="divTasaInteresEfectivaAnualValor" class="divFormValue"><input type="text" id="inputTasaInteresEfectivaAnualValor"/></div>
		<div class="divFormLabelExtended">Vigente hasta:</div><div id="divTasaInteresEfectivaAnualFechaVigenciaHasta" class="divFormValue"><input type="text" id="inputTasaInteresEfectivaAnualVigenciaHasta"/></div>
	</div>
</body>
</html>