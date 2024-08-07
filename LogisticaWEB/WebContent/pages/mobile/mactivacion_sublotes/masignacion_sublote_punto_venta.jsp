<%@ include file="../mincludes/mheader.jsp" %>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("sid") != null ? request.getParameter("sid") : "null" %>;
		var numeroSublote = <%= request.getParameter("code") != null ? request.getParameter("code") : "null" %>;
	</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBb6ZHkQPu3YqYlFLsBAGZ-79aVjSXwEig"></script>
	<script type="text/javascript" src="./masignacion_sublote_punto_venta.js"></script>
	<link rel="stylesheet" type="text/css" href="./masignacion_sublote_punto_venta.css"/>
</head>
<body>
<%@ include file="../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormularioAsignacionSublotePuntoVenta">
			<div id="divFormularioControles">
				<div class="divFormLabel">Empresa:</div><div class="divFormValue" id="divEmpresa">&nbsp;</div>
				<div class="divFormLabel">Distribuidor:</div><div class="divFormValue" id="divDistribuidor">&nbsp;</div>
				<div class="divFormLabel">Asignado:</div><div class="divFormValue" id="divFechaAsignacionDistribuidor">&nbsp;</div>
				<div class="divFormLabel">Sub-lote:</div>
				<div class="divFormValue">
					<input type="text" id="inputNumeroSublote" name="inputNumeroSublote" onchange="javascript:inputNumeroSubloteOnChange(event, this)"/>
					<a href="zxing://scan/?ret=${pageContext.request.requestURL}?code=%7BCODE%7D&SCAN_FORMATS=UPC_A,EAN_13,128">
						<img id="imgBarcode" src="/LogisticaWEB/images/barcode.png"/>
					</a>
				</div>
				<div class="divFormLabel">Departamento:</div><div class="divFormValue" id="divDepartamento"><select id="selectDepartamento"></select></div>
				<div class="divFormLabel">Barrio:</div><div class="divFormValue" id="divBarrio"><select id="selectBarrio"></select></div>
				<div class="divFormLabel">Pto. de venta:</div><div class="divFormValue" id="divPuntoVenta"><select id="selectPuntoVenta"></select></div>
				<div class="divFormLabel" style="display: none;">Posici&oacute;n:</div>
				<input type="hidden" name="caller" value="/pages/mobile/mactivacion_sublotes/masignacion_sublote_punto_venta.jsp"/>
				<div class="divFormLabel">&nbsp;</div><div class="divFormValue">
					<input type="button" id="inputSubmit" value="Enviar" onclick="javascript:inputSubmitOnClick(event, this)"/>
					<input type="button" id="inputLimpiar" value="Limpiar" onclick="javascript:inputLimpiarOnClick(event, this)"/>
				</div>
				<div class="divFormValue" id="divLatitud" style="display: none;"><input type="text" id="inputLatitud" name="inputLatitud"/></div>
				<div class="divFormValue" id="divLongitud" style="display: none;"><input type="text" id="inputLongitud" name="inputLongitud"/></div>
				<div class="divFormValue" id="divPrecision" style="display: none;"><input type="text" id="inputPrecision" name="inputPrecision"/></div>
				<div>&nbsp;</div>
				<div id="divMap">&nbsp;</div>
			</div>
		</div>
	</div>
<%@ include file="../mincludes/mfooter.jsp" %>