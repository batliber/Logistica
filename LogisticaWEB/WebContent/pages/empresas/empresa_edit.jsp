<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Empresa</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/EmpresaDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/UsuarioDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/FormaPagoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="./empresa_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="./empresa_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardarEmpresa" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButton" id="divEliminarEmpresa"><input type="submit" id="inputEliminarEmpresa" value="Eliminar" onclick="javascript:inputEliminarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Empresa</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<form id="formEmpresa" method="POST" action="/LogisticaWEB/Upload" enctype="multipart/form-data">
			<input type="hidden" name="caller" value="/LogisticaWEB/pages/empresas/empresa_edit.jsp"/>
			<input type="hidden" name="inputEmpresaId" id="inputEmpresaId"/>
			<div class="divFormLabelExtended">Nombre:</div><div id="divEmpresaNombre" class="divFormValue"><input type="text" id="inputEmpresaNombre" name="inputEmpresaNombre"/></div>
			<div class="divFormLabelExtended">C&oacute;d. promotor:</div><div id="divEmpresaCodigoPromotor" class="divFormValue"><input type="text" id="inputEmpresaCodigoPromotor" name="inputEmpresaCodigoPromotor"/></div>
			<div class="divFormLabelExtended">Nombre (contrato):</div><div id="divEmpresaNombreContrato" class="divFormValue"><input type="text" id="inputEmpresaNombreContrato" name="inputEmpresaNombreContrato"/></div>
			<div class="divFormLabelExtended">Nombre sucursal:</div><div id="divEmpresaNombreSucursal" class="divFormValue"><input type="text" id="inputEmpresaNombreSucursal" name="inputEmpresaNombreSucursal"/></div>
			<div class="divFormLabelExtended">Direcci&oacute;n:</div><div id="divEmpresaDireccion" class="divFormValue"><input type="text" id="inputEmpresaDireccion" name="inputEmpresaDireccion"/></div>
			<div class="divFormLabelExtended" id="divLabelAgregarFormaPago">Forma de pago:</div><div class="divFormValue" id="divFormaPago"><select id="selectFormaPago"></select><input type="button" id="inputAgregarFormaPago" value="" onclick="javascript:inputAgregarFormaPagoOnClick(event, this)"/></div>
			<div class="divFormLabelExtended" id="divLabelAgregarEmpresaUsuarioContrato">Usuarios (contrato):</div><div class="divFormValue" id="divUsuario"><select id="selectUsuario"></select><input type="button" id="inputAgregarUsuario" value="" onclick="javascript:inputAgregarUsuarioOnClick(event, this)"/></div>
			
			<div class="divFormLabelExtended">Formas de pago:</div>
			<div id="divTableFormaPagos">&nbsp;</div>
			<div class="divFormLabelExtended">Usuarios (contrato):</div>
			<div id="divTableEmpresaUsuarioContratos">&nbsp;</div>
			
			<div class="divFormLabelExtended">Logo:</div><div id="divEmpresaLogo" class="divFormValue"><img id="imgEmpresaLogo" src="#"/></div>
			<div class="divFormLabelExtended">&nbsp;</div><div id="divEmpresaLogoArchivo" class="divFormValue"><input type="file" id="inputEmpresaLogo" name="inputEmpresaLogo" onchange="javascript:inputEmpresaLogoOnChange(event, this)"/></div>
		</form>
	</div>
</body>
</html>