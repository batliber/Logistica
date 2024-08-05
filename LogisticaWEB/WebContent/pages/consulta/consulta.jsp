<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Consulta</title>
	<script type="text/javascript" src="./formulario_contrato.js"></script>
	<script type="text/javascript" src="./consulta.js"></script>
	<link rel="stylesheet" type="text/css" href="./formulario_dinamico.css"/>
	<link rel="stylesheet" type="text/css" href="./consulta.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divContratos">
					<div id="divTableContratos">&nbsp;</div>
				</div>
			</div>
		</div>
	</div>
	<div id="divIFrameContrato" style="display:none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Contrato</div>
			<div id="divCloseIFrameContrato" class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameContrato" src="about:blank"></iframe>
	</div>
	<div id="divIFrameHistoricoContrato" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Hist&oacute;rico</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameHistorico" src="about:blank"></iframe>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>