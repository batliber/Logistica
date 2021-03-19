<%@ include file="../mincludes/mheader.jsp" %>
	<script type="text/javascript" src="./mpuntos_venta.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBb6ZHkQPu3YqYlFLsBAGZ-79aVjSXwEig&libraries=places"></script>
	<link rel="stylesheet" type="text/css" href="./mpuntos_venta.css"/>
</head>
<body>
<%@ include file="../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormulario">
			<div id="divFormularioControles">
				<div class="divFormLabel">Depto.:</div>
				<div class="divFormValue" id="divDepartamento">
					<select id="selectDepartamento" name="selectDepartamento"></select>
				</div>
				<div class="divFormLabel">Barrio:</div>
				<div class="divFormValue" id="divBarrio">
					<select id="selectBarrio" name="selectBarrio"></select>
				</div>
				<div class="divFormLabel">Estado:</div>
				<div class="divFormValue" id="divEstado">
					<select id="selectEstado" name="selectEstado"></select>
				</div>
				<div id="divMap">&nbsp;</div>
			</div>
		</div>
	</div>
<%@ include file="../mincludes/mfooter.jsp" %>