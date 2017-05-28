<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Plan</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/PlanDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./plan_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./plan_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarPlan" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarPlan"><input type="submit" id="inputEliminarPlan" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Plan</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtraExtended" id="divLabelPlanDescripcion">Descripci&oacute;n:</div><div id="divPlanDescripcion"><input type="text" id="inputPlanDescripcion"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanAbreviacion">Abreviaci&oacute;n:</div><div id="divPlanAbreviacion"><input type="text" id="inputPlanAbreviacion"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanConsumoMinimo">Consumo m&iacute;nimo:</div><div id="divPlanConsumoMinimo"><input type="text" id="inputPlanConsumoMinimo"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanDuracion">Duraci&oacute;n contractual:</div><div id="divPlanDuracion"><input type="text" id="inputPlanDuracion"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioMinutoDestinosAntelHorarioNormal">Precio minuto a destinos ANTEL en horario normal:</div><div id="divPlanPrecioMinutoDestinosAntelHorarioNormal"><input type="text" id="inputPlanPrecioMinutoDestinosAntelHorarioNormal"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioMinutoDestinosAntelHorarioReducido">Precio minuto a destinos ANTEL en horario reducido:</div><div id="divPlanPrecioMinutoDestinosAntelHorarioReducido"><input type="text" id="inputPlanPrecioMinutoDestinosAntelHorarioReducido"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanRendimientoMinutosMensualDestinosAntelHorarioNormal">Rendimiento en minutos a destinos ANTEL en horario normal:</div><div id="divPlanRendimientoMinutosMensualDestinosAntelHorarioNormal"><input type="text" id="inputPlanRendimientoMinutosMensualDestinosAntelHorarioNormal"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanRendimientoMinutosMensualDestinosAntelHorarioReducido">Rendimiento en minutos a destinos ANTEL en horario reducido:</div><div id="divPlanRendimientoMinutosMensualDestinosAntelHorarioReducido"><input type="text" id="inputPlanRendimientoMinutosMensualDestinosAntelHorarioReducido"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioMinutoOtrasOperadoras">Precio minuto a otras operadoras:</div><div id="divPlanPrecioMinutoOtrasOperadoras"><input type="text" id="inputPlanPrecioMinutoOtrasOperadoras"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanRendimientoMinutosMensualOtrasOperadoras">Rendimiento en minutos a otras operadoras:</div><div id="divPlanRendimientoMinutosMensualOtrasOperadoras"><input type="text" id="inputPlanRendimientoMinutosMensualOtrasOperadoras"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioSms">Precio SMS:</div><div id="divPlanPrecioSms"><input type="text" id="inputPlanPrecioSms"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanMontoNavegacionCelular">GB incluidos para navegaci&oacute;n:</div><div id="divPlanMontoNavegacionCelular"><input type="text" id="inputPlanMontoNavegacionCelular"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioConsumoFueraBono">Precio por consumo fuera de bono:</div><div id="divPlanPrecioConsumoFueraBono"><input type="text" id="inputPlanPrecioConsumoFueraBono"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanTopeFacturacionMensualTraficoDatos">Tope de facturaci&oacute;n mensual por tr&aacute;fico de datos:</div><div id="divPlanTopeFacturacionMensualTraficoDatos"><input type="text" id="inputPlanTopeFacturacionMensualTraficoDatos"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanDestinosGratis">Destinos gratis:</div><div id="divPlanDestinosGratis"><input type="text" id="inputPlanDestinosGratis"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanMinutosGratisMesCelularesAntel">Minutos gratis al mes a celulares ANTEL:</div><div id="divPlanMinutosGratisMesCelularesAntel"><input type="text" id="inputPlanMinutosGratisMesCelularesAntel"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanCantidadCelularesAntelMinutosGratis">Cantidad de celulares minutos gratis al mes:</div><div id="divPlanCantidadCelularesAntelMinutosGratis"><input type="text" id="inputPlanCantidadCelularesAntelMinutosGratis"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanSmsGratisMesCelularesAntel">SMSs gratis al mes a celulares ANTEL:</div><div id="divPlanSmsGratisMesCelularesAntel"><input type="text" id="inputPlanSmsGratisMesCelularesAntel"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanCantidadCelularesAntelSmsGratis">Cantidad de celulares SMSs gratis al mes:</div><div id="divPlanCantidadCelularesAntelSmsGratis"><input type="text" id="inputPlanCantidadCelularesAntelSmsGratis"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanMinutosGratisMesFijosAntel">Minutos gratis al mes a tel&eacute;fonos fijos ANTEL:</div><div id="divPlanMinutosGratisMesFijosAntel"><input type="text" id="inputPlanMinutosGratisMesFijosAntel"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanCantidadFijosAntelMinutosGratis">Cantidad de tel&eacute;fonos fijos minutos gratis al mes:</div><div id="divPlanCantidadFijosAntelMinutosGratis"><input type="text" id="inputPlanCantidadFijosAntelMinutosGratis"/></div>		
	</div>
</body>
</html>