<%@ include file="../mincludes/mheader.jsp" %>
	<script type="text/javascript" src="./musuario.js"></script>
	<link rel="stylesheet" type="text/css" href="./musuario.css"/>
</head>
<body>
<%@ include file="../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormulario">
			<div class="divFormLabel">Actual:</div>
			<div class="divFormValue">
				<input type="password" id="inputContrasenaActual" name="inputContrasenaActual"/>
			</div>
			<div class="divFormLabel">Nueva:</div>
			<div class="divFormValue">
				<input type="password" id="inputNuevaContrasena" name="inputNuevaContrasena"/>
			</div>
			<div class="divFormLabel">Confirma:</div>
			<div class="divFormValue">
				<input type="password" id="inputConfirmaContrasena" name="inputConfirmaContrasena"/>
			</div>
			<div class="divFormLabel">&nbsp;</div><div class="divFormValue">
				<input type="button" id="inputSubmit" value="Enviar" onclick="javascript:inputSubmitOnClick(event, this)"/>
			</div>
		</div>
	</div>
<%@ include file="../mincludes/mfooter.jsp" %>