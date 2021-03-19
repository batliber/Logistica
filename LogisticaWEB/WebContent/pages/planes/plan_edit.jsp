<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Plan</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./plan_edit.js"></script>
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
		<div class="divFormLabelExtraExtended" id="divLabelPlanTipoPlan">Tipo:</div><div id="divPlanTipoPlan" class="divFormValue"><select id="selectPlanTipoPlan"></select></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanDescripcion">Descripci&oacute;n:</div><div id="divPlanDescripcion" class="divFormValue"><input type="text" id="inputPlanDescripcion"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanAbreviacion">Abreviaci&oacute;n:</div><div id="divPlanAbreviacion" class="divFormValue"><input type="text" id="inputPlanAbreviacion"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanConsumoMinimo">Consumo m&iacute;nimo:</div><div id="divPlanConsumoMinimo" class="divFormValue"><input type="text" id="inputPlanConsumoMinimo"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanDuracion">Duraci&oacute;n contractual:</div><div id="divPlanDuracion" class="divFormValue"><input type="text" id="inputPlanDuracion"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioMinutoDestinosAntelHorarioNormal">Precio minuto a destinos ANTEL:</div><div id="divPlanPrecioMinutoDestinosAntelHorarioNormal" class="divFormValue"><input type="text" id="inputPlanPrecioMinutoDestinosAntelHorarioNormal"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioMinutoDestinosAntelHorarioReducido">Precio minuto a destinos ANTEL en horario reducido:</div><div id="divPlanPrecioMinutoDestinosAntelHorarioReducido" class="divFormValue"><input type="text" id="inputPlanPrecioMinutoDestinosAntelHorarioReducido"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanRendimientoMinutosMensualDestinosAntelHorarioNormal">M&aacute;ximo rendimiento en minutos:</div><div id="divPlanRendimientoMinutosMensualDestinosAntelHorarioNormal" class="divFormValue"><input type="text" id="inputPlanRendimientoMinutosMensualDestinosAntelHorarioNormal"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanRendimientoMinutosMensualDestinosAntelHorarioReducido">Rendimiento en minutos a destinos ANTEL en horario reducido:</div><div id="divPlanRendimientoMinutosMensualDestinosAntelHorarioReducido" class="divFormValue"><input type="text" id="inputPlanRendimientoMinutosMensualDestinosAntelHorarioReducido"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanBeneficioIncluidoEnLlamadas">Beneficios (amigos) incluidos en llamadas :</div><div id="divPlanBeneficioIncluidoEnLlamadas" class="divFormValue"><input type="checkbox" id="inputPlanBeneficioIncluidoEnLlamadas"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioMinutoNumerosAmigos">Precio minuto n&uacute;meros amigos:</div><div id="divPlanPrecioMinutoNumerosAmigos" class="divFormValue"><input type="text" id="inputPlanPrecioMinutoNumerosAmigos"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioMinutoOtrasOperadoras">Precio minuto a otras operadoras:</div><div id="divPlanPrecioMinutoOtrasOperadoras" class="divFormValue"><input type="text" id="inputPlanPrecioMinutoOtrasOperadoras"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanRendimientoMinutosMensualOtrasOperadoras">Rendimiento en minutos a otras operadoras:</div><div id="divPlanRendimientoMinutosMensualOtrasOperadoras" class="divFormValue"><input type="text" id="inputPlanRendimientoMinutosMensualOtrasOperadoras"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioSms">Precio SMS:</div><div id="divPlanPrecioSms" class="divFormValue"><input type="text" id="inputPlanPrecioSms"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanMontoNavegacionCelular">GB incluidos para navegaci&oacute;n:</div><div id="divPlanMontoNavegacionCelular" class="divFormValue"><input type="text" id="inputPlanMontoNavegacionCelular"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPrecioConsumoFueraBono">Precio por consumo fuera de bono:</div><div id="divPlanPrecioConsumoFueraBono" class="divFormValue"><input type="text" id="inputPlanPrecioConsumoFueraBono"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanTopeFacturacionMensualTraficoDatos">Tope de facturaci&oacute;n mensual por tr&aacute;fico de datos:</div><div id="divPlanTopeFacturacionMensualTraficoDatos" class="divFormValue"><input type="text" id="inputPlanTopeFacturacionMensualTraficoDatos"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanDestinosGratis">Destinos gratis:</div><div id="divPlanDestinosGratis" class="divFormValue"><input type="text" id="inputPlanDestinosGratis"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanMinutosGratisMesCelularesAntel">Minutos gratis al mes a celulares ANTEL:</div><div id="divPlanMinutosGratisMesCelularesAntel" class="divFormValue"><input type="text" id="inputPlanMinutosGratisMesCelularesAntel"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanCantidadCelularesAntelMinutosGratis">Cantidad de celulares minutos gratis al mes:</div><div id="divPlanCantidadCelularesAntelMinutosGratis" class="divFormValue"><input type="text" id="inputPlanCantidadCelularesAntelMinutosGratis"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanSmsGratisMesCelularesAntel">SMSs gratis al mes a celulares ANTEL:</div><div id="divPlanSmsGratisMesCelularesAntel" class="divFormValue"><input type="text" id="inputPlanSmsGratisMesCelularesAntel"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanCantidadCelularesAntelSmsGratis">Cantidad de celulares SMSs gratis al mes:</div><div id="divPlanCantidadCelularesAntelSmsGratis" class="divFormValue"><input type="text" id="inputPlanCantidadCelularesAntelSmsGratis"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanMinutosGratisMesFijosAntel">Minutos gratis al mes a tel&eacute;fonos fijos ANTEL:</div><div id="divPlanMinutosGratisMesFijosAntel" class="divFormValue"><input type="text" id="inputPlanMinutosGratisMesFijosAntel"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanCantidadFijosAntelMinutosGratis">Cantidad de tel&eacute;fonos fijos minutos gratis al mes:</div><div id="divPlanCantidadFijosAntelMinutosGratis" class="divFormValue"><input type="text" id="inputPlanCantidadFijosAntelMinutosGratis"/></div>
		<div class="divFormLabelExtraExtended" id="divLabelPlanPiePagina">Pie de p&aacute;gina:</div><div id="divPlanPiePagina" class="divFormValue"><input type="text" id="inputPlanPiePagina"/></div>		
	</div>
</body>
</html>