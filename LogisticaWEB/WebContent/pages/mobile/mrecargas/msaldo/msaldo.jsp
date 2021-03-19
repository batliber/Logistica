<%@ include file="../../mincludes/mheader.jsp" %>
	<script type="text/javascript" src="./msaldo.js"></script>
	<link rel="stylesheet" type="text/css" href="./msaldo.css"/>
</head>
<body>
<%@ include file="../../mincludes/mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divFormulario">
			<div id="divTotales">
				<div id="divTableTotales">&nbsp;</div>
			</div>
			<div id="divButtonSolicitudSaldo"><input type="button" id="inputSubmit" value="Solicitud de saldo" onclick="javascript:inputSolicitudSaldoOnClick(event, this)"/></div>
			<div id="divSolicitudesSaldo">
				<div id="divTableSolicitudesSaldo">&nbsp;</div>
			</div>
		</div>
	</div>
<%@ include file="../../mincludes/mfooter.jsp" %>