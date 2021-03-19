var __EMPRESA_ID_ANTEL_POLO_1 = 63371826;

var grid = null;

$(document).ready(init);

function init() {
	$("#divTitle").append("Mis tr&aacute;mites");
	
	grid = new Grid(
		document.getElementById("divTableContratos"),
		{
			tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdEmpresa: { campo: "empresa.nombre", clave: "empresa.id", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listEmpresas, clave: "id", valor: "nombre" }, ancho: 80 },
			tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO, ancho: 80 },
			tdResultadoEntregaDistribucion: { campo: "resultadoEntregaDistribucion.descripcion", clave: "resultadoEntregaDistribucion.id", descripcion: "Resultado entrega", abreviacion: "Entrega", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listResultadoEntregaDistribuciones, clave: "id", valor: "descripcion" } },
		}, 
		true,
		reloadData,
		trContratoOnClick,
		null,
		13,
		35
	);
	
	grid.rebuild();
	
	grid.filtroDinamico.preventReload = true;
	grid.filtroDinamico.agregarFiltrosManuales(
		[
			{
				campo: "empresa.nombre",
				operador: "keq",
				valores: [63371826]
			}, {
				campo: "resultadoEntregaDistribucion.descripcion",
				operador: "nl",
				valores: []
			}
		], 
		false
	).then(function (data) {
		grid.filtroDinamico.preventReload = false
		
		reloadData();
	});
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/listContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.reload(data);
	});
	
	$.ajax({
		url: "/LogisticaWEB/RESTFacade/ContratoREST/countContextAware",
		method: "POST",
		contentType: 'application/json',
		data: JSON.stringify(grid.filtroDinamico.calcularMetadataConsulta())
	}).then(function(data) {
		grid.setCount(data);
	});
}

function trContratoOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	window.location.href = 
		"/LogisticaWEB/pages/mobile/mdistribucion/mdistribucion.jsp?cid=" + $(target).attr("id");
}