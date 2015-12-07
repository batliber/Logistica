$(document).ready(function() {
	reloadData();
});

function reloadData() {
	ACMInterfaceProcesoDWR.listEstadisticas(
		{
			callback: function(data) {
				$("#tableProcesos > tbody:last > tr").remove();
				
				for (var i=0; i<data.length; i++) {
					$("#tableProcesos > tbody:last").append(
						"<tr>"
							+ "<td class='tdProcesoFechaInicio'><div class='divProcesoFechaInicio'>" 
								+ (data[i].fechaInicio != null ? formatLongDate(data[i].fechaInicio) : "&nbsp;")
							+ "</div></td>"
							+ "<td class='tdProcesoFechaFin'><div class='divProcesoFechaFin'>" 
								+ (data[i].fechaFin != null ? formatLongDate(data[i].fechaFin) : "&nbsp;")
							+ "</div></td>"
							+ "<td class='tdProcesoObservaciones'><div class='divProcesoObservaciones'>" 
								+ (data[i].observaciones != null ? data[i].observaciones : "&nbsp;")
							+ "</div></td>"
							+ "<td class='tdProcesoCantidadRegistrosParaProcesar'><div class='divProcesoCantidadRegistrosParaProcesar'>" 
								+ (data[i].cantidadRegistrosParaProcesar != null ? data[i].cantidadRegistrosParaProcesar : "0") 
							+ "</div></td>"
							+ "<td class='tdProcesoCantidadRegistrosEnProceso'><div class='divProcesoCantidadRegistrosEnProceso'>" 
								+ (data[i].cantidadRegistrosEnProceso != null ? data[i].cantidadRegistrosEnProceso : "0")
							+ "</div></td>"
							+ "<td class='tdProcesoCantidadRegistrosParaProcesarPrioritario'><div class='divProcesoCantidadRegistrosParaProcesarPrioritario'>" 
								+ (data[i].cantidadRegistrosParaProcesarPrioritario != null ? data[i].cantidadRegistrosParaProcesarPrioritario : "0")
							+ "</div></td>"
							+ "<td class='tdProcesoCantidadRegistrosProcesado'><div class='divProcesoCantidadRegistrosProcesado'>" 
								+ (data[i].cantidadRegistrosProcesado != null ? data[i].cantidadRegistrosProcesado : "0")
							+ "</div></td>"
							+ "<td class='tdProcesoCantidadRegistrosListaVacia'><div class='divProcesoCantidadRegistrosListaVacia'>" 
								+ (data[i].cantidadRegistrosListaVacia != null ? data[i].cantidadRegistrosListaVacia : "0")
							+ "</div></td>"
						+ "</tr>"
					);
				}
			}, async: false
		}
	);
}

function inputActualizarOnClick(event) {
	reloadData();
}