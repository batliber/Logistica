<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Sub-lotes de activaciones</title>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioRolEmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/PuntoVentaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ActivacionSubloteDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/menu.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/activaciones_sublote/activaciones_sublotes.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/menu.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/activaciones_sublote/activaciones_sublotes.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" id="inputActualizar" value="Actualizar" onclick="javascript:inputActualizarOnClick(event, this)"/></div>
				<div class="divButton" id="divButtonNuevo"><input type="submit" id="inputNuevo" value="Nuevo" onclick="javascript:inputNuevoOnClick(event, this)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div id="divActivacionesSublotes">
					<div id="divTableActivacionesSublotes">&nbsp;</div>
				</div>
			</div>
		</div>
	</div>
	<div id="divIFrameActivacionSublote" style="display: none;">
		<div class="divTitleBar">
			<div class="divTitleBarText" style="float:left;">Sub-lote</div>
			<div id="divCloseIFrameActivacionSublote" class="divTitleBarCloseButton" onclick="javascript:divCloseOnClick(event, this)">&nbsp;</div>
		</div>
		<iframe id="iFrameActivacionSublote" frameborder="0" src="#"></iframe>
	</div>
	<div id="divModalBackgroundChild">&nbsp;</div>
<%@ include file="/includes/footer.jsp" %>