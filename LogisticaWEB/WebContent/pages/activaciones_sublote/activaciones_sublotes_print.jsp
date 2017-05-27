<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="uy.com.amensg.logistica.dwr.*" %>
<%@ page import="uy.com.amensg.logistica.util.*" %>
<%@ page import="uy.com.amensg.logistica.entities.ActivacionSubloteTO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Sub-lote</title>
	<script type="text/javascript">
		var id = <%= request.getParameter("sid") != null ? request.getParameter("sid") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ActivacionSubloteDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./activaciones_sublotes_print.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./activaciones_sublotes_print.css"/>
</head>
<%
	ActivacionSubloteTO contratoTO = new ActivacionSubloteDWR().getById(new Long(request.getParameter("sid")));
%>
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