<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Procesos de exportaci&oacute;n</title>
	<script type="text/javascript" src="./procesos_exportacion.js"></script>
	<link rel="stylesheet" type="text/css" href="./procesos_exportacion.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divProcesosExportacion">
					<div id="divTableProcesosExportacion">&nbsp;</div>
				</div>
			</div>
		</div>
	</div>
	<div id="divModalBackgroundChild">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>