<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Contrato</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
		var crhid = <%= request.getParameter("crhid") != null ? request.getParameter("crhid") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoRoutingHistoryDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioRolEmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/DepartamentoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/StockMovimientoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/TurnoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ZonaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/DisponibilidadEntregaEmpresaZonaTurnoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./contrato.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./contrato.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton" id="divInputAgendar"><input type="submit" value="Agendar" onclick="javascript:inputAgendarOnClick(event)"/></div>
		<div class="divButton" id="divInputRechazar"><input type="submit" value="Rechazar" onclick="javascript:inputRechazarOnClick(event)"/></div>
		<div class="divButton" id="divInputPosponer"><input type="submit" value="Rellamar" onclick="javascript:inputPosponerOnClick(event)"/></div>
		<div class="divButton" id="divInputDistribuir"><input type="submit" value="Distribuir" onclick="javascript:inputDistribuirOnClick(event)"/></div>
		<div class="divButton" id="divInputRedistribuir"><input type="submit" value="Re-distribuir" onclick="javascript:inputRedistribuirOnClick(event)"/></div>
		<div class="divButton" id="divInputTelelink"><input type="submit" value="Telelink" onclick="javascript:inputTelelinkOnClick(event)"/></div>
		<div class="divButton" id="divInputRenovo"><input type="submit" value="Renovó" onclick="javascript:inputRenovoOnClick(event)"/></div>
		<div class="divButton" id="divInputActivar"><input type="submit" value="Activar" onclick="javascript:inputActivarOnClick(event)"/></div>
		<div class="divButton" id="divInputNoFirma"><input type="submit" value="No firma" onclick="javascript:inputNoFirmaOnClick(event)"/></div>
		<div class="divButton" id="divInputRecoordinar"><input type="submit" value="Re-coordinar" onclick="javascript:inputRecoordinarOnClick(event)"/></div>
		<div class="divButton" id="divInputTerminar"><input type="submit" value="Terminar" onclick="javascript:inputTerminarOnClick(event)"/></div>
		<div class="divButton" id="divInputAgendarActivacion"><input type="submit" value="Agendar activaci&oacute;n" onclick="javascript:inputAgendarActivacionOnClick(event)"/></div>
		<div class="divButton" id="divInputReagendar"><input type="submit" value="Reagendar" onclick="javascript:inputReagendarOnClick(event)"/></div>
		<div class="divButton" id="divInputFaltaDocumentacion"><input type="submit" value="Falta documentación" onclick="javascript:inputFaltaDocumentacionOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
		<div class="divButton"><input type="submit" value="Imprimir" onclick="javascript:inputImprimirOnClick(event)"/></div>
		<div class="divButton"><input type="submit" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleFourfoldSize" class="divButtonTitleBarTitle">Proceso</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
		<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divMainWindow">
		<div class="divLayoutColumn">
			<div class="divFormLabelExtended" id="divLabelEmpresa">Empresa:</div><div id="divEmpresa" class="divFormValue"><select id="selectEmpresa"></select></div>
			<div class="divFormLabelExtended" id="divLabelRol">Rol:</div><div id="divRol" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelUsuario">Usuario:</div><div id="divUsuario" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelNumeroTramite">Tr&aacute;mite:</div><div id="divNumeroTramite" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelMid">MID:</div><div id="divMid" class="divFormValue"><input type="text" id="inputMid"/></div>
			<div class="divFormLabelExtended" id="divLabelEstado">Estado:</div><div id="divEstado" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaVenta">Fecha de venta:</div><div id="divFechaVenta" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelLocalidad">Localidad:</div><div id="divLocalidad" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelCodigoPostal">C&oacute;digo postal:</div><div id="divCodigoPostal" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaVencimiento">Fecha de vencimiento:</div><div id="divFechaVencimiento" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelNumeroContrato">N&uacute;mero de contrato:</div><div id="divNumeroContrato" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelPlan">Plan:</div><div id="divPlan" class="divFormValue"><input type="text" id="inputPlan"/></div>
			<div class="divFormLabelExtended" id="divLabelNuevoPlan">Nuevo plan:</div><div id="divNuevoPlan" class="divFormValue"><input type="text" id="inputNuevoPlan"/></div>
			<div class="divFormLabelExtended" id="divLabelEquipo">Equipo:</div><div id="divEquipo" class="divFormValue"><select id="selectEquipo"></select></div>
			<div class="divFormLabelExtended" id="divLabelNumeroSerie" id="divLabelNumeroSerie">Nº de serie:</div><div id="divNumeroSerie" class="divFormValue"><input type="text" id="inputNumeroSerie"/></div>
		</div>
		<div class="divLayoutColumn">
			<div class="divFormLabelExtended" id="divLabelDocumento">Documento:</div><div id="divDocumento" class="divFormValue"><input type="text" id="inputDocumento"/></div>
			<div class="divFormLabelExtended" id="divLabelNombre">Nombre:</div><div id="divNombre" class="divFormValue"><input type="text" id="inputNombre"/></div>
			<div class="divFormLabelExtended" id="divLabelNumeroFactura" id="divLabelNumeroFactura">N&uacute;mero factura:</div><div id="divNumeroFactura" class="divFormValue"><input type="text" id="inputNumeroFactura"/></div>
			<div class="divFormLabelExtended" id="divLabelDireccionFactura">Direcci&oacute;n factura:</div><div id="divDireccionFactura" class="divFormValue"><input type="text" id="inputDireccionFactura"/></div>
			<div class="divFormLabelExtended" id="divLabelDireccionEntrega">Direcci&oacute;n entrega:</div><div id="divDireccionEntrega" class="divFormValue"><input type="text" id="inputDireccionEntrega"/></div>
			<div class="divFormLabelExtended" id="divLabelTelefonoContacto">Tel&eacute;fono contacto:</div><div id="divTelefonoContacto" class="divFormValue"><input type="text" id="inputTelefonoContacto"/></div>
			<div class="divFormLabelExtended" id="divLabelEmail">E-Mail:</div><div id="divEmail" class="divFormValue"><input type="text" id="inputEmail"/></div>
			<div class="divFormLabelExtended" id="divLabelPrecio">Precio:</div><div id="divPrecio" class="divFormValue"><input type="text" id="inputPrecio"/></div>
			<div class="divFormLabelExtended" id="divLabelDepartamento">Departamento:</div><div id="divDepartamento" class="divFormValue"><select id="selectDepartamento" onchange="javascript:selectDepartamentoOnChange(this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelZona">Zona:</div><div id="divZona" class="divFormValue"><select id="selectZona" onchange="javascript:selectZonaOnChange(this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelTurno">Turno:</div><div id="divTurno" class="divFormValue"><select id="selectTurno" onchange="javascript:selectTurnoOnChange(this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelFechaEntrega">Fecha de entrega:</div><div id="divFechaEntrega" class="divFormValue"><select id="selectFechaEntrega"></select></div>
			<div class="divFormLabelExtended" id="divLabelFechaActivarEn" id="divLabelFechaActivarEn">Activar en:</div><div id="divFechaActivarEn" class="divFormValue"><input type="text" id="inputFechaActivarEn"/></div>
			<div class="divFormLabelExtended" id="divLabelObservaciones">Observaciones:</div><div id="divObservaciones" class="divFormValue"><textarea id="textareaObservaciones"></textarea></div>
		</div>
	</div>
</body>
</html>