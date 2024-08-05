<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>URSEC</title>
	<script type="text/javascript" src="./contrato_ursec.js"></script>
	<link rel="stylesheet" type="text/css" href="./contrato_ursec.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonSubirArchivo"><input type="submit" id="inputSubirArchivo" value="Subir archivo" onclick="javascript:inputSubirArchivoOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divURSEC">
					<div id="divTableURSEC">&nbsp;</div>
				</div>
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
					<input type="hidden" name="caller" value="/LogisticaWEB/pages/monitoreo/ursec.jsp"/>
					<div class="divFormLabelExtended">Archivo:</div><div id="divArchivo" class="divFormValue"><input type="file" id="inputArchivo" name="inputArchivo"/></div>
				</form>
			</div>
		</div>
	</div>
	<div id="divModalBackgroundChild">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>