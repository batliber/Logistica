<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="/includes/header.jsp" %>
	<title>Riesgo crediticio PY</title>
	<script type="text/javascript">
		var mode = <%= request.getParameter("m") != null ? request.getParameter("m") : "0" %>;
		var id = <%= request.getParameter("id") != null ? request.getParameter("id") : "null" %>;
	</script>
	<script type="text/javascript" src="./riesgo_crediticio_paraguay_edit.js"></script>
	<link rel="stylesheet" type="text/css" href="./riesgo_crediticio_paraguay_edit.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" id="inputGuardar" value="Guardar" onclick="javascript:inputGuardarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Datos de riesgo</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divPopupWindow">
		<div class="divLayoutColumn">
			<div class="divFormLabelExtended">Empresa:</div><div id="divEmpresa" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended">Documento:</div><div id="divDocumento" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended" id="divLabelFechaNacimiento">Fecha de nacimiento:</div><div id="divFechaNacimiento" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended">Fecha de nacimiento:</div><div id="divFechaNacimientoMostrar" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended">Situaci&oacute;n:</div><div id="divSituacionRiesgoCrediticioParaguay" class="divFormValue">&nbsp;</div>
			<div class="divFormLabelExtended">Estado:</div><div id="divEstadoRiesgoCrediticio" class="divFormValue"><select id="selectEstadoRiesgoCrediticio"></select></div>
			<div class="divFormLabelExtended">Fecha de vigencia:</div><div id="divFechaVigenciaDesde" class="divFormValue"><input type="text" id="inputFechaVigenciaDesde"/></div>
			<div class="divFormLabelExtended">Condicion:</div><div id="divCondicion" class="divFormValue"><input type="text" id="inputCondicion"/></div>
			<div class="divFormLabelExtended">Motivo:</div><div id="divMotivo" class="divFormValue"><input type="text" id="inputMotivo"/></div>
			<div class="divFormLabelExtended">IPS Documento:</div><div id="divIPSDocumento" class="divFormValue"><input type="text" id="inputIPSDocumento"/></div>
			<div class="divFormLabelExtended">IPS Nombres:</div><div id="divIPSNombres" class="divFormValue"><input type="text" id="inputIPSNombres"/></div>
			<div class="divFormLabelExtended">IPS Apellidos:</div><div id="divIPSApellidos" class="divFormValue"><input type="text" id="inputIPSApellidos"/></div>
			<div class="divFormLabelExtended">IPS Fnac:</div><div id="divIPSFnac" class="divFormValue"><input type="text" id="inputIPSFnac"/></div>
			<div class="divFormLabelExtended">IPS Sexo:</div><div id="divIPSSexo" class="divFormValue"><input type="text" id="inputIPSSexo"/></div>
			<div class="divFormLabelExtended">IPS Tipo aseg.:</div><div id="divIPSTipoAseg" class="divFormValue"><input type="text" id="inputIPSTipoAseg"/></div>
			<div class="divFormLabelExtended">IPS Empleador:</div><div id="divIPSEmpleador" class="divFormValue"><input type="text" id="inputIPSEmpleador"/></div>
			<div class="divFormLabelExtended">IPS Estado:</div><div id="divIPSEstado" class="divFormValue"><input type="text" id="inputIPSEstado"/></div>
			<div class="divFormLabelExtended">IPS Meses aporte:</div><div id="divIPSMesesAporte" class="divFormValue"><input type="text" id="inputIPSMesesAporte"/></div>
			<div class="divFormLabelExtended">IPS Nu. patronal:</div><div id="divIPSNuPatronal" class="divFormValue"><input type="text" id="inputIPSNuPatronal"/></div>
			<div class="divFormLabelExtended">IPS UPA:</div><div id="divIPSUPA" class="divFormValue"><input type="text" id="inputIPSUPA"/></div>
		</div>
		<div class="divLayoutColumn">
			<div class="divFormLabelExtended">SET Documento:</div><div id="divSETDocumento" class="divFormValue"><input type="text" id="inputSETDocumento"/></div>
			<div class="divFormLabelExtended">SET DV:</div><div id="divSETDV" class="divFormValue"><input type="text" id="inputSETDV"/></div>
			<div class="divFormLabelExtended">SET Nombre compl.:</div><div id="divSETNombreCompleto" class="divFormValue"><input type="text" id="inputSETNombreCompleto"/></div>
			<div class="divFormLabelExtended">SET Estado:</div><div id="divSETEstado" class="divFormValue"><input type="text" id="inputSETEstado"/></div>
			<div class="divFormLabelExtended">SET Situaci&oacute;n:</div><div id="divSETSituacion" class="divFormValue"><input type="text" id="inputSETSituacion"/></div>
			<div class="divFormLabelExtended">SFP Entidad:</div><div id="divSFPEntidad" class="divFormValue"><input type="text" id="inputSFPEntidad"/></div>
			<div class="divFormLabelExtended">SFP C&eacute;dula:</div><div id="divSFPCedula" class="divFormValue"><input type="text" id="inputSFPCedula"/></div>
			<div class="divFormLabelExtended">SFP Nombres:</div><div id="divSFPNombres" class="divFormValue"><input type="text" id="inputSFPNombres"/></div>
			<div class="divFormLabelExtended">SFP Apellidos:</div><div id="divSFPApellidos" class="divFormValue"><input type="text" id="inputSFPApellidos"/></div>
			<div class="divFormLabelExtended">SFP Presupuesto:</div><div id="divSFPPresupuesto" class="divFormValue"><input type="text" id="inputSFPPresupuesto"/></div>
			<div class="divFormLabelExtended">SFP Fnac:</div><div id="divSFPFnac" class="divFormValue"><input type="text" id="inputSFPFnac"/></div>
			<div class="divFormLabelExtended">Respuesta externa:</div><div id="divRespuestaExterna" class="divFormValue">&nbsp;</div>
		</div>
	</div>
</body>
</html>