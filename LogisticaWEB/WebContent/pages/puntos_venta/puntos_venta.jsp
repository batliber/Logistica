<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Puntos de venta</title>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBb6ZHkQPu3YqYlFLsBAGZ-79aVjSXwEig"></script>
	<script type="text/javascript" src="./puntos_venta.js"></script>
	<link rel="stylesheet" type="text/css" href="./puntos_venta.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Actualizar" onclick="javascript:inputActualizarOnClick(event)"/></div>
				<div class="divButton" id="divButtonNew"><input type="submit" value="Agregar" onclick="javascript:inputNewOnClick(event)"/></div>
				<form method="post" id="formExportarAExcel" action="#"></form>
				<div class="divButton" id="divButtonExportarAExcel"><input type="submit" id="inputExportarAExcel" value="Exporta a Excel" onclick="javascript:inputExportarAExcelOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
				<div class="divButton" id="divButtonAsignarVisitas"><input type="submit" value="Asignar visitas" onclick="javascript:inputAsignarVisitasOnClick(event)"/></div>
				<div class="divButton" id="divButtonVisitasPermanentes"><input type="submit" value="Visitas permanentes" onclick="javascript:inputVisitasPermanentesOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleTripleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Visitas</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divTablePuntosVenta">&nbsp;</div><div id="divMapaPuntosVenta">&nbsp;</div>
			</div>
		</div>
	</div>
	<div id="divIFramePuntoVenta" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Punto de venta</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFramePuntoVenta" src="about:blank"></iframe>
	</div>
	<div id="divIFrameSeleccionDistribuidor" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Asignar visitas</div>
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
				<div class="divFormLabelExtended" style="display: none;">Visitas permanentes:</div><div id="divVisitasPermanentes" class="divFormValue" style="display: none;"><input type="checkbox" id="inputVisitasPermanentes"/></div>
				<div class="divFormLabelExtended">Distribuidor:</div><div id="divDistribuidor" class="divFormValue"><select id="selectDistribuidor"></select></div>
				<div class="divFormLabelExtended">Observaciones:</div><div id="divObservaciones" class="divFormValue"><textarea id="textareaObservaciones"></textarea></div>
			</div>
		</div>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>