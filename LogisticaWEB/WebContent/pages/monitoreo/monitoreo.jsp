<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Monitoreo</title>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ProductoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/StockMovimientoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioRolEmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/RolDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/DepartamentoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/BarrioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ZonaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/TurnoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EstadoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/menu.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/formulario_dinamico.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/contrato/formulario_contrato.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/monitoreo/monitoreo.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/menu.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/formulario_dinamico.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/monitoreo/monitoreo.css"/>
</head>
<body>
	<div class="divMenuBar">
<%@ include file="/includes/menu.jsp" %>	
	</div>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
		<div class="divButton"><input type="submit" id="inputAgregarMid" value="Agregar MID" onclick="javascript:inputAgregarMidOnClick(event, this)"/></div>
		<div class="divButton"><input type="submit" id="inputSubirArchivo" value="Subir archivo" onclick="javascript:inputSubirArchivoOnClick(event, this)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleTripleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divMainWindow">
		<div id="divFiltros">
			<div class="divFormLabelExtended">Tama&ntilde;o de muestra:</div>
			<div id="divTamanoMuestra"><input type="text" id="inputTamanoMuestra" value="50" onchange="javascript:grid.filtroDinamico.tamanoMuestraOnChange(event)"/></div>
			<div class="divFormLabelExtended">Tama&ntilde;o subconjunto:</div>
			<div id="divTamanoSubconjunto"><input type="text" id="inputTamanoSubconjunto" value="500" onchange="javascript:grid.filtroDinamico.tamanoSubconjuntoOnChange(event)"/></div>
			<div id="divAgregarFiltroContainer">
				<div class="divFormLabelExtended">Agregar filtro:</div>
				<div id="divAgregarFiltro"><input type="submit" value="Agregar" id="inputAgregarFiltro" onclick="javascript:grid.filtroDinamico.agregarFiltro(event, this)"/></div>
			</div>
		</div>
		<div id="divContratos">
			<div id="divTableContratos">&nbsp;</div>
		</div>
	</div>
	<div id="divIFrameContrato" style="display:none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Contrato</div>
			<div id="divCloseIFrameContrato" class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divFormularioContrato" style="display: none;">&nbsp;</div>
		<iframe id="iFrameContrato" frameborder="0" src="#"></iframe>
		<!-- 
		<div>
			<div class="divButtonBar">
		-->
				<!-- 
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
				<div class="divButton" id="divInputTerminar"><input type="submit" value="Terminar" onclick="javascript:inputTerminarOnClick(event)"/></div>
				<div class="divButton" id="divInputAgendarActivacion"><input type="submit" value="Agendar activaci&oacute;n" onclick="javascript:inputAgendarActivacionOnClick(event)"/></div>
				<div class="divButton" id="divInputReagendar"><input type="submit" value="Reagendar" onclick="javascript:inputReagendarOnClick(event)"/></div>
				<div class="divButton" id="divInputFaltaDocumentacion"><input type="submit" value="Falta documentación" onclick="javascript:inputFaltaDocumentacionOnClick(event)"/></div>
				 -->
		 <!-- 
				<div class="divButtonBarSeparator">&nbsp;</div>
				<div class="divButton" id="divInputImprimir"><input type="submit" value="Imprimir" onclick="javascript:inputImprimirOnClick(event)"/></div>
				<div class="divButton" id="divInputGuardar"><input type="submit" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleFourfoldSize" class="divButtonTitleBarTitle">Procesos</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div id="divFormularioContrato">&nbsp;</div>
		</div>
		 -->
	</div>
	<div id="divIFrameHistoricoContrato" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Hist&oacute;rico</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameHistorico" frameborder="0" src="#"></iframe>
	</div>
	<div id="divIFrameImportacionArchivo" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Importaci&oacute;n archivo</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divImportacionArchivo">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Aceptar" onclick="javascript:inputAceptarOnClick(event)"/></div>
				<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<form id="formSubirArchivo" method="POST" action="/LogisticaWEB/Upload" enctype="multipart/form-data">
					<input type="hidden" name="caller" value="/LogisticaWEB/pages/monitoreo/monitoreo.jsp"/>
					<div class="divFormLabelExtended">Empresa:</div><div id="divEmpresa"><select id="selectEmpresa" name="selectEmpresa"></select></div>
					<div class="divFormLabelExtended">Archivo:</div><div id="divArchivo"><input type="file" id="inputArchivo" name="inputArchivo"/></div>
				</form>
			</div>
		</div>
	</div>
	<div id="divModalBackground">&nbsp;</div>
</body>
</html>