<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Hist&oacute;rico</title>
	<script type="text/javascript">
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="./historico.js"></script>
	<link rel="stylesheet" type="text/css" href="./historico.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" value="Actualizar" onclick="javascript:inputActualizarOnClick(event)"/></div>
		<div class="divButton" id="divInputImprimirKit"><input type="submit" value="Imprimir kit" onclick="javascript:inputImprimirKitOnClick(event)"/></div>
		<div class="divButton" id="divInputCancelar"><input type="submit" value="Cancelar trámite" onclick="javascript:inputCancelarTramiteOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleTripleSize" class="divButtonTitleBarTitle">Acciones</div>
	</div>
	<div class="divPopupWindow">
		<div id="divHistorico">
			<div id="divTableHistorico">&nbsp;</div>
		</div>
	</div>
</body>
</html>