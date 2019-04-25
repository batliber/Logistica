<%@ include file="../mincludes/mheader.jsp" %>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("vid") != null ? request.getParameter("vid") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioRolEmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EstadoVisitaPuntoVentaDistribuidorDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/PuntoVentaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/VisitaPuntoVentaDistribuidorDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/mobile/mvisita/mvisita.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBb6ZHkQPu3YqYlFLsBAGZ-79aVjSXwEig"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/mobile/mvisita/mvisita.css"/>
</head>
<body>
<%@ include file="../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormularioVisitaPuntoVentaDistribuidor">
			<div id="divFormularioControles">
				<div class="divFormLabel">Distribuidor:</div>
				<div class="divFormValue" id="divDistribuidor">&nbsp;</div>
				<!-- 
				<div class="divFormLabel">Dpto.:</div>
				<div class="divFormValue" id="divDepartamento">&nbsp;</div>
				 -->
				<div class="divFormLabel">Pto. venta:</div>
				<div class="divFormValue" id="divPuntoVenta">&nbsp;</div>
				<div class="divFormLabel">Direcci&oacute;n:</div>
				<div class="divFormValue" id="divPuntoVentaDireccion">&nbsp;</div>
				<div class="divFormLabel">Barrio:</div>
				<div class="divFormValue" id="divPuntoVentaBarrio">&nbsp;</div>
				<div class="divFormLabel">Tel. contacto:</div>
				<div class="divFormValue" id="divPuntoVentaTelefono">&nbsp;</div>
				<div class="divFormLabel">Contacto:</div>
				<div class="divFormValue" id="divPuntoVentaContacto">&nbsp;</div>
				<div class="divFormLabel">Asignado:</div>
				<div class="divFormValue" id="divFechaAsignacion">&nbsp;</div>
				<div class="divFormLabel">Estado:</div>
				<div class="divFormValue">
					<select id="selectEstadoVisitaPuntoVentaDistribuidor" name="selectEstadoVisitaPuntoVentaDistribuidor"></select>
				</div>
				<div class="divFormLabel">Comentarios:</div><div class="divFormValue"><textarea id="textareaObservaciones" name="textareaObservaciones"></textarea></div>
				<div class="divFormLabel" style="display: none;">Posici&oacute;n:</div>
				<input type="hidden" name="caller" value="/pages/mobile/mvisita/mvisita.jsp"/>
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