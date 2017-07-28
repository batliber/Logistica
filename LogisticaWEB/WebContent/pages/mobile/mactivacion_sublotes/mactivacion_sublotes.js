var grid = null;

$(document).ready(init);

function init() {
	$("#divTitle").append("Sub-lotes");
	
	grid = new Grid(
		document.getElementById("divTableSublotes"),
		{
			tdNumero: { campo: "numero", descripcion: "Número", abreviacion: "Nro", tipo: __TIPO_CAMPO_NUMERICO, ancho: 75 },
			tdPuntoVenta: { campo: "puntoVenta.nombre", clave: "puntoVenta.id", descripcion: "Punto de venta", abreviacion: "Pto. venta", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listPuntoVentas, clave: "id", valor: "nombre" }, ancho: 200 },
		}, 
		false,
		reloadData,
		trSubloteOnClick,
		null,
		13,
		35
	);
	
	grid.rebuild();
	
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

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	ActivacionSubloteDWR.listMisSublotesContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	ActivacionSubloteDWR.countMisSublotesContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function trSubloteOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	window.location.href = "/LogisticaWEB/pages/mobile/mactivacion_sublotes/masignacion_sublote_punto_venta.jsp?sid=" + $(target).attr("id");
}