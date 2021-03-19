<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Trazabilidad</title>
	<script type="text/javascript" src="./trazabilidad_imei.js"></script>
	<link rel="stylesheet" type="text/css" href="./trazabilidad_imei.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Actualizar" onclick="javascript:inputActualizarOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div>
					<div id="divAgregarFiltroContainer">
						<div class="divFormLabelExtended">IMEI:</div><div class="divFormValue"><input type="text" id="inputIMEI" onchange="javascript:inputIMEIOnChange(event, this)"/></div>
					</div>
				</div> 
				<div id="divTableStockMovimientos">&nbsp;</div>
			</div>
		</div>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>