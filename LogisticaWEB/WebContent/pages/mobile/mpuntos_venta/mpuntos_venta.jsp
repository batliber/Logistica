<%@ include file="../mincludes/mheader.jsp" %>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/DepartamentoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/BarrioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/PuntoVentaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/mobile/mpuntos_venta/mpuntos_venta.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBb6ZHkQPu3YqYlFLsBAGZ-79aVjSXwEig&libraries=places"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/mgrid.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/mobile/mpuntos_venta/mpuntos_venta.css"/>
</head>
<body>
<%@ include file="../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormularioPuntoVenta">
			<div id="divFormularioControles">
				<div class="divFormLabel">Nombre:</div><div class="divFormValue" id="divNombre"><input type="text" id="inputNombre"/></div>
				<div class="divFormLabel">Tel&eacute;fono:</div><div class="divFormValue" id="divTelefono"><input type="text" id="inputTelefono"/></div>
				<div class="divFormLabel">Documento:</div><div class="divFormValue" id="divDocumento"><input type="text" id="inputDocumento"/></div>
				<div class="divFormLabel">Contacto:</div><div class="divFormValue" id="divContacto"><input type="text" id="inputContacto"/></div>
				<div class="divFormLabel">Departamento:</div><div class="divFormValue" id="divDepartamento"><select id="selectDepartamento" onchange="javascript:selectDepartamentoOnChange(event, this)"></select></div>
				<div class="divFormLabel">Barrio:</div><div class="divFormValue" id="divBarrio"><select id="selectBarrio" onchange="javascript:selectBarrioOnChange(event, this)"></select></div>
				<div class="divFormLabel">Direcci&oacute;n:</div><div class="divFormValue" id="divDireccion"><input type="text" id="inputDireccion"/></div>
				<div class="divFormLabel" style="display: none;">Posici&oacute;n:</div>
				<input type="hidden" name="caller" value="/pages/mobile/mpuntos_venta/mpuntos_venta.jsp"/>
				<div class="divFormLabel">&nbsp;</div><div class="divFormValue">
					<input type="button" id="inputSubmit" value="Enviar" onclick="javascript:inputSubmitOnClick(event, this)"/>
					<input type="button" id="inputLimpiar" value="Limpiar" onclick="javascript:inputLimpiarOnClick(event, this)"/>
				</div>
				<div class="divFormValue" id="divLatitud" style="display: none;"><input type="text" id="inputLatitud" name="inputLatitud"/></div>
				<div class="divFormValue" id="divLongitud" style="display: none;"><input type="text" id="inputLongitud" name="inputLongitud"/></div>
				<div class="divFormValue" id="divPrecision" style="display: none;"><input type="text" id="inputPrecision" name="inputPrecision"/></div>
				<div>&nbsp;</div>
				<input type="text" id="inputBusqueda"/>
				<div id="divMap">&nbsp;</div>
			</div>
		</div>
	</div>
<%@ include file="../mincludes/mfooter.jsp" %>