<%@ include file="../../mincludes/mheader.jsp" %>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./msolicitud_saldo.js"></script>
	<link rel="stylesheet" type="text/css" href="./msolicitud_saldo.css"/>
</head>
<body>
<%@ include file="../../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormularioSolicitud">
			<div id="divFormularioControles">
				<div class="divFormLabel">Empresa:</div><div class="divFormValue" id="divRecargaEmpresa"><select id="selectRecargaEmpresa"></select></div>
				<div class="divFormLabel">Monto:</div><div class="divFormValue" id="divMonto"><input type="text" id="inputMonto"/></div>
				<div class="divFormLabel">Forma pago:</div><div class="divFormValue" id="divRecargaFormaPago"><select id="selectRecargaFormaPago"></select></div>
				<div class="divFormLabel">Banco dest.:</div><div class="divFormValue" id="divRecargaBanco"><select id="selectRecargaBanco" onchange="javascript:selectRecargaBancoOnChange(event, this)"></select></div>
				<div class="divFormLabel">Cuenta:</div><div class="divFormValue" id="divEmpresaRecargaBancoCuenta"><select id="selectEmpresaRecargaBancoCuenta"></select></div>
				<div class="divFormLabel">Comprobantes:</div><div class="divFormValue" id="divAgregarArchivos"><input type="submit" id="inputAgregarArchivo" value="Agregar" onclick="javascript:inputAgregarArchivoOnClick(event, this);"/></div>
				<div class="divArchivos">&nbsp;</div>
				<div class="divFormLabel">Observaciones:</div><div class="divFormValue"><textarea id="textareaObservaciones" name="textareaObservaciones"></textarea></div>
				<input type="hidden" name="caller" value="/pages/mobile/mrecargas/msolicitud_saldo/msolicitud_saldo.jsp"/>
				<div class="divFormLabel">&nbsp;</div><div class="divFormValue">
					<input type="button" id="inputSubmit" value="Enviar" onclick="javascript:inputSubmitOnClick(event, this)"/>
				</div>
			</div>
		</div>
	</div>
<%@ include file="../../mincludes/mfooter.jsp" %>