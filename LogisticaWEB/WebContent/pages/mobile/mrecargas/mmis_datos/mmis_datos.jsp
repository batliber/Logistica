<%@ include file="../../mincludes/mheader.jsp" %>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./mmis_datos.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBb6ZHkQPu3YqYlFLsBAGZ-79aVjSXwEig&libraries=places"></script>
	<link rel="stylesheet" type="text/css" href="./mmis_datos.css"/>
</head>
<body>
<%@ include file="../../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormularioPuntoVenta">
			<div id="divFormularioControles">
				<div class="divFormLabel">N&uacute;mero:</div><div class="divFormValue" id="divId"><input type="text" id="inputId"/></div>
				<div class="divFormLabel">Nombre:</div><div class="divFormValue" id="divNombre"><input type="text" id="inputNombre"/></div>
				<div class="divFormLabel">Tel&eacute;fono:</div><div class="divFormValue" id="divTelefono"><input type="text" id="inputTelefono"/></div>
				<div class="divFormLabel">Documento:</div><div class="divFormValue" id="divDocumento"><input type="text" id="inputDocumento"/></div>
				<div class="divFormLabel">Contacto:</div><div class="divFormValue" id="divContacto"><input type="text" id="inputContacto"/></div>
				<div class="divFormLabel">Depto.:</div><div class="divFormValue" id="divDepartamento"><input type="text" id="inputDepartamento"/></div>
				<div class="divFormLabel">Barrio:</div><div class="divFormValue" id="divBarrio"><input type="text" id="inputBarrio"/></div>
				<div class="divFormLabel">Direcci&oacute;n:</div><div class="divFormValue" id="divDireccion"><input type="text" id="inputDireccion"/></div>
				<div class="divFormLabel" style="display: none;">Posici&oacute;n:</div>
				<input type="hidden" name="caller" value="/pages/mobile/mpuntos_venta/mpuntos_venta.jsp"/>
				<div class="divFormValue" id="divLatitud" style="display: none;"><input type="text" id="inputLatitud" name="inputLatitud"/></div>
				<div class="divFormValue" id="divLongitud" style="display: none;"><input type="text" id="inputLongitud" name="inputLongitud"/></div>
				<div class="divFormValue" id="divPrecision" style="display: none;"><input type="text" id="inputPrecision" name="inputPrecision"/></div>
				<div>&nbsp;</div>
				<div id="divMap">&nbsp;</div>
			</div>
		</div>
	</div>
<%@ include file="../../mincludes/mfooter.jsp" %>