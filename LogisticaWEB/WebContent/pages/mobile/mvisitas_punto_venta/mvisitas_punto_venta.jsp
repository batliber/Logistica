<%@ include file="../mincludes/mheader.jsp" %>
	<script type="text/javascript" src="./mvisitas_punto_venta.js"></script>
	<link rel="stylesheet" type="text/css" href="./mvisitas_punto_venta.css"/>
</head>
<body>
<%@ include file="../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divTableVisitasPuntoVentaDistribuidor">&nbsp;</div>
		<div class="divFormValue" id="divLatitud" style="display: none;"><input type="text" id="inputLatitud" name="inputLatitud"/></div>
		<div class="divFormValue" id="divLongitud" style="display: none;"><input type="text" id="inputLongitud" name="inputLongitud"/></div>
		<div class="divFormValue" id="divPrecision" style="display: none;"><input type="text" id="inputPrecision" name="inputPrecision"/></div>
		<div id="divMap" style="display:none;">&nbsp;</div>
	</div>
<%@ include file="../mincludes/mfooter.jsp" %>