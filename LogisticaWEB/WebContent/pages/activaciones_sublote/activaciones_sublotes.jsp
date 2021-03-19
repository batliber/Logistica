<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Sub-lotes de activaciones</title>
	<script type="text/javascript" src="./activaciones_sublotes.js"></script>
	<link rel="stylesheet" type="text/css" href="./activaciones_sublotes.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonNuevo"><input type="submit" id="inputNuevo" value="Nuevo" onclick="javascript:inputNuevoOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonRecalcularPorcentajes"><input type="submit" value="Recalcular porcentajes" onclick="javascript:inputRecalcularPorcentajesOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonRecalcularFechasVencimientoChipMasViejo"><input type="submit" value="Recalcular f. venc." onclick="javascript:inputRecalcularFechasVencimientoChipMasViejoOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonAsignarVisitas"><input type="submit" value="Asignar visitas" onclick="javascript:inputAsignarVisitasOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divActivacionesSublotes">
					<div id="divTableActivacionesSublotes">&nbsp;</div>
				</div>
			</div>
		</div>
	</div>
	<div id="divIFrameActivacionSublote" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Sub-lote</div>
			<div id="divCloseIFrameActivacionSublote" class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameActivacionSublote" src="about:blank"></iframe>
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
				<div class="divFormLabelExtended">Distribuidor:</div><div id="divDistribuidor" class="divFormValue"><select id="selectDistribuidor"></select></div>
				<div class="divFormLabelExtended">Observaciones:</div><div id="divObservaciones" class="divFormValue"><textarea id="textareaObservaciones"></textarea></div>
			</div>
		</div>
	</div>
	<div id="divModalBackgroundChild">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>