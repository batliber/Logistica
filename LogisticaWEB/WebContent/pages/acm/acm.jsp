<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>ACM</title>
	<script type="text/javascript" src="/LogisticaWEB/pages/acm/acm.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/acm/acm.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
				<div class="divButton"><input type="submit" id="inputHabilitarAcciones" value="Habilitar acciones" onclick="javascript:inputHabilitarAccionesOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
				<form method="post" id="formExportarAExcel" action="#"></form>
				<div class="divButton" id="divButtonExportarAExcel"><input type="submit" id="inputExportarAExcel" value="Exportar a Excel" onclick="javascript:inputExportarAExcelOnClick(event, this)"/></div>
				<div class="divButton"><input type="submit" id="inputExportarSubconjunto" value="Exportar subconjunto" onclick="javascript:inputExportarSubconjuntoOnClick(event, this)"/></div>
				<div class="divButton"><input type="submit" id="inputAsignar" value="Asignar" onclick="javascript:inputAsignarOnClick(event, this)"/></div>
				<div class="divButton"><input type="submit" id="inputAsignarSubconjunto" value="Asignar subconjunto" onclick="javascript:inputAsignarSubconjuntoOnClick(event, this)"/></div>
				<div class="divButton"><input type="submit" id="inputDeshacerAsignacion" value="Deshacer asignaci&oacute;n" onclick="javascript:inputDeshacerAsignacionOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
				<div class="divButton"><input type="submit" id="inputReprocesar" value="Reprocesar" onclick="javascript:inputReprocesarOnClick(event, this)"/></div>
				<div class="divButton"><input type="submit" id="inputReprocesarSubconjunto" value="Reprocesar subconjunto" onclick="javascript:inputReprocesarSubconjuntoOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
				<div class="divButton"><input type="submit" id="inputControlarRiesgoCrediticio" value="Controlar riesgo" onclick="javascript:inputControlarRiesgoCrediticioOnClick(event, this)"/></div>
				<div class="divButton"><input type="submit" id="inputControlarRiesgoCrediticioSubconjunto" value="Controlar subconjunto" onclick="javascript:inputControlarRiesgoCrediticioSubconjuntoOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
				<div class="divButton"><input type="submit" id="inputListaNegra" value="Lista negra" onclick="javascript:inputListaNegraOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
				<div class="divButton"><input type="submit" id="inputSubirArchivo" value="Subir archivo" onclick="javascript:inputSubirArchivoOnClick(event, this)"/></div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
				<div id="divButtonTitleQuintupleSize" class="divButtonTitleBarTitle">Exportar</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Reprocesar</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Riesgo crediticio</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Lista negra</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Archivo</div>
			</div>
			<div class="divMainWindow">
				<div>
					<div class="divFormLabelExtended">Tipo de registro:</div>
					<div id="divTipoRegistro">
						<select id="selectTipoRegistro" onchange="javascript:selectTipoRegistroOnChange(event)">
							<option value="contrato">Contrato</option>
							<option value="prepago">Prepago</option>
							<option value="listaNegra">Lista negra</option>
							<option value="sinDatos">Sin datos</option>
							<option value="numeroContrato">Nros. contrato</option>
						</select>
					</div>
				</div>
				<div id="divTabla">&nbsp;</div>
			</div>
		</div>
	</div>
	<div id="divIFrameSeleccionEmpresa" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Empresa</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divSeleccionEmpresa">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Aceptar" onclick="javascript:inputAceptarOnClick(event)"/></div>
				<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divPopupWindow">
				<div class="divFormLabelExtended" style="display: none;">Tama&ntilde;o subconjunto:</div><div style="display: none;"><input type="hidden" id="inputTamanoSubconjuntoAsignacion"/></div>
				<div class="divFormLabelExtended">Empresa:</div><div id="divEmpresa" class="divFormValue"><select id="selectEmpresa"></select></div>
				<div class="divFormLabelExtended">Observaciones:</div><div id="divObservaciones" class="divFormValue"><textarea id="textareaObservaciones"></textarea></div>
			</div>
		</div>
	</div>
	<div id="divIFrameSeleccionTipoProcesamiento" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Tipo de procesamiento</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divSeleccionTipoProcesamiento">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Aceptar" onclick="javascript:inputAceptarSeleccionTipoProcesamientoOnClick(event)"/></div>
				<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarSeleccionTipoProcesamientoOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divPopupWindow">
				<div class="divFormLabelExtended">Tipo:</div>
				<div id="divTipoProcesamiento" class="divFormValue">
					<select id="selectTipoProcesamiento">
						<option value="0">Seleccione...</option>
						<option value="1">Por MID</option>
						<option value="2">Por nro. de contrato</option>
					</select>
				</div>
				<div class="divFormLabelExtended">Observaciones:</div><div id="divSeleccionTipoProcesamientoObservaciones" class="divFormValue"><textarea id="textareaSeleccionTipoProcesamientoObservaciones"></textarea></div>
			</div>
		</div>
	</div>
	<div id="divIFrameSeleccionTipoControlRiesgoCrediticio" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Riesgo crediticio</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divSeleccionTipoControlRiesgoCrediticio">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Aceptar" onclick="javascript:inputAceptarSeleccionTipoControlRiesgoCrediticioOnClick(event)"/></div>
				<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarSeleccionTipoControlRiesgoCrediticioOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divPopupWindow">
				<div class="divFormLabelExtended">Empresa:</div><div id="divTipoControlRiesgoCrediticioEmpresa" class="divFormValue"><select id="selectTipoControlRiesgoCrediticioEmpresa"></select></div>
				<div class="divFormLabelExtended">Tipo de control:</div><div id="divTipoControlRiesgoCrediticio" class="divFormValue"><select id="selectTipoControlRiesgoCrediticio"></select></div>
				<div class="divFormLabelExtended" style="display: none;">Tama&ntilde;o suconjunto:</div><div id="divTipoControlRiesgoCrediticioTamanoSubconjunto" class="divFormValue"><input type="hidden" id="inputTipoControlRiesgoCrediticioTamanoSubconjunto"/></div>
			</div>
		</div>
	</div>
	<div id="divIFrameImportacionArchivo" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Importaci&oacute;n archivo</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divImportacionArchivo">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Aceptar" onclick="javascript:inputAceptarSubirArchivoOnClick(event)"/></div>
				<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divPopupWindow">
				<form id="formSubirArchivo" method="POST" action="/LogisticaWEB/Upload" enctype="multipart/form-data">
					<input type="hidden" name="caller" value="/LogisticaWEB/pages/acm/acm.jsp"/>
					<div class="divFormLabelExtended">Archivo:</div><div id="divArchivo" class="divFormValue"><input type="file" id="inputArchivo" name="inputArchivo"/></div>
				</form>
			</div>
		</div>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>