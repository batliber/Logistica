<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="uy.com.amensg.logistica.dwr.*" %>
<%@ page import="uy.com.amensg.logistica.entities.ContratoTO" %>
<%@ page import="uy.com.amensg.logistica.entities.ContratoArchivoAdjuntoTO" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Contrato</title>
	<script type="text/javascript">
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./contrato_adjunto_print.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./contrato_adjunto_print.css"/>
</head>
<%
	ContratoTO contratoTO = new ContratoDWR().getById(new Long(request.getParameter("cid")));
%>
<body>
	<div class="divPrintingButtonBar">
		<div class="divButtonBar">
			<div class="divButton"><input type="submit" value="Imprimir" onclick="javascript:inputImprimirOnClick(event, this)"></div>
			<div class="divButton"><input type="submit" value="Cancelar" onclick="javascript:inputCancelarOnClick(event, this)"></div>
		</div>
	</div>
<%
	int i = 0;
	for (ContratoArchivoAdjuntoTO contratoArchivoAdjuntoTO : contratoTO.getArchivosAdjuntos()) {
		if (i == 0 || i % 2 == 0) {
%>
	<div class="divA4Sheet">
		<div class="divA4SheetContent">
			<div class="divPageHeading">&nbsp;</div>
			<div class="divPageContent">
				<div class="divTitulo"><%= "Trámite: " + contratoTO.getNumeroTramite() + " MID:" + contratoTO.getMid()%></div>
<%
		}
%>
				<div class="divImage">
					<img class="imgImage" src="/LogisticaWEB/Stream?fn=<%= contratoArchivoAdjuntoTO.getUrl() %>"/>
				</div>
				<div style="float: left;width: 100%">&nbsp;</div>
<%
		if (i % 2 == 1) {
%>
			</div>
		</div>
	</div>
<%
		}

		i++;
	}
%>
</body>