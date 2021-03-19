<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.3/dist/Chart.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@0.7.0"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/util.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/global.js"></script>
	<script type="text/javascript" src="/LogisticaWEB/js/remote_combo_data.js"></script>
	<title>Tablero ventas ANTEL</title>
	<script type="text/javascript" src="./tablero_ventas_antel_print.js"></script>
	<link rel="stylesheet" type="text/css" href="./tablero_ventas_antel_print.css"/>
</head>
<body>
	<div id="divFiltroEmpresas">
		<div id="divLabelEmpresa" class="divFormLabel">Empresas:</div>
		<div id="divEmpresas" class="divFormValue">&nbsp;</div>
	</div>
	<div id="divFiltros">
		<div id="divLabelFechaDesde" class="divFormLabel">Desde:</div>
		<div id="divFechaDesde" class="divFormValue"><input type="text" id="inputFechaDesde" onchange="javascript:inputFechaDesdeOnChange(event, this)"></input></div>
		<div id="divLabelFechaHasta" class="divFormLabel">Hasta:</div>
		<div id="divFechaHasta" class="divFormValue"><input type="text" id="inputFechaHasta" onchange="javascript:inputFechaHastaOnChange(event, this)"></input></div>
	</div>
	<div id="divCanvasChartStatusVentas">
		<canvas id="canvasChartStatusVentas"></canvas>
	</div>
	<div id="divCanvasChartResultadoEntregas">
		<canvas id="canvasChartResultadoEntregas"></canvas>
	</div>
</body>
</html>