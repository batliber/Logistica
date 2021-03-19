<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Stock</title>
	<script type="text/javascript" src="./stock.js"></script>
	<link rel="stylesheet" type="text/css" href="./stock.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Actualizar" onclick="javascript:inputActualizarOnClick(event)"/></div>
				<div class="divButton" id="divButtonNuevoStockMovimiento"><input type="submit" value="Nuevo movimiento" onclick="javascript:inputNuevoStockMovimientoOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divTableStockMovimientos">&nbsp;</div>
			</div>
		</div>
	</div>
	<div id="divIFrameStockMovimiento" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Movimiento de Stock</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameStockMovimiento" src="about:blank"></iframe>
	</div>
	<div id="divIFrameIMEI" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">IMEI</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameIMEI" src="about:blank"></iframe>
	</div>
	<div id="divIFrameEmpresaService" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Empresa</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameEmpresaService" src="about:blank"></iframe>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>