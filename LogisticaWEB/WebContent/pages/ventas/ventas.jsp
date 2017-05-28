<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Ventas</title>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioRolEmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EstadoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ModeloDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/FormaPagoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ZonaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ResultadoEntregaDistribucionDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/menu.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/ventas/ventas.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/menu.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/ventas/ventas.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
<<<<<<< HEAD
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonAsignar"><input type="submit" id="inputAsignar" value="Asignar" onclick="javascript:inputAsignarOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonSubirArchivo"><input type="submit" id="inputSubirArchivo" value="Subir archivo" onclick="javascript:inputSubirArchivoOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonAgregarMid"><input type="submit" id="inputAgregarMid" value="Agregar MID" onclick="javascript:inputAgregarMidOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonExportarAExcel">
					<form method="post" id="formExportarAExcel" action="#"><input type="submit" id="inputExportarAExcel" value="Exportar a Excel" onclick="javascript:inputExportarAExcelOnClick(event, this)"/></form>
				</div>
				<div class="divButton" id="divButtonExportarAExcelNucleo">
					<form method="post" id="formExportarAExcelNucleo" action="#"><input type="submit" id="inputExportarAExcelNucleo" value="Excel para Nucleo" onclick="javascript:inputExportarAExcelNucleoOnClick(event, this)"/></form>
				</div>
				<div class="divButtonBarSeparator">&nbsp;</div>
=======
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
		<div class="divButton" id="divButtonAsignar"><input type="submit" id="inputAsignar" value="Asignar" onclick="javascript:inputAsignarOnClick(event, this)"/></div>
		<div class="divButton" id="divButtonSubirArchivo"><input type="submit" id="inputSubirArchivo" value="Subir archivo" onclick="javascript:inputSubirArchivoOnClick(event, this)"/></div>
		<div class="divButton" id="divButtonAgregarMid"><input type="submit" id="inputAgregarMid" value="Agregar MID" onclick="javascript:inputAgregarMidOnClick(event, this)"/></div>
		<div class="divButton" id="divButtonExportarAExcel">
			<form method="post" id="formExportarAExcel" action="#"><input type="submit" id="inputExportarAExcel" value="Exporta a Excel" onclick="javascript:inputExportarAExcelOnClick(event, this)"/></form>
		</div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divMainWindow">
		<div id="divFiltros">
			<div class="divFormLabelExtended">Tama&ntilde;o de muestra:</div>
			<div id="divTamanoMuestra"><input type="text" id="inputTamanoMuestra" value="50" onchange="javascript:grid.filtroDinamico.tamanoMuestraOnChange(event)"/></div>
			<div class="divFormLabelExtended">Tama&ntilde;o subconjunto:</div>
			<div id="divTamanoSubconjunto"><input type="text" id="inputTamanoSubconjunto" value="1" onchange="javascript:grid.filtroDinamico.tamanoSubconjuntoOnChange(event)"/></div>
			<div id="divAgregarFiltroContainer">
				<div class="divFormLabelExtended">Agregar filtro:</div>
				<div id="divAgregarFiltro"><input type="submit" value="Agregar" id="inputAgregarFiltro" onclick="javascript:grid.filtroDinamico.agregarFiltro(event, this)"/></div>
				<div class="divFormLabelExtended">Limpiar filtros:</div>
				<div id="divLimpiarFiltros"><input type="submit" value="Limpiar" id="inputLimpiarFiltros" onclick="javascript:grid.filtroDinamico.limpiarFiltros(event, this)"/></div>
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divContratos">
					<div id="divTableContratos">&nbsp;</div>
				</div>
			</div>
		</div>
	</div>
	<div id="divIFrameContrato" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Contrato</div>
			<div id="divCloseIFrameContrato" class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameContrato" frameborder="0" src="#"></iframe>
	</div>
	<div id="divIFrameSeleccionVendedor" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Vendedor</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divSeleccionVendedor">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Aceptar" onclick="javascript:inputAceptarOnClick(event)"/></div>
				<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
<<<<<<< HEAD
			<div class="divPopupWindow">
=======
			<div class="divMainWindow">
>>>>>>> branch 'master' of https://github.com/batliber/Logistica.git
				<div class="divFormLabelExtended">Vendedor:</div><div id="divVendedor" class="divFormValue"><select id="selectVendedor"></select></div>
				<div class="divFormLabelExtended">Observaciones:</div><div id="divObservaciones" class="divFormValue"><textarea id="textareaObservaciones"></textarea></div>
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
					<input type="hidden" name="caller" value="/LogisticaWEB/pages/ventas/ventas.jsp"/>
					<div class="divFormLabelExtended">Empresa:</div><div id="divEmpresa" class="divFormValue"><select id="selectEmpresa" name="selectEmpresa"></select></div>
					<div class="divFormLabelExtended">Archivo:</div><div id="divArchivo" class="divFormValue"><input type="file" id="inputArchivo" name="inputArchivo"/></div>
				</form>
			</div>
		</div>
	</div>
	<div id="divModalBackgroundChild">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>