<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Movimiento de Stock</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./stock_movimiento_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./stock_movimiento_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarStockMovimiento" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Stock</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Empresa:</div><div id="divEmpresa" class="divFormValue"><select id="selectEmpresa"></select></div>
		<div class="divFormLabelExtended">Tipo de movimiento:</div><div id="divStockTipoMovimiento" class="divFormValue"><select id="selectStockTipoMovimiento" onchange="javascript:selectStockTipoMovimientoOnChange(event, this)"></select></div>
		<div class="divFormLabelExtended">Marca:</div><div id="divMarca" class="divFormValue"><select id="selectMarca" onchange="javascript:selectMarcaOnChange(event, this)"></select></div>
		<div class="divFormLabelExtended">Modelo:</div><div id="divModelo" class="divFormValue"><select id="selectModelo"></select></div>
		<div class="divFormLabelExtended">Tipo de producto:</div><div id="divTipoProducto" class="divFormValue"><select id="selectTipoProducto"></select></div>
		<div class="divFormLabelExtended">Cantidad:</div><div id="divCantidad" class="divFormValue"><input type="text" id="inputCantidad"/></div>
		<div class="divFormLabelExtended">IMEI:</div><div id="divIMEI" class="divFormValue"><input type="text" id="inputIMEI" onchange="javascript:inputIMEIOnChange(event, this)"/></div>
		<div class="divFormLabelExtended">&nbsp;</div><div class="divFormValue">&nbsp;</div>
		<div class="divFormLabelExtended">&nbsp;</div><div id="divTableIMEIs" style="float:left;">&nbsp;</div>
	</div>
</body>
</html>