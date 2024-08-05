<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Contrato</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="./contrato_qr_instalacion_fibra_print.js"></script>
	<link rel="stylesheet" type="text/css" href="./contrato_qr_instalacion_fibra_print.css"/>
</head>
<body>
	<div class="divA4Sheet">
		<div class="divA4SheetContent">
			<div class="divHeader">&nbsp;</div>
			<div class="divRow">
				<div class="divColumn">
					<div class="divHeaderFecha">&nbsp;</div>
					<div class="divHeaderLogoANTEL"><img class="imgLogoANTEL" src="./logo_antel.jpg"/></div>
					<div class="divHeaderContrato">&nbsp;</div>
				</div>
			</div>
			<div class="divRow">&nbsp;</div>
			<div class="divRow">
				<p style="font-weight: bold;">¡Hola! Te estamos entregando un Router de &uacute;ltima generaci&oacute;n.</p>
			</div>
			<div class="divRow">
				<p>A continuaci&oacute;n te guiamos para sustituir tu router actual por este.</p>
				<p style="border: solid 1px black;padding: 2px;">AVISO: Durante el proceso no tendr&aacute;s conexi&oacute;n a internet por fibra &oacute;ptica, por lo cual te solicitamos seguir los pasos desde tu celular. Asegurate de tener habilitados los datos en tu m&oacute;vil.</p>
				<ol>
					<li>Abr&iacute; una aplicaci&oacute;n que te permita leer c&oacute;digos QR.</li>
					<li>Le&eacute; el c&oacute;digo QR que adjuntamos a continuaci&oacute;n, centr&aacute;ndolo en el recuadro que aparece en la pantalla de tu celular.</li>
				</ol>
			</div>
			<div class="divRow">
				<div class="divColumn">
					<div class="divQR">
						<div class="divFormLabelPrint" id="divLabelNumeroTramite">Tr&aacute;mite:</div><div id="divNumeroTramite" class="divFormValuePrint">&nbsp;</div>
						<div class="divFormLabelPrint" id="divLabelAntelNroTrn">Orden:</div><div id="divAntelNroTrn" class="divFormValuePrint">&nbsp;</div>
						<div class="divFormLabelPrint" id="divLabelNumeroSerie">Serie:</div><div id="divNumeroSerie" class="divFormValuePrint">&nbsp;</div>
						<div class="divFormLabelPrint" id="divLabelNumeroTramiteBarCode">QR:</div><div class="divBarCode"><img id="imgNumeroTramiteBarCode" src="about:blank"></img></div>
					</div>
				</div>
			</div>
			<div>
				<ol start="3">
					<li>Cuando leas el c&oacute;digo QR, selecciona la opci&oacute;n "Abrir navegador" y sigue las instrucciones.</li>
				</ol>
			</div>
			<div class="divRow">
				<p>En caso de dudas o inconvenientes comun&iacute;cate al 121 desde un tel&eacute;fono fijo o el m&oacute;vil de otra compa&ntilde;&iacute;a, o al *121 desde tu m&oacute;vil Antel.</p>
				<p>Ahora ya pod&eacute;s empezar a disfrutar de los beneficios de tu nuevo Router:</p>
				<ul>
					<li>Mayor velocidad de conexi&oacute;n y cobertura en tu hogar.</li>
					<li>Menor nivel de interferencias.</li>
					<li>Es compatible con el servicio WiFi Max</li>
				</ul> 
			</div>
			<div class="divRow">
				<p>Si no puedes leer el QR, ingresa al siguiente link: <a href="http://www.antel.com.uy/as">www.antel.com.uy/as</a>, eleg&iacute; la opci&oacute;n "Gu&iacute;a para instalar tu router nuevo" y en el &uacute;ltimo paso cuando te solicite el identificador, ingre&aacute; el siguiente valor:</p>
			</div>
			<div class="divRow">
				<div class="divSquare">&nbsp;</div>
			</div>
		</div>
	</div>
</body>
</html>