<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Cuenta</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./cuenta_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./cuenta_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarCuenta" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarCuenta"><input type="submit" id="inputEliminarCuenta" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Empresa:</div><div id="divEmpresa" class="divFormValue"><select id="selectEmpresa"></select></div>
		<div class="divFormLabelExtended">Banco:</div><div id="divRecargaBanco" class="divFormValue"><select id="selectRecargaBanco"></select></div>
		<div class="divFormLabelExtended">Moneda:</div><div id="divMoneda" class="divFormValue"><select id="selectMoneda"></select></div>
		<div class="divFormLabelExtended">N&uacute;mero:</div><div id="divNumero" class="divFormValue"><input type="text" id="inputNumero"/></div>
	</div>
</body>
</html>