<%@ include file="../mincludes/mheader.jsp" %>
	<script type="text/javascript">
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
		var numeroTramite = <%= request.getParameter("code") != null ? request.getParameter("code") : "null" %>;
	</script>
	<script type="text/javascript" src="./mpick_up.js"></script>
	<!-- <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBb6ZHkQPu3YqYlFLsBAGZ-79aVjSXwEig"></script>  -->
	<link rel="stylesheet" type="text/css" href="./mpick_up.css"/>
</head>
<body>
<%@ include file="../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormularioPickUp">
			<div id="divFormularioControles">
				<div class="divFormLabel">Tr&aacute;mite:</div>
				<div class="divFormValue">
					<input type="text" id="inputNumeroTramite" name="inputNumeroTramite" onchange="javascript:inputNumeroTramiteOnChange(event, this)"/>
					<a href="zxing://scan/?ret=${pageContext.request.requestURL}?code=%7BCODE%7D&SCAN_FORMATS=UPC_A,EAN_13,128">
						<img id="imgBarcode" src="/LogisticaWEB/images/barcode.png"/>
					</a>
				</div>
				<div class="divFormLabel">MID:</div><div class="divFormValue" id="divMID"><a id="aMID" name="aMID" href="#">&nbsp;</a></div>
				<div class="divFormLabel">Comentarios:</div><div class="divFormValue"><textarea id="textareaObservaciones" name="textareaObservaciones"></textarea></div>
				<div class="divFormLabel" style="display: none;">Posici&oacute;n:</div>
				<input type="hidden" name="caller" value="/pages/mobile/mpick_up/mpick_up.jsp"/>
				<div class="divFormLabel">&nbsp;</div><div class="divFormValue">
					<input type="button" id="inputSubmit" value="Enviar" onclick="javascript:inputSubmitOnClick(event, this)"/>
					<input type="button" id="inputLimpiar" value="Limpiar" onclick="javascript:inputLimpiarOnClick(event, this)"/>
				</div>
				<div class="divFormValue" id="divLatitud" style="display: none;"><input type="text" id="inputLatitud" name="inputLatitud"/></div>
				<div class="divFormValue" id="divLongitud" style="display: none;"><input type="text" id="inputLongitud" name="inputLongitud"/></div>
				<div class="divFormValue" id="divPrecision" style="display: none;"><input type="text" id="inputPrecision" name="inputPrecision"/></div>
				<div>&nbsp;</div>
				<!-- <div id="divMap">&nbsp;</div>  -->
			</div>
		</div>
		<div id="divTableContratos">&nbsp;</div>
	</div>
<%@ include file="../mincludes/mfooter.jsp" %>