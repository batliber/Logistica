<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Contrato</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./contrato_print.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./contrato_print.css"/>
</head>
<body>
	<div class="divA4Sheet">
		<div class="divA4SheetContent">
			<div class="divHeader">
				Contrato
			</div>
			<div class="divColumn">
				<!--
				<div class="divFormLabelExtended" id="divLabelEmpresa">Empresa:</div><div id="divEmpresa" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelRol">Rol:</div><div id="divRol" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelUsuario">Usuario:</div><div id="divUsuario" class="divFormValue">&nbsp;</div>
				-->
				<div class="divFormLabelExtended" id="divLabelNumeroTramite">Tr&aacute;mite:</div><div id="divNumeroTramite" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelNumeroTramiteBarCode">C&oacute;digo de barras:</div><div class="divFormValue"><div id="divNumeroTramiteBarCode" class="divBarCode">&nbsp;</div></div>
				<div class="divFormLabelExtended" id="divLabelMid">MID:</div><div id="divMid" class="divFormValue">&nbsp;</div>
				<!-- 
				<div class="divFormLabelExtended" id="divLabelEstado">Estado:</div><div id="divEstado" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelFechaVenta">Fecha de venta:</div><div id="divFechaVenta" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelLocalidad">Localidad:</div><div id="divLocalidad" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelCodigoPostal">C&oacute;digo postal:</div><div id="divCodigoPostal" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelFechaVencimiento">Fecha de vencimiento:</div><div id="divFechaVencimiento" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelNumeroContrato">N&uacute;mero de contrato:</div><div id="divNumeroContrato" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelPlan">Plan:</div><div id="divPlan" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelNuevoPlan">Nuevo plan:</div><div id="divNuevoPlan" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelEquipo">Equipo:</div><div id="divEquipo" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelNumeroSerie" id="divLabelNumeroSerie">Nº de serie:</div><div id="divNumeroSerie" class="divFormValue">&nbsp;</div>
				-->
				<div class="divFormLabelExtended" id="divLabelDocumento">Documento:</div><div id="divDocumento" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelNombre">Nombre:</div><div id="divNombre" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelNumeroFactura" id="divLabelNumeroFactura">N&uacute;mero factura:</div><div id="divNumeroFactura" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelDireccionFactura">Direcci&oacute;n factura:</div><div id="divDireccionFactura" class="divFormValue">&nbsp;</div>
			</div>
			<div class="divColumn">
				<div class="divFormLabelExtended" id="divLabelDireccionEntrega">Direcci&oacute;n entrega:</div><div id="divDireccionEntrega" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelTelefonoContacto">Tel&eacute;fono contacto:</div><div id="divTelefonoContacto" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelEmail">E-Mail:</div><div id="divEmail" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelPrecio">Precio:</div><div id="divPrecio" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelDepartamento">Departamento:</div><div id="divDepartamento" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelZona">Zona:</div><div id="divZona" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelTurno">Turno:</div><div id="divTurno" class="divFormValue">&nbsp;</div>
				<!--
				<div class="divFormLabelExtended" id="divLabelFechaEntrega">Fecha de entrega:</div><div id="divFechaEntrega" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelFechaActivarEn" id="divLabelFechaActivarEn">Activar en:</div><div id="divFechaActivarEn" class="divFormValue">&nbsp;</div>
				 -->
				<div class="divFormLabelExtended" id="divLabelObservaciones">Observaciones:</div><div id="divObservaciones" class="divFormValue">&nbsp;</div>
			</div>
		</div>
	</div>
</body>
</html>