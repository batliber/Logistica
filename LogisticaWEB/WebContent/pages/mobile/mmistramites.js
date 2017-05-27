var grid = null;

$(document).ready(function() {
	$("#divTitle").append("Mis tr&aacute;mites");
	
	grid = new Grid(
		document.getElementById("divTableContratos"),
		{
			tdContratoNumeroTramite: { campo: "numeroTramite", descripcion: "Número de trámite", abreviacion: "Trámite", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdContratoMid: { campo: "mid", descripcion: "MID", abreviacion: "MID", tipo: __TIPO_CAMPO_NUMERICO, ancho: 80 },
			tdResultadoEntregaDistribucion: { campo: "resultadoEntregaDistribucion.descripcion", clave: "resultadoEntregaDistribucion.id", descripcion: "Resultado entrega", abreviacion: "Entrega", tipo: __TIPO_CAMPO_RELACION, dataSource: { funcion: listResultadoEntregaDistribuciones, clave: "id", valor: "descripcion" } },
		}, 
		false,
		reloadData,
		trContratoOnClick
	);
	
	grid.rebuild();
	
	reloadData();
});

function listResultadoEntregaDistribuciones() {
	var result = [];
	
	ResultadoEntregaDistribucionDWR.list(
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
	
	ContratoDWR.listContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	ContratoDWR.countContextAware(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function trContratoOnClick(eventObject) {
	var target = eventObject.currentTarget;
	
	window.location.href = "/LogisticaWEB/pages/mobile/mdistribucion.jsp?cid=" + $(target).attr("id");
}