<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Instalaciones</title>
	<script type="text/javascript" src="./instalaciones.js"></script>
	<link rel="stylesheet" type="text/css" href="./instalaciones.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton" id="divButtonActualizar"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonAgregarMid"><input type="submit" id="inputAgregarMid" value="Agregar Tr&aacute;mite" onclick="javascript:inputAgregarMidOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonAsignar"><input type="submit" id="inputAsignar" value="Asignar" onclick="javascript:inputAsignarOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonNotificar"><input type="submit" id="inputNotificar" value="Notificar ANTEL" onclick="javascript:inputNotificarOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonNotificarEntrega"><input type="submit" id="inputNotificarEntrega" value="Notificar Entrega" onclick="javascript:inputNotificarEntregaOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonNotificarAPIStock"><input type="submit" id="inputNotificarAPIStock" value="Notificar GLA" onclick="javascript:inputNotificarAPIStockOnClick(event, this)"/></div>
				<form method="post" id="formExportarAExcel" action="#"></form>
				<div class="divButton" id="divButtonExportarAExcel"><input type="submit" id="inputExportarAExcel" value="Exporta a Excel" onclick="javascript:inputExportarAExcelOnClick(event, this)"/></div>
				<form method="post" id="formExportarReporteTiempos" action="#"></form>
				<div class="divButton" id="divButtonExportarReporteTiempos"><input type="submit" id="inputExportarReporteTiempos" value="Reporte tiempos" onclick="javascript:inputExportarReporteTiemposOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
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
		<iframe id="iFrameContrato" src="about:blank"></iframe>
	</div>
	<div id="divIFrameSeleccionDistribuidor" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Distribuidor</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divSeleccionDistribuidor">
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
				<div class="divFormLabelExtended">Distribuidor:</div><div id="divDistribuidor" class="divFormValue"><select id="selectDistribuidor"></select></div>
				<div class="divFormLabelExtended">Observaciones:</div><div id="divObservaciones" class="divFormValue"><textarea id="textareaObservaciones"></textarea></div>
			</div>
		</div>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>