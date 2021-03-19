<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Acreditaci&oacute;n</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./acreditacion_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./acreditacion_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton" id="divInputAcreditar"><input type="submit" value="Acreditar" onclick="javascript:inputAcreditarOnClick(event)"/></div>
		<div class="divButton" id="divInputPreAprobar"><input type="submit" value="Pre-aprobar" onclick="javascript:inputPreAprobarOnClick(event)"/></div>
		<div class="divButton" id="divInputDenegar"><input type="submit" value="Denegar" onclick="javascript:inputDenegarOnClick(event)"/></div>
		<div class="divButton" id="divInputCredito"><input type="submit" value="Crédito" onclick="javascript:inputCreditoOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acreditaci&oacute;n</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Empresa:</div><div id="divRecargaEmpresa" class="divFormValue"><select id="selectRecargaEmpresa"></select></div>
		<div class="divFormLabelExtended">Estado:</div><div id="divRecargaEstadoAcreditacionSaldo" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Punto de Venta:</div><div id="divPuntoVenta" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Fecha solicitud:</div><div id="divFechaSolicitud" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Fecha pre-aprobaci&oacute;n:</div><div id="divFechaPreAprobacion" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Fecha acreditaci&oacute;n:</div><div id="divFechaAcreditacion" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Fecha denegaci&oacute;n:</div><div id="divFechaDenegacion" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Fecha cr&eacute;dito:</div><div id="divFechaCredito" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Monto:</div><div id="divMonto" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Observaciones:</div><div id="divObservaciones" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Distribuidor:</div><div id="divDistribuidor" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Forma de pago:</div><div id="divRecargaFormaPago" class="divFormValue"><select id="selectRecargaFormaPago"></select></div>
		<div class="divFormLabelExtended">Banco destino:</div><div id="divRecargaBanco" class="divFormValue"><select id="selectRecargaBanco"></select></div>
		<div class="divFormLabelExtended">Cajero:</div><div id="divCajero" class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">Cuentas:</div><div id="divCuentas" class="divFormValue">&nbsp;</div>
		<div id="divTableArchivosAdjuntos">&nbsp;</div>
	</div>
</body>
</html>