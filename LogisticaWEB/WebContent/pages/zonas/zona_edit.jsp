<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Zona</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./zona_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./zona_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarZona" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarZona"><input type="submit" id="inputEliminarZona" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Zona</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabel">Nombre:</div><div id="divZonaNombre" class="divFormValue"><input type="text" id="inputZonaNombre"/></div>
		<div class="divFormLabel">Detalle:</div><div id="divZonaDetalle" class="divFormValue"><input type="text" id="inputZonaDetalle"/></div>
		<div class="divFormLabel">Departamento:</div><div id="divDepartamento" class="divFormValue"><select id="selectDepartamento"></select></div>
		<div class="divFormLabel">Disponibilidad:</div>
		<div id="divDisponibilidadEntrega" class="divFormValue">
			<div class="divColumnTurno" id="1">
				<div class="divTitulo">Ma&ntilde;ana</div>
				<div class="divFormLabel">Domingo:</div><div class="divCheckbox"><input type="checkbox" id="inputMananaDomingo"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputMananaDomingoCantidad" tid="1" did="1"/></div>
				<div class="divFormLabel">Lunes:</div><div class="divCheckbox"><input type="checkbox" id="inputMananaLunes"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputMananaLunesCantidad" tid="1" did="2"/></div>
				<div class="divFormLabel">Martes:</div><div class="divCheckbox"><input type="checkbox" id="inputMananaMartes"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputMananaMartesCantidad" tid="1" did="3"/></div>
				<div class="divFormLabel">Mi&eacute;rcoles:</div><div class="divCheckbox"><input type="checkbox" id="inputMananaMiercoles"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputMananaMiercolesCantidad" tid="1" did="4"/></div>
				<div class="divFormLabel">Jueves:</div><div class="divCheckbox"><input type="checkbox" id="inputMananaJueves"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputMananaJuevesCantidad" tid="1" did="5"/></div>
				<div class="divFormLabel">Viernes:</div><div class="divCheckbox"><input type="checkbox" id="inputMananaViernes"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputMananaViernesCantidad" tid="1" did="6"/></div>
				<div class="divFormLabel">S&aacute;bado:</div><div class="divCheckbox"><input type="checkbox" id="inputMananaSabado"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputMananaSabadoCantidad" tid="1" did="7"/></div>
			</div>
			<div class="divColumnTurno" id=2>
				<div class="divTitulo">Tarde</div>
				<div class="divFormLabel">Domingo:</div><div class="divCheckbox"><input type="checkbox" id="inputTardeDomingo"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputTardeDomingoCantidad" tid="2" did="1"/></div>
				<div class="divFormLabel">Lunes:</div><div class="divCheckbox"><input type="checkbox" id="inputTardeLunes"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputTardeLunesCantidad" tid="2" did="2"/></div>
				<div class="divFormLabel">Martes:</div><div class="divCheckbox"><input type="checkbox" id="inputTardeMartes"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputTardeMartesCantidad" tid="2" did="3"/></div>
				<div class="divFormLabel">Mi&eacute;rcoles:</div><div class="divCheckbox"><input type="checkbox" id="inputTardeMiercoles"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputTardeMiercolesCantidad" tid="2" did="4"/></div>
				<div class="divFormLabel">Jueves:</div><div class="divCheckbox"><input type="checkbox" id="inputTardeJueves"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputTardeJuevesCantidad" tid="2" did="5"/></div>
				<div class="divFormLabel">Viernes:</div><div class="divCheckbox"><input type="checkbox" id="inputTardeViernes"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputTardeViernesCantidad" tid="2" did="6"/></div>
				<div class="divFormLabel">S&aacute;bado:</div><div class="divCheckbox"><input type="checkbox" id="inputTardeSabado"/></div><div class="divInput"><input type="text" class="inputCantidad" id="inputTardeSabadoCantidad" tid="2" did="7"/></div>
			</div>
		</div>
	</div>
</body>
</html>