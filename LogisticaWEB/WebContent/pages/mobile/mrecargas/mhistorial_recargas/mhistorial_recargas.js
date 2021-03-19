var grid = null;

$(document).ready(init);

function init() {
	$("#divTitle").append("Historial recargas");
	
	grid = new Grid(
		document.getElementById("divTableRecargas"),
		{
			tdEmpresa: { campo: "recargaEmpresa.nombre", clave: "recargaEmpresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listRecargaEmpresas, clave: "id", valor: "nombre" }, ancho: 75 },
			tdFecha: { campo: "fechaSolicitud", descripcion: "Fecha de recarga", abreviacion: "Fecha carg.", tipo: __TIPO_CAMPO_FECHA },
			tdMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO },
			tdMonto: { campo: "monto", descripcion: "Monto", abreviacion: "Monto", tipo: __TIPO_CAMPO_DECIMAL },
			tdEstadoRecarga: { campo: "recargaEstado.nombre", clave: "recargaEstado.id", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listRecargaEstados, clave: "id", valor: "descripcion" }, ancho: 80 },
		}, 
		true,
		reloadData,
		trRecargaOnClick,
		null,
		13,
		35
	);
	
	grid.rebuild();
	
	reloadData();
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/RecargaREST/countContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.setCount(data);
	});
}

function trRecargaOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	window.location.href = 
		"/LogisticaWEB/pages/mobile/mrecargas/mrecarga/mrecarga.jsp?cid=" + $(target).attr("id");
}