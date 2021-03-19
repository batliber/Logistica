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

function reloadData() {
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/listMisSublotesContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.reload(data);
    });
	
	$.ajax({
        url: "/LogisticaWEB/RESTFacade/ActivacionSubloteREST/countMisSublotesContextAware",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
    }).then(function(data) {
    	grid.setCount(data);
    });
}

function trSubloteOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	window.location.href = 
		"/LogisticaWEB/pages/mobile/mactivacion_sublotes/masignacion_sublote_punto_venta.jsp?sid=" 
		+ $(target).attr("id");
}