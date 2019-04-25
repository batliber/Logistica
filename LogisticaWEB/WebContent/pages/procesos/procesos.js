var grid = null;

$(document).ready(init);

function init() {
	grid = new Grid(
		document.getElementById("divTableProcesos"),
		{
			tdFechaInicio: { campo: "fechaInicio", descripcion: "Fecha de inicio", abreviacion: "Inicio", tipo: __TIPO_CAMPO_FECHA_HORA },
			tdFechaFin: { campo: "fechaFin", descripcion: "Fecha de fin", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA_HORA },
			tdObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 400 },
			tdCantidadRegistrosParaProcesar: { campo: "cantidadRegistrosParaProcesar", descripcion: "Para procesar", abreviacion: "Para procesar", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdCantidadRegistrosEnProceso: { campo: "cantidadRegistrosEnProceso", descripcion: "En proceso", abreviacion: "En proceso", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdCantidadRegistrosParaProcesarPrioritario: { campo: "cantidadRegistrosParaProcesarPrioritario", descripcion: "Para procesar prioritario", abreviacion: "Prioritarios", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdCantidadRegistrosProcesado: { campo: "cantidadRegistrosProcesado", descripcion: "Procesados", abreviacion: "Procesados", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdCantidadRegistrosListaVacia: { campo: "cantidadRegistrosListaVacia", descripcion: "Lista vacía", abreviacion: "Lista vacía", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 }
		}, 
		true,
		reloadData,
		trProcesoOnClick
	);
		
	grid.rebuild();
	
	reloadData();
}

function reloadData() {
	grid.setStatus(grid.__STATUS_LOADING);
	
	ACMInterfaceProcesoDWR.listEstadisticas(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.reload(data);
			}
		}
	);
	
	ACMInterfaceProcesoDWR.countEstadisticas(
		grid.filtroDinamico.calcularMetadataConsulta(),
		{
			callback: function(data) {
				grid.setCount(data);
			}
		}
	);
}

function trProcesoOnClick() {
	
}

function inputActualizarOnClick(event) {
	reloadData();
}