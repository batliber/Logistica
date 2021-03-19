<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Transferencias de Stock</title>
	<script type="text/javascript" src="./transferencias_stock.js"></script>
	<link rel="stylesheet" type="text/css" href="./transferencias_stock.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div class="divFormLabelExtended">Empresa destino:</div><div id="divEmpresaDestino" class="divFormValue"><select id="selectEmpresaDestino"></select></div>
				<div class="divFormLabelExtended">IMEI:</div><div id="divIMEI" class="divFormValue"><input type="text" id="inputIMEI" onchange="javascript:inputIMEIOnChange(event, this)"/></div>
				<div class="divFormLabelExtended">&nbsp;</div><div class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended">&nbsp;</div><div id="divTableIMEIs">&nbsp;</div>
			</div>
		</div>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>