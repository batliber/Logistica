<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Contrato</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="./contrato.js"></script>
	<script type="text/javascript" src="./contrato_acciones.js"></script>
	<link rel="stylesheet" type="text/css" href="./contrato.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton" id="divInputAgendar"><input type="submit" value="Agendar" onclick="javascript:inputAgendarOnClick(event)"/></div>
		<div class="divButton" id="divInputPosponer"><input type="submit" value="Rellamar" onclick="javascript:inputPosponerOnClick(event)"/></div>
		<div class="divButton" id="divInputDistribuir"><input type="submit" value="Distribuir" onclick="javascript:inputDistribuirOnClick(event)"/></div>
		<div class="divButton" id="divInputRechazar"><input type="submit" value="Rechazar" onclick="javascript:inputRechazarOnClick(event)"/></div>
		<div class="divButton" id="divInputRedistribuir"><input type="submit" value="Re-distribuir" onclick="javascript:inputRedistribuirOnClick(event)"/></div>
		<div class="divButton" id="divInputTelelink"><input type="submit" value="Telelink" onclick="javascript:inputTelelinkOnClick(event)"/></div>
		<div class="divButton" id="divInputRenovo"><input type="submit" value="Renovó" onclick="javascript:inputRenovoOnClick(event)"/></div>
		<div class="divButton" id="divInputActivar"><input type="submit" value="Activar" onclick="javascript:inputActivarOnClick(event)"/></div>
		<div class="divButton" id="divInputNoFirma"><input type="submit" value="No firma" onclick="javascript:inputNoFirmaOnClick(event)"/></div>
		<div class="divButton" id="divInputRecoordinar"><input type="submit" value="Re-coordinar" onclick="javascript:inputRecoordinarOnClick(event)"/></div>
		<div class="divButton" id="divInputEnviarAAntel"><input type="submit" value="Enviar a ANTEL" onclick="javascript:inputEnviarAAntelOnClick(event)"/></div>
		<div class="divButton" id="divInputAgendarActivacion"><input type="submit" value="Agendar activaci&oacute;n" onclick="javascript:inputAgendarActivacionOnClick(event)"/></div>
		<div class="divButton" id="divInputTerminar"><input type="submit" value="Terminar" onclick="javascript:inputTerminarOnClick(event)"/></div>
		<div class="divButton" id="divInputReagendar"><input type="submit" value="Reagendar" onclick="javascript:inputReagendarOnClick(event)"/></div>
		<div class="divButton" id="divInputFaltaDocumentacion"><input type="submit" value="Falta documentación" onclick="javascript:inputFaltaDocumentacionOnClick(event)"/></div>
		<div class="divButton" id="divInputReActivar"><input type="submit" value="Activar" onclick="javascript:inputReActivarOnClick(event)"/></div>
		<div class="divButton" id="divInputNoRecoordina"><input type="submit" value="No recoordina" onclick="javascript:inputNoRecoordinaOnClick(event)"/></div>
		<div class="divButton" id="divInputCerrar"><input type="submit" value="Cerrar" onclick="javascript:inputCerrarOnClick(event)"/></div>
		<div class="divButton" id="divInputGestionInterna"><input type="submit" value="Gestión interna" onclick="javascript:inputGestionInternaOnClick(event)"/></div>
		<div class="divButton" id="divInputGestionDistribucion"><input type="submit" value="Gestión distribución" onclick="javascript:inputGestionDistribucionOnClick(event)"/></div>
		<div class="divButton" id="divInputEquipoPerdido"><input type="submit" value="Equipo perdido" onclick="javascript:inputEquipoPerdidoOnClick(event)"/></div>
		<div class="divButton" id="divInputFacturaImpaga"><input type="submit" value="Factura impaga" onclick="javascript:inputFacturaImpagaOnClick(event)"/></div>
		<div class="divButton" id="divInputEnviadoANucleo"><input type="submit" value="Enviado a Núcleo" onclick="javascript:inputEnviadoANucleoOnClick(event)"/></div>
		<div class="divButton" id="divInputCanceladoPorCliente"><input type="submit" value="Cancelado por Cliente" onclick="javascript:inputCanceladoPorClienteOnClick(event)"/></div>
		<div class="divButton" id="divInputEquiposPagos"><input type="submit" value="Equipos pagos" onclick="javascript:inputEquiposPagosOnClick(event)"/></div>
		<div class="divButton" id="divInputEquipoDevuelto"><input type="submit" value="Equipo devuelto" onclick="javascript:inputEquipoDevueltoOnClick(event)"/></div>
		<div class="divButton" id="divInputNoRecuperado"><input type="submit" value="No recuperado" onclick="javascript:inputNoRecuperadoOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
		<div class="divButton" id="divInputEstadoRiesgoCrediticio"><input type="submit" value="Estado Riesgo" onclick="javascript:inputEstadoRiesgoCrediticioOnClick(event)"/></div>
		<div class="divButton" id="divInputImprimirKit"><input type="submit" value="Imprimir kit" onclick="javascript:inputImprimirKitOnClick(event)"/></div>
		<div class="divButton" id="divInputImprimirKitANTEL"><input type="submit" value="Imprimir kit" onclick="javascript:inputImprimirKitANTELOnClick(event)"/></div>
		<div class="divButton" id="divInputImprimirContrato"><input type="submit" value="Imprimir contrato" onclick="javascript:inputImprimirContratoOnClick(event)"/></div>
		<div class="divButton" id="divInputImprimirPagare"><input type="submit" value="Imprimir pagare" onclick="javascript:inputImprimirPagareOnClick(event)"/></div>
		<div class="divButton" id="divInputImprimirRemito"><input type="submit" value="Imprimir remito" onclick="javascript:inputImprimirRemitoOnClick(event)"/></div>
		<div class="divButton" id="divInputImprimirAdjuntos"><input type="submit" value="Imprimir adjuntos" onclick="javascript:inputImprimirAdjuntosOnClick(event)"/></div>
		<div class="divButton" id="divInputGuardar"><input type="submit" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleFourfoldSize" class="divButtonTitleBarTitle">Procesos</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
		<div id="divButtonTitleTripleSize" class="divButtonTitleBarTitle">Acciones</div>
	</div>
	<div class="divPopupWindow">
		<div class="divLayoutColumn">
			<div class="divFormLabelExtended" id="divLabelEmpresa">Empresa:</div><div id="divEmpresa" class="divFormValue"><select id="selectEmpresa"></select></div>
			<div class="divFormLabelExtended" id="divLabelVendedor">Vendedor:</div><div id="divVendedor" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelBackoffice">Back-office:</div><div id="divBackoffice" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelDistribuidor">Distribuidor:</div><div id="divDistribuidor" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelActivador">Activador:</div><div id="divActivador" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelCoordinador">Coordinador:</div><div id="divCoordinador" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelRol">Rol:</div><div id="divRol" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelUsuario">Usuario:</div><div id="divUsuario" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelNumeroTramite">Tr&aacute;mite:</div><div id="divNumeroTramite" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelAntelNroTrn">Nro. orden:</div><div id="divAntelNroTrn" class="divFormValue"><input type="text" id="inputAntelNroTrn"/></div>
			<div class="divFormLabelExtended" id="divLabelAntelFormaPago">Forma de pago:</div><div id="divAntelFormaPago" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelAntelNroServicioCuenta">Nro servicio cuenta:</div><div id="divAntelNroServicioCuenta" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelAntelImporte">Importe:</div><div id="divAntelImporte" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelMid">MID:</div><div id="divMid" class="divFormValue"><input type="text" id="inputMid"/></div>
			<div class="divFormLabelExtended" id="divLabelEstado">Estado:</div><div id="divEstado" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelRandom">Random:</div><div id="divRandom" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaVenta">Fecha de venta:</div><div id="divFechaVenta" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaVentaMostrar">Fecha de venta:</div><div id="divFechaVentaMostrar" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaBackoffice">Fecha de backoffice:</div><div id="divFechaBackoffice" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaEntregaDistribuidor">Fecha de entrega distribuidor:</div><div id="divFechaEntregaDistribuidor" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaDevolucionDistribuidor">Fecha de devoluci&oacute;n distribuidor:</div><div id="divFechaDevolucionDistribuidor" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaEnvioAntel">Fecha de env&iacute;o a ANTEL:</div><div id="divFechaEnvioAntel" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaActivacion">Fecha de activaci&oacute;n:</div><div id="divFechaActivacion" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaCoordinacion">Fecha de cordinaci&oacute;n:</div><div id="divFechaCoordinacion" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaEnvioANucleo">Fecha de env. N&uacute;cleo:</div><div id="divFechaEnvioANucleo" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelResultadoEntregaDistribucionFecha">Fecha de resultado entrega:</div><div id="divResultadoEntregaDistribucionFecha" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaPickUp">Fecha de pick-up:</div><div id="divFechaPickUp" divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelLocalidad">Localidad:</div><div id="divLocalidad" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelCodigoPostal">C&oacute;digo postal:</div><div id="divCodigoPostal" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaVencimiento">Fecha de vencimiento:</div><div id="divFechaVencimiento" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelNumeroContrato">N&uacute;mero de contrato:</div><div id="divNumeroContrato" class="divFormValue"><input type="text" id="inputNumeroContrato"/></div>
			<div class="divFormLabelExtended" id="divLabelPlan">Plan:</div><div id="divPlan" class="divFormValue"><input type="text" id="inputPlan"/></div>
			<div class="divFormLabelExtended" id="divLabelNuevoPlanString">Nuevo plan (digit):</div><div id="divNuevoPlanString" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelModalidadVenta">Modalidad de venta:</div><div id="divModalidadVenta" class="divFormValue"><select id="selectModalidadVenta"></select></div>
			<div class="divFormLabelExtended" id="divLabelNuevoPlan">Nuevo plan:</div><div id="divNuevoPlan" class="divFormValue"><select id="selectNuevoPlan"></select></div>
			<div class="divFormLabelExtended" id="divLabelMotivoCambioPlan">Motivo cambio plan:</div><div id="divMotivoCambioPlan" class="divFormValue"><select id="selectMotivoCambioPlan"></select></div>
			<div class="divFormLabelExtended" id="divLabelTipoProducto">Tipo de producto:</div><div id="divTipoProducto" class="divFormValue"><select id="selectTipoProducto" onchange="javascript:selectTipoProductoOnChange(event, this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelEquipo">Equipo:</div><div id="divEquipo" class="divFormValue"><select id="selectEquipo" onchange="javascript:selectEquipoOnChange(event, this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelIncluirChip">Inclu&iacute;r chip:</div><div id="divIncluirChip" class="divFormValue"><input type="checkbox" id="inputIncluirChip"/></div>
			<div class="divFormLabelExtended" id="divLabelCostoEnvio">Costo env&iacute;o:</div><div id="divCostoEnvio" class="divFormValue"><input type="text" id="inputCostoEnvio"/></div>
			<div class="divFormLabelExtended" id="divLabelMoneda">Moneda:</div><div id="divMoneda" class="divFormValue"><select id="selectMoneda" onchange="javascript:selectMonedaOnChange(event, this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelPrecio">Precio:</div><div id="divPrecio" class="divFormValue"><input type="text" id="inputPrecio" onchange="javascript:inputPrecioOnChange(event, this)"/></div>
			<div class="divFormLabelExtended" id="divLabelTipoTasaInteresEfectivaAnual">Tipo TEA:</div><div id="divTipoTasaInteresEfectivaAnual" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFormaPago">Forma de pago:</div><div id="divFormaPago" class="divFormValue"><select id="selectFormaPago" onchange="javascript:selectFormaPagoOnChange(this)"></select><div id="divRealizarClearing">Realizar clearing.</div></div>
			<div class="divFormLabelExtended" id="divLabelTarjetaCredito">Tarjeta de cr&eacute;dito:</div><div id="divTarjetaCredito" class="divFormValue"><select id="selectTarjetaCredito" onchange="javascript:selectTarjetaCreditoOnChange(this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelCuotas">Cuotas:</div><div id="divCuotas" class="divFormValue"><select id="selectCuotas" onchange="javascript:selectCuotasOnChange(event, this)"></select><div class="divFormLabel" id="divLabelValorCuota">Cuota:</div><div id="divValorCuota" class="divFormValue"><input type="text" id="inputValorCuota"/></div></div>
			<div class="divFormLabelExtended" id="divLabelNumeroVale">Vale:</div><div id="divNumeroVale" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelGastosAdministrativos">Gastos administrativos:</div><div id="divGastosAdministrativos" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelGastosConcesion">Gastos concesi&oacute;n:</div><div id="divGastosConcesion" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelGastosAdministrativosTotales">Gastos administrativos totales:</div><div id="divGastosAdministrativosTotales" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelIntereses">Intereses:</div><div id="divIntereses" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelUnidadIndexada">Unidad Indexada:</div><div id="divUnidadIndexada" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelTasaInteresEfectivaAnual">TEA:</div><div id="divTasaInteresEfectivaAnual" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelNumeroFactura" id="divLabelNumeroFactura">N&uacute;mero factura:</div><div id="divNumeroFactura" class="divFormValue"><input type="text" id="inputNumeroFactura"/></div>
			<div class="divFormLabelExtended" id="divLabelNumeroFacturaRiverGreen" id="divLabelNumeroFacturaRiverGreen">N&uacute;m. fac. River Green:</div><div id="divNumeroFacturaRiverGreen" class="divFormValue"><input type="text" id="inputNumeroFacturaRiverGreen"/><input type="checkbox" id="inputFacturaRiverGreen" onclick="javascript:inputFacturaRiverGreenOnClick(event, this)"/></div>
			<div class="divFormLabelExtended" id="divLabelNumeroSerie" id="divLabelNumeroSerie">Nº de serie:</div><div id="divNumeroSerie" class="divFormValue"><input type="text" id="inputNumeroSerie" onchange="javascript:inputNumeroSerieOnChange(event, this)"/></div>
			<div class="divFormLabelExtended" id="divLabelNumeroChip" id="divLabelNumeroChip">Nº de chip:</div><div id="divNumeroChip" class="divFormValue"><input type="text" id="inputNumeroChip"/></div>
			<div class="divFormLabelExtended" id="divLabelNumeroBloqueo" id="divLabelNumeroBloqueo">C&oacute;d. de bloqueo:</div><div id="divNumeroBloqueo" class="divFormValue"><input type="text" id="inputNumeroBloqueo"/></div>
			<div class="divFormLabelExtended" id="divLabelResultadoEntregaDistribucion">Resultado entrega:</div><div id="divResultadoEntregaDistribucion" class="divFormValue">&nbsp;</div>
			<!--  <div class="divFormLabelExtended" id="divLabelResultadoEntregaDistribucionDocumentos">Documentos:</div><div id="divResultadoEntregaDistribucionDocumentos" class="divFormValue">&nbsp;</div>  -->
			<div class="divFormLabelExtended" id="divLabelResultadoEntregaDistribucionLatitud">Latitud:</div><div id="divResultadoEntregaDistribucionLatitud" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelResultadoEntregaDistribucionLongitud">Longitud:</div><div id="divResultadoEntregaDistribucionLongitud" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelResultadoEntregaDistribucionPrecision">Precisi&oacute;n:</div><div id="divResultadoEntregaDistribucionPrecision" class="divFormValue">&nbsp;</div>
		</div>
		<div class="divLayoutColumn">
			<div class="divFormLabelExtended" id="divLabelTipoDocumento">Tipo de documento:</div><div id="divTipoDocumento" class="divFormValue"><select id="selectTipoDocumento"></select></div>
			<div class="divFormLabelExtended" id="divLabelDocumento">Documento:</div><div id="divDocumento" class="divFormValue"><input type="text" id="inputDocumento" onchange="javascript:inputDocumentoOnChange(event, this)"/></div>
			<div class="divFormLabelExtended" id="divLabelNombre">Nombre:</div><div id="divNombre" class="divFormValue"><input type="text" id="inputNombre"/></div>
			<div class="divFormLabelExtended" id="divLabelApellido">Apellido:</div><div id="divApellido" class="divFormValue"><input type="text" id="inputApellido"/></div>
			<div class="divFormLabelExtended" id="divLabelFechaNacimiento">Fecha de nacimiento:</div><div id="divFechaNacimiento" class="divFormValue"><input type="text" id="inputFechaNacimiento"/></div>
			<div class="divFormLabelExtended" id="divLabelSexo">Sexo:</div><div id="divSexo" class="divFormValue"><select id="selectSexo"></select></div>
			<div class="divFormLabelExtended" id="divLabelTelefonoContacto">Tel&eacute;fono contacto:</div><div id="divTelefonoContacto" class="divFormValue"><input type="text" id="inputTelefonoContacto"/></div>
			<div class="divFormLabelExtended" id="divLabelEmail">E-Mail:</div><div id="divEmail" class="divFormValue"><input type="text" id="inputEmail"/></div>
			<div class="divFormLabelExtended" id="divLabelDepartamento">Departamento:</div><div id="divDepartamento" class="divFormValue"><select id="selectDepartamento" onchange="javascript:selectDepartamentoOnChange(this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelBarrio">Barrio:</div><div id="divBarrio" class="divFormValue"><select id="selectBarrio" onchange="javascript:selectBarrioOnChange(this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelZona">Zona:</div><div id="divZona" class="divFormValue"><select id="selectZona" onchange="javascript:selectZonaOnChange(this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelTurno">Turno:</div><div id="divTurno" class="divFormValue"><select id="selectTurno" onchange="javascript:selectTurnoOnChange(this)"></select></div>
			<div class="divFormLabelExtended" id="divLabelFechaEntrega">Fecha de entrega:</div><div id="divFechaEntrega" class="divFormValue"><select id="selectFechaEntrega"></select></div>
			<div class="divFormLabelExtended" id="divLabelFechaActivarEn">Activar en:</div><div id="divFechaActivarEn" class="divFormValue"><input type="text" id="inputFechaActivarEn"/></div>
			<div class="divFormLabelExtended" id="divLabelObservaciones">Observaciones:</div><div id="divObservaciones" class="divFormValue"><textarea id="textareaObservaciones"></textarea></div>
			<div class="divFormLabelExtended" id="divLabelResultadoEntregaDistribucionObservaciones">Obs. entrega:</div><div id="divResultadoEntregaDistribucionObservaciones" class="divFormValue"><textarea id="textareaResultadoEntregaDistribucionObservaciones"></textarea></div>
		</div>
		<div style="float: left;width: 100%;height: 21px;">&nbsp;</div>
		<div class="divTabbedPanel">
			<div class="divTabHeader">
				<div class="divTabTitleSelected" id="divTabTitle1">Direcci&oacute;n de entrega</div>
				<div class="divTabTitle" id="divTabTitle2">Direcci&oacute;n de env&iacute;o de factura</div>
				<div class="divTabTitle" id="divTabTitle3">Archivos adjuntos</div>
				<div class="divTabTitle" id="divTabTitle4">Tr&aacute;mites relacionados</div>
				<div class="divTabTitleFiller" id="divTabTitle5">&nbsp;</div>
			</div>
			<div class="divTab" id="divTab1">
				<div class="divLayoutColumnFull">
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaCalle">Calle:</div><div id="divDireccionEntregaCalle" class="divFormValue"><input type="text" id="inputDireccionEntregaCalle"/></div>
				</div>
				<div class="divLayoutColumn">
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaNumero">N&uacute;mero:</div><div id="divDireccionEntregaNumero" class="divFormValue"  style="width: 125px;"><input type="text" id="inputDireccionEntregaNumero"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaBis">Bis:</div><div id="divDireccionEntregaBis" class="divFormValue" style="width: 130px;"><input type="checkbox" id="inputDireccionEntregaBis"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaApto">Apto.:</div><div id="divDireccionEntregaApto" class="divFormValue"><input type="text" id="inputDireccionEntregaApto"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaManzana">Manzana:</div><div id="divDireccionEntregaManzana" class="divFormValue"><input type="text" id="inputDireccionEntregaManzana"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaCodigoPostal">C.P.:</div><div id="divDireccionEntregaCodigoPostal" class="divFormValue"><input type="text" id="inputDireccionEntregaCodigoPostal"/></div>
				</div>
				<div class="divLayoutColumn">
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaBlock">Block:</div><div id="divDireccionEntregaBlock" class="divFormValue"><input type="text" id="inputDireccionEntregaBlock"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaSolar">Solar:</div><div id="divDireccionEntregaSolar" class="divFormValue"><input type="text" id="inputDireccionEntregaSolar"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaDepartamento">Departamento:</div><div id="divDireccionEntregaDepartamento" class="divFormValue"><select id="selectDireccionEntregaDepartamento"></select></div>
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaLocalidad">Localidad:</div><div id="divDireccionEntregaLocalidad" class="divFormValue"><input type="text" id="inputDireccionEntregaLocalidad"/></div>
				</div>
				<div class="divLayoutColumnFull">
					<div class="divFormLabelExtended" id="divLabelDireccionEntregaObservaciones">Observaciones:</div><div id="divDireccionEntregaObservaciones" class="divFormValue"><input type="text" id="inputDireccionEntregaObservaciones"/></div>
				</div>
			</div>
			<div class="divTab" id="divTab2">
				<div class="divLayoutColumnFull">
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaCalle">Calle:</div><div id="divDireccionFacturaCalle" class="divFormValue"><input type="text" id="inputDireccionFacturaCalle"/></div>
				</div>
				<div class="divLayoutColumn">
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaNumero">N&uacute;mero:</div><div id="divDireccionFacturaNumero" class="divFormValue" style="width: 125px;"><input type="text" id="inputDireccionFacturaNumero"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaBis">Bis:</div><div id="divDireccionFacturaBis" class="divFormValue" style="width: 130px;"><input type="checkbox" id="inputDireccionFacturaBis"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaApto">Apto.:</div><div id="divDireccionFacturaApto" class="divFormValue"><input type="text" id="inputDireccionFacturaApto"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaManzana">Manzana:</div><div id="divDireccionFacturaManzana" class="divFormValue"><input type="text" id="inputDireccionFacturaManzana"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaCodigoPostal">C.P.:</div><div id="divDireccionFacturaCodigoPostal" class="divFormValue"><input type="text" id="inputDireccionFacturaCodigoPostal"/></div>
				</div>
				<div class="divLayoutColumn">
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaBlock">Block:</div><div id="divDireccionFacturaBlock" class="divFormValue"><input type="text" id="inputDireccionFacturaBlock"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaSolar">Solar:</div><div id="divDireccionFacturaSolar" class="divFormValue"><input type="text" id="inputDireccionFacturaSolar"/></div>
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaDepartamento">Departamento:</div><div id="divDireccionFacturaDepartamento" class="divFormValue"><select id="selectDireccionFacturaDepartamento"></select></div>
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaLocalidad">Localidad:</div><div id="divDireccionFacturaLocalidad" class="divFormValue"><input type="text" id="inputDireccionFacturaLocalidad"/></div>
				</div>
				<div class="divLayoutColumnFull">
					<div class="divFormLabelExtended" id="divLabelDireccionFacturaObservaciones">Observaciones:</div><div id="divDireccionFacturaObservaciones" class="divFormValue"><input type="text" id="inputDireccionFacturaObservaciones"/></div>
				</div>
			</div>
			<div class="divTab" id="divTab3">
				<div class="divLayoutColumn" style="width: 85%;">
					<div class="divFormLabel" id="divLabelTipoArchivoAdjunto">Tipo:</div><div class="divFormValue" id="divTipoArchivoAdjunto"><select id="selectTipoArchivoAdjunto"></select></div>
					<div class="divFormLabel" id="divLabelAgregarAdjunto">Archivo:</div><div class="divFormValue" id="divAgregarAdjunto"><form id="formArchivo" method="POST" action="/LogisticaWEB/Upload"><input type="file" id="inputAdjunto" name="inputAdjunto"/><input type="button" id="inputAgregarAdjunto" value="" onclick="javascript:inputAgregarAdjuntoOnClick(event, this)"/></form></div>
					<div id="divTableArchivosAdjuntos">&nbsp;</div>
				</div>
				<div class="divLayoutColumn" style="width: 15%;">
					<div id="divResultadoEntregaDistribucionDocumentos">&nbsp;</div>
				</div>
			</div>
			<div class="divTab" id="divTab4">
				<div class="divLayoutColumn" style="width: 100%;">
					<div class="divFormLabel" id="divLabelNumeroTramiteRelacionado">Nro. Tr&aacute;mite:</div><div class="divFormValue" id="divNumeroTramiteRelacionado"><input id="inputNumeroTramiteRelacionado"/></div><div><input type="button" id="inputAgregarContratoRelacionado" value="" onclick="javascript:inputAgregarContratoRelacionadoOnClick(event, this)"/></div>
					<div id="divTableContratosRelacionados">&nbsp;</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>