<%@ page import="javax.naming.InitialContext" %>
<%@ page import="uy.com.amensg.logistica.bean.IContratoBean" %>
<%@ page import="uy.com.amensg.logistica.bean.ContratoBean" %>
<%@ page import="uy.com.amensg.logistica.entities.Contrato" %>
<%@ page import="uy.com.amensg.logistica.entities.ContratoArchivoAdjunto" %>
<%@ page import="uy.com.amensg.logistica.util.Configuration" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Contrato</title>
	<script type="text/javascript">
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="./contrato_adjunto_print.js"></script>
	<link rel="stylesheet" type="text/css" href="./contrato_adjunto_print.css"/>
</head>
<%
	String prefix = "java:jboss/exported/";
	String EARName = "Logistica";
	String appName = "LogisticaEJB";
	String beanName = ContratoBean.class.getSimpleName();
	String remoteInterfaceName = IContratoBean.class.getName();
	String lookupName = 
		prefix + "/" + EARName + "/" + appName + "/" + beanName + "!" + remoteInterfaceName;

	javax.naming.Context context = new InitialContext();
		
	IContratoBean iContratoBean = (IContratoBean)context.lookup(lookupName);

	Contrato contrato = iContratoBean.getById(Long.parseLong(request.getParameter("cid")), true);
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
	for (ContratoArchivoAdjunto contratoArchivoAdjunto : contrato.getArchivosAdjuntos()) {
		if (i == 0 || i % 2 == 0) {
%>
	<div class="divA4Sheet">
		<div class="divA4SheetContent">
			<div class="divPageHeading">&nbsp;</div>
			<div class="divPageContent">
				<div class="divTitulo"><%= "Trámite: " + contrato.getNumeroTramite() + " MID:" + contrato.getMid()%></div>
<%
		}
%>
				<div class="divImage">
					<img class="imgImage" src="/LogisticaWEB/Stream?fn=<%= contratoArchivoAdjunto.getUrl() %>"/>
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