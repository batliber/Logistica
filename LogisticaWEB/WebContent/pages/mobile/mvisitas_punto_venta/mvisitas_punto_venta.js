var grid = null;

$(document).ready(init);

function init() {
	$("#divTitle").append("Mis visitas");
	
	grid = new Grid(
		document.getElementById("divTableVisitasPuntoVentaDistribuidor"),
		{
			tdPuntoVenta: { campo: "puntoVenta.nombre", clave: "puntoVenta.id", descripcion: "Punto de venta", abreviacion: "Pto. Venta", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPuntoVentas, clave: "id", valor: "nombre"}, ancho: 120 },
			tdPuntoVentaBarrio: { campo: "puntoVenta.barrio.nombre", clave: "puntoVenta.barrio.id", descripcion: "Barrio", abreviacion: "Barrio", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listBarrios, clave: "id", valor: "nombre"}, ancho: 120 },
			tdObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING },
			tdFechaAsignacion: { campo: "fechaAsignacion", descripcion: "Asignado", abreviacion: "Asignado", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
			tdEstado: { campo: "estadoVisitaPuntoVentaDistribuidor.nombre", clave: "estadoVisitaPuntoVentaDistribuidor.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEstadoVisitaPuntoVentaDistribuidores, clave: "id", valor: "nombre"}, ancho: 80 }
		}, 
		true,
		reloadData,
		trVisitaPuntoVentaDistribuidorOnClick,
		null,
		13,
		35
	);
	
	grid.rebuild();

	grid.filtroDinamico.agregarFiltroManual(
		{
			campo: "estadoVisitaPuntoVentaDistribuidor.nombre",
			operador: "keq",
			valores: ["1"]
		},
		false
	);
	
	navigator.geolocation.getCurrentPosition(
		center, 
		positionError
	);
}

function center(cdata) {
	var crd = cdata.coords;
	
	$("#inputLatitud").val(crd.latitude);
	$("#inputLongitud").val(crd.longitude);
	$("#inputPrecision").val(crd.accuracy);
	
	reloadData();
}

function positionError() {
	alert("No se puede determinar la posición.");
	
	reloadData();
}

function listPuntoVentas() {
	var result = [];
	
	PuntoVentaDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					result = data;
				}
			}, async: false
		}
	);
	
	return result;
}

function listBarrios() {
	var result = [];
	
	BarrioDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					result = data;
				}
			}, async: false
		}
	);
	
	return result;
}

function listEstadoVisitaPuntoVentaDistribuidores() {
	var result = [];
	
	EstadoVisitaPuntoVentaDistribuidorDWR.list(
		{
			callback: function(data) {
				if (data != null) {
					result = data;
				}
			}, async: false
		}
	);
	
	return result;
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	VisitaPuntoVentaDistribuidorDWR.listMisVisitasContextAndLocationAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		$("#inputLatitud").val(),
		$("#inputLongitud").val(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	VisitaPuntoVentaDistribuidorDWR.countMisVisitasContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function trVisitaPuntoVentaDistribuidorOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	window.location.href = "/LogisticaWEB/pages/mobile/mvisita/mvisita.jsp?vid=" + $(target).attr("id");
}