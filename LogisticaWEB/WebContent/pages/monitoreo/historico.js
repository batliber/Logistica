var grid = null;

$(document).ready(function() {
	grid = new Grid(
		document.getElementById("divTableHistorico"),
		{
			tdFecha: { campo: "fecha", descripcion: "Fecha", abreviacion: "Fecha", tipo: __TIPO_CAMPO_FECHA_HORA },
			tdEmpresaNombre: { campo: "empresa.nombre", descripcion: "Empresa", abreviacion: "Empresa", tipo: __TIPO_CAMPO_STRING },
			tdUsuarioAsignadoNombre: { campo: "usuario.nombre", descripcion: "Asignado", abreviacion: "Asignado", tipo: __TIPO_CAMPO_STRING },
			tdRolAsignadoNombre: { campo: "rol.nombre", descripcion: "Rol asignado", abreviacion: "Rol asignado", tipo: __TIPO_CAMPO_STRING },
			tdEstadoNombre: { campo: "estado.nombre", descripcion: "Estado", abreviacion: "Estado", tipo: __TIPO_CAMPO_STRING },
			tdUsuarioAutorNombre: { campo: "usuarioAct.nombre", descripcion: "Autor", abreviacion: "Autor", tipo: __TIPO_CAMPO_STRING }
		}, 
		false,
		reloadData,
		trHistoricoOnClick
	);
	
	grid.rebuild();
	
	reloadData();
});

function reloadData() {
	ContratoRoutingHistoryDWR.listByContratoId(
		id,
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
						id: ordered[i].id,
						fecha: ordered[i].fecha,
						empresa: ordered[i].empresa,
						rol: ordered[i].rol,
						usuario: ordered[i].usuario,
						estado: ordered[i].estado,
						contrato: ordered[i].contrato,
						usuarioAct: ordered[i].usuarioAct,
						uact: ordered[i].uact,
						fact: ordered[i].fact,
						term: ordered[i].term
					};
				}
				
				grid.reload(registros);
			}, async: false
		}
	);
}

function trHistoricoOnClick() {
	
}

function inputActualizarOnClick() {
	reloadData();
}