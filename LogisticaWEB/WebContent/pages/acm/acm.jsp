<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>ACM</title>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ACMInterfaceContratoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ACMInterfacePrepagoDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="./acm.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="./acm.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" value="Exportar a Excel" onclick="javascript:inputExportarAExcelOnClick(event)"/></div>
		<div class="divButton"><input type="submit" value="Reprocesar" onclick="javascript:inputReprocesarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleDoubleSize" class="divButtonTitleBarTitle">Acciones</div>
		<div class="divButtonTitleBarSeparator">&nbsp;</div>
	</div>
	<div class="divMainWindow">
		<div id="divFiltros">
			<div class="divFormLabel">Tipo de registro:</div>
			<div id="divTipoRegistro" style="float: left;">
				<select id="selectTipoRegistro" onchange="javascript:selectTipoRegistroOnChange(event)">
					<option value="contrato">Contrato</option>
					<option value="prepago">Prepago</option>
				</select>
			</div>
			<div class="divFormLabel">Tama&ntilde;o de muestra:</div>
			<div id="divTamanoMuestra"><input type="text" id="inputTamanoMuestra" value="50" onchange="javascript:inputTamanoMuestraOnChange(event)"/></div>
			<div class="divFormLabel">Agregar filtro:</div>
			<div id="divAgregarFiltro"><input type="submit" value="Agregar" id="inputAgregarFiltro" onclick="javascript:inputAgregarFiltroOnClick(event)"/></div>
		</div>
		<div id="divContratos">
			<div id="divTableContratos">
				<table id="tableContratos" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<td class="tdContratoMidNOO" onclick="javascript:tableTheadTdOnClick(event, this)">MID</td>
							<td class="tdContratoFinContratoNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Fin</td>
							<td class="tdContratoTipoContratoDescripcionNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Tipo</td>
							<td class="tdContratoDocumentoNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Documento</td>
							<td class="tdContratoNombreNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Nombre</td>
							<td class="tdContratoDireccionNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Direcci&oacute;n</td>
							<td class="tdContratoCodigoPostalNOO" onclick="javascript:tableTheadTdOnClick(event, this)">CP</td>
							<td class="tdContratoLocalidadNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Localidad</td>
							<td class="tdContratoFactNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Obtenido</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td id="tdContratoCantidadRegistrosLabel"><div>Cantidad de registros:</div></td>
							<td id="tdContratoCantidadRegistrosValor"><div id="divContratoCantidadRegistros">&nbsp;</div></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<div id="divPrepagos">
			<div id="divTablePrepagos">
				<table id="tablePrepagos" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<td class="tdPrepagoMidNOO" onclick="javascript:tableTheadTdOnClick(event, this)">MID</td>
							<td class="tdPrepagoMesAnoNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Mes/A&ntilde;o</td>
							<td class="tdPrepagoMontoMesActualNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Monto actual</td>
							<td class="tdPrepagoMontoMesAnterior1NOO" onclick="javascript:tableTheadTdOnClick(event, this)">Monto ant. 1</td>
							<td class="tdPrepagoMontoMesAnterior2NOO" onclick="javascript:tableTheadTdOnClick(event, this)">Monto ant. 2</td>
							<td class="tdPrepagoMontoPromedioNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Monto prom.</td>
							<td class="tdPrepagoFactNOO" onclick="javascript:tableTheadTdOnClick(event, this)">Obtenido</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td id="tdPrepagoCantidadRegistrosLabel"><div>Cantidad de registros:</div></td>
							<td id="tdPrepagoCantidadRegistrosValor"><div id="divPrepagoCantidadRegistros">&nbsp;</div></td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
	<div id="divModalBackground">&nbsp;</div>
</body>
</html>