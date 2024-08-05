<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Conceptos</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./atencion_cliente_concepto_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./atencion_cliente_concepto_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarAtencionClienteConcepto" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarAtencionClienteConcepto"><input type="submit" id="inputEliminar" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Concepto</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divFormLabelExtended">Descripci&oacute;n:</div><div id="divAtencionClienteConceptoDescripcion" class="divFormValue"><input type="text" id="inputAtencionClienteConceptoDescripcion"/></div>
	</div>
</body>
</html>