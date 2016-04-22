<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Hist&oacute;rico</title>
	<script type="text/javascript">
		var id = <%= request.getParameter("cid") != null ? request.getParameter("cid") : "null" %>;
	</script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/engine.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/dwr/interface/ContratoRoutingHistoryDWR.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/filtros_dinamicos.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/grid.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/pages/monitoreo/historico.js"></script>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/global.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/filtros_dinamicos.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="/LogisticaWEB/pages/monitoreo/historico.css"/>
</head>
<body>
	<div class="divButtonBar">
		<div class="divButton"><input type="submit" value="Actualizar" onclick="javascript:inputActualizarOnClick(event)"/></div>
		<div class="divButtonBarSeparator">&nbsp;</div>
	</div>
	<div class="divButtonTitleBar">
		<div id="divButtonTitleSingleSize" class="divButtonTitleBarTitle">Acciones</div>
	</div>
	<div class="divMainWindow">
		<div id="divHistorico">
			<div id="divTableHistorico">
				<!-- 
				<table id="tableHistorico" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<td class="tdFecha" onclick="javascript:tableTheadTdOnClick(event, this)">Fecha</td>
							<td class="tdEmpresa" onclick="javascript:tableTheadTdOnClick(event, this)">Empresa</td>
							<td class="tdUsuario" onclick="javascript:tableTheadTdOnClick(event, this)">Usuario</td>
							<td class="tdRol" onclick="javascript:tableTheadTdOnClick(event, this)">Rol</td>
							<td class="tdEstado" onclick="javascript:tableTheadTdOnClick(event, this)">Estado</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</tbody>
				</table>
				-->
				&nbsp;
			</div>
		</div>
	</div>
</body>
</html>