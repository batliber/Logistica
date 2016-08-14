<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Barrio</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/DepartamentoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ZonaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/BarrioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./barrio_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./barrio_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarBarrio" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarBarrio"><input type="submit" id="inputEliminarBarrio" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Barrio</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divMainWindow">
		<div class="divFormLabel">Nombre:</div><div id="divBarrioNombre" class="divFormValue"><input type="text" id="inputBarrioNombre"/></div>
		<div class="divFormLabel">Departamento:</div><div id="divDepartamento" class="divFormValue"><select id="selectDepartamento" onchange="javascript:selectDepartamentoOnChange(this)"></select></div>
		<div class="divFormLabel">Zona:</div><div id="divZona" class="divFormValue"><select id="selectZona"></select></div>
	</div>
</body>
</html>