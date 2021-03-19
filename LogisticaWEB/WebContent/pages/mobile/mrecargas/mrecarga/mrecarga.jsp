<%@ include file="../../mincludes/mheader.jsp" %>
	<script type="text/javascript" src="./mrecarga.js"></script>
	<link rel="stylesheet" type="text/css" href="./mrecarga.css"/>
</head>
<body>
<%@ include file="../../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormularioRecarga">
			<div id="divFormularioControles">
				<div class="divFormLabel">Empresa:</div><div class="divFormValue" id="divRecargaEmpresa"><select id="selectRecargaEmpresa"></select></div>
				<div class="divFormLabel">Nro. de cel.:</div><div class="divFormValue"><input type="text" id="inputMID" onchange="javascript:inputMIDOnChange(event, this)"/></div>
				<div class="divFormLabel">Monto:</div><div class="divFormValue"><input type="text" id="inputMonto"/></div>
				<div class="divFormLabel">&nbsp;</div><div class="divFormValue"><input type="button" id="inputSubmit" value="Enviar" onclick="javascript:inputSubmitOnClick(event, this)"/><input type="button" id="inputComprobarNuevamente" value="Comprobar" onclick="javascript:inputComprobarNuevamenteOnClick(event, this)"/></div>
				<div class="divFormLabel">Resultado:</div><div class="divFormValue" id="divResultado">&nbsp;</div>
			</div>
		</div>
	</div>
<%@ include file="../../mincludes/mfooter.jsp" %>