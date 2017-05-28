<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Planes</title>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/PlanDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/menu.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/planes/planes.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/menu.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/planes/planes.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Actualizar" onclick="javascript:inputActualizarOnClick(event)"/></div>
				<div class="divButton" id="divButtonNew"><input type="submit" value="Agregar" onclick="javascript:inputNewOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div>
					<div id="divAgregarFiltroContainer">
						<div style="float: left;">Mostrar planes dados de baja:</div><div style="float: left;"><input type="checkbox" id="inputMostrarFechaBaja" onchange="javascript:inputMostrarFechaBajaOnClick(event, this)"/></div>
					</div>
				</div>
				<div id="divTablePlanes">&nbsp;</div>
			</div>
		</div>
	</div>
	<div id="divIFramePlan" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Plan</div>
			<div class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFramePlan" frameborder="0" src="#"></iframe>
	</div>
	<div id="divModalBackground">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>
