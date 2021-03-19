<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Punto de venta</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./punto_venta_edit.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBb6ZHkQPu3YqYlFLsBAGZ-79aVjSXwEig&libraries=places"></script>
	<link rel="stylesheet" type="text/css" href="./punto_venta_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarPuntoVenta" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarPuntoVenta"><input type="submit" id="inputEliminarPuntoVenta" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divTabbedPanel">
			<div class="divTabHeader">
				<div class="divTabTitleSelected" id="divTabTitle1">Datos</div>
				<div class="divTabTitle" id="divTabTitle2">Cotas</div>
				<div class="divTabTitleFiller" id="divTabTitle5">&nbsp;</div>
			</div>
			<div class="divTab" id="divTab1">
				<div class="divFormLabelExtended">Nombre:</div><div id="divPuntoVentaNombre" class="divFormValue"><input type="text" id="inputPuntoVentaNombre"/></div>
				<div class="divFormLabelExtended">Estado:</div><div id="divPuntoVentaEstado" class="divFormValue"><select id="selectPuntoVentaEstado"></select></div>
				<div class="divFormLabelExtended">Departamento:</div><div id="divPuntoVentaDepartamento" class="divFormValue"><select id="selectPuntoVentaDepartamento" onchange="javascript:selectDepartamentoOnChange(event, this)"></select></div>
				<div class="divFormLabelExtended">Barrio:</div><div id="divPuntoVentaBarrio" class="divFormValue"><select id="selectPuntoVentaBarrio"></select></div>
				<div class="divFormLabelExtended">Direcci&oacute;n:</div><div id="divPuntoVentaDireccion" class="divFormValue"><input type="text" id="inputPuntoVentaDireccion"/></div>
				<div class="divFormLabelExtended">Tel&eacute;fono:</div><div id="divPuntoVentaTelefono" class="divFormValue"><input type="text" id="inputPuntoVentaTelefono"/></div>
				<div class="divFormLabelExtended">Contacto:</div><div id="divPuntoVentaContacto" class="divFormValue"><input type="text" id="inputPuntoVentaContacto"/></div>
				<div class="divFormLabelExtended">Documento:</div><div id="divPuntoVentaDocumento" class="divFormValue"><input type="text" id="inputPuntoVentaDocumento"/></div>
				<div class="divFormLabelExtended" id="divLabelFechaAsignacionDistribuidor">Fecha asignaci&oacute;n distribuidor:</div><div id="divPuntoVentaFechaAsignacionDistribuidor" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelDistribuidor">Distribuidor:</div><div id="divPuntoVentaDistribuidor" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelFechaVisitaDistribuidor">Fecha visita distribuidor:</div><div id="divPuntoVentaFechaVisitaDistribuidor" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor">Fecha &uacute;ltimo cambio estado visita:</div><div id="divPuntoVentaFechaUltimoCambioEstadoVisitaPuntoVentaDistribuidor" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelEstadoVisitaPuntoVentaDistribuidor">Estado visita:</div><div id="divPuntoVentaEstadoVisitaPuntoVentaDistribuidor" class="divFormValue">&nbsp;</div>
				<div class="divFormLabelExtended" id="divLabelFechaVencimientoChipMasViejo">Fecha vencimiento chip m&aacute;s viejo:</div><div id="divPuntoVentaFechaVencimientoChipMasViejo" class="divFormValue">&nbsp;</div>
				<input type="text" id="inputBusqueda"/>
				<div id="divMapa">&nbsp;</div>
			</div>
			<div class="divTab" id="divTab2">
				<div class="divFormLabelExtended">Tope alarma saldo:</div><div id="divCotaTopeAlarmaSaldo" class="divFormValue"><input type="text" id="inputCotaTopeAlarmaSaldo"/></div>
				<div class="divFormLabelExtended">Recargas totales/día:</div><div id="divCotaRecargaTotalDia" class="divFormValue"><input type="text" id="inputCotaRecargaTotalDia"/></div>
				<div class="divFormLabelExtended">Recargas total/mes:</div><div id="divCotaRecargaTotalMes" class="divFormValue"><input type="text" id="inputCotaRecargaTotalMes"/></div>
				<div class="divFormLabelExtended">Tope/mid:</div><div id="divCotaRecargaTopeMid" class="divFormValue"><input type="text" id="inputCotaRecargaTopeMid"/></div>
				<div class="divFormLabelExtended">Tope %descuento:</div><div id="divCotaRecargaTopeDescuento" class="divFormValue"><input type="text" id="inputCotaRecargaTopeDescuento"/></div>
			</div>
		</div>
	</div>
</body>
</html>