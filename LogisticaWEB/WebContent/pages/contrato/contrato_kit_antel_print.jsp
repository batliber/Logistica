<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Contrato</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="./contrato_kit_antel_print.js"></script>
	<link rel="stylesheet" type="text/css" href="./contrato_kit_antel_print.css"/>
</head>
<body>
	<div class="divSheet">
		<div class="divSheetContent">
			<div class="divHeader">
				Contrato
			</div>
			<div class="divRow">
				<div class="divColumn">
					<!--
					<div class="divFormLabelPrint" id="divLabelEmpresa">Empresa:</div><div id="divEmpresa" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelRol">Rol:</div><div id="divRol" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelUsuario">Usuario:</div><div id="divUsuario" class="divFormValuePrint">&nbsp;</div>
					-->
					
					<div class="divFormLabelPrint" id="divLabelNumeroTramite">Tr&aacute;mite:</div><div id="divNumeroTramite" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelNumeroTramiteBarCode">C&oacute;d. barras:</div><div class="divBarCode"><img id="imgNumeroTramiteBarCode" src="about:blank"></img><!-- <div id="divNumeroTramiteBarCode" class="divBarCode">&nbsp;</div> --></div>
					<div class="divFormLabelPrint" id="divLabelDepartamento">Depto.:</div><div class="divFormValuePrint divDepartamento">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelZona">Zona:</div><div class="divFormValuePrint divZona">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelBarrio">Barrio:</div><div class="divFormValuePrint divBarrio">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelBarrio">Localidad:</div><div class="divFormValuePrint divDireccionEntregaLocalidad">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelDireccionEntrega">Dir.:</div><div id="divDireccionEntrega" class="divFormValuePrint">&nbsp;</div>
					<!-- 
					<div class="divFormLabelPrint" id="divLabelDireccionEntregaNumero">N&uacute;mero:</div><div id="divDireccionEntregaNumero" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelDireccionEntregaApto">Apto.:</div><div id="divDireccionEntregaApto" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelDireccionEntregaManzana">Manzana:</div><div id="divDireccionEntregaManzana" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelDireccionEntregaSolar">Solar:</div><div id="divDireccionEntregaSolar" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelDireccionEntregaCodigoPostal">C.P.:</div><div id="divDireccionEntregaCodigoPostal" class="divFormValuePrint">&nbsp;</div>
					 -->
					<div class="divFormLabelPrint divDireccionEntregaObservaciones" id="divLabelDireccionEntregaObservaciones">Obs.:</div><div id="divDireccionEntregaObservaciones" class="divFormValuePrint divDireccionEntregaObservaciones">&nbsp;</div>
					
					<!-- 
					<div class="divFormLabelPrint" id="divLabelEstado">Estado:</div><div id="divEstado" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelFechaVenta">Fecha de venta:</div><div id="divFechaVenta" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelLocalidad">Localidad:</div><div id="divLocalidad" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelCodigoPostal">C&oacute;digo postal:</div><div id="divCodigoPostal" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelNumeroContrato">N&uacute;mero de contrato:</div><div id="divNumeroContrato" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelNumeroSerie" id="divLabelNumeroSerie">Nº de serie:</div><div id="divNumeroSerie" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelNumeroFactura" id="divLabelNumeroFactura">N&uacute;mero factura:</div><div id="divNumeroFactura" class="divFormValuePrint">&nbsp;</div>
					-->
					
					<div class="divFormLabelPrint" id="divLabelMid">MID:</div><div id="divMid" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelMidBarCode">C&oacute;d. barras:</div><div class="divBarCode"><img id="imgMidBarCode" src="about:blank"></img><!-- <div id="divMidBarCode" class="divBarCode">&nbsp;</div> --></div>
					<div class="divFormLabelPrint" id="divLabelNombre">Nombre:</div><div id="divNombre" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelDocumento">Documento:</div><div id="divDocumento" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelTelefonoContacto">Tel.:</div><div id="divTelefonoContacto" class="divFormValuePrint">&nbsp;</div>
					<!--
					<div class="divFormLabelPrint" id="divLabelCostoEnvio">Costo de env&iacute;o:</div><div id="divCostoEnvio" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelNumeroFacturaRiverGreen">No. fac. River Green:</div><div id="divNumeroFacturaRiverGreen" class="divFormValueDoubleHeightPrint">&nbsp;</div>
					 -->
					<div class="divFormLabelPrint" id="divLabelTurno">Turno:</div><div id="divTurno" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelFechaEntrega">Fecha ent.:</div><div id="divFechaEntrega" class="divFormValuePrint">&nbsp;</div>
					<!--
					<div class="divFormLabelPrint" id="divLabelPrecio">Precio:</div><div id="divPrecio" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelFormaPago">Forma de pago:</div><div id="divFormaPago" class="divFormValuePrint">&nbsp;</div>
					 -->
					<div class="divFormLabelPrint divObservaciones" id="divLabelObservaciones">Obs:</div><div id="divObservaciones" class="divFormValuePrint divObservaciones">&nbsp;</div>
					
					<!--
					<div class="divFormLabel" id="divLabelEmail">E-Mail:</div><div id="divEmail" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabel" id="divLabelFechaActivarEn" id="divLabelFechaActivarEn">Activar en:</div><div id="divFechaActivarEn" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelPlan">Plan anterior:</div><div id="divPlan" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelNuevoPlan">Nuevo plan:</div><div id="divNuevoPlan" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelDireccionFactura">Direcci&oacute;n factura:</div><div id="divDireccionFactura" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelVendedor">Vendedor:</div><div id="divVendedor" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelIncluirChip">Inclu&iacute;r chip:</div><div id="divIncluirChip" class="divFormValuePrint">&nbsp;</div>
					
					<div class="divFormLabelPrint" id="divLabelFechaNacimiento">Fecha de nac.:</div><div id="divFechaNacimiento" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelFechaVencimiento">Fecha de venc.:</div><div id="divFechaVencimiento" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelEquipo">Equipo:</div><div id="divEquipo" class="divFormValuePrint">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelDepartamento">Departamento:</div><div class="divFormValuePrint divDepartamento">&nbsp;</div>
					<div class="divFormLabelPrint" id="divLabelBarrio">Barrio:</div><div class="divFormValuePrint divBarrio">&nbsp;</div>
					 -->
				</div>
			</div>
		</div>
	</div>
</body>
</html>