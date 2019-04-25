<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="uy.com.amensg.logistica.dwr.*" %>
<%@ page import="uy.com.amensg.logistica.util.*" %>
<%@ page import="uy.com.amensg.logistica.entities.ContratoTO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Pagar&eacute;</title>
	<script type="text/javascript">
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./contrato_preimpreso_pagare_print.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./contrato_preimpreso_print.css"/>
	<link rel="stylesheet" type="text/css" href="./contrato_preimpreso_pagare_print.css"/>
</head>
<%
	ContratoTO contratoTO = new ContratoDWR().getById(new Long(request.getParameter("cid")));
%>
<body>
	<div class="divPrintingButtonBar">
		<div class="divButtonBar">
			<div class="divButton"><input type="submit" value="Imprimir" onclick="javascript:inputImprimirOnClick(event, this)"></div>
			<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarOnClick(event, this)"></div>
		</div>
	</div>
<%
	int vias = 2; 
	for (int i = 0; i<vias; i++) {
%>
	<div class="divA4Sheet">
		<div class="divA4SheetContent">
			<div class="divPageHeading">&nbsp;</div>
			<div class="divPageContent">
				<div class="divTexto">
					Vale n&uacute;mero: <input type="text" class="inputValeNumero"/>
					<br/>
					Capital prestado: <input type="text" class="inputCapitalPrestado"/>
					<br/>
					Intereses compensatorios: <input type="text" class="inputInteresesCompensatorios"/>
					<br/>
					Gastos de administraci&oacute;n: <input type="text" class="inputGastosAdministracion"/>
					<br/>
					IVA: <input type="text" class="inputIVA"/>
					<br/>
					Total $U: <input type="text" class="inputTotal"/>
				</div>
				<div class="divTitulo">&nbsp;</div>
				<div class="divTexto" style="height: 70px;">
					<input type="text" class="inputDepartamento"/>, <input type="text" class="inputDia"/> de <input type="text" class="inputMes"/> de <input type="text" class="inputAno"/>.
				</div>
				<div class="divTexto">
					VALE POR LA CANTIDAD DE PESOS URUGUAYOS $U <input type="text" class="inputTotal"/> que debo/debemos y pagar&eacute;/pagaremos en forma indivisible y solidaria a <input type="text" class="inputAgente"/> o a su orden en <input type="text" class="inputCuotas"/> cuotas mensuales y consecutivas de $U <input type="text" class="inputValorCuota"/> cada una, venciendo la primera de ellas el d&iacute;a <input type="text" class="inputFechaVencimientoPrimeraCuota"/> y las restantes siguientes los mismos d&iacute;as de los meses sucesivos. Los pagos se realizar&aacute;n en <input type="text" class="inputFormaPagoCuota"/>.
				</div>
				<div class="divTexto">
					La parte deudora caer&aacute; en mora de pleno derecho por el solo vencimiento de los plazos acordados de cualquiera de las cuotas pactadas, sin necesidad de interpelaci&oacute;n judicial o extrajudicial de especie alguna, asumiendo los gastos de aviso de atraso y de la gesti&oacute;n extrajudicial de cobranza de deuda, de acuerdo a la legislaci&oacute;n vigente al respecto. La falta de pago de una cuota o m&aacute;s cuotas se har&aacute; exigible el saldo total adeudado, caducando todos los plazos pendientes. Se pacta una tasa efectiva moratoria que se calcula sobre las cuotas vencidas y no pagas del <input type="text" class="inputPorcentajeMora"/>% anual.
				</div>
				<div class="divTexto" style="height: 50px;">
					Para las acciones judiciales ser&aacute;n competentes los jueces del domicilio que elija el tenedor de este documento. Fijando el acreedor domicilio en <input type="text" class="inputDomicilioAcreedor"/>.
				</div>
				<div class="divTexto">
					Tasa efectiva anual (%): <input type="text" class="inputTasaEfectivaAnual"/>
					<br/>
					Tasa efectiva mensual (%): <input type="text" class="inputTasaEfectivaMensual"/>
					<br/>
					Gastos administrativos en UI por cuota: <input type="text" class="inputGastosAdministrativosUIPorCuota"/>
				</div>
				<div class="divTexto">
					C&eacute;dula de identidad: <input type="text" class="inputCI"/>
					<br/>
					<br/>
					Domicilio: <input type="text" class="inputDomicilio"/>
					<br/>
					<br/>
					Localidad/Departamento: <input type="text" class="inputLocalidadDepartamento"/>
					<br/>
					<br/>
					Firma: <input type="text" class="inputFirma"/>
					<br/>
					<br/>
					Aclaraci&oacute;n: <input type="text" class="inputAclaracion"/>
				</div>
			</div>
			<div class="divPageFooter">&nbsp;</div>
		</div>
	</div>
<%
	}
%>
</body>
</html>