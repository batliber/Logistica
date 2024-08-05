<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Atenci&oacute;n a Clientes</title>
	<script type="text/javascript" src="./tramites.js"></script>
	<link rel="stylesheet" type="text/css" href="./tramites.css"/>
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
				<div class="divButton" id="divButtonAsignarAOperador"><input type="submit" id="inputAsignarAOperador" value="Asignar Operador" onclick="javascript:inputAsignarAOperadorOnClick(event, this)"/></div>
				<form method="post" id="formExportarAExcel" action="#"></form>
				<div class="divButton" id="divButtonExportarAExcel"><input type="submit" id="inputExportarAExcel" value="Exporta a Excel" onclick="javascript:inputExportarAExcelOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divTramites">
					<div id="divTableTramites">&nbsp;</div>
				</div>
			</div>
		</div>
	</div>
	<div id="divIFrameTramite" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Tr&aacute;mite</div>
			<div id="divCloseIFrameTramite" class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameTramite" src="about:blank"></iframe>
	</div>
	<div id="divIFrameSeleccionAtencionClienteOperador" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Operador</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<div id="divSeleccionAtencionClienteOperador">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Aceptar" onclick="javascript:inputAceptarAtencionClienteOperadorOnClick(event)"/></div>
				<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarAtencionClienteOperadorOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divPopupWindow">
				<div class="divFormLabelExtended">Operador:</div><div id="divAtencionClienteOperador" class="divFormValue"><select id="selectAtencionClienteOperador"></select></div>
				<div class="divFormLabelExtended">Observaciones:</div><div id="divAtencionClienteOperadorObservaciones" class="divFormValue"><textarea id="textareaAtencionClienteOperadorObservaciones"></textarea></div>
			</div>
		</div>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>