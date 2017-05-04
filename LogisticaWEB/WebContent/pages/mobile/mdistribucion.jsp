<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ include file="./mheader.jsp" %>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
		var numeroTramite = <%= request.getParameter("code") != null ? request.getParameter("code") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ResultadoEntregaDistribucionDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/mobile/mdistribucion.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?callback=initMap" async defer></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/mobile/mdistribucion.css"/>
	<script type="text/javascript" src="/LogisticaWEB/pages/mobile/mglobal.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/mobile/mglobal.css"/>
</head>
<body>
<%@ include file="./mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormularioResultadoEntregaDistribucion">
			<form id="formResultadoEntregaDistribucion" method="POST" action="/LogisticaWEB/Upload" enctype="multipart/form-data">
				<div id="divFormularioControles">
					<div class="divFormLabel">Tr&aacute;mite:</div>
					<div class="divFormValue">
						<input type="text" id="inputNumeroTramite" name="inputNumeroTramite" onchange="javascript:inputNumeroTramiteOnChange(event, this)"/>
						<a href="zxing://scan/?ret=http%3A%2F%2F557f04881ee7.sn.mynetname.net:8080/LogisticaWEB/pages/mobile/mdistribucion.jsp?code=%7BCODE%7D&SCAN_FORMATS=UPC_A,EAN_13,128">
							<img id="imgBarcode" src="/LogisticaWEB/Barcode?code=12345678"/>
						</a>
					</div>
					<div class="divFormLabel">MID:</div><div class="divFormValue" id="divMID"><a id="aMID" name="aMID" href="#">&nbsp;</a></div>
					<div class="divFormLabel">Opci&oacute;n:</div>
					<div class="divFormValue">
						<select id="selectResultadoEntregaDistribucion" name="selectResultadoEntregaDistribucion"></select>
					</div>
					<div class="divFormLabel">Anverso:</div><div class="divFormValue"><input type="file" id="inputAnverso" name="inputAnverso"/></div>
					<div class="divFormLabel">Reverso:</div><div class="divFormValue"><input type="file" id="inputReverso" name="inputReverso"/></div>
					<div class="divFormLabel">Comentarios:</div><div class="divFormValue"><textarea id="textareaObservaciones" name="textareaObservaciones"></textarea></div>
					<div class="divFormLabel" style="display: none;">Posici&oacute;n:</div>
					<input type="hidden" name="caller" value="/pages/mobile/mdistribucion.jsp"/>
					<div class="divFormLabel">&nbsp;</div><div class="divFormValue">
						<input type="button" id="inputSubmit" value="Enviar" onclick="javascript:inputSubmitOnClick(event, this)"/>
						<input type="button" id="inputLimpiar" value="Limpiar" onclick="javascript:inputLimpiarOnClick(event, this)"/>
					</div>
					<div class="divFormValue" id="divLatitud" style="display: none;"><input type="text" id="inputLatitud" name="inputLatitud"/></div>
					<div class="divFormValue" id="divLongitud" style="display: none;"><input type="text" id="inputLongitud" name="inputLongitud"/></div>
					<div class="divFormValue" id="divPrecision" style="display: none;"><input type="text" id="inputPrecision" name="inputPrecision"/></div>
				</div>
				<div>&nbsp;</div>
				<div id="divMap">&nbsp;</div>
			</form>
		</div>
	</div>
<%@ include file="./mfooter.jsp" %>