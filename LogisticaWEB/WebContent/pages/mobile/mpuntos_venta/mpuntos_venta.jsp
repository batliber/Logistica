<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ include file="../mheader.jsp" %>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/PuntoVentaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/mobile/mpuntos_venta/mpuntos_venta.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?callback=initMap" async defer></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/mgrid.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/mobile/mpuntos_venta/mpuntos_venta.css"/>
	<script type="text/javascript" src="/LogisticaWEB/pages/mobile/mglobal.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/mobile/mglobal.css"/>
</head>
<body>
<%@ include file="../mtitle.jsp" %>
	<div id="divIFrameActivePage">
		<div id="divMap">&nbsp;</div>
	</div>
<%@ include file="../mfooter.jsp" %>