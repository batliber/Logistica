var grid = null;

$(document).ready(function() {
	grid = new Grid(
		document.getElementById("divTableProcesos"),
		{
			tdFechaInicio: { campo: "fechaInicio", descripcion: "Fecha de inicio", abreviacion: "Inicio", tipo: __TIPO_CAMPO_FECHA },
			tdFechaFin: { campo: "fechaFin", descripcion: "Fecha de fin", abreviacion: "Fin", tipo: __TIPO_CAMPO_FECHA },
			tdObservaciones: { campo: "observaciones", descripcion: "Observaciones", abreviacion: "Observaciones", tipo: __TIPO_CAMPO_STRING, ancho: 400 },
			tdCantidadRegistrosParaProcesar: { campo: "cantidadRegistrosParaProcesar", descripcion: "Para procesar", abreviacion: "Para procesar", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdCantidadRegistrosEnProceso: { campo: "cantidadRegistrosEnProceso", descripcion: "En proceso", abreviacion: "En proceso", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdCantidadRegistrosParaProcesarPrioritario: { campo: "cantidadRegistrosParaProcesarPrioritario", descripcion: "Para procesar prioritario", abreviacion: "Prioritarios", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdCantidadRegistrosProcesado: { campo: "cantidadRegistrosProcesado", descripcion: "Procesados", abreviacion: "Procesados", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 },
			tdCantidadRegistrosListaVacia: { campo: "cantidadRegistrosListaVacia", descripcion: "Lista vac�a", abreviacion: "Lista vac�a", tipo: __TIPO_CAMPO_NUMERICO, ancho: 100 }
		}, 
		false,
		reloadData,
		trProcesoOnClick
	);
		
	grid.rebuild();
	
	reloadData();
});

function reloadData() {
	ACMInterfaceProcesoDWR.listEstadisticas(
		{
			callback: function(data) {
				var registros = {
						cantidadRegistros: data.length,
						registrosMuestra: []
					};
					
				var ordered = data;
				
				var ordenaciones = grid.filtroDinamico.calcularOrdenaciones();
				if (ordenaciones.length > 0 && data != null) {
					ordered = data.sort(function(a, b) {
						var result = 0;
						
						for (var i=0; i<ordenaciones.length; i++) {
							var aValue = null;
							try {
								aValue = eval("a." + ordenaciones[i].campo);
						    } catch(e) {
						        aValue = null;
						    }
						    
						    var bValue = null;
						    try {
								bValue = eval("b." + ordenaciones[i].campo);
						    } catch(e) {
						        bValue = null;
						    }
							
							if (aValue < bValue) {
								result = -1 * (ordenaciones[i].ascendente ? 1 : -1);
								
								break;
							} else if (aValue > bValue) {
								result = 1 * (ordenaciones[i].ascendente ? 1 : -1);
								
								break;
							}
						}
						
						return result;
					});
				}
				
				for (var i=0; i<ordered.length; i++) {
					registros.registrosMuestra[registros.registrosMuestra.length] = {
						fechaInicio: ordered[i].fechaInicio,
						fechaFin: ordered[i].fechaFin,
						observaciones: ordered[i].observaciones,
						cantidadRegistrosParaProcesar: ordered[i].cantidadRegistrosParaProcesar,
						cantidadRegistrosEnProceso: ordered[i].cantidadRegistrosEnProceso,
						cantidadRegistrosParaProcesarPrioritario: ordered[i].cantidadRegistrosParaProcesarPrioritario,
						cantidadRegistrosProcesado: ordered[i].cantidadRegistrosProcesado,
						cantidadRegistrosListaVacia: ordered[i].cantidadRegistrosListaVacia
					};
				}
				
				grid.reload(registros);
			}, async: false
		}
	);
}

function trProcesoOnClick() {
	
}

function inputActualizarOnClick(event) {
	reloadData();
}