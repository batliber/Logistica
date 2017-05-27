var grid = null;

$(document).ready(function() {
	grid = new Grid(
		document.getElementById("divTableArchivos"),
		{
			tdNombre: { campo: "nombre", descripcion: "Archivo", abreviacion: "Archivo", tipo: __TIPO_CAMPO_STRING, ancho: 400 }
		}, 
		false,
		reloadData,
		trArchivoOnClick
	);
	
	grid.rebuild();
	
	reloadData();
});

function reloadData() {
	FileManagerDWR.listArchivos(
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
						nombre: "<a href=\"/LogisticaWEB/Download?fn=" + ordered[i].nombre + "\">" + ordered[i].nombre + "</a>"
					};
				}
				
				grid.reload(registros);
			}
		}
	);
}

function trArchivoOnClick() {
	
}

function inputActualizarOnClick(event) {
	reloadData();
}