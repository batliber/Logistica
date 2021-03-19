var __REFRESH_TIME = 60000;

var ventasChart = null;
var ventasChartData = {
	labels: [],
	datasets: []
};

var resultadoEntregasChart = null;
var resultadoEntregasChartData = {
	labels: [],
	datasets: []
};

$(document).ready(init);

function init() {
	initListEmpresas();
	
	var fechaHasta = new Date();
	$("#inputFechaHasta").val(formatShortDate(fechaHasta));
	
	fechaHasta.setDate(fechaHasta.getDate() - 10);
	$("#inputFechaDesde").val(formatShortDate(fechaHasta));
	
	var canvasContext = document.getElementById("canvasChartStatusVentas").getContext("2d");
	ventasChart = new Chart(
		canvasContext, 
		{
			type: 'bar',
			data: ventasChartData,
			options: {
				scales: {
					xAxes: [{
						stacked: true,
						ticks: {
							beginAtZero: true
						}
					}],
					yAxes: [{
						stacked: true,
						ticks: {
							beginAtZero: true
						}
					}]
				}
			}
		}
	);
	
	var options = {
		responsive: true,
		tooltips: {
			enabled: true
		},
		plugins: {
			datalabels: {
				formatter: (value, ctx) => {
					let sum = 0;
					let dataArr = ctx.chart.data.datasets[0].data;
					dataArr.map(
						data => { sum += data;}
					);
					let percentage = (value*100 / sum).toFixed(2)+"%";
					return percentage;
				},
				color: '#fff',
			}
		}
	};
	
	var canvasResultadoEntregasContext = document.getElementById("canvasChartResultadoEntregas").getContext("2d");
	resultadoEntregasChart = new Chart(
		canvasResultadoEntregasContext, 
		{
			type: 'pie',
			data: resultadoEntregasChartData,
			options: options
		}
	);
}

function initListEmpresas() {
	listEmpresas()
		.then(function (data) {
			var html = 
				"<div class='divEmpresa'>"
					+ "<div class='divEmpresaNombre' id='labelEmpresaTodas'>Todas</div>"
					+ "<div class='divCheckboxEmpresa'>"
						+ "<input type='checkbox'"
							+ " onchange='javascript:inputTodasOnChange(event, this)'>"
						+ "</input>"
					+ "</div>"
				+ "</div>";
			for (var i=0; i<data.length; i++) {
				html +=
					"<div class='divEmpresa' id='" + data[i].id + "'>" 
						+ "<div class='divEmpresaNombre'>" + data[i].nombre + "</div>"
						+ "<div class='divCheckboxEmpresa'>"
							+ "<input type='checkbox' class='checkboxEmpresa'"
								+ " id='" + data[i].id + "'"
								+ " onchange='javascript:inputEmpresaOnChange(event, this)'>"
							+ "</input>"
						+ "</div>"
					+ "</div>";
			}
			
			$("#divEmpresas").html(html);
			
			roundRobin();
		});
}

function reloadData() {
	var fechaDesde = parseShortDate($("#inputFechaDesde").val());
	var fechaHasta = parseShortDate($("#inputFechaHasta").val());
	var empresas = collectEmpresas();
	
	$.ajax({
		url: "/LogisticaWEB/REST/TramiteREST/listDatosGraficoVentasANTEL",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			desde: formatShortDate(fechaDesde, ""),
			hasta: formatShortDate(fechaHasta, ""),
			empresas: empresas
		})
	}).then(populateGraficoEstadoVentas);
	
	$.ajax({
		url: "/LogisticaWEB/REST/TramiteREST/listDatosGraficoResultadoEntregasANTEL",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			desde: formatShortDate(fechaDesde, ""),
			hasta: formatShortDate(fechaHasta, ""),
			empresas: empresas
		})
	}).then(populateGraficoResultadoEntregas);
}

function collectEmpresas() {
	var result = [];
	
	var checks = $(".checkboxEmpresa:checked");
	for (var i=0; i<checks.length; i++) {
		result[result.length] = checks[i].id;
	}
	
	return result;
}

function inputTodasOnChange(event, element) {
	var pesosElement = $(element);
	if (pesosElement.prop('checked')) {
		$("#labelEmpresaTodas").text("Ninguna");
	} else {
		$("#labelEmpresaTodas").text("Todas");
	}
	
	$(".checkboxEmpresa").prop('checked', pesosElement.prop('checked'));
	
	reloadData();
}

function inputEmpresaOnChange(event, element) {
	reloadData();
}

function populateGraficoEstadoVentas(data) {
	var labels = [];
	var datasetLabels = [
		"PendienteMVD", 
		"PendienteINT", 
		"EntregadoMVD", 
		"EntregadoINT", 
		"RechazadoMVD", 
		"RechazadoINT"
	];
	var datasetValores = {};
	for (var i=0; i<datasetLabels.length; i++) {
		datasetValores[datasetLabels[i]] = [];
	}
	
	var datasetColors = [
		'rgb(255, 217, 102)',
		'rgb(249, 203, 156)',
		'rgb(39, 78, 19)',
		'rgb(56, 118, 29)',
		'rgb(255, 0, 0)',
		'rgb(153, 0, 0)'
	];
	var datasets = [];
	
	for (var i=0; i<data.length; i++) {
		if (labels.length == 0 || labels[labels.length - 1] != data[i].fecha) {
			labels[labels.length] = data[i].fecha;
		}
		datasetValores[data[i].status][datasetValores[data[i].status].length] = data[i].cantidad;
	}
	
	for (var i=0; i<datasetLabels.length; i++) {
		datasets[datasets.length] = {
			label: datasetLabels[i],
			backgroundColor: datasetColors[i],
			data: datasetValores[datasetLabels[i]],
			borderWidth: 1
		};
	}
	
	ventasChartData.labels = labels;
	ventasChartData.datasets = datasets;
	
	ventasChart.update();
}

function populateGraficoResultadoEntregas(data) {
	var labels = [];
	var dataset = [];
	
	var datasetColorsMap = {
		"Pendiente pick-up": "rgb(255, 229, 153)",
		"Firma": "rgb(164, 194, 244)",
		"Picked-up": "rgb(147, 196, 125)",
		"Solicita que pase despues": "rgb(255, 255, 0)",
		"Rechazado": "rgb(255, 0, 0)",
		"Desea cambios en contrato/equipo": "rgb(255, 217, 66)",
		"No atiende/Correo de voz/Reintentar": "rgb(204, 0, 0)"
	};
	
	var datasetColors = [];
	
	for (var i=0; i<data.length; i++) {
		labels[labels.length] = data[i].resultadoEntregaDistribucionDescripcion;
		dataset[dataset.length] = data[i].cantidad;
		datasetColors[datasetColors.length] = datasetColorsMap[data[i].resultadoEntregaDistribucionDescripcion];
	}
	
	resultadoEntregasChartData.labels = labels;
	resultadoEntregasChartData.datasets = [{
		data: dataset,
		backgroundColor: datasetColors
	}];
	
	resultadoEntregasChart.update();
}

function inputFechaDesdeOnChange(event, element) {
	reloadData();
}

function inputFechaHastaOnChange(event, element) {
	reloadData();
}

function roundRobin() {
	var checkboxes = $("input[type='checkbox']");
	
	var selectedIndex = -1;
	for (var i=0; i<checkboxes.length; i++) {
		if ($(checkboxes[i]).prop("checked")) {
			selectedIndex = i;
			break;
		}
	}
	
	$("input[type='checkbox']").prop("checked", false);
	
	if (selectedIndex > -1) {
		// Si hay alguna checkeada.
		$(checkboxes[selectedIndex]).prop("checked", false);
		
		if (selectedIndex + 1 < checkboxes.length) {
			$(checkboxes[selectedIndex + 1]).prop("checked", true);
		} else {
			// Voy a todas.
			$("input[type='checkbox']").prop("checked", true);
		}
	} else if (checkboxes.length > 0) {
		// Si no hay ninguna checkeada voy a Todas.
		$("input[type='checkbox']").prop("checked", true);
	}
	
	reloadData();
	
	setTimeout(roundRobin, __REFRESH_TIME);
}