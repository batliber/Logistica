<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Tipos de productos</title>
	<script type="text/javascript" src="./tipos_productos.js"></script>
	<link rel="stylesheet" type="text/css" href="./tipos_productos.css"/>
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
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divTableTiposProductos">&nbsp;</div>
			</div>
		</div>
	</div>
	<div id="divIFrameTipoProducto" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Tipo de producto</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameTipoProducto" src="about:blank"></iframe>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>