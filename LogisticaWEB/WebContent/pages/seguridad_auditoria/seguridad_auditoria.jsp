<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Auditoria</title>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadTipoEventoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/SeguridadAuditoriaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-ui.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/menu.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/seguridad_auditoria/seguridad_auditoria.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/menu.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/seguridad_auditoria/seguridad_auditoria.css"/>
</head>
<body>
	<div class="divMenuBarContainer">
<%@ include file="/includes/menu.jsp" %>
	</div>
	<div class="divBodyContainer">
		<div class="divBody">
			<div class="divButtonBar">
				<div class="divButton"><input type="submit" value="Actualizar" onclick="javascript:inputActualizarOnClick(event)"/></div>
				<div class="divButtonBarSeparator">&nbsp;</div>
			</div>
			<div class="divButtonTitleBar">
				<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
				<div class="divButtonTitleBarSeparator">&nbsp;</div>
			</div>
			<div class="divMainWindow">
				<div>
					<div id="divAgregarFiltroContainer">
						<div class="divFormLabel" style="width: 75px;">Fecha desde:</div><div class="divFormValue" style="width: 90px;"><input type="text" id="inputFechaDesde" onchange="javascript:inputFechaDesdeOnChange(event, this)"/></div>
						<div class="divFormLabel" style="width: 75px;">Fecha hasta:</div><div class="divFormValue" style="width: 90px;"><input type="text" id="inputFechaHasta" onchange="javascript:inputFechaHastaOnChange(event, this)"/></div>
					</div>
				</div> 
				<div id="divTableSeguridadAuditoria">&nbsp;</div>
			</div>
			<div id="divModalBackground">&nbsp;</div>
		</div>
	</div>
<%@ include file="/includes/footer.jsp" %>