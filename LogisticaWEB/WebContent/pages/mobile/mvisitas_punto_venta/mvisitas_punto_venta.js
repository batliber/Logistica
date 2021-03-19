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
			tdFechaVencimientoChipMasViejo: { campo: "puntoVenta.fechaVencimientoChipMasViejo", descripcion: "Fecha de vencimiento chip más viejo", abreviacion: "F. venc. chip", tipo: __TIPO_CAMPO_FECHA, ancho: 90 },
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

	grid.filtroDinamico.preventReload = true;
	grid.filtroDinamico.agregarFiltroManual(
		{
			campo: "estadoVisitaPuntoVentaDistribuidor.nombre",
			operador: "keq",
			valores: ["1"]
		},
		false
	).then(function (data) {
		grid.filtroDinamico.preventReload = false;
		
		navigator.geolocation.getCurrentPosition(
			center, 
			positionError
		);
	});
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

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/listMisVisitasContextAndLocationAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify({
			"metadataConsulta": grid.filtroDinamico.calcularMetadataConsulta(),
			"latitud": $("#inputLatitud").val(),
			"longitud": $("#inputLongitud").val()
		})
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/VisitaPuntoVentaDistribuidorREST/countMisVisitasContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.setCount(data);
	});
}

function trVisitaPuntoVentaDistribuidorOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	window.location.href = "/LogisticaWEB/pages/mobile/mvisita/mvisita.jsp?vid=" + $(target).attr("id");
}