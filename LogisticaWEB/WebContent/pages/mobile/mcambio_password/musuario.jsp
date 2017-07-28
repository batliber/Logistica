<%@ include file="../mincludes/mheader.jsp" %>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/mobile/mcambio_password/musuario.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/mobile/mcambio_password/musuario.css"/>
</head>
<body>
<%@ include file="../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormulario">
			<div class="divFormLabel">Actual:</div>
			<div class="divFormValue">
				<input type="password" id="inputActual" name="inputActual"/>
			</div>
			<div class="divFormLabel">Nueva:</div>
			<div class="divFormValue">
				<input type="password" id="inputNueva" name="inputNueva"/>
			</div>
			<div class="divFormLabel">Confirma:</div>
			<div class="divFormValue">
				<input type="password" id="inputConfirma" name="inputConfirma"/>
			</div>
			<div class="divFormLabel">&nbsp;</div><div class="divFormValue">
				<input type="button" id="inputSubmit" value="Enviar" onclick="javascript:inputSubmitOnClick(event, this)"/>
			</div>
		</div>
	</div>
<%@ include file="../mincludes/mfooter.jsp" %>