<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Monitoreo</title>
	<script type="text/javascript" src="./formulario_contrato.js"></script>
	<script type="text/javascript" src="./monitoreo.js"></script>
	<link rel="stylesheet" type="text/css" href="./formulario_dinamico.css"/>
	<link rel="stylesheet" type="text/css" href="./monitoreo.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
				<div class="divButton"><input type="submit" id="inputAgregarMid" value="Agregar MID" onclick="javascript:inputAgregarMidOnClick(event, this)"/></div>
				<div class="divButton"><input type="submit" id="inputSubirArchivo" value="Subir archivo" onclick="javascript:inputSubirArchivoOnClick(event, this)"/></div>
				<div class="divButton"><input type="submit" id="inputSubirArchivoURSEC" value="Subir URSEC" onclick="javascript:inputSubirArchivoURSECOnClick(event, this)"/></div>
				<form method="post" id="formExportarAExcel" action="#"></form>
				<div class="divButton" id="divButtonExportarAExcel"><input type="submit" id="inputExportarAExcel" value="Exportar a Excel" onclick="javascript:inputExportarAExcelOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleQuintupleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divContratos">
					<div id="divTableContratos">&nbsp;</div>
				</div>
			</div>
		</div>
	</div>
	<div id="divIFrameContrato" style="display:none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Contrato</div>
			<div id="divCloseIFrameContrato" class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divFormularioContrato" style="display: none;">&nbsp;</div>
		<iframe id="iFrameContrato" src="about:blank"></iframe>
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
		<iframe id="iFrameHistorico" src="about:blank"></iframe>
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
			<div class="divPopupWindow">
				<form id="formSubirArchivo" method="POST" action="/LogisticaWEB/Upload" enctype="multipart/form-data">
					<input type="hidden" name="caller" value="/LogisticaWEB/pages/monitoreo/monitoreo.jsp"/>
					<div class="divFormLabelExtended">Empresa:</div><div id="divEmpresa" class="divFormValue"><select id="selectEmpresa" name="selectEmpresa"></select></div>
					<div class="divFormLabelExtended">Archivo:</div><div id="divArchivo" class="divFormValue"><input type="file" id="inputArchivo" name="inputArchivo"/></div>
				</form>
			</div>
		</div>
	</div>
	<div id="divIFrameImportacionArchivoURSEC" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Importaci&oacute;n archivo</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divImportacionArchivoURSEC">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Aceptar" onclick="javascript:inputAceptarURSECOnClick(event)"/></div>
				<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarURSECOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divPopupWindow">
				<form id="formSubirArchivoURSEC" method="POST" action="/LogisticaWEB/Upload" enctype="multipart/form-data">
					<input type="hidden" name="caller" value="/LogisticaWEB/pages/monitoreo/ursec.jsp"/>
					<div class="divFormLabelExtended">Archivo:</div><div id="divArchivoURSEC" class="divFormValue"><input type="file" id="inputArchivoURSEC" name="inputArchivoURSEC"/></div>
				</form>
			</div>
		</div>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>