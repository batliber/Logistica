<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Precio de lista</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioRolEmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/TipoProductoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/MarcaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ModeloDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/MonedaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/PrecioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="./precio_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="./precio_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarPrecio" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Precio</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Empresa:</div><div id="divEmpresa" class="divFormValue"><select id="selectEmpresa"></select></div>
		<div class="divFormLabelExtended">Tipo:</div><div id="divTipoProducto" class="divFormValue"><select id="selectTipoProducto" onchange="javascript:selectTipoProductoOnChange(event, this)"></select></div>
		<div class="divFormLabelExtended">Marca:</div><div id="divMarca" class="divFormValue"><select id="selectMarca" onchange="javascript:selectMarcaOnChange(event, this)"></select></div>
		<div class="divFormLabelExtended">Modelo:</div><div id="divModelo" class="divFormValue"><select id="selectModelo"></select></div>
		<div class="divFormLabelExtended">Moneda:</div><div id="divMoneda" class="divFormValue"><select id="selectMoneda"></select></div>
		<div class="divFormLabelExtended">Cuotas:</div><div id="divCuotas" class="divFormValue"><input type="text" id="inputCuotas"/></div>
		<div class="divFormLabelExtended">Precio:</div><div id="divPrecio" class="divFormValue"><input type="text" id="inputPrecio"/></div>
	</div>
</body>
</html>