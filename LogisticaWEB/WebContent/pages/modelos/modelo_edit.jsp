<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Modelo</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/MarcaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EmpresaServiceDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ModeloDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./modelo_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./modelo_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarModelo" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarModelo"><input type="submit" id="inputEliminarModelo" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Modelo</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Descripci&oacute;n:</div><div id="divModeloDescripcion" class="divFormValue"><input type="text" id="inputModeloDescripcion"/></div>
		<div class="divFormLabelExtended">Marca:</div><div id="divModeloMarca" class="divFormValue"><select id="selectModeloMarca"/></select></div>
		<div class="divFormLabelExtended">Service:</div><div id="divModeloEmpresaService" class="divFormValue"><select id="selectModeloEmpresaService"/></select></div>
	</div>
</body>
</html>