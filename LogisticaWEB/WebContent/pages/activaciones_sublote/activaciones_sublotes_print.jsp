<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Sub-lote</title>
	<script type="text/javascript">
		var id = <%= request.getParameter("sid") != null ? request.getParameter("sid") : "null" %>;
	</script>
	<script type="text/javascript" src="./activaciones_sublotes_print.js"></script>
	<link rel="stylesheet" type="text/css" href="./activaciones_sublotes_print.css"/>
</head>
<body>
<%
	int vias = 1; 
	for (int i = 0; i<vias; i++) {
%>
	<div class="divTagSheet">
		<div class="divTagSheetContent">
			<div class="divPageHeading">&nbsp;</div>
			<div class="divPageContent">
				<div class="divTagLabel">Empresa:</div><div id="divEmpresa" class="divTagValue">&nbsp;</div>
				<div class="divTagLabel">Lote:</div><div id="divNumeroLote" class="divTagValue">&nbsp;</div>
				<div class="divNumeroLoteBarCode">&nbsp;</div>
				<div class="divTagLabel">Vence:</div><div id="divFechaVencimiento" class="divTagValue">&nbsp;</div>
			</div>
			<div class="divPageFooter">&nbsp;</div>
		</div>
	</div>
<% 
	}
%>
</body>
</html>